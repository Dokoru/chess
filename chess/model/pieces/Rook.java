package chess.model.pieces;

public class Rook extends Piece{

    public Rook(Color color) {
        super(PieceType.ROOK, color);
    }

    public Rook(Piece piece) {
        super(piece);
        this.setType(PieceType.ROOK);
    }
}
