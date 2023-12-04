package structures;

import utils.Constants;

class PairNode<K, V> {
    private K key;
    private V value;

    public PairNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}

public class HashMap<K, V> {
    private LinkedList<PairNode<K, V>>[] buckets;
    private int capacity;
    private int size;

    public HashMap(int capacity) {
        this.buckets = (LinkedList<PairNode<K, V>>[]) new LinkedList[capacity];
        this.capacity = capacity;
        this.size = 0;

        for (int i = 0; i < capacity; i++) {
            this.buckets[i] = new LinkedList<PairNode<K, V>>();
        }
    }

    public V get(K key) {
        int index = hash(key, capacity);
        LinkedList<PairNode<K, V>> bucket = buckets[index];

        for (int i = 0; i < bucket.length(); i++) {
            String bucketKey = bucket.get(i).getValue().getKey().toString();
            if (bucketKey.equals(key)) {
                return bucket.get(i).getValue().getValue();
            }
        }

        return null;
    }

    public void put(K key, V value) {
        if ((double) size / capacity >= Constants.LOAD_FACTOR) {
            resize();
        }
        int index = hash(key, capacity);
        LinkedList<PairNode<K, V>> bucket = buckets[index];
        for (int i = 0; i < bucket.length(); i++) {
            String bucketKey = bucket.get(i).getValue().getKey().toString();
            if (bucketKey.equals(key.toString())) {
                bucket.get(i).setValue(new PairNode<K, V>(key, value));
                return;
            }
        }

        bucket.append(new PairNode<K, V>(key, value));
        size++;
    }

    public void remove(K key) {
        int index = hash(key, capacity);
        LinkedList<PairNode<K, V>> bucket = buckets[index];

        for (int i = 0; i < bucket.length(); i++) {
            PairNode<K, V> node = bucket.get(i).getValue();
            K nodeKey = node.getKey();

            if (nodeKey.toString().equals(key.toString())) {
                bucket.remove(i);
            }
        }
    }

    public void display() {
        for (int i = 0; i < capacity; i++) {
            String output = "";
            LinkedList<PairNode<K, V>> bucket = buckets[i];

            for (int j = 0; j < bucket.length(); j++) {
                output += buckets[i].get(j).getValue().getValue() + ", ";
            }

            System.out.println("Index " + i + ": [(" + output + ")]");
        }
    }

    public int size() {
        return size;
    }

    private void resize() {
        int newCapacity = capacity * 2;
        LinkedList<PairNode<K, V>>[] newBuckets = (LinkedList<PairNode<K, V>>[]) new LinkedList[newCapacity];

        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = new LinkedList<PairNode<K, V>>();
        }

        for (int i = 0; i < capacity; i++) {
            LinkedList<PairNode<K, V>> bucket = buckets[i];
            for (int j = 0; j < bucket.length(); j++) {
                PairNode<K, V> node = bucket.get(j).getValue();

                int index = hash(node.getKey(), newCapacity);
                newBuckets[index].append(node);
            }
        }

        capacity = newCapacity;
        buckets = newBuckets;
    }

    private int hash(K input, int capacity) {
        String inputString = input.toString();
        int total = 0;

        for (int i = 0; i < inputString.length(); i++) {
            total = total + inputString.charAt(i);
        }

        return Math.abs(total % capacity);
    }
}