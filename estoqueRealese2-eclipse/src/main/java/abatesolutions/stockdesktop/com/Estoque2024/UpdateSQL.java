package abatesolutions.stockdesktop.com.Estoque2024;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateSQL {
	
	public void update (String marca, int id) {
        String modificar = "UPDATE camisetas SET marca=? WHERE id=?";
        
        ConnectDB conexaoDB = new ConnectDB();
        Connection conexcao = conexaoDB.connectToBank();
        
        if (conexcao != null) {
        try (PreparedStatement pstm = conexcao.prepareStatement(modificar)) {
                
                pstm.setString(1, marca);
                pstm.setInt(2, id);
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

}
