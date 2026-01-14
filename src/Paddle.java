import processing.core.*;

public class Paddle {
    private PApplet canvas;
    private float paddleX = 600;
    private float paddleY = 520;
    private int paddleWidth = 100;
    private int paddleHeight = 20;
    private int paddleSpeed = 10;

    public Paddle(PApplet c) {
        canvas = c;
    }

    public void display() {
        canvas.rect(paddleX, paddleY, paddleWidth, paddleHeight);
    }

    public float getPaddleX() {
        return paddleX;
    }

    public float getPaddleY() {
        return paddleY;
    }

    public int getPaddleWidth() {
        return paddleWidth;
    }

    public int getPaddleHeight() {
        return paddleHeight;
    }

    public int getPaddleSpeed() {
        return paddleSpeed;
    }

    public void update() {
        canvas.fill(255);
        paddleX = canvas.mouseX - paddleWidth / 2;
        if (paddleX < 0) {
            paddleX = 0;
        } else if (paddleX + paddleWidth > canvas.width) {
            paddleX = canvas.width - paddleWidth;
        }
    }
}