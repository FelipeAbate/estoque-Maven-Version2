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
import javax.swing.table.DefaultTableModel;

import java.util.List;

public class ScreenTableTshirts {

    JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JTextField textFieldDeleteProduct;
    private JTextField textFieldSearchProduct;
    private JTextField textFieldProductBrand;
    private JTextField textFieldProductQuantity;
    private JTextField textFieldRegisterTamanhoProduct;
    private JTextField textFieldUpdateAndCadaster;
    

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
            new Object[][] {
            	{"      "}
            },
            new String[] {"id", "marca", "tamanho", "quant"}
        ) {
         
        };

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(100, 136, 700, 417); 
        frame.getContentPane().add(scrollPane);

        if (data != null) {
            for (Object[] row : data) {
                model.addRow(row);
            }
        }

        JButton returSelectTables = new JButton("< return");
        returSelectTables.setBounds(0, 0, 90, 17);
        frame.getContentPane().add(returSelectTables);
        returSelectTables.addActionListener(e -> returnSelectTablesScreen());

        JLabel lblregisterProduct = new JLabel("Cadastrar Produto");
        lblregisterProduct.setBounds(100, 28, 200, 16);
        lblregisterProduct.setFont(new Font("Arial", Font.PLAIN, 21));
        frame.getContentPane().add(lblregisterProduct);
        
        textFieldProductBrand = new JTextField();
        textFieldProductBrand.setBounds(310, 25, 142, 20);
        frame.getContentPane().add(textFieldProductBrand);
        
        textFieldRegisterTamanhoProduct = new JTextField();
        textFieldRegisterTamanhoProduct.setBounds(485, 25, 142, 20);
        frame.getContentPane().add(textFieldRegisterTamanhoProduct);
        
        textFieldProductQuantity = new JTextField();
        textFieldProductQuantity.setBounds(655, 25, 142, 20);
        frame.getContentPane().add(textFieldProductQuantity);

        JButton saveButton = new JButton("Salvar");
        saveButton.setBounds(495, 65, 118, 20);
        frame.getContentPane().add(saveButton);
         
        saveButton.addActionListener(e -> {
            String marca = textFieldProductBrand.getText();
            String tamanho = textFieldRegisterTamanhoProduct.getText();
            String quant = textFieldProductQuantity.getText();
            
            try {
                Integer.parseInt(quant);
            } catch (NumberFormatException ex) {
                System.out.println("Quantidade deve ser um número válido.");
                return;
            }
        
            insertInTShirts(marca, tamanho, quant);
            model.addRow(new Object[]{null, marca, tamanho, quant});
        
            // Limpar os campos de texto
            textFieldProductBrand.setText("");
            textFieldRegisterTamanhoProduct.setText("");
            textFieldProductQuantity.setText("");
        });

        saveButton.addActionListener(e -> saveTableChanges());

        JLabel deletedProduct = new JLabel("Deletar Produto");
        deletedProduct.setBounds(100, 102, 200, 16);
        deletedProduct.setFont(new Font("Arial", Font.PLAIN, 21));
        frame.getContentPane().add(deletedProduct);

        textFieldDeleteProduct = new JTextField();
        textFieldDeleteProduct.setBounds(310, 62, 142, 20);
        frame.getContentPane().add(textFieldDeleteProduct);
        
        JLabel lblupdateAndCadaster = new JLabel("Editar Produto");
        lblupdateAndCadaster.setBounds(100, 65, 200, 16);
        lblupdateAndCadaster.setFont(new Font("Arial", Font.PLAIN, 21));
        frame.getContentPane().add(lblupdateAndCadaster);
        
        textFieldUpdateAndCadaster = new JTextField();
        textFieldUpdateAndCadaster.setBounds(310, 99, 142, 20);
        frame.getContentPane().add(textFieldUpdateAndCadaster);
        
        JLabel lblSearchProduct = new JLabel("Pesquisar Produto");
        lblSearchProduct.setBounds(880, 28, 200, 16);
        lblSearchProduct.setFont(new Font("Arial", Font.PLAIN, 21));
        frame.getContentPane().add(lblSearchProduct);
        
        textFieldSearchProduct = new JTextField();
        textFieldSearchProduct.setBounds(1060, 25, 233, 20);
        frame.getContentPane().add(textFieldSearchProduct);

    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public void insertInTShirts(String marca, String tamanho, String quant){
		
        String inserirSQL = "INSERT INTO camisetas (marca, tamanho, quant) VALUES (?, ?, ?)";
        
        ConnectDB conexaoDB = new ConnectDB();
        Connection conexcao = conexaoDB.connectToBank();

        int quantInt = Integer.parseInt(quant);

        if (conexcao != null) {
            try (PreparedStatement psmt = conexcao.prepareStatement(inserirSQL)) {
            	
                psmt.setString(1, marca);
                psmt.setString(2, tamanho);
                psmt.setInt(3, quantInt);

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

    private void saveTableChanges() {
        UpdateSQL updateSQL = new UpdateSQL();

        // Iterar sobre as linhas da tabela
        for (int i = 1; i < model.getRowCount(); i++) {
            int id = (int) model.getValueAt(i, 0);
            String marca = (String) model.getValueAt(i, 1);
            String tamanho = (String) model.getValueAt(i, 2);
            int quant = Integer.parseInt(model.getValueAt(i, 3).toString());

            // Atualizar o banco de dados para a linha atual
            updateSQL.update(marca, tamanho, quant, id);
        }

        JOptionPane.showMessageDialog(frame, "Salvo com sucesso");
    }

    private void returnSelectTablesScreen() {
        EventQueue.invokeLater(() -> {
                try {
                    frame.dispose();
                    SelectTablesScreen window = new SelectTablesScreen();
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