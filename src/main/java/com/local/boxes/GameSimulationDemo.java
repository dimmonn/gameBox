package com.local.boxes;

import com.local.boxes.factory.GameFactory;

public class GameSimulationDemo {
    public static void main(String[] args) {
        Utils demoBuildHelper = new Utils();
        demoBuildHelper.basicSetup();
        Game game = new GameFactory().getGameInstance(
                demoBuildHelper.getBonuses(), demoBuildHelper.getSigns(), demoBuildHelper.getSecondChanceAward(),
                demoBuildHelper.getSecondChanceNewLife(), true
        );
        game.playRound(false);
    }


}
