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
        //IMPLEMENT CHECK/CHECKMATE FIRST SO CAN CHECK THOSE CONDITIONS

        Collection<ChessMove> validMvs = new ArrayList<>();
        ChessPiece p = board.getPiece(new ChessPosition(startPosition.getRow(), startPosition.getColumn()));
        if (p==null){
            return null;
        }


        //iterate through its moves, add to validMvs if valid
        for (ChessMove  move : p.pieceMoves(board, new ChessPosition(startPosition.getRow(), startPosition.getColumn()))) {
            ChessPiece p1 = board.getPiece(new ChessPosition(move.getEndPosition().getRow(), move.getEndPosition().getColumn()));
            //check if it's the other team
            if (p1 == null || !(p1.getTeamColor().equals(p.getTeamColor()))) {
//                if (!(p1.getTeamColor().equals(p.getTeamColor()))) {
                    board.addPiece(move.getStartPosition(), null);
                    board.addPiece(move.getEndPosition(), p);
                    if (!isInCheck(p.getTeamColor())) {
                        if (!isInCheckmate(p.getTeamColor())) {
                            validMvs.add(move);
                        }
                    }
                    board.addPiece(move.getEndPosition(), p1);
                    board.addPiece(move.getStartPosition(), p);
                }
            //}
//            if (p1 == null) {
//                board.addPiece(move.getStartPosition(), null);
//                board.addPiece(move.getEndPosition(), p);
//                if (!isInCheck(p.getTeamColor())) {
//                    if (!isInCheckmate(p.getTeamColor())) {
//                        validMvs.add(move);
//                    }
//                }
//                board.addPiece(move.getEndPosition(), p1);
//                board.addPiece(move.getStartPosition(), p);
//            }
        }

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

        ChessPiece p = board.getPiece(move.getStartPosition());

        if (p==null){
            throw new InvalidMoveException("No piece at start");
        }

        //check team's turn (throw exception if not)
        if (p.getTeamColor().equals(getTeamTurn())) {
            //check in valid moves for that piece (throw exception if not)
            if (!(validMoves(move.getStartPosition()).contains(move))) {
                throw new InvalidMoveException("Invalid Move");
            }
            //make the move
            else {
                //make the move, set start to null and end to that piece
                if (p.getPieceType() != ChessPiece.PieceType.PAWN) {
                    board.addPiece(move.getStartPosition(), null);
                    board.addPiece(move.getEndPosition(), p);
                }
                if (p.getPieceType().equals(ChessPiece.PieceType.PAWN)) {
                    if (move.getEndPosition().getRow() == 8 | move.getEndPosition().getRow() == 1) {
                        board.addPiece(move.getStartPosition(), null);
                        board.addPiece(move.getEndPosition(), new ChessPiece(p.getTeamColor(), move.getPromotionPiece()));
                    } else {
                        board.addPiece(move.getStartPosition(), null);
                        board.addPiece(move.getEndPosition(), p);
                    }
                }

                //change teamTurn
                if (getTeamTurn().equals(TeamColor.WHITE)) {
                    setTeamTurn(TeamColor.BLACK);
                } else if (getTeamTurn().equals(TeamColor.BLACK)) {
                    setTeamTurn(TeamColor.WHITE);
                }
            }
        } else {
            throw new InvalidMoveException("Not team's turn");
        }

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
                                ChessPiece p1 = board.getPiece(move.getEndPosition());
                                board.addPiece(move.getStartPosition(), null);
                                board.addPiece(move.getEndPosition(), p);                                //for each move call if it is in check still
                                //if for a move it is no longer in check return false
                                if (!(isInCheck(teamColor))) {
                                    board.addPiece(move.getEndPosition(), p1);
                                    board.addPiece(move.getStartPosition(), p);
                                    return false;
                                }
                                board.addPiece(move.getEndPosition(), p1);
                                board.addPiece(move.getStartPosition(), p);
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
        if (getTeamTurn().equals(teamColor)) {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    ChessPiece p = board.getPiece(new ChessPosition(i, j));
                    if (p != null) {
                        if (p.getTeamColor().equals(teamColor)) {
                            for (ChessMove move : p.pieceMoves(board, new ChessPosition(i, j))) {
                                ChessPiece p1 = board.getPiece(move.getEndPosition());
                                //make move
                                //isInCheck
                                //put both pieces back
                                board.addPiece(move.getStartPosition(), null);
                                board.addPiece(move.getEndPosition(), p);
                                if (!isInCheck(teamColor)) {
                                    return false;
                                    }
                                board.addPiece(move.getEndPosition(), p1);
                                board.addPiece(move.getStartPosition(), p);
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
