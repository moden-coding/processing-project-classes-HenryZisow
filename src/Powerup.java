import processing.core.*;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.*;

public class Powerup {
    private PApplet canvas;
    private Paddle paddle;
    private PImage image; //Used chatGPT to help make the powerup an image

    private int powerupWidth;
    private int powerupHeight;
    private float powerupX;
    private float powerupY;

    private int powerupBallCount = 2;
    private int powerupSpeed = 2;
    private boolean active = true;

    public Powerup(PApplet c, int width, int height, float x, float y, Paddle paddle, PImage image) {
        canvas = c;
        this.powerupWidth = width;
        this.powerupHeight = height;
        this.powerupX = x;
        this.powerupY = y;
        this.paddle = paddle;
        this.image = image;
    }

    public void update() {
        if (active == false) {
            return;
        }
        powerupY += powerupSpeed;
    }

    public void display() {
        if (active == false) {
            return;
        }
        canvas.image(image, powerupX, powerupY, powerupWidth, powerupHeight);
    }

    public boolean hitsPaddle() {
        if (active == false) {
            return false;
        }
        if (powerupX + powerupWidth > paddle.getPaddleX() &&
                powerupX < paddle.getPaddleX() + paddle.getPaddleWidth() &&
                powerupY + powerupHeight > paddle.getPaddleY() &&
                powerupY < paddle.getPaddleY() + paddle.getPaddleHeight()) {

            active = false;
            return true;
        }
        return false;
    }

    public boolean isOffScreen() {
        return powerupY > canvas.height;
    }

    public boolean isActive() {
        return active;
    }

    public int getBallCount() {
        return powerupBallCount;
    }
}
