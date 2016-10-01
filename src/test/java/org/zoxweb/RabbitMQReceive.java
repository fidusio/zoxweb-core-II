package org.zoxweb;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

public class RabbitMQReceive {

	private static long counter = 0;

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
		    Map<String, Object> argsRM = new HashMap<String, Object>();
		    argsRM.put("x-max-priority", 10);
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
		        @Override
		        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
		            throws IOException {
		          String message = new String(body, "UTF-8");
		          System.out.println( "[" +(++counter) +"] Received '" + message + "'" + properties.getPriority() + "," + properties.getMessageId());
//		          try {
//					Thread.sleep(50);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
		        }
		      };
		      channel.basicConsume(EXCHANGE, true, argsRM, consumer);
		    
		   
//		      channel.close();
//			  connection.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
	}

}
