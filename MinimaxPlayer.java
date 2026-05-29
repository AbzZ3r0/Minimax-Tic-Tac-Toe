// XOX vs OXO — a tic-tac-toe variant solved with Minimax
// Originally built for an Artificial Intelligence course assignment
// Copyright (c) 2025 AbzZ3r0
// Licensed under the MIT License (see LICENSE)
/**
 * Computes MAX's optimal move with the Minimax algorithm.
 *
 * <p>Two refinements over the textbook version:
 * <ul>
 *   <li><b>Alpha-beta pruning</b> — skips branches that cannot affect the result,
 *       so far fewer states are explored for the same answer.</li>
 *   <li><b>Depth-aware scoring</b> — wins are worth more when they happen sooner
 *       and losses are delayed as long as possible, so MAX plays decisively
 *       instead of stalling on an already-won position.</li>
 * </ul>
 */
public class MinimaxPlayer {

    // Large enough that any win outranks a draw on a 4x3 board (max depth 12).
    private static final int WIN_SCORE = 100;

    /** Returns MAX's best move as {row, col}, or {-1,-1} if the board is full. */
    public int[] findBestMove(XOBoard board) {
        int bestScore = Integer.MIN_VALUE;
        int[] move = {-1, -1};

        for (int r = 0; r < board.rows(); r++) {
            for (int c = 0; c < board.cols(); c++) {
                if (board.isEmpty(r, c)) {
                    board.place(r, c, XOBoard.MAX);
                    int score = minimax(board, false, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board.remove(r, c);
                    if (score > bestScore) {
                        bestScore = score;
                        move[0] = r;
                        move[1] = c;
                    }
                }
            }
        }
        return move;
    }

    /**
     * Minimax with alpha-beta pruning.
     *
     * @param maximizing true on MAX's turn (X), false on MIN's turn (O)
     * @param depth      plies played so far (used to favour quicker wins)
     */
    public int minimax(XOBoard board, boolean maximizing, int depth, int alpha, int beta) {
        int result = board.getResult();
        if (result == 1)  return WIN_SCORE - depth;   // MAX formed XOX
        if (result == -1) return depth - WIN_SCORE;   // MIN formed OXO
        if (board.isFull()) return 0;                 // draw

        if (maximizing) {
            int best = Integer.MIN_VALUE;
            for (int r = 0; r < board.rows(); r++)
                for (int c = 0; c < board.cols(); c++)
                    if (board.isEmpty(r, c)) {
                        board.place(r, c, XOBoard.MAX);
                        best = Math.max(best, minimax(board, false, depth + 1, alpha, beta));
                        board.remove(r, c);
                        alpha = Math.max(alpha, best);
                        if (beta <= alpha) return best; // beta cut-off
                    }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int r = 0; r < board.rows(); r++)
                for (int c = 0; c < board.cols(); c++)
                    if (board.isEmpty(r, c)) {
                        board.place(r, c, XOBoard.MIN);
                        best = Math.min(best, minimax(board, true, depth + 1, alpha, beta));
                        board.remove(r, c);
                        beta = Math.min(beta, best);
                        if (beta <= alpha) return best; // alpha cut-off
                    }
            return best;
        }
    }
}
