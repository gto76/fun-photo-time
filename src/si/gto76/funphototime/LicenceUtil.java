package si.gto76.funphototime;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class LicenceUtil {

	public static boolean check() {
		Licence licence = readFile();
		if (licence == null)
			return false;
		// Uses absolute value of hash, so licence key doesent need to be negative
		if (Math.abs(licence.user.hashCode()) == Integer.valueOf(licence.hash))
			return true;
		return false;
	}
	
	private static Licence readFile() {
		InputStream fis = null;
		try {
			fis = new FileInputStream("Licence.ffl");
		} catch (FileNotFoundException e) {
			return null;
		}
		if (fis == null)
			return null;
		BufferedReader br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
		String name = null, hash = null;
		
		try {
			name = br.readLine();
			hash = br.readLine();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (name == null || hash == null) {
			return null;
		}
		return new Licence(name, hash);
	}

}

class Licence {
	String user;
	String hash;
	public Licence(String user, String hash) {
		// TODO: check input
		this.user = user;
		this.hash = hash;
	}
}