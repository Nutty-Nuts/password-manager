package structures;

class KeyValuePair {
    private String key;
    private String value;

    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}

public class HashMap {
    private static final double LOAD_FACTOR = 0.75;

    private LinkedList<KeyValuePair>[] buckets;
    private int size;
    private int capacity;

    public HashMap(int capacity) {
        this.capacity = capacity;
        this.size = 0;

        buckets = (LinkedList<KeyValuePair>[]) new LinkedList[capacity];

        for (int i = 0; i < capacity; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    private void resize() {
        int newCapacity = capacity * 2;
        LinkedList<KeyValuePair>[] newBuckets = (LinkedList<KeyValuePair>[]) new LinkedList[newCapacity];

        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = new LinkedList<>();
        }

        for (int i = 0; i < capacity; i++) {
            LinkedList<KeyValuePair> bucket = buckets[i];

            for (int j = 0; j < bucket.length(); j++) {
                int newIndex = hash(bucket.get(j).value.getKey(), newCapacity);
                newBuckets[newIndex].append(bucket.get(j).value);
            }
        }

        capacity = newCapacity;
        buckets = newBuckets;
    }

    public int hash(String key) {
        int total = 0;
        for (int i = 0; i < key.length(); i++) {
            total = total + key.charAt(i);
        }

        return Math.abs(total % capacity - 1);
    }

    public int hash(String key, int capacity) {
        int total = 0;
        for (int i = 0; i < key.length(); i++) {
            total = total + key.charAt(i);
        }

        return Math.abs(total % capacity - 1);
    }

    public void put(String key, String value) {
        if ((double) size / capacity >= LOAD_FACTOR) {
            resize();
        }

        int index = hash(key);
        LinkedList<KeyValuePair> bucket = buckets[index];

        for (int i = 0; i < bucket.length(); i++) {
            String bucketKey = bucket.get(i).value.getKey();
            if (bucketKey.equals(key)) {
                bucket.get(i).value = new KeyValuePair(key, value);
                return;
            }
        }

        bucket.append(new KeyValuePair(key, value));
        size++;
    }

    public String get(String key) {
        int index = hash(key);
        LinkedList<KeyValuePair> bucket = buckets[index];

        for (int i = 0; i < bucket.length(); i++) {
            String bucketKey = bucket.get(i).value.getKey();
            if (bucketKey.equals(key)) {
                return bucket.get(i).value.getValue();
            }
        }

        return null;
    }

    public void remove(String key) {
        int index = hash(key);

        LinkedList<KeyValuePair> bucket = buckets[index];

        for (int i = 0; i < bucket.length(); i++) {
            String bucketKey = bucket.get(i).value.getKey();
            System.out.println(bucketKey + " " + i);
            if (bucketKey.equals(key)) {
                bucket.remove(i);
            }
        }
    }

    public void display() {
        for (int i = 0; i < capacity; i++) {
            String output = "";
            LinkedList<KeyValuePair> bucket = buckets[i];

            for (int j = 0; j < bucket.length(); j++) {
                output += buckets[i].get(j).value.getValue() + ", ";
            }

            System.out.println("Index " + i + ": [(" + output + ")]");
        }
    }

    public int size() {
        return size;
    }
}
