package com.mygdx.game.Screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Character.Monster.Goblin;
import com.mygdx.game.Character.Monster.Golem;
import com.mygdx.game.Character.Monster.Monster;
import com.mygdx.game.Character.Monster.Mushroom;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Character.Player;
import com.mygdx.game.Enums.Direction;
import com.mygdx.game.Enums.State;
import com.mygdx.game.Weapon.AttackParticle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameScreen implements Screen, InputProcessor {
    MyGdxGame parentGame;
    AssetManager assetManager;

    SpriteBatch batch;
    OrthographicCamera camera;
    Viewport viewport;
    BitmapFont font;
    BitmapFontCache fontScore, fontLives;


    Player mainPlayer;
    ArrayList<Monster> monsters = new ArrayList<>();
    ArrayList<AttackParticle> monsterAttackParticles = new ArrayList<>();
    ArrayList<AttackParticle> playerAttackParticles = new ArrayList<>();
    int score = 0;

    Random randomizer = new Random();

    private static GameScreen gameScreen;
    public static GameScreen getInstance() { return gameScreen; }

    public GameScreen() {
        gameScreen = this;
        parentGame = (MyGdxGame) Gdx.app.getApplicationListener();
        assetManager = parentGame.getAssetManager();

        camera = new OrthographicCamera(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        camera.setToOrtho(true, MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        viewport = new FitViewport(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT,camera);

        batch = new SpriteBatch();

        font = assetManager.get("font.ttf");
		fontScore = new BitmapFontCache(font);
		fontScore.setText("Score: 0", 5, 5);
        fontLives = new BitmapFontCache(font);
        fontLives.setText("Lives: 1000", 5, 25);
        
        mainPlayer = new Player();
        mainPlayer.setPosition(new Vector2(400,300));

        spawnMonster(7);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        parentGame.playGameMusic();
    }

    @Override
    public void render(float delta) {
        camera.update();
		ScreenUtils.clear(0, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		Texture background = assetManager.get("game_screen_background.jpg", Texture.class);
        batch.draw(background, 0, 0);

        mainPlayer.draw(batch);
        for(Monster m: monsters) m.draw(batch);
        for(AttackParticle ap: monsterAttackParticles) {
            if(ap.getAnimation() != null)
                ap.draw(batch);
        }
        for(AttackParticle ap: playerAttackParticles) {
            if(ap.getAnimation() != null)
                ap.draw(batch);
        }

		fontScore.draw(batch);
        fontLives.draw(batch);
		batch.end();

		//update logic
		this.update();
    }

    public void update() {
        float elapsed = Gdx.graphics.getDeltaTime();
        mainPlayer.update();
        if (mainPlayer.getState() == State.INACTIVE) {
            MenuScreen.messages = "Previous score: " + score;
            parentGame.setScreen(new MenuScreen());
        }

        for (int i = 0; i < monsters.size(); i++) {
            Monster m = monsters.get(i);
            if (m.getState() != State.DIE && m.getState() != State.INACTIVE) {
                float dx = mainPlayer.getXPos() - m.getXPos();
                float dy = mainPlayer.getYPos() - m.getYPos();

                if (m instanceof Goblin || m instanceof Mushroom) {
                    dy -= 30;
                }
                m.setMovement(new Vector2(dx / (Math.abs(dx) + Math.abs(dy)), dy / (Math.abs(dx) + Math.abs(dy))));

                float dist = 0;
                if (m instanceof Golem) {
                    dist = 150;
                } else if (m instanceof Goblin) {
                    dist = 50;
                } else {
                    dist = 30;
                }

                if ((dx * dx + dy * dy) <= dist * dist) {
                    m.setState(State.ATTACK);
                } else {
                    m.setState(State.MOVE);
                }

                if (m.getAttackAnimationTime() <= m.getStateTime() &&
                        m.getState() == State.ATTACK) monsterAttackParticles.add(m.attack());
            }

            m.update();
            if (m.getState() == State.INACTIVE) {
                addScore(1);
                monsters.remove(i);
                i--;
                spawnMonster(score / 20 + 1);
            }
        }

        for (int i = 0; i < monsterAttackParticles.size(); i++) {
            AttackParticle attackParticle = monsterAttackParticles.get(i);
            attackParticle.update();

            if (attackParticle.hit(new Vector2(mainPlayer.getPosition()))) {
                mainPlayer.takeDamage(attackParticle.getDamage());
                monsterAttackParticles.remove(i);
                i--;
            } else if (attackParticle.isOutOfRange()) {
                monsterAttackParticles.remove(i);
                i--;
            }
        }

        for (int i = 0; i < playerAttackParticles.size(); i++) {
            AttackParticle attackParticle = playerAttackParticles.get(i);
            attackParticle.update();

            boolean isRemoved = false;
            for (Monster m : monsters) {
                if (attackParticle.hit(new Vector2(m.getPosition()))) {
                    m.takeDamage(attackParticle.getDamage());
                    playerAttackParticles.remove(i);
                    i--;
                    isRemoved = true;
                    break;
                }
            }

            if(isRemoved) continue;

            if (attackParticle.isOutOfRange()) {
                playerAttackParticles.remove(i);
                i--;
            }
        }
    }


    public void spawnMonster(int num) {
        for(int i=0;i<num;i++) {
            int idx = randomizer.nextInt(3);

            float mx = (float) randomizer.nextInt(MyGdxGame.WORLD_WIDTH);
            float my = (float) randomizer.nextInt(MyGdxGame.WORLD_HEIGHT);
            float dx = mainPlayer.getXPos() - mx;
            float dy = mainPlayer.getYPos() - my;

            if(idx == 0) {
                Monster golem = new Golem();
                golem.setPosition(new Vector2(mx, my));
                golem.setMovement(new Vector2(dx / (Math.abs(dx) + Math.abs(dy)), dy / (Math.abs(dx) + Math.abs(dy))));
                monsters.add(golem);
            }
            else if(idx == 1) {
                dy -= 30;
                Monster goblin = new Goblin();
                goblin.setPosition(new Vector2(mx, my));
                goblin.setMovement(new Vector2(dx / (Math.abs(dx) + Math.abs(dy)), dy / (Math.abs(dx) + Math.abs(dy))));
                monsters.add(goblin);
            }
            else {
                dy -= 30;
                Monster mushroom = new Mushroom();
                mushroom.setPosition(new Vector2(mx, my));
                mushroom.setMovement(new Vector2(dx / (Math.abs(dx) + Math.abs(dy)), dy / (Math.abs(dx) + Math.abs(dy))));
                monsters.add(mushroom);
            }
        }
    }

    public void addScore(int scr) {
        this.score += scr;
        fontScore.setText(String.format("Score: %d", score), 5, 5);
    }

    public void updateLives(int lives) {
        fontLives.setText(String.format("Lives: %d",lives), 5, 25);
    }

    @Override
    public void resize(int width, int height) { viewport.update(width, height); }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}

    @Override
    public boolean keyDown(int keycode) {
        if(mainPlayer.getState() == State.DIE || mainPlayer.getState() == State.INACTIVE) return false;
        //cek input
        if(keycode == Input.Keys.A)
            mainPlayer.setMove(Direction.LEFT);
        else if(keycode == Input.Keys.D)
            mainPlayer.setMove(Direction.RIGHT);
        else if(keycode == Input.Keys.W)
            mainPlayer.setMove(Direction.UP);
        else if(keycode == Input.Keys.S)
            mainPlayer.setMove(Direction.DOWN);
        else if(keycode == Input.Keys.E)
            mainPlayer.changeWeapon();

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(mainPlayer.getState() == State.DIE || mainPlayer.getState() == State.INACTIVE) return false;
        if(keycode == Input.Keys.A && mainPlayer.getDirection() == Direction.LEFT)
            mainPlayer.Stop();
        else if(keycode == Input.Keys.D && mainPlayer.getDirection() == Direction.RIGHT)
            mainPlayer.Stop();
        else if(keycode == Input.Keys.W  && mainPlayer.getDirection() == Direction.UP)
            mainPlayer.Stop();
        else if(keycode == Input.Keys.S && mainPlayer.getDirection() == Direction.DOWN)
            mainPlayer.Stop();

        return true;
    }

    @Override
    public boolean keyTyped(char character) { return false; }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(mainPlayer.getState() == State.DIE || mainPlayer.getState() == State.INACTIVE) return false;
        playerAttackParticles.add(mainPlayer.attack());
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }

    @Override
    public boolean mouseMoved(int screenX, int screenY) { return false; }

    @Override
    public boolean scrolled(float amountX, float amountY) { return false; }
}
