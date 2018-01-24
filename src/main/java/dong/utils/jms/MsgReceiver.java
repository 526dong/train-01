package dong.utils.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author Created by ${xzd} on 2018/1/14.
 * @Description
 */
public class MsgReceiver {
    // ConnectionFactory: use to create JMS connection
    private static ConnectionFactory connectionFactory;

    // Connection: connect message provider and JMS server
    private static Connection connection;

    // Session: a message send or receive thread
    private static Session session;

    // use to sign the message type
    private static Destination destination;

    // MessageConsumer: receiver
    private static MessageConsumer messageConsumer;

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
        messageConsumer = session.createConsumer(destination);

        connection.start();
    }

    /**
     * receive activeMq message
     */
    public static void receiveMessage() throws Exception {
        while (true) {
            TextMessage message = (TextMessage) messageConsumer.receive();
            if (message != null) {
                System.out.println("receive: " + message.getText());
            } else {
                break;
            }
        }
    }

    /**
     * release resource
     */
    public static void release() throws Exception {
        messageConsumer.close();
        session.close();
        connection.close();
    }

    /**
     * main method
     */
    public static void main(String[] args) throws Exception {
        init();
        receiveMessage();
        release();
    }
}
