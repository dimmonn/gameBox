package com.local.boxes.shuffle;


import com.local.boxes.model.Box;

import java.util.List;

public class ShuffleContext {
    private Shuffleable collectionShuffleable;

    public ShuffleContext(Shuffleable collectionShuffleable) {
        this.collectionShuffleable = collectionShuffleable;
    }
    public void shuffle(List<? extends Box> toShuffle){
        collectionShuffleable.shuffle(toShuffle);
    }
}
