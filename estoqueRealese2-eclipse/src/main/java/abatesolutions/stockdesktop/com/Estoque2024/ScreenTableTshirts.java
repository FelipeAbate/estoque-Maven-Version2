package abatesolutions.stockdesktop.com.Estoque2024;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.util.ArrayList;
import java.util.List;

public class ScreenTableTshirts {

    JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JTextField textFieldSearchProduct;
    private JTextField textFieldNameProduct;
    private JTextField textFieldTamanhoProduct;
    private JTextField textFieldProductQuantity;
    private JTextField textFieldProductPrecoUn;
    private TableRowSorter<DefaultTableModel> sorter;
    
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ScreenTableTshirts window = new ScreenTableTshirts(null);
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ScreenTableTshirts(List<Object[]> data) {
        initialize(data);
    }

    private void initialize(List<Object[]> data) {
        frame = new JFrame("TABELA CAMISETAS");
        frame.getContentPane().setBackground(new Color(5, 85, 116));
        frame.setBounds(-6, 0, 1377, 725);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton returSelectTables = new JButton("< voltar");
        returSelectTables.setBounds(0, 0, 90, 17);
        frame.getContentPane().add(returSelectTables);
        returSelectTables.addActionListener(e -> returnSelectTablesScreen());

        JLabel lblregisterProduct = new JLabel("Cadastrar");
        lblregisterProduct.setBounds(66, 230, 162, 25);
        lblregisterProduct.setFont(new Font("Arial", Font.PLAIN, 29));
        lblregisterProduct.setForeground(new Color(255, 255, 255));
        frame.getContentPane().add(lblregisterProduct);
        
        textFieldNameProduct = new JTextField();
        textFieldNameProduct.setBounds(255, 238, 184, 20);
        frame.getContentPane().add(textFieldNameProduct);
        
        textFieldTamanhoProduct = new JTextField();
        textFieldTamanhoProduct.setBounds(455, 238, 173, 20);
        frame.getContentPane().add(textFieldTamanhoProduct);
        
        textFieldProductQuantity = new JTextField();
        textFieldProductQuantity.setBounds(638, 238, 184, 20);
        frame.getContentPane().add(textFieldProductQuantity);

        textFieldProductPrecoUn = new JTextField();
        textFieldProductPrecoUn.setBounds(832, 238, 184, 20);
        frame.getContentPane().add(textFieldProductPrecoUn);

        JLabel lblSearchProduct = new JLabel("Pesquisar");
        lblSearchProduct.setBounds(68, 120, 160, 35);
        lblSearchProduct.setFont(new Font("Arial", Font.PLAIN, 29));
        lblSearchProduct.setForeground(new Color(255, 255, 255));
        frame.getContentPane().add(lblSearchProduct);

        textFieldSearchProduct = new JTextField();
        textFieldSearchProduct.setBounds(255, 133, 184, 20);
        frame.getContentPane().add(textFieldSearchProduct);
        // Adicionar o DocumentListener para o campo de pesquisa
        textFieldSearchProduct.getDocument().addDocumentListener(new DocumentListener() {
           @Override
              public void insertUpdate(DocumentEvent e) {
                 filter();
        }

           @Override
              public void removeUpdate(DocumentEvent e) {
                 filter();
        }

           @Override
              public void changedUpdate(DocumentEvent e) {
                 filter();
        }

        // Método que aplica o filtro baseado no texto inserido
              private void filter() {
                String searchText = textFieldSearchProduct.getText().trim();
                    if (searchText.length() == 0) {
                         sorter.setRowFilter(null);// Remove o filtro se o campo de pesquisa estiver vazio
                    } else {
                         sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 1)); // Aplica o filtro na coluna "marca" (índice 1)
                    }
               }
        });

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

        JButton ButtonInsert = new JButton("Salvar Cadastro do Produto");
        ButtonInsert.setBounds(1028, 238, 190, 20);
        frame.getContentPane().add(ButtonInsert);
        ButtonInsert.addActionListener(e -> {

            String produto = textFieldNameProduct.getText();
            String tamanho = textFieldTamanhoProduct.getText();
            int quant;
            Float precoUn;
            
            try {
                quant = Integer.parseInt(textFieldProductQuantity.getText());
                precoUn = Float.parseFloat(textFieldProductPrecoUn.getText());
            } catch (NumberFormatException ex) {
                System.out.println("Quantidade deve ser um número válido.");
                return;
            }
        
            insert(produto, tamanho, quant, precoUn);
            model.addRow(new Object[]{null, produto, tamanho, quant, precoUn});
        
            // Limpar os campos de texto de CADASTRAR
            textFieldNameProduct.setText("");
            textFieldTamanhoProduct.setText("");
            textFieldProductQuantity.setText("");
            textFieldProductPrecoUn.setText("");
        });
            
        JButton updateButton = new JButton("Atualizar Produto");
        updateButton.setBounds(1028, 271, 190, 20);
        frame.getContentPane().add(updateButton);
        updateButton.addActionListener(e -> {
        
            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {
                selectedRow = table.convertRowIndexToModel(selectedRow); // Converte o índice para o modelo
                int id = (int) model.getValueAt(selectedRow, 0); // Supondo que a coluna 0 é o ID
        
                String produto = textFieldNameProduct.getText();
                String tamanho = textFieldTamanhoProduct.getText();
                int quant;
                float precoUn;
                
                try {
                    quant = Integer.parseInt(textFieldProductQuantity.getText());
                    precoUn = Float.parseFloat(textFieldProductPrecoUn.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Quantidade e Preço devem ser números válidos.");
                    return;
                }
        
                update(produto, tamanho, quant, precoUn, id);
        
                // Atualiza o modelo da tabela com os novos dados
                model.setValueAt(produto, selectedRow, 1);
                model.setValueAt(tamanho, selectedRow, 2);
                model.setValueAt(quant, selectedRow, 3);
                model.setValueAt(precoUn, selectedRow, 4);
        
                JOptionPane.showMessageDialog(frame, "Produto atualizado com sucesso.");
            } else {
                JOptionPane.showMessageDialog(frame, "Selecione um produto para atualizar.");
            }
        });
        
        model = new DefaultTableModel(
            new Object[][] {{"      "}},
            new String[] {"id", "produto", "tamanho", "quant", "preco_un", "cliente"}
        );
         
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permitir apenas uma linha selecionada
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(7, 300, 1370, 350); 
        frame.getContentPane().add(scrollPane);

        if (data != null) {
            for (Object[] row : data) {
                model.addRow(row);
            }
        }

    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
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

    public List<Object[]> selectFrom() {
	
    String querySQL = "SELECT * FROM camisetas";

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

    public void insert(String produto, String tamanho, int quant, Float precoUn){
		
        String insertSQL = "INSERT INTO camisetas (produto, tamanho, quant, preco_un) VALUES (?, ?, ?, ?)";
        
        ConnectDB conexaoDB = new ConnectDB();
        Connection conexcao = conexaoDB.connectToBank();

        if (conexcao != null) {
            try (PreparedStatement psmt = conexcao.prepareStatement(insertSQL)) {
            	
                psmt.setString(1, produto);
                psmt.setString(2, tamanho);
                psmt.setInt(3, quant);
                psmt.setFloat(4, precoUn);

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

    public void update(String produto, String tamanho, int quant, Float precoUn, int id) {

        String modificar = 
        "UPDATE camisetas SET produto=?, tamanho=?, quant=?, preco_un=? WHERE id=?";
        
        ConnectDB conexaoDB = new ConnectDB();
        Connection conexcao = conexaoDB.connectToBank();
        
        if (conexcao != null) {
        try (PreparedStatement pstm = conexcao.prepareStatement(modificar)) {
                
                pstm.setString(1, produto);
                pstm.setString(2, tamanho);
                pstm.setInt(3, quant);
                pstm.setFloat(4, precoUn);
                pstm.setInt(5, id);

                int linhasAfetadas = pstm.executeUpdate();
                
            if (linhasAfetadas > 0) {
               System.out.println("Um registro foi modificado com sucesso!");
            } else {
               System.out.println("Nenhum registro encontrado com o ID fornecido.");
            }
        
        } catch (SQLException e) {
            System.err.println("Erro ao modificar dados: " + e.getMessage());
        }
        
    }
}

    public JFrame getFrame() {
        return frame;
    }

}
