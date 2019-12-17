package com.example.demo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangtong
 * @create 2019-12-13 18:21
 *
 * 题目：三个售票员卖出30张票
 *  * 目的：写出企业级多线程程序
 *
 * 异常
 * illegalThreadStateException 异常的线程状态  原因threadStatus!=0
 *一.高聚合低耦合前提下，线程操作资源类
 * λ表达式 LambdaExpression
 * 1.必须是函数式接口
 * 2.必须是一个方法
 * 3.拷贝小括号，写死右箭头，落地大括号
 * 4.@FunctionalInterface 函数式接口（只能有一个函数方法）
 * 5.default 默认方法的实现
 * 6.static 静态方法的实现
 *
 */

class Ticket{
    private int number = 30;
    private Lock lock = new ReentrantLock();
    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "\t 卖出第：" + (number--) + "\t 还剩下：" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

       /* public synchronized void sale(){
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "\t 卖出第：" + (number--) + "\t 还剩下：" + number);
            }
        }*/

    }
}


public class SaleTicket {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <=35 ; i++) {
                    ticket.sale();
                }
            }
        },"A").start();
        new Thread(() -> { for (int i = 0; i <=35 ; i++) ticket.sale(); },"B").start();
        new Thread(() -> { for (int i = 0; i <=35 ; i++) ticket.sale(); },"C").start();
    }
}





