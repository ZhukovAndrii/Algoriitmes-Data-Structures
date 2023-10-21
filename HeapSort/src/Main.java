import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Integer[] arr = new Integer[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(1000);
        }
        Stopwatch a = new Stopwatch();
        a.enter();
        buildHeap(arr);
        a.exit();
        System.out.println(a.elapsedTime());


        Integer[] arr2 = new Integer[100];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = random.nextInt(1000);
        }

        Stopwatch b = new Stopwatch();
        b.enter();
        heapSort(arr2);
        b.exit();
        System.out.println(b.elapsedTime());
    }
    static <E extends Comparable<E>> void buildHeap (E[] a) {
        for (int i = a.length / 2 - 1; i >= 0; i--){
            buildHeap(a, i, a.length - 1);
        }
    }


    static <E extends Comparable<E>> void buildHeap(E[] a, int i, int j) {
        int k = i, m = 2 * k + 1;
        E x = a[i];
        while (m <= j){
            if (m < j && a[m].compareTo(a[m + 1]) < 0)
                m++;
            if (x.compareTo(a[m]) >= 0) {
                break;
            }
            a[k] = a[m];
            k = m;
            m = 2 * k + 1;
        }
        a[k] = x;
    }
    public static <E extends Comparable<E>> void heapSort (E[] a) {
        buildHeap(a);
        for (int j = a.length - 1; j > 0; j--) {
            swap(a, 0, j);
            buildHeap(a, 0, j-1);
        }
    }
    public static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
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