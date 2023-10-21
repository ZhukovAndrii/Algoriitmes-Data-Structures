public class Main {
    public static void main(String[] args) {

    }

    static void countSort (int[] a, int m) {
        int counts[] = new int[m+1];
        for (int v: a) // Counting the occurrences of v
            counts[v]++;
        int i = 0;
        for (int v = 0; v < m; v++)
            for (int c = 1; c <= counts[v]; c++)
                a[i++] = v;
    }

    static void countSort (int[] a) {
        int max = a[0];
        for (int v : a) {
            assert v >= 0;
            if (v > max)
                max = v;

        }
        countSort(a, max);
    }
}