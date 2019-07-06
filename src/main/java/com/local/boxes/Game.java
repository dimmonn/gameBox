package com.local.boxes;

import com.local.boxes.model.Box;
import com.local.boxes.model.SIGNS;
import com.local.boxes.shuffle.Shuffleable;
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
    private final List<Box> secondChance;
    private Stack<Box> shuffled;
    private Stack<Box> secondChanceShuffled;
    private int result = 0;
    private boolean isFinished = true;

    private Game(GameBuilder gameBuilder) {
        this.boxes = gameBuilder.boxes;
        this.shuffled = gameBuilder.shuffled;
        this.secondChance = gameBuilder.secondChance;
        this.secondChanceShuffled = gameBuilder.secondChanceShuffled;

    }

    Stack<Box> getShuffled() {
        Stack<Box> shuffledCopy = new Stack<>();
        shuffledCopy.addAll(shuffled);
        return shuffledCopy;
    }

    Stack<Box> getSecondChanceShuffled() {
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

    int getResult() {
        return result;
    }

    void playRound(boolean disableShuffleOnTheSecondTry) {
        isFinished = false;
        log.warn("starting the game, second shuffle is disabled: " + disableShuffleOnTheSecondTry);
        int gameOver = 0;
        for (int i = 0; i < getAvailableBoxes(); i++) {
            Box boxFromShuffledDeck = getBoxFromShuffledDeck(true);
            i--;
            if (boxFromShuffledDeck.getSign() == GO_GO_GO) {
                result += boxFromShuffledDeck.getReward();
            } else if (boxFromShuffledDeck.getSign() == GAME_OVER) {
                gameOver++;
                if (gameOver == 3) {
                    log.warn("three subsequent game-over sign ends first round");
                    secondChanceRun(disableShuffleOnTheSecondTry);
                    isFinished = true;
                    log.warn("FINISHED THE GAME WITH RESULT OF: " + result);
                    return;
                }
            } else {
                gameOver--;
            }
        }
        secondChanceRun(disableShuffleOnTheSecondTry);
        isFinished = true;
    }

    void resetAndShuffle(Shuffleable shuffleable) {
        if (isFinished) {
            shuffled = new Stack<>();
            shuffled.addAll(boxes);
            shuffleable.shuffle(shuffled);
            secondChanceShuffled = new Stack<>();
            secondChanceShuffled.addAll(secondChance);
            shuffleable.shuffle(secondChanceShuffled);
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

    @Override
    public String toString() {
        return "Game{" +
                "boxes=" + boxes +
                ", shuffled=" + shuffled +
                ", secondChance=" + secondChance +
                ", secondChanceShuffled=" + secondChanceShuffled +
                ", result=" + result +
                ", isFinished=" + isFinished +
                '}';
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

        public Game build(boolean shuffleOnStart, Shuffleable shuffleable) {
            shuffled.addAll(boxes);
            secondChanceShuffled.addAll(secondChance);
            if (shuffleOnStart) {
                shuffleable.shuffle(shuffled);
                shuffleable.shuffle(secondChanceShuffled);
            }
            return new Game(this);
        }
    }
}
