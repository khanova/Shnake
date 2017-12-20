package main.PowerUps;

import main.Game;
import main.PowerUps.PowerUp;
import main.Snake;

public class SpeedPowerUp extends PowerUp {
    public SpeedPowerUp() {
        counter = 10;
    }

    public void start(Snake snake) {
        snake.setSpeed(1);
    }

    public void finish(Snake snake) {
        snake.setSpeed(2);
    }
}
