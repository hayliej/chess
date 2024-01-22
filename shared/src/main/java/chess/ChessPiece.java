package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return color == that.color && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, pieceType);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "color=" + color +
                ", pieceType=" + pieceType +
                '}';
    }

    ChessGame.TeamColor color;
    ChessPiece.PieceType pieceType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        color = pieceColor;
        pieceType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
//        if (board.getPiece(myPosition).getPieceType() == PieceType.BISHOP)
//        {
//            return bishopMove(board, myPosition);
//        }
//        else if (board.getPiece(myPosition).getPieceType() == PieceType.ROOK) {
//            return rookMove(board, myPosition);
//        }
//        else if (board.getPiece(myPosition).getPieceType() == PieceType.KNIGHT) {
//            return knightMove(board, myPosition);
//        }
//        else if (board.getPiece(myPosition).getPieceType() == PieceType.PAWN) {
//            return pawnMove(board, myPosition);
//        }
//        else
        if (board.getPiece(myPosition).getPieceType() == PieceType.KING) {
            return kingMove(board, myPosition);
        }
//        else if (board.getPiece(myPosition).getPieceType() == PieceType.QUEEN) {
//            return queenMove(board, myPosition);
//        }
        return new ArrayList<>();
    }
//
//    public Collection<ChessMove> bishopMove(ChessBoard board, ChessPosition myPosition) {
//        //for loop of all valid moves, check if no piece already there break
//        //return list of all options myPosition +/- row & column row and column adding or subtracting equal #s;
//    }
//
//    public Collection<ChessMove> rookMove(ChessBoard board, ChessPosition myPosition) {
//        //return list of all options myPosition +/- row or +/- column;
//    }
//
//    public Collection<ChessMove> knightMove(ChessBoard board, ChessPosition myPosition) {
//        //return list of all options myPosition +/- row & column row and column adding or subtracting equal 2 in one direction and 1 in the others;
//    }
//
//    public Collection<ChessMove> pawnMove(ChessBoard board, ChessPosition myPosition) {
//        //return list of all options myPosition +1 column;
//    }
//
    public Collection<ChessMove> kingMove(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> king = new ArrayList<>();
        ChessPiece p = board.getPiece(new ChessPosition(myPosition.getRow(),myPosition.getColumn()));

        for (int i = -1; i<2; i++) {
            for (int j=-1; j<2; j++) {
                if (!(i == 0 & j == 0)) {
                    ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + j));
                    if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                        king.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+i,myPosition.getColumn()+j), null));
                    }
                }
            }
        }
        return king;
        //return list of all options myPosition +/-1 row &/or column ;
    }
//
//    public Collection<ChessMove> queenMove(ChessBoard board, ChessPosition myPosition) {
//        //return list of all options myPosition +/- row & column row and column adding or subtracting equal #s;
//    }
}
