package ru.javawebinar.basejava;

class MainDeadlock {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        new Thread(() -> deadlockOperation(lock1, lock2)).start();

        new Thread(() -> deadlockOperation(lock2, lock1)).start();
    }

    public static void deadlockOperation(Object lock1, Object lock2)  {
        synchronized (lock1) {
            System.out.println("start of deadLockOperation...");
            System.out.println(Thread.currentThread().getName() + " is working...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock2) {
                System.out.println("end of deadLockOperation!");
            }
        }
    }
}
