package com.example.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author wangtong
 * @create 2019-12-19 14:33
 *
 * CyclecBarrier 正计时
 *
 */
public class CyclecBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,() -> {
            System.out.println("集齐七颗龙珠，召唤神龙");
    });
            for(int i = 1;i <=7 ;i++) {
                final int tempI = i;
                new Thread(() -> {
                    try {
                        System.out.println(Thread.currentThread().getName() + "\t 收集到第：" + tempI + "\t 颗龙珠");
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    } finally {
                    }
                }, String.valueOf(i)).start();
            }
    }
}
