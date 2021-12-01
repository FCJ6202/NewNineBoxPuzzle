package NineBoxPuzzle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.*;

public class DataBase {

	public DataBase(String PlayerName,int count) {
		// TODO Auto-generated constructor stub
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/nineboxpuzzle","root","");   
			Statement stmt=con.createStatement();  
			PreparedStatement ps= con.prepareStatement("insert into score (Player_name,Player_score) value(?,?)");
			ps.setString(1,PlayerName);
			ps.setLong(2,count);
			int x = ps.executeUpdate();
			if(x>0) {
				System.out.println("Success");
			}else {
				System.out.println("Failure");
			}
			
		}catch(Exception el) {
			System.out.println(el);
		}
	}

}
