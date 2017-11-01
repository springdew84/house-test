package com.cassey.house.lock;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

/**
 * 基于redis实现的分布式锁
 * 主要使用SETNX
 * 使用分布式锁要满足的几个条件：
1.系统是一个分布式系统（关键是分布式，单机的可以使用ReentrantLock或者synchronized代码块来实现）
2.共享资源（各个系统访问同一个资源，资源的载体可能是传统关系型数据库或者NoSQL）
3.同步访问（即有很多个进程同事访问同一个共享资源。没有同步访问，谁管你资源竞争不竞争）
 * @author chunyang.zhao
 *
 */
public class RedisLock {
	private final static Logger logger = LoggerFactory.getLogger(RedisLock.class);
	private Jedis jedis;
	private long timeOut;
	private int expireTime;
	private String key;
	private String value;
	private boolean locked = false;
	private Random random = new Random();
	//默认锁超时时间 单位：秒
	private final static int DEFAULT_EXPIRE_TIME = 3;

	public RedisLock(Jedis jedis, String key, Long timeOut, Long expireTime, TimeUnit timeUnit) {
		this.jedis = jedis;
		this.timeOut = timeUnit.toNanos(timeOut);
		this.expireTime = (int) timeUnit.toSeconds(expireTime);
		if (expireTime <= 0) {
			this.expireTime = DEFAULT_EXPIRE_TIME;
		}
		this.key = key;
	}

	/**
	 * @return 获取锁是否成功
	 */
	public boolean tryLock() {
		long nanoTime = System.nanoTime();
		this.value = Thread.currentThread().getName() + "," + nanoTime;
		try {
			//在timeout的时间范围内不断轮询锁
			while (System.nanoTime() - nanoTime < timeOut) {
				//锁不存在的话，设置锁并设置锁过期时间，即加锁
				if (jedis.setnx(key, value) == 1) {
					//设置锁过期时间是为了在没有释放锁的情况下锁过期后消失，不会造成永久阻塞
					jedis.expire(key, expireTime);
					this.locked = true;
					return true;
				}
				//短暂休眠，避免可能的活锁                       
				Thread.sleep(3, random.nextInt(30));
				logger.info("出现锁等待！key={}", key);
				//检验是否因未设置上expire而导致的key无法释放
				if (jedis.ttl(key) == -1) {
					reset(key);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("locking error", e);
		}
		return false;
	}

	private void reset(String key) {
		try {
			String value = jedis.get(key);
			if(StringUtils.isEmpty(value)){
				return;
			}
			Long time = Long.parseLong(value.split(",")[1]);
			//已过期，清除key
			if (System.nanoTime() - time > TimeUnit.SECONDS.toNanos(expireTime)) {
				jedis.expire(key, 0);
			}
		} catch (Exception e) {
			logger.error("Reset redisLock error!key={}", key);
		}

	}

	/**
	 * 由于设置了锁自动过期时间，因次释放锁时，校验是否还是当前线程加的锁
	 */
	public void unlock() {
		if (!this.locked) {
			return;
		}
		if (value.equals(jedis.get(key))) {
			this.locked = true;
			jedis.expire(key, 0);
		} else {
			throw new RuntimeException("Lock has been reset！key={}");
		}

	}

}
