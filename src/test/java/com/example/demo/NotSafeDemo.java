package com.example.demo;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wangtong
 * @create 2019-12-16 20:47
 *
 * 1.故障现象
 *      java.util.ConcurrentModificationException
 * 2.导致原因
 *      2.1 没加锁+tostring遍历读写不一致if(modCount != expectedModCount)
 *
 * 3.解决方法
 *      3.1 Vector();
 *      3.2 Collections.synchronizedList(new ArrayList<>())
 *      3.3 CopyOnWriteArrayList<>()  写时复制
 *
 * 4.优化建议
 *
 * 写时复制
 *     CopyOnWrite容器即写时复制的容器。往一个容器添加元素的时候，不直接往当前容器Object[]添加，而是先将当前容器Object[]进行copy，
 * 复制出一个新的容器Object[] newElements,然后新的容器Object[] newElements里添加元素，添加完成后，
 * 再讲原容器的引用指向新的容器setArray(newElements);这样做的好处是可以对CopyOnWrite容器进行并发的读，
 * 而不需要加锁，因为当前容器不会添加任何元素。所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器。
 *
 */
public class NotSafeDemo {
    public static void main(String[] args) {
        List<String> list = new CopyOnWriteArrayList<>();//Collections.synchronizedList(new ArrayList<>());//new Vector<>();//new ArrayList<>();
        /*list.add("a");
        list.add("b");
        list.add("c");
        //list.forEach(System.out::println);
        for (String element:list){
            System.out.println(element);
        }*/

        for(int i = 1;i <=30 ;i++){
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,6));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
