/*
 * Copyright (c) 2012-May 30, 2014 ZoxWeb.com LLC.
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zoxweb.server.util.GSONUtil;

public class FinancialTransactionDAOTest {

	private static final DateFormat TIME_STAMP_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	public static void main(String[] args) {

		try {
			FinancialTransactionDAO transaction = new FinancialTransactionDAO();
			BillingAccountDAO account = new BillingAccountDAO();
			
			transaction.setAmount(new MoneyValueDAO(new BigDecimal("100"), Currency.USD));
			transaction.setType(TransactionType.CREDIT);
			transaction.setDescriptor(TransactionDescriptor.MONTHLY_PAYMENT.name());
			transaction.setCreationTime(System.currentTimeMillis());
			
			account.getCurrentBalance().setCurrency(Currency.USD);
			account.getCurrentBalance().setValue(new BigDecimal("200"));
			account.applyTransaction(transaction);
			
			System.out.println("Transaction Values: ");
			System.out.println("Currency: " + transaction.getAmount().getCurrency());
			System.out.println("Amount: " + transaction.getAmount().getValue());
			System.out.println("Type: " + transaction.getType());
			System.out.println("Description: " + transaction.getDescriptor());
			System.out.println("Time Stamp: " + TIME_STAMP_FORMAT.format(new Date(transaction.getCreationTime())));
			
			System.out.println(GSONUtil.toJSON(transaction, true));
			System.out.println(GSONUtil.toJSON(account, true));
			
			String json = GSONUtil.toJSON(account, true);
			System.out.println(json);
			
			account = GSONUtil.fromJSON(json, BillingAccountDAO.class);
			
			String json1 = GSONUtil.toJSON(account, true);
			System.out.println("JSON Objects Comparison: "+ json1.equals(json));

			System.out.println(account.getCurrentBalance().getCurrency());
			System.out.println(account.getCurrentBalance());
			System.out.println(account.applyTransaction(new FinancialTransactionDAO(new MoneyValueDAO(10))));
			System.out.println(account.applyTransaction(new FinancialTransactionDAO(new MoneyValueDAO(15))));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}