package chess.service;

import chess.model.Cell;
import chess.model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class BishopService {

    PieceService pieceService = new PieceService();

    protected List<Cell> getLegalMoveBishop(Cell cell, BoardService boardService) {
        Piece bishop = cell.getPiece();

        List<Cell> legalMoves = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();

        for (int i = 1; x + i < 8 && y + i < 8; i++) {
            if (pieceService.addMove(x + i, y + i, bishop, legalMoves, boardService)) {
                break;
            }
        }
        for (int i = 1; x + i < 8 && y - i >= 0; i++) {
            if (pieceService.addMove(x + i, y - i, bishop, legalMoves, boardService)) {
                break;
            }
        }
        for (int i = 1; x - i >= 0 && y + i < 8; i++) {
            if (pieceService.addMove(x - i, y + i, bishop, legalMoves, boardService)) {
                break;
            }
        }
        for (int i = 1; x - i >= 0 && y - i >= 0; i++) {
            if (pieceService.addMove(x - i, y - i, bishop, legalMoves, boardService)) {
                break;
            }
        }

        return legalMoves;
    }
}