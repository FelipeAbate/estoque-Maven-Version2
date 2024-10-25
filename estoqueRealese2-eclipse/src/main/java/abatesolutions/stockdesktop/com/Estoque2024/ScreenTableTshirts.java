package abatesolutions.stockdesktop.com.Estoque2024;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import java.sql.Connection;
import java.sql.PreparedStatement;
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


import java.util.List;

public class ScreenTableTshirts {

    JFrame frame;
    private JTable table;
    private DefaultTableModel model;

    private JTextField textFieldSearchProduct;
    private JTextField textFieldProductBrand;
    private JTextField textFieldRegisterTamanhoProduct;
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

        model = new DefaultTableModel(
            new Object[][] {{"      "}},
            new String[] {"id", "marca", "tamanho", "quant", "preco_un"}
        ) {
         
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permitir apenas uma linha selecionada
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(66, 300, 950, 350); 
        frame.getContentPane().add(scrollPane);

        if (data != null) {
            for (Object[] row : data) {
                model.addRow(row);
            }
        }

        JButton returSelectTables = new JButton("< voltar");
        returSelectTables.setBounds(0, 0, 90, 17);
        frame.getContentPane().add(returSelectTables);

        returSelectTables.addActionListener(e -> returnSelectTablesScreen());

        JLabel lblregisterProduct = new JLabel("Cadastrar");
        lblregisterProduct.setBounds(66, 230, 162, 25);
        lblregisterProduct.setFont(new Font("Arial", Font.PLAIN, 29));
        lblregisterProduct.setForeground(new Color(255, 255, 255));
        frame.getContentPane().add(lblregisterProduct);
        
        textFieldProductBrand = new JTextField();
        textFieldProductBrand.setBounds(255, 238, 184, 20);
        frame.getContentPane().add(textFieldProductBrand);
        
        textFieldRegisterTamanhoProduct = new JTextField();
        textFieldRegisterTamanhoProduct.setBounds(455, 238, 173, 20);
        frame.getContentPane().add(textFieldRegisterTamanhoProduct);
        
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

        // Implementação do campo de pesquisa
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
                         sorter.setRowFilter(null);  // Remove o filtro se o campo de pesquisa estiver vazio
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
                deleteFromDatabase(id);
                model.removeRow(selectedRow);// Remover a linha da tabela
        
                JOptionPane.showMessageDialog(frame, "Produto excluído com sucesso.");
            } else {
                JOptionPane.showMessageDialog(frame, "Selecione um produto para excluir.");
            }
        });

        JButton saveButton = new JButton("Salvar");
        saveButton.setBounds(898, 269, 118, 20);
        frame.getContentPane().add(saveButton);
         
        saveButton.addActionListener(e -> {
            String marca = textFieldProductBrand.getText();
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
        
            insertInTShirts(marca, tamanho, quant, preco_un);
            model.addRow(new Object[]{null, marca, tamanho, quant, preco_un});
        
            // Limpar os campos de texto
            textFieldProductBrand.setText("");
            textFieldRegisterTamanhoProduct.setText("");
            textFieldProductQuantity.setText("");
            textFieldProductPrecoUn.setText("");
        });

        saveButton.addActionListener(e -> saveTableChanges());
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public void insertInTShirts(String marca, String tamanho, String quant, String preco_un){
		
        String inserirSQL = "INSERT INTO camisetas (marca, tamanho, quant, preco_un) VALUES (?, ?, ?, ?)";
        
        ConnectDB conexaoDB = new ConnectDB();
        Connection conexcao = conexaoDB.connectToBank();

        int quantInt = Integer.parseInt(quant);
        Float precFloat = Float.parseFloat(preco_un);

        if (conexcao != null) {
            try (PreparedStatement psmt = conexcao.prepareStatement(inserirSQL)) {
            	
                psmt.setString(1, marca);
                psmt.setString(2, tamanho);
                psmt.setInt(3, quantInt);
                psmt.setFloat(4, precFloat);

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

    private void deleteFromDatabase(int id) {
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

    private void saveTableChanges() {
        UpdateSQL updateSQL = new UpdateSQL();

        // Iterar sobre as linhas da tabela
        for (int i = 1; i < model.getRowCount(); i++) {
            int id = (int) model.getValueAt(i, 0);
            String marca = (String) model.getValueAt(i, 1);
            String tamanho = (String) model.getValueAt(i, 2);
            int quant = Integer.parseInt(model.getValueAt(i, 3).toString());
            Float preco_un = Float.parseFloat(model.getValueAt(i, 4).toString()); 
            // Atualizar o banco de dados para a linha atual
            updateSQL.update(marca, tamanho, quant, preco_un, id);
        }

        JOptionPane.showMessageDialog(frame, "Salvo com sucesso");
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
