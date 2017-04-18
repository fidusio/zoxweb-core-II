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
package org.zoxweb.shared.accounting;

import java.math.BigDecimal;

public class BillingItemContainerDAOTest {

	public static void main(String[] args) {
		BillingItemsContainerDAO container = new BillingItemsContainerDAO();
		
		for (int i = 0; i < 10; i++) {
			BillingItemDAO item = new BillingItemDAO();
			item.setUnitCost(new BigDecimal(10));
			item.setQuantity(new BigDecimal(1));
			
			container.addBillingItem(item);
		}
		
		System.out.println("Number of Billing Items: " + container.getBillingItems().size() + " Total: " + container.getTotal());
		
		BillingItemDAO item1 = new BillingItemDAO();
		item1.setUnitCost(new BigDecimal(10));
		item1.setQuantity(new BigDecimal(2));
		System.out.println("ADD Billing Item 1  [10 units x 2]");
		container.addBillingItem(item1);
		System.out.println("Number of Billing Items: " + container.getBillingItems().size() + " Total: " + container.getTotal());
		
		System.out.println("REMOVE Billing Item 1  [10 units x 1]");
		container.removeBillingItem(item1);
		System.out.println("Number of Billing Items: " + container.getBillingItems().size() + " Total: " + container.getTotal());
		
		BillingItemDAO item2 = new BillingItemDAO();
		item2.setUnitCost(new BigDecimal(80));
		item2.setQuantity(new BigDecimal(2));
		System.out.println("ADD Billing Item 2  [80 units x 2]");
		container.addBillingItem(item2);
		System.out.println("Number of Billing Items: " + container.getBillingItems().size() + " Total: " + container.getTotal());
		
		BillingItemDAO item3 = new BillingItemDAO();
		item3.setUnitCost(new BigDecimal(20));
		item3.setQuantity(new BigDecimal(2));
		System.out.println("ADD Billing Item 3  [20 units x 2]");
		container.addBillingItem(item3);
		System.out.println("Number of Billing Items: " + container.getBillingItems().size() + " Total: " + container.getTotal());
		
		BillingItemDAO item4 = new BillingItemDAO();
		item4.setReferenceID("4");
		item4.setUnitCost(new BigDecimal(50));
		item4.setQuantity(new BigDecimal(4));
		System.out.println("ADD Billing Item 4  [50 units]");
		container.addBillingItem(item4);
		System.out.println("Number of Billing Items: " + container.getBillingItems().size() + " Total: " + container.getTotal());
		System.out.println("ADD Billing Item 4  [50 units x 4]");
		container.addBillingItem(item4);
		System.out.println("FAILED: Already Added!");
		System.out.println("Number of Billing Items: " + container.getBillingItems().size() + " Total: " + container.getTotal());
		
		BillingItemDAO item5 = new BillingItemDAO();
		item5.setUnitCost(new BigDecimal(100));
		item5.setQuantity(new BigDecimal(4));
		System.out.println("ADD Billing Item 5  [100 units x 4]");
		container.addBillingItem(item5);
		System.out.println("Number of Billing Items: " + container.getBillingItems().size() + " Total: " + container.getTotal());

	}
	
}