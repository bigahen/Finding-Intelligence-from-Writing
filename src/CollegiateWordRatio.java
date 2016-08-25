import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CollegiateWordRatio {
	private static String[] collegiateWords = new String[5014];
	private static ArrayList<String> sampleWords = new ArrayList<String>();
	private final static String LIST_LOCATION = "C:\\Users\\bigah\\workspace\\Finding Intelligence from Writing\\src\\List of Words.txt";
	private final static String SAMPLE_DATA_LOCATION = "C:\\Users\\bigah\\workspace\\Finding Intelligence from Writing\\Sample Data.xlsx";
	private final static int ROW_TO_TEST = 1;
		
	public static void main(String args[]){
		int collegiateSampleWordsNum = 0;
		double collegiateWordRatio;
		buildWordList();
		buildSampleList("C:\\Users\\bigah\\workspace\\Finding Intelligence from Writing\\Samples\\"+getSampleLocation(ROW_TO_TEST));

		for(int i = 0; i < sampleWords.size(); i++){
			for(int j = 0; j < collegiateWords.length; j++){
				if(sampleWords.get(i).equalsIgnoreCase(collegiateWords[j])){
					collegiateSampleWordsNum++;
				}
			}
		}
		int sampleWordsNum = sampleWords.size();
		collegiateWordRatio = (double) collegiateSampleWordsNum / sampleWordsNum;
		System.out.println(collegiateWordRatio);
		
	}
	
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
	}
	
	
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
		
		
	}
	
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
		//this cell refers to the value from column A in that row
		Cell cell = sheet1.getRow(rowNum).getCell(0);
		
		return cell.getRichStringCellValue().getString();
		
	}
	
}
