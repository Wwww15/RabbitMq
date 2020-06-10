import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 */
public class MQConsumer {

    public static void main(String[] args) {
        try {
            consumeMsg("rabbitmq.first");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
    /**
     * 消费消息构建
     * @param queue
     * @throws IOException
     * @throws TimeoutException
     */
    public static void consumeMsg(String queue) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接地址
        connectionFactory.setHost("192.168.239.128");
        //设置端口(默认配置)
        connectionFactory.setPort(5672);
        //设置用户(默认配置)
        connectionFactory.setUsername("guest");
        //设置密码(默认配置)
        connectionFactory.setPassword("guest");
        //设置连接虚拟主机(默认配置)
        connectionFactory.setVirtualHost("/");
        //创建连接
        Connection connection = connectionFactory.newConnection();
        //创建通道(大部)
        Channel channel = connection.createChannel();
        //创建回调
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String msg = new String(message.getBody(), "utf-8");
            System.out.println(msg);
        };
        //声明队列(如果队列不存在，会主动创建)
        channel.queueDeclare(queue,true,false,false,null);
        //创建生产者
        channel.basicConsume(queue,true,deliverCallback,consumerTag -> {});

        //关闭channel,connection
        /*channel.close();
        connection.close();*/
    }
}
