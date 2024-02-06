package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    public ChessGame() {

    }
    private ChessBoard board;
    private TeamColor teamTurn = TeamColor.WHITE;

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
        //set team turn to input color if not already that team's turn
//        if (team != teamTurn) {
//            if (team == TeamColor.WHITE) {
//                teamTurn = TeamColor.BLACK;
//            }
//            if
//            (teamTurn == TeamColor.BLACK) {
//                teamTurn = TeamColor.WHITE;
//            }
//        }

    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        //call ChessPiece.pieceMoves(startPosition) and check if each possible move is valid,
        //no pieces in the way,
        //not putting yourself in check
        //return collection of valid moves
        //return null if no piece at that position


        Collection<ChessMove> validMvs = new ArrayList<>();
        //IMPLEMENT CHECK/CHECKMATE FIRST SO CAN CHECK THOSE CONDITIONS
//        ChessPiece p = ChessBoard.getPiece(startPosition.getRow(), startPosition.getColumn());
//        Collection<ChessMove> possibleMoves = ChessPiece.pieceMoves(startPosition);
//
//        for (ChessMove move : possibleMoves) {
//            if (move.getEndPosition() == null |
//                    ChessBoard.getPiece(move.getEndPosition().getRow(), move.getEndPosition().getColumn())) {
//                validMvs.add(move);
//            }
//        }

        return validMvs;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        //if move is legal, move the piece
        //if illegal move throw exception
        //set start space to null, end position put piece?

        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //if teamColor team's king can be captured by an opposing piece return true
        //for loop of all opposing team's valid moves, return true once one can move to king
            for (int i = 1; i <=8; i++) {
                for (int j = 1; j<=8; j++) {
                    ChessPiece p = board.getPiece(new ChessPosition(i,j));
                    if (p != null) {
                        if (p.getTeamColor() != teamColor) {
                            for (ChessMove  move : p.pieceMoves(board, new ChessPosition(i,j))){
                                ChessPiece p1 = board.getPiece(new ChessPosition(move.getEndPosition().getRow(), move.getEndPosition().getColumn()));
                                //check if it's the other team's king
                                if (p1 != null) {
                                    if (p1.getPieceType() == ChessPiece.PieceType.KING) {
                                        if (p1.getTeamColor().equals(teamColor)) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        //call isInCheck, if true check
        //can king be defended, if false
        //isInCheckmate returns true
        //if king can be defended, return false

        if (isInCheck(teamColor)) {
            //if it is in check, go through whole board looking for same team's pieces
            for (int i = 1; i <=8; i++) {
                for (int j = 1; j<=8; j++) {
                    ChessPiece p = board.getPiece(new ChessPosition(i,j));
                    if (p != null) {
                        //check same team
                        if (p.getTeamColor().equals(teamColor)) {
                            //for each piece check each move
                            for (ChessMove  move : p.pieceMoves(board, new ChessPosition(i,j))){
                                board.addPiece(new ChessPosition(move.getEndPosition().getRow(), move.getEndPosition().getColumn()), p);
                                //for each move call if it is in check still
                                //if for a move it is no longer in check return false
                                if (!(isInCheck(teamColor))) {
                                    board.addPiece(new ChessPosition(move.getEndPosition().getRow(), move.getEndPosition().getColumn()), null);
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        //check if it is that team's turn & check all teamColor team's moves
            //if none are valid, return true

        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
