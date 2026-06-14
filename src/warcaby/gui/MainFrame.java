package warcaby.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Warcaby");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        showMainMenu();
    }

    public void showMainMenu() {
        MenuPanel menu = new MenuPanel(this);
        setContentPane(menu);
        pack();
        setLocationRelativeTo(null);
    }
}
