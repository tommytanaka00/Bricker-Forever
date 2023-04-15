package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;
import src.gameobjects.Ball;

public class RemoveBrickStrategy implements CollisionStrategy {
    protected final GameObjectCollection gameObjects;

    /**
     * Constructor for CollisionStrategy
     * @param gameObjectCollection the game object
     */
    public RemoveBrickStrategy(GameObjectCollection gameObjectCollection) {
        this.gameObjects = gameObjectCollection;
    }

    @Override
    public GameObjectCollection getGameObjectCollection() {
        return gameObjects;
    }

    /**
     * What to do when a ball collides with a brick
     * @param thisObject a brick that the ball collided with
     * @param otherObj Expects a ball
     * @param curAmountOfBricks the counter to the current amount of blocks.
     */
    public void onCollision(GameObject thisObject, GameObject otherObj, Counter curAmountOfBricks) {
        if (otherObj instanceof Ball) {
            gameObjects.removeGameObject(thisObject, Layer.STATIC_OBJECTS);
            curAmountOfBricks.decrement();
        }
    }
}
