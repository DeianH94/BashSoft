package main.bg.softuni.dataStructures;

import main.bg.softuni.contracts.SimpleOrderedBag;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class SimpleSortedList<E extends Comparable<E>> implements SimpleOrderedBag<E> {
    private static final int DEFAULT_SIZE = 16;

    private E[] innerCollection;
    private int size;
    private Comparator<E> comparator;

    public SimpleSortedList(Class<E> type, Comparator<E> comparator, int capacity) {
        this.initializeInnerCollection(type, capacity);
        this.comparator = comparator;
    }

    public SimpleSortedList(Class<E> type, int capacity) {
        this(type, Comparable::compareTo, capacity);
    }

    public SimpleSortedList(Class<E> type, Comparator<E> comparator) {
        this(type, comparator, DEFAULT_SIZE);
    }

    public SimpleSortedList(Class<E> type) {
        this(type, Comparable::compareTo, DEFAULT_SIZE);
    }

    @Override
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }

        if (this.size() >= innerCollection.length) {
            this.resize();
        }

        this.innerCollection[this.size()] = element;
        this.size++;
        //Arrays.sort(this.innerCollection, 0, this.size(), this.comparator);
        this.BubbleSort(this.innerCollection, this.comparator);
    }

    @Override
    public void addAll(Collection<E> elements) {
        if (elements.contains(null)) {
            throw new IllegalArgumentException();
        }

        if (this.size() + elements.size() >= this.innerCollection.length) {
            this.multiResize(elements);
        }

        for (E element : elements) {
            this.innerCollection[this.size()] = element;
            this.size++;
        }

        Arrays.sort(this.innerCollection, 0, this.size(), this.comparator);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public String joinWith(String joiner) {
        if (joiner == null){
            throw new IllegalArgumentException();
        }

        StringBuilder output = new StringBuilder();

        for (E e : this) {
            output.append(e);
            output.append(joiner);
        }

        output.setLength(output.length() - joiner.length());
        return output.toString();
    }

    @Override
    public boolean remove(E element) {
        if (element == null){
            throw new IllegalArgumentException();
        }

        boolean hasBeenRemoved = false;
        int indexOfRemovedElement = 0;

        for (int i = 0; i < this.size(); i++) {
            if (this.innerCollection[i].compareTo(element) == 0){
                indexOfRemovedElement = i;
                this.innerCollection[i] = null;
                hasBeenRemoved = true;
                break;
            }
        }

        if (hasBeenRemoved){
            for (int i = indexOfRemovedElement; i < this.size() - 1; i++) {
                this.innerCollection[i] = this.innerCollection[i + 1];
            }
            this.innerCollection[this.size() - 1] = null;
            this.size--;
        }
        return hasBeenRemoved;
    }

    @Override
    public int capacity() {
        return this.innerCollection.length;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < size();
            }

            @Override
            public E next() {
                return innerCollection[this.index++];
            }
        };
    }

    private void multiResize(Collection<E> elements) {
        int newSize = this.innerCollection.length * 2;
        while (this.size() + elements.size() >= newSize) {
            newSize *= 2;
        }

        this.innerCollection = Arrays.copyOf(
                this.innerCollection,
                newSize);

    }

    private void resize() {
        this.innerCollection = Arrays.copyOf(
                this.innerCollection,
                this.innerCollection.length * 2);
    }

    @SuppressWarnings("unchecked")
    private void initializeInnerCollection(Class<E> type, int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity cannot be negative!");
        }

        this.innerCollection = (E[]) Array.newInstance(type, capacity);
    }

    private void BubbleSort(E[] arr, Comparator<E> comparer1)
    {
        E temp;

        for (int write = 0; write < this.size(); write++)
        {
            for (int sort = 0; sort < this.size() - 1; sort++)
            {
                if (comparer1.compare(arr[sort], arr[sort + 1]) > 0)
                {
                    temp = arr[sort + 1];
                    arr[sort + 1] = arr[sort];
                    arr[sort] = temp;
                }
            }
        }
    }
}