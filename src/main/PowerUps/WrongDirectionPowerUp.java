package main.PowerUps;

import main.PowerUps.PowerUp;

public class WrongDirectionPowerUp extends PowerUp {
    public WrongDirectionPowerUp() {
        counter = 10;
    }

    public int getDirection(int dir) {
        return (dir + 2) % 4;
    }
}
