import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * 生产者
 *
 * @Tag 消息确认和持久性
 */
public class MQProducer {

    public static void main(String[] args) {

        for (int n=1;n<11;n++)
        {
            try {
                produceMsg("ack.durable","看你是不是能确认哇！第"+n+"次确认！");
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        //创建生产者
        channel.basicPublish("",queue, MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes());
        //关闭连接
        channel.close();
        connection.close();
    }
}
