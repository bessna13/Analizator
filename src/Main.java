import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<String> a = new ArrayBlockingQueue<>(100);
        BlockingQueue<String> b = new ArrayBlockingQueue<>(100);
        BlockingQueue<String> c = new ArrayBlockingQueue<>(100);


        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                String letters = generateText("abc", 100000);
                try {
                    a.put(letters);
                    b.put(letters);
                    c.put(letters);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }


        });

        thread.start();

        Thread threadA = new Thread(() -> {
            int maxCount = 0;
            for (int i = 0; i < 10000; i++) {
                try {
                    maxCount = Math.max(maxCount, count(a.take(), 'a'));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Max a: " + maxCount);
        });
        threadA.start();

        Thread threadB = new Thread(() -> {
            int maxCount = 0;
            for (int i = 0; i < 10000; i++) {
                try {
                    maxCount = Math.max(maxCount, count(b.take(), 'b'));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Max b: " + maxCount);
        });
        threadB.start();

        Thread threadC = new Thread(() -> {
            int maxCount = 0;
            for (int i = 0; i < 10000; i++) {
                try {
                    maxCount = Math.max(maxCount, count(c.take(), 'c'));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("Max c: " + maxCount);
        });
        threadC.start();
    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static int count(String letters, char character) {
        int count = 0;
        for (int i = 0; i < 100000; i++) {
            if (character == letters.charAt(i)) {
                count++;
            }
        }
        return count;
    }
}