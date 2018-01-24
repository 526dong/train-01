package dong.utils.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author Created by ${xzd} on 2018/1/14.
 * @Description
 */
public class MsgSender {

    // ConnectionFactory: use to create JMS connection
    private static ConnectionFactory connectionFactory;

    // Connection: connect message provider and JMS server
    private static Connection connection;

    // Session: a message send or receive thread
    private static Session session;

    // Destination: use to sign the message type
    private static Destination destination;

    // MessageProducer：sender
    private static MessageProducer messageProducer;

    /**
     * init the JMS object
     */
    public static void init() throws Exception {
        // use ActiveMQ to to create connection factory.
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");

        // get the connection from connection factory
        connection = connectionFactory.createConnection();
        session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue("myQueue");
        messageProducer = session.createProducer(destination);
        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        connection.start();
    }

    /**
     * send activeMq message
     */
    public static void sendMessage() throws Exception {
        for (int i = 1; i <= 5; i++) {
            TextMessage message = session.createTextMessage("ActiveMq message " + i);
            System.out.println("send：" + "ActiveMq message " + i);
            messageProducer.send(message);
        }
        session.commit();
    }

    /**
     * release resource
     */
    public static void release() throws Exception {
        messageProducer.close();
        session.close();
        connection.close();
    }

    /**
     * main method
     */
    public static void main(String[] args) throws Exception {
        init();
        sendMessage();
        release();
    }
}