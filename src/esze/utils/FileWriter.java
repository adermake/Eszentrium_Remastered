package esze.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class FileWriter {


	public static void appendFile(String filename,String data)
	 {
		try {
		    Files.write(Paths.get(filename+".txt"), data.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
	}
}
