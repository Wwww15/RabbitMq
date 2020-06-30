import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者1
 *
 * @Tag 扇出交换 fanout
 */
public class MQConsumerOne {

    public static void main(String[] args) {
        try {
            consumerMsg("fanout.exchange");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void consumerMsg(String exchange) throws IOException, TimeoutException {
        //创建工厂连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接地址
        connectionFactory.setHost("192.168.239.128");
        //创建连接
        Connection connection = connectionFactory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(exchange, BuiltinExchangeType.FANOUT);
        //声明队列
        String queue = channel.queueDeclare().getQueue();
        //绑定队列
        channel.queueBind(queue,exchange,"",null);
        //创建消费者，消费消息
        channel.basicConsume(queue, true, (consumerTag, message) -> {
            //消费消息
            String msg = new String(message.getBody(), "UTF-8");
            System.out.println(msg);
        }, (consumerTag) -> {
            System.out.println(consumerTag);
        });
        //关闭连接
     /*   channel.close();
        connection.close();*/
    }
}
