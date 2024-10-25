package abatesolutions.stockdesktop.com.Estoque2024;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;


public class ScreenFinances {

    private JFrame frame;
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ScreenFinances screenFinances = new ScreenFinances();
                screenFinances.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });   
	}

    public ScreenFinances() {
        initialize();
    }

    private void initialize(){
        frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 16));
        frame.getContentPane().setBackground(new Color(5, 85, 116));
        frame.setBounds(-6, 0, 1377, 725);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public JFrame getFrame() {
        return frame; 
    } 

}
