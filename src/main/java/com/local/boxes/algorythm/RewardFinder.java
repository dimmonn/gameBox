package com.local.boxes.algorythm;

import com.local.boxes.model.Box;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

import static com.local.boxes.model.SIGNS.EXTRA_LIFE;
import static com.local.boxes.model.SIGNS.GAME_OVER;

@Log4j
public class RewardFinder implements BoxGameRunnable {
    private int result;
    private boolean firstRoundState = true;
    private List<Box> boxes;
    private Box extraLifeBox;
    private Box gameOverBox;
    private Box secondChanceBox;

    @Override
    public void playGame(Stack<Box> shuffled, Stack<Box> secondChanceShuffled) {
        log.info("SHOW TIME...");
        init(shuffled, secondChanceShuffled);
        if (boxes.indexOf(extraLifeBox) > boxes.indexOf(gameOverBox)) {
            countSumOfAwards(shuffled.stream());
        } else {
            List<Box> firstRound = boxes.subList(boxes.indexOf(gameOverBox), boxes.size());
            countSumOfAwards(firstRound.stream());
        }
        if (!firstRoundState) {
            return;
        }
        runSecondRound(shuffled, secondChanceShuffled);
        log.warn("FINISHED THE GAME WITH RESULT OF: " + result);
    }

    private void runSecondRound(Stack<Box> shuffled, Stack<Box> secondChanceShuffled) {
        firstRoundState = false;
        if (secondChanceBox.getSign() == EXTRA_LIFE) {
            playGame(shuffled, secondChanceShuffled);
        } else {
            int reward = secondChanceBox.getReward();
            result += reward;
            log.warn("round result is " + reward);
        }
    }

    private void init(Stack<Box> shuffled, Stack<Box> secondChanceShuffled) {
        boxes = new ArrayList<>(shuffled);
        extraLifeBox = new Box().createBox(0, EXTRA_LIFE);
        gameOverBox = new Box().createBox(0, GAME_OVER);
        secondChanceBox = secondChanceShuffled.pop();
    }

    private void countSumOfAwards(Stream<Box> stream) {
        int sum = stream.mapToInt(Box::getReward).sum();
        result += sum;
        log.warn("round result is " + sum);
    }

    @Override
    public int getResult() {
        return result;
    }

}
