package com.lab2;

enum BoardTypes{
    AI, BB, HB, RO
}

enum SuitType{
    Double, Single, Triple, Apartment
}

class HotelCharacteristics{
    int stars;
    BoardTypes board;
    SuitType suit;
    int TV;
    boolean conditioneer;


    void setStars(String stars){
        this.stars = Integer.parseInt(stars);
    }

    void setSuit(String suit){
        try {
            switch (suit) {
                case "Double": {
                    this.suit = SuitType.Double;
                    break;
                }
                case "Single": {
                    this.suit = SuitType.Single;
                    break;
                }
                case "Triple": {
                    this.suit = SuitType.Triple;
                    break;
                }
                case "Apartment": {
                    this.suit = SuitType.Apartment;
                    break;
                }
                default:{
                    throw new VoucherExeption();
                }
            }
        }catch (VoucherExeption e){
            e.getMessage();
        }
    }

    void setTV(String tv){
        this.TV = Integer.parseInt(tv);
    }

    void setConditioneer(String conditioneer){
        this.conditioneer = Boolean.parseBoolean(conditioneer);
    }

    void setBroad(String broad){
        try {
            switch (broad) {
                case "AI": {
                    this.board = BoardTypes.AI;
                    break;
                }
                case "BB": {
                    this.board = BoardTypes.BB;
                    break;
                }
                case "HB": {
                    this.board = BoardTypes.HB;
                    break;
                }
                case "RO": {
                    this.board = BoardTypes.RO;
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
}



