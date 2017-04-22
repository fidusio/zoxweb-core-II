/*
 * Copyright (c) 2012-2017 ZoxWeb.com LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.zoxweb.shared.data.inventory;

import org.junit.Assert;
import org.junit.Test;

public class RangeDAOTest
{

    @Test
    public void testRangeDAO()
    {
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