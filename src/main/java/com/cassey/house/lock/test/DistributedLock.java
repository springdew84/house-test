package com.cassey.house.lock.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 分布式锁
 * 
 * 获取锁的时候，使用setnx加锁，并使用expire命令为锁添加一个超时时间，
 * 超过该时间则自动释放锁，锁的value值为一个随机生成的UUID，
 * 通过此在释放锁的时候进行判断
 * 获取锁的时候还设置一个获取的超时时间，若超过这个时间则放弃获取锁。
 * 释放锁的时候，通过UUID判断是不是该锁，若是该锁，则执行delete进行锁释放。
 * @author chunyang.zhao
 *
 */
public class DistributedLock {
	
    private final JedisPool jedisPool;
	private Random random = new Random();

    public DistributedLock(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * 加锁
     * @param locaName  锁的key
     * @param acquireTimeout  获取超时时间
     * @param timeout   锁的超时时间
     * @return 锁标识
     */
    public String lockWithTimeout(String locaName,long acquireTimeout,long timeout) {
        Jedis conn = null;
        String retIdentifier = null;
        try {
            // 获取连接
            conn = jedisPool.getResource();
            // 随机生成一个value
            String identifier = UUID.randomUUID().toString();
            // 锁名，即key值
            String lockKey = "lock:" + locaName;
            // 超时时间，上锁后超过此时间则自动释放锁
            int lockExpire = (int)(timeout / 1000);

            // 获取锁的超时时间，超过这个时间则放弃获取锁
            long end = System.currentTimeMillis() + acquireTimeout;
            while (System.currentTimeMillis() < end) {
                if (conn.setnx(lockKey, identifier) == 1) {
                    conn.expire(lockKey, lockExpire);
                    // 返回value值，用于释放锁时间确认
                    retIdentifier = identifier;
                    return retIdentifier;
                }
                
				//短暂休眠，避免可能的活锁                       
				Thread.sleep(3, random.nextInt(30));
				System.out.println("出现锁等待！thread="+Thread.currentThread().getName());
				
                // 返回-1代表key没有设置超时时间，为key设置一个超时时间
                if (conn.ttl(lockKey) == -1) {
                    conn.expire(lockKey, lockExpire);
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (JedisException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return retIdentifier;
    }

    /**
     * 释放锁
     * @param lockName 锁的key
     * @param identifier    释放锁的标识
     * @return
     */
    public boolean releaseLock(String lockName, String identifier) {
        Jedis conn = null;
        String lockKey = "lock:" + lockName;
        boolean retFlag = false;
        try {
            conn = jedisPool.getResource();
            while (true) {
                // 监视lock，准备开始事务
                conn.watch(lockKey);
                // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                if(null == identifier){
                	throw new Exception("identifier is null");
                }
                if (identifier.equals(conn.get(lockKey))) {
                    Transaction transaction = conn.multi();
                    transaction.del(lockKey);
                    List<Object> results = transaction.exec();
                    if (results == null) {
                        continue;
                    }
                    retFlag = true;
                }
                conn.unwatch();
                break;
            }
        } catch (JedisException e) {
            e.printStackTrace();
        } catch(Exception e){
        	e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return retFlag;
    }
}
