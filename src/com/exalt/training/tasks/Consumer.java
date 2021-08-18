package com.exalt.training.tasks;

import java.util.List;

public class Consumer implements Runnable {
    private final List<Integer> taskQueue;
    public volatile boolean exit = false;

    public Consumer(List<Integer> taskQueue) {
        this.taskQueue = taskQueue;

    }

    @Override
    public void run() {
        while (!exit) {
            try {
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Consumer thread stopped");

    }

    private void consume() throws InterruptedException {
        synchronized (taskQueue) {
            //keep consuming elements whenever it finds something in taskQueue
            while (taskQueue.isEmpty()) {
                System.out.println("Queue is empty " + Thread.currentThread().getName() + " is waiting, size " + taskQueue.size());
                taskQueue.wait();
            }
            Thread.sleep(1000);
            int i = (Integer) taskQueue.remove(0);
            System.out.println("Consumed: " + i);
            //the last-time wait() method was called by producer thread
            taskQueue.notifyAll();
        }
    }

    public void exitThread() {
        exit = true;
    }
}
