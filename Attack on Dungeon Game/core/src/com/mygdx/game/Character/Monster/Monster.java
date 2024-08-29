package com.mygdx.game.Character.Monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Character.Character;
import com.mygdx.game.Enums.Direction;
import com.mygdx.game.Enums.State;
import com.mygdx.game.Interfaces.CanAttack;
import com.mygdx.game.Interfaces.Damageable;
import com.mygdx.game.Interfaces.Drawable;
import com.mygdx.game.Interfaces.Updateable;
import com.mygdx.game.Weapon.AttackParticle;

public class Monster extends Character implements Drawable, Updateable, Damageable, CanAttack {
    float damage;
    protected Animation<TextureRegion> projectileLeftAnimation;
    protected Animation<TextureRegion> projectileRightAnimation;

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = null;

        if(movement.x > 0 && state == State.MOVE)
            currentFrame = moveRightAnimation.getKeyFrame(stateTime, true);
        else if(movement.x <= 0 && state == State.MOVE)
            currentFrame = moveLeftAnimation.getKeyFrame(stateTime, true);
        else if(state == State.ATTACK && animationDirection == Direction.RIGHT)
            currentFrame = attackRightAnimation.getKeyFrame(stateTime, true);
        else if(state == State.ATTACK && animationDirection == Direction.LEFT)
            currentFrame = attackLeftAnimation.getKeyFrame(stateTime, true);
        else if(state == State.DIE && animationDirection == Direction.LEFT)
            currentFrame = dieLeftAnimation.getKeyFrame(stateTime, false);
        else if(state == State.DIE && animationDirection == Direction.RIGHT)
            currentFrame = dieRightAnimation.getKeyFrame(stateTime, false);

        batch.draw(currentFrame, position.x, position.y);
    }

    @Override
    public void update() {
        float elapsed = Gdx.graphics.getDeltaTime();
        stateTime += elapsed;

        if(state == State.DIE && stateTime >= dieLeftAnimation.getAnimationDuration())
            state = State.INACTIVE;

        position.x += movement.x * speed * elapsed;
        position.y += movement.y * speed * elapsed;
        if(movement.x > 0) animationDirection = Direction.RIGHT;
        else animationDirection = Direction.LEFT;
    }

    @Override
    public AttackParticle attack() {
        if(state == State.DIE || state == State.INACTIVE) return null;
        state = State.ATTACK;
        stateTime = 0;
        return new AttackParticle();
    }

    @Override
    public float getAttackAnimationTime() {
        return 0;
    }

    @Override
    public void takeDamage(float damage) {
        health -= damage;
        if (health <= 0) die();
    }

    @Override
    public void die() {
        state = State.DIE;
        stateTime = 0;
    }
}
