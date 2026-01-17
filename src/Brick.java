import java.util.ArrayList;
import processing.core.PApplet;

public class Brick {
    private PApplet canvas;
    private float brickX = 50; // sets the brick x value to 50
    private float brickY = 100; // sets the brick y value to 100
    private int brickWidth = 50; // initializes the brick width
    private int brickHeight = 30; // initializes the brick height
    private int brickCount; // initializes the brick count variable
    private float r, g, b; // initializes the random color variables
    private int brickColor; // initializes the brick color
    private boolean alive = true; // initializes the boolean which checks if a brick is alive or not

    public Brick(PApplet c, float x, float y, int width, int height) { // brick constructor
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

    public void display() { // displays the bricks on the screen
        if (alive == false) {
            return;
        }

        canvas.fill(brickColor);
        canvas.rect(brickX, brickY, brickWidth, brickHeight);
    }

    public static ArrayList<Brick> createBricks(PApplet canvas, int rows, int columns, int brickWidth, int brickHeight,
            int spacing, float startX, float startY) { //makes an arraylist which contains all of the bricks

        ArrayList<Brick> bricks = new ArrayList<>(); //arraylist with all the bicks in it

        for (int i = 0; i < rows; i++) {
            for (int b = 0; b < columns; b++) {
                float x = startX + b * (brickWidth + spacing);
                float y = startY + i * (brickHeight + spacing);
                bricks.add(new Brick(canvas, x, y, brickWidth, brickHeight));
            }
        }
        return bricks;
    }

    public void destroy() { //method which makes the bricks get destroyed
        alive = false;
    }

    public boolean isAlive() { //method to check if the brick is alive
        return alive;
    }

    public float getBrickX() { //method which return the brick x value
        return brickX;
    }

    public float getBrickY() { //method which returns the brick y value
        return brickY;
    }

    public float getBrickWidth() { //method which returns the bricks width
        return brickWidth;
    }

    public float getBrickHeight() { //method which returns the bricks height
        return brickHeight;
    }
}
