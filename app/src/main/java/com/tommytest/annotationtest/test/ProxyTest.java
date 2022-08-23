package com.tommytest.annotationtest.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {

    public static void main(String[] args) {

        Push newpusher = new Push() {
            @Override
            public void push() {
                System.out.println("push in newpusher");
            }
        };

        Object o = Proxy.newProxyInstance(ProxyTest.class.getClassLoader(),
                //实现多个接口
                new Class[]{Push.class, Pull.class},
                new InvocationHandler() {
                    // 起到监听、回调的作用
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        System.out.println("invoke in Proxy");
                        return method.invoke(newpusher, objects);
                    }
                }
        );

        Push pusher = (Push)o;
        pusher.push();


        Pull puller = (Pull)o;
        puller.pull();
    }
}
