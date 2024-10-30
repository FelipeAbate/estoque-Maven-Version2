package abatesolutions.stockdesktop.com.Estoque2024;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class ScreenProducts {

    private JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ScreenProducts window = new ScreenProducts();
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ScreenProducts() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(5, 85, 116));
        frame.setBounds(-6, 0, 1377, 725);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton returSelectTables = new JButton("< voltar");
        returSelectTables.setBounds(0, 0, 90, 17);
        frame.getContentPane().add(returSelectTables);
        returSelectTables.addActionListener(e -> returnScreenMenu());

        JLabel lblProduct = new JLabel("PRODUTOS");
        lblProduct.setBounds(32, 40, 160, 54);
        lblProduct.setForeground(new Color(255, 255, 255));
        lblProduct.setFont(new Font("Arial", Font.PLAIN, 26));
        frame.getContentPane().add(lblProduct);
        
        JButton buttonTableShirts = new JButton("CAMISETAS");
        buttonTableShirts.setBounds(32, 102, 685, 38);
        buttonTableShirts.setFont(new  Font("Arial", Font.PLAIN, 21));
        frame.getContentPane().add(buttonTableShirts);
        buttonTableShirts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ScreenTableTshirts selectAll = new ScreenTableTshirts(null);
                List<Object[]> data = selectAll.selectFrom();
                openScreenTableTShirts(data);
            }
        });

        JButton btnSelectTableViewPants = new JButton("CALÇAS");
        btnSelectTableViewPants.setBounds(32, 198, 685, 38);
        btnSelectTableViewPants.setFont(new  Font("Arial", Font.PLAIN, 21));
        frame.getContentPane().add(btnSelectTableViewPants);
        btnSelectTableViewPants.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ScreenTablePants selectAll = new ScreenTablePants(null);
                List<Object[]> data = selectAll.selectFrom();
                openScreenTablePants(data);
            }
        });

    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
        
    private void openScreenTableTShirts(List<Object[]> data) {
        EventQueue.invokeLater(() -> {
            try {
                frame.dispose();
                ScreenTableTshirts window = new ScreenTableTshirts(data);
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void returnScreenMenu() {
        EventQueue.invokeLater(() -> {
                try {
                    frame.dispose();
                    ScreenMenu window = new ScreenMenu();
                    window.getFrame().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        });
    }    

    private void openScreenTablePants(List<Object[]> dad) {
        EventQueue.invokeLater(() -> {
            try {
                frame.dispose();
                ScreenTablePants window = new ScreenTablePants(dad);
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }
}


