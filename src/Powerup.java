import processing.core.*;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.*;

public class Powerup {
    private PApplet canvas;
    private Paddle paddle;
    private PImage image; // Used chatGPT to help make the powerup an image

    private int powerupWidth; // initializes the powerup width
    private int powerupHeight; // initializes the powerup height
    private float powerupX; // initializes the powerup x value
    private float powerupY; // initializes the powerup y value

    private int powerupBallCount = 2; // initializes the powerup ball count with a value of 2
    private int powerupSpeed = 2; // initializes the powerup speed with a value of 2
    private boolean active = true; // initializes a boolean which checks if the powerup is active or not

    public Powerup(PApplet c, int width, int height, float x, float y, Paddle paddle, PImage image) { // powerup
                                                                                                      // constructor
                                                                                                      // which sets all
                                                                                                      // the variables
        canvas = c;
        this.powerupWidth = width;
        this.powerupHeight = height;
        this.powerupX = x;
        this.powerupY = y;
        this.paddle = paddle;
        this.image = image;
    }

    public void update() { // updates the powerup and makes it go down the screen
        if (active == false) {
            return;
        }
        powerupY += powerupSpeed;
    }

    public void display() { // displays the powerups on the screen
        if (active == false) {
            return;
        }
        canvas.image(image, powerupX, powerupY, powerupWidth, powerupHeight); // used chatGPT to make an image appear
                                                                              // over the powerup
    }

    public boolean hitsPaddle() { // checks if the rectangular powerup touches the paddle
        if (active != true) {
            return false;
        }
        float closestX = PApplet.constrain(powerupX, paddle.getPaddleX(),
                paddle.getPaddleX() + paddle.getPaddleWidth());

        float closestY = PApplet.constrain(powerupY, paddle.getPaddleY(),
                paddle.getPaddleY() + paddle.getPaddleHeight());

        if (closestX >= powerupX && closestX <= powerupX + powerupWidth && closestY >= powerupY
                && closestY <= powerupY + powerupHeight) {
            active = false;
            return true;
        }

        return false;
    }

    public boolean isOffScreen() { // method to see if the powerup is off the screen
        return powerupY > canvas.height;
    }

    public boolean isActive() { // method to see if the powerup is currently active
        return active;
    }

    public int getBallCount() { // method to get how many balls there are
        return powerupBallCount;
    }
}
