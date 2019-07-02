package com.local.boxes;

import com.local.boxes.algorythm.BoxGameContext;
import com.local.boxes.algorythm.RewardFinder;

public class AlgorythmCounterDemo {

    public static void main(String[] args) {
        Utils demoBuildHelper = new Utils();
        demoBuildHelper.basicSetup();
        BoxGameContext boxGameContext = new BoxGameContext(
                new RewardFinder(), demoBuildHelper.getShuffled(),
                demoBuildHelper.getSecondChanceShuffled()
        );
        boxGameContext.superBoxGameRun();

    }
}
