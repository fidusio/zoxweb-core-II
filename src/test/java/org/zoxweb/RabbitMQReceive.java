package org.zoxweb;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

public class RabbitMQReceive {

    public static class ConsumerTask
        implements Runnable
    {

        volatile Channel channel;
        long id;
        String message;
        String uuid;


        public void run()
        {
            try
            {
                System.out.println( "START " + Thread.currentThread().getName() + ":" + id + ":" + message);
                long timeToSleep = SecureRandom.getInstance("SHA1PRNG").nextInt(100) +1 ;
		          try {

					Thread.sleep(timeToSleep);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

                System.out.println( "END afer sleeping " + timeToSleep + ":"+ Thread.currentThread().getName() + ":" + id);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

	//private static long counter = 0;
    private static Executor executor = Executors.newCachedThreadPool();

	private  static String EXCHANGE = "";
	public static void main(String[] args) 
	{
		try
		{
			int index = 0;
		// TODO Auto-generated method stub
		 ConnectionFactory factory = new ConnectionFactory();
		 	
		 	factory.setHost(args[index++]);
		 	factory.setVirtualHost(args[index++]);
		 	EXCHANGE = args[index++];
		    factory.setUsername(args[index++]);
		    factory.setPassword(args[index++]);
		    
		    Connection connection = factory.newConnection();
		    Channel channel = connection.createChannel();
		    channel.basicQos(5, false);
		    Map<String, Object> argsRM = new HashMap<String, Object>();
		    argsRM.put("x-max-priority", 10);
		    //argsRM.put("x-delayed-type", "direct");
		    argsRM.put("x-max-length", 10000000);//10000000
		    channel.queueDeclare(EXCHANGE, true, false, false, argsRM);
		    //channel.queueDeclarePassive(QUEUE_NAME);
		    
		    
		    
		    
		    //channel.exchangeDeclare(EXCHANGE, "fanout", true);
		    //String queueName = channel.queueDeclare().getQueue();
		    //channel.queueBind(queueName, EXCHANGE, "");
		    
		    
//		    channel.exchangeDeclare("fidusex", "fanout", true);
//		    String queueName = channel.queueDeclare().getQueue();
//		    System.out.println("Queue:"+ queueName);
//		    channel.queueBind(queueName, "fidusex", "");
//		    
		    
		    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		    Consumer consumer = new DefaultConsumer(channel) {
		    	String uuid = UUID.randomUUID().toString();
		        @Override
		        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
		            throws IOException {
		          String message = new String(body, "UTF-8");
		          //if (counter++ % 10000 == 0)
		        	 // System.out.println( Thread.currentThread().getName() + ":" + uuid + " [" +(++counter) +"] Received '" + message + "'" + properties.getPriority() + "," + properties.getMessageId());
//		          try {
//					Thread.sleep(50);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
		          ConsumerTask ct = new ConsumerTask();
		          ct.channel = getChannel();
		          ct.id = envelope.getDeliveryTag();
		          ct.message = message;
		          ct.uuid = uuid;

		          executor.execute(ct);
		          getChannel().basicAck(envelope.getDeliveryTag(), true);

		        }
		        
		      };
		      channel.basicConsume(EXCHANGE, false, argsRM, consumer);
		    
		   
//		      channel.close();
//			  connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		
		
		System.out.println("end of main");
		
	}

}
