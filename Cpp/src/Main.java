import java.util.Random;

class Stopwatch {
    private int calls;                 // Number of calls of a routine
    private long counter;              // Explicit counter (see count())
    private long startTime, totalTime; // Timing

    private static long getCpuTime( ) { return System.currentTimeMillis(); }

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

class Point {
    private double x, y;

    public Point (double x, double y) { this.x = x; this.y = y; }

    public double getX () { return x; }
    public double getY () { return y; }

    public String toString () { return x + "/" + y; }
}

interface PointGenerator {
    public Point next ();
}

class UDGenerator implements PointGenerator { // Unified distribution
    private Random r = new Random();
    private double xMin, xMax, yMin, yMax;

    public UDGenerator (double xMin, double xMax, double yMin, double yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    public Point next () {
        return new Point(r.nextDouble() * (xMax - xMin) + xMin,
                r.nextDouble() * (yMax - yMin) + yMin);
    }

    public String toString () {
        return "UD " + xMin + " " + xMax + " " + yMin + " " + yMax;
    }
}

public class Main {
    private static Stopwatch log = new Stopwatch();

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

    public static void main (String[] args) {
        // Program arguments:
        // -from 32
        // -to 65536 ... range of n
        // -r 10 ....... repetitions
        // -v .......... verbose, turn on report
        final int nMin = getOpt("from", args, 32);
        final int nMax = getOpt("to", args, nMin);        // 33554432
        final double cloudWidth = 1;  // Width of point cloud
        final double cloudHeight = 1; // Hight of point cloud
        final int repeat = getOpt("r", args, 10);         // No. or repetitions for each n
        final boolean report = getFlag("v", args);              // Detailed results

        PointGenerator randomGen =  new UDGenerator(-cloudWidth/2, cloudWidth/2, -cloudHeight/2, cloudHeight/2);

        System.out.println("nMin: " + nMin + " nMax: " + nMax + " width: " + cloudWidth + " height: " + cloudHeight + " repeat: " + repeat + " generator: " + randomGen);
        System.out.println();
        System.out.println("N " + log.header());

        for (int n = nMin; n <= nMax; n *= 2) { // For all n
            log.start();
            for (int t = 1; t <= repeat; t++) { // Repeat experiment
                Point[] points = makePoints(n, randomGen);
                Point[] cpp = findCpp(points); // Result (Point pair)

                if (report)
                    System.out.println("Closest pair of points: " + cpp[0] + " & " + cpp[1] + " d = " + dist(cpp[0], cpp[1]));
            }
            System.out.println(n + " " + log);
        }
    }

    private static double dist (Point p1, Point p2) {
        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();
        return  Math.sqrt(dx*dx + dy*dy);
    }

    private static Point[] findCpp (Point[] points) {
        log.enter();
        Point[] cpp = new Point[2]; // Result: Closest pair of points
        cpp[0] = points[0];
        cpp[1] = points[1];
        // **************
        double way = dist(points[0], points[1]);
        for(int i =0; i<points.length; i++) {
            for (int j = i + 1; j<points.length; j++) {
                if (j != i && dist(points[i], points[j]) < way) {
                    cpp[0] = points[i];
                    cpp[1] = points[j];
                    way = dist(points[i], points[j]);
                }
                log.count();

            }
        }
        // Insert your code here, marking the "innermost point" with
        // **************
        log.exit();
        return cpp;
    }

    private static Point[] makePoints (int n, PointGenerator r) {
        Point[] points = new Point[n];
        for (int i=0; i<n; i++) {
            points[i] = r.next();
        }
        return points;
    }
}

