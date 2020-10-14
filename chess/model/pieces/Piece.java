package chess.model.pieces;

import chess.model.Cell;

import java.util.List;

public abstract class Piece {

    private PieceType type;
    private Color color;
    private int firstTurn = 0;

    public Piece(PieceType type, Color color) {
        this.type = type;
        this.color = color;
    }

    public Piece(Piece piece) {
        this.color = piece.getColor();
        this.firstTurn = piece.getFirstTurn();
    }

    public PieceType getType() {
        return type;
    }

    public void setType(PieceType type) {
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getFirstTurn() {
        return firstTurn;
    }

    public void setFirstTurn(int firstTurn) {
        this.firstTurn = firstTurn;
    }
}
