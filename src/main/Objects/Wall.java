package main.Objects;

import main.*;

public class Wall extends Entity implements Edible {
    public Wall(Point pos) {
        position = pos;
    }

    public void tick(Game game) {

    }

    public void eatEffect(Snake snake) {
        snake.eatWall();
    }

    public Integer getSpriteId() {
        return 6;
    }
}
