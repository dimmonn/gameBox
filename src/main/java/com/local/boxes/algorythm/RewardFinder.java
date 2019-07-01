package com.local.boxes.algorythm;

import com.local.boxes.model.Box;
import lombok.extern.log4j.Log4j;

import java.util.*;

import static com.local.boxes.model.SIGNS.*;

@Log4j
public class RewardFinder implements BoxGameRunnable {
    private int result;

    @Override
    public void playGame(Stack<Box> shuffled, Stack<Box> secondChanceShuffled) {
        log.info("SHOW TIME...");
        int secondRoundSize = secondChanceShuffled.size();
        Collections.shuffle(shuffled);
        List<Box> boxes = new ArrayList<>(shuffled);
        Box extraLifeBox = new Box().createBox(0, EXTRA_LIFE);
        Box gameOverBox = new Box().createBox(0, GAME_OVER);
        if (boxes.indexOf(extraLifeBox) > boxes.indexOf(gameOverBox)) {
            int sum = shuffled.stream().mapToInt(Box::getReward).sum();
            result += sum;
            log.warn("round result is " + sum);
        } else {
            List<Box> firstRound = boxes.subList(boxes.indexOf(gameOverBox), boxes.size());
            int sum = firstRound.stream().mapToInt(Box::getReward).sum();
            result += sum;
            log.warn("round result is " + sum);
        }
        if (secondChanceShuffled.size() < secondRoundSize) {
            return;
        }
        Box pop = secondChanceShuffled.pop();
        if (pop.getSign() == EXTRA_LIFE) {
            playGame(shuffled, secondChanceShuffled);
        } else {
            int reward = pop.getReward();
            result += reward;
            log.warn("round result is " + reward);
        }
        log.warn("FINISHED THE GAME WITH RESULT OF: " + result);
    }

    @Override
    public int getResult() {
        return result;
    }

}
