import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者1
 *
 * @Tag 公平派遣模拟
 */
public class MQConsumeOne {

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
        //设置消费者中最大的未确认消息数
        channel.basicQos(1);
        //创建消费者
        channel.basicConsume(queue,false,( consumerTag, message)->{
            //模拟处理耗时很长得消息
            try {
                Thread.sleep((long) (Math.random()*20000+1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(new String(message.getBody(),"UTF-8"));
            //添加手动确认
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        },(consumerTag)->{
            System.out.println(consumerTag);
        });
        //关闭连接
       /* channel.close();
        connection.close();*/
    }
}
