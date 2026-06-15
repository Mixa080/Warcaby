package warcaby.model;
import java.io.Serializable;

// Reprezentuje współrzędne ruchu i umożliwia przesył przez sieć
public class Move implements Serializable {
    public int fromRow, fromCol;
    public int toRow, toCol;
    public boolean isJump;

    public Move(int fromRow, int fromCol, int toRow, int toCol) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
        this.isJump = Math.abs(fromRow - toRow) == 2; 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return fromRow == move.fromRow && fromCol == move.fromCol && 
               toRow == move.toRow && toCol == move.toCol;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(fromRow, fromCol, toRow, toCol);
    }
}
