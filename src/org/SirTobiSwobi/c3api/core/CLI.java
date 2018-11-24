package org.SirTobiSwobi.c3api.core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.SirTobiSwobi.c3api.io.Connection;

public class CLI {
	private Connection conn;

	public CLI(Connection conn) {
		super();
		this.conn = conn;
	}

	public static void printUsage(){
		System.out.println("Usage: java c3-cli.jar http://exampleservice.com:8080 CMD");
		System.out.println("Available commands (CMD): ");
		System.out.println("upload");
		System.out.println("download");
		System.out.println("delete");
		System.out.println("set");	
	}
	
	public static void printUploadUsage(){
		System.out.println("Usage: java c3-cli.jar http://exampleservice.com:8080 upload TYPE");
		System.out.println("Available types (TYPE): ");
		System.out.println("document");
		System.out.println("category");
		System.out.println("relationship");
		System.out.println("assignment");
		System.out.println("Alternative upload from filesystem: java c3-cli.jar http://exampleservice.com:8080 upload TYPE PATH");
		System.out.println("Available types (TYPE): ");
		System.out.println("documents");
		System.out.println("directory  (uploads all files in one directory as documents)");
		System.out.println("categories");
		System.out.println("relationships");
		System.out.println("targetfunction");
	}
	
	public static void printUploadRelationshipUsage(){
		System.out.println("Usage 1: java c3-cli.jar http://exampleservice.com:8080 upload relationship -i ID -f FROM -t TO -ty TYPE");
		System.out.println("Usage 1: java c3-cli.jar http://exampleservice.com:8080 upload relationship -f FROM -t TO -ty TYPE");
		System.out.println("Available parameters: ");
		System.out.println("ID: The ID of the relationship. If not specified, the microservice generates it automatically (usage 2)");
		System.out.println("FROM: The ID of the category from which the relationship originates.");
		System.out.println("TO: The ID of the category to which the relationship leads.");
		System.out.println("TYPE: Sub or Equality.");
		System.out.println("Example: Category 1: Science, Category 2: Physics. -> upload relationship -f 2 -t 1 -ty Sub --> Physics is treated as sub-category of Science.");
	}
	
	public void uploadRelationship(String[] args) throws UnsupportedEncodingException, IOException{
		if(args.length<=3){
			printUploadRelationshipUsage();
		}else{
			int id=-1;
			int from=-1;
			int to=-1;
			String type="";
			int idIndex=this.getIndexOf(args, "-i");
			int fromIndex=this.getIndexOf(args, "-f");
			int toIndex=this.getIndexOf(args, "-t");	
			int typeIndex=this.getIndexOf(args, "-ty");
			if(idIndex!=-1){
				id = Integer.parseInt(args[idIndex+1]);
			}
			if(fromIndex!=-1){
				from = Integer.parseInt(args[fromIndex+1]);
			}
			if(toIndex!=-1){
				to = Integer.parseInt(args[toIndex+1]);
			}
			if(typeIndex!=-1){
				int i=typeIndex+1;
				while(i<args.length&&!(args[i].equals("-i")||args[i].equals("-f")||args[i].equals("-t"))){
					type+=args[i];
					i++;
				}
			}
			if(from!=-1&&to!=-1&&type.equals("Sub")){
				conn.uploadSubRelationship(id, from, to);
			}else if(from!=-1&&to!=-1&&type.equals("Equality")){
				conn.uploadEqualityRelationship(id, from, to);
			}else{
				printUploadRelationshipUsage();
			}
		}
	}
	
	public static void printUploadCategoryUsage(){
		System.out.println("Usage 1: java c3-cli.jar http://exampleservice.com:8080 upload category -i ID -l LABEL -d DESCRIPTION");
		System.out.println("Usage 2: java c3-cli.jar http://exampleservice.com:8080 upload category -i ID -l LABEL");
		System.out.println("Usage 3: java c3-cli.jar http://exampleservice.com:8080 upload category -l LABEL -d DESCRIPTION");
		System.out.println("Usage 4: java c3-cli.jar http://exampleservice.com:8080 upload category -l LABEL");
		System.out.println("Available parameters: ");
		System.out.println("ID: The ID of the category. If not specified, the microservice generates it automatically (usages 1 and 2)");
		System.out.println("LABEL: The label of the category. Cannot be left out.");
		System.out.println("DESCRIPTION: An optional description of the category (usages 1 and 3).");
	}
	
	public void uploadCategory(String[] args) throws UnsupportedEncodingException, IOException{
		if(args.length<=3){
			printUploadCategoryUsage();
		}else{
			int id=-1;
			String label="";
			String description="";
			int idIndex=this.getIndexOf(args, "-i");
			int labelIndex=this.getIndexOf(args, "-l");
			int descriptionIndex=this.getIndexOf(args, "-d");		
			if(idIndex!=-1){
				id = Integer.parseInt(args[idIndex+1]);
			}
			if(labelIndex!=-1){
				int i=labelIndex+1;
				while(i<args.length&&!(args[i].equals("-i")||args[i].equals("-d")||args[i].equals("-u"))){
					label+=args[i]+" ";
					i++;
				}
			}
			if(descriptionIndex!=-1){
				int i=descriptionIndex+1;
				while(i<args.length&&!(args[i].equals("-i")||args[i].equals("-l")||args[i].equals("-u"))){
					description+=args[i]+" ";
					i++;
				}
			}
			conn.uploadCategory(id, label, description);
		}
	}
	
	public static void printUploadDocumentUsage(){
		System.out.println("Usage 1: java c3-cli.jar http://exampleservice.com:8080 upload document -i ID -l LABEL -c CONTENT");
		System.out.println("Usage 2: java c3-cli.jar http://exampleservice.com:8080 upload document -l LABEL -c CONTENT");
		System.out.println("Usage 3: java c3-cli.jar http://exampleservice.com:8080 upload document -i ID -l LABEL -u URL");
		System.out.println("Usage 4: java c3-cli.jar http://exampleservice.com:8080 upload document -i ID -u URL");
		System.out.println("Usage 5: java c3-cli.jar http://exampleservice.com:8080 upload document -u URL");
		System.out.println("Available parameters: ");
		System.out.println("ID: The ID of the document. If not specified, the microservice generates it automatically (usages 1 and 2)");
		System.out.println("LABEL: The label or title of the document. Can be left out.");
		System.out.println("CONTENT: The content of the document.");
		System.out.println("URL: If specified, the microservice downloads the document from the specified source.");
	}
	
	public void uploadDocument(String[] args) throws UnsupportedEncodingException, IOException{
		if(args.length<=3){
			printUploadDocumentUsage();
		}else{
			int id=-1;
			String label="";
			String content="";
			String url="";		
			int idIndex=this.getIndexOf(args, "-i");
			int labelIndex=this.getIndexOf(args, "-l");
			int contentIndex=this.getIndexOf(args, "-c");
			int urlIndex=this.getIndexOf(args, "-u");		
			if(idIndex!=-1){
				id = Integer.parseInt(args[idIndex+1]);
			}
			if(labelIndex!=-1){
				int i=labelIndex+1;
				while(i<args.length&&!(args[i].equals("-i")||args[i].equals("-c")||args[i].equals("-u"))){
					label+=args[i]+" ";
					i++;
				}
			}
			if(contentIndex!=-1){
				int i=contentIndex+1;
				while(i<args.length&&!(args[i].equals("-i")||args[i].equals("-l")||args[i].equals("-u"))){
					content+=args[i]+" ";
					i++;
				}
			}
			if(urlIndex!=-1){
				int i=urlIndex+1;
				while(i<args.length&&!(args[i].equals("-i")||args[i].equals("-l")||args[i].equals("-c"))){
					url+=args[i]+" ";
					i++;
				}
			}
			conn.uploadDocument(id, label, content, url);
		}
	}
	
	public void determineUpload(String[] args) throws UnsupportedEncodingException, IOException{
		if(args.length<=2){
			printUploadUsage();
		}else{
			if(args[2].equals("document")){
				uploadDocument(args);
			}else if(args[2].equals("category")){
				uploadCategory(args);
			}else if(args[2].equals("relationship")){
				uploadRelationship(args);
			}else if(args[2].equals("assignment")){
				
			}else if(args[2].equals("documents")){
				
			}else if(args[2].equals("directory")){
				
			}else if(args[2].equals("categories")){
				
			}else if(args[2].equals("relationships")){
				
			}else if(args[2].equals("targetfunction")){
				
			}else {
				printUploadUsage();
			}
		}
	}
	
	public int getIndexOf(String[] array, String element){
		int index=-1;
		for(int i=0;i<array.length;i++){
			if(array[i].equals(element)){
				index=i;
			}
		}
		return index;
	}

	public static void main(String[] args) {
		try {
			if(args.length<=1||!args[0].startsWith("http")){
				printUsage();
			}else{
				CLI cli = new CLI(new Connection(args[0]));
				if(args[1].equals("upload")){
					cli.determineUpload(args);
				}else if(args[1].equals("download")){
					
				}else if(args[1].equals("delete")){
					
				}else if(args[1].equals("set")){
					
				}else {
					printUsage();
				}
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		Connection conn = new Connection("http://localhost:8082");
		try {
			
			//conn.uploadCategory(5, "test category");
			//System.out.println(conn.getJSON("/categories/5"));
			conn.delete("/categories/5");
			System.out.println(conn.getJSON("/categories"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}

}
