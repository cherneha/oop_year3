package com.lab2;

import javax.xml.stream.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class StAXParser {

    File file;
    ArrayList<TouristVoucher> allVouchers;

    public StAXParser(String file){
        this.file = new File(file);
        TouristVoucher voucher = new TouristVoucher();
        allVouchers = new ArrayList<TouristVoucher>();
    }

    public void parse() {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        TouristVoucher voucher = new TouristVoucher();
        HotelCharacteristics hotelCharacteristics = new HotelCharacteristics();
        String currentTag = " ";
        allVouchers = new ArrayList<>();

        try {
            XMLStreamReader eventReader = factory.createXMLStreamReader(new FileReader(file));
            while(eventReader.hasNext()){
                //XMLEvent event = eventReader.;

                int j = eventReader.next();

                switch(j) {
                    case XMLStreamConstants.START_ELEMENT: {
                        String event = eventReader.getLocalName();

                        switch (event) {
                            case "all_tourist_vouchers": {
                                System.out.println("Root element started...");
                                break;
                            }
                            case "tourist_voucher": {
                                voucher = new TouristVoucher();
                                break;
                            }
                            case "hotel_characteristics": {
                                hotelCharacteristics = new HotelCharacteristics();
                                break;
                            }
                            default: {
                                currentTag = event;
                            }
                        }
                    }
                    case XMLStreamConstants.CHARACTERS:{
                        System.out.println(currentTag);
                        switch (currentTag) {
                            case "type": {
                                voucher.setType(eventReader.getText());
                                break;
                            }
                            case "country": {
                                voucher.setCountry(eventReader.getText());
                                break;
                            }
                            case "days_nights": {
                                voucher.setDaysNights(eventReader.getText());
                                break;
                            }
                            case "transport": {
                                voucher.setTransport(eventReader.getText());
                                break;
                            }
                            case "stars": {
                                hotelCharacteristics.setStars(eventReader.getText());
                                break;
                            }
                            case "ration": {
                                hotelCharacteristics.setBroad(eventReader.getText());
                                break;
                            }
                            case "suit_type": {
                                hotelCharacteristics.setSuit(eventReader.getText());
                                break;
                            }
                            case "TV": {
                                hotelCharacteristics.setSuit(eventReader.getText());
                                break;
                            }
                            case "conditioneer": {
                                hotelCharacteristics.setConditioneer(eventReader.getText());
                                break;
                            }
                            case "price": {
                                voucher.setPrice(eventReader.getText());
                                break;
                            }
                            default:{
                                System.out.println("---");
                                break;
                            }
                        }
                        break;
                    }

                    case XMLStreamConstants.END_ELEMENT: {
                        String event = eventReader.getLocalName();
                        //EndElement endElement = event.asEndElement();
                        //String eventString = endElement.getName().getLocalPart();
                        if (event.equals("hotel_characteristics")){
                            voucher.hotelCharacteristics = hotelCharacteristics;
                        }
                        if (event.equals("tourist_voucher")){
                            this.allVouchers.add(voucher);
                        }
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e){
            System.out.println(e.getMessage());
        }
    }

}
