import java.util.Arrays;
import java.util.Random;

public class Main {
    public static Stopwatch logA = new Stopwatch();

    private static int getOpt (String arg, String[] args, int value) {
        for (int i=0; i < args.length; i++)
            if (args[i].charAt(0) == '-') // Option found
                if (args[i].indexOf(arg, 1) == 1) { // arg found
                    value = Integer.parseInt(args[i+1]);
                    break;
                }
        return value;
    }

    private static boolean getFlag (String arg, String[] args) {
        for (int i=0; i < args.length; i++)
            if (args[i].charAt(0) == '-') // Option found
                if (args[i].indexOf(arg, 1) == 1)  // arg found
                    return true;
        return false;
    }


    public static void bSort (int[] a, int k) {
        Bucket[] buckets = new Bucket[k];
        for (int i=0; i < k; i++)
            buckets[i] = new Bucket(a.length/k);
        int max = a[0], min = max;
        for (int v : a) {
            if (v > max)
                max = v;
            else if (v < min)
                min = v;
        }

        double f = (double) k / (max - min + 1);
        for (int key : a) {
            int b = (int)((key - min) * f);
            buckets[b].insert(key);
        }

        for (Bucket b : buckets)
            b.sort();

        int i = 0;
        for (Bucket b : buckets)
            i = b.transfer(a, i);

    }


    public static void main (String[] args) {

        Random random = new Random();
        int[] arr = new int[16];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(1000);
        }
        System.out.println(Arrays.toString(arr));
        Stopwatch a = new Stopwatch();
        a.enter();
        bSort(arr, arr.length / 5);
        a.exit();
        System.out.println(a.elapsedTime());
        System.out.println(Arrays.toString(arr));
    }


    private static boolean isSorted(int[] arr) {
        for (int i=1; i<arr.length; i++)
            if (arr[i-1] > arr[i])
                return false;
        return true;
    }

}
class Bucket {         // Bucket of int values
    int size;          // Physical bucket size
    int[] b;           // Elements of the bucket
    int n;             // No. of elements in the bucket

    Bucket (int expBucketSize) {
        size = expBucketSize;
        b = new int[size];
        n = 0;
    }

    void insert(int e) {
        if (n == size) {
            int[] a = new int[size+1];
            for (int i = 0; i < n; i++) {
                a[i] = b[i];
            }
            b = a;
            size += 1;
        }
        b[n] = e;
        n++;
    }

    void sort () {
        if (n<10) {
            int[] a = Arrays.copyOfRange(b, 0, n);
            Arrays.sort(a);
            for (int i = 0; i < n; i++) {
                b[i] = a[i];
            }
        } else {
            int[] a = Arrays.copyOfRange(b, 0, n);
            Main.bSort(a, 5);
            for (int i = 0; i < n; i++) {
                b[i] = a[i];
            }
        }

    }

    int transfer (int[] array, int start)  {
        for (int i = 0; i < n; i++) {
            array[start + i] = b[i];
        }
        return start + n;
    }

}

class Stopwatch {
    private int calls;                 // Number of calls of a routine
    private long counter;              // Explicit counter (see count())
    private long startTime, totalTime; // Timing

    private static long getCpuTime( ) {
        return System.nanoTime();
    }

    public Stopwatch () { start(); }

    public void start () {
        calls = 0;
        counter = 0;
        totalTime = 0;
    }

    public long elapsedTime () { // ms
        return totalTime ;
    }

    public long getCalls() { return calls; }

    public long getCounter () { return counter; }

    public void count () { counter++; }

    public void enter () { // Entering a routine
        startTime = getCpuTime();
        calls++;
    }

    public void exit () { // Exiting a routine
        totalTime += getCpuTime() - startTime;
    }

    public static String header () { return "Calls Counter Time"; }

    public String toString () { return getCalls() + " " + getCounter() + " " + elapsedTime(); }
}

