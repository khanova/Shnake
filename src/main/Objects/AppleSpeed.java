package main.Objects;

import main.*;
import main.PowerUps.SpeedPowerUp;

public class AppleSpeed extends Apple  {
    public AppleSpeed(Point pos, Field field) {
        super(pos, field);
    }

    public void eatEffect(Snake snake) {
        snake.addGrowth(1);
        snake.setPowerUp(new SpeedPowerUp());
    }

    public Integer getSpriteId() {
        return 8;
    }
}
