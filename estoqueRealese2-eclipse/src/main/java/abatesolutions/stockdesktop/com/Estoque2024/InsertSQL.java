package abatesolutions.stockdesktop.com.Estoque2024;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertSQL {
	
	public void insert(){
		
        String inserirSQL = "INSERT INTO camisetas (marca, tamanho, quant) VALUES (?, ?, ?)";
        
        ConnectDB conexaoDB = new ConnectDB();
        Connection conexcao = conexaoDB.connectToBank();
        
        if (conexcao != null) {
            try (PreparedStatement psmt = conexcao.prepareStatement(inserirSQL)) {
            	
                psmt.setString(1, "Gutti");
                psmt.setString(2, "PP");
                psmt.setInt(3, 49);

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

}
