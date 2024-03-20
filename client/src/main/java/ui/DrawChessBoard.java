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
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private static final String EMPTY = "   ";
    private static final String QUEEN = " Q ";
    private static final String KING = " K ";
    private static final String ROOK = " R ";
    private static final String BISHOP = " B ";
    private static final String KNIGHT = " N ";
    private static final String PAWN = " P ";



    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out);

        drawChessBoard(out);

        drawHeaders(out);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawHeaders(PrintStream out) {

        setBlack(out);

        String[] headers = { "a", "b", "c", "d", "e", "f", "g", "h" };
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, headers[boardCol]);

            if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
                out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
            }
        }

        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);

        out.print(player);

        setBlack(out);
    }

    private static void drawChessBoard(PrintStream out) {
        boolean startWhite = true;
        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            if (startWhite) {
                drawRowOfSquares(out, true);
            } else {
                drawRowOfSquares(out, false);
            }

            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
//                drawVerticalLine(out);
                setBlack(out);
            }
            if (startWhite) {
                startWhite = false;
            } else {
                startWhite = true;
            }
        }
    }

    private static void drawRowOfSquares(PrintStream out, boolean tf) {
        boolean white = tf;
        ChessBoard board = new ChessBoard();
        board.resetBoard();

        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                if (white) {
                    setWhite(out);
                } else {
                    setBlack(out);
                }

                    if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
                        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
                        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

                        out.print(EMPTY.repeat(prefixLength));
                        //print out chess piece letter here
                        printPlayer(out, board.getPiece(new ChessPosition(squareRow+1, boardCol+1)));
                        out.print(EMPTY.repeat(suffixLength));
                    } else {
                        out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                    }

                    if (white) {
                        white = false;
                    } else {
                        white = true;
                    }


//                if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
//                    // Draw right line
//                    setRed(out);
//                    out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
//                }

                setBlack(out);
            }

            out.println();
        }
    }

//    private static void drawVerticalLine(PrintStream out) {
//
//        int boardSizeInSpaces = BOARD_SIZE_IN_SQUARES * SQUARE_SIZE_IN_CHARS +
//                (BOARD_SIZE_IN_SQUARES - 1) * LINE_WIDTH_IN_CHARS;
//
//        for (int lineRow = 0; lineRow < LINE_WIDTH_IN_CHARS; ++lineRow) {
//            setRed(out);
//            out.print(EMPTY.repeat(boardSizeInSpaces));
//
//            setBlack(out);
//            out.println();
//        }
//    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setRed(PrintStream out) {
        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_RED);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }


    private static void printPlayer(PrintStream out, ChessPiece piece) {
        //out.print(SET_BG_COLOR_WHITE);

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
