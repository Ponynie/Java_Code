package datastr;

import java.util.ArrayList;

import lab.CourseGrade;

public class SeparateChainingHashMap implements Map {
    private static class LinkedNode {
        Object key, value;
        LinkedNode next;
        LinkedNode(Object k, Object v, LinkedNode n) {
            key = k; value = v; next = n;
        }
    }
    private int size;
    private LinkedNode[] table;
    public SeparateChainingHashMap(int cap) {
        table = new LinkedNode[cap];
    }
    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }
    public Object get(Object key) {
        LinkedNode node = getNode(key);
        return node == null ? null : node.value;
    }
    public boolean containsKey(Object key) {
        return getNode(key) != null;
    }
    private LinkedNode getNode(Object key) {
        LinkedNode cur = table[h(key)];
        while (cur != null && !cur.key.equals(key)) {
            cur = cur.next;
        }
        return cur;
    }
    private int h(Object x) {
        return Math.abs(x.hashCode()) % table.length;
    }
    public Object put(Object key, Object value) {
        LinkedNode node = getNode(key);
        Object oldValue = null;
        if (node != null) {
            oldValue = node.value;
            node.value = value;
        } else {
            int h = h(key);
            table[h] = new LinkedNode(key, value, table[h]);
            ++size;
        }
        return oldValue;
    }
    public void remove(Object key) {
        int h = h(key);
        if (table[h] == null) return;
        if (table[h].key.equals(key)) {
            table[h] = table[h].next; --size;
        } else {
            LinkedNode prev = table[h];
            while (prev.next != null && !prev.next.key.equals(key)) {
                prev = prev.next;
            }
            if (prev.next != null) {
                prev.next = prev.next.next; --size;
            }
        }
    }
    public String toString() {
        String s = "";
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                s = s + "[" + i + "]";
                LinkedNode node = table[i];
                while (node != null) {
                    s = s + " => [";
                    for (CourseGrade j : (ArrayList<CourseGrade>) node.value) { //ควรเป็น toString ของ node.value นั้นๆ
                        s = s + j.toString() + ", ";
                    }
                    node = node.next;
                    s = s.substring(0, s.length() - 2) + "]";
                }
                s = s + "\n";
            }
            else s = s + "[" + i + "]\n";
        }
        return s;
    }
}