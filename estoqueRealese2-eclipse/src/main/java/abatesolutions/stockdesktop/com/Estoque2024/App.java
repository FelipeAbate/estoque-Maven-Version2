package abatesolutions.stockdesktop.com.Estoque2024;

import java.awt.EventQueue;

public class App {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoginScreen loginScreen = new LoginScreen();
                loginScreen.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    //DeleteSQL deletePants = new DeleteSQL();
    //deletePants.deleteInPants(18);  

    //UpdateSQL updateShirts = new UpdateSQL();
    //updateShirts.update ("adidas", "GG", 63, 58);  
        
	}
}
