package com.mygdx.game.Weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Enums.Direction;
import com.mygdx.game.MyGdxGame;

public class Bow extends Weapon{
    public Bow() {
        damage = 90;
        generateBowAnimation();
    }

    public void generateBowAnimation() {
        Texture projectile = assetManager.get("player/bowman/bowman_projectile.png");

        //membuat animasi projectile hadap kanan
        TextureRegion[] frames = MyGdxGame.createAnimationFrames(projectile, 40,5,1, false, true);
        projectileRightAnimation = new Animation<TextureRegion>(0.05f, frames);

        //membuat animasi projectile hadap kiri, sumbu x di-flip
        frames = MyGdxGame.createAnimationFrames(projectile, 40,5,1, true, true);
        projectileLeftAnimation = new Animation<TextureRegion>(0.05f, frames);
    }

    public AttackParticle attack(Vector2 position, Direction direction) {
        AttackParticle attackParticle = new AttackParticle();
        if(direction == Direction.LEFT) {
            attackParticle.setMovement(new Vector2(-1,0));
            attackParticle.setAnimation(projectileLeftAnimation);
        }
        else {
            attackParticle.setMovement(new Vector2(1,0));
            attackParticle.setAnimation(projectileRightAnimation);
        }
        attackParticle.setOriginPosition(new Vector2(position.x, position.y + 30));
        attackParticle.setPosition(new Vector2(position.x, position.y + 30));
        attackParticle.setMaxRadius(400);
        attackParticle.setSpeed(190);
        attackParticle.setDamage(damage);
        return attackParticle;
    }
}
