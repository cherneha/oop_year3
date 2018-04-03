package game;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Game {
    private Queue queue;
    private Queue queue2;
    private Queue queue3;
    private Connection connection;
    private Session session;
    private MessageConsumer consumer;
    private MessageProducer producer;
    private MessageProducer producerForStart;
    public static boolean notDecided = true;
    private MessageConsumer consumerForStart;

    public Game(String q1, String q2){
        ActiveMQConnectionFactory mqConnectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616?keepAlive=true");
        try{
            connection = mqConnectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            queue = session.createQueue(q1);
            queue2 = session.createQueue("queue2");
            queue3 = session.createQueue(q2);
            consumer = session.createConsumer(queue);
            while (consumer.receive(50) != null){}
            producer = session.createProducer(queue3);
            producerForStart = session.createProducer(queue2);
            consumerForStart = session.createConsumer(queue2);
        }catch(JMSException e){
            System.out.println(e.getMessage());
        }
    }



    public void sendStart(String symbol){
        if(notDecided) {
            try {
                TextMessage message = session.createTextMessage();
                message.setText(symbol);
                producerForStart.send(message);
                Main.player = symbol;
                notDecided = false;
            } catch (JMSException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void recieveStart(){
        try {
            System.out.println("in recieve start");
            TextMessage message = (TextMessage) consumerForStart.receive();
            System.out.println("recieved");
            String role = message.getText();
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
    public void sendMove(String cellNumber){
        try {
            TextMessage message = session.createTextMessage();
            message.setText(cellNumber);
            producer.send(message);
        }catch (JMSException e){
            System.out.println(e.getMessage());
        }

        }

    public String recieveMove(){
        try {

            TextMessage message = (TextMessage) consumer.receive();
            return message.getText();
        }catch (JMSException e){
            System.out.println(e.getMessage());
        }
        return "-";
    }
    public void endGame(){
        try {
            consumer.close();
            producer.close();
            session.close();
            connection.close();
        }catch (JMSException e){
            System.out.println(e.getMessage());
        }
    }
}
