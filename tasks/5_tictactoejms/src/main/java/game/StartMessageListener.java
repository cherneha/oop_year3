package game;

import javax.jms.*;

public class StartMessageListener implements MessageListener {
    public void onMessage(Message message){
        Game.notDecided = false;
        TextMessage textMessage = (TextMessage) message;
        try {
            String role = textMessage.getText();
            if(role.equals("x")){
                Main.player = "o";
            }
            else {
                Main.player = "x";
            }
        }catch (JMSException e){
            System.out.println(e.getMessage());
        }
    }
}
