/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wj;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Administrator
 */
public class TestSingleThreadExecutor {

    public static void main(String[] args) {

        MyThread t1 = new MyThread();
        MyThread t2 = new MyThread();

        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        threadPool.execute(t1);
        threadPool.execute(t2);
        threadPool.shutdown();

    }

}
