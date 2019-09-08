import java.util.NoSuchElementException;

/**
 * Double-linked node implementation of ICache.
 * 
 * @author  Benjamin Warner
 * 
 * @param <T> store data of type T
 */
public class Cache<T> implements ICache<T> {
    private DLLNode<T> head, tail;
    private int capacity;
    private int size = 0;
    private int hits = 0;
    private int accesses = 0;

    /**
     * Create an empty cache with default capacity 100
     */
    public Cache() {
        this.head = null;
        this.tail = null;
        this.capacity = 100;
    }

    /**
     * Create an empty cache with a user specified capacity
     */
    public Cache(int capacity) {
        this.head = null;
        this.tail = null;
        this.capacity = capacity;
    }

    public T get(T target) {
        ++accesses;
        DLLNode<T> current = head;

        int i;
        for (i = 0; i < size; ++i) {
            if (current.getElement().equals(target))
                break;
            current = current.getNext();
        }

        if (i != size) {
            moveToFront(current);
            ++hits;
            return current.getElement();
        } else {
            add(target);
            return null;
        }
    }

    public void clear() {
        // garbage collector should clear out the elements
        head = tail = null;
        size = 0;
    }

    public void add(T data) {
        DLLNode<T> node = new DLLNode<T>(data);

        if (isEmpty()) {
            head = tail = node;
            ++size;
        } else {
            node.setNext(head);
            head.setPrevious(node);
            head = node;
            if (isFull()) {
                tail.getPrevious().setNext(null);
                tail = tail.getPrevious();
            } else {
                ++size;
            }
        }
    }

    public void removeLast() {
        if (isEmpty())
            throw new IllegalStateException();
        
        if (size == 1) {
            head = tail = null;
        } else {
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
        }
        --size;
    }

    public void remove(T target) {
        if (isEmpty())
            throw new NoSuchElementException();

        boolean found = false;
        DLLNode<T> current = head;

        while (current != null && !found) {
            if (current.getElement().equals(target))
                found = true;
            else
                current = current.getNext();
        }

        if (!found)
            throw new NoSuchElementException();

        if (size == 1)
            head = tail = null;
        else if (current == head) {
            head.getNext().setPrevious(null);
            head = current.getNext();
        } else if (current == tail) {
            tail.getPrevious().setNext(null);
            tail = current.getPrevious();
        } else {
            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());
        }

        --size;
    }

    public void write(T data) {
        DLLNode<T> current = head;
        int i;
        for (i = 0; i < size; ++i) {
            if (current.getElement().equals(data))
                break;
            current = current.getNext();
        }

        if (i == size)
            throw new NoSuchElementException();

        moveToFront(current);
    }

    private void moveToFront(DLLNode<T> node) {
        if (node == head)
            return;
        else if (node == tail) {
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
        } else {
            node.getPrevious().setNext(node.getNext());
            node.getNext().setPrevious(node.getPrevious());
        }

        node.setPrevious(null);
        node.setNext(head);
        head.setPrevious(node);
        head = node;
    }

    public double getHitRate() {
        if (accesses == 0)
            return 0.0;
        return (double)hits / (double)accesses;
    }

    public double getMissRate() {
        return 1.0 - getHitRate();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public String toString() {
        String s = "[";

        DLLNode<T> current = head;
        for (int i = 0; i < size - 1; ++i) {
            s += String.format("%s, ", current.getElement().toString());
            current = current.getNext(); 
        }

        s += tail.getElement();
        s += "]";

        return s;
    }
}