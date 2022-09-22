package com.my.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.sql.MyConnection;

public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		//parameter
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String addr = request.getParameter("addr");
		String buildingno = request.getParameter("buildingno");

		System.out.println("id : " + id + ", pwd : " + pwd + ", name : " + name + ", addr : " + addr +
				", buildingno : " + buildingno);
		
		String result = "{\"status\": 0, \"message\": \"Sign up failure\"}";
		
		//db와 연결
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = MyConnection.getConnection();
			String insertSQL = "INSERT INTO customer(id, password, name, address, status, buildingno) VALUES(?,?,?,?,1,?)";
			pstmt = con.prepareStatement(insertSQL);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, addr);
			pstmt.setString(5, buildingno);
			pstmt.executeUpdate();
			result = "{\"status\": 1, \"message\": \"Sign up success\"}";		
		} catch (SQLException e) {
			System.out.println("SQL EXCEPTION");
		} finally {
			MyConnection.close(pstmt, con);
		}
		
		// 가입 성공여부 출력 {"status":1 - 성공, 2 - 실패)
		System.out.println(result);
		out.print(result);
	}

}