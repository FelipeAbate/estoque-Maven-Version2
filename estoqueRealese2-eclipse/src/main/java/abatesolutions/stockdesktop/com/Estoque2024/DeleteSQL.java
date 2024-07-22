package abatesolutions.stockdesktop.com.Estoque2024;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteSQL {
	
	public void delete_sql(int id) {

        String delete = "DELETE FROM camisetas WHERE id = ?";

        ConnectDB conexaoDB = new ConnectDB();
        Connection conexcao = conexaoDB.connectToBank();
        
        if (conexcao != null) {
        try (PreparedStatement pstm = conexcao.prepareStatement(delete)) {
                    
                pstm.setInt(1, id); 
                int linhasAfetadas = pstm.executeUpdate();

             if (linhasAfetadas > 0) {
                System.out.println("Um registro foi deletado com sucesso!");
            } else {
                System.out.println("Nenhum registro encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar dados: " + e.getMessage());
        }
        
    }
}

}
