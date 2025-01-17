package com.uniquedeveloper.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String Uname = request.getParameter("name");
		String Uemail = request.getParameter("email");
		String Upwd = request.getParameter("pass");
		String Umobile = request.getParameter("contact");
		
		RequestDispatcher dispatcher = null;
		Connection con =null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Company?useSSL=false","root","Hari@1709");
			PreparedStatement pst = con.prepareStatement("insert into users(Uname,Upwd,Uemail,Umobile) values(?,?,?,?) ");
			pst.setString(1, Uname);
			pst.setString(2, Upwd);
			pst.setString(3, Uemail);
			pst.setString(4, Umobile);
			
			int rowCount =pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("registration.jsp");
			if(rowCount > 0) {
				request.setAttribute("status", "success");
			}
			else {
				request.setAttribute("status", "failed");
			}
			
			dispatcher.forward(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(con != null) {
			try{
				con.close();
			}catch(SQLException e) {
				e.printStackTrace();		
				}
			}
		}
		
	}

}
