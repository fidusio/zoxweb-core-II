package org.zoxweb.shared.data.order;

import org.junit.Test;

import org.zoxweb.shared.accounting.Currency;
import org.zoxweb.shared.accounting.MoneyValueDAO;
import org.zoxweb.shared.data.order.ItemDAO;
import org.zoxweb.shared.data.order.LimitValueDAO;
import org.zoxweb.shared.data.order.PriceDAO;
import org.zoxweb.shared.data.order.PriceRangeDAO;
import org.zoxweb.shared.data.order.RangeDAO;
import org.zoxweb.shared.data.order.LimitValueDAO.LimitType;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class ItemQuantityDAOTest
{

    @Test
    public void testItemQuantityDAO()
    {
        RangeDAO rangeDAO1 = new RangeDAO(new LimitValueDAO(1), new LimitValueDAO(50));
        MoneyValueDAO moneyValueDAO1 = new MoneyValueDAO(new BigDecimal("10.00"), Currency.USD);
        PriceDAO priceDAO1 = new PriceDAO();
        priceDAO1.setRange(rangeDAO1);
        priceDAO1.setPrice(moneyValueDAO1);

        RangeDAO rangeDAO2 = new RangeDAO(new LimitValueDAO(51), new LimitValueDAO(100));
        MoneyValueDAO moneyValueDAO2 = new MoneyValueDAO(new BigDecimal("8.00"), Currency.USD);
        PriceDAO priceDAO2 = new PriceDAO();
        priceDAO2.setRange(rangeDAO2);
        priceDAO2.setPrice(moneyValueDAO2);
        
        RangeDAO rangeDAO3 = new RangeDAO(new LimitValueDAO(101), new LimitValueDAO(150));
        MoneyValueDAO moneyValueDAO3 = new MoneyValueDAO(new BigDecimal("6.00"), Currency.USD);
        PriceDAO priceDAO3 = new PriceDAO();
        priceDAO3.setRange(rangeDAO3);
        priceDAO3.setPrice(moneyValueDAO3);
        
        RangeDAO rangeDAO4 = new RangeDAO(new LimitValueDAO(151), new LimitValueDAO(200));
        MoneyValueDAO moneyValueDAO4 = new MoneyValueDAO(new BigDecimal("4.00"), Currency.USD);
        PriceDAO priceDAO4 = new PriceDAO();
        priceDAO4.setRange(rangeDAO4);
        priceDAO4.setPrice(moneyValueDAO4);
        
        RangeDAO rangeDAO5 = new RangeDAO(new LimitValueDAO(201), new LimitValueDAO(LimitType.OPEN_VALUE));
        MoneyValueDAO moneyValueDAO5 = new MoneyValueDAO(new BigDecimal("2.00"), Currency.USD);
        PriceDAO priceDAO5 = new PriceDAO();
        priceDAO5.setRange(rangeDAO5);
        priceDAO5.setPrice(moneyValueDAO5);

        PriceRangeDAO priceRangeDAO = new PriceRangeDAO();
        priceRangeDAO.getPriceList().add(priceDAO1);
        priceRangeDAO.getPriceList().add(priceDAO2);
        priceRangeDAO.getPriceList().add(priceDAO3);
        priceRangeDAO.getPriceList().add(priceDAO4);
        priceRangeDAO.getPriceList().add(priceDAO5);

        ItemDAO itemDAO = new ItemDAO();
        itemDAO.setDisplayName("Item A");
        itemDAO.setPriceRange(priceRangeDAO);
        
        ItemQuantityDAO itemQuantityDAO = new ItemQuantityDAO();
        itemQuantityDAO.setItem(itemDAO);
        itemQuantityDAO.setQuantity(50);
        MoneyValueDAO total = itemQuantityDAO.computeTotal();
        assertNotNull(total);
        assertNotNull(total.getValue());
        assertEquals("$500.00", total.toString());
        
        itemQuantityDAO = new ItemQuantityDAO();
        itemQuantityDAO.setItem(itemDAO);
        itemQuantityDAO.setQuantity(150);
        total = itemQuantityDAO.computeTotal();
        assertNotNull(total);
        assertNotNull(total.getValue());
        assertEquals("$900.00", total.toString());
        
        itemQuantityDAO = new ItemQuantityDAO();
        itemQuantityDAO.setItem(itemDAO);
        itemQuantityDAO.setQuantity(400);
        total = itemQuantityDAO.computeTotal();
        assertNotNull(total);
        assertNotNull(total.getValue());
        assertEquals("$800.00", total.toString());
    }
    
    
}