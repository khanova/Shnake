package main.Objects;

import main.*;
import main.PowerUps.NoCrushPowerUp;

public class Balloon extends Apple {
    public Balloon(Point pos, Field field) {
        super(pos, field);
    }

    public void eatEffect(Snake snake) {
        snake.addGrowth(1);
        snake.setPowerUp(new NoCrushPowerUp());
    }

    public Integer getSpriteId() {
        return 7;
    }
}
