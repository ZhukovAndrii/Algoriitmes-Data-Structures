import java.util.Random;
import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        Random random = new Random();
        int[] arr = randomIntArray();
        List<Integer> list = new List<>();

        for (int i = 0; i < 10000; i++) {
            list.append(arr[i]);
        }

        int key = random.nextInt(1000);

        Stopwatch a = new Stopwatch();
        a.enter();
        for (int i = 0; i < 10000; i++){
            if (arr[i] == key){
                a.exit();
                break;
            }
        }

        //////////////




        Stopwatch f = new Stopwatch();
        f.enter();
        list.T(key);
        f.exit();
        System.out.println("Time for T rule: " + f.elapsedTime());
    }




    public static int[] randomIntArray(){
        Random random = new Random();
        int[] arr = new int[10000];
        for (int i = 0; i < 10000; i++) {
            arr[i] = random.nextInt(1000);
        }
        Arrays.sort(arr);

        return arr;
    }

}

class List<E> {
    private class ListElement {
        E value;
        ListElement next;

        ListElement(E v, ListElement n) {
            value = v;
            next = n;
        }

        ListElement() {
        }
    }

    private ListElement first = null;
    private ListElement last = null;

    public List() {
    }

    private List(ListElement e) {
        first = e;
    }

    public boolean empty() {
        return first == null;
    }

    public void insert(E value) { // Inserts at the beginning
        first = new ListElement(value, first);

    }

    public E head() {
        return first.value;
    }

    public List<E> tail() { // Returns the remainder of the list
        return first == null ? null : new List<>(first.next);
    }

    public List<E> find(E key) {// Returns a sublist beginning
        for (ListElement l = this.first; l != null; l = l.next) {
            if (l.value == key) {
                return new List(l);
            }
        }

        return null;
    }

    public void Mf(E key) {
        for (ListElement l = this.first; l.next != null; l = l.next) {
            if (l.next.value == key) {
                ListElement temp = l.next;
                l.next = l.next.next;
                this.insert(temp.value);
                return;
            }
        }
    }

    public void T(E key) {

        for (ListElement l = this.first; l.next.next != null; l = l.next) {
            if (l.next.next.value == key) {
                ListElement temp = l.next.next;
                l.next.next = l.next.next.next;
                temp.next = l.next;
                l.next = temp;
                return;
            }
        }
    }


    public int FcIndex(E key){

        int index = 0;
        ListElement k = this.first;

        for (ListElement l = this.first; l != null; l = l.next){
            index++;
            if (l.value == key)
                return index;
        }
        return -1;
    }
    public void Fc(E key){
        int[] freq = new int[10000];
        List<Integer> list = new List<>();
        if (list.FcIndex((Integer) key) > -1) {
            freq[list.FcIndex((Integer) key)]++;
            for(int i = 1; i < 100; i++){
                for(int j = 0; j < 10000; j++){
                    if(freq[j] == i){
                        int step = 0;
                        for (ListElement l = this.first; l.next != null; l = l.next){
                            if (i == step){
                                E temp =  l.value;
                                l.value = (E) l.next;
                                list.insert((Integer) temp);
                                step++;
                            }
                        }
                        i--;
                    }
                }
            }
        }
    }

    public void append(E e){
        ListElement oldLast = this.last;
        last = new ListElement();
        last.value = e;
        last.next = null;
        if(empty())
            first = last;
        else
            oldLast.next = last;
    }
    public E remove() {
        if (first == null) {
            return null;
        } else {
            E value = first.value;
            first = first.next;
            if (first == null) {
                last = null;
            }
            return value;
        }
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



/* (6) The relationship between index size and block size depends on the use case and index type. Larger block sizes
result in fewer blocks and faster searches, but can waste space and require frequent updates. Leaving sublists unsorted
is often more efficient in external sorting algorithms because it reduces disk I/O operations.
 */

