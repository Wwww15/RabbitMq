import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者2
 *
 * @Tag 工厂队列
 */
public class MQConsumerTwo {

    public static void main(String[] args) {
        try {
            consumerMsg("work.queue");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 消费消息
     * @param queue
     */
    public static void consumerMsg(String queue) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置代理地址
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
        Connection  connection = connectionFactory.newConnection();
        //创建通道
        Channel  channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare("work.exchange", BuiltinExchangeType.DIRECT,false,true,null);
        //声明队列
        channel.queueDeclare(queue,false,false,false,null);
        //创建消费者
        channel.basicConsume(queue,true,(consumerTag,message)->{
            //打印消息
            try {
                Thread.sleep(Math.round(Math.random()*2000)+1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(new String(message.getBody(),"UTF-8"));
        },(consumerTag) -> {
            //打印用户标记
            System.out.println(consumerTag);
        });
        //关闭连接
        /* channel.close();
           connection.close();
        */
    }
}
