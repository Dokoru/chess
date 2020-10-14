package chess.model.pieces;

public class Queen extends Piece{

    public Queen(Color color) {
        super(PieceType.QUEEN, color);
    }

    public Queen(Piece piece) {
        super(piece);
        this.setType(PieceType.QUEEN);
    }
}
