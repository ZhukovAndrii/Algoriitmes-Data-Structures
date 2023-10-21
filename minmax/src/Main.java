import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        int[] a = new int[100];

        for (int i = 0; i < a.length; i++)
            a[i] = rand.nextInt(99);
        System.out.println(Arrays.toString(a));
        Stopwatch b = new Stopwatch();
        b.enter();
        int max = a[0];
        for (int i = 1; i < a.length; i++)
            if (a[i] > max) max = a[i];
        int min = a[0];
        for (int i = 1; i < a.length; i++)
            if (a[i] < min) min = a[i];
        b.exit();
        System.out.println(b.elapsedTime());
        System.out.println(Arrays.toString(a));

    }

    public static int[] minMaxDC(int[] nums, int start, int end) {
        int[] result = new int[2];

        // Base case: if there is only one element, return it as both min and max
        if (start == end) {
            result[0] = nums[start];
            result[1] = nums[start];
            return result;
        }

        // Base case: if there are only two elements, compare them and return the min and max
        if (end - start == 1) {
            result[0] = Math.min(nums[start], nums[end]);
            result[1] = Math.max(nums[start], nums[end]);
            return result;
        }

        // Divide the list into two halves and recursively find the min and max of each half
        int mid = (start + end) / 2;
        int[] left = minMaxDC(nums, start, mid);
        int[] right = minMaxDC(nums, mid + 1, end);

        // Combine the results from the two halves to find the overall min and max
        result[0] = Math.min(left[0], right[0]);
        result[1] = Math.max(left[1], right[1]);

        return result;
    }
}
class Stopwatch {
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