package com.demo.gavant.sudoku;

import java.io.File;

public class Game 
{
    public static void main( String[] args )
    {
    	
    	String fileDirPath = "files"; 
        
        // File object 
        File fileDir = new File(fileDirPath); 
           
        if(fileDir.exists() && fileDir.isDirectory()) 
        { 
            File arr[] = fileDir.listFiles(); 
            
            for(File file: arr) {
            	Board sudoku = new Board(file.getAbsolutePath());
            	sudoku.solve();
    	
            	String fileName = "output/" + file.getName().replace("txt", ".sln.txt");
            	FileAccessor.getInstance().createOutputFile(sudoku.toString(), fileName);
            }
        }
 
    }
}
