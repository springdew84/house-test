package com.cassey.house.thread;

/**
 * 仓库
 * @author chunyang.zhao
 *
 */
class Depot{
	//仓库容量
	private int capacity;
	//仓库的库存
	private int size;
	//bool
	boolean flag = false;

	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Depot(int capacity){
		this.capacity = capacity;
		this.size = 0;
	}
}

/**
 * 生产者
 * @author chunyang.zhao
 *
 */
class ProducerThread implements Runnable{
	Depot depot;
	
	public ProducerThread(Depot depot){
		this.depot = depot;
	}
	
	@Override
	public void run() {
		while(true){
			synchronized(depot){
				//库存小于容量5时生产每次生产5个
				if(depot.getSize()< (depot.getCapacity()-10)){
					System.out.println("生产前库存:" +depot.getSize());
					java.util.Random r=new java.util.Random(); 
					int count = r.nextInt(10);
					depot.setSize(depot.getSize()+count);
					System.out.println("生产了"+count+"个");
					System.out.println("生产后库存:" +depot.getSize());
					
					depot.notifyAll();
				}else{
					try {
						System.out.println("库存足够，无需生产：" +depot.getSize());
						depot.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}	
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

/**
 * 消费者1
 * @author chunyang.zhao
 *
 */
class ConsumerThread1 implements Runnable{

	Depot depot;
	
	public ConsumerThread1(Depot depot){
		this.depot = depot;
	}
	
	@Override
	public void run() {
		synchronized(depot){
			while(true){
				if(depot.isFlag()){
					depot.setFlag(!depot.isFlag());
					if(depot.getSize()>0){
						depot.setSize(depot.getSize()-1);
						System.out.println("消费者[" 
						+Thread.currentThread().getName() + "]消费了1个 库存：" + depot.getSize());	
					
						depot.notifyAll();
					}else{
						try {
							System.out.println("库存没了，赶紧崔生产:" + depot.getSize());
							depot.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					try {
						depot.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}	
	}
}


/**
 * 消费者1
 * @author chunyang.zhao
 *
 */
class ConsumerThread2 implements Runnable{

	Depot depot;
	
	public ConsumerThread2(Depot depot){
		this.depot = depot;
	}
	
	@Override
	public void run() {
		synchronized(depot){
			while(true){
				if(!depot.isFlag()){
					depot.setFlag(!depot.isFlag());
					if(depot.getSize()>0){
						depot.setSize(depot.getSize()-1);
						System.out.println("消费者[" 
						+Thread.currentThread().getName() + "]消费了1个  ->库存还有：" + depot.getSize());
						
						depot.notifyAll();
					}else{
						try {
							System.out.println("库存没了，赶紧崔生产:" + depot.getSize());
							depot.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					try {
						depot.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}	
	}
}

/**
 * 生产者消费者
 * @author chunyang.zhao
 *
 */
public class ProducerConsumerTest {
	
	public static void main(String[] args){
	
		Depot depot = new Depot(100);
		Thread producer = new Thread(new ProducerThread(depot),"producer");
		Thread consumer1 = new Thread(new ConsumerThread1(depot),"consumer1");
		Thread consumer2 = new Thread(new ConsumerThread2(depot),"consumer2");
		
		producer.start();
		consumer1.start();
		consumer2.start();
	}
}
