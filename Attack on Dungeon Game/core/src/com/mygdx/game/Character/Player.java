package com.mygdx.game.Character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Interfaces.CanAttack;
import com.mygdx.game.Interfaces.Damageable;
import com.mygdx.game.Interfaces.Drawable;
import com.mygdx.game.Interfaces.Updateable;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screen.GameScreen;
import com.mygdx.game.Enums.Direction;
import com.mygdx.game.Enums.State;
import com.mygdx.game.Weapon.*;

import javax.print.DocFlavor;

public class Player extends Character implements Drawable, Updateable, Damageable, CanAttack {
    //informasi animation tiap senjata
    int playerIdx;
    String fileName[] = {"swordsman", "bowman", "mage"};
    int xOffSet[] = {0,0,-25};
    int yOffSet[] = {0,0,-30};
    int frameWidth[] = {80, 80, 125};
    int frameHeight[] = {80, 80, 100};
    int idleFrameCount[] = {9, 4, 8};
    int moveFrameCount[] = {6, 8, 8};
    int attackFrameCount[] = {12, 7, 13};
    int dieFrameCount[] = {23, 8, 9};

    Direction direction;
    Weapon equippedWeapon;
    Weapon weaponList[] = {null, null, null};

    public Player() {
        position = new Vector2(400,200);
        movement = new Vector2(0,0);
        health = 1000;
        speed = 250f;
        stateTime = 0;
        state = State.IDLE;
        animationDirection = Direction.LEFT;

        playerIdx = 0;
        direction = Direction.LEFT;

        this.generatePlayerAnimation();

        Weapon sword = new Sword();
        weaponList[playerIdx] = sword;

        changeWeapon();

        Weapon bow = new Bow();
        weaponList[playerIdx] = bow;

        changeWeapon();

        Weapon magic = new Magic();
        weaponList[playerIdx] = magic;

        changeWeapon();

        equippedWeapon = weaponList[playerIdx];
    }

    public void generatePlayerAnimation()
    {
        MyGdxGame app = (MyGdxGame) Gdx.app.getApplicationListener();
        AssetManager assetManager = app.getAssetManager();

        Texture idle = assetManager.get("player/" + fileName[playerIdx] + "/" + fileName[playerIdx] + "_idle.png");
        Texture move = assetManager.get("player/" + fileName[playerIdx] + "/" + fileName[playerIdx] + "_move.png");
        Texture attack = assetManager.get("player/" + fileName[playerIdx] + "/" + fileName[playerIdx] + "_attack.png");
        Texture die = assetManager.get("player/" + fileName[playerIdx] + "/" + fileName[playerIdx] + "_die.png");
        Texture projectile = assetManager.get("player/" + fileName[playerIdx] + "/" + fileName[playerIdx] + "_projectile.png");

        //membuat animasi diam hadap kanan
        TextureRegion[] frames = MyGdxGame.createAnimationFrames(idle, frameWidth[playerIdx], frameHeight[playerIdx], idleFrameCount[playerIdx], false, true);
        idleRightAnimation = new Animation<TextureRegion>(0.05f, frames);

        //membuat animasi diam hadap kiri, sumbu x di-flip
        frames = MyGdxGame.createAnimationFrames(idle, frameWidth[playerIdx], frameHeight[playerIdx], idleFrameCount[playerIdx], true, true);
        idleLeftAnimation = new Animation<TextureRegion>(0.05f, frames);

        //membuat animasi jalan hadap kanan
        frames = MyGdxGame.createAnimationFrames(move, frameWidth[playerIdx], frameHeight[playerIdx], moveFrameCount[playerIdx], false, true);
        moveRightAnimation = new Animation<TextureRegion>(0.05f, frames);

        //membuat animasi jalan hadap kiri, sumbu x di-flip
        frames = MyGdxGame.createAnimationFrames(move, frameWidth[playerIdx], frameHeight[playerIdx], moveFrameCount[playerIdx], true, true);
        moveLeftAnimation = new Animation<TextureRegion>(0.05f, frames);

        //membuat animasi attack hadap kanan
        frames = MyGdxGame.createAnimationFrames(attack, frameWidth[playerIdx], frameHeight[playerIdx], attackFrameCount[playerIdx], false, true);
        attackRightAnimation = new Animation<TextureRegion>(0.05f, frames);

        //membuat animasi attack hadap kiri, sumbu x di-flip
        frames = MyGdxGame.createAnimationFrames(attack, frameWidth[playerIdx], frameHeight[playerIdx], attackFrameCount[playerIdx], true, true);
        attackLeftAnimation = new Animation<TextureRegion>(0.05f, frames);

        //membuat animasi mati hadap kanan
        frames = MyGdxGame.createAnimationFrames(die, frameWidth[playerIdx], frameHeight[playerIdx], dieFrameCount[playerIdx], false, true);
        dieRightAnimation = new Animation<TextureRegion>(0.05f, frames);

        //membuat animasi mati hadap kiri, sumbu x di-flip
        frames = MyGdxGame.createAnimationFrames(die, frameWidth[playerIdx], frameHeight[playerIdx], dieFrameCount[playerIdx], true, true);
        dieLeftAnimation = new Animation<TextureRegion>(0.05f, frames);

        stateTime = 0;
    }

    public void draw(SpriteBatch batch)
    {
        TextureRegion currentFrame = null;
        if(state == State.MOVE && animationDirection == Direction.LEFT)
            currentFrame = moveLeftAnimation.getKeyFrame(stateTime, true);
        else if(state == State.MOVE && animationDirection == Direction.RIGHT)
            currentFrame = moveRightAnimation.getKeyFrame(stateTime, true);
        else if(state == State.IDLE && animationDirection == Direction.LEFT)
            currentFrame = idleLeftAnimation.getKeyFrame(stateTime, true);
        else if(state == State.IDLE && animationDirection == Direction.RIGHT)
            currentFrame = idleRightAnimation.getKeyFrame(stateTime, true);
        else if(state == State.ATTACK && animationDirection == Direction.LEFT)
            currentFrame = attackLeftAnimation.getKeyFrame(stateTime, false);
        else if(state == State.ATTACK && animationDirection == Direction.RIGHT)
            currentFrame = attackRightAnimation.getKeyFrame(stateTime, false);
        else if(state == State.DIE && animationDirection == Direction.LEFT)
            currentFrame = dieLeftAnimation.getKeyFrame(stateTime, false);
        else if(state == State.DIE && animationDirection == Direction.RIGHT)
            currentFrame = dieRightAnimation.getKeyFrame(stateTime, false);

        batch.draw(currentFrame, position.x + xOffSet[playerIdx], position.y + yOffSet[playerIdx]);
    }

    public void update()
    {
        float elapsed = Gdx.graphics.getDeltaTime();
        stateTime += elapsed;

        if(state == State.DIE && stateTime >= dieLeftAnimation.getAnimationDuration())
            state = State.INACTIVE;

        if(state == state.ATTACK && stateTime >= attackFrameCount[playerIdx] * 0.05f)
            state = State.IDLE;

        position.x += movement.x * speed * elapsed;
        if(position.x > MyGdxGame.WORLD_WIDTH-70) {
            position.x = MyGdxGame.WORLD_WIDTH-70;
            this.Stop();
        }
        else if(position.x < -20) {
            position.x = -20;
            this.Stop();
        }

        position.y += movement.y * speed * elapsed;
        if(position.y > MyGdxGame.WORLD_HEIGHT-80) {
            position.y = MyGdxGame.WORLD_HEIGHT-80;
            this.Stop();
        }
        else if(position.y < -20) {
            position.y = -20;
            this.Stop();
        }
    }

    public void setMove(Direction d)
    {
        direction = d;
        state = State.MOVE;
        if(animationDirection == Direction.LEFT && d == Direction.RIGHT) {
            animationDirection = Direction.RIGHT;
            stateTime = 0;
        }
        else if(animationDirection == Direction.RIGHT && d == Direction.LEFT) {
            animationDirection = Direction.LEFT;
            stateTime = 0;
        }

        if(d == Direction.RIGHT)
            movement = new Vector2(1,0);
        else if(d == Direction.LEFT)
            movement = new Vector2(-1,0);
        else if(d == Direction.UP)
            movement = new Vector2(0,-1);
        else if(d == Direction.DOWN)
            movement = new Vector2(0,1);
    }

    public void Stop() {
        if(state != State.IDLE) {
            movement.x = 0;
            movement.y = 0;
            state = State.IDLE;
            stateTime = 0;
        }
    }

    @Override
    public AttackParticle attack() {
        state = State.ATTACK;
        stateTime = 0;
        return equippedWeapon.attack(new Vector2(position), direction);
    }

    @Override
    public float getAttackAnimationTime() {
        return 0.05f * attackFrameCount[playerIdx];
    }

    public void changeWeapon() {
        playerIdx = (playerIdx + 1) % 3;
        equippedWeapon = weaponList[playerIdx];
        generatePlayerAnimation();
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void takeDamage(float damage) {
        if(state == State.DIE || state == State.INACTIVE) return;
        health -= damage;
        if(health <= 0) {
            health = 0;
            die();
        }
        GameScreen.getInstance().updateLives((int) health);
    }

    @Override
    public void die() {
        state = State.DIE;
        stateTime = 0;
    }
}
