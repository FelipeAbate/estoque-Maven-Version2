package abatesolutions.stockdesktop.com.Estoque2024;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginScreen {

	private JFrame frame;
	private JTextField textFieldUser;
	private JTextField textFieldPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen window = new LoginScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(255, 128, 255));
		frame.setBackground(new Color(255, 128, 192));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textFieldUser = new JTextField();
		textFieldUser.setBounds(161, 58, 205, 20);
		frame.getContentPane().add(textFieldUser);
		textFieldUser.setColumns(10);
		
		textFieldPassword = new JTextField();
		textFieldPassword.setBounds(161, 112, 205, 20);
		frame.getContentPane().add(textFieldPassword);
		textFieldPassword.setColumns(10);
		
		JLabel lblUser = new JLabel("Usuário");
		lblUser.setBounds(95, 61, 46, 14);
		frame.getContentPane().add(lblUser);
		
		JLabel lblPassword = new JLabel("Senha");
		lblPassword.setBounds(95, 115, 46, 14);
		frame.getContentPane().add(lblPassword);
		
		JButton btnEnter = new JButton("ENTER");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                if(checkLogin(textFieldUser.getText(), new String (textFieldPassword.getText()))) {
					
					JOptionPane.showMessageDialog(null, "Bem vindo");
					
					frame.dispose(); // Fechar a tela de login
                    openSelectTablesScreen(); // Abrir a próxima tela
				
				}
				else {
					JOptionPane.showMessageDialog(null, "Senha Incorreta");
				}
			}

			private boolean checkLogin(String login, String password) {
				return login.equals("Isabel") && password.equals("123");
			}
			 private void openSelectTablesScreen() {
	                EventQueue.invokeLater(new Runnable() {
	                    public void run() {
	                        try {
	                            SelectTablesScreen window = new SelectTablesScreen();
	                            window.getFrame().setVisible(true);
	                        } catch (Exception e) {
	                            e.printStackTrace();
	                        }
	                    }
	                });
	            }
		});
		btnEnter.setBounds(223, 158, 89, 23);
		frame.getContentPane().add(btnEnter);
	}
	 public JFrame getFrame() {
	        return frame;
	    }
}
