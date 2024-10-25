package abatesolutions.stockdesktop.com.Estoque2024;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;


public class ScreenMenu {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(( ) ->  {
				try {
					ScreenMenu window = new ScreenMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
		});
	}

	public ScreenMenu() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
        frame.getContentPane().setBackground(new Color(5, 85, 116));
        frame.setBounds(-6, 0, 1377, 725);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JLabel lblSelecione = new JLabel("SELECIONE");
        lblSelecione.setForeground(new Color(255, 255, 255));
        lblSelecione.setBounds(32, 11, 139, 54);
        lblSelecione.setFont(new  Font("Arial", Font.PLAIN, 24));
        frame.getContentPane().add(lblSelecione);
        
        JButton btnProducts = new JButton("ESTOQUE");
        btnProducts.setBounds(32, 102, 685, 38);
        btnProducts.setFont(new  Font("Arial", Font.PLAIN, 21));
        frame.getContentPane().add(btnProducts);
        btnProducts.addActionListener(e -> openScreenProducts());

        JButton btnVendas = new JButton("VENDAS");
        btnVendas.setBounds(32, 198, 685, 38);
        btnVendas.setFont(new  Font("Arial", Font.PLAIN, 21));
        frame.getContentPane().add(btnVendas);
        
        JButton btnFinanceiro = new JButton("FINANCEIRO");
        btnFinanceiro.setBounds(32, 289, 685, 38);
        btnFinanceiro.setFont(new  Font("Arial", Font.PLAIN, 21));
        frame.getContentPane().add(btnFinanceiro);
        btnFinanceiro.addActionListener(e -> openScreenFinances());
        
	}
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    private void openScreenProducts() {
        EventQueue.invokeLater(() -> {
                try {
                    ScreenProducts window = new ScreenProducts();
                    window.getFrame().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        });
    }

    private void openScreenFinances() {
        EventQueue.invokeLater(() -> {
                try {
                    ScreenFinances window = new ScreenFinances();
                    window.getFrame().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        });
    }

    public JFrame getFrame() {
        return frame;//Só abre com esse método pois la na 
    }                 // classe LoginScreen há o método getFrame dentro do 
                       // método openScreenMenu
}
