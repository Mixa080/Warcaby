package warcaby.model;

public class Piece {
    private PlayerColor color;
    private PieceType type;

    public Piece(PlayerColor color) {
        this.color = color;
        this.type = PieceType.MAN;
    }

    public PlayerColor getColor() { return color; }
    public PieceType getType() { return type; }
    public void makeKing() { this.type = PieceType.KING; }
    public boolean isKing() { return type == PieceType.KING; }
}
