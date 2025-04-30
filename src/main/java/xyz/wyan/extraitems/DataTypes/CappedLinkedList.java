package xyz.wyan.extraitems.DataTypes;

import java.util.LinkedList;

public class CappedLinkedList<E> extends LinkedList<E> {
    private final int maxSize;

    public CappedLinkedList(int maxSize) {
        super();
        this.maxSize = maxSize;
    }

    @Override
    public boolean add(E e) {
        super.add(e);
        checkSize();
        return true;
    }

    @Override
    public void addLast(E e) {
        super.addLast(e);
        checkSize();
    }

    private void checkSize() {
        while (size() > maxSize) {
            removeFirst();
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size(); i++) {
            sb.append(get(i));
            if (i < size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
