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

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FinancialTransactionDAOTest
{

	@Test
    public void testFinancialTransactionDAO()
    {
        FinancialTransactionDAO transaction = new FinancialTransactionDAO();
        transaction.setAmount(new MoneyValueDAO(new BigDecimal("100"), Currency.USD));
        transaction.setType(TransactionType.CREDIT);
        transaction.setDescriptor(TransactionDescriptor.MONTHLY_PAYMENT.name());
        transaction.setCreationTime(System.currentTimeMillis());

        BillingAccountDAO account = new BillingAccountDAO();
        account.setCurrentBalance(new MoneyValueDAO(new BigDecimal("200"), Currency.USD));
        assertEquals(new MoneyValueDAO(new BigDecimal("200.00"), Currency.USD), account.getCurrentBalance());

        account.applyTransaction(transaction);

        assertNotNull(transaction.getAmount());
        assertEquals(Currency.USD, transaction.getAmount().getCurrency());
        assertEquals(new BigDecimal("100.00"), transaction.getAmount().getValue());
        assertEquals(TransactionType.CREDIT, transaction.getType());
        assertEquals(TransactionDescriptor.MONTHLY_PAYMENT.name(), transaction.getDescriptor());

        assertNotNull(account.getCurrentBalance());
        assertEquals(new MoneyValueDAO(new BigDecimal("300.00"), Currency.USD), account.getCurrentBalance());

        account.applyTransaction(new FinancialTransactionDAO(new MoneyValueDAO(10)));
        assertEquals(new MoneyValueDAO(new BigDecimal("310.00"), Currency.USD), account.getCurrentBalance());

        account.applyTransaction(new FinancialTransactionDAO(new MoneyValueDAO(20)));
        assertEquals(new MoneyValueDAO(new BigDecimal("330.00"), Currency.USD), account.getCurrentBalance());
    }

}