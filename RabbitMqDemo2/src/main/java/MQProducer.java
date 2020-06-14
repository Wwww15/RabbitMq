import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 */

public class MQProducer {

    public static void main(String[] args) {


            try {
                produceMsg("work.queue","来啦，老弟");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public static  void produceMsg(String queue,String msg) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置代理地址
        connectionFactory.setHost("192.168.239.128");
        //创建连接
        Connection connection = connectionFactory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare("work.exchange", BuiltinExchangeType.DIRECT,false,true,null);
        //声明队列
        channel.queueDeclare(queue,false,false,false,null);
        //创建生产者
        channel.basicPublish("work.exchange","queue",null, msg.getBytes());
        //关闭连接
        channel.close();
        connection.close();
    }
}
