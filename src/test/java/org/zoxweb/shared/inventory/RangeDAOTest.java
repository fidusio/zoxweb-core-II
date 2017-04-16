package org.zoxweb.shared.inventory;

import org.junit.Assert;
import org.junit.Test;

import org.zoxweb.shared.data.inventory.RangeDAO;
import org.zoxweb.shared.data.inventory.RangeValueDAO;

/**
 * Created on 4/16/17
 */
public class RangeDAOTest {

    @Test
    public void testRangeDAO() {
        RangeDAO rangeDAO = new RangeDAO();

        RangeValueDAO startRangeValueDAO = new RangeValueDAO();
        startRangeValueDAO.setValue(1);
        Assert.assertEquals(1, startRangeValueDAO.getValue());

        RangeValueDAO endRangeValueDAO = new RangeValueDAO();
        endRangeValueDAO.setValue(100);
        Assert.assertEquals(100, endRangeValueDAO.getValue());

        rangeDAO.setStart(startRangeValueDAO);
        rangeDAO.setEnd(endRangeValueDAO);

        System.out.println(rangeDAO);
    }

}