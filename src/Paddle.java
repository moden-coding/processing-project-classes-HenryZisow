import processing.core.*;

public class Paddle {
    private PApplet canvas;
    private float paddleX = 600; //initializes the paddles x value
    private float paddleY = 520; //initializes the paddles y value
    private int paddleWidth = 100; //initializes the paddles width
    private int paddleHeight = 20; //initializes the paddles height
    private int paddleSpeed = 10; //initializes the paddles speed

    public Paddle(PApplet c) { //paddle constructor
        canvas = c;
    }

    public void display() { //method which displays the paddle on the screen
        canvas.rect(paddleX, paddleY, paddleWidth, paddleHeight);
    }

    public void update() { //method which updates the paddle and makes sure that it doesnt go off the screen
        canvas.fill(255);
        paddleX = canvas.mouseX - paddleWidth / 2;
        if (paddleX < 0) {
            paddleX = 0;
        } else if (paddleX + paddleWidth > canvas.width) {
            paddleX = canvas.width - paddleWidth;
        }
    }

    public float getPaddleX() { //method which returns the paddles x value
        return paddleX;
    }

    public float getPaddleY() { //method which returns the paddles y value
        return paddleY;
    }

    public int getPaddleWidth() { //method which returns the paddles width
        return paddleWidth;
    }

    public int getPaddleHeight() { //method which returns the paddles height
        return paddleHeight;
    }

    public int getPaddleSpeed() { //method which returns the paddles speed
        return paddleSpeed;
    }
}