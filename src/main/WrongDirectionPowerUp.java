package main;

public class WrongDirectionPowerUp extends PowerUp {
    private int counter;


    public WrongDirectionPowerUp() {
        counter = 10;
    }


    public int getDirection(int dir) {
        return (dir + 2) % 4;
    }


    public void tick(Game game) {
        --counter;
        if (counter == 0) {
            game.powerUp = new PowerUp();
        }
    }
}
