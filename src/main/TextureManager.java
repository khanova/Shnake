package main;

import javax.swing.*;
import java.awt.*;

public class TextureManager {
    private Image head, apple, body, grass;

    public TextureManager() {
        ImageIcon ii = new ImageIcon("src/images/head.png");
        head = ii.getImage();
        ii = new ImageIcon("src/images/apple.png");
        apple = ii.getImage();
        ii = new ImageIcon("src/images/body.png");
        body = ii.getImage();
        ii = new ImageIcon("src/images/field.png");
        grass = ii.getImage();
    }

    public Image getHead() {
        return head;
    }

    public Image getBody() {
        return body;
    }

    public Image getApple() {
        return apple;
    }

    public Image getGrass() {
        return grass;
    }
}
