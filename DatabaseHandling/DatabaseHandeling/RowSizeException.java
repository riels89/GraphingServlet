package DatabaseHandeling;

@SuppressWarnings("serial")
public class RowSizeException extends Exception{

	public RowSizeException(){}
	
	public RowSizeException(String message)
	{
		super(message);
	}
}
