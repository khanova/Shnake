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

    private Image head, apple;
    private Field field;
    private Timer timer;

    public Board() {
        addKeyListener(new TAdapter());
        setBackground(Color.white);
        setFocusable(true);

        setPreferredSize(new Dimension(1000, 1000));
        loadImages();
        initBoard();
    }

    private void initBoard() {
         field = new Field(5, 5, true);
         snake = field.spawnSnake(new Point(1, 2), 0);
         field.spawnRandomApple();
         timer = new Timer(DELAY, this);
         timer.start();


    }

    private void loadImages() {

        ImageIcon ii = new ImageIcon("src/images/head.png");
        head = ii.getImage();
        ii = new ImageIcon("src/images/apple.png");
        apple = ii.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDriwing(g);
    }

    private void doDriwing(Graphics g) {
        if (!field.getLost())
        {
            ArrayList<ArrayList<Entity>> rectangle = field.toRectangle();
            for (int x = 0; x < rectangle.size(); ++x) {
                for (int y = 0; y < rectangle.get(x).size(); ++y) {
                    Entity entity = rectangle.get(x).get(y);
                    if (entity instanceof Snake) {
                        g.drawImage(head, x * 100, y * 100, this);
                    }
                    else if (entity instanceof Apple) {
                        g.drawImage(apple, x * 100, y * 100, this);
                    }
                }
            }

        }
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
                        snake.SetDirection(i);
                    }
                }
        }
    }
}

