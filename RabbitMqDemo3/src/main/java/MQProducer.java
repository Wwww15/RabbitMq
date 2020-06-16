import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MQProducer {

    public static void main(String[] args) {
        try {
            produceMsg("ack.durable","看你是不是能确认哇！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void produceMsg(String queue,String msg) throws IOException, TimeoutException {
        //获取连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接地址(其他信息默认)
        connectionFactory.setHost("192.168.239.128");
        //获取连接
        Connection connection = connectionFactory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //创建消费者
        channel.basicPublish("",queue,null,msg.getBytes());
        //关闭连接
        channel.close();
        connection.close();
    }
}
