package com.company;

public class MyConcurrentHashMap<T> {

    Entry<T>[] table;
    Segment[] segments;
    int initialCapacity;
    double loadFactor;
    int concurrencyLevel;
    volatile int elements;
    public static int MAX_SEGMENTS = 32;

    public MyConcurrentHashMap(int initialCapacity, double loadFactor, int concurrencyLevel){
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
        elements = 0;
        segments = new Segment[initialCapacity];
        for(int i = 0; i < segments.length; i++){
            segments[i] = new Segment();
        }
        this.concurrencyLevel = concurrencyLevel;
        if(concurrencyLevel > this.MAX_SEGMENTS){
            this.concurrencyLevel = this.MAX_SEGMENTS;
        }
        if(this.initialCapacity < 0){
            initialCapacity = 32;
        }
        table = new Entry[initialCapacity];

    }

    private int countHash(int key){
        int h = key;
        h += (h << 15) ^ 0xffffcd7d;
        h ^= (h >>> 10);
        h += (h << 3);
        h ^= (h >>> 6);
        h += (h << 2) + (h << 14);
        return  h ^ (h >>> 16);
    }

    public void insert(int key, T value) throws InterruptedException{
        Entry<T> toInsert = new Entry<T>(value, key, countHash(key));
        int hash = toInsert.getHash();

        int segmentNum = hash & 0x1F;

        /////////////////////////////////////////

        synchronized (this) {
            while(segments[segmentNum].writing){
            this.wait();
        }

            segments[segmentNum].writing = true;

            int index = hash & table.length - 1;
            //System.out.println(index);
            Entry<T> firstInCell = table[index];

            if (firstInCell == null) {
                firstInCell = new Entry<T>();
                table[index] = toInsert;
            }
            boolean update = false;
            while (firstInCell.Next != null) {
                if (firstInCell.getKey() == toInsert.getKey()) {
                    firstInCell.updateValue(toInsert.getValue());
                    update = true;
                    break;
                }
                firstInCell = firstInCell.Next;
            }

            if(!update) {
                firstInCell.Next = toInsert;
            }
            elements++;
            segments[segmentNum].writing = false;
            this.notifyAll();
        }

        /////////////////////////////////////////

    }

    public T read(int key) throws IllegalArgumentException, InterruptedException{
        int hash = countHash(key);
        //System.out.println(hash);
        while (segments[hash & 0x1F].writing){
            wait();
        }
        Entry<T> temp = table[hash & table.length - 1];

        if(temp == null) return null;
        do{
            if((temp.getKey() == key) && (temp.getHash() == hash)){
                return temp.getValue();
            }
            temp = temp.Next;
        }while(temp != null);
        throw new IllegalArgumentException("No such element!");

    }

}
