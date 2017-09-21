package DatabaseHandeling;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AddToDataBase {
	HashMap<String, String> tables = new HashMap<>();
	private static Connection conn;
	private String normalColumns="";
	private String piColumns ="";
	public AddToDataBase()
	{
		conn = SQLConnect.getConnection();
		tables.put("graph_normal", normalColumns);
		tables.put("graph_pi", piColumns);
		for(int i=0;i<16*50;i++)
		{
			normalColumns+= "point_"+i+",";
		}
		normalColumns=normalColumns.substring(0,normalColumns.length()-1);
	}
	
	/**
	 * SHOULD NOT BE USED, ONLY NEEDED ONCE
	 */
	public void creatGraph(String table)
	{
		PreparedStatement ps=null;
		try
		{
			String columns="";
			for(int i=0;i<16*50;i++)
			{
				columns+= "point_"+i+"_50 double,";
			}
			columns=columns.substring(0,columns.length()-1);
		ps = conn.prepareStatement("create table "+table+"("+columns+");");
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
	public void addRow(int[] points,String table) throws RowSizeException
	{
		if(points.length==16*50)
		{
			PreparedStatement ps=null;
			String dispPoints="";
			for(int point:points)
			{
				dispPoints+= point+",";
			}
			try
			{
				ps = conn.prepareStatement("insert into "+table+"("+ getColumn(table) + ")"
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
	public String getColumn(String table)
	{
		for (String key : tables.keySet()) {
		    if(table.equals(key))
		    {
		    	return tables.get(key);
		    }
		}
		return null;
	}
	public double[] find(int row)
	{
		PreparedStatement ps=null;
		ResultSet rs=null;
		double[] points = new double[16*50];
		try
		{
			ps = conn.prepareStatement("SELECT employee.*,department.* FROM employee JOIN department on department.dept_id = employee.dept_id;");
			rs = ps.executeQuery();
			
			if(rs.absolute(row))
			{
				for(int i=0;i<16*50;i++)
				{
					points[i] = rs.getDouble(i);
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
