package com.mygdx.game.Weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Enums.Direction;
import com.mygdx.game.Enums.State;
import com.mygdx.game.Interfaces.CanAttack;
import com.mygdx.game.Interfaces.Drawable;
import com.mygdx.game.Interfaces.Updateable;

public class AttackParticle implements Drawable, Updateable {
    protected Vector2 originPosition;
    protected Vector2 position;
    protected Vector2 movement;
    protected float maxRadius;
    protected float speed;
    protected float damage;
    protected float stateTime;
    protected Animation<TextureRegion> animation;

    public AttackParticle() {
        stateTime = 0;
    }

    public boolean isOutOfRange() {
        float dx = position.x - originPosition.x;
        float dy = position.y - originPosition.y;
        if((dx * dx + dy * dy) >= maxRadius * maxRadius) return true;
        return false;
    }

    @Override
    public void draw(SpriteBatch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, position.x, position.y);
    }

    @Override
    public void update() {
        float elapsed = Gdx.graphics.getDeltaTime();
        stateTime += elapsed;

        position.x += movement.x * speed * elapsed;
        position.y += movement.y * speed * elapsed;
    }

    public Vector2 getOriginPosition() { return originPosition; }

    public void setOriginPosition(Vector2 originPosition) { this.originPosition = originPosition; }

    public Vector2 getPosition() { return position; }

    public void setPosition(Vector2 position) { this.position = position; }

    public Vector2 getMovement() { return movement; }

    public void setMovement(Vector2 movement) { this.movement = movement; }

    public float getMaxRadius() { return maxRadius; }

    public void setMaxRadius(float maxRadius) { this.maxRadius = maxRadius; }

    public float getSpeed() { return speed; }

    public void setSpeed(float speed) { this.speed = speed; }

    public float getDamage() { return damage; }

    public void setDamage(float damage) { this.damage = damage; }

    public Animation<TextureRegion> getAnimation() { return animation; }

    public void setAnimation(Animation<TextureRegion> animation) { this.animation = animation; }

    public boolean hit(Vector2 vector2) {
        float dx = vector2.x - position.x;
        float dy = vector2.y - position.y;
        return (dx * dx + dy * dy) <= 40 * 40;
    }
}
