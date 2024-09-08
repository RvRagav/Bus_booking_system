import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class hello1 {
    public static void main(String[] args) {
        try {
            // Step 1: Register the MySQL JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Step 2: Establish the connection to the MySQL database
            String url = "jdbc:mysql://localhost:3306/adpdb?characterEncoding=UTF-8";
            String username = "root";
            String password = "Ragavan@2005";
            Connection connection = DriverManager.getConnection(url, username, password);

           
            // Step 4: Insert data into the MySQL table
            java.sql.Statement stmt = connection.createStatement();
            java.sql.ResultSet ResultSet= stmt.executeQuery("select * from user");
    

           while(ResultSet.next())
           {
            System.out.println( ResultSet.getString(1)+" "+ ResultSet.getString(2)+" "+ ResultSet.getString(3));
        
           }

            // Step 5: Close the resources
        ResultSet.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
}
