package com.hadjali.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Define datasource/connetion pool for resource injection
	@Resource(name = "jdbc/web_student_tracker")
	private DataSource dataSource;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// step1: set up the printwriter
		PrintWriter out = response.getWriter(); // to send data back to the browser
		response.setContentType("text/plain");
		// step2: get a connection to the database
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		try {
			myConn = dataSource.getConnection();
			// step3: Create a SQL statements
			String sql = "select * from student";
			myStmt = myConn.createStatement();
			// step4: Exectute SQL query
			myRs = myStmt.executeQuery(sql);
			// step5: Process the result set
			while (myRs.next()) {
				String email = myRs.getString("email");
				out.println(email);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

}
