package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class DrawChessBoard {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 1;
    private static final String EMPTY = "   ";
    private static final String QUEEN = " Q ";
    private static final String KING = " K ";
    private static final String ROOK = " R ";
    private static final String BISHOP = " B ";
    private static final String KNIGHT = " N ";
    private static final String PAWN = " P ";
    ChessBoard board;

    public DrawChessBoard(ChessBoard board) {
        this.board = board;
    }

    public ChessBoard getBoard(){
        return this.board;
    }

    public void drawBoard(ChessGame.TeamColor color) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);
        if (color.equals(ChessGame.TeamColor.BLACK)) {
            drawHeaders(out, "black");
            drawChessBoard(out, "black", getBoard());
            drawHeaders(out, "black");
        } else if (color.equals(ChessGame.TeamColor.WHITE)){
            drawHeaders(out, "white");
            drawChessBoard(out, "white", getBoard());
            drawHeaders(out, "white");
        }

        out.println();

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawHeaders(PrintStream out, String color) {
        setBlack(out);
        String[] headers;

        if (color.equals("white")) {
            headers = new String[]{"   a ", " b ", " c ", " d ", " e ", " f ", " g ", " h "};
        } else {
            headers = new String[]{"   h ", " g ", " f ", " e ", " d ", " c ", " b ", " a "};
        }

        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, headers[boardCol]);
        }
        out.println();
    }

//    private static void drawHeadersReverse(PrintStream out) {
//
//        setBlack(out);
//
//        String[] headers = { "   h ", " g ", " f ", " e ", " d ", " c ", " b ", " a " };
//        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
//            drawHeader(out, headers[boardCol]);
//        }
//
//        out.println();
//    }

    private static void drawHeader(PrintStream out, String headerText) {
        printHeaderText(out, headerText);
    }
    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);

        out.print(player);

        setBlack(out);
    }

    private static void drawChessBoard(PrintStream out, String color, ChessBoard board) {
        boolean startWhite = true;
        int row;

        if (color.equals("white")){
            row = 8;
        } else {
            row = 1;
        }

        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            if (startWhite) {
                drawRowOfSquares(out, true, row, color, board);

            } else {
                drawRowOfSquares(out, false, row, color, board);
            }

            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
                setBlack(out);
            }
            if (startWhite) {
                startWhite = false;
            } else {
                startWhite = true;
            }

            if (color.equals("white")) {
                row = row - 1;
            } else {
                row = row +1;
            }
        }
    }

//    private static void drawChessBoardReverse(PrintStream out) {
//        boolean startWhite = true;
//        int row = 1;
//        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
//            if (startWhite) {
//                drawRowOfSquaresReverse(out, true, row);
//
//            } else {
//                drawRowOfSquaresReverse(out, false, row);
//            }
//
//            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
//                setBlack(out);
//            }
//            if (startWhite) {
//                startWhite = false;
//            } else {
//                startWhite = true;
//            }
//
//            row = row +1;
//        }
//    }

    private static void drawRowOfSquares(PrintStream out, boolean tf, int row, String color, ChessBoard board) {
        boolean white = tf;
        //ChessBoard board = new ChessBoard();
        //board.resetBoard();

        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_WHITE);
            out.print(row + " ");

            if (color.equals("white")) {
                for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                    if (white) {
                        setWhite(out);
                    } else {
                        setBlack(out);
                    }

                    if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
                        //print out chess piece letter here
                        printPlayer(out, board.getPiece(new ChessPosition(row, boardCol + 1)));
                    } else {
                        out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                    }

                    if (white) {
                        white = false;
                    } else {
                        white = true;
                    }
                }
            } else {
                for (int boardCol = 8; boardCol > 0; --boardCol) {
                    if (white) {
                        setWhite(out);
                    } else {
                        setBlack(out);
                    }

                    if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
                        //print out chess piece letter here
                        printPlayer(out, board.getPiece(new ChessPosition(row, boardCol)));
                    } else {
                        out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                    }

                    if (white) {
                        white = false;
                    } else {
                        white = true;
                    }
                }
            }
            out.print(SET_BG_COLOR_BLACK);
            out.print(SET_TEXT_COLOR_WHITE);
            out.print(" " + row);

            out.println();
        }
    }

//    private static void drawRowOfSquaresReverse(PrintStream out, boolean tf, int row) {
//        boolean white = tf;
//        ChessBoard board = new ChessBoard();
//        board.resetBoard();
//
//        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
//            out.print(SET_BG_COLOR_BLACK);
//            out.print(SET_TEXT_COLOR_WHITE);
//            out.print(row + " ");
//            for (int boardCol = 8; boardCol > 0; --boardCol) {
//                if (white) {
//                    setWhite(out);
//                } else {
//                    setBlack(out);
//                }
//
//                if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
//                    //print out chess piece letter here
//                    printPlayer(out, board.getPiece(new ChessPosition(row, boardCol)));
//                } else {
//                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
//                }
//
//                if (white) {
//                    white = false;
//                } else {
//                    white = true;
//                }
//            }
//            out.print(SET_BG_COLOR_BLACK);
//            out.print(SET_TEXT_COLOR_WHITE);
//            out.print(" " + row);
//
//            out.println();
//        }
//    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }


    private static void printPlayer(PrintStream out, ChessPiece piece) {
        if (piece == null){
            out.print(EMPTY);
        } else {

            if (piece.getTeamColor().equals(ChessGame.TeamColor.BLACK)) {
                out.print(SET_TEXT_COLOR_BLUE);
            } else {
                out.print(SET_TEXT_COLOR_RED);
            }

            switch (piece.getPieceType()) {
                case KING -> {
                    out.print(KING);
                }
                case QUEEN -> {
                    out.print(QUEEN);
                }
                case BISHOP -> {
                    out.print(BISHOP);
                }
                case KNIGHT -> {
                    out.print(KNIGHT);
                }
                case ROOK -> {
                    out.print(ROOK);
                }
                case PAWN -> {
                    out.print(PAWN);
                }
            }
        }

        setWhite(out);
    }

}
