package main.Objects;

import main.*;

public class AppleBig extends Apple  {
    public AppleBig(Point pos, Field field) {
        super(pos, field);
        ticks = 0;
    }

    private static final int MAX_TICK = 15;
    private int ticks;

    public void eatEffect(Snake snake) {
        snake.addGrowth(2);
    }

    public void tick(Game game) {
        ticks++;
        if (ticks == MAX_TICK) {
            game.removeEntity(this);
            game.addEntity(new ApplePoison(position, field));
        }
    }

    public Integer getSpriteId() {
        return 3;
    }
}
