package com.example.demo;

import java.util.concurrent.CountDownLatch;

/**
 * @author wangtong
 * @create 2019-12-19 14:24
 *
 * CountDownLatch倒计时
 *
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for(int i = 1;i <=6 ;i++){
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+"\t 离开教室");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName()+"\t 班长关门走人");

    }
}
