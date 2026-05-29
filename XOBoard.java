// XOX vs OXO — a tic-tac-toe variant solved with Minimax
// Copyright (c) 2025 Dimitris-Kosmas Karras, Panagiotis Theodoropoulos
// Licensed under the MIT License (see LICENSE).

/**
 * The 4 x 3 game board and its rules.
 *
 * <p>This is a tic-tac-toe variant: a line of three is a win only if it spells a
 * specific pattern. MAX plays 'X' and wins by forming <b>X-O-X</b>; MIN plays 'O'
 * and wins by forming <b>O-X-O</b>, along any row, column or diagonal of length 3.
 */
public class XOBoard {
    public static final char MAX = 'X';
    public static final char MIN = 'O';
    public static final char EMPTY = '_';

    private static final int ROWS = 4;
    private static final int COLS = 3;

    private final char[][] cells;

    public XOBoard() {
        cells = new char[ROWS][COLS];
        for (char[] row : cells) java.util.Arrays.fill(row, EMPTY);
    }

    public int rows() { return ROWS; }
    public int cols() { return COLS; }

    public boolean isEmpty(int r, int c) { return cells[r][c] == EMPTY; }
    public char at(int r, int c)         { return cells[r][c]; }
    public void place(int r, int c, char player) { cells[r][c] = player; }
    public void remove(int r, int c)     { cells[r][c] = EMPTY; }

    public boolean isFull() {
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c < COLS; c++)
                if (cells[r][c] == EMPTY) return false;
        return true;
    }

    /** Returns +1 if MAX has formed "XOX", -1 if MIN has formed "OXO", else 0. */
    public int getResult() {
        int[][] line = winningLine();
        if (line == null) return 0;
        return cells[line[1][0]][line[1][1]] == MIN ? 1 : -1; // middle is O -> XOX (MAX); middle is X -> OXO (MIN)
    }

    /**
     * Finds a completed 3-in-a-row that spells "XOX" or "OXO".
     *
     * @return the three winning cells as {@code [[r,c],[r,c],[r,c]]}, or null if none.
     */
    public int[][] winningLine() {
        // Horizontal
        for (int r = 0; r < ROWS; r++)
            for (int c = 0; c + 2 < COLS; c++) {
                int[][] line = {{r, c}, {r, c + 1}, {r, c + 2}};
                if (isWinningPattern(line)) return line;
            }
        // Vertical
        for (int c = 0; c < COLS; c++)
            for (int r = 0; r + 2 < ROWS; r++) {
                int[][] line = {{r, c}, {r + 1, c}, {r + 2, c}};
                if (isWinningPattern(line)) return line;
            }
        // Diagonals
        for (int r = 0; r + 2 < ROWS; r++)
            for (int c = 0; c + 2 < COLS; c++) {
                int[][] down = {{r, c}, {r + 1, c + 1}, {r + 2, c + 2}};
                if (isWinningPattern(down)) return down;
                int[][] up = {{r + 2, c}, {r + 1, c + 1}, {r, c + 2}};
                if (isWinningPattern(up)) return up;
            }
        return null;
    }

    private boolean isWinningPattern(int[][] line) {
        String s = "" + cells[line[0][0]][line[0][1]]
                      + cells[line[1][0]][line[1][1]]
                      + cells[line[2][0]][line[2][1]];
        return s.equals("XOX") || s.equals("OXO");
    }

    public boolean isGameOver() { return getResult() != 0 || isFull(); }

    /** Prints the board to the console with coloured symbols. */
    public void printBoard() {
        final String RESET = "\u001B[0m", CYAN = "\u001B[96m", CORAL = "\u001B[38;5;209m", DIM = "\u001B[90m";
        System.out.println("       1   2   3");
        System.out.println("     +---+---+---+");
        for (int r = 0; r < ROWS; r++) {
            System.out.print("  " + (r + 1) + "  |");
            for (int c = 0; c < COLS; c++) {
                char ch = cells[r][c];
                String shown = ch == MAX ? CYAN + "X" + RESET
                             : ch == MIN ? CORAL + "O" + RESET
                             : DIM + "." + RESET;
                System.out.print(" " + shown + " |");
            }
            System.out.println("\n     +---+---+---+");
        }
        System.out.println();
    }
}
