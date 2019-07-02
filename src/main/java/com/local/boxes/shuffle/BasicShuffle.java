package com.local.boxes.shuffle;

import com.local.boxes.model.Box;

import java.util.Collections;
import java.util.List;

public class BasicShuffle implements Shuffleable {

    @Override
    public void shuffle(List<? extends Box> toShuffle) {
        Collections.shuffle(toShuffle);
    }
}
