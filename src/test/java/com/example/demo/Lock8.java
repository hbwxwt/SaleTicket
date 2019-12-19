package com.example.demo;

/**
 * @author wangtong
 * @create 2019-12-16 19:41
 *
 *
 * TimeUnit.SECONDS.sleep(3)  睡眠3秒
 *
 * 题目：多线程8锁
 * 1.标准访问，普通方法先打印邮件还是短信？  Email
 * 2.email方法新增暂停3秒钟，先打印哪个？  Email
 * 3.新增普通Hello方法，先打印Hello还是邮件？  Hello
 * 4.标准访问，两部手机普通方法先打印邮件还是短信？  SMS
 * 5.两个静态同步方法，先打印邮件还是短信？    Email
 * 6.两个静态同步方法，两部手机先打印邮件还是短信？    Email
 * 7.一个普通同步方法，一个静态同步方法，先打印哪个？   SMS
 * 8.一个普通同步方法，一个静态同步方法，两部手机先打印哪个？   SMS
 *
 * 笔记
 * 1-2.一个对象里面如果有多个synchronized方法，某一时刻内，只要一个线程去调用其中的一个synchronized方法了，
 *     其他的线程都只能等待，换句话说，某一时刻内，只能有唯一一个线程去访问这些synhronized方法，
 *     锁的是当前对象this，被锁定后，其他的线程都不能进入到当前对象的其它的synchronized方法。
 *
 * 3-4.加个普通方法后发现和同步锁无关。
 *     换成两个对象后，不是同一把锁了，情况立即变化。
 *
 * 5-6.都换成静态同步方法后，情况又变化
 *     若是普通同步方法，new this，具体的一部部手机，所有的同步方法用的都是同一把锁--实例对象本身。
 *     若是静态同步方法，static Class ，唯一的一个模板。
 *     synchronized是实现同步的基础：java中的每一个对象都可以作为锁。
 *     具体表现为3中形式
 *     对于普通同步方法，锁是当前实例对象，他等同于，对于同步方法块，锁是synchonized括号里的配置的对象
 *     对于静态同步方法，锁的是当前类的Class对象本身
 *
 * 7-8.当一个线程试图访问同步代码时，它首先必须得到锁，退出或抛出异常时必须释放锁。
 *
 *     所有的普通方法用的都是同一把锁--实例对象本身，就是new出来的具体实例对象本身
 *     也就是说，如果一个实例对象的普通同步方法取锁后，该实例对象的其他同步方法必须等待获取的方法释放锁后才能获取锁。
 *     可是别的实例对象的普通同步方法因为跟该实例对象的普通同步方法用的是不同的锁，所以不用等待该实例对象已获取锁的普通
 *     同步方法释放锁就可以获取他们自己的锁。
 *
 *     所有的静态同步方法用的也是同一把锁--类对象本身，就是我们说过的唯一模板Class
 *     具体实例对象this和唯一模板Class，这两把锁是两个不同的对象，所以静态同步方法余普通同步方法之间是不会有竞态条件的。
 *     但是一旦一个静态同步方法获取锁后，其他的静态同步方法都必须等待该方法释放锁后才能获取锁。
 *
 *
 *
 *
 */

class Phone{

   public void sendEmail()throws Exception{
        /*TimeUnit.SECONDS.sleep(3);*/
        System.out.println("sendEmail");
    }
    /*public synchronized void sendSMS()throws Exception{
        System.out.println("sendSMS");
    }*/
    public void Hello(){
        System.out.println("Hello");
    }
}

public class Lock8 {
    public static void main(String[] args) throws Exception{

        Phone phone = new Phone();

        new Thread(() -> {
            try {
                phone.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"A").start();
        new Thread(() -> {
            try {
                phone.Hello();/*phone.sendSMS();*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"B").start();
    }
}
