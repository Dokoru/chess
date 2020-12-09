package chess.service;

import chess.model.Cell;
import chess.model.pieces.Color;
import chess.model.pieces.Piece;
import chess.model.pieces.PieceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class KingService {

    protected List<Cell> getLegalMoveKing(Cell cell, BoardService boardService) {
        Piece king = cell.getPiece();

        List<Cell> legalMoves = new ArrayList<>();
        Cell[][] board = boardService.getBoard();
        Color color = king.getColor();
        int x = cell.getX();
        int y = cell.getY();
        Set<Cell> cellUnderAttack = boardService.getCellUnderAttack(opponentColor(cell));
        Cell newCell;

        for (int dx = -1; dx <=1; dx++) {       //common movement into a non-attacked cell
            for (int dy = -1; dy <= 1; dy++) {
                if ((x + dx >=0 && x + dx < 8 && y + dy >=0 && y + dy < 8)
                        && (dx != 0 || dy != 0) && (newCell = board[x + dx][y + dy]) != null &&
                        !cellUnderAttack.contains(newCell) &&
                        (newCell.getPiece() == null || newCell.getPiece().getColor() != color)) {
                    legalMoves.add(newCell);
                }
            }
        }
        if (king.getFirstTurn() == 0) {     //castling
            Cell rookCell = board[x][7];        //right castling
            if (rookCell != null && rookCell.getPiece() != null &&
                    rookCell.getPiece().getType() == PieceType.ROOK && rookCell.getPiece().getFirstTurn() == 0) {
                boolean canCastling = true;
                for (int i = y + 1; i < 7; i++) {
                    Cell currentCell;
                    if ((currentCell = board[x][i]) != null &&
                            (currentCell.getPiece() != null || cellUnderAttack.contains(currentCell))) {
                        canCastling = false;
                        break;
                    }
                }
                if (canCastling) {
                    legalMoves.add(board[x][6]);
                }
            }
            rookCell = board[x][0];        //left castling
            if (rookCell != null && rookCell.getPiece() != null &&
                    rookCell.getPiece().getType() == PieceType.ROOK && rookCell.getPiece().getFirstTurn() == 0) {
                boolean canCastling = true;
                for (int i = y - 1; i > 0; i--) {
                    Cell currentCell;
                    if ((currentCell = board[x][i]) != null &&
                            (currentCell.getPiece() != null || cellUnderAttack.contains(currentCell))) {
                        canCastling = false;
                        break;
                    }
                }
                if (canCastling) {
                    legalMoves.add(board[x][2]);
                }
            }
        }

        return legalMoves;
    }

    private Color opponentColor(Cell cell) {
        ColorService colorService = new ColorService();
        return colorService.reversColor(cell.getPiece().getColor());
    }
}
