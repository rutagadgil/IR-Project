package neu.informationretrieval.project.run1.bm25;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class IO_Operations {
	FileReader fr;
	BufferedReader br;
	String content = "";
	
	FileWriter fw;
	BufferedWriter bw;
	
	public String readFile(File file){
		String line = null;
		StringBuilder sb = new StringBuilder();
		
		try{
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			line = br.readLine();
			
			while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
			content = sb.toString();
			br.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return content;
	}
	public void writeToFile(String path, String filename, String content) {
		// TODO Auto-generated method stub
		
		try{
			File file = new File(path + filename + ".txt");
			System.out.println(file.getName());
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
