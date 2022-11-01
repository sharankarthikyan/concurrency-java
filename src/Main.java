public class Main {
    public static void main(String[] args) {
        Countdown countdown = new Countdown();

        // In below 2 thread processing a same object.
        // Assume that object is a bank account object, then 2 thread takes a thread and process them parallel.
        // This cause a problem in bank system.
        // Hence, synchronization was created. With the help of this no 2 parallel thread can't access the same object, which is present in heap.
        // This synchronization creates a lock for that object.
        CountdownThread t1 = new CountdownThread(countdown);
        t1.setName("Thread 1");
        CountdownThread t2 = new CountdownThread(countdown);
        t2.setName("Thread 2");

        t1.start();
        t2.start();
    }
}

class Countdown {
    private int i;
    public void doCountdown() {
        String color;

        switch (Thread.currentThread().getName()) {
            case "Thread 1":
                color = ThreadColor.ANSI_CYAN;
                break;
            case "Thread 2":
                color = ThreadColor.ANSI_PURPLE;
                break;
            default:
                color = ThreadColor.ANSI_GREEN;
                break;
        }

        /*
            synchronized(object) {
                // block of code
            }
        */

        synchronized(this) { // synchronized current object
            for (i = 10; i >= 1; i--) {
                System.out.println(color + Thread.currentThread().getName() + ": i = " + i);
            }
        }
    }
}

class CountdownThread extends Thread {
    private Countdown threadCountdown;
    public CountdownThread(Countdown countdown) {
        threadCountdown = countdown;
    }

    public void run() {
        threadCountdown.doCountdown();
    }
}