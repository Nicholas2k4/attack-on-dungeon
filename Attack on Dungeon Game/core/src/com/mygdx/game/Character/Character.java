package com.mygdx.game.Character;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Enums.Direction;
import com.mygdx.game.Enums.State;

public class Character {
    protected Vector2 position;
    protected Vector2 movement;
    protected float health;
    protected float speed;
    protected float stateTime;
    protected State state;
    protected Direction animationDirection;
    protected Animation<TextureRegion> idleLeftAnimation;
    protected Animation<TextureRegion> idleRightAnimation;
    protected Animation<TextureRegion> moveLeftAnimation;
    protected Animation<TextureRegion> moveRightAnimation;
    protected Animation<TextureRegion> attackLeftAnimation;
    protected Animation<TextureRegion> attackRightAnimation;
    protected Animation<TextureRegion> dieLeftAnimation;
    protected Animation<TextureRegion> dieRightAnimation;


    public Vector2 getPosition() {
        return position;
    }

    public float getXPos() {
        return position.x;
    }

    public float getYPos() {
        return position.y;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getMovement() {
        return movement;
    }

    public void setMovement(Vector2 movement) {
        this.movement = movement;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Animation<TextureRegion> getIdleLeftAnimation() {
        return idleLeftAnimation;
    }

    public void setIdleLeftAnimation(Animation<TextureRegion> idleLeftAnimation) {
        this.idleLeftAnimation = idleLeftAnimation;
    }

    public Animation<TextureRegion> getIdleRightAnimation() {
        return idleRightAnimation;
    }

    public void setIdleRightAnimation(Animation<TextureRegion> idleRightAnimation) {
        this.idleRightAnimation = idleRightAnimation;
    }

    public Animation<TextureRegion> getMoveLeftAnimation() {
        return moveLeftAnimation;
    }

    public void setMoveLeftAnimation(Animation<TextureRegion> moveLeftAnimation) {
        this.moveLeftAnimation = moveLeftAnimation;
    }

    public Animation<TextureRegion> getMoveRightAnimation() {
        return moveRightAnimation;
    }

    public void setMoveRightAnimation(Animation<TextureRegion> moveRightAnimation) {
        this.moveRightAnimation = moveRightAnimation;
    }

    public Animation<TextureRegion> getAttackLeftAnimation() {
        return attackLeftAnimation;
    }

    public void setAttackLeftAnimation(Animation<TextureRegion> attackLeftAnimation) {
        this.attackLeftAnimation = attackLeftAnimation;
    }

    public Animation<TextureRegion> getAttackRightAnimation() {
        return attackRightAnimation;
    }

    public void setAttackRightAnimation(Animation<TextureRegion> attackRightAnimation) {
        this.attackRightAnimation = attackRightAnimation;
    }

    public Animation<TextureRegion> getDieLeftAnimation() {
        return dieLeftAnimation;
    }

    public void setDieLeftAnimation(Animation<TextureRegion> dieLeftAnimation) {
        this.dieLeftAnimation = dieLeftAnimation;
    }

    public Animation<TextureRegion> getDieRightAnimation() {
        return dieRightAnimation;
    }

    public void setDieRightAnimation(Animation<TextureRegion> dieRightAnimation) {
        this.dieRightAnimation = dieRightAnimation;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Direction getAnimationDirection() {
        return animationDirection;
    }

    public void setAnimationDirection(Direction animationDirection) {
        this.animationDirection = animationDirection;
    }
}
