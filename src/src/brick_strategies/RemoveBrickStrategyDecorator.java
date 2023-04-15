package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public abstract class RemoveBrickStrategyDecorator implements CollisionStrategy {
    private final CollisionStrategy toBeDecorated;

    /**
     * The constructor
     * I understand that this was not was expected, but I personally did not use the decorator
     * to my advantage as much
     */
    public RemoveBrickStrategyDecorator(CollisionStrategy toBeDecorated){
        this.toBeDecorated = toBeDecorated;
    }

    /**
     * The collision
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        toBeDecorated.onCollision(thisObj, otherObj, counter);
    }

    /**
     * @return the game object collection
     */
    @Override
    public GameObjectCollection getGameObjectCollection(){
        return toBeDecorated.getGameObjectCollection();
    }
}
