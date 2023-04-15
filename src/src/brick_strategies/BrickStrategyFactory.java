package src.brick_strategies;

import danogl.gui.*;
import src.BrickerGameManager;

import java.util.Random;

public class BrickStrategyFactory {
    private static int AMOUNT_OF_STRATEGIES = 5;

    private static final int ADD_NEW_BALL = 0;
    private static final int ADD_NEW_PADDLE = 1;
    private static final int DIFFERENT_CAMERA_ANGLE = 2;
    private static final int CHANGE_PADDLE_WIDTH = 3;
    private static final int DOUBLE_STRATEGY = 4;

    private final Random random = new Random();
    private final ImageReader imageReader;
    private final SoundReader soundReader;

    //---------Strategies that are one time and therefore do------------
    //---------not change (no need for a new one every time)------------
    private final RemoveBrickStrategy removeBrickStrategy;
    private final AddPaddleStrategy addPaddleStrategy;
    private final ChangePaddleWidthStrategy changePaddleWidthStrategy;
    private final ChangeCameraStrategy changeCameraStrategy;


    /**
     * The constructor for BrickStrategyFactory
     */
    public BrickStrategyFactory(danogl.collisions.GameObjectCollection gameObjectCollection,
                                BrickerGameManager gameManager,
                                danogl.gui.ImageReader imageReader,
                                danogl.gui.SoundReader soundReader,
                                danogl.gui.UserInputListener inputListener,
                                danogl.gui.WindowController windowController,
                                danogl.util.Vector2 windowDimensions) {
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.removeBrickStrategy = new RemoveBrickStrategy(gameObjectCollection);
        this.addPaddleStrategy = new AddPaddleStrategy(removeBrickStrategy, imageReader, inputListener, windowDimensions);
        this.changeCameraStrategy = new ChangeCameraStrategy(removeBrickStrategy, windowController, gameManager);
        this.changePaddleWidthStrategy = new ChangePaddleWidthStrategy(removeBrickStrategy, imageReader, windowController);

    }


    /**
     * Chooses randomly between all the possible brick strategies
     * 50% chance that it's a regular brick strategy that just removes the brick,
     * and 50% chance that it's a brick strategy with an added feature
     * @return A collision strategy
     */
    public CollisionStrategy getStrategy() {

        boolean removeStrategy = random.nextBoolean();
        //50% chance that it's the regular strategy
        if (removeStrategy) {
            return removeBrickStrategy;
        }


        int strategy = random.nextInt(AMOUNT_OF_STRATEGIES);
        switch (strategy) {
            case ADD_NEW_BALL:
                return new PuckStrategy(removeBrickStrategy, imageReader, soundReader);
            case ADD_NEW_PADDLE:
                return addPaddleStrategy;
            case DIFFERENT_CAMERA_ANGLE:
                return changeCameraStrategy;
            case CHANGE_PADDLE_WIDTH:
                return changePaddleWidthStrategy;
            case DOUBLE_STRATEGY:
                AMOUNT_OF_STRATEGIES -= 1;
                CollisionStrategy strategyWithoutDouble1 = getStrategy();
                CollisionStrategy strategyWithoutDouble2 = getStrategy();
                AMOUNT_OF_STRATEGIES += 1;
                return new DoubleStrategy(removeBrickStrategy, strategyWithoutDouble1, strategyWithoutDouble2);
        }
        return null;
    }

}

