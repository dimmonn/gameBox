package com.local.boxes.model;


public class Payload {
    private final Integer reward;
    private final SIGNS sign;

    private Payload(PayloadBuilder pb) {
        this.reward = pb.reward;
        this.sign = pb.sign;
    }

    Integer getReward() {
        return reward;
    }

    SIGNS getSign() {
        return sign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payload payload = (Payload) o;

        if (!reward.equals(payload.reward)) return false;
        return sign == payload.sign;
    }

    @Override
    public int hashCode() {
        int result = reward.hashCode();
        result = 31 * result + sign.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "reward=" + reward +
                ", sign=" + sign +
                '}';
    }

    static class PayloadBuilder {
        private final Integer reward;
        private final SIGNS sign;

        PayloadBuilder(Integer reward, SIGNS sign) {
            this.reward = reward;
            this.sign = sign;
        }

        Payload build() {
            return new Payload(this);
        }
    }
}
