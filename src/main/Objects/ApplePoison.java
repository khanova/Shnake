package main.Objects;

import main.*;
import main.PowerUps.WrongDirectionPowerUp;

public class ApplePoison extends Apple  {
    public ApplePoison(Point pos, Field field) { super(pos, field); }

    public void eatEffect(Snake snake) {
        snake.addGrowth(1);
        snake.setPowerUp(new WrongDirectionPowerUp());
    }

    public void tick(Game game) {
    }

    public Integer getSpriteId() {
        return 5;
    }
}
