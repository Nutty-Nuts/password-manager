package structures;

class Node<T> {
    public T value;
    Node<T> next;
    Node<T> prev;

    public Node(T value) {
        this.value = value;
        this.next = null;
        this.prev = null;
    }
}

public class LinkedList<T> {
    private int length;
    private Node<T> head;
    private Node<T> tail;

    public LinkedList() {
        this.length = 0;
        this.head = null;
        this.tail = null;
    }

    public void append(T value) {
        Node<T> node = new Node<T>(value);
        this.length++;
        if (this.tail == null) {
            this.head = node;
            this.tail = node;
            return;
        }

        node.prev = tail;
        this.tail.next = node;
        this.tail = node;
    }

    public void prepend(T value) {
        Node<T> node = new Node<T>(value);
        this.length++;
        if (this.head == null) {
            this.head = node;
            this.tail = node;
            return;
        }

        node.next = this.head;
        this.head.prev = node;
        this.head = node;
    }

    public void insert(int index, T value) {
        if (index > this.length()) {
            System.out.println("Index error");
            return;
        }
        if (index == 0) {
            this.prepend(value);
            return;
        }
        if (index == this.length()) {
            this.append(value);
            return;
        }

        this.length++;

        Node<T> node = new Node<T>(value);
        Node<T> current = this.get(index);

        node.next = current;
        node.prev = current.prev;
        current.prev.next = node;
        current.prev = node;
    }

    public void remove(int index) {
        this.length = Math.max(0, this.length - 1);

        if (this.head == this.tail) {
            this.head = null;
            return;
        }
        if (index == 0) {
            this.head = this.head.next;
            this.head.prev = null;
            return;
        }
        if (index == this.length() - 1) {
            this.tail = this.tail.prev;
            this.tail.next = null;
            return;
        }
        if (this.length == 0) {
            this.head = null;
            this.tail = null;
        }
        if (this.head == null) {
            return;
        }

        Node<T> current = this.get(index);
        current.next.prev = current.prev;
        current.prev.next = current.next;
    }

    public Node<T> get(int index) {
        if (index == this.length - 1) {
            return this.tail;
        }

        Node<T> current = this.head;
        for (int i = 0; i < index; ++i) {
            current = current.next;
        }

        return current;
    }

    public int length() {
        return this.length;
    }
}
