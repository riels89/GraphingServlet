package Graphs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.mysql.jdbc.Connection;

import DatabaseHandeling.SQLConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CreateGraph{
	
	Connection conn;
	
	int dataSize=1;
	int xMax;
	int yMax;
	public CreateGraph(String equation, String table)
	{
		conn = (Connection) SQLConnect.getConnection();
		getPoints(equation, table);
	}
	private void getPoints(String equation, String table)
	{
		PreparedStatement select;
		PreparedStatement add;
		ResultSet rs;
		Scanner scan = new Scanner(Table.getColumns());
		scan.useDelimiter(",");
		
		int[] xPoints = new int[Table.SIZE];
		int[] yPoints = new int[Table.SIZE];
		
		try {
			select = conn.prepareStatement("select * from "+table);
			rs = select.executeQuery();
			
			while(rs.next())
			{
				for(int i=0;i<Table.SIZE;i++)
				{
					xPoints[i] = (0-Table.SIZE/2)+i+(Table.SIZE/2);

					yPoints[i] = (Table.SIZE/2)-rs.getInt(scan.next());
				}
			//	displayPoints(xPoints,yPoints);
				
				add = conn.prepareStatement("update "+table+"_images set path ="
						+ "\"http://localhost:8080/images/graph_"+dataSize+".jpg\" where image_id ="+dataSize+";");
				add.executeUpdate();
				add.close();
	
				createImage(xPoints,yPoints);
				scan = new Scanner(Table.getColumns());
				scan.useDelimiter(",");
				dataSize++;
			}
			
			select.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void createImage(int[] xPoints,int[] yPoints)
	{

		BufferedImage image = new BufferedImage(Table.SIZE,Table.SIZE,BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.GRAY);
		g2.fillRect(0, 0, Table.SIZE, Table.SIZE);
		g2.setColor(Color.BLACK);
		g2.drawLine(Table.SIZE/2, 0, Table.SIZE/2, Table.SIZE);
		g2.drawLine(0, Table.SIZE/2, Table.SIZE, Table.SIZE/2);
		g2.setColor(Color.GREEN);
		
		for(int i=0;i<Table.SIZE-1;i++)
		{
			g2.drawLine(xPoints[i], yPoints[i], xPoints[i+1], yPoints[i+1]);
		}
		exportImage(image);
	}
	
	private void exportImage(BufferedImage image)
	{
		try {
			ImageIO.write(image, "jpg", new File("C:\\Users\\Riley\\..Java Workspace - Ecplise\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\ROOT\\images\\graph_"+dataSize+".jpg"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unused")
	private void displayPoints(int[] xPoints, int[] yPoints)
	{
		String dispY="";
		String dispX="";
		for(int i=0;i<xPoints.length;i++)
		{
			dispY+=yPoints[i]+", ";
			dispX+=xPoints[i]+", ";
		}
		System.out.println("Y points: "+dispY);
		System.out.println("X points: "+dispX);
		System.out.println();
	}
}
