package warcaby.logic;

import warcaby.model.Board;
import warcaby.model.Move;
import warcaby.model.Piece;
import warcaby.model.PlayerColor;
import warcaby.model.PieceType;

import java.util.List;

public class AIPlayer {
    private GameManager gameManager;
    private PlayerColor color;
    private int maxDepth = 4;

    public AIPlayer(GameManager gameManager, PlayerColor color) {
        this.gameManager = gameManager;
        this.color = color;
    }

    public void makeMove() {
        if (gameManager.isGameOver()) return;

        List<Move> validMoves = gameManager.getValidMoves();
        if (validMoves.isEmpty()) return;

        Move bestMove = null;
        int bestValue = Integer.MIN_VALUE;

        for (Move move : validMoves) {
            Board newBoard = new Board(gameManager.getBoard());
            newBoard.applyMove(move);


            int boardVal = minimax(newBoard, maxDepth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            
            if (boardVal > bestValue) {
                bestValue = boardVal;
                bestMove = move;
            }
        }

        if (bestMove == null) {
            bestMove = validMoves.get(0);
        }

        gameManager.applyMove(bestMove);
    }


    private int minimax(Board board, int depth, int alpha, int beta, boolean isMaximizing) {
        PlayerColor currentColor = isMaximizing ? color
                : (color == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE);
        List<Move> validMoves = MoveValidator.getValidMoves(board, currentColor);


        if (depth == 0 || validMoves.isEmpty()) {
            return evaluateBoard(board);
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : validMoves) {
                Board newBoard = new Board(board);
                newBoard.applyMove(move);
                
                int eval = minimax(newBoard, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                

                if (beta <= alpha) break; 
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move move : validMoves) {
                Board newBoard = new Board(board);
                newBoard.applyMove(move);
                
                int eval = minimax(newBoard, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                

                if (beta <= alpha) break;
            }
            return minEval;
        }
    }


    private int evaluateBoard(Board board) {
        int score = 0;
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                Piece p = board.getPiece(r, c);
                if (p != null) {
                    int val = p.getType() == PieceType.KING ? 3 : 1;
                    if (p.getColor() == color) {
                        score += val;
                    } else {
                        score -= val;
                    }
                }
            }
        }
        return score;
    }
}
