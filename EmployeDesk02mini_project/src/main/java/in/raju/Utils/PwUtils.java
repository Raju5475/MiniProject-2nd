package in.raju.Utils;

import org.apache.commons.lang3.RandomStringUtils;

public class PwUtils {
	
	public  static String getRandomPw() {
	
	String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	String pwd = RandomStringUtils.random( 6, characters );
	return pwd;
		
	}

}
