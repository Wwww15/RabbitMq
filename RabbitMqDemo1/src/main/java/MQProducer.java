import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MQProducer {

    public static void main(String[] args) {

        try {
            produceMsg("rabbitmq.first","第一条消息");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        finally {

        }

    }

    public static void produceMsg(String queue,String msg) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接地址
        connectionFactory.setHost("192.168.239.128");
        //设置连接端口(默认配置)
        connectionFactory.setPort(5672);
        //设置连接用户(默认配置)
        connectionFactory.setUsername("guest");
        //设置连接密码(默认配置)
        connectionFactory.setPassword("guest");
        //设置连接虚拟机(默认配置)
        connectionFactory.setVirtualHost("/");
        //创建连接
        Connection connection = connectionFactory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明队列(如果队列不存在，会创建)
        channel.queueDeclare(queue,true,false,false,null);
        //创建生产者
        channel.basicPublish("",queue,null,msg.getBytes());
        //关闭连接
        channel.close();
        connection.close();
    }
}
