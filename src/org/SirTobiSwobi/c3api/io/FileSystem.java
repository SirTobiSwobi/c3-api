package org.SirTobiSwobi.c3api.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileSystem {
	public ArrayList<File> getAllFiles(String directory){
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
	
	public void listf(String directoryName, ArrayList<File> files) {
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
	
	public void storeString(String file, String content){
		try{
			PrintWriter writer = new PrintWriter(new FileOutputStream(file, false));
			writer.println(content);
			writer.close();
		}catch (Exception e){
			System.out.println("Unable to save "+file);
			System.out.println(e.getMessage());
		}
	}
}
