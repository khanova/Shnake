package main;

public class AppleBig extends Apple {
    public AppleBig(Point pos, Field field) {
        super(pos, field);
        ticks = 0;
    }

    private static final int MAX_TICK = 5;
    private int ticks;

    public void eatEffect(Game game) {
        game.addGrowth(2);
        game.addPoints(2);
    }

    public void tick(Game game) {
        ticks++;
        if (ticks == MAX_TICK) {
            field.removeEntity(this);
            field.addEntity(new ApplePoison(position, field));
        }
    }

    @Override
    public Sprite createSprite() {
        return new AppleBigSprite(this);
    }
}
