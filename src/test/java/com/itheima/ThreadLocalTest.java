package com.itheima;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {
    @Test
    public void testThreadLocalSetAndGet(){
        ThreadLocal tl=new ThreadLocal<>();

        new Thread(()->{
            tl.set("aa");
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
        },"蓝色").start();

        new Thread(()->{
            tl.set("b");
            System.out.println(Thread.currentThread().getName()+":"+tl.get());
        },"绿色").start();
    }


}
