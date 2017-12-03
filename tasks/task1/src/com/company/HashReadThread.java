package com.company;

public class HashReadThread<T> extends Thread{
    int key;
    MyConcurrentHashMap<T> hashMap;
    public HashReadThread(int key, MyConcurrentHashMap map){
        this.key = key;
        hashMap = map;
    }
    public void run(){
        try {
            System.out.println(hashMap.read(this.key));
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }

    }

}
