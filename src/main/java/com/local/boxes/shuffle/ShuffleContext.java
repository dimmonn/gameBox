package com.local.boxes.shuffle;


import com.local.boxes.model.Box;

import java.util.List;

class ShuffleContext {
    private final Shuffleable collectionShuffleable;

    public ShuffleContext(Shuffleable collectionShuffleable) {
        this.collectionShuffleable = collectionShuffleable;
    }
    public void shuffle(List<? extends Box> toShuffle){
        collectionShuffleable.shuffle(toShuffle);
    }
}
