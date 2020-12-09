package chess.service;

import chess.model.Cell;
import chess.model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class KnightService {

    PieceService pieceService = new PieceService();

    protected List<Cell> getLegalMoveKnight(Cell cell, BoardService boardService) {
        Piece knight = cell.getPiece();

        List<Cell> legalMoves = new ArrayList<>();
        int x = cell.getX();
        int y = cell.getY();

        pieceService.addMove(x - 2, y + 1, knight, legalMoves, boardService);
        pieceService.addMove(x - 1, y + 2, knight, legalMoves, boardService);
        pieceService.addMove(x + 1, y + 2, knight, legalMoves, boardService);
        pieceService.addMove(x + 2, y + 1, knight, legalMoves, boardService);
        pieceService.addMove(x + 2, y - 1, knight, legalMoves, boardService);
        pieceService.addMove(x + 1, y - 2, knight, legalMoves, boardService);
        pieceService.addMove(x - 1, y - 2, knight, legalMoves, boardService);
        pieceService.addMove(x - 2, y - 1, knight, legalMoves, boardService);

        return legalMoves;
    }
}
