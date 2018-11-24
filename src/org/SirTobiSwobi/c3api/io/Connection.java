package org.SirTobiSwobi.c3api.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Connection {
	private String endpoint;

	public Connection(String endpoint) {
		super();
		this.endpoint = endpoint;
	}
	
	public String postJSON(String json, URL url) throws UnsupportedEncodingException, IOException{
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setUseCaches(false);
		connection.setDoOutput(true);
		connection.setDoInput(true);
		OutputStream os = connection.getOutputStream();
		os.write(json.getBytes("UTF-8"));
		os.close();
		InputStream in = new BufferedInputStream(connection.getInputStream());
		
		BufferedInputStream bis = new BufferedInputStream(in);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();
		while(result != -1){
			buf.write((byte) result);
			result = bis.read();
		}
		
		String output = buf.toString("UTF-8");
		
		in.close();
		connection.disconnect();
		
		return output;
	}
	
	public void uploadJson(String json, String path) throws UnsupportedEncodingException, IOException{
		URL url = new URL(endpoint+path);
		postJSON(json,url);	
	}
	
	public void putJSON(String json, URL url) throws UnsupportedEncodingException, IOException{
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("PUT");
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setUseCaches(false);
		connection.setDoOutput(true);
		connection.setDoInput(true);
		OutputStream os = connection.getOutputStream();
		os.write(json.getBytes("UTF-8"));
		os.close();
		connection.disconnect();
	}
	
	public void delete(String path)throws UnsupportedEncodingException, IOException{
		URL url = new URL(endpoint+path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("DELETE");
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setUseCaches(false);
		connection.setDoOutput(true);
		int resp = connection.getResponseCode();
		if(resp!=200){
			throw new IOException("Unable to delete on server");
		}else{
			System.out.println("Deleted "+path);
		}
	}
	
	public String getJSON(String path) throws IOException{
		URL url = new URL(endpoint+path);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		connection.setUseCaches(false);
		connection.setDoOutput(true);
		connection.setDoInput(true);			
		InputStream in = new BufferedInputStream(connection.getInputStream());
		
		BufferedInputStream bis = new BufferedInputStream(in);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();
		while(result != -1){
			buf.write((byte) result);
			result = bis.read();
		}
		
		String output = buf.toString("UTF-8");
		
		in.close();
		connection.disconnect();
		
		return output;
	}
	
	public void uploadCategory(String title) throws IOException{
		String json="";
		json+="{ \"categories\":[";
		json+="\t{";
		json+="\t\t\"id\":-1,";
		json+="\t\t\"label\":\""+title+"\",";
		json+="\t\t\"description\":\"\"";
		json+="\t}";			
		json+="]}";
		System.out.println(json);
		URL url = new URL(endpoint+"/categories");
		postJSON(json,url);	
		System.out.println("transefering cat "+title+" to the server "+url.toString());
	}
	
	public void uploadCategory(int id, String title) throws IOException{
		String json="";
		json+="{ \"categories\":[";
		json+="\t{";
		json+="\t\t\"id\":"+id+",";
		json+="\t\t\"label\":\""+title+"\",";
		json+="\t\t\"description\":\"\"";
		json+="\t}";			
		json+="]}";
		System.out.println(json);
		URL url = new URL(endpoint+"/categories");
		postJSON(json,url);	
		System.out.println("transefering cat "+id+" to the server "+url.toString());
	}
	
	public void uploadCategory(int id, String title, String description) throws IOException{
		String json="";
		json+="{ \"categories\":[";
		json+="\t{";
		json+="\t\t\"id\":"+id+",";
		json+="\t\t\"label\":\""+title+"\",";
		json+="\t\t\"description\":\""+description+"\"";
		json+="\t}";			
		json+="]}";
		System.out.println(json);
		URL url = new URL(endpoint+"/categories");
		postJSON(json,url);	
		System.out.println("transefering cat "+id+" to the server "+url.toString());
	}
	
	public void uploadDocument(String label, String content) throws UnsupportedEncodingException, IOException{
		content=content.replace("\"", "\\\"");
		String json="{ \"documents\":[";
		json+="\t{";
		json+="\t\t\"id\":-1,";
		json+="\t\t\"label\":\""+label+"\",";
		json+="\t\t\"content\":\""+content+"\",";
		json+="\t\t\"url\":\"\"";
		json+="\t}";			
		json+="]}";
		System.out.println(json);
		URL url = new URL(endpoint+"/documents");
		postJSON(json,url);	
		System.out.println("uploading document "+label);
	}
	
	public void uploadDocument(int id, String label, String content) throws UnsupportedEncodingException, IOException{
		content=content.replace("\"", "\\\"");
		String json="{ \"documents\":[";
		json+="\t{";
		json+="\t\t\"id\":"+id+",";
		json+="\t\t\"label\":\""+label+"\",";
		json+="\t\t\"content\":\""+content+"\",";
		json+="\t\t\"url\":\"\"";
		json+="\t}";			
		json+="]}";
		System.out.println(json);
		URL url = new URL(endpoint+"/documents");
		postJSON(json,url);	
		System.out.println("uploading document "+id);
	}
	
	public void uploadDocument(int id, String label, String content, String docUrl) throws UnsupportedEncodingException, IOException{
		content=content.replace("\"", "\\\"");
		String json="{ \"documents\":[";
		json+="\t{";
		json+="\t\t\"id\":"+id+",";
		json+="\t\t\"label\":\""+label+"\",";
		json+="\t\t\"content\":\""+content+"\",";
		json+="\t\t\"url\":\""+docUrl+"\"";
		json+="\t}";			
		json+="]}";
		System.out.println(json);
		URL url = new URL(endpoint+"/documents");
		postJSON(json,url);	
		System.out.println("uploading document "+id);
	}
	
	public void loadDocumentFromUrl(String url) throws UnsupportedEncodingException, IOException{
		String json="{ \"documents\":[";
		json+="\t{";
		json+="\t\t\"id\":-1,";
		json+="\t\t\"label\":\"\",";
		json+="\t\t\"content\":\"\",";
		json+="\t\t\"url\":\""+url+"\"";
		json+="\t}";			
		json+="]}";
		System.out.println(json);
		URL serverUrl = new URL(endpoint+"/documents");
		postJSON(json,serverUrl);	
		System.out.println("instructing "+serverUrl+" to load document from "+url);
	}
	
	public void uploadSubRelationship(int fromId, int toId) throws UnsupportedEncodingException, IOException{
		uploadSubRelationship(-1,fromId,toId);
	}
	
	public void uploadEqualityRelationship(int fromId, int toId) throws UnsupportedEncodingException, IOException{
		uploadEqualityRelationship(-1, fromId, toId);
	}
	
	public void uploadSubRelationship(int id, int fromId, int toId) throws UnsupportedEncodingException, IOException{
		String json="";
		json+="{ \"relationships\":[";
		json+="\t{";
		json+="\t\t\"id\":"+id+",";
		json+="\t\t\"fromId\":"+fromId+",";
		json+="\t\t\"toId\":"+toId+",";
		json+="\t\t\"type\":\"Sub\"";
		json+="\t}";			
		json+="]}";
		System.out.println(json);
		URL url = new URL(endpoint+"/relationships");
		postJSON(json,url);	
		System.out.println("transefering relationship "+fromId+"/"+toId+" to the server "+url.toString());
	}
	
	public void uploadEqualityRelationship(int id, int fromId, int toId) throws UnsupportedEncodingException, IOException{
		String json="";
		json+="{ \"relationships\":[";
		json+="\t{";
		json+="\t\t\"id\":"+id+",";
		json+="\t\t\"fromId\":"+fromId+",";
		json+="\t\t\"toId\":"+toId+",";
		json+="\t\t\"type\":\"Equality\"";
		json+="\t}";			
		json+="]}";
		System.out.println(json);
		URL url = new URL(endpoint+"/relationships");
		postJSON(json,url);	
		System.out.println("transefering relationship "+fromId+"/"+toId+" to the server "+url.toString());
	}
	
	public void uploadAssignment(int docId, int catId) throws UnsupportedEncodingException, IOException{
		uploadAssignment(-1,docId,catId);
	}
	
	public void uploadAssignment(int id, int docId, int catId) throws UnsupportedEncodingException, IOException{
		String json="{ \"assignments\":[";
		json+="\t{";
		json+="\t\t\"id\":"+id+",";
		json+="\t\t\"documentId\":"+docId+",";
		json+="\t\t\"categoryId\":"+catId+"";
		json+="\t}";			
		json+="]}";
		System.out.println(json);
		URL url = new URL(endpoint+"/targetfunction");
		postJSON(json, url);
	}
	
	public void uploadModel(String modelLocation) throws IOException{
		BufferedReader br= new BufferedReader(new FileReader(modelLocation));
		String line;
		String json="";
		while((line = br.readLine())!=null){
			json+=line;
		}
		br.close();
		URL url = new URL(endpoint+"/model");
		postJSON(json, url);
	}
	
	public void uploadConfig(String configLocation) throws IOException{
		BufferedReader br= new BufferedReader(new FileReader(configLocation));
		String line;
		String json="";
		while((line = br.readLine())!=null){
			json+=line;
		}
		br.close();
		URL url = new URL(endpoint+"/configurations");
		postJSON(json, url);
	}
	
	public void triggerTraining(int confId) throws IOException{
		URL url = new URL(endpoint+"/models?confId="+confId);
		postJSON("",url);
	}
	
	public void categorizeExisting(int docId) throws IOException{
		URL url = new URL(endpoint+"/categorizations/existing/"+docId);
		postJSON("",url);
	}
	
	public void categorizeNewDocument(int id, String label, String content, String docUrl) throws UnsupportedEncodingException, IOException{
		content=content.replace("\"", "\\\"");
		label = label.replace("\"", "\\\"");
		String json="{";
		json+="\t\"id\":"+id+",";
		json+="\t\"label\":\""+label+"\",";
		json+="\t\"content\":\""+content+"\",";
		json+="\t\"url\":\""+docUrl+"\"";
		json+="}";
		System.out.println(json);
		URL url = new URL(endpoint+"/categorizations");
		postJSON(json,url);	
		System.out.println("categorizing new document "+id);
	}
	
}
