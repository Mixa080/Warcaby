package warcaby.logic;

import warcaby.model.Board;
import warcaby.model.Move;
import warcaby.model.PlayerColor;
import java.util.List;

public class GameManager {
    private Board board;
    private PlayerColor currentTurn;
    private Runnable onTurnChange;
    private Runnable onGameEnd;
    private int[] multiJumpPiece = null;

    public GameManager() {
        this.board = new Board();
        this.currentTurn = PlayerColor.WHITE;
    }

    public void setCallbacks(Runnable onTurnChange, Runnable onGameEnd) {
        this.onTurnChange = onTurnChange;
        this.onGameEnd = onGameEnd;
    }

    public Board getBoard() {
        return board;
    }

    public PlayerColor getCurrentTurn() {
        return currentTurn;
    }

    public List<Move> getValidMoves() {
        if (multiJumpPiece != null) {

            return MoveValidator.getJumpsFrom(board, multiJumpPiece[0], multiJumpPiece[1]); 
        }
        return MoveValidator.getValidMoves(board, currentTurn); 
    }

    public boolean isGameOver() {
        return getValidMoves().isEmpty();
    }

    public boolean applyMove(Move move) {
        List<Move> validMoves = getValidMoves();
        if (validMoves.contains(move)) {
            boolean isJump = move.isJump;
            boolean becameKing = false;
            
            warcaby.model.Piece p = board.getPiece(move.fromRow, move.fromCol);
            boolean wasKing = p.isKing();
            
            board.applyMove(move); 
            
            warcaby.model.Piece pNow = board.getPiece(move.toRow, move.toCol);
            if (!wasKing && pNow.isKing()) {
                becameKing = true;
            }


            boolean multiJumpAvailable = false;
            if (isJump && !becameKing) {
                List<Move> nextJumps = MoveValidator.getJumpsFrom(board, move.toRow, move.toCol);
                if (!nextJumps.isEmpty()) {
                    multiJumpAvailable = true;
                    multiJumpPiece = new int[]{move.toRow, move.toCol};
                }
            }


            if (!multiJumpAvailable) {
                multiJumpPiece = null;
                currentTurn = (currentTurn == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE; 
            }
            
            if (onTurnChange != null) {
                onTurnChange.run();
            }

            if (isGameOver() && onGameEnd != null) {
                onGameEnd.run();
            }

            return true;
        }
        return false;
    }
}
