import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

interface MyList<E> extends Collection<E> {
    void add(int index, E element);
    E get(int index);
    E set(int index, E element);
    E remove(int index);
    int indexOf(Object o);
    int lastIndexOf(Object o);
}

public class TwoWayLinkedList<E> implements MyList<E> {
    private Node<E> head, tail;
    private int size;

    public TwoWayLinkedList() {
    }

    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
        if (size == 0) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        }
        size++;
    }

    public void addLast(E e) {
        Node<E> newNode = new Node<>(e);
        if (size == 0) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }
        size++;
    }

    public void add(int index, E e) {
        if (index == 0) {
            addFirst(e);
        } else if (index >= size) {
            addLast(e);
        } else {
            Node<E> current = head;
            for (int i = 1; i < index; i++) {
                current = current.next;
            }
            Node<E> temp = current.next;
            current.next = new Node<>(e);
            (current.next).previous = current;
            (current.next).next = temp;
            (temp.previous) = current.next;
            size++;
        }
    }

    public E removeFirst() {
        if (size == 0) {
            return null;
        } else {
            Node<E> temp = head;
            head = head.next;
            size--;
            if (head == null) {
                tail = null;
            }
            return temp.element;
        }
    }

    public E removeLast() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            Node<E> temp = head;
            head = tail = null;
            size = 0;
            return temp.element;
        } else {
            Node<E> current = head;
            for (int i = 0; i < size - 2; i++) {
                current = current.next;
            }
            Node<E> temp = tail;
            tail = current;
            tail.next = null;
            size--;
            return temp.element;
        }
    }

    public E remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            Node<E> previous = head;
            for (int i = 1; i < index; i++) {
                previous = previous.next;
            }
            Node<E> current = previous.next;
            previous.next = current.next;
            (current.next).previous = previous;
            size--;
            return current.element;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object e) {
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            if (current.element.equals(e)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else if (index == 0) {
            return head.element;
        } else if (index == size - 1) {
            return tail.element;
        } else {
            Node<E> current = head;
            for (int i = 1; i <= index; i++) {
                current = current.next;
            }
            return current.element;
        }
    }

    public int indexOf(Object e) {
        Node<E> current = head;
        for (int i = 0; i < size; i++) {
            if (current.element.equals(e)) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    public int lastIndexOf(Object e) {
        Node<E> current = tail;
        for (int i = size - 1; i >= 0; i--) {
            if (current.element.equals(e)) {
                return i;
            }
            current = current.previous;
        }
        return -1;
    }

    public E set(int index, E e) {
        if (index < 0 || index >= size) {
            return null;
        } else {
            Node<E> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            E temp = current.element;
            current.element = e;
            return temp;
        }
    }

    public void clear() {
        head = tail = null;
        size = 0;
    }

    public ListIterator<E> listIterator() {
        return new LinkedListIterator();
    }

    public ListIterator<E> listIterator(int index) {
        return new LinkedListIterator(index);
    }

    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements ListIterator<E> {
        private Node<E> current;
        private int index = 0;

        public LinkedListIterator() {
            current = head;
        }

        public LinkedListIterator(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            this.index = index;
        }

        public boolean hasNext() {
            return index < size;
        }

        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E e = current.element;
            current = current.next;
            index++;
            return e;
        }

        public boolean hasPrevious() {
            return index > 0;
        }

        public E previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            current = current.previous;
            E e = current.element;
            index--;
            return e;
        }

        public int nextIndex() {
            return index;
        }

        public int previousIndex() {
            return index - 1;
        }

        public void remove() {
            if (index == 0) {
                throw new IllegalStateException();
            }
            Node<E> previousNode = current.previous;
            previousNode.next = current.next;
            if (current.next != null) {
                (current.next).previous = previousNode;
            } else {
                tail = previousNode;
            }
            size--;
            index--;
        }

        public void set(E e) {
            if (index == 0) {
                throw new IllegalStateException();
            }
            current.element = e;
        }

        public void add(E e) {
            Node<E> newNode = new Node<>(e);
            if (current == null) {
                head = tail = newNode;
            } else if (current == head) {
                newNode.next = head;
                head.previous = newNode;
                head = newNode;
            } else if (current == tail) {
                tail.next = newNode;
                newNode.previous = tail;
                tail = newNode;
            } else {
                newNode.next = current;
                newNode.previous = current.previous;
                (current.previous).next = newNode;
                current.previous = newNode;
            }
            size++;
            index++;
        }
    }

    private class Node<E> {
        E element;
        Node<E> next;
        Node<E> previous;

        public Node(E e) {
            element = e;
        }
    }
}