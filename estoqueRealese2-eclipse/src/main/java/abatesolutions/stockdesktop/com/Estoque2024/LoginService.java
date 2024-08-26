package abatesolutions.stockdesktop.com.Estoque2024;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {

    public boolean verificarLogin(String nomeUsuario, String senha) {
        String sql = "SELECT * FROM users WHERE nome_usuario = ? AND senha = ?";

        ConnectDB conexaoDB = new ConnectDB();
        Connection conexcao = conexaoDB.connectToBank();
        
        if (conexcao != null) {
        try (PreparedStatement pstm = conexcao.prepareStatement(sql)) {

            pstm.setString(1, nomeUsuario);
            pstm.setString(2, senha);

            ResultSet rs = pstm.executeQuery();

            // Se o resultado tiver pelo menos uma linha, o login é válido
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
        return false;
}
}
