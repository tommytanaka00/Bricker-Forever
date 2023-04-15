package src.gameobjects;

import danogl.GameObject;
import danogl.util.Vector2;
import src.brick_strategies.ChangeCameraStrategy;

public class BallCollisionCountdownAgent extends GameObject {
    private final Ball ball;
    private final ChangeCameraStrategy owner;

    private int countDownValue;
    private int oldCollisionCount;

    /**
     * Constructor for BallCollisionCountdownAgent
     * @param ball the ball that we are tracking
     * @param owner the owner of the countDownAgent of type ChangeCameraStrategy
     * @param countDownValue how many collisions of the ball until the camera
     *                       is set back to normal
     */
    public BallCollisionCountdownAgent(Ball ball, ChangeCameraStrategy owner, int countDownValue){
        super(Vector2.ZERO, Vector2.ZERO, null);
        this.ball = ball;
        this.owner = owner;
        this.countDownValue = countDownValue;
        this.oldCollisionCount = ball.getCollisionCount();

    }


    /**
     * if the ball collides with something, the countDownValue is decremented
     * and if the countDownValue is less than 0, the camera change is turned off
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (ball.getCollisionCount() > oldCollisionCount)
        {
           countDownValue--;
           oldCollisionCount = ball.getCollisionCount();
           if (countDownValue <= 0) {
               owner.turnOffCameraChange();
           }
        }

    }
}
