package main;

public class Balloon extends Apple{
    public Balloon(Point pos, Field field) {
        super(pos, field);
    }
    public void eatEffect(Game game) {
        game.addGrowth(0);
        game.addPoints(0);
        game.powerUp = new NoCrushPowerUp();
    }
    @Override
    public Sprite createSprite() {
        return new AppleSprite(this, (textureManager -> textureManager.getBalloon()));
    }
}
