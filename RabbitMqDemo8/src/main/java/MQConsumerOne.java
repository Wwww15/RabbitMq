import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 消费者1
 * @Tag 主题交换Topic exchange
 */
public class MQConsumerOne {

    public static void main(String[] args) {
        try {
            consumerMsg("header.exchange");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建参数
     */
    public static Map buildArgs()
    {
        HashMap<String,String> args = new HashMap<>();
        args.put("x-match","all");
        return args;
    }

    /**
     * 消费者逻辑
     * @param exchangeName
     * @throws IOException
     * @throws TimeoutException
     */
    public static void consumerMsg(String exchangeName) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接地址
        connectionFactory.setHost("192.168.239.128");
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
        //声明交换机
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.HEADERS);
        //声明队列(临时排它队列)
        String queueName = channel.queueDeclare().getQueue();
        //构建参数
        Map args = buildArgs();
        //绑定交换机
        channel.queueBind(queueName,exchangeName,"",args);
        //消费消息（主动确认）
        channel.basicConsume(queueName,true,(tag,msg)->{
            System.out.println(new String(msg.getBody(),"UTF-8"));
        },(cancel)->{

        });
       /* //关闭连接
        channel.close();
        connection.close();*/
    }



}
