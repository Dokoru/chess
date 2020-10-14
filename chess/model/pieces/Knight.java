package chess.model.pieces;

public class Knight extends Piece{

    public Knight(Color color) {
        super(PieceType.KNIGHT, color);
    }

    public Knight(Piece piece) {
        super(piece);
        this.setType(PieceType.KNIGHT);
    }
}
