package abatesolutions.stockdesktop.com.Estoque2024;

import java.awt.EventQueue;

public class App {

	public static void main(String[] args) {
		
		InsertSQL insertInCamisetas = new InsertSQL();
        insertInCamisetas.insert();
        
        UpdateSQL updateShirts = new UpdateSQL();
        updateShirts.update("Nike", 67);
        
        DeleteSQL deleteInshirts = new DeleteSQL();
        deleteInshirts.delete_sql(76);
        
	}
}
