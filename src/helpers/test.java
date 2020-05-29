package helpers;

import java.io.File;

public class test {

	public static void main(String[] args) {
		File f = new File("\\Amazon\\resources\\config.properties");
		if (f.exists()) {
			System.out.println("exists");
		} else {
			System.out.println("not exists");
		}
	}
}



