package NineBoxPuzzle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PlayerData extends JFrame {
	private JTextField PlayerName;
	private PlayerData Player;
	private JFrame frame;

	/**
	 * Create the panel.
	 */
	public PlayerData(int count,JFrame frame) {
		Player = this;
		this.frame = frame;
		//run();
		try {
			//PlayerData frame = new PlayerData(35);
			Player.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 534, 417);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel Congo = new JLabel("Congratulations ");
		Congo.setFont(new Font("Book Antiqua", Font.BOLD, 30));
		Congo.setHorizontalAlignment(SwingConstants.CENTER);
		Congo.setBounds(67, 40, 371, 61);
		contentPane.add(Congo);
		
		JLabel lblNewLabel = new JLabel("You Win the game enter your name");
		lblNewLabel.setFont(new Font("Segoe UI Light", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(67, 132, 383, 61);
		contentPane.add(lblNewLabel);
		
		PlayerName = new JTextField();
		PlayerName.setToolTipText("name");
		PlayerName.setBounds(164, 208, 192, 61);
		contentPane.add(PlayerName);
		PlayerName.setColumns(10);
		
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DataBase d = new DataBase(PlayerName.getText(),count);
			}
		});
		submit.setBackground(Color.PINK);
		submit.setForeground(Color.BLACK);
		submit.setBounds(201, 293, 120, 38);
		contentPane.add(submit);
		
		JButton score = new JButton("View Score");
		score.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				JFrame j = new JFrame(); 
				Player.setContentPane(new scoreTable(j));
			}
		});
		score.setFont(new Font("Sitka Heading", Font.PLAIN, 15));
		score.setBounds(377, 11, 112, 23);
		contentPane.add(score);

	}
}
