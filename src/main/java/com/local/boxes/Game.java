package com.local.boxes;

import com.local.boxes.model.Box;
import com.local.boxes.model.SIGNS;
import lombok.extern.log4j.Log4j;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static com.local.boxes.model.SIGNS.GAME_OVER;
import static com.local.boxes.model.SIGNS.GO_GO_GO;

@Log4j
public class Game {
    private final List<Box> boxes;
    private Stack<Box> shuffled;
    private final List<Box> secondChance;
    private Stack<Box> secondChanceShuffled;
    private int result = 0;
    private boolean isFinished = true;

    public Stack<Box> getShuffled() {
        Stack<Box> shuffledCopy = new Stack<>();
        shuffledCopy.addAll(shuffled);
        return shuffledCopy;
    }

    public Stack<Box> getSecondChanceShuffled() {
        Stack<Box> secondChanceShuffledCopy = new Stack<>();
        secondChanceShuffledCopy.addAll(secondChanceShuffled);
        return secondChanceShuffledCopy;
    }

    private int getSecondChanceSize() {
        return secondChance.size();
    }

    private int getAvailableBoxes() {
        return shuffled.size();
    }

    private Box getBoxFromShuffledDeck(boolean isFirstChance) {
        if (isFirstChance) {
            log.info("got the box " + shuffled.peek());
            return new Box(shuffled.pop());
        } else {
            log.info("second chance box  " + secondChanceShuffled.peek());
            return new Box(secondChanceShuffled.pop());
        }
    }

    public int getResult() {
        return result;
    }

    public void playRound(boolean disableShuffleOnTheSecondTry) {
        isFinished = false;
        log.warn("starting the game, second shuffle is disabled: " + disableShuffleOnTheSecondTry);
        int gameOver = 0;
        for (int i = 0; i < getAvailableBoxes(); i++) {
            Box boxFromShuffledDeck = getBoxFromShuffledDeck(true);
            i--;
            if (gameOver == 3) {
                secondChanceRun(disableShuffleOnTheSecondTry);
                isFinished = true;
                log.warn("three subsequent game-over sign ends first round");
                log.warn("FINISHED THE GAME WITH RESULT OF: " + result);
                return;
            } else if (boxFromShuffledDeck.getSign() == GO_GO_GO) {
                result += boxFromShuffledDeck.getReward();
            } else if (boxFromShuffledDeck.getSign() == GAME_OVER) {
                gameOver++;
            } else {
                gameOver--;
            }
        }
        secondChanceRun(disableShuffleOnTheSecondTry);
        isFinished = true;
    }

    public void resetAndShuffle() {
        if (isFinished) {
            shuffled = new Stack<>();
            shuffled.addAll(boxes);
            Collections.shuffle(shuffled);
            secondChanceShuffled = new Stack<>();
            secondChanceShuffled.addAll(secondChance);
            Collections.shuffle(secondChanceShuffled);
            log.warn("resetting the game and shuffling the deck");
            result = 0;
        }
    }

    private void secondChanceRun(boolean disableShuffleOnSecondTry) {
        if (secondChanceShuffled.size() < getSecondChanceSize()) {
            return;
        }
        Box boxFromShuffledDeckSecondRun = getBoxFromShuffledDeck(false);
        if (boxFromShuffledDeckSecondRun.getSign() == SIGNS.EXTRA_LIFE) {
            shuffled = new Stack<>();
            shuffled.addAll(boxes);
            if (!disableShuffleOnSecondTry) {
                Collections.shuffle(shuffled);
            }
            playRound(disableShuffleOnSecondTry);
        } else {
            result += boxFromShuffledDeckSecondRun.getReward();
        }
        log.warn("FINISHED THE GAME WITH RESULT OF: " + result);
    }

    private Game(GameBuilder gameBuilder) {
        this.boxes = gameBuilder.boxes;
        this.shuffled = gameBuilder.shuffled;
        this.secondChance = gameBuilder.secondChance;
        this.secondChanceShuffled = gameBuilder.secondChanceShuffled;

    }

    public static class GameBuilder {
        private final List<Box> boxes = new ArrayList<>();
        private final Stack<Box> shuffled = new Stack<>();
        private final List<Box> secondChance = new ArrayList<>();
        private final Stack<Box> secondChanceShuffled = new Stack<>();

        public void addBox(Box box) {
            Box _box = new Box(box);
            boxes.add(_box);
        }

        public void addSecondChance(Box box) {
            Box _box = new Box(box);
            secondChance.add(_box);
        }

        public Game build(boolean shuffleOnStart) {
            shuffled.addAll(boxes);
            secondChanceShuffled.addAll(secondChance);
            if (shuffleOnStart) {
                Collections.shuffle(shuffled);
                Collections.shuffle(secondChanceShuffled);
            }
            return new Game(this);
        }
    }

    @Override
    public String toString() {
        return "Game{" +
                "boxes=" + boxes +
                '}';
    }
}
