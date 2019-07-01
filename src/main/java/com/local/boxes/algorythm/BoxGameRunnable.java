package com.local.boxes.algorythm;

import com.local.boxes.model.Box;

import java.util.Stack;

public interface BoxGameRunnable {
    void playGame(Stack<Box> shuffled, Stack<Box> secondChanceShuffled);

    int getResult();
}
