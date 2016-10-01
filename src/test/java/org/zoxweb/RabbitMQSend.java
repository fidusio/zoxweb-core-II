package org.zoxweb;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


import java.util.HashMap;
import java.util.Map;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;

public class RabbitMQSend {


	private  static String EXCHANGE = "";
	public static void main(String[] args) 
	{
		long ts = System.currentTimeMillis();
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
		    int repeat = 1;
		    if (args.length > index)
		    {
		    	repeat = Integer.parseInt(args[index++]);
		    }
		    Connection connection = factory.newConnection();
		    Channel channel = connection.createChannel();
		    Map<String, Object> argsRM = new HashMap<String, Object>();
		    argsRM.put("x-max-priority", 10);
		   // channel.exchangeDeclare(EXCHANGE, "fanout", true, true, argsRM);
		  
		    channel.queueDeclare(EXCHANGE, true, false, false, argsRM);
		    //channel.queueDeclarePassive(QUEUE_NAME);
//		    SecureRandom sr =  SecureRandom.getInstanceStrong();
		    BasicProperties bpAll[] = new BasicProperties[repeat];
		    byte messages[][] = new byte[repeat][];
		    
		    
		    for (int i = 0; i < repeat; i++)
		    {
		    	int priority = 5 -((i)%2) + i%4;
		    	String message = "[" + i +"]:" + priority;
		    	BasicProperties bp = new BasicProperties();
		    	bp = bp.builder().priority(priority).messageId("" + i).build();
		    	bpAll[i] = bp;
		    	messages[i] = message.getBytes();
		    	//System.out.println(" [x] Sent '" + message + "'");
		    	
		    
		    }
		    for (int i = 0; i < repeat; i++)
		    {
		    	channel.basicPublish("", EXCHANGE, bpAll[i], messages[i]);
		    }
		    
		    channel.close();
		    connection.close();
		    
		    ts = System.currentTimeMillis() - ts;
		    System.out.println("Total sent " + repeat + " it took " + ts + " millis rate " + (float)repeat/(float)ts);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
//		System.out.println("Total sent " + repeat + " it took " + ts millis);
	}

}
