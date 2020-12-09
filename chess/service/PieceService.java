package chess.service;

import chess.model.Cell;
import chess.model.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class PieceService {

    public List<Cell> getLegalMove(Cell cell, BoardService boardService) {
        switch (cell.getPiece().getType()) {
            case PAWN:
                PawnService pawnService = new PawnService();
                return pawnService.getLegalMovePawn(cell, boardService);
            case ROOK:
                RookService rookService = new RookService();
                return rookService.getLegalMoveRook(cell, boardService);
            case KNIGHT:
                KnightService knightService = new KnightService();
                return knightService.getLegalMoveKnight(cell, boardService);
            case BISHOP:
                BishopService bishopService = new BishopService();
                return bishopService.getLegalMoveBishop(cell, boardService);
            case QUEEN:
                QueenService queenService = new QueenService();
                return queenService.getLegalMoveQueen(cell, boardService);
            case KING:
                KingService kingService = new KingService();
                return kingService.getLegalMoveKing(cell, boardService);
        }
        List<Cell> legalMoves = new ArrayList<>();
        return legalMoves;
    }

    protected boolean addMove(int x, int y, Piece piece, List<Cell> moves, BoardService boardService) {
        if (!(x >= 0 && x < 8 && y >=0 && y < 8)) {
            return false;
        }
        Cell newCell = boardService.getCell(x, y);
        if (newCell != null) {
            if (newCell.getPiece() == null) {
                moves.add(newCell);
                return false;       //false == has more moves
            } else if (piece.getColor() != newCell.getPiece().getColor()) {
                moves.add(newCell);
            }
        }
        return true;        //true == hasn't more moves
    }

    public char getCharValue(Cell cell) {
        if (cell.getPiece().getColor() == Color.WHITE) {
            switch (cell.getPiece().getType()) {
                case PAWN: return 'P';
                case KNIGHT: return 'N';
                case ROOK: return 'R';
                case BISHOP: return 'B';
                case QUEEN: return 'Q';
                case KING: return 'K';
            }
        }
        else {
            switch (cell.getPiece().getType()) {
                case PAWN: return 'p';
                case KNIGHT: return 'n';
                case ROOK: return 'r';
                case BISHOP: return 'b';
                case QUEEN: return 'q';
                case KING: return 'k';
            }
        }
        return ' ';
    }
}
