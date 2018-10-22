package com.demo.gavant.sudoku;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileAccessor {
	
private static final FileAccessor instance = new FileAccessor();
    
	private FileAccessor(){}

	public static FileAccessor getInstance(){
        return instance;
    }

	/**
	 * Create and write output to file
	 * @param data
	 * @param fileName
	 */
	 public void createOutputFile(String data, String fileName) 
	 {
		FileOutputStream out;
		try {
			out = new FileOutputStream(fileName);
			 out.write(data.getBytes());
			 out.close();

		} catch (FileNotFoundException e) {
			System.out.println("Can't create the output file");
		} catch ( IOException e) {
			System.out.println("There is a problem to write to the file");
		}
	 }
}
