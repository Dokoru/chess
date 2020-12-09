package chess.service;

import chess.model.Cell;
import chess.model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class RookService {

    PieceService pieceService = new PieceService();

    protected List<Cell> getLegalMoveRook(Cell cell, BoardService boardService) {
        Piece rook = cell.getPiece();

        List<Cell> legalMoves = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();

        for (int i = 1; x + i < 8; i++) {
            if (pieceService.addMove(x + i, y, rook, legalMoves, boardService)) {
                break;
            }
        }
        for (int i = 1; x - i >= 0; i++) {
            if (pieceService.addMove(x - i, y, rook, legalMoves, boardService)) {
                break;
            }
        }
        for (int i = 1; y + i < 8; i++) {
            if (pieceService.addMove(x, y + i, rook, legalMoves, boardService)) {
                break;
            }
        }
        for (int i = 1; y - i >= 0; i++) {
            if (pieceService.addMove(x, y - i, rook, legalMoves, boardService)) {
                break;
            }
        }

        return legalMoves;
    }
}
