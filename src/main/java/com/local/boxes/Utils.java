package com.local.boxes;

import com.local.boxes.model.Box;
import com.local.boxes.model.SIGNS;

import java.util.*;

import static com.local.boxes.model.SIGNS.GO_GO_GO;

class Utils {

    private List<SIGNS> secondChanceNewLife;
    private Stack<Box> shuffled;
    private Stack<Box> secondChanceShuffled;
    private Map<Integer, Integer> bonuses;
    private List<SIGNS> signs;
    private Map<Integer, Integer> secondChanceAward;

    void basicSetup() {
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
        Collections.shuffle(shuffled);
        return shuffled;
    }

    public Stack<Box> getSecondChanceShuffled() {
        Collections.shuffle(secondChanceShuffled);
        return secondChanceShuffled;
    }

    public List<SIGNS> getSecondChanceNewLife() {
        return new ArrayList<>(secondChanceNewLife);
    }

    public Map<Integer, Integer> getBonuses() {
        return new HashMap<>(bonuses);
    }

    public List<SIGNS> getSigns() {
        return new ArrayList<>(signs);
    }

    public Map<Integer, Integer> getSecondChanceAward() {
        return new HashMap<>(secondChanceAward);
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
}
