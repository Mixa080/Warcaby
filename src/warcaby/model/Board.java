package warcaby.model;

public class Board {
    private Piece[][] grid;
    public static final int SIZE = 8;

    public Board() {
        grid = new Piece[SIZE][SIZE];
        initialize();
    }


    public Board(Board other) {
        grid = new Piece[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (other.grid[r][c] != null) {
                    Piece p = new Piece(other.grid[r][c].getColor());
                    if (other.grid[r][c].isKing()) {
                        p.makeKing();
                    }
                    this.grid[r][c] = p;
                }
            }
        }
    }

    private void initialize() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if ((r + c) % 2 != 0) {
                    if (r < 3) {
                        grid[r][c] = new Piece(PlayerColor.BLACK);
                    } else if (r > 4) {
                        grid[r][c] = new Piece(PlayerColor.WHITE);
                    }
                }
            }
        }
    }

    public Piece getPiece(int r, int c) {
        if (r >= 0 && r < SIZE && c >= 0 && c < SIZE) {
            return grid[r][c];
        }
        return null;
    }

    public void setPiece(int r, int c, Piece p) {
        if (r >= 0 && r < SIZE && c >= 0 && c < SIZE) {
            grid[r][c] = p;
        }
    }

    public void applyMove(Move move) {
        Piece p = getPiece(move.fromRow, move.fromCol);
        if (p == null) return;
        
        setPiece(move.fromRow, move.fromCol, null);
        setPiece(move.toRow, move.toCol, p);


        if (Math.abs(move.fromRow - move.toRow) == 2) {
            int midRow = (move.fromRow + move.toRow) / 2;
            int midCol = (move.fromCol + move.toCol) / 2;
            setPiece(midRow, midCol, null);
        }


        if (p.getColor() == PlayerColor.WHITE && move.toRow == 0) {
            p.makeKing();
        } else if (p.getColor() == PlayerColor.BLACK && move.toRow == SIZE - 1) {
            p.makeKing();
        }
    }
}
