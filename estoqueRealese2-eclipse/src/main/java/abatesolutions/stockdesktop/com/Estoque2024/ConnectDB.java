package abatesolutions.stockdesktop.com.Estoque2024;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	
	public Connection connectToBank(){
        Connection conexcao = null;
        
        try {
            conexcao = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/produtos",
                "postgres", 
                "senha123");

            if (conexcao != null) {
                System.out.println("\n\nBanco de dados conectado com sucesso!");
            } else {
                System.out.println("Conexão falhou!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexcao;
    }

}
