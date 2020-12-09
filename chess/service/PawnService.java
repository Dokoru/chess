package chess.service;

import chess.model.Cell;
import chess.model.pieces.Color;
import chess.model.pieces.Piece;
import chess.model.pieces.PieceType;

import java.util.ArrayList;
import java.util.List;

public class PawnService {

    protected List<Cell> getLegalMovePawn(Cell cell, BoardService boardService) {
        Piece pawn = cell.getPiece();

        List<Cell> legalMoves = new ArrayList<>();
        Cell[][] board = boardService.getBoard();
        int x = cell.getX();      //horizontal
        int y = cell.getY();      //vertical
        Color color = pawn.getColor();
        Cell newCell;       //cell to which a move can be made

        int dx, enPassantX, startX;
        if (color == Color.WHITE) {
            dx = -1;
            enPassantX = 3;
            startX = 6;
        }
        else {
            dx = 1;
            enPassantX = 4;
            startX = 1;
        }

        if (x + dx >= 0 && x + dx < 8 && (newCell = board[x + dx][y]) != null && newCell.getPiece() == null) {        //move forward one cell
            legalMoves.add(newCell);
            if (x == startX && (newCell = board[x + dx + dx][y]) != null && newCell.getPiece() == null) {      //move forward two cell during the first move
                legalMoves.add(newCell);
            }
        }
        if (x + dx >= 0 && x + dx < 8 && y < 7 && (newCell = board[x + dx][y + 1]) != null &&
                newCell.getPiece() != null && newCell.getPiece().getColor() != color) {     //right attack
            legalMoves.add(newCell);
        }
        if (x + dx >= 0 && x + dx < 8 && y > 0 && (newCell = board[x + dx][y - 1]) != null &&
                newCell.getPiece() != null && newCell.getPiece().getColor() != color) {     //left attack
            legalMoves.add(newCell);
        }
        if (x == enPassantX) {
            Cell attackedCell;
            if (y < 7 && (newCell = board[x + dx][y + 1]) != null && (attackedCell =  board[x][y + 1]) != null &&
                    attackedCell.getPiece() != null && attackedCell.getPiece().getType() == PieceType.PAWN &&
                    attackedCell.getPiece().getColor() != color &&
                    (boardService.getCurrentTurn() - attackedCell.getPiece().getFirstTurn()) == 1) {     //right en passant
                legalMoves.add(newCell);
            }
            if (y > 0 && (newCell = board[x + dx][y - 1]) != null && (attackedCell =  board[x][y - 1]) != null &&
                    attackedCell.getPiece() != null &&  attackedCell.getPiece().getType() == PieceType.PAWN &&
                    attackedCell.getPiece().getColor() != color &&
                    (boardService.getCurrentTurn() - attackedCell.getPiece().getFirstTurn()) == 1) {     //left en passant
                legalMoves.add(newCell);
            }
        }

        return legalMoves;
    }

}
