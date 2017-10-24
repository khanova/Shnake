package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.*;

public class Board extends JPanel implements ActionListener {

    private static final int[] directions = new int[]{VK_RIGHT, VK_DOWN, VK_LEFT, VK_UP};
    private static final int DELAY = 500;
    private Snake snake;

    private int tileWidth;
    private int tileHeight;

    private Image head, apple, body, grass;
    private Field field;
    private Timer timer;

    public Board() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        initBoard();
        loadImages();
    }

    private void initBoard() {
        tileWidth = 100;
        tileHeight = 100;
         field = new Field(5, 5, true);
         snake = field.spawnSnake(new Point(1, 2), 0);
         field.spawnRandomApple();
         timer = new Timer(DELAY, this);
         timer.start();
         setPreferredSize(new Dimension(100 * field.getWidth(), 100 * field.getHeight()));
    }

    private void loadImages() {
        ImageIcon ii = new ImageIcon("src/images/head.png");
        head = ii.getImage();
        ii = new ImageIcon("src/images/apple.png");
        apple = ii.getImage();
        ii = new ImageIcon("src/images/body.png");
        body = ii.getImage();
        ii = new ImageIcon("src/images/field.png");
        grass = ii.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (!field.getLost())
        {
            ArrayList<ArrayList<Entity>> rectangle = field.toRectangle();

            for (int x = 0; x < rectangle.size(); ++x) {
                for (int y = 0; y < rectangle.get(x).size(); ++y) {
                    Entity entity = rectangle.get(x).get(y);
                    if (entity instanceof Snake) {
                        drawImage(body, x, y, g);
                    }
                    else if (entity instanceof Apple) {
                        drawImage(apple, x, y, g);
                    }
                    else
                        drawImage(grass, x, y, g);
                }
            }
            drawImage(head, snake.getX(), snake.getY(), g);
        }
    }


    void drawImage(Image image, int x, int y, Graphics g)
    {
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

            for (int i = 0; i < directions.length; ++i){
                    if (directions[i] == key) {
                        snake.setDirection(i);
                    }
                }
        }
    }
}

