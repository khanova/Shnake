package main;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Console {
    private static final String directions = "sdwa";

    public static void printField(Field field) {
        System.out.println();
        List<List<Entity>> rectangle = field.toRectangle();
        for (List<Entity> line: rectangle) {
            for (Entity entity: line) {
                if (entity == null) {
                    System.out.print('.');
                }
                else if (entity instanceof Snake) {
                    System.out.print('#');
                }
                else if (entity instanceof Apple) {
                    System.out.print('O');
                }
            }
            System.out.println();
        }
    }





    public static void main(String[] args) {
        Field field = new Field(5, 5, true);
        Snake snake = field.spawnSnake(new Point(1, 2), 0);
        field.spawnRandomApple();
        printField(field);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                field.tick();
                if (field.getLost())
                    timer.cancel();

                printField(field);
            }
        }, 1000, 1000);

        int code;
        while (!field.getLost()) {
            try {
                code = System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            if (code != -1) {
                char c = (char) code;
                for (int i = 0; i < directions.length(); ++i){
                    if (directions.charAt(i) == c) {
                        snake.setDirection(i);
                    }
                }
            }
        }
    }
}
