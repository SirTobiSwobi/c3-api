package org.SirTobiSwobi.c3api.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileSystem {
	public static ArrayList<File> getAllFiles(String directory){
		File documentDirectory = new File(directory);
		ArrayList<File> documents = new ArrayList<File>();
		File[] fList = documentDirectory.listFiles();
		    for (File file : fList) {
		        if (file.isFile()) {
		            documents.add(file);
		        } else if (file.isDirectory()) {
		            listf(file.getAbsolutePath(), documents);
		        }
		    }
		
		return documents;
	}
	
	public static void listf(String directoryName, ArrayList<File> files) {
	    File directory = new File(directoryName);
	    File[] fList = directory.listFiles();
	    for (File file : fList) {
	        if (file.isFile()) {
	            files.add(file);
	        } else if (file.isDirectory()) {
	            listf(file.getAbsolutePath(), files);
	        }
	    }
	}
	
	public static void storeString(String file, String content){
		try{
			PrintWriter writer = new PrintWriter(new FileOutputStream(file, false));
			writer.println(content);
			writer.close();
		}catch (Exception e){
			System.out.println("Unable to save "+file);
			System.out.println(e.getMessage());
		}
	}
	
	public static String readFile(String path) throws IOException{
		BufferedReader br= new BufferedReader(new FileReader(path));
		String line;
		String json="";
		while((line = br.readLine())!=null){
			json+=line;
		}
		br.close();
		return json;
	}
	
	public static String sanitizeText(String text){
		text = text.replaceAll("<.*?>",  ""); //removes any xml tags
		text = text.replaceAll("[^a-zA-Z0-9]", " ").toLowerCase();
		text = text.replaceAll("\\s"," ");
		text = text.replaceAll("\\s{2,}", " ").trim();
		text = text.toLowerCase();
		return text;
	}
}
