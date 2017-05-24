package org.zoxweb.shared.data.order;

import java.util.Date;

import org.zoxweb.shared.data.TimeStampDAO;
import org.zoxweb.shared.util.GetNVConfig;
import org.zoxweb.shared.util.NVConfig;
import org.zoxweb.shared.util.NVConfigEntity;
import org.zoxweb.shared.util.NVConfigEntityLocal;
import org.zoxweb.shared.util.NVConfigManager;
import org.zoxweb.shared.util.SharedUtil;

@SuppressWarnings("serial")
public class CustomerOrderDAO 
	extends TimeStampDAO 
{

    public enum Param
    	implements GetNVConfig
	{
        ORDER_ID(NVConfigManager.createNVConfig("order_id", "Order ID", "OrderID", true, false, String.class)),
        ORDER(NVConfigManager.createNVConfigEntity("order", "order", "Order", true, true, OrderDAO.class, NVConfigEntity.ArrayType.NOT_ARRAY)),
	    ORDER_STATUS(NVConfigManager.createNVConfig("order_status", "Order status", "OrderStatus", true, false, OrderStatus.class)),
		SCHEDULED_DELIVERY(NVConfigManager.createNVConfig("scheduled_delivery", "Scheduled delivery.", "ScheduledDelivery", true, true, Date.class)),
		
	    ;
	
	    private final NVConfig nvc;
	
	    Param(NVConfig nvc)
	    {
	        this.nvc = nvc;
	    }
	    
	    @Override
	    public NVConfig getNVConfig()
	    {
	        return nvc;
	    }
	}
	
    public static final NVConfigEntity NVC_CUSTOMER_ORDER_DAO = new NVConfigEntityLocal(
            "customer_order_dao",
            null,
            CustomerOrderDAO.class.getSimpleName(),
            true,
            false,
            false,
            false,
            CustomerOrderDAO.class,
            SharedUtil.extractNVConfigs(Param.values()),
            null,
            false,
            TimeStampDAO.NVC_TIME_STAMP_DAO
    );

    public CustomerOrderDAO()
    {
        super(NVC_CUSTOMER_ORDER_DAO);
    }

    /**
     * Returns the order ID.
     * @return
     */
    public String getOrderID()
    {
        return lookupValue(Param.ORDER_ID);
    }

    /**
     * Sets the order ID.
     * @param orderID
     */
    public void setOrderID(String orderID)
    {
        setValue(Param.ORDER_ID, orderID);
    }

    /**
     * Returns the order.
     * @return
     */
    public OrderDAO getOrder() 
    {
    	return lookupValue(Param.ORDER);
    }
    
    /**
     * Sets the order.
     * @param order
     */
    public void setOrder(OrderDAO order)
    {
    	setValue(Param.ORDER, order);
    }
    
    
    /**
     * Returns the order status.
     * @return
     */
    public OrderStatus getOrderStatus() {
    	return lookupValue(Param.ORDER_STATUS);
	}
    
    /**
     * Sets the order status.
     * @param status
     */
    public void setOrderStatus(OrderStatus status)
	{
    	setValue(Param.ORDER_STATUS, status);
	}
    
    /**
     * Returns the scheduled delivery time.
     * @return
     */
	public long getScheduledDelivery() 
	{
		return lookupValue(Param.SCHEDULED_DELIVERY);
	}
	
	/**
	 * Sets the scheduled delivery time.
	 * @param scheduledDelivery
	 */
	public void setScheduledDelivery(long scheduledDelivery) 
	{
		setValue(Param.SCHEDULED_DELIVERY, scheduledDelivery);
	}

	
}