package com.lab2;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DomParserVoucher {

    File file; // target xml file
    ArrayList <TouristVoucher> allVouchers;  // save the collection

    public DomParserVoucher(String file){
        this.file = new File(file);
        TouristVoucher voucher = new TouristVoucher();
        allVouchers = new ArrayList<TouristVoucher>();
    }

    // all parsing job

    public void parse(){
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);

            NodeList nodeList = doc.getElementsByTagName("tourist_voucher");
            for(int i = 0; i < nodeList.getLength(); i++){

                Element currentElement = (Element)nodeList.item(i);
                if(currentElement.getNodeType() == Node.ELEMENT_NODE){
                    TouristVoucher voucher = new TouristVoucher();
                    voucher.setID(currentElement.getAttribute("id"));
                    voucher.setType(currentElement.getElementsByTagName("type").item(0).getTextContent());
                    voucher.setCountry(currentElement.getElementsByTagName("country").item(0).getTextContent());
                    voucher.setDaysNights(currentElement.getElementsByTagName("days_nights").item(0).getTextContent());
                    voucher.setTransport(currentElement.getElementsByTagName("transport").item(0).getTextContent());
                    Element hotelChar = (Element)currentElement.getElementsByTagName("hotel_characteristics").item(0);

                    HotelCharacteristics hotelCharacteristics = new HotelCharacteristics();
                    hotelCharacteristics.setStars(hotelChar.getElementsByTagName("stars").item(0).getTextContent());
                    hotelCharacteristics.setBroad(hotelChar.getElementsByTagName("ration").item(0).getTextContent());
                    hotelCharacteristics.setSuit(hotelChar.getElementsByTagName("suit_type").item(0).getTextContent());
                    hotelCharacteristics.setTV(hotelChar.getElementsByTagName("TV").item(0).getTextContent());
                    hotelCharacteristics.setConditioneer(hotelChar.getElementsByTagName("conditioneer").item(0).getTextContent());

                    voucher.hotelCharacteristics = hotelCharacteristics;

                    voucher.setPrice(currentElement.getElementsByTagName("price").item(0).getTextContent());

                    allVouchers.add(voucher);
                }
            }



        }catch (ParserConfigurationException | IOException | SAXException e){
            System.out.println(e.getMessage());
        }

    }
    ArrayList <TouristVoucher> getAllVouchers(){

        // use this function to get the collection of all TouristVoucher objects in target xml file
        // after calling parse() method

        return this.allVouchers;
    }

    public static void main(String args[]){
        DomParserVoucher parserVoucher = new DomParserVoucher("/home/stasey/IdeaProjects/3 year/lab2/src/com/lab2/tour.xml");
        parserVoucher.parse();
    }
}
