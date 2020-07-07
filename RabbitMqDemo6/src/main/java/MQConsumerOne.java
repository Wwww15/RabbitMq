import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者1
 *
 * @Tag 扇出交换 Fanout exchange
 */
public class MQConsumerOne {

    public static void main(String[] args) {
        try {
            consumerMsg("fanout.exchange","fanout.queue","");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void consumerMsg(String exchange,String queue,String routeKey) throws IOException, TimeoutException {
        //创建工厂连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接地址
        connectionFactory.setHost("10.0.10.3");
        //创建连接
        Connection connection = connectionFactory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(exchange, BuiltinExchangeType.FANOUT,false);
        //声明队列
        channel.queueDeclare(queue,false,false,true,null);
        //绑定队列
        channel.queueBind(queue,exchange,routeKey,null);
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
