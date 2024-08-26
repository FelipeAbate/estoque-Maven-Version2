package abatesolutions.stockdesktop.com.Estoque2024;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class SelectTablesScreen {

    private JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SelectTablesScreen window = new SelectTablesScreen();
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SelectTablesScreen() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(5, 85, 116));
        frame.setBounds(-6, 0, 1377, 725);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblTables = new JLabel("TABELAS");
        lblTables.setBounds(109, 11, 117, 23);
        frame.getContentPane().add(lblTables);

        JLabel lblTableShirts = new JLabel("Camisetas");
        lblTableShirts.setBounds(67, 68, 61, 23);
        frame.getContentPane().add(lblTableShirts);

        JButton buttonTableShirts = new JButton("Ver");
        buttonTableShirts.setBounds(138, 68, 61, 23);
        frame.getContentPane().add(buttonTableShirts);
        buttonTableShirts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SelectTableShirts selectAll = new SelectTableShirts();
                List<Object[]> data = selectAll.selectAllFromCamisetas();
                openScreenTableTShirts(data);
            }
        });

        JLabel lblTablePants = new JLabel("Calças");
        lblTablePants.setBounds(67, 117, 52, 14);
        frame.getContentPane().add(lblTablePants);
        
        JButton btnSelectTableViewPants = new JButton("Ver");
        btnSelectTableViewPants.setBounds(138, 117, 61, 23);
        frame.getContentPane().add(btnSelectTableViewPants);
        btnSelectTableViewPants.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SelectTablePants selectAll = new SelectTablePants();
                List<Object[]> dad = selectAll.selectAllFromPants();
                openScreenTablePants(dad);
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


