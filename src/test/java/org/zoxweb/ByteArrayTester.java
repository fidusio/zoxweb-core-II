package org.zoxweb;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.zoxweb.server.io.UByteArrayOutputStream;
import org.zoxweb.shared.util.SharedUtil;

public class ByteArrayTester {
	public static void main(String args[]) {

		try {
			UByteArrayOutputStream baos = new UByteArrayOutputStream();

			UByteArrayOutputStream.printInfo(baos);
			String numbers = "1234567890";
			String letters = "ABCDEF";

			baos.write(numbers);
			UByteArrayOutputStream.printInfo(baos);
			baos.reset();
			UByteArrayOutputStream.printInfo(baos);
			baos.write(numbers);
			UByteArrayOutputStream.printInfo(baos);
			baos.insertAt(baos.size(), letters);
			UByteArrayOutputStream.printInfo(baos);
			baos.insertAt(0, letters);
			UByteArrayOutputStream.printInfo(baos);
			baos.insertAt(letters.length(), numbers);
			UByteArrayOutputStream.printInfo(baos);
			baos.insertAt(letters.length() + numbers.length(), letters);
			UByteArrayOutputStream.printInfo(baos);

			baos.write(letters);
			UByteArrayOutputStream.printInfo(baos);

			for (int i = 0; i < 256; i++) {
				baos.write("A");
			}
			System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% ");
			UByteArrayOutputStream.printInfo(baos);
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream("baos"));
			oos.writeObject(baos);
			oos.close();

			baos.removeAt(letters.length(), numbers.length());
			UByteArrayOutputStream.printInfo(baos);

			baos.removeAt(0, letters.length());
			UByteArrayOutputStream.printInfo(baos);

			baos.removeAt(0, letters.length());
			UByteArrayOutputStream.printInfo(baos);

			baos.removeAt(numbers.length(), letters.length());
			UByteArrayOutputStream.printInfo(baos);

			baos.removeAt(baos.size() - 1, 1);
			UByteArrayOutputStream.printInfo(baos);

			baos.removeAt("12345".length(), 2);
			UByteArrayOutputStream.printInfo(baos);

			baos.removeAt(0, 1);
			UByteArrayOutputStream.printInfo(baos);

			System.out.println("Exceptions sections +++++++++++++++++++++++++++++++++++++");
			try {
				baos.insertAt(60, "toto");
			} catch (Exception e) {
				e.printStackTrace();
			}

			UByteArrayOutputStream.printInfo(baos);

			try {
				baos.insertAt(-1, "toto");
			} catch (Exception e) {
				e.printStackTrace();
			}

			UByteArrayOutputStream.printInfo(baos);

			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					"baos"));
			UByteArrayOutputStream tmp = (UByteArrayOutputStream) ois
					.readObject();
			ois.close();
			UByteArrayOutputStream.printInfo(tmp);
			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

			baos.reset();
			baos.write("Marwan");
			UByteArrayOutputStream os = new UByteArrayOutputStream();
			System.out.println("Equals " + baos.equals(os));
			os.write("Marwa");
			System.out.println("Equals " + baos.equals(os));
			os.write("n");
			System.out.println("Equals " + baos.equals(os));
			UByteArrayOutputStream.printInfo( os);
			os.writeAt(1, (byte)'N');
			UByteArrayOutputStream.printInfo( os);
			os.writeAt(33, (byte)'N');
			UByteArrayOutputStream.printInfo( os);
			
			String matches[] = 
				{
					"MN",
					"n",
					"X",
					"xdFDFDFSDFSfds",
					"a",
					"A",
					"MNrwannael",
				};
			
			for ( String str: matches)
			{
				System.out.println( str + " index " +SharedUtil.indexOf(os.getInternalBuffer(), 0, os.size(), str.getBytes(), 0, str.length()));
				try
				{
					System.out.println( str + " index details " + SharedUtil.indexOf(os.getInternalBuffer(), 0, os.size(), str, 0, str.length(), true));
				}
				catch( Exception e)
				{
					e.printStackTrace();
				}
			}
			
			
			
			UByteArrayOutputStream ubaosShift = new UByteArrayOutputStream();
			ubaosShift.write(" Marwan NAEL");
			UByteArrayOutputStream.printInfo(ubaosShift);
			
			ubaosShift.shiftLeft(7, 1);
			UByteArrayOutputStream.printInfo(ubaosShift);
			ubaosShift.write(" Marwan");
			ubaosShift.shiftLeft(1, 0);
			UByteArrayOutputStream.printInfo(ubaosShift);
			
			
			ubaosShift.shiftLeft(11, 0);
			UByteArrayOutputStream.printInfo(ubaosShift);
			ubaosShift.shiftLeft(1, 0);
			UByteArrayOutputStream.printInfo(ubaosShift);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
