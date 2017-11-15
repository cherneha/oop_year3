package com.company;

public class Main {

    public static void main(String[] args)throws InterruptedException {
    	int concurrencyWrite = 6;
	    MyConcurrentHashMap<String> testMap = new MyConcurrentHashMap(1000, 0.75, 32);

	    Thread[] threadsWrite = new HashWriteThread[concurrencyWrite];
	    int keys[] = {12, -45, 0, 2343145, 111, 111};
	    String values[] = {"first", "second", "third", "fourth", "fifth", "sixth"};

	    for(int i = 0; i < concurrencyWrite; i++){
	    	threadsWrite[i] = new HashWriteThread<String>(keys[i], values[i], testMap);
		}
		for(int i = 0; i < concurrencyWrite; i++){
	    	threadsWrite[i].start();
		}
		for(int i = 0; i < concurrencyWrite; i++){
			threadsWrite[i].join();
		}


		int concurrencyRead = 3;
		Thread[] theadsRead = new HashReadThread[concurrencyRead];
		int keysRead[] = {-45, -45, 111};
		for(int i = 0; i < concurrencyRead; i++){
			theadsRead[i] = new HashReadThread<String>(keysRead[i], testMap);
		}
		for(int i = 0; i < concurrencyRead; i++){
			theadsRead[i].start();
		}
		for(int i = 0; i < concurrencyRead; i++){
			theadsRead[i].join();
		}

    }
}
