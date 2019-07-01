package com.local.boxes;
import com.local.boxes.factory.GameFactory;
import com.local.boxes.model.SIGNS;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

public class SimulateTheGame {
    public static void main(String[] args) {
        Game gameInstance = setUp();
        gameInstance.playRound(false);
    }

    private static Game setUp() {
        Map<Integer, Integer> bonuses = new HashMap<Integer, Integer>() {{
            put(100, 1);
            put(20, 2);
            put(5, 5);
        }};
        List<SIGNS> signs = new ArrayList<SIGNS>() {{
            add(SIGNS.EXTRA_LIFE);
            add(SIGNS.GAME_OVER);
            add(SIGNS.GAME_OVER);
            add(SIGNS.GAME_OVER);
        }};

        Map<Integer, Integer> secondChanceAward = new HashMap<Integer, Integer>() {{
            put(5, 1);
            put(10, 1);
            put(20, 1);
        }};
        List<SIGNS> secondChanceNewLife = new ArrayList<SIGNS>() {{
            add(SIGNS.EXTRA_LIFE);
        }};
        return new GameFactory().getGameInstance(bonuses, signs,secondChanceAward,secondChanceNewLife);
    }


}
