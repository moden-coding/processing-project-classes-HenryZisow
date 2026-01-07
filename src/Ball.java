import processing.core.*;

public class Ball {
    private PApplet canvas;
    private float ballX = canvas.mouseX;
    private float ballY = canvas.mouseY;
    private float ballSize = 30;
    private Paddle paddle;
    private float paddleX = paddle.getPaddleX();
    private float paddleY = paddle.getPaddleY();
    private float paddleWidth = paddle.getPaddleWidth();
    private float paddleHeight = paddle.getPaddleHeight();

    public boolean ballTouchingPaddle() {
        float closestXOnPaddle = canvas.constrain(ballX, paddleX, paddleX + paddleWidth);
        float closestYOnPaddle = canvas.constrain(ballY, paddleY, paddleY + paddleHeight);

        if (canvas.dist(closestXOnPaddle, closestYOnPaddle, ballX, ballY) < ballSize / 2) {
            return true;
        } else {
            return false;
        }
    }

    public void display() {
        canvas.circle(ballX,ballY,ballSize);
    }

    public Ball(PApplet c) {
        canvas = c;
    }

    public void isTouching() {
        if (ballTouchingPaddle() == true) {
            canvas.fill(255, 0, 0);
        } else {
            canvas.fill(0, 255, 0);
        }
    }

}
