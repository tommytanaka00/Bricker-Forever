package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.*;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;
import src.gameobjects.Puck;

public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator {


    private static final int COUNTDOWN_VALUE = 4;
    private BallCollisionCountdownAgent countdownAgent;
    private final WindowController windowController;
    private final BrickerGameManager gameManager;
    private boolean cameraStrategyInitiated;

    /**
     * The strategy that changes the camera to track the ball
     * until the ball collides with 4 objects
     */
    public ChangeCameraStrategy(CollisionStrategy toBeDecorated,
                                WindowController windowController,
                                BrickerGameManager gameManager) {
        super(toBeDecorated);
        this.windowController = windowController;
        this.gameManager = gameManager;
        this.cameraStrategyInitiated = false;
    }

    /**
     * When the brick is broken by a ball, initiates the camera angle change
     * @param otherBall expects the gameObject to be a ball.
     */
    @Override
    public void onCollision(GameObject thisObject, GameObject otherBall, Counter curAmountOfBricks) {
        //Remove the brick
        super.onCollision(thisObject, otherBall, curAmountOfBricks);

        if (cameraStrategyInitiated) {
            return;
        }

        if (otherBall instanceof Ball && !(otherBall instanceof Puck)) {
            gameManager.setCamera(new Camera(
                    otherBall,            //object to follow, the ball
                    Vector2.ZERO,    //follow the center of the object
                    windowController.getWindowDimensions().mult(1.2f),  //widen the frame a bit
                    windowController.getWindowDimensions()   //share the window dimensions
            ));

            //COUNTDOWN_VALUE + 1 because the first time is the colliding with the brick itself which doesn't count
            this.countdownAgent = new BallCollisionCountdownAgent((Ball) otherBall, this, COUNTDOWN_VALUE + 1);
            super.getGameObjectCollection().addGameObject(countdownAgent);
            this.cameraStrategyInitiated = true;
        }
    }

    /**
     * Returns the camera to its regular state
     */
    public void turnOffCameraChange(){
        gameManager.setCamera(null);
        cameraStrategyInitiated = false;
        super.getGameObjectCollection().removeGameObject(countdownAgent);
    }

    /**
     * returns gameObjectCollection
     */
    @Override
    public GameObjectCollection getGameObjectCollection(){
        return super.getGameObjectCollection();
    }
}
