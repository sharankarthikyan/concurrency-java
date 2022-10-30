public class Main {
    public static void main(String[] args) {
        System.out.println("Hello from the main thread!!");

        Thread anotherThread = new AnotherThread();
        anotherThread.start();

        System.out.println("Hello again from the main thread!!");
    }
}