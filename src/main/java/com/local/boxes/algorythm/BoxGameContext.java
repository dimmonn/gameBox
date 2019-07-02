package com.local.boxes.algorythm;

import com.local.boxes.model.Box;

import java.util.Stack;

public class BoxGameContext {
    private final BoxGameRunnable boxGameRunnable;
    private final Stack<Box> shuffled;
    private final Stack<Box> secondChanceShuffled;

    public BoxGameContext(BoxGameRunnable boxGameRunnable, Stack<Box> shuffled, Stack<Box> secondChanceShuffled) {
        this.boxGameRunnable = boxGameRunnable;
        this.shuffled = shuffled;
        this.secondChanceShuffled = secondChanceShuffled;
    }

    public void superBoxGameRun() {
        boxGameRunnable.playGame(shuffled, secondChanceShuffled);
    }

    public int getResult() {
        return boxGameRunnable.getResult();
    }
}
