package org.zoxweb.shared.data.order;

import org.junit.Test;

import org.zoxweb.shared.accounting.Currency;
import org.zoxweb.shared.accounting.MoneyValueDAO;
import org.zoxweb.shared.data.order.LimitValueDAO;
import org.zoxweb.shared.data.order.LimitValueDAO.LimitType;
import org.zoxweb.shared.data.order.PriceDAO;
import org.zoxweb.shared.data.order.RangeDAO;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class PriceDAOTest
{
	
	@Test
	public void testPriceDAO() 
	{
		MoneyValueDAO moneyValueDAO = new MoneyValueDAO(new BigDecimal("1000.00"), Currency.USD);
		assertEquals("$1000.00", moneyValueDAO.toString());
		
		RangeDAO rangeDAO = new RangeDAO(new LimitValueDAO(1), new LimitValueDAO(100));
		PriceDAO priceDAO = new PriceDAO();
		priceDAO.setPrice(moneyValueDAO);
		priceDAO.setRange(rangeDAO);
		
		assertFalse(priceDAO.isWithinRange(0));
		assertTrue(priceDAO.isWithinRange(1));
		assertTrue(priceDAO.isWithinRange(10));
		assertTrue(priceDAO.isWithinRange(25));
		assertTrue(priceDAO.isWithinRange(50));
		assertTrue(priceDAO.isWithinRange(100));
		assertFalse(priceDAO.isWithinRange(101));
		assertFalse(priceDAO.isWithinRange(1000));
		
		rangeDAO = new RangeDAO(new LimitValueDAO(1), new LimitValueDAO(100, true));
		priceDAO = new PriceDAO();
		priceDAO.setRange(rangeDAO);
		priceDAO.setPrice(moneyValueDAO);
		
		assertFalse(priceDAO.isWithinRange(0));
		assertTrue(priceDAO.isWithinRange(1));
		assertTrue(priceDAO.isWithinRange(10));
		assertTrue(priceDAO.isWithinRange(25));
		assertTrue(priceDAO.isWithinRange(50));
		assertTrue(priceDAO.isWithinRange(99));
		assertFalse(priceDAO.isWithinRange(100));
		assertFalse(priceDAO.isWithinRange(101));
		assertFalse(priceDAO.isWithinRange(1000));
		
		rangeDAO = new RangeDAO(new LimitValueDAO(1), new LimitValueDAO(LimitType.OPEN_VALUE));
		priceDAO = new PriceDAO();
		priceDAO.setRange(rangeDAO);
		priceDAO.setPrice(moneyValueDAO);
		
		assertFalse(priceDAO.isWithinRange(0));
		assertTrue(priceDAO.isWithinRange(1));
		assertTrue(priceDAO.isWithinRange(10));
		assertTrue(priceDAO.isWithinRange(25));
		assertTrue(priceDAO.isWithinRange(50));
		assertTrue(priceDAO.isWithinRange(99));
		assertTrue(priceDAO.isWithinRange(100));
		assertTrue(priceDAO.isWithinRange(101));
		assertTrue(priceDAO.isWithinRange(1000));
		
	}

    @Test
    public void testPriceRangeDAO()
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
        assertNotNull(priceRangeDAO.getPriceList());
        assertTrue(priceRangeDAO.getPriceList().isEmpty());
        priceRangeDAO.getPriceList().add(priceDAO1);
        priceRangeDAO.getPriceList().add(priceDAO2);
        priceRangeDAO.getPriceList().add(priceDAO3);
        priceRangeDAO.getPriceList().add(priceDAO4);
        priceRangeDAO.getPriceList().add(priceDAO5);
        assertEquals(5, priceRangeDAO.getPriceList().size());
        
        MoneyValueDAO price = priceRangeDAO.caclculatePrice(1);
        assertNotNull(price);
        assertEquals("$10.00", price.toString());
        price = priceRangeDAO.caclculatePrice(50);
        assertNotNull(price);
        assertEquals("$10.00", price.toString());
        price = priceRangeDAO.caclculatePrice(75);
        assertNotNull(price);
        assertEquals("$8.00", price.toString());
        price = priceRangeDAO.caclculatePrice(100);
        assertNotNull(price);
        assertEquals("$8.00", price.toString());
        price = priceRangeDAO.caclculatePrice(150);
        assertNotNull(price);
        assertEquals("$6.00", price.toString());
        price = priceRangeDAO.caclculatePrice(175);
        assertNotNull(price);
        assertEquals("$4.00", price.toString());
        price = priceRangeDAO.caclculatePrice(200);
        assertNotNull(price);
        assertEquals("$4.00", price.toString());
        price = priceRangeDAO.caclculatePrice(400);
        assertNotNull(price);
        assertEquals("$2.00", price.toString());
        
        price = priceRangeDAO.caclculatePrice(0);
        assertNull(price);
    }

}