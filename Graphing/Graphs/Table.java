package Graphs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DatabaseHandeling.RowSizeException;
import DatabaseHandeling.SQLConnect;

public class Table {

	public final static int SIZE = 500;
	
	private Connection conn;
	private static String columns="";
	private String table = "###NONE###";
	
	public Table(String table)
	{
		conn = SQLConnect.getConnection();
		setColumns();
		this.table = table;
		createTable();
	}
	public void createTable()
	{
		PreparedStatement ps=null;
		try
		{
			String columns="";
			for(int i=0;i<SIZE;i++)
			{
				columns+= "point_"+i+" int,";
			}
			columns=columns.substring(0,columns.length()-1);
		ps = conn.prepareStatement("create table if not exists "+table+"(id int auto_increment,primary key(id),"+columns+");");
		ps.executeUpdate();
		}catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
	         //finally block used to close resources
	         try{
	            if(ps!=null)
	               ps.close();
	         }catch(SQLException se2){
	         }// nothing we can do
		}
		
	}
	
	public void addRow(int[] points) throws RowSizeException
	{
		if(points.length==SIZE)
		{
			PreparedStatement ps=null;
			String dispPoints="";
			for(int point:points)
			{
				dispPoints+= point+",";
			}
			try
			{
				ps = conn.prepareStatement("insert into "+table+"_images"
						+ "(path) values (\"EMPTY\")");
				ps.executeUpdate();
				ps.close();
				
				ps = conn.prepareStatement("insert into "+table+"("+ columns + ")"
						+ "values (" + dispPoints.substring(0, dispPoints.length()-1) + ");");
				ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
		         //finally block used to close resources
		         try{
		            if(ps!=null)
		               ps.close();
		         }catch(SQLException se2){
		         }// nothing we can do
			}
		}
		else
		{
			throw new RowSizeException();
		}
	}
	public void deleteRow(int rowId)
	{
		PreparedStatement ps=null;
		try
		{
			ps = conn.prepareStatement("delete from " + table + " where id = " + rowId+";");
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
	         //finally block used to close resources
	         try{
	            if(ps!=null)
	               ps.close();
	         }catch(SQLException se2){
	         }// nothing we can do
		}
	}
	public int[] find(int row)
	{
		PreparedStatement ps=null;
		ResultSet rs=null;
		int[] points = new int[SIZE];
		try
		{
			ps = conn.prepareStatement("SELECT * "+table +";");
			rs = ps.executeQuery();
			
			if(rs.absolute(row))
			{
				for(int i=0;i<16*50;i++)
				{
					points[i] = rs.getInt(i);
				}
			}
			 rs.close();
	         ps.close();
	         conn.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}finally{
	         //finally block used to close resources
	         try{
	            if(ps!=null)
	               ps.close();
	         }catch(SQLException se2){
	         }// nothing we can do
		}
	
		return points;
	}
	public void setColumns()
	{
		for(int i=0;i<SIZE;i++)
		{
			columns+= "point_"+i+",";
		}
		columns=columns.substring(0,columns.length()-1);
	}
	public static String getColumns()
	{
		return columns;
	}
	public void close()
	{
		 try{
	            if(conn!=null)
	            conn.close();
	         }catch(SQLException se){
	            se.printStackTrace();
	         }//end finally try
	}
}
