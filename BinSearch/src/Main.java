import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        class BinarySearch {
            int binarySearch(int arr[], int x)
            {
                int l = 0, r = arr.length - 1;
                while (l <= r) {
                    int m = l + (r - l) / 2;

                    if (arr[m] == x)
                        return m;

                    if (arr[m] < x)
                        l = m + 1;

                    else
                        r = m - 1;
                }
                return -1;
            }

            public static void main(String args[])
            {
                BinarySearch b = new BinarySearch();
                Random rand = new Random();
                int[] arr = new int[100];

                for (int i = 0; i < arr.length; i++)
                    arr[i] = rand.nextInt(99);
                System.out.println(Arrays.toString(arr));

                int x = 10;
                int result = b.binarySearch(arr, x);
                if (result == -1)
                    System.out.println(
                            "Element is not present in array");
                else
                    System.out.println("Element is present at "
                            + "index " + result);
            }
        }
    }
}