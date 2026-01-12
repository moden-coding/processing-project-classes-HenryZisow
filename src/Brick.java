import java.util.ArrayList;

import processing.core.PApplet;

public class Brick {
    private PApplet canvas;
    private float brickX = 50;
    private float brickY = 100;
    private int brickWidth = 50;
    private int brickHeight = 30;
    private int brickCount;
    private float r, g, b;
    private int brickColor;
    private boolean alive = true;
    

    public Brick(PApplet c, float x, float y, int width, int height) {
        canvas = c;
        r = canvas.random(0, 255);
        g = canvas.random(0, 255);
        b = canvas.random(0, 255);
        brickColor = canvas.color(canvas.random(0, 255), canvas.random(0, 255), canvas.random(0, 255));
        this.brickX = x;
        this.brickY = y;
        this.brickWidth = width;
        this.brickHeight = height;
    }

    public void update() {

    }

    public void display() {
        if (!alive) {
            return;
        }
        
        canvas.fill(brickColor);
        canvas.rect(brickX, brickY, brickWidth, brickHeight);
    }

    public static ArrayList<Brick> createBricks(PApplet canvas, int rows, int columns, int brickWidth, int brickHeight, int spacing, float startX, float startY) {

        ArrayList<Brick> bricks = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            for (int b = 0; b < columns; b++) {
                float x = startX + b * (brickWidth + spacing);
                float y = startY + i * (brickHeight + spacing);
                bricks.add(new Brick(canvas, x, y, brickWidth, brickHeight));
            }
        }
        return bricks;
    }

    public void destroy() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public float getBrickX() {
        return brickX;
    }

    public float getBrickY() {
        return brickY;
    }

    public float getBrickWidth() {
        return brickWidth;
    }

    public float getBrickHeight() {
        return brickHeight;
    }
}
