package com.exalt.training.tasks;

import java.util.List;

public class Producer implements Runnable {
    private final List<Integer> taskQueue;
    private final int MAX_CAPACITY;
    public volatile boolean exit = false;

    public Producer(List<Integer> taskQueue, int MAX_CAPACITY) {
        this.taskQueue = taskQueue;
        this.MAX_CAPACITY = MAX_CAPACITY;

    }

    @Override
    public void run() {
        int counter = 0;
        while (!exit) {
            try {
                produce(counter++);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Producer thread stopped");
    }

    private void produce(int i) throws InterruptedException {
        synchronized (taskQueue) {
            while (taskQueue.size() == MAX_CAPACITY) {
                System.out.println("Queue is full " + Thread.currentThread().getName() + " is waiting, size: " + taskQueue.size());
                taskQueue.wait(); //Causes the current thread to wait until another thread invokes the notify() method
            }

            //queue is not full, produce a new resource in very 1 second and put in the queue
            Thread.sleep(1000);
            taskQueue.add(i);
            System.out.println("Produced: " + i);
            //Wakes up all threads that are waiting on this object's monitor
            //in this case it's the consumer thread (the last-time wait() method was called by consumer thread )
            taskQueue.notifyAll();
        }
    }

    public void exitThread() {
        exit = true;
    }
}
