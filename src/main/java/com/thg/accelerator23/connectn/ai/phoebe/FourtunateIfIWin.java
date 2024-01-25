package com.thg.accelerator23.connectn.ai.phoebe;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.Random;

public class FourtunateIfIWin extends Player {
  public FourtunateIfIWin(Counter counter) {
    //TODO: fill in your name here
    super(counter, FourtunateIfIWin.class.getName());
  }

  @Override
  public int makeMove(Board board) {
    Random random = new Random();
    int randomIndex = random.nextInt(board.getConfig().getWidth());

    while (!isValidMove(board, randomIndex) || isColumnFull(board, randomIndex) || !isWinningMove(board, randomIndex))
    {
      randomIndex = random.nextInt(board.getConfig().getWidth());
    }
    return randomIndex;
  }

  private boolean isValidMove(Board board, int col) {
    return !board.hasCounterAtPosition(new Position(col, board.getConfig().getHeight() - 1));
  }

  private boolean isColumnFull(Board board, int col) {
    int row = 0;
    while (row < board.getConfig().getHeight() && board.hasCounterAtPosition(new Position(col, row))) {
      row++;
    }
    return row == board.getConfig().getHeight();
  }
  private boolean isWinningMove(Board board, int col) {
    int row = findEmptyRow(board, col);
    if (row == -1) {
      return false;  // Column is full, not a winning move
    }

    return isFourInARowHorizontally(board, row) ||
            isFourInARowVertically(board, col) ||
            isFourInARowDiagonally(board, row, col);
  }

  private boolean isFourInARowHorizontally(Board board, int row) {
    int count = 0;
    Counter player = board.getCounterAtPosition(new Position(0, row));

    for (int col = 0; col < board.getConfig().getWidth(); col++) {
      if (board.getCounterAtPosition(new Position(col, row)).equals(player)) {
        count++;
        if (count == 4) {
          return true;
        }
      } else {
        count = 0;
        player = board.getCounterAtPosition(new Position(col, row));
      }
    }

    return false;
  }
  private boolean isFourInARowVertically(Board board, int col) {
    int count = 0;
    Counter player = board.getCounterAtPosition(new Position(col, findEmptyRow(board, col)));

    for (int row = 0; row < board.getConfig().getHeight(); row++) {
      if (board.getCounterAtPosition(new Position(col, row)).equals(player)) {
        count++;
        if (count == 4) {
          return true;
        }
      } else {
        count = 0;
        player = board.getCounterAtPosition(new Position(col, row));
      }
    }

    return false;
  }

  private boolean isFourInARowDiagonally(Board board, int row, int col) {
    int count = 1;
    count += countConsecutiveDiagonals(board, row - 1, col - 1, -1, -1);
    count += countConsecutiveDiagonals(board, row + 1, col - 1, 1, -1);
    return count >= 4;
  }

  private int countConsecutiveDiagonals(Board board, int startRow, int startCol, int rowDirection, int colDirection) {
    Counter player = board.getCounterAtPosition(new Position(startCol, startRow));
    int count = 0;

    while (startRow >= 0 && startRow < board.getConfig().getHeight() &&
            startCol >= 0 && startCol < board.getConfig().getWidth() &&
            board.getCounterAtPosition(new Position(startCol, startRow)).equals(player)) {
      count++;
      startRow += rowDirection;
      startCol += colDirection;
    }

    return count;
  }

  private int findEmptyRow(Board board, int col) {
    for (int row = board.getConfig().getHeight() - 1; row >= 0; row--) {
      if (!board.hasCounterAtPosition(new Position(col, row))) {
        return row;
      }
    }
    return -1;
  }
}
