package com.local.boxes;

import com.local.boxes.algorythm.BoxGameContext;
import com.local.boxes.algorythm.RewardFinder;
import com.local.boxes.model.Box;
import com.local.boxes.model.SIGNS;

import java.util.*;

import static com.local.boxes.model.SIGNS.GO_GO_GO;

public class AlgorythmCounter {

    private Map<Integer, Integer> bonuses;
    private List<SIGNS> signs;
    private Map<Integer, Integer> secondChanceAward;
    private List<SIGNS> secondChanceNewLife;
    private Stack<Box> shuffled;

    private Stack<Box> secondChanceShuffled;

    void setup() {
        bonuses = new HashMap<Integer, Integer>() {{
            put(100, 1);
            put(20, 2);
            put(5, 5);
        }};
        signs = new ArrayList<SIGNS>() {{
            add(SIGNS.EXTRA_LIFE);
            add(SIGNS.GAME_OVER);
            add(SIGNS.GAME_OVER);
            add(SIGNS.GAME_OVER);
        }};

        secondChanceAward = new HashMap<Integer, Integer>() {{
            put(5, 1);
            put(10, 1);
            put(20, 1);
        }};
        secondChanceNewLife = new ArrayList<SIGNS>() {{
            add(SIGNS.EXTRA_LIFE);
        }};
        shuffled = fillInMockedShuffledDeck(bonuses, signs);
        secondChanceShuffled = fillInMockedShuffledDeck(secondChanceAward, secondChanceNewLife);

    }

    public Stack<Box> getShuffled() {
        return shuffled;
    }

    public Stack<Box> getSecondChanceShuffled() {
        return secondChanceShuffled;
    }

    private Stack<Box> fillInMockedShuffledDeck(Map<Integer, Integer> bonuses, List<SIGNS> signs) {
        Stack<Box> shuffled = new Stack<>();
        for (Integer bonus : bonuses.keySet()) {
            for (int i = 0; i < bonuses.get(bonus); i++) {
                shuffled.add(new Box().createBox(bonus, GO_GO_GO));
            }
        }
        for (SIGNS sign : signs) {
            shuffled.add(new Box().createBox(0, sign));
        }
        return shuffled;
    }

    public static void main(String[] args) {
        AlgorythmCounter algorythmCounter = new AlgorythmCounter();
        algorythmCounter.setup();
        BoxGameContext boxGameContext = new BoxGameContext(new RewardFinder(), algorythmCounter.getShuffled(), algorythmCounter.getSecondChanceShuffled());
        boxGameContext.superBoxGameRun();

    }
}
