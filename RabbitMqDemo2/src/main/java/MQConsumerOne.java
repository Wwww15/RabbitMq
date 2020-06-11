import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MQConsumerOne {

    public static void main(String[] args) {

    }

    /**
     * 消费消息
     * @param queue
     */
    public void consumerMsg(String queue)
    {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置代理地址
        connectionFactory.setHost("");

        Connection connection = null;
        Channel channel = null;
        try {
            //创建连接
            connection = connectionFactory.newConnection();
            //创建通道
            channel = connection.createChannel();
            //声明队列
            channel.queueDeclare(queue,true,false,false,null);
            //创建消费者
            channel.basicConsume(queue,true,(consumerTag,message)->{
                //打印消息
                System.out.println(new String(message.getBody(),"UTF-8"));
            },(consumerTag) -> {
                //打印用户标记
                System.out.println(consumerTag);
            });
        } catch (Exception e) {
        }
    }
}
