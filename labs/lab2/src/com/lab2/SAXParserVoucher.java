package com.lab2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SAXParserVoucher extends DefaultHandler{
    String currentElement;
    TouristVoucher voucher;
    String file;  // target xml file
    ArrayList <TouristVoucher> allVouchers = new ArrayList<>();  // save the collection
    HotelCharacteristics characteristics;

    public SAXParserVoucher(String filename){
        this.file = filename;
    }

    @Override
    public void startDocument(){
        System.out.println("Document started...");

    }

    @Override
    // save tag name
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes) throws SAXException {
        currentElement = qName;
    }

    // use info according to current tag
    public void characters (char[] ch,
                            int start,
                            int length) throws SAXException{
        if(currentElement.equals("all_tourist_vouchers")){

        }
        else if(currentElement.equals("tourist_voucher")){
            voucher = new TouristVoucher();
        }
        else if (currentElement.equals("type")) {
            voucher.setType(String .valueOf(ch, start, length));
        }
        else if(currentElement.equals("country")){
            voucher.setCountry(String.valueOf(ch, start, length));
        }
        else if(currentElement.equals("price")){
            voucher.setPrice(String .valueOf(ch, start, length));
        }
        else if(currentElement.equals("days_nights")){
            voucher.setDaysNights(String .valueOf(ch, start, length));
        }
        else if(currentElement.equals("transport")){
            voucher.setTransport(String .valueOf(ch, start, length));
        }
        else if(currentElement.equals("hotel_characteristics")){
            voucher.setHotelCharacteristics();
        }
        else if(currentElement.equals("stars")){
            voucher.hotelCharacteristics.setStars(String .valueOf(ch, start, length));
        }
        else if(currentElement.equals("ration")){
            voucher.hotelCharacteristics.setBroad(String .valueOf(ch, start, length));
        }
        else if(currentElement.equals("suit_type")){
            voucher.hotelCharacteristics.setSuit(String .valueOf(ch, start, length));
        }
        else if(currentElement.equals("TV")){
            voucher.hotelCharacteristics.setTV(String .valueOf(ch, start, length));
        }
        else if(currentElement.equals("conditioneer")){
            voucher.hotelCharacteristics.setConditioneer(String .valueOf(ch, start, length));
        }
    }

    @Override
    // after element fully parsed, save results
    public void endElement(String uri,
                           String localName,
                           String qName) throws SAXException{

        if(qName.equals("tourist_voucher")){
            allVouchers.add(voucher);
        }
        else if(qName.equals("all_tourist_vouchers")){

        }
        this.currentElement = "";

    }

    @Override
    public void endDocument() throws SAXException{
        System.out.println("Document ended.");
    }


    public ArrayList<TouristVoucher> parse(){

        // use this function to get the collection of all TouristVoucher objects in target xml file

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser parser;
        try {
            parser = saxParserFactory.newSAXParser();
            parser.parse(file, this);
        }catch (ParserConfigurationException | SAXException | IOException e){
            System.out.println(e.getMessage());
        }

        return allVouchers;
    }
}
