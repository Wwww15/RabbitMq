import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 *
 * @Tag 消息确认
 */
public class MQConsumer {

    public static void main(String[] args) {

        try {
            consumerMsg("ack.durable");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void consumerMsg(String queue) throws IOException, TimeoutException {
        //获取连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接地址(其他信息默认)
        connectionFactory.setHost("192.168.239.128");
        //获取连接
        Connection connection = connectionFactory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明队列(第一次创建持久队列的时候声明，临时队列每次声明)
        channel.queueDeclare(queue,true,false,false,null);
        //创建消费者
        channel.basicConsume(queue,false,( consumerTag, message)->{
            System.out.println(message);
        },(consumerTag)->{
            System.out.println(consumerTag);
        });
        //关闭连接
       /* channel.close();
        connection.close();*/
    }
}
