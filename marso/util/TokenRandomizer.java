package marso.util;

import java.util.concurrent.ThreadLocalRandom;
import java.util.List;
import java.util.Arrays;

public class TokenRandomizer {
	private static List<String> chars = Arrays.asList( new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "l", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" } );

	public static String next(){
		String token = "";
		for( int i = 0; i < 10; i++ ){
			int r = ThreadLocalRandom.current().nextInt(0, chars.size());
			token += chars.get(r);
		}
		return token;
	}
	
}
