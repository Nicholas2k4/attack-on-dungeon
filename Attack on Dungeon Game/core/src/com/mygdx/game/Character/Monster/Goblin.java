package com.mygdx.game.Character.Monster;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Enums.Direction;
import com.mygdx.game.Enums.State;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Weapon.AttackParticle;

public class Goblin extends Monster{
    public Goblin() {
        position = new Vector2(0, 0);
        movement = new Vector2(0, 0);
        speed = 80f;
        stateTime = 0;
        health = 120;
        damage = 50f;
        state = State.MOVE;
        animationDirection = Direction.LEFT;

        this.generateGoblinAnimation();
    }

    public void generateGoblinAnimation()
    {
        MyGdxGame app = (MyGdxGame) Gdx.app.getApplicationListener();
        AssetManager assetManager = app.getAssetManager();

        Texture idle = assetManager.get("monster/goblin/goblin_idle.png");
        Texture move = assetManager.get("monster/goblin/goblin_move.png");
        Texture attack = assetManager.get("monster/goblin/goblin_attack.png");
        Texture die = assetManager.get("monster/goblin/goblin_die.png");
        Texture projectile = assetManager.get("monster/goblin/goblin_projectile.png");

        //membuat animasi diam hadap kanan
        TextureRegion[] frames = MyGdxGame.createAnimationFrames(idle, 150,150,4, false, true);
        idleRightAnimation = new Animation<TextureRegion>(0.05f, frames);

        //membuat animasi diam hadap kiri, sumbu x di-flip
        frames = MyGdxGame.createAnimationFrames(idle, 150,150,4, true, true);
        idleLeftAnimation = new Animation<TextureRegion>(0.05f, frames);

        //membuat animasi jalan hadap kanan
        frames = MyGdxGame.createAnimationFrames(move, 150, 150, 8, false, true);
        moveRightAnimation = new Animation<TextureRegion>(0.05f, frames);

        //membuat animasi jalan hadap kiri, sumbu x di-flip
        frames = MyGdxGame.createAnimationFrames(move, 150, 150, 8, true, true);
        moveLeftAnimation = new Animation<TextureRegion>(0.05f, frames);

        //membuat animasi attack hadap kanan
        frames = MyGdxGame.createAnimationFrames(attack, 150, 150, 8, false, true);
        attackRightAnimation = new Animation<TextureRegion>(0.1f, frames);

        //membuat animasi attack hadap kiri, sumbu x di-flip
        frames = MyGdxGame.createAnimationFrames(attack, 150, 150, 8, true, true);
        attackLeftAnimation = new Animation<TextureRegion>(0.1f, frames);

        //membuat animasi mati hadap kanan
        frames = MyGdxGame.createAnimationFrames(die, 150, 150, 4, false, true);
        dieRightAnimation = new Animation<TextureRegion>(0.05f, frames);

        //membuat animasi mati hadap kiri, sumbu x di-flip
        frames = MyGdxGame.createAnimationFrames(die, 150, 150, 4, true, true);
        dieLeftAnimation = new Animation<TextureRegion>(0.05f, frames);

        //membuat projectile hadap kanan
        frames = MyGdxGame.createAnimationFrames(projectile, 82, 60, 20, false, true);
        projectileLeftAnimation = new Animation<TextureRegion>(0.05f, frames);

        //membuat projectile hadap kiri, sumbu x di-flip
        frames = MyGdxGame.createAnimationFrames(projectile, 82, 60, 20, true, true);
        projectileRightAnimation = new Animation<TextureRegion>(0.05f, frames);
    }

    @Override
    public AttackParticle attack() {
        stateTime = 0;
        AttackParticle attackParticle = new AttackParticle();
        if(animationDirection == Direction.LEFT) {
            attackParticle.setMovement(new Vector2(-1,0));
            attackParticle.setAnimation(projectileLeftAnimation);
        }
        else {
            attackParticle.setMovement(new Vector2(1,0));
            attackParticle.setAnimation(projectileRightAnimation);
        }
        attackParticle.setOriginPosition(new Vector2(position.x, position.y + 70));
        attackParticle.setPosition(new Vector2(position.x, position.y + 70));
        attackParticle.setMaxRadius(130);
        attackParticle.setSpeed(120);
        attackParticle.setDamage(damage);
        return attackParticle;
    }

    @Override
    public float getAttackAnimationTime() {
        return 0.1f * 8;
    }
}
