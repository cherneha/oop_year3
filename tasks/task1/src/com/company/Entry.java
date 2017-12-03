package com.company;

import java.util.Map;

public class Entry<T> {
    private T value;
    private int key;
    private int hash;
    Entry <T> Next;

    public Entry(){
        value = null;
        Next = null;
        key = 0;
        hash = 0;
    }


    public Entry(T value, int key, int code){
        this.value = value;
        this.key = key;
        this.hash = code;
        Next = null;
    }

    public int getHash(){
        return hash;
    }

    public T getValue(){
        return value;
    }
    public int getKey(){
        return key;
    }
    public void updateValue(T value){
        this.value = value;
    }

}
