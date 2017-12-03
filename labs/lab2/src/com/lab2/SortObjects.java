package com.lab2;

import java.util.Comparator;

//comparator to sort TouristVoucher objects by price

public class SortObjects implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        TouristVoucher t1 = (TouristVoucher)o1;
        TouristVoucher t2 = (TouristVoucher)o2;
        if(t1.price == t2.price){
            return 0;
        }
        else if(t1.price < t2.price){
            return -1;
        }
        else{
            return 1;
        }
    }

}
