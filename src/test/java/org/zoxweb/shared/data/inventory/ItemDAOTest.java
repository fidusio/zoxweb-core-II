package org.zoxweb.shared.data.inventory;

import org.junit.Test;
import org.zoxweb.shared.accounting.Currency;
import org.zoxweb.shared.accounting.MoneyValueDAO;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ItemDAOTest
{

    @Test
    public void testItemDAO()
    {
        RangeDAO rangeDAO1 = new RangeDAO(new RangeValueDAO(1), new RangeValueDAO(100));
        MoneyValueDAO moneyValueDAO1 = new MoneyValueDAO(new BigDecimal("1000.00"), Currency.USD);
        PriceDAO priceDAO1 = new PriceDAO(rangeDAO1, moneyValueDAO1);

        RangeDAO rangeDAO2 = new RangeDAO(new RangeValueDAO(101), new RangeValueDAO(200));
        MoneyValueDAO moneyValueDAO2 = new MoneyValueDAO(new BigDecimal("900.00"), Currency.USD);
        PriceDAO priceDAO2 = new PriceDAO(rangeDAO2, moneyValueDAO2);

        PriceRangeDAO priceRangeDAO = new PriceRangeDAO();
        priceRangeDAO.getPriceList().add(priceDAO1);
        priceRangeDAO.getPriceList().add(priceDAO2);

        ItemDAO itemDAO = new ItemDAO();
        itemDAO.setDisplayName("Item A");
        itemDAO.setAvailableQuantity(100);
        itemDAO.setPriceRange(priceRangeDAO);
        itemDAO.setUnitPrice(new MoneyValueDAO(new BigDecimal("10.00"), Currency.USD));
        assertEquals("$10.00", itemDAO.getUnitPrice().toString());
    }

}