package main;

public class ApplePoison extends Apple {
    public ApplePoison(Point pos, Field field) {
        super(pos, field);
    }

    public void eatEffect(Game game) {
        game.addGrowth(0);
        game.addPoints(0);
        game.setSpeed(1);
        game.powerUp = new WrongDirectionPowerUp();
    }

    public void tick(Game game) {
    }

    @Override
    public Sprite createSprite() {
        return new AppleSprite(this, (textureManager -> textureManager.getApplePoison()));
    }
}
