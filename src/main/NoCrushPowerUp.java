package main;

public class NoCrushPowerUp extends PowerUp {
    private int counter;
    public NoCrushPowerUp() {
        counter = 10;
    }

    @Override
    public void crush(Game game) {

    }

    @Override
    public void tick(Game game) {
        --counter;
        if (counter == 0)
            game.powerUp = new PowerUp();
    }
}
