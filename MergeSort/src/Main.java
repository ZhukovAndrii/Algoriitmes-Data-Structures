import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Integer[] arr = new Integer[15];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(1000);
        }
        System.out.println(Arrays.toString(arr));
        Stopwatch a = new Stopwatch();
        a.enter();
        msort(arr);
        a.exit();
        System.out.println(a.elapsedTime());
        System.out.println(Arrays.toString(arr));
    }
    public static <E extends Comparable<E>> void msort (E[] arr){
        if (arr.length > 1) {

            int mid = arr.length / 2;

            E[] larr = Arrays.copyOfRange(arr, 0, mid);
            E[] rarr = Arrays.copyOfRange(arr, mid, arr.length);

            msort(larr);
            msort(rarr);
            merge((Integer[]) arr, (Integer[]) larr, (Integer[]) rarr);
        }
    }

    private static void merge(Integer[] arr, Integer[] larr, Integer[] rarr) {
        int i = 0;
        int j = 0;
        int n = 0;
        for(; i != larr.length && j != rarr.length; n++){
            if (larr[i] < rarr[j]){
                arr[n] = larr[i];
                i++;
            }else{
                arr[n] = rarr[j];
                j++;
            }
        }
        while (i < larr.length) {
            arr[n] = larr[i];
            i++;
            n++;
        }

        while (j < rarr.length) {
            arr[n] = rarr[j];
            j++;
            n++;
        }
    }

    static class Stopwatch {
        private int calls;                 // Number of calls of a routine
        private long counter;              // Explicit counter (see count())
        private long startTime, totalTime; // Timing

        private static long getCpuTime( ) { return System.nanoTime(); }

        public Stopwatch () { start(); }

        public void start () {
            calls = 0;
            counter = 0;
            totalTime = 0;
        }

        public long elapsedTime () { // ms
            return totalTime ;
        }

        public void enter () { // Entering a routine
            startTime = getCpuTime();
            calls++;
        }

        public void exit () { // Exiting a routine
            totalTime += getCpuTime() - startTime;
        }

    }

}