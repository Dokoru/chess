package chess.service;

import chess.model.Cell;

import java.util.ArrayList;
import java.util.List;

public class QueenService {

    private RookService rookService = new RookService();
    private BishopService bishopService = new BishopService();

    protected List<Cell> getLegalMoveQueen(Cell cell, BoardService boardService) {
        List<Cell> legalMoves = new ArrayList<>();

        legalMoves.addAll(rookService.getLegalMoveRook(cell, boardService));
        legalMoves.addAll(bishopService.getLegalMoveBishop(cell, boardService));

        return legalMoves;
    }
}
