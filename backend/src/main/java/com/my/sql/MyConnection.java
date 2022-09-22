package com.my.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyConnection {
	static { // JDBC �겢�옒�뒪 濡쒕뱶�뒗 �븳踰덈쭔 �븯硫� �뜑 �씠�긽 �븷 �븘�슂媛� �뾾�쑝誘�濡� MyConnection �겢�옒�뒪媛� 濡쒕뱶�맆 �븣 �옄�룞�샇異쒕릺�룄濡� static 釉붾윮�뿉 �꽔�쓬
		// 1. JDBC �뱶�씪�씠踰� �꽕移�
		// -> ojdbc8.jar �떎�슫濡쒕뱶 & build path濡� �뿰寃�
		// 2. JDBC �뱶�씪�씠踰� �겢�옒�뒪 濡쒕뱶
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // Oracle driver 연결
			System.out.println("Driver와 연결되었습니다");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	//db�뿉 connect�븯湲�
	public static Connection getConnection() throws SQLException {
		// 3. DB �뿰寃�
		Connection con = null;
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "hr";
		String password = "hr";
		con = DriverManager.getConnection(url, user, password);
		System.out.println(url + "에 연결");
		return con;
	}
	
	//db���쓽 �뿰寃� �빐�젣
	public static void close(ResultSet rs, Statement stat, Connection con) {
		// 4.. DB ���쓽 �뿰寃� �빐�젣
		if (rs != null) {
			try {
				rs.close(); // ResultSet �떕湲�
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stat != null) {
			try {
				stat.close(); // Statement �떕湲�
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close(); // Connection �떕湲�
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//db���쓽 �뿰寃� �빐�젣
	public static void close(Statement stat, Connection con) {
		// 4. DB ���쓽 �뿰寃� �빐�젣
		close(null, stat, con);
	}
}
