package warcaby.gui;

import warcaby.logic.GameManager;
import warcaby.logic.AIPlayer;
import warcaby.model.PlayerColor;
import warcaby.network.NetworkManager;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private MainFrame mainFrame;

    public MenuPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridLayout(4, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        setPreferredSize(new Dimension(630, 520));

        JButton btnLocal = new JButton("Graj lokalnie (2 graczy)");
        btnLocal.addActionListener(e -> startGame(false, false, false, ""));
        add(btnLocal);

        JButton btnAI = new JButton("Graj z komputerem (AI)");
        btnAI.addActionListener(e -> startGame(true, false, false, ""));
        add(btnAI);

        JButton btnServer = new JButton("Stwórz grę w sieci (Serwer)");
        btnServer.addActionListener(e -> startGame(false, true, true, ""));
        add(btnServer);

        JButton btnClient = new JButton("Dołącz do gry w sieci (Klient)");
        btnClient.addActionListener(e -> {
            String ip = JOptionPane.showInputDialog("Podaj IP serwera:", "localhost");
            if (ip != null && !ip.trim().isEmpty()) {
                startGame(false, true, false, ip);
            }
        });
        add(btnClient);
    }

    private void startGame(boolean vsAI, boolean isNetwork, boolean isServer, String ip) {
        GameManager manager = new GameManager();
        GamePanel panel = new GamePanel(manager, mainFrame);
        
        if (vsAI) {
            panel.getBoardPanel().setLocalPlayerColor(PlayerColor.WHITE);
            AIPlayer ai = new AIPlayer(manager, PlayerColor.BLACK);
            
            manager.setCallbacks(() -> {
                panel.updateTimers();
                panel.getBoardPanel().repaint();
                
                if (manager.getCurrentTurn() == PlayerColor.BLACK) {
                    new Thread(() -> {
                        try { Thread.sleep(500); } catch (Exception ignored) {}
                        ai.makeMove();
                        SwingUtilities.invokeLater(() -> panel.getBoardPanel().repaint());
                    }).start();
                }
            }, () -> panel.showEndGameMessage());
            
        } else if (isNetwork) {
            PlayerColor myColor = isServer ? PlayerColor.WHITE : PlayerColor.BLACK;
            panel.getBoardPanel().setLocalPlayerColor(myColor);
            
            panel.stopTimers();
            
            NetworkManager net = new NetworkManager(isServer, ip, 12345, () -> {
                SwingUtilities.invokeLater(() -> panel.updateTimers());
            }, move -> {
                SwingUtilities.invokeLater(() -> {
                    manager.applyMove(move);
                    panel.getBoardPanel().repaint();
                });
            }, () -> {
                JOptionPane.showMessageDialog(this, "Utracono połączenie z drugim graczem!");
            });
            
            manager.setCallbacks(() -> {
                panel.updateTimers();
                panel.getBoardPanel().repaint();
            }, () -> panel.showEndGameMessage());
            
            panel.getBoardPanel().setNetworkManager(net);
        } else {
            manager.setCallbacks(() -> {
                panel.updateTimers();
                panel.getBoardPanel().repaint();
            }, () -> panel.showEndGameMessage());
        }

        mainFrame.setContentPane(panel);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
    }
}
