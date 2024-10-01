package esercizioB;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Scanner;


public class ThreadCouples implements Runnable {
    private static int N;
    private AtomicInteger counter; // Contatore per la coppia
    private final Object lock;     // Lock per la coppia
    private int pairId;            // Identificativo della coppia

    public ThreadCouples(int N, AtomicInteger counter, Object lock, int pairId) {
        ThreadCouples.N = N; //
        this.counter = counter;
        this.lock = lock;
        this.pairId = pairId;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                int current = counter.get();
                if (current > N) {
                    break;  // Esce quando il contatore ha superato N
                }
                System.out.println(Thread.currentThread().getName() + " (Coppia " + pairId + ") printed: " + current);
                counter.incrementAndGet();  // Incrementa il contatore per la coppia
            }
        }
    }

    public static void main(String[] args) {
    	Scanner scanner = new Scanner(System.in);
		 
		System.out.print("Scrivi quante coppie di thread vuoi realizzare ");
        int T = scanner.nextInt();
        
        System.out.print("Scrivi fino a quanto deve contare ogni coppia di thread ");
        int N = scanner.nextInt();

        if (T % 2 != 0) {
            System.out.println("Il numero di thread deve essere pari per formare coppie.");
            return;
        }

        for (int i = 0; i < T; i += 2) {
           
            AtomicInteger counter = new AtomicInteger(1);
            Object lock = new Object();
            int pairId = (i / 2) + 1;

            // Crea la prima metà della coppia
            Thread thread1 = new Thread(new ThreadCouples(N, counter, lock, pairId));
            thread1.start();

            // Crea la seconda metà della coppia
            Thread thread2 = new Thread(new ThreadCouples(N, counter, lock, pairId));
            thread2.start();
        }
    }
}