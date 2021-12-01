package NineBoxPuzzle;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.ResultSet;

import javax.swing.JTable;

public class scoreTable extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private static JFrame frame;
	private scoreTable data;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	
	public scoreTable(JFrame frame) {
		
		this.frame= frame;
		data = this;
		try {
			data.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 641, 549);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		scoreTable.FetchData(contentPane);
	}
	public static Connection connectToserver() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/nineboxpuzzle","root","");
			System.out.println("Success");
			
			
			
		}catch(Exception el) {
			System.out.println(el);
		}
		return con;
	}
	public static void FetchData(JPanel contentPane2) {
		Connection con = connectToserver();
		String sql = "select * from score ";
		try {
			Statement st = con.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			
			// table contain
			String col[] = {"Rank", "Name", "Number Of Moves"};
	        DefaultTableModel model = new DefaultTableModel(col, 0){
	            @Override
	            public boolean isCellEditable(int row, int column) {
	                //all cells false
	                return false;
	            }
	        };
	        int rank = 1;
			while(rs.next()) {
				//System.out.println(rs.getInt(1)+ " " + rs.getString(2)+"  "+rs.getInt(3));
				String row[] = new String[3];
	            row[0] = ""+rank++;
	            row[1] = rs.getString(2);
	            row[2] = ""+rs.getInt(3);
	            model.addRow(row);
			}	
			
			JTable myTable = new JTable(model);
	        contentPane2.setLayout(new BorderLayout());
	        contentPane2.add(new JScrollPane(myTable), BorderLayout.CENTER);
	        contentPane2.setVisible(true);
		}catch(Exception el){
			System.out.println(el);
		}
		
	}
}
