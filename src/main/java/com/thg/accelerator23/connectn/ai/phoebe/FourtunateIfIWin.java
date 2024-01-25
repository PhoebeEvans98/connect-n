package com.thg.accelerator23.connectn.ai.phoebe;

import com.thehutgroup.accelerator.connectn.player.Board;
import com.thehutgroup.accelerator.connectn.player.Counter;
import com.thehutgroup.accelerator.connectn.player.Player;
import com.thehutgroup.accelerator.connectn.player.Position;

import java.util.Random;

public class FourtunateIfIWin extends Player {
  public FourtunateIfIWin(Counter counter) {
    //TODO: fill in your name here
    super(counter, PhoebeAI.class.getName());
  }
  @Override
  public int makeMove(Board board) {
    Random random = new Random();
    int randomIndex = random.nextInt(board.getConfig().getWidth());
    while (!isValidMove(board, randomIndex)) {
      randomIndex = random.nextInt(board.getConfig().getWidth());
    }

    return randomIndex;
  }
  private boolean isValidMove(Board board, int col) {
    return !board.hasCounterAtPosition(new Position(col, board.getConfig().getHeight() - 1));
  }
}
