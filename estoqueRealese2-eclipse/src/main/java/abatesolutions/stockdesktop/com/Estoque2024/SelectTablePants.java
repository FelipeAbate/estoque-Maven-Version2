package abatesolutions.stockdesktop.com.Estoque2024;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SelectTablePants {
	
	public List<Object[]> selectAllFromPants() {
	
    String querySQL = "SELECT * FROM calcas";
    List<Object[]> data = new ArrayList<>();
    
    ConnectDB conexaoDB = new ConnectDB();
    Connection conexcao = conexaoDB.connectToBank();
    
    if (conexcao != null) {
        try (Statement stmt = conexcao.createStatement();
             ResultSet rs = stmt.executeQuery(querySQL)) {
          
            while (rs.next()) {
                int id = rs.getInt("id");
                String tipo = rs.getString("tipo");
                String marca = rs.getString("marca");
                int tamanho = rs.getInt("tamanho");

                data.add(new Object[]{id, tipo, marca, tamanho});
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
}