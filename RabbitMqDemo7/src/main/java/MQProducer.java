import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 * @Tag 主题交换Topic exchange
 */
public class MQProducer {

    public static void main(String[] args) {
        try {
            consumerMsg("topic.exchange","topic.route.one","想要我的消息？就看你有没得这个本事！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void consumerMsg(String exchangeName,String routeKey,String msg) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接地址
        connectionFactory.setHost("10.0.10.3");
        //设置连接端口
        connectionFactory.setPort(5672);
        //设置连接的虚拟机
        connectionFactory.setVirtualHost("mqtest");
        //设置连接用户
        connectionFactory.setUsername("mqtest");
        //设置连接用户密码
        connectionFactory.setPassword("test123");
        //创建连接
        Connection connection = connectionFactory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明临时交换机
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC,false);
        //创建生产者
        channel.basicPublish(exchangeName,routeKey,null,msg.getBytes("UTF-8"));
        //关闭连接
        channel.close();
        connection.close();
    }
}
