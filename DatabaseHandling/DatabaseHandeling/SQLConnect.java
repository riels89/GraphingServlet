package DatabaseHandeling;
//Step 1: Use interfaces from java.sql package 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnect {
  //static reference to itself
  private static SQLConnect instance = new SQLConnect();
  public static final String URL = "jdbc:mysql://localhost:3306/graph_points";
  public static final String USER = "root";
  public static final String PASSWORD = "759778";
  public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver"; 
   
  //private constructor
  private SQLConnect() {
      try {
          //Step 2: Load MySQL Java driver
          Class.forName(DRIVER_CLASS);
      } catch (ClassNotFoundException e) {
          e.printStackTrace();
      }
  }
   
  private Connection createConnection() {

      Connection connection = null;
      try {
          //Step 3: Establish Java MySQL connection
          connection = DriverManager.getConnection(URL, USER, PASSWORD);
          connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

          System.out.println("Got connection");
      } catch (SQLException e) {
          System.out.println("ERROR: Unable to Connect to Database.");
      }
      return connection;
  }   
   
  public static Connection getConnection() {
      return instance.createConnection();
  }
}
