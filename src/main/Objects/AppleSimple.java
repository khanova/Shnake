package main.Objects;

import main.*;

public class AppleSimple extends Apple  {
    public AppleSimple(Point pos, Field field) {
        super(pos, field);
    }

    public void eatEffect(Snake snake) {
        snake.addGrowth(1);
    }

    public void tick(Game game) {
    }

    public Integer getSpriteId() {
        return 2;
    }
}
