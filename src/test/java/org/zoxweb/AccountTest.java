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
package org.zoxweb;

import java.math.BigDecimal;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AccountTest {

	private static BigDecimal balance = new BigDecimal("0.00");
	private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
	private static final int DECIMALS = 2;
	private static final DateFormat TIME_STAMP_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	//private static final SimpleDateFormat DUE_DATE_FORMAT = new SimpleDateFormat("MM-dd-yyyy");

	public static BigDecimal getBalance()
	{
		return rounded(balance);
	}
	
	public static BigDecimal credit(String amount) {
		BigDecimal credit = new BigDecimal(amount);
		
		balance = rounded(balance.add(credit));
		
		return rounded(credit);
	}
	
	public static BigDecimal debit(String amount) {
		BigDecimal debit = new BigDecimal(amount);
		
		balance = rounded(balance.subtract(debit));
		
		return rounded(debit);
	}

	private static BigDecimal rounded(BigDecimal number)
	{
	    return number.setScale(DECIMALS, ROUNDING_MODE);
	}

	public static long timeStamp()
	{
		return System.currentTimeMillis();
	}

	public static BigDecimal paymentDue() {
		return null;
	}
	
	
	public static void main(String[] args) {
		System.out.format("Current Balance: $ %s\t%s\n", getBalance(), TIME_STAMP_FORMAT.format(new Date(timeStamp())));
		System.out.println("Credit: $" + credit("100.00"));
		System.out.format("Current Balance: $ %s\t%s\n", getBalance(), TIME_STAMP_FORMAT.format(new Date(timeStamp())));
		System.out.println("Credit: $" + credit("400.55"));
		System.out.format("Current Balance: $ %s\t%s\n", getBalance(), TIME_STAMP_FORMAT.format(new Date(timeStamp())));
		System.out.println("Debit:  ($" + debit("100.35888") + ")");
		System.out.format("Current Balance: $ %s\t%s\n", getBalance(), TIME_STAMP_FORMAT.format(new Date(timeStamp())));

	}

}