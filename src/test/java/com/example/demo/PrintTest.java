package com.example.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangtong
 * @create 2019-12-17 20:34
 * <p>
 * 多线程之间按照顺序调用
 * 三个线程启动，要求：
 * A打印5次，B打印10次，C打印15次
 * 接着
 * A打印5次，B打印10次，C打印15次
 * ......
 */
class Print {
    private int flag = 1;
    private Lock lock = new ReentrantLock();//A:1  B:2  C:3
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();

    public void print1() throws InterruptedException {
        lock.lock();
        try {
            while (flag != 1) {
                c1.await();
            }
            for (int i = 0; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + "A打印" + i);
            }
            flag = 2;
            c2.signal();
        }  finally {
            lock.unlock();
        }
    }

    public void print2() throws InterruptedException {
        lock.lock();
        try {
            while (flag != 2) {
                c2.await();
                for (int i = 0; i <= 10; i++) {
                    System.out.println(Thread.currentThread().getName() + "B打印" + i);
                }
                flag = 3;
                c3.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void print3() throws InterruptedException {
        lock.lock();
        try {
            while (flag != 3) {
                c3.await();
                for (int i = 0; i <= 15; i++) {
                    System.out.println(Thread.currentThread().getName() + "C打印" + i);
                }
                flag = 1;
                c1.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}

public class PrintTest {

    public static void main(String[] args) {
        Print print = new Print();
        new Thread(() -> {
            for (int i = 0; i <=10 ; i++) {
                try {
                    print.print1();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            },"A").start();

        new Thread(() -> {
            for (int i = 0; i <=10 ; i++) {
                try {
                    print.print2();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(() -> {
            for (int i = 0; i <=10 ; i++) {
                try {
                    print.print3();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },"C").start();
    }
}
