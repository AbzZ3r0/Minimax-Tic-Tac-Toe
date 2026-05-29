// XOX vs OXO — a tic-tac-toe variant solved with Minimax
/// Originally built for an Artificial Intelligence course assignment
// Copyright (c) 2025 AbzZ3r0
// Licensed under the MIT License (see LICENSE)

import java.util.Scanner;

/**
 * Console game loop.
 *
 * <p>Flow: the player seeds the board with one starting X and one starting O
 * (the O must not be adjacent to the X). Then play alternates with the computer
 * (MAX, 'X') moving first via Minimax, and the player responding as MIN ('O').
 */
public class XOGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MinimaxPlayer ai = new MinimaxPlayer();

        do {
            XOBoard board = new XOBoard();
            printIntro();
            board.printBoard();

            // --- Setup: player places the starting X and a non-adjacent O ---
            int[] x = readCell(scanner, "Starting X", board, null);
            board.place(x[0], x[1], XOBoard.MAX);
            int[] o = readCell(scanner, "Starting O (not adjacent to X)", board, x);
            board.place(o[0], o[1], XOBoard.MIN);

            System.out.println();
            board.printBoard();

            // --- Play: MAX (AI) first, then MIN (player), until the game ends ---
            while (!board.isGameOver()) {
                System.out.println("  MAX is thinking...\n");
                int[] best = ai.findBestMove(board);
                board.place(best[0], best[1], XOBoard.MAX);
                System.out.printf("  MAX plays (%d, %d)%n%n", best[0] + 1, best[1] + 1);
                board.printBoard();
                if (board.isGameOver()) break;

                int[] m = readCell(scanner, "Your move O", board, null);
                board.place(m[0], m[1], XOBoard.MIN);
                System.out.println();
                board.printBoard();
            }

            announce(board.getResult());
        } while (askPlayAgain(scanner));

        scanner.close();
    }

    /**
     * Reads a valid, empty cell. If {@code avoidAdjacentTo} is non-null, the cell
     * must also be non-adjacent to it (used for the starting O).
     */
    private static int[] readCell(Scanner sc, String label, XOBoard board, int[] avoidAdjacentTo) {
        while (true) {
            System.out.print("  " + label + " (row col, 1-" + board.rows() + " / 1-" + board.cols() + "): ");
            try {
                int r = Integer.parseInt(sc.next()) - 1;
                int c = Integer.parseInt(sc.next()) - 1;
                if (r < 0 || r >= board.rows() || c < 0 || c >= board.cols()) {
                    System.out.println("  Out of range. Try again.");
                } else if (!board.isEmpty(r, c)) {
                    System.out.println("  That cell is taken. Try again.");
                } else if (avoidAdjacentTo != null && isAdjacent(r, c, avoidAdjacentTo[0], avoidAdjacentTo[1])) {
                    System.out.println("  Too close to X — choose a non-adjacent cell.");
                } else {
                    return new int[]{r, c};
                }
            } catch (NumberFormatException e) {
                System.out.println("  Please enter two integers.");
            }
        }
    }

    /** Adjacent includes the 8 surrounding cells. */
    private static boolean isAdjacent(int r1, int c1, int r2, int c2) {
        return Math.abs(r1 - r2) <= 1 && Math.abs(c1 - c2) <= 1;
    }

    private static void announce(int result) {
        String msg = result == 1 ? "MAX wins with XOX!"
                   : result == -1 ? "MIN wins with OXO!"
                   : "It's a draw.";
        System.out.println("  ===> " + msg + "\n");
    }

    private static boolean askPlayAgain(Scanner sc) {
        while (true) {
            System.out.print("  Play again? (y/n): ");
            String ans = sc.next().trim().toLowerCase();
            if (ans.equals("y")) { System.out.println(); return true; }
            if (ans.equals("n")) return false;
            System.out.println("  Please enter 'y' or 'n'.");
        }
    }

    private static void printIntro() {
        System.out.println(
            "\n===============================\n" +
            "        XOX  vs  OXO\n" +
            "===============================\n" +
            "  MAX plays X and wins with X-O-X\n" +
            "  MIN plays O and wins with O-X-O\n" +
            "  (any row, column or diagonal of 3)\n" +
            "\n  You seed one X and one non-adjacent O,\n" +
            "  then play as O against the AI (X moves first).\n"
        );
    }
}
