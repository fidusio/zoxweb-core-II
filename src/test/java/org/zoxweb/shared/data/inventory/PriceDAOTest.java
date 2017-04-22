package org.zoxweb.shared.data.inventory;

import org.junit.Test;
import org.zoxweb.shared.accounting.Currency;
import org.zoxweb.shared.accounting.MoneyValueDAO;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class PriceDAOTest
{

    @Test
    public void testPriceDAO()
    {
        RangeDAO rangeDAO = new RangeDAO(new RangeValueDAO(1), new RangeValueDAO(100));

        MoneyValueDAO moneyValueDAO = new MoneyValueDAO(new BigDecimal("1000.00"), Currency.USD);
        assertEquals("$1000.00", moneyValueDAO.toString());

        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setRange(rangeDAO);
        priceDAO.setPrice(moneyValueDAO);
        System.out.println(priceDAO);
    }

    @Test
    public void testPriceRangeDAO()
    {
        RangeDAO rangeDAO1 = new RangeDAO(new RangeValueDAO(1), new RangeValueDAO(100));
        MoneyValueDAO moneyValueDAO1 = new MoneyValueDAO(new BigDecimal("1000.00"), Currency.USD);
        PriceDAO priceDAO1 = new PriceDAO();
        priceDAO1.setRange(rangeDAO1);
        priceDAO1.setPrice(moneyValueDAO1);

        RangeDAO rangeDAO2 = new RangeDAO(new RangeValueDAO(101), new RangeValueDAO(200));
        MoneyValueDAO moneyValueDAO2 = new MoneyValueDAO(new BigDecimal("900.00"), Currency.USD);
        PriceDAO priceDAO2 = new PriceDAO();
        priceDAO2.setRange(rangeDAO2);
        priceDAO2.setPrice(moneyValueDAO2);

        PriceRangeDAO priceRangeDAO = new PriceRangeDAO();
        assertNotNull(priceRangeDAO.getPriceList());
        assertTrue(priceRangeDAO.getPriceList().isEmpty());
        priceRangeDAO.getPriceList().add(priceDAO1);
        priceRangeDAO.getPriceList().add(priceDAO2);
        assertEquals(2, priceRangeDAO.getPriceList().size());
    }

}