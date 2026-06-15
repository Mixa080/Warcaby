package warcaby.gui;

import warcaby.logic.GameManager;
import warcaby.model.PlayerColor;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private GameManager gameManager;
    private BoardPanel boardPanel;
    private TimerPanel whiteTimer;
    private TimerPanel blackTimer;
    private MainFrame mainFrame;

    public GamePanel(GameManager gameManager, MainFrame mainFrame) {
        this.gameManager = gameManager;
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        boardPanel = new BoardPanel(gameManager);
        whiteTimer = new TimerPanel("Białe");
        blackTimer = new TimerPanel("Czarne");

        JPanel timersPanel = new JPanel(new GridLayout(3, 1));
        timersPanel.setPreferredSize(new Dimension(150, 0));
        timersPanel.add(whiteTimer);
        timersPanel.add(blackTimer);

        JButton btnExit = new JButton("Wyjście do menu");
        btnExit.addActionListener(e -> {
            whiteTimer.stopTimer();
            blackTimer.stopTimer();
            if (boardPanel.getNetworkManager() != null) {
                boardPanel.getNetworkManager().close();
            }
            mainFrame.showMainMenu();
        });
        timersPanel.add(btnExit);

        add(boardPanel, BorderLayout.CENTER);
        add(timersPanel, BorderLayout.EAST);

        updateTimers();
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public void updateTimers() {
        if (gameManager.getCurrentTurn() == PlayerColor.WHITE) {
            blackTimer.stopTimer();
            whiteTimer.startTimer();
        } else {
            whiteTimer.stopTimer();
            blackTimer.startTimer();
        }
    }

    public void stopTimers() {
        whiteTimer.stopTimer();
        blackTimer.stopTimer();
    }

    public void showEndGameMessage() {
        boardPanel.repaint();
        whiteTimer.stopTimer();
        blackTimer.stopTimer();
        JOptionPane.showMessageDialog(this, "Koniec gry! Wygrywa: " + 
            (gameManager.getCurrentTurn() == PlayerColor.WHITE ? "Czarne" : "Białe"));
    }
}
