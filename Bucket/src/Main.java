import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        int[] arr = new int[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(1000);
        }
        System.out.println(Arrays.toString(arr));
        Stopwatch a = new Stopwatch();
        a.enter();
        sort(arr);
        a.exit();
        System.out.println(a.elapsedTime());
        System.out.println(Arrays.toString(arr));
    }

    public static void sort(int[] elements) {
        int maxValue = findMax(elements);
        int[] counts = new int[maxValue + 1];

        // Phase 1: Count
        for (int element : elements) {
            counts[element]++;
        }

        // Phase 2: Write results back
        int targetPos = 0;
        for (int i = 0; i < counts.length; i++) {
            for (int j = 0; j < counts[i]; j++) {
                elements[targetPos++] = i;
            }
        }
    }

    private static int findMax(int[] elements) {
        int max = 0;
        for (int element : elements) {
            if (element < 0) {
                throw new IllegalArgumentException("This implementation does not support negative values.");
            }
            if (element > max) {
                max = element;
            }
        }
        return max;
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