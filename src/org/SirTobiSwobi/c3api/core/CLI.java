package org.SirTobiSwobi.c3api.core;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.SirTobiSwobi.c3api.io.Connection;
import org.SirTobiSwobi.c3api.io.FileSystem;

public class CLI {
	private Connection conn;

	public CLI(Connection conn) {
		super();
		this.conn = conn;
	}

	public static String sanitizeText(String text){
		text = text.replaceAll("<.*?>",  ""); //removes any xml tags
		text = text.replaceAll("[^a-zA-Z0-9]", " ").toLowerCase();
		text = text.replaceAll("\\s"," ");
		text = text.replaceAll("\\s{2,}", " ").trim();
		text = text.toLowerCase();
		return text;
	}
	
	public static void printUsage(){
		System.out.println("Usage: java c3-cli.jar http://exampleservice.com:8080 CMD");
		System.out.println("Available commands (CMD): ");
		System.out.println("upload");
		System.out.println("download");
		System.out.println("delete");
		System.out.println("train");	
		System.out.println("categorize");
		System.out.println("version");
		System.out.println("help");
	}
	
	public static void printUploadUsage(){
		System.out.println("Usage: java c3-cli.jar http://exampleservice.com:8080 upload TYPE");
		System.out.println("Available types (TYPE): ");
		System.out.println("document");
		System.out.println("documentWithCategory");
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
		System.out.println("configurations (sets the configuration to train models for the classifier trainer)");
		System.out.println("model (sets the model of the classifier athlete)");
	}
	
	public static void printUploadDirectoryUsage(){
		System.out.println("Usage 1: java c3-cli.jar http://exampleservice.com:8080 upload directory PATH");
		System.out.println("Available parameters: ");
		System.out.println("PATH: Absolute path to the directory containing multiple documents");
	}
	
	public void uploadDirectory(String[] args) throws UnsupportedEncodingException, IOException{
		if(args.length<=3){
			printUploadDirectoryUsage();
		}else{
			ArrayList<File> files = FileSystem.getAllFiles(args[3]);
			for(int i=0;i<files.size();i++){
				String label = files.get(i).getName();
				String content = FileSystem.readFile(files.get(i).getAbsolutePath());
				content = sanitizeText(content);
				conn.uploadDocument(i, label, content);
			}
		}
		
	}
	
	public static void printUploadConfigurationsUsage(){
		System.out.println("Usage 1: java c3-cli.jar http://exampleservice.com:8080 upload configuration PATH");
		System.out.println("Available parameters: ");
		System.out.println("PATH: Absolute path to the JSON file containing the configurations");
	}
	
	public void uploadConfigurations(String[] args) throws UnsupportedEncodingException, IOException{
		if(args.length<=3){
			printUploadConfigurationsUsage();
		}else{
			String path = args[3];
			String json = FileSystem.readFile(path);
			conn.uploadJson(json, "/configurations");
		}
	}
	
	public static void printUploadModelUsage(){
		System.out.println("Usage 1: java c3-cli.jar http://exampleservice.com:8080 upload model PATH");
		System.out.println("Available parameters: ");
		System.out.println("PATH: Absolute path to the JSON file containing the model");
	}
	
	public void uploadModel(String[] args) throws UnsupportedEncodingException, IOException{
		if(args.length<=3){
			printUploadModelUsage();
		}else{
			String path = args[3];
			String json = FileSystem.readFile(path);
			conn.uploadJson(json, "/model");
		}
	}
	
	public static void printUploadCategoriesUsage(){
		System.out.println("Usage 1: java c3-cli.jar http://exampleservice.com:8080 upload categories PATH");
		System.out.println("Available parameters: ");
		System.out.println("PATH: Absolute path to the JSON file containing multiple categories");
	}
	
	public void uploadCategories(String[] args) throws UnsupportedEncodingException, IOException{
		if(args.length<=3){
			printUploadCategoriesUsage();
		}else{
			String path = args[3];
			String json = FileSystem.readFile(path);
			conn.uploadJson(json, "/categories");
		}
	}
	
	public static void printUploadRelationshipsUsage(){
		System.out.println("Usage 1: java c3-cli.jar http://exampleservice.com:8080 upload relationships PATH");
		System.out.println("Available parameters: ");
		System.out.println("PATH: Absolute path to the JSON file containing the relationships.");
	}
	
	public void uploadRelationships(String[] args) throws UnsupportedEncodingException, IOException{
		if(args.length<=3){
			printUploadCategoriesUsage();
		}else{
			String path = args[3];
			String json = FileSystem.readFile(path);
			conn.uploadJson(json, "/relationships");
		}
	}
	
	public static void printUploadTargetfunctionUsage(){
		System.out.println("Usage 1: java c3-cli.jar http://exampleservice.com:8080 upload targetfunction PATH");
		System.out.println("Available parameters: ");
		System.out.println("PATH: Absolute path to the JSON file containing the targetfunction.");
	}
	
	public void uploadTargetfunction(String[] args) throws UnsupportedEncodingException, IOException{
		if(args.length<=3){
			printUploadCategoriesUsage();
		}else{
			String path = args[3];
			String json = FileSystem.readFile(path);
			conn.uploadJson(json, "/targetfunction");
		}
	}
	
	public static void printUploadDocumentsUsage(){
		System.out.println("Usage 1: java c3-cli.jar http://exampleservice.com:8080 upload documents PATH");
		System.out.println("Available parameters: ");
		System.out.println("PATH: Absolute path to the JSON file specifying multiple documents");
	}
	
	public void uploadDocuments(String[] args) throws UnsupportedEncodingException, IOException{
		if(args.length<=3){
			printUploadDocumentsUsage();
		}else{
			String path = args[3];
			String json = FileSystem.readFile(path);
			conn.uploadJson(json, "/documents");
		}
	}
	
	public static void printUploadAssignmentUsage(){
		System.out.println("Usage 1: java c3-cli.jar http://exampleservice.com:8080 upload assignment -i ID -d DOCUMENT -c CATEGORY");
		System.out.println("Available parameters: ");
		System.out.println("ID: The ID of the assignment. If not specified, the microservice generates it automatically (usage 2)");
		System.out.println("DOCUMENT: The ID of the document that should be assigned.");
		System.out.println("CATEGORY: The ID of the category to which the document should be assigned.");
	}
	
	public void uploadAssignment(String[] args) throws UnsupportedEncodingException, IOException{
		if(args.length<=3){
			printUploadAssignmentUsage();
		}else{
			int id=-1;
			int doc=-1;
			int cat=-1;
			int idIndex=this.getIndexOf(args, "-i");
			int docIndex=this.getIndexOf(args, "-d");
			int catIndex=this.getIndexOf(args, "-c");	
			if(idIndex!=-1){
				id = Integer.parseInt(args[idIndex+1]);
			}
			if(docIndex!=-1){
				doc = Integer.parseInt(args[docIndex+1]);
			}
			if(catIndex!=-1){
				cat = Integer.parseInt(args[catIndex+1]);
			}
			if(doc!=-1&&cat!=-1){
				conn.uploadAssignment(id, doc, cat);
			}
		}
	}
	
	
	public static void printUploadRelationshipUsage(){
		System.out.println("Usage 1: java c3-cli.jar http://exampleservice.com:8080 upload relationship -i ID -f FROM -t TO -ty TYPE");
		System.out.println("Usage 2: java c3-cli.jar http://exampleservice.com:8080 upload relationship -f FROM -t TO -ty TYPE");
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
	
	public static void printUploadDocumentWithCategoryUsage(){
		System.out.println("Usage 1: java c3-cli.jar http://exampleservice.com:8080 upload documentWithCategory -i ID -l LABEL -c CONTENT -ca CATEGORY");
		System.out.println("Usage 2: java c3-cli.jar http://exampleservice.com:8080 upload documentWithCategory -i ID -l LABEL -u URL -ca CATEGORY");
		System.out.println("Usage 3: java c3-cli.jar http://exampleservice.com:8080 upload documentWithCategory -i ID -u URL -ca CATEGORY");
		System.out.println("Available parameters: ");
		System.out.println("ID: The ID of the document.  Must be specified to assign a category.");
		System.out.println("LABEL: The label or title of the document. Can be left out.");
		System.out.println("CONTENT: The content of the document.");
		System.out.println("URL: If specified, the microservice downloads the document from the specified source.");
		System.out.println("CATEGORY: The ID of the category to which this document should be assigned.");
	}
	
	public void uploadDocumentWithCategory(String[] args) throws UnsupportedEncodingException, IOException{
		if(args.length<=3){
			printUploadDocumentWithCategoryUsage();
		}else{
			int id=-1;
			String label="";
			String content="";
			String url="";	
			int cat=-1;
			int idIndex=this.getIndexOf(args, "-i");
			int labelIndex=this.getIndexOf(args, "-l");
			int contentIndex=this.getIndexOf(args, "-c");
			int urlIndex=this.getIndexOf(args, "-u");	
			int catIndex=this.getIndexOf(args, "-ca");
			if(idIndex!=-1){
				id = Integer.parseInt(args[idIndex+1]);
			}
			if(labelIndex!=-1){
				int i=labelIndex+1;
				while(i<args.length&&!(args[i].equals("-i")||args[i].equals("-c")||args[i].equals("-u")||args[i].equals("-ca"))){
					label+=args[i]+" ";
					i++;
				}
			}
			if(contentIndex!=-1){
				int i=contentIndex+1;
				while(i<args.length&&!(args[i].equals("-i")||args[i].equals("-l")||args[i].equals("-u")||args[i].equals("-ca"))){
					content+=args[i]+" ";
					i++;
				}
			}
			if(urlIndex!=-1){
				int i=urlIndex+1;
				while(i<args.length&&!(args[i].equals("-i")||args[i].equals("-l")||args[i].equals("-c")||args[i].equals("-ca"))){
					url+=args[i]+" ";
					i++;
				}
			}
			if(id!=-1){
				conn.uploadDocument(id, label, content, url);
				if(catIndex!=-1){
					cat = Integer.parseInt(args[catIndex+1]);
					conn.uploadAssignment(id, cat);
				}
			}else{
				printUploadDocumentWithCategoryUsage();
			}		
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
				uploadAssignment(args);
			}else if(args[2].equals("documentWithCategory")){
				uploadDocumentWithCategory(args);
			}else if(args[2].equals("documents")){
				uploadDocuments(args);
			}else if(args[2].equals("directory")){
				uploadDirectory(args);
			}else if(args[2].equals("categories")){
				uploadCategories(args);
			}else if(args[2].equals("relationships")){
				uploadRelationships(args);
			}else if(args[2].equals("targetfunction")){
				uploadTargetfunction(args);
			}else if(args[2].equals("configurations")){
				uploadConfigurations(args);
			}else if(args[2].equals("model")){
				uploadModel(args);
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

	public static void printDownloadUsage(){
		System.out.println("Usage: java c3-cli.jar http://exampleservice.com:8080 download RESOURCE PATH");
		System.out.println("Available Parameters: ");
		System.out.println("RESOURCE: What to download from the server. For example /documents/1");
		System.out.println("PATH: Absolute path where to store the resource");
		System.out.println("Downloadable ressource (and their sub-entries): /documents, /categories, /relationships, /configurations, /targetfunction, /models, /model");
	}
	
	public void downloadResource(String[] args) throws IOException{
		if(args.length<=2){
			printDownloadUsage();
		}else{
			String json = conn.getJSON(args[2]);
			FileSystem.storeString(args[3], json);
		}
	}
	
	public static void printDeleteUsage(){
		System.out.println("Usage: java c3-cli.jar http://exampleservice.com:8080 delete RESOURCE");
		System.out.println("Available Parameters: ");
		System.out.println("RESOURCE: What to delete from the server. For example /documents/1");
		System.out.println("Deletable ressource (and their sub-entries): /documents, /categories, /relationships, /configurations, /targetfunction, /models, /model");
	}
	
	public void deleteResource(String[] args) throws IOException{
		if(args.length<=2){
			printDeleteUsage();
		}else{
			System.out.println("Deleting "+args[2]);
			conn.delete(args[2]);
		}
	}
	
	public static void printTrainUsage(){
		System.out.println("Usage: java c3-cli.jar http://exampleservice.com:8080 train CONFIG");
		System.out.println("Available Parameters: ");
		System.out.println("CONFIG: The ID of the configuration that should be used for training.");
	}
	
	public void train(String[] args) throws IOException{
		if(args.length<=2){
			printTrainUsage();
		}else{
			System.out.println("Training a new model using configuration "+args[2]+".");
			conn.triggerTraining(Integer.parseInt(args[2]));
		}
	}
	
	public static void printCategorizeUsage(){
		System.out.println("Usage 1: java c3-cli.jar http://exampleservice.com:8080 categorize existing ID");
		System.out.println("Usage 2: java c3-cli.jar http://exampleservice.com:8080 categorize new -i ID -l LABEL -c CONTENT");
		System.out.println("Usage 3: java c3-cli.jar http://exampleservice.com:8080 categorize new -l LABEL -c CONTENT");
		System.out.println("Usage 4: java c3-cli.jar http://exampleservice.com:8080 categorize new -i ID -l LABEL -u URL");
		System.out.println("Usage 5: java c3-cli.jar http://exampleservice.com:8080 categorize new -i ID -u URL");
		System.out.println("Usage 6: java c3-cli.jar http://exampleservice.com:8080 categorize new -u URL");
		System.out.println("Available parameters: ");
		System.out.println("ID: The ID of the document to be categorized. If not specified, the microservice generates it automatically (usages 2 and 6)");
		System.out.println("LABEL: The label or title of the document. Can be left out.");
		System.out.println("CONTENT: The content of the document.");
		System.out.println("URL: If specified, the microservice downloads the document from the specified source.");
	}
	
	public void categorize(String[] args) throws UnsupportedEncodingException, IOException{
		if(args.length<=3){
			printCategorizeUsage();
		}else{
			int id=-1;
			
			if(args[2].equals("existing")){
				id=Integer.parseInt(args[3]);
				System.out.println("Categorizing existing document "+id);
				conn.categorizeExisting(id);
			}else if(args[2].equals("new")){
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
				conn.categorizeNewDocument(id, label, content, url);
				
			}else{
				printCategorizeUsage();
			}
		}
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
					cli.downloadResource(args);
				}else if(args[1].equals("delete")){
					cli.deleteResource(args);
				}else if(args[1].equals("train")){
					cli.train(args);
				}else if(args[1].equals("categorize")){
					cli.categorize(args);
				}else if(args[1].equals("version")){
					System.out.println("This CLI is compatible with the classifier trainer and athlete API v.1.0.7");
				}else if(args[1].equals("help")){
					CLI.printUsage();
					CLI.printUploadUsage();
					CLI.printUploadDocumentUsage();
					CLI.printUploadDocumentWithCategoryUsage();
					CLI.printUploadCategoryUsage();
					CLI.printUploadRelationshipUsage();
					CLI.printUploadAssignmentUsage();
					CLI.printUploadDocumentsUsage();
					CLI.printUploadDirectoryUsage();
					CLI.printUploadCategoriesUsage();
					CLI.printUploadRelationshipsUsage();
					CLI.printUploadTargetfunctionUsage();
					CLI.printUploadConfigurationsUsage();
					CLI.printUploadModelUsage();
					CLI.printDownloadUsage();
					CLI.printDeleteUsage();
					CLI.printTrainUsage();
					CLI.printCategorizeUsage();
				}else {
					printUsage();
				}
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
