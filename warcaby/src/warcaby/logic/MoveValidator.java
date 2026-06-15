package warcaby.logic;

import warcaby.model.Board;
import warcaby.model.Move;
import warcaby.model.Piece;
import warcaby.model.PlayerColor;
import warcaby.model.PieceType;

import java.util.ArrayList;
import java.util.List;

public class MoveValidator {

    public static List<Move> getValidMoves(Board board, PlayerColor color) {
        List<Move> jumps = new ArrayList<>();
        List<Move> normalMoves = new ArrayList<>();

        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                Piece p = board.getPiece(r, c);
                if (p != null && p.getColor() == color) {
                    jumps.addAll(getJumpsFrom(board, r, c));
                    normalMoves.addAll(getNormalMovesFrom(board, r, c));
                }
            }
        }

        // Zasada obowiązkowego bicia: jeśli jest bicie, zwykłe ruchy są niedostępne
        if (!jumps.isEmpty()) {
            return jumps;
        }
        return normalMoves;
    }

    public static List<Move> getJumpsFrom(Board board, int r, int c) {
        List<Move> jumps = new ArrayList<>();
        Piece p = board.getPiece(r, c);
        if (p == null) return jumps;

        // Damka bije w obie strony, zwykły pionek tylko do przodu
        int[] rowDirs;
        if (p.getType() == PieceType.KING) {
            rowDirs = new int[] { -1, 1 };
        } else {
            rowDirs = new int[] { p.getColor() == PlayerColor.WHITE ? -1 : 1 };
        }

        int[] colDirs = { -1, 1 };

        for (int rd : rowDirs) {
            for (int cd : colDirs) {
                int r1 = r + rd;
                int c1 = c + cd;
                int r2 = r + 2 * rd;
                int c2 = c + 2 * cd;

                if (r2 >= 0 && r2 < Board.SIZE && c2 >= 0 && c2 < Board.SIZE) {
                    Piece p1 = board.getPiece(r1, c1);
                    Piece p2 = board.getPiece(r2, c2);
                    
                    // Warunek bicia: na sąsiednim polu jest wróg, a za nim jest puste pole
                    if (p1 != null && p1.getColor() != p.getColor() && p2 == null) {
                        jumps.add(new Move(r, c, r2, c2));
                    }
                }
            }
        }
        return jumps;
    }

    public static List<Move> getNormalMovesFrom(Board board, int r, int c) {
        List<Move> moves = new ArrayList<>();
        Piece p = board.getPiece(r, c);
        if (p == null) return moves;

        int[] rowDirs;
        if (p.getType() == PieceType.KING) {
            rowDirs = new int[] { -1, 1 };
        } else {
            rowDirs = new int[] { p.getColor() == PlayerColor.WHITE ? -1 : 1 };
        }
        
        int[] colDirs = { -1, 1 };

        for (int rd : rowDirs) {
            for (int cd : colDirs) {
                int r1 = r + rd;
                int c1 = c + cd;

                if (r1 >= 0 && r1 < Board.SIZE && c1 >= 0 && c1 < Board.SIZE) {
                    if (board.getPiece(r1, c1) == null) {
                        moves.add(new Move(r, c, r1, c1));
                    }
                }
            }
        }
        return moves;
    }
}
