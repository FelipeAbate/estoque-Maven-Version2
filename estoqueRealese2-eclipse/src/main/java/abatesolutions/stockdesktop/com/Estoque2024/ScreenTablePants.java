package abatesolutions.stockdesktop.com.Estoque2024;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ScreenTablePants {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;

    private JTextField textFieldRegisterProduct;
    private JTextField textFieldRegisterTamanhoProduct;
    private JTextField textFieldProductQuantity;
    private JTextField textFieldProductPrecoUn;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ScreenTablePants window = new ScreenTablePants(null);
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ScreenTablePants(List<Object[]> data) {
        initialize(data);
    }

    private void initialize(List<Object[]> data) {
        frame = new JFrame("TABELA CALÇAS");
        frame.getContentPane().setBackground(new Color(5, 85, 116));
        frame.setBounds(-6, 0, 1377, 725);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton returSelectTables = new JButton("< voltar");
        returSelectTables.setBounds(0, 0, 90, 17);
        frame.getContentPane().add(returSelectTables);
        returSelectTables.addActionListener(e -> returnSelectTablesScreen());

          JButton deletedProduct = new JButton("Excluir");
        deletedProduct.setBounds(455, 133, 118, 20);
        frame.getContentPane().add(deletedProduct);
        deletedProduct.addActionListener(e -> {
            
            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {
                 // Converter o índice da linha para o modelo
                selectedRow = table.convertRowIndexToModel(selectedRow);
                // Obter o ID da linha selecionada (assumindo que a coluna 0 é o ID)
                int id = (int) model.getValueAt(selectedRow, 0);
                delete(id);
                model.removeRow(selectedRow);// Remover a linha da tabela
        
                JOptionPane.showMessageDialog(frame, "Produto excluído com sucesso.");
            } else {
                JOptionPane.showMessageDialog(frame, "Selecione um produto para excluir.");
            }
        });
        
        JLabel lblregisterProduct = new JLabel("Cadastrar");
        lblregisterProduct.setBounds(66, 230, 162, 25);
        lblregisterProduct.setFont(new Font("Arial", Font.PLAIN, 29));
        lblregisterProduct.setForeground(new Color(255, 255, 255));
        frame.getContentPane().add(lblregisterProduct);
        
        textFieldRegisterProduct = new JTextField();
        textFieldRegisterProduct.setBounds(255, 238, 184, 20);
        frame.getContentPane().add(textFieldRegisterProduct);
        
        textFieldRegisterTamanhoProduct = new JTextField();
        textFieldRegisterTamanhoProduct.setBounds(455, 238, 173, 20);
        frame.getContentPane().add(textFieldRegisterTamanhoProduct);
        
        textFieldProductQuantity = new JTextField();
        textFieldProductQuantity.setBounds(638, 238, 184, 20);
        frame.getContentPane().add(textFieldProductQuantity);

        textFieldProductPrecoUn = new JTextField();
        textFieldProductPrecoUn.setBounds(832, 238, 184, 20);
        frame.getContentPane().add(textFieldProductPrecoUn);

        model = new DefaultTableModel(
            new Object[][] {
                {"      "}
            },
            new String[] {"id", "produto", "tamanho", "quant", "preco_un"}
        );

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(66, 300, 950, 350); 
        frame.getContentPane().add(scrollPane); 

        if (data != null) {
            for (Object[] row : data) {
                model.addRow(row);
            }
        }

        JButton saveButton = new JButton("Salvar");
        saveButton.setBounds(898, 269, 118, 20);
        frame.getContentPane().add(saveButton);
        saveButton.addActionListener(e -> {

            String produto = textFieldRegisterProduct.getText();
            String tamanho = textFieldRegisterTamanhoProduct.getText();
            String quant = textFieldProductQuantity.getText();
            String preco_un = textFieldProductPrecoUn.getText();
        
            try {
                Integer.parseInt(quant);
                Float.parseFloat(preco_un);
            } catch (NumberFormatException ex) {
                System.out.println("Quantidade deve ser um número válido.");
                return;
            }
        
            insert(produto, tamanho, quant, preco_un);
            model.addRow(new Object[]{null, produto, tamanho, quant, preco_un});
        

            textFieldRegisterProduct.setText("");
            textFieldRegisterTamanhoProduct.setText("");
            textFieldProductQuantity.setText("");
            textFieldProductPrecoUn.setText("");
        });
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
    
    private void delete(int id) {

        String deleteSQL = "DELETE FROM camisetas WHERE id = ?";

        try (Connection connection = new ConnectDB().connectToBank();
             PreparedStatement psmt = connection.prepareStatement(deleteSQL)) {

            psmt.setInt(1, id);
            int rowsAffected = psmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Produto excluído com sucesso.");
            } else {
                System.out.println("Nenhum produto foi encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> selectFrom() {
	
    String querySQL = "SELECT * FROM calcas";

    List<Object[]> data = new ArrayList<>();
    
    ConnectDB conexaoDB = new ConnectDB();
    Connection conexcao = conexaoDB.connectToBank();
    
    if (conexcao != null) {
        try (Statement stmt = conexcao.createStatement();
             ResultSet rs = stmt.executeQuery(querySQL)) {
          
            while (rs.next()) {
                int id = rs.getInt("id");
                String marca = rs.getString("produto");
                String produto = rs.getString("tamanho");
                int quant = rs.getInt("quant");
                Float preco_un = rs.getFloat("preco_un");

                data.add(new Object[]{id, marca, produto, quant, preco_un});
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

    public void insert(String produto, String tamanho, String quant, String preco_un){
		
        String inserirSQL = "INSERT INTO calcas (produto, tamanho, quant, preco_un) VALUES (?, ?, ?, ?)";
        
        ConnectDB conexaoDB = new ConnectDB();
        Connection conexcao = conexaoDB.connectToBank();

        int quantInt = Integer.parseInt(quant);
        Float preco_unFloat = Float.parseFloat(preco_un);

        if (conexcao != null) {
            try (PreparedStatement psmt = conexcao.prepareStatement(inserirSQL)) {
            	
                psmt.setString(1, produto);
                psmt.setString(2, tamanho);
                psmt.setInt(3, quantInt);
                psmt.setFloat(4, preco_unFloat);

                int linhasAfetadas = psmt.executeUpdate();

                if (linhasAfetadas > 0) {
                    System.out.println("Dados inseridos com sucesso.");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao inserir dados: " + e.getMessage());
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
    }

    private void returnSelectTablesScreen() {
        EventQueue.invokeLater(() -> {
                try {
                    frame.dispose();
                    ScreenProducts window = new ScreenProducts();
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

