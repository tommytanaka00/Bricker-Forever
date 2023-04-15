package src.brick_strategies;

import danogl.GameObject;
import danogl.util.Counter;

public class DoubleStrategy extends RemoveBrickStrategyDecorator{
    private final CollisionStrategy firstStrategy;
    private final CollisionStrategy secondStrategy;

    /**
     * The strategy that adds two different strategies
     */
    public DoubleStrategy(CollisionStrategy toBeDecorated, CollisionStrategy firstStrategy,
                          CollisionStrategy secondStrategy) {
        super(toBeDecorated);
        this.firstStrategy = firstStrategy;
        this.secondStrategy = secondStrategy;
    }

    /**
     *  Activates the onCollision function of two different strategies
     */
    @Override
    public void onCollision(GameObject thisObject, GameObject other, Counter curAmountOfBricks) {
        //Removes the brick
        super.onCollision(thisObject, other, curAmountOfBricks);

        //Because both the strategy's onCollision method with decrement the curAmountOfBricks
        curAmountOfBricks.increaseBy(2);
        firstStrategy.onCollision(thisObject, other, curAmountOfBricks);
        secondStrategy.onCollision(thisObject, other, curAmountOfBricks);
    }
}
