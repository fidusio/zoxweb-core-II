package org.zoxweb;

import org.zoxweb.shared.util.SharedBase64;
import org.zoxweb.shared.util.SharedStringUtil;

public class CipherTest {

	public static void main(String[] args) {
		byte key[] = SharedBase64.BASE_64;
		StringBuilder sb = new StringBuilder();

		for (String str:args) {
			byte word[] = SharedStringUtil.getBytes(str);
			
			byte xorCipher[] = new byte[word.length];
			byte decrypted[] = new byte[word.length];
			for(int i=0; i < word.length; i++)
			{
				xorCipher[i] = (byte) (word[i] ^ key[i%key.length]);
				decrypted[i] = (byte) (xorCipher[i] ^key[i%key.length]);
				System.out.println(i%key.length);
			}
			sb.append(SharedStringUtil.toString(decrypted));
			sb.append(" ");
		}
		
		System.out.print(sb.toString());
	}

}