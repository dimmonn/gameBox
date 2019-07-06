package com.local.boxes.shuffle;

import com.local.boxes.model.Box;

import java.util.List;

public interface Shuffleable {
    void shuffle(List<? extends Box> toShuffle);

}
