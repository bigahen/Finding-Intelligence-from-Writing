import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TrimList {
	public static void main(String args[]){
		ArrayList<String> names = new ArrayList<String>();		
		
		try {
			
			@SuppressWarnings("resource")
			Scanner inScanner = new Scanner(new FileReader("C:\\Users\\bigah\\workspace\\Finding Intelligence from Writing\\src\\List of Words.txt"));
			while(inScanner.hasNextLine()){
				names.add(inScanner.nextLine());
			}
			
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
		}
		
		BufferedWriter writer = null;
        try {
            //create a temporary file
            String fileString = "C:\\Users\\bigah\\workspace\\Finding Intelligence from Writing\\src\\List of Words 2.txt"; 
            File file = new File(fileString);

            // This will output the full path where the file will be written to...
            System.out.println(file.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(file));
            
            for(int i = 0; i < names.size(); i++){
            	int index = names.get(i).indexOf(' ');
                writer.write(names.get(i).substring(0, index));
                writer.newLine();
            }
          
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
            
		
		
        }
        
	
	}
}
