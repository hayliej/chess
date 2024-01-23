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
        if (board.getPiece(myPosition).getPieceType() == PieceType.BISHOP)
        {
            return bishopMove(board, myPosition);
        }
        else if (board.getPiece(myPosition).getPieceType() == PieceType.ROOK) {
            return rookMove(board, myPosition);
        }
        else if (board.getPiece(myPosition).getPieceType() == PieceType.KNIGHT) {
            return knightMove(board, myPosition);
        }
        else if (board.getPiece(myPosition).getPieceType() == PieceType.PAWN) {
            return pawnMove(board, myPosition);
        }
        else if (board.getPiece(myPosition).getPieceType() == PieceType.KING) {
            return kingMove(board, myPosition);
        }
        else if (board.getPiece(myPosition).getPieceType() == PieceType.QUEEN) {
            return queenMove(board, myPosition);
        }
        return new ArrayList<>();
    }


    //BISHOP MOVEMENT
    public Collection<ChessMove> bishopMove(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> bishop = new ArrayList<>();
        ChessPiece p = board.getPiece(new ChessPosition(myPosition.getRow(),myPosition.getColumn()));

        //for loop of all valid moves, check if no piece already there break
        for (int i = 1; i<=8; i++) {
            //check if in bounds
            if ((myPosition.getRow() + i >= 1 & myPosition.getRow() + i <= 8) &
                    (myPosition.getColumn() + i >= 1 & myPosition.getColumn() + i <= 8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + i));
                //check if moving to empty space or enemy piece
                if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                    bishop.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + i), null));
                }
                if (p1 != null){
                    break;
                }
            }
        }

            for (int i = 1; i<=8; i++) {
                if ((myPosition.getRow() + i >= 1 & myPosition.getRow() + i <= 8) &
                        (myPosition.getColumn() - i >= 1 & myPosition.getColumn() - i <= 8)) {
                    ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i));
                    //check if moving to empty space or enemy piece
                    if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                        bishop.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i), null));
                    }
                    if (p1 != null){
                        break;
                    }
                }
            }

        for (int i = 1; i<=8; i++) {
            if ((myPosition.getRow() - i >= 1 & myPosition.getRow() - i <= 8) &
                    (myPosition.getColumn() + i >= 1 & myPosition.getColumn() + i <= 8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i));
                //check if moving to empty space or enemy piece
                if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                    bishop.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i), null));
                }
                if (p1 != null){
                    break;
                }
            }
        }

        for (int i = 1; i<=8; i++){
            if ((myPosition.getRow()-i >=1 & myPosition.getRow()-i <=8) &
                    (myPosition.getColumn()-i >=1 & myPosition.getColumn()-i <=8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i));
                //check if moving to empty space or enemy piece
                if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                    bishop.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i), null));
                }
                if (p1 != null){
                    break;
                }
            }
        }
        //return list of all options myPosition +/- row & column row and column adding or subtracting equal #s;
    return bishop;
    }


    //ROOK MOVEMENT
    public Collection<ChessMove> rookMove(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> rook = new ArrayList<>();
        ChessPiece p = board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn()));

        //increasing row
        for (int i = 1; i<=8; i++){
            //check if in bounds
            if ((myPosition.getRow()+i >=1 & myPosition.getRow()+i <=8) & (myPosition.getColumn() >=1 & myPosition.getColumn() <=8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() + i, myPosition.getColumn()));
                //check if moving to empty space or enemy piece
                if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                    rook.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn()), null));
                }
                if (p1 != null){
                    break;
                }
            }
        }

        //decreasing row
        for (int i = 1; i<=8; i++){
            //check if in bounds
            if ((myPosition.getRow()-i >=1 & myPosition.getRow()-i <=8) & (myPosition.getColumn() >=1 & myPosition.getColumn() <=8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() - i, myPosition.getColumn()));
                //check if moving to empty space or enemy piece
                if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                    rook.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn()), null));
                }
                if (p1 != null){
                    break;
                }
            }
        }

        //increasing col
        for (int i = 1; i<=8; i++){
            //check if in bounds
            if ((myPosition.getRow() >=1 & myPosition.getRow() <=8) & (myPosition.getColumn()+i >=1 & myPosition.getColumn()+i <=8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn()+i));
                //check if moving to empty space or enemy piece
                if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                    rook.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+i), null));
                }
                if (p1 != null){
                    break;
                }
            }
        }

        //decreasing col
        for (int i = 1; i<=8; i++){
            //check if in bounds
            if ((myPosition.getRow() >=1 & myPosition.getRow() <=8) & (myPosition.getColumn()-i >=1 & myPosition.getColumn()-i <=8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn()-i));
                //check if moving to empty space or enemy piece
                if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                    rook.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()-i), null));
                }
                if (p1 != null){
                    break;
                }
            }
        }
        //return list of all options myPosition +/- row or +/- column;
        return rook;
    }


    //KNIGHT MOVEMENT
    public Collection<ChessMove> knightMove(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> knight = new ArrayList<>();
        ChessPiece p = board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn()));

        //row +2, col +1
        //check if in bounds
        if ((myPosition.getRow()+2 >=1 & myPosition.getRow()+2 <=8) & (myPosition.getColumn()+1 >=1 & myPosition.getColumn()+1 <=8)) {
            ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow()+2, myPosition.getColumn()+1));
            //check if moving to empty space or enemy piece
            if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                knight.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+2, myPosition.getColumn()+1), null));
            }
        }

        //row +2, col -1
        //check if in bounds
        if ((myPosition.getRow()+2 >=1 & myPosition.getRow()+2 <=8) & (myPosition.getColumn()-1 >=1 & myPosition.getColumn()-1 <=8)) {
            ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow()+2, myPosition.getColumn()-1));
            //check if moving to empty space or enemy piece
            if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                knight.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+2, myPosition.getColumn()-1), null));
            }
        }

        //row -2, col +1
        //check if in bounds
        if ((myPosition.getRow()-2 >=1 & myPosition.getRow()-2 <=8) & (myPosition.getColumn()+1 >=1 & myPosition.getColumn()+1 <=8)) {
            ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()+1));
            //check if moving to empty space or enemy piece
            if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                knight.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()+1), null));
            }
        }

        //row -2, col -1
        //check if in bounds
        if ((myPosition.getRow()-2 >=1 & myPosition.getRow()-2 <=8) & (myPosition.getColumn()-1 >=1 & myPosition.getColumn()-1 <=8)) {
            ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()-1));
            //check if moving to empty space or enemy piece
            if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                knight.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-2, myPosition.getColumn()-1), null));
            }
        }

        //row +1, col +2
        //check if in bounds
        if ((myPosition.getRow()+1 >=1 & myPosition.getRow()+1 <=8) & (myPosition.getColumn()+2 >=1 & myPosition.getColumn()+2 <=8)) {
            ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+2));
            //check if moving to empty space or enemy piece
            if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                knight.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()+2), null));
            }
        }

        //row +1, col -2
        //check if in bounds
        if ((myPosition.getRow()+1 >=1 & myPosition.getRow()+1 <=8) & (myPosition.getColumn()-2 >=1 & myPosition.getColumn()-2 <=8)) {
            ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-2));
            //check if moving to empty space or enemy piece
            if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                knight.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()-2), null));
            }
        }

        //row -1, col +2
        //check if in bounds
        if ((myPosition.getRow()-1 >=1 & myPosition.getRow()-1 <=8) & (myPosition.getColumn()+2 >=1 & myPosition.getColumn()+2 <=8)) {
            ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+2));
            //check if moving to empty space or enemy piece
            if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                knight.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()+2), null));
            }
        }

        //row -1, col -2
        //check if in bounds
        if ((myPosition.getRow()-1 >=1 & myPosition.getRow()-1 <=8) & (myPosition.getColumn()-2 >=1 & myPosition.getColumn()-2 <=8)) {
            ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-2));
            //check if moving to empty space or enemy piece
            if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                knight.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()-2), null));
            }
        }

        //return list of all options myPosition +/- row & column row and column adding or subtracting equal 2 in one direction and 1 in the others;
        return knight;
    }


    //PAWN MOVEMENT
    public Collection<ChessMove> pawnMove(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> pawn = new ArrayList<>();
        ChessPiece p = board.getPiece(new ChessPosition(myPosition.getRow(),myPosition.getColumn()));

        //check if it's on starting line, white row 2, black row 7
        if (p.getTeamColor().equals(ChessGame.TeamColor.WHITE)) {
            if (myPosition.getRow() == 2) {
                //2 spaces
                if ((myPosition.getRow() + 2 >= 1 & myPosition.getRow() + 2 <= 8) & (myPosition.getColumn() >= 1 & myPosition.getColumn() <= 8)) {
                    ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn()));
                    if (p1 == null) {
                        pawn.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 2, myPosition.getColumn()), null));
                    }
                }
            }
            //1 space
            if ((myPosition.getRow() + 1 >= 1 & myPosition.getRow() + 1 <= 8) & (myPosition.getColumn() >= 1 & myPosition.getColumn() <= 8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()));
                if (p1 == null) {
                    pawn.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn()), null));
                }
            }

            //check for attacking
            if ((myPosition.getRow() + 1 >= 1 & myPosition.getRow() + 1 <= 8) & (myPosition.getColumn() + 1 >= 1 & myPosition.getColumn() + 1 <= 8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1));
                if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                    pawn.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() + 1), null));
                }
            }
            if ((myPosition.getRow() + 1 >= 1 & myPosition.getRow() + 1 <= 8) & (myPosition.getColumn() - 1 >= 1 & myPosition.getColumn() - 1 <= 8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1));
                if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                    pawn.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + 1, myPosition.getColumn() - 1), null));
                }
            }
        }

        if (p.getTeamColor().equals(ChessGame.TeamColor.BLACK) & myPosition.getRow()==7){
            if (myPosition.getRow() == 7) {
                //2 spaces
                if ((myPosition.getRow() - 2 >= 1 & myPosition.getRow() - 2 <= 8) & (myPosition.getColumn() >= 1 & myPosition.getColumn() <= 8)) {
                    ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn()));
                    if (p1 == null) {
                        pawn.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 2, myPosition.getColumn()), null));
                    }
                }
            }
            //1 space
            if ((myPosition.getRow() - 1 >= 1 & myPosition.getRow() - 1 <= 8) & (myPosition.getColumn() >= 1 & myPosition.getColumn() <= 8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()));
                if (p1 == null) {
                    pawn.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn()), null));
                }
            }

            //check for attacking
            if ((myPosition.getRow() - 1 >= 1 & myPosition.getRow() - 1 <= 8) & (myPosition.getColumn() + 1 >= 1 & myPosition.getColumn() + 1 <= 8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1));
                if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                    pawn.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() + 1), null));
                }
            }
            if ((myPosition.getRow() - 1 >= 1 & myPosition.getRow() - 1 <= 8) & (myPosition.getColumn() - 1 >= 1 & myPosition.getColumn() - 1 <= 8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1));
                if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                    pawn.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - 1, myPosition.getColumn() - 1), null));
                }
            }
        }

        //promotion piece, if it gets to other side change type, 4 moves one to end and become ___
        //go through all and add to separate collection

        //return list of all options myPosition +1 row;
        return pawn;
    }



    //KING MOVEMENT
    public Collection<ChessMove> kingMove(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> king = new ArrayList<>();
        ChessPiece p = board.getPiece(new ChessPosition(myPosition.getRow(),myPosition.getColumn()));

        for (int i = -1; i<2; i++) {
            for (int j=-1; j<2; j++) {
                //make sure it moves at least somewhere
                if (!(i == 0 & j == 0)) {
                    //check if in bounds
                    if ((myPosition.getRow()+i >=1 & myPosition.getRow()+i <=8) & (myPosition.getColumn()+j >=1 & myPosition.getColumn()+j <=8)) {
                        ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + j));
                        //check if moving to empty space or enemy piece
                        if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                            king.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + j), null));
                        }
                    }
                }
            }
        }
        return king;
        //return list of all options myPosition +/-1 row &/or column ;
    }


    //QUEEN MOVEMENT
    public Collection<ChessMove> queenMove(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> queen = new ArrayList<>();
        ChessPiece p = board.getPiece(new ChessPosition(myPosition.getRow(),myPosition.getColumn()));

        //moves like rook
        //increasing row
        for (int i = 1; i<=8; i++){
            //check if in bounds
            if ((myPosition.getRow()+i >=1 & myPosition.getRow()+i <=8) & (myPosition.getColumn() >=1 & myPosition.getColumn() <=8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() + i, myPosition.getColumn()));
                //check if moving to empty space or enemy piece
                if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                    queen.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn()), null));
                }
                if (p1 != null){
                    break;
                }
            }
        }

        //decreasing row
        for (int i = 1; i<=8; i++){
            //check if in bounds
            if ((myPosition.getRow()-i >=1 & myPosition.getRow()-i <=8) & (myPosition.getColumn() >=1 & myPosition.getColumn() <=8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() - i, myPosition.getColumn()));
                //check if moving to empty space or enemy piece
                if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                    queen.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn()), null));
                }
                if (p1 != null){
                    break;
                }
            }
        }

        //increasing col
        for (int i = 1; i<=8; i++){
            //check if in bounds
            if ((myPosition.getRow() >=1 & myPosition.getRow() <=8) & (myPosition.getColumn()+i >=1 & myPosition.getColumn()+i <=8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn()+i));
                //check if moving to empty space or enemy piece
                if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                    queen.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()+i), null));
                }
                if (p1 != null){
                    break;
                }
            }
        }

        //decreasing col
        for (int i = 1; i<=8; i++){
            //check if in bounds
            if ((myPosition.getRow() >=1 & myPosition.getRow() <=8) & (myPosition.getColumn()-i >=1 & myPosition.getColumn()-i <=8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn()-i));
                //check if moving to empty space or enemy piece
                if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                    queen.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow(), myPosition.getColumn()-i), null));
                }
                if (p1 != null){
                    break;
                }
            }
        }

        //moves like bishop
        for (int i = 1; i<=8; i++) {
            //check if in bounds
            if ((myPosition.getRow() + i >= 1 & myPosition.getRow() + i <= 8) &
                    (myPosition.getColumn() + i >= 1 & myPosition.getColumn() + i <= 8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + i));
                //check if moving to empty space or enemy piece
                if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                    queen.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() + i), null));
                }
                if (p1 != null){
                    break;
                }
            }
        }

        for (int i = 1; i<=8; i++) {
            if ((myPosition.getRow() + i >= 1 & myPosition.getRow() + i <= 8) &
                    (myPosition.getColumn() - i >= 1 & myPosition.getColumn() - i <= 8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i));
                //check if moving to empty space or enemy piece
                if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                    queen.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() + i, myPosition.getColumn() - i), null));
                }
                if (p1 != null){
                    break;
                }
            }
        }

        for (int i = 1; i<=8; i++) {
            if ((myPosition.getRow() - i >= 1 & myPosition.getRow() - i <= 8) &
                    (myPosition.getColumn() + i >= 1 & myPosition.getColumn() + i <= 8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i));
                //check if moving to empty space or enemy piece
                if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                    queen.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() + i), null));
                }
                if (p1 != null){
                    break;
                }
            }
        }

        for (int i = 1; i<=8; i++){
            if ((myPosition.getRow()-i >=1 & myPosition.getRow()-i <=8) &
                    (myPosition.getColumn()-i >=1 & myPosition.getColumn()-i <=8)) {
                ChessPiece p1 = board.getPiece(new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i));
                //check if moving to empty space or enemy piece
                if (p1 == null || !p1.getTeamColor().equals(p.getTeamColor())) {
                    queen.add(new ChessMove(myPosition, new ChessPosition(myPosition.getRow() - i, myPosition.getColumn() - i), null));
                }
                if (p1 != null){
                    break;
                }
            }
        }
        //return list of all options myPosition +/- row & column row and column adding or subtracting equal #s;
        return queen;
    }

}
