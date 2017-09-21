package MyServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DatabaseHandeling.SQLConnect;
import Graphs.Table;
@SuppressWarnings("serial")
@WebServlet(name="graph",urlPatterns={"/graph"})
public class PostGraphs extends HttpServlet
{
	Connection conn;
	 public void init() throws ServletException
	  {
	      // Do required initialization
	      conn = SQLConnect.getConnection();
	  }
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    String title = "Database Result";
		String docType =
		        "<!doctype html public \"-//w3c//dtd html 4.0 " +
		         "transitional//en\">\n";
		         out.println(docType +
		         "<html>\n" +
		         "<head><title>" + title + "</title></head>\n" +
		         "<body bgcolor=\"#f0f0f0\">\n");
		         
		PreparedStatement ps;
		ResultSet rs;
		String path;
		int counter = 0;
			try {
				ps = conn.prepareStatement("select path from graph_normal_images");
				rs = ps.executeQuery();
				
				while(rs.next())
				{
					counter++;
					path = rs.getString("path");
					out.println("<img src=\""+ path +"\" alt = \"Graph"+ counter +"\" style = \"width:"+ Table.SIZE +"px;"
							+ "height:"+Table.SIZE+"px;\">");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally
			{
				out.println("</body>\n"
						+ "</html>");
			}
		
	}
}
