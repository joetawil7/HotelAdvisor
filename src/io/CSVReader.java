package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {

	public ArrayList<String[]> parseFile(String filename){
		ArrayList<String[]> fileLinesTokens = new ArrayList<String[]>();

		try{	
			BufferedReader bufferReader = new BufferedReader(new FileReader(filename));
			String line = bufferReader.readLine();
			while ((line = bufferReader.readLine()) != null){
					final String[] lineTokens = line.split(",");;
					fileLinesTokens.add(lineTokens);
					
			}
			bufferReader.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return fileLinesTokens;
	}
}
