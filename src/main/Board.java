package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.awt.event.KeyEvent.*;

public class Board extends JPanel implements ActionListener {
    private static final int[] directions = new int[]{VK_RIGHT, VK_DOWN, VK_LEFT, VK_UP};
    private static final int DELAY = 500;

    private int tileWidth;
    private int tileHeight;

    private TextureManager textureManager;
    private List<Sprite> sprites;
    private Snake snake;
    private Field field;
    private Timer timer;

    public Board() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        initBoard();
    }

    private void initBoard() {
        tileWidth = 100;
        tileHeight = 100;

        field = new Field(5, 5, true);
        snake = field.spawnSnake(new Point(1, 2), 0);
        field.spawnRandomApple();

        textureManager = new TextureManager();
        sprites = new ArrayList<>();

        timer = new Timer(DELAY, this);
        timer.start();
        setPreferredSize(new Dimension(tileWidth * field.getWidth(), tileHeight * field.getHeight()));
    }

    private void updateSprites() {
        List<Entity> allEntities = field.getAllEntities();
        List<Sprite> newSprites = new ArrayList<>();
        List<Entity> accountedEntities = new ArrayList<>();
        for (Sprite sprite: sprites) {
            accountedEntities.add(sprite.entity);
            if (allEntities.contains(sprite.getEntity())) {
                newSprites.add(sprite);
            }
        }
        for (Entity entity: allEntities) {
            if (!accountedEntities.contains(entity)) {
                newSprites.add(entity.createSprite());
            }
        }
        sprites = newSprites;
    }

    public TextureManager getTextureManager() {
        return textureManager;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!field.getLost()) {
            doDrawing(g);
        }
    }

    private void doDrawing(Graphics g) {
        updateSprites();
        for (int x = 0; x < field.getWidth(); x++) {
            for (int y = 0; y < field.getHeight(); y++) {
                drawImage(textureManager.getGrass(), x, y, g);
            }
        }
        for (Sprite sprite: sprites) {
            sprite.draw(g, this);
        }
    }

    public void drawImage(Image image, int x, int y, Graphics g) {
        g.drawImage(image, x * tileWidth, y * tileHeight, this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        field.tick();
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            for (int i = 0; i < directions.length; ++i) {
                    if (directions[i] == key) {
                        snake.setDirection(i);
                    }
                }
        }
    }
}

