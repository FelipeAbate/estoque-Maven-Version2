package abatesolutions.stockdesktop.com.Estoque2024;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ScreenClientes {

    private JFrame frame;
    private DefaultTableModel model;
    private JTable table;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ScreenClientes screenVendas = new ScreenClientes(null);
                screenVendas.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });   
	}

    public ScreenClientes(List<Object[]> data) {
        initialize(data);
    }

    private void initialize(List<Object[]> data){
        frame = new JFrame("CLIENTES");
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
        frame.getContentPane().setBackground(new Color(5, 85, 116));
        frame.setBounds(-6, 0, 1377, 725);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton returSelectTables = new JButton("< voltar");
        returSelectTables.setBounds(0, 0, 90, 17);
        frame.getContentPane().add(returSelectTables);
        returSelectTables.addActionListener(e -> returnScreenMenu());

        model = new DefaultTableModel (
            new Object[][] {{"    "}},
            new String[] {"id", "nome", "cpf", "endereco", "pedido", "produto", "quant"}
        );

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(69, 200, 1100, 480);
        frame.getContentPane().add(scrollPane);

        if (data != null) { // esse if com for quem "exibe" as row's ja que addRow na table
            for (Object[] row : data) {
                model.addRow(row);
            }
        }
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public List<Object[]> selectFrom() {
	
    String querySQL = "SELECT * FROM cliente";

    List<Object[]> data = new ArrayList<>();
    
    ConnectDB conexaoDB = new ConnectDB();
    Connection conexcao = conexaoDB.connectToBank();
    
    if (conexcao != null) {
        try (Statement stmt = conexcao.createStatement();
             ResultSet rs = stmt.executeQuery(querySQL)) {
          
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String endereco = rs.getString("endereco");
                String pedido = rs.getString("pedido");
                String produto = rs.getString("produto");

                data.add(new Object[]{id, nome, cpf, endereco, pedido, produto});
            }
        } catch (SQLException e) {
            System.err.println("Erro ao executar SELECT: " + e.getMessage());
        } finally {
            try {
                conexcao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    } else {
        System.out.println("Não foi possível conectar ao banco de dados.");
    }
	return data;
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

    public JFrame getFrame() {
        return frame; 
    } 

}

