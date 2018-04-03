package game;

import org.apache.activemq.broker.BrokerService;

import javax.jms.*;
import java.util.Scanner;

public class Main {
    public static String player = "x";
    public static String opponent = "o";

    public static void main(String args[]) {

        Scanner reader = new Scanner(System.in);
        GameField gameField = new GameField();
        gameField.drawField();
        int i = 0;
        String n;
        System.out.println(args[0]);
        if(args[0].equals("x")) {
            System.out.println("you're playing for x");
            player = "x";
            opponent = "o";
            Game theGame = new Game("queue1", "queue3");
            while (i < 4){
                n = theGame.recieveMove();
                gameField.editField(new Integer(n), opponent);
                gameField.drawField();
                if(i > 1){
                    if(gameField.winner()){
                        theGame.endGame();
                        return;
                    }
                }
                System.out.printf("Your move, %s: \n", player);
                n = reader.nextLine();
                theGame.sendMove(n);
                if(i > 1){
                    if(gameField.winner()){
                        theGame.endGame();
                        return;
                    }
                }
                gameField.editField(new Integer(n), player);
                gameField.drawField();
                i++;
            }
            n = theGame.recieveMove();
            gameField.editField(new Integer(n), opponent);
            gameField.drawField();
            if(i > 1){
                if(gameField.winner()){
                    theGame.endGame();
                    return;
                }
            }
            theGame.endGame();
        }

        else if(args[0].equals("o")){
            System.out.println("you're playing for o");
            player = "o";
            opponent = "x";
            Game theGame = new Game("queue3", "queue1");
            while (i < 5){
                System.out.printf("Your move, %s: \n", player);
                n = reader.nextLine();
                gameField.editField(new Integer(n), player);
                gameField.drawField();
                theGame.sendMove(n);
                if(i > 1){
                    if(gameField.winner()){
                        theGame.endGame();
                        return;

                    }
                }
                n = theGame.recieveMove();
                gameField.editField(new Integer(n), opponent);
                gameField.drawField();
                if(i > 1){
                    if(gameField.winner()){
                        theGame.endGame();
                        return;
                    }
                }
                i++;
            }
            theGame.endGame();
        }
        reader.close();
    }

    //public void

}
