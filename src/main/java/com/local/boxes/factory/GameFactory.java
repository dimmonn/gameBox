package com.local.boxes.factory;

import com.local.boxes.Game;
import com.local.boxes.model.SIGNS;
import com.local.boxes.model.Box;

import java.util.List;
import java.util.Map;

import static com.local.boxes.model.SIGNS.GO_GO_GO;


public class GameFactory {
    private final Game.GameBuilder gameBuilder = new Game.GameBuilder();

    public Game getGameInstance(Map<Integer, Integer> bonuses, List<SIGNS> signs, Map<Integer, Integer> secondChanceAward, List<SIGNS> secondChanceSigns) {
        fillInAwards(bonuses, true);
        fillInSigns(signs, true);
        fillInAwards(secondChanceAward, false);
        fillInSigns(secondChanceSigns, false);
        return gameBuilder.build();
    }

    private void fillInSigns(List<SIGNS> signs, boolean isFfirstChance) {
        for (SIGNS sign : signs) {
            if (isFfirstChance) {
                gameBuilder.addBox(new Box().createBox(0, sign));
            } else {
                gameBuilder.addSecondChance(new Box().createBox(0, sign));
            }
        }
    }

    private void fillInAwards(Map<Integer, Integer> bonuses, boolean isFfirstChance) {
        for (Integer bonus : bonuses.keySet()) {
            for (int i = 0; i < bonuses.get(bonus); i++) {
                if (isFfirstChance) {
                    gameBuilder.addBox(new Box().createBox(bonus, GO_GO_GO));
                }
                else {
                    gameBuilder.addSecondChance(new Box().createBox(bonus, GO_GO_GO));
                }
            }
        }
    }

}
