/*Author: Austin Hendrix
 *Date: 8/26/2016
 *Info: This is an application that utilizes the Apache POI API to read text location from an excel spreadsheet,
 *process the text file, and record the sought after data to the aforementioned spreadsheet. 
 *
 *The spreadsheet stores the location of the original full text name, the name of the file containing the sample taken
 *from the text, the title and author of the text, the number of words in the sample, the number of collegiate words
 *in the sample, and the Collegiate Word Ratio of the sample. 
 *
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class CollegiateWordRatio {
	
	private static String[] collegiateWords = new String[5014];
	private static ArrayList<String> sampleWords = new ArrayList<String>();
	//private final static String LIST_LOCATION = "C:\\Users\\bigah\\workspace\\Finding Intelligence from Writing\\src\\List of Words.txt";
	//private final static String SAMPLE_DATA_LOCATION = "C:\\Users\\bigah\\workspace\\Finding Intelligence from Writing\\Sample Data.xlsx";
	
	private final static String LIST_LOCATION = "C:\\Users\\bigah\\Documents\\workspace\\Finding-Intelligence-from-Writing\\src\\List of Words.txt";
	private final static String SAMPLE_DATA_LOCATION = "C:\\Users\\bigah\\Documents\\workspace\\Finding-Intelligence-from-Writing\\Sample Data.xlsx";
	
	private final static int ROW_TO_TEST = 4;
		
	public static void main(String args[]){
		long startTime = System.currentTimeMillis();
		int collegiateSampleWordsNum = 0;
		double collegiateWordRatio;
		buildWordList();
		//buildSampleList("C:\\Users\\bigah\\workspace\\Finding Intelligence from Writing\\Samples\\"+getSampleLocation(ROW_TO_TEST));

		buildSampleList("C:\\Users\\bigah\\Documents\\workspace\\Finding-Intelligence-from-Writing\\Samples\\"+getSampleLocation(ROW_TO_TEST));
		
		for(int i = 0; i < sampleWords.size(); i++){
			for(int j = 0; j < collegiateWords.length; j++){
				if(sampleWords.get(i).equalsIgnoreCase(collegiateWords[j])){
					collegiateSampleWordsNum++;
				}
			}
		}
		
		int sampleWordsNum = sampleWords.size();
		collegiateWordRatio = (double) collegiateSampleWordsNum / sampleWordsNum;
		
		writeData(ROW_TO_TEST, collegiateSampleWordsNum, collegiateWordRatio);
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Total Run Time: " + totalTime + " ms");
		
	}
	
	//uses the list of collegiate words to build an array of strings to compare our sample list with 
	private static void buildWordList(){
		try {
			@SuppressWarnings("resource")
			Scanner inScanner = new Scanner(new FileReader(LIST_LOCATION));
			for(int i = 0; i < collegiateWords.length ; i++){
				collegiateWords[i] = inScanner.nextLine();
			}
			
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
		}	
		System.out.println("Word List Built.");
	}
	
	//uses file location from getSampleLocation to build arraylist of words from the sample
	//removes all punctuation 
	public static void buildSampleList(String fileLocation){
		try {
			@SuppressWarnings("resource")
			Scanner inScanner = new Scanner(new FileReader(fileLocation));
			while(inScanner.hasNext()){
				sampleWords.add(inScanner.next().replaceAll("[^a-zA-Z ]", ""));
			}
			
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
		}	
		System.out.println("Sample List Built.");
	}
	
	//Method to get the location of the sample from the data spreadsheet 
	public static String getSampleLocation(int rowNum){
		FileInputStream file = null;
		Workbook wb = null;
		try {
			file = new FileInputStream(new File(SAMPLE_DATA_LOCATION));
			try {
				wb = new  XSSFWorkbook(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		Sheet sheet1 = wb.getSheetAt(0);
		//this cell refers to the value from column B in that row
		Cell cell = sheet1.getRow(rowNum).getCell(1);
		return cell.getRichStringCellValue().getString();
	}
	
	//Write important data to the spreadsheet 
	public static void writeData(int rowNum, int collegiateSampleWordsNum, double collegiateWordRatio){
		//Setup sheet to write to
		
		InputStream inp = null;
		try {
			inp = new FileInputStream(SAMPLE_DATA_LOCATION);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(inp);
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    Sheet sheet = wb.getSheetAt(0);
	    
	    //write word count of sample to sample data
	    Row row = sheet.getRow(rowNum);
	    Cell cell = row.getCell(4);
	    if (cell == null)
	        cell = row.createCell(4);
	    cell.setCellValue(sampleWords.size());
	    
	    //write # of collegiate words in sample to sample data
	    cell = row.getCell(5);
	    if(cell == null)
	    	cell = row.createCell(5);
	    cell.setCellValue(collegiateSampleWordsNum);
	    
	    //write ratio to sample data
	    cell = row.getCell(6);
	    if(cell == null)
	    	cell = row.createCell(6);
	    cell.setCellValue(collegiateWordRatio);
	    
	    // Write the output to a file
	    FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(SAMPLE_DATA_LOCATION);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    try {
			wb.write(fileOut);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    try {
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	    System.out.println("Sample Data Written.");
	}
	
}
