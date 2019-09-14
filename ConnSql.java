import java.sql.*;

import javax.swing.JOptionPane;



public class ConnSql {

	
		
	Connection conn=null;
 public static Connection connectdb() {
	
	
	try {
	
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","abdouAlger1");
		
		return conn;
		
		
	}
	catch (Exception  e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, e);
		return null;
	}

	}
}
		
	
