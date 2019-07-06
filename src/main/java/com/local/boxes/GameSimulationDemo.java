package com.local.boxes;

import com.local.boxes.factory.GameFactory;
import com.local.boxes.shuffle.BasicShuffle;

class GameSimulationDemo {
    public static void main(String[] args) {
        Utils demoBuildHelper = new Utils();
        demoBuildHelper.basicSetup();
        Game game = new GameFactory().getGameInstance(
                demoBuildHelper.getBonuses(), demoBuildHelper.getSigns(), demoBuildHelper.getSecondChanceAward(),
                demoBuildHelper.getSecondChanceNewLife(), new BasicShuffle(), true
        );
        game.playRound(false);
    }


}
