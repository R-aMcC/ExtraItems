package xyz.wyan.extraitems.DataTypes;

import java.util.LinkedHashMap;
import java.util.Map;

public class CappedLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
    private final int maxSize;

    public CappedLinkedHashMap(int maxSize) {
        super(maxSize + 1, 0.75f, false); // +1 because removeEldestEntry checks after put
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }
}