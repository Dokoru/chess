package chess.model.pieces;

public class Pawn extends Piece{

    public Pawn(Color color) {
        super(PieceType.PAWN, color);
    }

    public Pawn(Piece piece) {
        super(piece);
    }
}
