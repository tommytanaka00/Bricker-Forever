package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.MockPaddle;
public class AddPaddleStrategy extends RemoveBrickStrategyDecorator {
    private static final int NUM_OF_COLLISIONS_TO_DISAPPEAR = 4;
    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = 5;
    private static final float MOCK_PADDLE_LENGTH = 100;
    private static final float MOCK_PADDLE_HEIGHT = 15;
    private static final float ADDED_HEIGHT = 30;

    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;


    /**
     * The strategy for adding a new mock paddle that disappears
     * after 4 collisions
     */
    public AddPaddleStrategy(CollisionStrategy toBeDecorated,
                             ImageReader imageReader,
                             UserInputListener inputListener,
                             Vector2 windowDimensions) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    /**
     *  When colliding with the brick, creates a new mockpaddle
     */
    @Override
    public void onCollision(GameObject thisObject, GameObject other, Counter curAmountOfBricks) {
        //Removes the brick
        super.onCollision(thisObject, other, curAmountOfBricks);
        if (MockPaddle.isInstantiated) {
            return;
        }
        Renderable mockPaddleImage = imageReader.readImage("assets/paddle.png", true);

        //Create mock paddle
        GameObject mockPaddle = new MockPaddle(Vector2.ZERO,
                new Vector2(MOCK_PADDLE_LENGTH, MOCK_PADDLE_HEIGHT),
                mockPaddleImage,
                inputListener,
                windowDimensions,
                getGameObjectCollection(),
                MIN_DISTANCE_FROM_SCREEN_EDGE,
                NUM_OF_COLLISIONS_TO_DISAPPEAR
        );

        //puts the mock paddle above the user paddle
        mockPaddle.setCenter(new Vector2(windowDimensions.x()/2, (int) windowDimensions.y() - 30 - ADDED_HEIGHT));

        getGameObjectCollection().addGameObject(mockPaddle);
        MockPaddle.isInstantiated = true;
    }

    /**
     * @return the game object collection
     */
    @Override
    public GameObjectCollection getGameObjectCollection(){
        return super.getGameObjectCollection();
    }
}
