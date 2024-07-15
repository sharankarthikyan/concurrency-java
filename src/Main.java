import java.util.Random;

// Issues Leading to Deadlock:
// 1. Busy Waiting:
//     In both the read() and write() methods, 
//     while loops that wait for a condition 
//     (empty for read() and !empty for write()). However, 
//     these loops do not yield control or allow the threads 
//     to be notified when the condition changes. This is 
//     referred to as "busy waiting," where the thread is 
//     constantly checking the condition without pausing or 
//     releasing the lock.
// 2. Lack of Notification:
//     The write() method should notify waiting threads after 
//     writing a message, and the read() method should notify 
//     after reading a message. Without these notifications, 
//     the threads that are waiting in the while loops may never 
//     proceed, leading to a deadlock.

// Suggested Fixes:
//     To resolve the deadlock, you can use wait() and notifyAll() 
//     methods to manage the state changes and let threads sleep 
//     while they are waiting for a condition to become true.

public class Main {
    public static void main(String[] args) {
        Message message = new Message();
        (new Thread(new Writer(message))).start();
        (new Thread(new Reader(message))).start();
    }
}

class Message {
    private String message;
    private boolean empty = true;

    public synchronized String read() {
        while (empty) {
//            try {
//                wait(); // Wait until notified
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt(); // Restore interrupt status
//            }
        }
        empty = true;
//        notifyAll(); // Notify waiting readers
        return message;
    }

    public synchronized void write(String message) {
        while (!empty){
//            try {
//                wait(); // Wait until notified
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt(); // Restore interrupt status
//            }
        }
        empty = false;
        this.message = message;
//        notifyAll(); // Notify waiting readers
    }
}

class Writer implements Runnable {
    private Message message;

    public Writer(Message message) {
        this.message = message;
    }

    public void run() {
        String messages[] = {
                "Humpty Dumpty sat on a wall",
                "Humpty Dumpty had a great fall",
                "All the king's horses and all the king's men",
                "Couldn't put Humpty together again"
        };

        Random random = new Random();
        for(int i = 0; i < messages.length; i++) {
            message.write(messages[i]);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {

            }
        }
        message.write("Finished");
    }
}

class Reader implements Runnable{
    private Message message;

    public Reader(Message message) {
        this.message = message;
    }

    public void run() {
        Random random = new Random();
        for(String latestMessage = message.read(); !message.equals("Finished"); latestMessage = message.read()) {
            System.out.println(latestMessage);
            try{
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {

            }
        }
    }
}
