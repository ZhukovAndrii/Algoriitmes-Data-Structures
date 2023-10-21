public class Main {
    public static void main(String[] args) {

    }
    public static <E extends Comparable<E>> void sort (E[] a) {
        int n = a.length;
        for (int i = 1; i < n; i++){
            for (int j = i; j > 0; j--){
                if (a[j-1].compareTo(a[j]) > 0)
                    swap(a, j-1, j);
                else
                    break;
            }
        }
    }

    private static <E> void swap (E[] a, int i, int j){
        E h = a[i];
        a[i] = a[j];
        a[j] = h;
    }

}