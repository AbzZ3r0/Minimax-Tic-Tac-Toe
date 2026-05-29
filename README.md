# XOX vs OXO — Minimax Game

A Java + web implementation of a **tic-tac-toe variant** played against an
unbeatable **Minimax** opponent with alpha-beta pruning.

It is not standard tic-tac-toe: a line of three only counts if it spells a
specific pattern. **MAX** plays `X` and wins by forming **X-O-X**; **MIN** plays
`O` and wins by forming **O-X-O**, along any row, column or diagonal on a 4×3
board.

The project ships in two flavours: a **Java command-line game** (the reference
implementation) and an **interactive web version** you can play in the browser.

**▶ Live demo:** https://abzz3r0.github.io/Minimax-Tic-Tac-Toe/

> Originally built as part of an Artificial Intelligence course assignment, then
> cleaned up and extended into a standalone project.

## How to play

1. You **seed** the board with one starting `X` and one starting `O` — the `O`
   must not be adjacent to the `X`.
2. From there the computer plays as **MAX (`X`)** and moves first; you respond as
   **MIN (`O`)**.
3. First side to complete its pattern (`XOX` for the AI, `OXO` for you) wins;
   a full board with no pattern is a draw.

## Features

- **Minimax with alpha-beta pruning** — explores the game tree to the end and
  prunes branches that cannot change the outcome, returning the exact same move
  far faster.
- **Depth-aware scoring** — earlier wins score higher and losses are pushed back,
  so the AI plays decisively instead of stalling on a won position.
- **Provably optimal** — verified two ways: alpha-beta returns the identical
  value to plain minimax across hundreds of positions, and across thousands of
  self-play games the AI never loses _unless the seed position was already lost._
- **Interactive web UI** with move animations and a highlighted winning line.

## A note on the "seed"

Because the player places both starting symbols, the opening is not symmetric —
some seeds are already winning for `O` no matter how the AI replies (about a
fifth of random non-adjacent seeds, in testing). The AI guarantees optimal play
_from the play phase onward_; it cannot rescue a position that is already lost.

## How Minimax works here

Each empty cell is a possible move. The algorithm assumes both sides play
perfectly: MAX picks the move with the highest score, MIN the lowest. The score
of a finished game is `+1` if MAX made `XOX`, `-1` if MIN made `OXO`, `0` for a
draw — adjusted by depth so quicker wins are preferred. **Alpha-beta** carries the
best score each side is already guaranteed (`alpha` for MAX, `beta` for MIN) and
stops exploring a branch as soon as it cannot beat that bound.

## Getting started

### Web version (no install)

Open `index.html` in any modern browser, or host it for free on **GitHub Pages**
(_Settings → Pages → deploy from branch → root_).

### Java command-line version

Requires a JDK (developed on Java 21).

```bash
javac *.java
java XOGame
```

## Example (console)

```
       1   2   3
     +---+---+---+
  1  | X | . | X |
     +---+---+---+
  2  | . | X | . |
     +---+---+---+
  3  | . | . | O |
     +---+---+---+
  4  | O | . | . |
     +---+---+---+

  MAX is thinking...
  MAX plays (1, 3)
```

## Project structure

```
xox-minimax/
├── index.html          # interactive web version (GitHub Pages ready)
├── XOBoard.java         # the 4x3 board, rules and win detection
├── MinimaxPlayer.java   # minimax + alpha-beta + depth-aware scoring
├── XOGame.java          # console game loop
└── README.md
```

## License

Released under the [MIT License](LICENSE) — free to use, modify and share,
provided the copyright notice is kept.

## Authors
