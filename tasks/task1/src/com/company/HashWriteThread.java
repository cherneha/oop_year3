package com.company;

public class HashWriteThread<T> extends Thread{
    private Thread thread;
    private String threadName;
    MyConcurrentHashMap<T> hashMap;
    int key;
    T value;

    public HashWriteThread(){
        thread = new Thread();
    }

    public HashWriteThread(int key, T value, MyConcurrentHashMap hashMap){
        this.key = key;
        this.value = value;
        this.hashMap = hashMap;
    }
    public void run(){
        try {
            hashMap.insert(this.key, this.value);
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }

    }

    public String getThreadName(){
        return threadName;
    }
}
