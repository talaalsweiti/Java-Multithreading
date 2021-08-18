package com.exalt.training.tasks;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static volatile boolean exit = false;
    public static void main(String[] args) {
        List<Integer> taskQueue = new ArrayList<Integer>();
        int MAX_CAPACITY = 5;

        Producer producer = new Producer(taskQueue, MAX_CAPACITY);
        Thread producerThread = new Thread(producer, "Producer");
        Consumer consumer = new Consumer(taskQueue);
        Thread consumerThread = new Thread(consumer, "Consumer");
        producerThread.start();
        consumerThread.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        producer.exitThread();
        consumer.exitThread();


    }
}
