package org.zoxweb.shared.data;

public class FormConfigInfoDAOTest {

	public static void main(String[] args) {
		try {
			FormConfigInfoDAO fcid = new FormConfigInfoDAO();

			System.out.println(fcid.getDefaultParameters().getClass().getName());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
