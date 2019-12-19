package com.example.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author wangtong
 * @create 2019-12-19 11:30
 *
 *
 * 读写锁：
 *      多个线程同时读一个资源类没有任何问题，所以为了满足发展，读取共享资源应该可以同时进行
 *      但是
 *      如果有一个线程想去写共享资源类，就不应该再有其他线程可以对该资源进行读或写
 *
 *
 * 小总结：
 *      读-读能共存
 *      读-写不能共存
 *      写-写不能共存
 *
 * 演示：
 *      1.不加锁， 乱写error， 并发读可以。
 *      2.加LOCK 写可以，但是并发读下降
 *      3.加ReentrantReadWriteLock，写唯一，读并发高性能
 */

class MyCahe{
    private volatile Map<String,String> map = new HashMap<>();
    ReentrantReadWriteLock rw1 = new ReentrantReadWriteLock();

    public void put(String key,String value){
        rw1.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 写入开始");
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t 写入结束");
        } finally {
            rw1.writeLock().unlock();
        }
    }

    public void get(String key){
        rw1.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 读取开始");
            String result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取结束"+result);
        } finally {
            rw1.readLock().unlock();
        }
    }

















    /*private Lock lock = new ReentrantLock();

    public void put(String key,String value){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 写入开始");
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t 写入结束");
        } finally {
            lock.unlock();
        }
    }

    public void get(String key){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 读取开始");
            String result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取结束"+result);
        } finally {
            lock.unlock();
        }
    }*/
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCahe myCahe = new MyCahe();

        for(int i = 1;i <=10 ;i++){
            final int tempI = i;
            new Thread(() -> {
                myCahe.put(tempI+"",tempI+"");
            },String.valueOf(i)).start();
        }

        for(int i = 1;i <=10 ;i++){
            final int tempI = i;
            new Thread(() -> {
                myCahe.get(tempI+"");
            },String.valueOf(i)).start();
        }
    }
}
