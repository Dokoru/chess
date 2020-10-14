package chess.model.pieces;

public class Bishop extends Piece{

    public Bishop(Color color) {
        super(PieceType.BISHOP, color);
    }

    public Bishop(Piece piece) {
        super(piece);
        this.setType(PieceType.BISHOP);
    }
}
