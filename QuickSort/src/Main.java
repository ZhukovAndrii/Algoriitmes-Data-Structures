import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Integer[] arr = new Integer[10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(1000);
        }
        System.out.println(Arrays.toString(arr));
        Stopwatch a = new Stopwatch();
        a.enter();
        qsort(arr);
        a.exit();
        System.out.println(a.elapsedTime());
        System.out.println(Arrays.toString(arr));

        Integer[] arr2 = new Integer[10];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = random.nextInt(1000);
        }

        Arrays.sort(arr2);
        arr2[0] = arr2[arr.length-1] + 1;

        System.out.println(Arrays.toString(arr2));
        Stopwatch b = new Stopwatch();
        b.enter();
        qsort(arr2);
        b.exit();
        System.out.println(b.elapsedTime());
        System.out.println(Arrays.toString(arr2));
    }
    private static <E extends Comparable<E>> void qsort (E[] a, int lo, int hi) {
        if (lo < hi) {
            int mid = hi / 2;
            E med;
            if (a[lo].compareTo(a[mid]) <= 0 && a[lo].compareTo(a[hi]) <= 0) {
                med = a[lo];
            } else if ((a[lo].compareTo(a[mid]) >= 0 && a[lo].compareTo(a[hi]) >= 0) || (a[lo].compareTo(a[mid]) <= 0 && a[lo].compareTo(a[hi]) >= 0)){
                med = a[mid];
            }
            else
                med = a[hi];

            if (med == a[lo]) {
                swap(a, lo, hi);
            } else if (med == a[mid]) {
                swap(a, mid, hi);
            }

            E pivot = a[hi];
            int i = lo - 1, j = hi;
            while (true) {
                do i++; while (a[i].compareTo(pivot) < 0);
                do j--; while (j >= 0 && a[j].compareTo(pivot) > 0);
                if (i >= j)
                    break;
                swap(a, i, j);
            }
            swap(a, i, hi);
            qsort(a, lo, i-1);
            qsort(a, i+1, hi);
        }
    }
    public static <E extends Comparable<E>> void qsort (E[] a) {
        qsort(a, 0, a.length-1);
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

