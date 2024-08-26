package abatesolutions.stockdesktop.com.Estoque2024;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginScreen {

    JFrame frame;
    private JTextField textFieldUser;
    private JPasswordField textFieldPassword;
    private LoginService loginService = new LoginService();

    public LoginScreen() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame
        ("BOM TRABALHO! PARA CONTINUAR EFETUE O LOGIN DE USUARIO");
        frame.getContentPane().setBackground(new Color(5, 85, 116));
        frame.setBounds(-6, 0, 1377, 725);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblUser = new JLabel("Nome de Usuário");
        lblUser.setBounds(372, 141, 178, 25);
        lblUser.setFont(new Font("Arial", Font.PLAIN, 21));
        frame.getContentPane().add(lblUser);

        textFieldUser = new JTextField();
        textFieldUser.setBounds(560, 141, 200, 25);
        frame.getContentPane().add(textFieldUser);
        textFieldUser.setColumns(10);

        JLabel lblPassword = new JLabel("Senha");
        lblPassword.setBounds(372, 214, 178, 25);
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 21));
        frame.getContentPane().add(lblPassword);

        textFieldPassword = new JPasswordField();
        textFieldPassword.setBounds(560, 214, 200, 25);
        frame.getContentPane().add(textFieldPassword);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(614, 290, 100, 25);
        frame.getContentPane().add(btnLogin);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = textFieldUser.getText();
                String password = new String(textFieldPassword.getPassword());

                boolean sucesso = loginService.verificarLogin(userName, password);

                if (sucesso) {
                    JOptionPane.showMessageDialog(frame, "Login bem-sucedido!");
                    frame.dispose(); 
                    openSelectTablesScreen(); 

                } else {
                    JOptionPane.showMessageDialog(frame, "Nome de usuário ou senha inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    private void openSelectTablesScreen() {
        EventQueue.invokeLater(() -> {
                try {
                    SelectTablesScreen window = new SelectTablesScreen();
                    window.getFrame().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        });
    }
}
