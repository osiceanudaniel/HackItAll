package ro.hackitall.encode.util;


public class StringUtil {
	public static Boolean isBlank(String string) {
		if(string == null || string.equals("")) {
			return true;
		}
		return false;
	}
}
