package com.lab2;

import javax.xml.parsers.SAXParser;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String args[]){

        // using SAX parser
        SAXParserVoucher myParser = new SAXParserVoucher("/home/stasey/IdeaProjects/3 year/lab2/src/com/lab2/tour.xml");
        ArrayList<TouristVoucher> allVouchers = myParser.parse();
        // sorting with custom comparator
        SortObjects sorter = new SortObjects();
        Collections.sort(allVouchers, sorter);

    }
}
