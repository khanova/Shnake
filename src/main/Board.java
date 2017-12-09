package main;

import main.Sprites.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static java.awt.event.KeyEvent.*;
import static java.util.Collections.reverse;

public class Board extends JPanel implements ActionListener {
    private static final int[] directions = new int[]{VK_RIGHT, VK_DOWN, VK_LEFT, VK_UP};
    private static final int DELAY = 250;
    private static final int tileWidth = 100;
    private static final int tileHeight = 100;

    private TextureManager textureManager;
    private List<Sprite> sprites;
    private Timer timer;
    private Game game;

    public Board() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        initBoard();
    }

    private void initBoard() {
        game = new Game(new Field(14,9, true), 2);

        game.spawnSnake(new Point(0, 0), 0);
        game.spawnAutoSnake(new Point(0, 2), 0);

        game.spawnWall(new Point(1, 3));
        game.spawnWall(new Point(3, 1));
        game.spawnWall(new Point(5, 3));
        game.spawnWall(new Point(3, 5));

        game.spawnRandomApple();
        textureManager = new TextureManager();
        sprites = new ArrayList<>();

        timer = new Timer(DELAY, this);
        timer.start();
        setPreferredSize(new Dimension(tileWidth * game.getWidth(), tileHeight * game.getHeight()));
    }

    private void updateSprites() {
        List<Entity> allEntities = game.getField().getAllEntities();
        List<Sprite> newSprites = new ArrayList<>();
        List<Entity> accountedEntities = new ArrayList<>();
        /*for (Sprite sprite: sprites) {
            accountedEntities.add(sprite.entity);
            if (allEntities.contains(sprite.getEntity())) {
                newSprites.add(sprite);
            }
        }
        */
        for (Entity entity: allEntities) {
            if (!accountedEntities.contains(entity)) {
                newSprites.add(entity.createSprite());
            }
        }

        reverse(newSprites);
        sprites = newSprites;
    }

    public TextureManager getTextureManager() {
        return textureManager;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!game.getLost()) {
            doDrawing(g);
        }
    }

    private void doDrawing(Graphics g) {
        updateSprites();
        for (int x = 0; x < game.getWidth(); x++) {
            for (int y = 0; y < game.getHeight(); y++) {
                drawImage(textureManager.getGrass(), x, y, g);
            }
        }
        for (Sprite sprite: sprites) {
            sprite.draw(g, this);
        }
        g.drawString("" + game.getPowerUp().getCounter(), 10, 10);
    }

    public void drawImage(Image image, int x, int y, Graphics g) {
        g.drawImage(image, x * tileWidth, y * tileHeight, this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        game.tick();
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            for (int i = 0; i < directions.length; ++i) {
                    if (directions[i] == key) {
                        game.setDirection(i);
                    }
                }
        }
    }
}

