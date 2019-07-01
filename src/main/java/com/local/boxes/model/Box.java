package com.local.boxes.model;

public class Box {
    private Payload payload;

    public Box() {

    }

    private Box(Payload payload) {
        this.payload = payload;
    }

    public Box(Box another) {
        this.payload = another.payload;
    }

    public int getReward() {
        return payload.getReward();
    }

    public SIGNS getSign() {
        return payload.getSign();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Box box = (Box) o;

        return payload.equals(box.payload);
    }

    @Override
    public int hashCode() {
        return payload.hashCode();
    }

    public Box createBox(Integer bonus, SIGNS sign) {
        return new Box(new Payload.PayloadBuilder(bonus, sign).build());
    }

    @Override
    public String toString() {
        return "Box{" +
                "payload=" + payload +
                '}';
    }
}
