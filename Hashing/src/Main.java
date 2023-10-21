import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        LinearHashing linearHashing = new LinearHashing();

        String command;
        boolean running = true;
        do {
            linearHashing.show();
            System.out.print("> ");
            switch (command = in.next()) {
                case "ins":
                case "insert":
                    linearHashing.insert(in.nextInt());
                    break;

                case "del":
                case "delete":
                    linearHashing.delete(in.nextInt());
                    break;

                case "find":
                    System.out.println(linearHashing.search(in.nextInt()));
                    break;

                case "split":
                    linearHashing.split();
                    break;

                case "merge":
                    linearHashing.merge();
                    break;

                case "x":
                case "q":
                    running = false;
                    break;

                default: // Check if number -> implicit insert
                    try {
                        linearHashing.insert(Integer.valueOf(command));
                    } catch (Exception x) {
                        System.out.println("Error " + x + ". Try again.");
                    }
            }
        } while (running);
        System.out.println("Done.");
    }
}

class LinearHashing { // Using Integer as keys and NO value component
    private static final int INITIAL_I = 1; // Initial number of buckets = 2^INITIAL_I

    class Bucket { // Normally also features a (short) array of directly stored elements – for simplicity, this version uses the overflow chain only
        List<Integer> elements = new LinkedList<>(); // Initially empty overflow chain
    }

    private List<Bucket> hashTable;
    private int nextToSplit;
    private int N, I, n; // Table size N = 2^I, n = number of elements stored

    public LinearHashing() {
        I = INITIAL_I;
        N = 1 << I;
        n = 0;
        hashTable = new ArrayList<>(N);
        for (int i = 0; i < N; i++) {
            hashTable.add(new Bucket());
        }
        nextToSplit = 0;
    }

    private int hash (int key) {
        return key % (1 << I); // 2^i
    }
    private int hash1 (int key) {
        return key % (1 << (I+1)); // 2^(i+1)
    }

    public void insert (int key) {
        Bucket bucket = findBucket(key);
        if (!bucket.elements.contains(key)) {
            bucket.elements.add(key);
            n++;
            if (bucket.elements.size() > 2)
                split();
        }
    }

    public boolean search (int key) {
        return findBucket(key).elements.contains(key);
    }

    private Bucket findBucket (int key) {
        int hashValue = hash(key);
        if (hashValue < nextToSplit)
            hashValue = hash1(key);
        return hashTable.get(hashValue);
    }

    public void delete (int key) {
        Bucket bucket = findBucket(key);
        if(bucket.elements.contains(key)){
            bucket.elements.remove(key);
            n--;
        }
        if(bucket.elements.size() < 2){
            merge();
        }
    }

    public void split () { // Split bucket nextToSplit
        Bucket originalBucket = hashTable.get(nextToSplit);
        Bucket newBucket = new Bucket();
        for (int i = originalBucket.elements.size() - 1; i >= 0; i--) {
            int key = originalBucket.elements.get(i);
            int newHash = hash1(key);
            if (newHash != nextToSplit) { // Move element to new bucket
                assert newHash == N;
                newBucket.elements.add(key);
                originalBucket.elements.remove(i);
            }
        }
        hashTable.add(newBucket);
        N++;
        nextToSplit++;

        if (nextToSplit * 2 == N) { // Round completed
            I++; // Increase level
            nextToSplit = 0;
        }
    }

    public void merge () { // Merge bucket nextToSplit-1
        int mergeIndex = nextToSplit -1;
        if(mergeIndex < 0){
            mergeIndex = N - 1;
        }
        Bucket OriginalBucket = hashTable.get(mergeIndex);
        Bucket previousbucket = hashTable.get(mergeIndex -1);

        previousbucket.elements.addAll(OriginalBucket.elements);
        hashTable.remove(mergeIndex);
        N--;

        nextToSplit = mergeIndex;
        if (nextToSplit == 0)
            I--;
    }

    public double alpha () { return n / (double) N; }

    public void show () {
        System.out.println("----- START (" + alpha() + ") -----");
        int i = 0;
        for (Bucket bucket: hashTable) {
            System.out.print(i + ": < ");
            for (Integer k : bucket.elements)
                System.out.print(k + " ");
            System.out.println(i == nextToSplit ? ">*" : ">");
            i++;
        }
        System.out.println("----- END -----");
    }
}

