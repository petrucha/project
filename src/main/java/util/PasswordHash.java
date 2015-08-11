package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PasswordHash {

	public static String hash(String string) {
		try {
			// Create MessageDigest and encoding for input String
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] hash = digest.digest(string.getBytes("UTF-8"));

			// Hash the Input String
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * This method verifies the content of a list.
	 * 
	 * @param clazz
	 * @param c
	 * @return
	 * @throws ClassCastException
	 */
	public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c)
			throws ClassCastException {
		List<T> r = new ArrayList<T>(c.size());
		for (Object o : c)
			r.add(clazz.cast(o));
		return r;
	}

}
