package abatesolutions.stockdesktop.com.Estoque2024;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ScreenTableT_shirts {

    private JFrame frame;
    private JTable table;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ScreenTableT_shirts window = new ScreenTableT_shirts(null);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ScreenTableT_shirts(List<Object[]> data) {
        initialize(data);
    }

    private void initialize(List<Object[]> data) {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        table = new JTable();
        DefaultTableModel model = new DefaultTableModel(
            new Object[][] {
            	{"id", "marca", "tamanho", "quant"}
            },
            new String[] {"id", "marca", "tamanho", "quant"}
        );
        table.setModel(model);
        table.setBounds(45, 77, 350, 150);
        frame.getContentPane().add(table);

        if (data != null) {
            for (Object[] row : data) {
                model.addRow(row);
            }
        }
    }

    public JFrame getFrame() {
        return frame;
    }
}