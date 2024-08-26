package abatesolutions.stockdesktop.com.Estoque2024;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ScreenTablePants {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JTextField textFieldRegisterTypeProduct;
    private JTextField textFieldProductQuantity;
    private JTextField textFieldRegisterTamanhoProduct;
    private JTextField textFieldDeleteProduct;
    private JTextField textFieldSearchProduct;


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

        model = new DefaultTableModel(
            new Object[][] {
                {"      "}
            },
            new String[] {"id", "tipo", "marca", "tamanho"}
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
        
        textFieldRegisterTypeProduct = new JTextField();
        textFieldRegisterTypeProduct.setBounds(310, 25, 142, 20);
        frame.getContentPane().add(textFieldRegisterTypeProduct);
        
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
            String tipo = textFieldRegisterTypeProduct.getText();
            String tamanho = textFieldRegisterTamanhoProduct.getText();
            String quant = textFieldProductQuantity.getText();
        
            try {
                Integer.parseInt(quant);
            } catch (NumberFormatException ex) {
                System.out.println("Quantidade deve ser um número válido.");
                return;
            }
        
            insertInPants(tipo, tamanho, quant);
            model.addRow(new Object[]{null, tipo, tamanho, quant});
        
            // Limpar os campos de texto
            textFieldRegisterTypeProduct.setText("");
            textFieldRegisterTamanhoProduct.setText("");
            textFieldProductQuantity.setText("");
        });
     
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
        
        JTextField textFieldUpdateAndCadaster = new JTextField();
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

    public void insertInPants(String tipo, String marca, String tamanho){
		
        String inserirSQL = "INSERT INTO calcas (tipo, marca, tamanho) VALUES (?, ?, ?)";
        
        ConnectDB conexaoDB = new ConnectDB();
        Connection conexcao = conexaoDB.connectToBank();

        int tamanhoInt = Integer.parseInt(tamanho);

        if (conexcao != null) {
            try (PreparedStatement psmt = conexcao.prepareStatement(inserirSQL)) {
            	
                psmt.setString(1, tipo);
                psmt.setString(2, marca);
                psmt.setInt(3, tamanhoInt);

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

     /*  Método para buscar e atualizar os dados da tabela
     private void updateTableData() {
        List<Object[]> data = fetchDataFromDatabase();

        // Limpa os dados atuais da tabela
        model.setRowCount(1); // Mantém o cabeçalho, mas limpa as linhas

        // Adiciona os novos dados à tabela
        for (Object[] row : data) {
            model.addRow(row);
        }
    }

    private List<Object[]> fetchDataFromDatabase() {
        List<Object[]> data = new ArrayList<>();
        String querySQL = "SELECT id, tipo, marca, tamanho FROM calcas";

        ConnectDB conexaoDB = new ConnectDB();
        Connection conexao = conexaoDB.connectToBank();

        if (conexao != null) {
            try (PreparedStatement psmt = conexao.prepareStatement(querySQL);
                 ResultSet rs = psmt.executeQuery()) {

                while (rs.next()) {
                    data.add(new Object[]{
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getString("marca"),
                        rs.getInt("tamanho")
                    });
                }
            } catch (SQLException e) {
                System.err.println("Erro ao buscar dados: " + e.getMessage());
            } finally {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Não foi possível conectar ao banco de dados.");
        }

        return data;
    }
*/
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

