package org.zoxweb.shared.util;

import org.junit.Assert;
import org.junit.Test;

public class AppIDURITest
{
	@Test
	public void testPositive()
	{
		AppIDURI aiu = AppIDURI.parse("///zoxweb.com/zoxweb/batata");
		System.out.println(aiu.getAppID().getSubjectID());
		Assert.assertTrue("batata".equals(aiu.getRest()[0]));
	}
}
