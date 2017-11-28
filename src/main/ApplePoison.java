package main;

public class ApplePoison extends Apple {
    public ApplePoison(Point pos, Field field) {
        super(pos, field);
    }

    public void eatEffect(Game game) {
        game.addGrowth(-1);
        game.addPoints(-2);
    }

    public void tick(Game game) {
    }

    @Override
    public Sprite createSprite() {
        return new ApplePoisonSprite(this);
    }
}
