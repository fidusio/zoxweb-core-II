package org.zoxweb;

import org.zoxweb.shared.data.FormConfigInfoDAO;

public class FormConfigInfoDAOTest
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		try
		{
			FormConfigInfoDAO fcid = new FormConfigInfoDAO();
			
			System.out.println(fcid.getDefaultParameters().getClass().getName());
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
