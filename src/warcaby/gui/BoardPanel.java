package warcaby.gui;

import warcaby.logic.GameManager;
import warcaby.model.Board;
import warcaby.model.Move;
import warcaby.model.Piece;
import warcaby.model.PlayerColor;
import warcaby.network.NetworkManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BoardPanel extends JPanel {
    private GameManager gameManager;
    private int tileSize = 60;
    
    private int selectedRow = -1;
    private int selectedCol = -1;

    private PlayerColor localPlayerColor = null;
    private NetworkManager networkManager = null;

    public BoardPanel(GameManager gameManager) {
        this.gameManager = gameManager;
        setPreferredSize(new Dimension(Board.SIZE * tileSize, Board.SIZE * tileSize));
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                handleMouseClick(e.getY() / tileSize, e.getX() / tileSize);
            }
        });
    }

    public void setLocalPlayerColor(PlayerColor color) {
        this.localPlayerColor = color;
    }

    public void setNetworkManager(NetworkManager net) {
        this.networkManager = net;
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    private void handleMouseClick(int row, int col) {
        if (gameManager.isGameOver()) return;


        if (localPlayerColor != null && gameManager.getCurrentTurn() != localPlayerColor) {
            return;
        }

        Piece p = gameManager.getBoard().getPiece(row, col);
        if (p != null && p.getColor() == gameManager.getCurrentTurn()) {
            selectedRow = row;
            selectedCol = col;
            repaint();
            return;
        }


        if (selectedRow != -1 && selectedCol != -1) {
            Move m = new Move(selectedRow, selectedCol, row, col);
            if (gameManager.applyMove(m)) {
                if (networkManager != null) {

                    networkManager.sendMove(m);
                }
                selectedRow = -1;
                selectedCol = -1;
                repaint();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        List<Move> validMoves = gameManager.getValidMoves();

        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                boolean isDark = (r + c) % 2 != 0;
                g2d.setColor(isDark ? new Color(139, 69, 19) : new Color(255, 222, 173));
                g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);


                if (r == selectedRow && c == selectedCol) {
                    g2d.setColor(new Color(255, 255, 0, 100));
                    g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
                }


                if (selectedRow != -1 && selectedCol != -1) {
                    for (Move move : validMoves) {
                        if (move.fromRow == selectedRow && move.fromCol == selectedCol && move.toRow == r && move.toCol == c) {
                            g2d.setColor(new Color(0, 255, 0, 100));
                            g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
                        }
                    }
                }

                Piece p = gameManager.getBoard().getPiece(r, c);
                if (p != null) {
                    g2d.setColor(p.getColor() == PlayerColor.WHITE ? Color.WHITE : Color.BLACK);
                    g2d.fillOval(c * tileSize + 5, r * tileSize + 5, tileSize - 10, tileSize - 10);
                    
                    if (p.isKing()) {
                        g2d.setColor(Color.RED);
                        g2d.setStroke(new BasicStroke(3));
                        g2d.drawOval(c * tileSize + 15, r * tileSize + 15, tileSize - 30, tileSize - 30);
                    }
                }
            }
        }
    }
}
