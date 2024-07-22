package abatesolutions.stockdesktop.com.Estoque2024;

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
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SelectTablesScreen window = new SelectTablesScreen();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public SelectTablesScreen() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblSelectTableShirts = new JLabel("Camisetas");
        lblSelectTableShirts.setBounds(67, 68, 61, 23);
        frame.getContentPane().add(lblSelectTableShirts);

        JButton btnSelectTableViewShirts = new JButton("Ver");
        btnSelectTableViewShirts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SQL_SelectFromShirts selectAll = new SQL_SelectFromShirts();
                List<Object[]> data = selectAll.selectAllFromCamisetas();
                openScreenTableTShirts(data);
            }
        });
        btnSelectTableViewShirts.setBounds(138, 68, 61, 23);
        frame.getContentPane().add(btnSelectTableViewShirts);

        JLabel lblTables = new JLabel("TABELAS");
        lblTables.setBounds(109, 11, 117, 23);
        frame.getContentPane().add(lblTables);
    }

    private void openScreenTableTShirts(List<Object[]> data) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ScreenTableT_shirts window = new ScreenTableT_shirts(data);
                    window.getFrame().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }
}


