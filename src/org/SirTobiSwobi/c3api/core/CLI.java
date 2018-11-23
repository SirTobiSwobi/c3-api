package org.SirTobiSwobi.c3api.core;

import java.io.IOException;

import org.SirTobiSwobi.c3api.io.Connection;

public class CLI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
	}

}
