import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Scanner;
import java.awt.*;

public class Password extends JFrame{
	private JButton login,look,quit;
	private JTextField userID,password;
	
	public Password() {
		super("登入");
		
		JPanel toppanel = new JPanel();
		JPanel toppanel1 = new JPanel();
		JPanel toppanel2 = new JPanel();
		JPanel toppanel3 = new JPanel();

		toppanel.setLayout(new GridLayout(3,1));
		
		login = new JButton("發佈者登入");
		JLabel space = new JLabel("                    userID / password: 12345");
		
		JLabel space1 = new JLabel("       userID: ");
		JLabel space2 = new JLabel("password: ");
		userID = new JTextField("",15);
		password = new JTextField("",15);
		toppanel3.add(space);
		toppanel.add(toppanel3);
		toppanel1.add(space1);
		toppanel1.add(userID);
		toppanel.add(toppanel1);
		toppanel2.add(space2);
		toppanel2.add(password);
		toppanel.add(toppanel2);
		add(toppanel, BorderLayout.NORTH);
		
		JPanel underpanel = new JPanel();
	
		login = new JButton("發佈者登入");
		look = new JButton("檢視");
		quit = new JButton("退出");
		
		
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(userID.getText().equals("12345") && password.getText().equals("12345"))
				{
					try 
					{
						PostFrame poster = new PostFrame(0);
						poster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						poster.setSize(600, 440);
						poster.setVisible(true);
						setVisible(false);
					}
					catch(Exception ioe)
					{
						System.out.println("IOExpection error");
					}
				}
				else
					System.out.print("Please try again");
			}
		});
		
		
		look.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
					PostFrame poster = new PostFrame(1);
					poster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					poster.setSize(600, 440);
					poster.setVisible(true);
					setVisible(false);
				}
				catch(Exception ioe)
				{
					System.out.println("IOExpection error");
				}
			}
		});
		
		//退出
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		
		underpanel.add(login);
		underpanel.add(look);
		underpanel.add(quit);
		add(underpanel, BorderLayout.SOUTH);
	}

}
