package DatabaseHandeling;

import Graphs.CreateGraph;

import Graphs.Table;
@SuppressWarnings("unused")
public class executable {

	public static void main(String[] args) {
		
		Table table = new Table("graph_normal");
		int[] points = new int[Table.SIZE];
		int[] points1 = new int[Table.SIZE];
		int[] points2 = new int[Table.SIZE];
		int[] points3 = new int[Table.SIZE];

		int x;
		/*for(int i=0;i<points.length;i++)
		{
			x = (0-points.length/2)+i;
			points[i] = (int) ((10*Math.sin(.1*x)));
		}
		for(int i=0;i<points1.length;i++)
		{
			x = (0-points1.length/2)+i;
			points1[i] = (int) (Math.pow(x, 2)+x+1);
		}
		for(int i=0;i<points2.length;i++)
		{
			x = (0-points2.length/2)+i;
			points2[i] = (int) (-(Math.pow(x, 2)+5*x+1));
		}*/
		int equation;
		for(int i=0;i<points3.length;i++)
		{
			x = (0-points3.length/2)+i;
			equation = (int) (80 * Math.sin( (Math.PI/6) * x - (Math.PI/2)) + 146);
			points3[i] = equation;
		}
		for(int i=0;i<points3.length;i++)
		{
			x = (0-points3.length/2)+i;
			equation = (int) (x);
			points3[i] = equation;
		}
		try {
			//table.addRow(points);
			//table.addRow(points1);
			table.addRow(points3);
		} catch (RowSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new CreateGraph("","graph_normal");
		table.close();
	}

}
