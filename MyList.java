import java.util.Collection;

interface MyList<E> extends Collection<E> {
    void add(int index, E e);
    E get(int index);
    int indexOf(Object e);
    int lastIndexOf(Object e);
    E remove(int index);
    E set(int index, E e);
}