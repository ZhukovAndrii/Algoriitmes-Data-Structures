import java.util.*;

public class Main {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        int v = Integer.parseInt(args[2]);

        Array(n);
        Array(m);
        Array(v);
        List(n);
        List(m);
        List(v);
        Hash(n);
        Hash(m);
        Hash(v);
        LinkedHash(n);
        LinkedHash(m);
        LinkedHash(v);
        Tree(n);
        Tree(m);
        Tree(v);
        Priority(n);
        Priority(m);
        Priority(v);
    }

    public static void Array(int n) {
        ArrayList<Integer> arr = new ArrayList<>();
        Random random = new Random();
        int key = random.nextInt(n);
        for (int i = 0; i < n; i++) {
            arr.add(random.nextInt(100));
        }
        Stopwatch a = new Stopwatch();
        a.enter();
        arr.get(key);
        arr.add(n - 1, 0);
        arr.remove(n-1);
        for (int i = 0; i < n; i++) {
            key = arr.get(i);
        }
        a.exit();
        System.out.println("Time for ArrayList of " + n + " size: " + a.elapsedTime());
    }


    public static void List(int n) {
        LinkedList<Integer> arr = new LinkedList<>();
        Random random = new Random();
        int key = random.nextInt(n);
        for (int i = 0; i < n; i++) {
            arr.add(random.nextInt(100));
        }
        Stopwatch a = new Stopwatch();
        a.enter();
        arr.get(key);
        arr.add(n - 1, 0);
        arr.remove(n - 1);
        for (int i = 0; i < n; i++) {
            key = arr.get(i);
        }
        a.exit();
        System.out.println("Time for LinkedList of " + n + " size: " + a.elapsedTime());
    }
    public static void Hash(int n) {
        HashSet<Integer> set1 = new HashSet<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            set1.add(random.nextInt(100));
        }
        Stopwatch a = new Stopwatch();
        a.enter();
        set1.contains(0);
        set1.add(0);
        set1.remove(0);
        set1.iterator();
        a.exit();
        System.out.println("Time for HashSet of " + n + " size: " + a.elapsedTime());
    }
    public static void LinkedHash(int n) {
        LinkedHashSet<Integer> set1 = new LinkedHashSet<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            set1.add(random.nextInt(100));
        }
        Stopwatch a = new Stopwatch();
        a.enter();
        set1.contains(0);
        set1.add(0);
        set1.remove(0);
        set1.iterator();
        a.exit();
        System.out.println("Time for LinkedHashSet of " + n + " size: " + a.elapsedTime());
    }
    public static void Priority(int n) {
        PriorityQueue q = new PriorityQueue<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            q.add(random.nextInt(100));
        }
        Stopwatch a = new Stopwatch();
        a.enter();
        q.contains(0);
        q.add(0);
        q.remove(0);
        q.iterator();
        a.exit();
        System.out.println("Time for PriorityQueue of " + n + " size: " + a.elapsedTime());
    }
    public static void Tree(int n) {
        TreeSet t = new TreeSet<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            t.add(random.nextInt(100));
        }
        Stopwatch a = new Stopwatch();
        a.enter();
        t.contains(0);
        t.add(0);
        t.remove(0);
        t.iterator();
        a.exit();
        System.out.println("Time for TreeSet of " + n + " size: " + a.elapsedTime());
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