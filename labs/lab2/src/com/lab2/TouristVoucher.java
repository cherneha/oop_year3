package com.lab2;


class VoucherExeption extends Exception{
    VoucherExeption(){
        super("Wrong tag");
    }


}

// class to save number of days and nights in a trip
class DaysNights{
    DaysNights(int days, int nights){
        this.days = days;
        this.nights = nights;
    }
    int days;
    int nights;
}

// available tour types
enum TourType{
    Weekend, Sightseeing, Resort, Pilgrimage, Skiing
}

// available transport
enum Transportation{
    Avia, Bus, Train, Liner
}


// class to manipulate with tourist vouchers
// used with specific xml file structure, defined in tourXSD.xml file by XML Schema

public class TouristVoucher {
    TourType type;
    String ID;
    String country;
    DaysNights daysNights;
    Transportation transports;
    HotelCharacteristics hotelCharacteristics;
    double price;

    void setID(String id){
        this.ID = id;
    }
    void setCountry(String country){
        this.country = country;
    }

    void setDaysNights(String daysNights){
        String splited[] = daysNights.split("/");
        DaysNights dn = new DaysNights(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]));
        this.daysNights = dn;
    }

    void setHotelCharacteristics(){
        hotelCharacteristics = new HotelCharacteristics();
    }


    void setPrice(String price){
        this.price = Double.parseDouble(price);
    }

    void setTransport(String transport) {
        try {
            switch (transport) {
                case "avia": {
                    this.transports = Transportation.Avia;
                    break;
                }
                case "bus": {
                    this.transports = Transportation.Bus;
                    break;
                }
                case "train": {
                    this.transports = Transportation.Train;
                    break;
                }
                case "liner": {
                    this.transports = Transportation.Liner;
                    break;
                }
                default: {
                    throw new VoucherExeption();
                }
            }
        }catch (VoucherExeption e){
            System.out.println(e.getMessage());
        }
    }

    void setType(String type){
        try {
            switch (type) {
                case "Weekend": {
                    this.type = TourType.Weekend;
                    break;
                }
                case "Sightseeing": {
                    this.type = TourType.Sightseeing;
                    break;
                }
                case "Resort": {
                    this.type = TourType.Resort;
                    break;
                }
                case "Pilgrimage": {
                    this.type = TourType.Pilgrimage;
                    break;
                }
                case "Skiing": {
                    this.type = TourType.Skiing;
                    break;
                }
                default:
                    throw new VoucherExeption();
            }
        }catch (VoucherExeption e){
            System.out.println(e.getMessage());
        }
    }
}
