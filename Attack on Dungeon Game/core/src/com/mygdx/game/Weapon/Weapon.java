package com.mygdx.game.Weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Enums.Direction;
import com.mygdx.game.Enums.State;
import com.mygdx.game.MyGdxGame;

public class Weapon {
    protected float damage;
    protected Animation<TextureRegion> projectileLeftAnimation;
    protected Animation<TextureRegion> projectileRightAnimation;
    MyGdxGame app = (MyGdxGame) Gdx.app.getApplicationListener();
    AssetManager assetManager = app.getAssetManager();

    public Weapon(){
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public AttackParticle attack(Vector2 position, Direction direction) {
        return new AttackParticle();
    }
}
