import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 * @Tag 主题交换Topic exchange
 */
public class MQProducer {

    public static void main(String[] args) {
        try {
            producerMsg("header.exchange","想要我的消息？就看你有没得这个本事！");
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
        args.put("name","张三");
        args.put("age","32");
        args.put("weight","180");
        return args;
    }

    /**
     * 生产者
     * @param exchangeName
     * @param msg
     * @throws IOException
     * @throws TimeoutException
     */
    public static void producerMsg(String exchangeName,String msg) throws IOException, TimeoutException {
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
        //声明临时交换机
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.HEADERS,false);
        //构建标头参数
        Map map = buildArgs();
        AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder().headers(map).build();
        //创建生产者
        channel.basicPublish(exchangeName,"",basicProperties,msg.getBytes("UTF-8"));
        //关闭连接
        channel.close();
        connection.close();
    }
}
