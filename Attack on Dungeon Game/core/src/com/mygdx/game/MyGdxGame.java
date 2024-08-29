package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Screen.LoadingScreen;

public class MyGdxGame extends Game {
	//ukuran screen
	public static final int WORLD_WIDTH = 800;
	public static final int WORLD_HEIGHT = 600;

	//asset manager
	AssetManager manager = new AssetManager();
	public AssetManager getAssetManager() {
		return manager;
	}

	@Override
	public void create () {
		manager.load("menu_screen_background.jpg", Texture.class);
		manager.load("game_screen_background.jpg", Texture.class);

		manager.load("player/swordsman/swordsman_idle.png", Texture.class);
		manager.load("player/swordsman/swordsman_move.png", Texture.class);
		manager.load("player/swordsman/swordsman_attack.png", Texture.class);
		manager.load("player/swordsman/swordsman_die.png", Texture.class);
		manager.load("player/swordsman/swordsman_projectile.png", Texture.class);

		manager.load("player/bowman/bowman_idle.png", Texture.class);
		manager.load("player/bowman/bowman_move.png", Texture.class);
		manager.load("player/bowman/bowman_attack.png", Texture.class);
		manager.load("player/bowman/bowman_die.png", Texture.class);
		manager.load("player/bowman/bowman_projectile.png", Texture.class);

		manager.load("player/mage/mage_idle.png", Texture.class);
		manager.load("player/mage/mage_move.png", Texture.class);
		manager.load("player/mage/mage_attack.png", Texture.class);
		manager.load("player/mage/mage_die.png", Texture.class);
		manager.load("player/mage/mage_projectile.png", Texture.class);

		manager.load("monster/golem/golem_idle.png", Texture.class);
		manager.load("monster/golem/golem_move.png", Texture.class);
		manager.load("monster/golem/golem_attack.png", Texture.class);
		manager.load("monster/golem/golem_die.png", Texture.class);
		manager.load("monster/golem/golem_projectile.png", Texture.class);

		manager.load("monster/goblin/goblin_idle.png", Texture.class);
		manager.load("monster/goblin/goblin_move.png", Texture.class);
		manager.load("monster/goblin/goblin_attack.png", Texture.class);
		manager.load("monster/goblin/goblin_die.png", Texture.class);
		manager.load("monster/goblin/goblin_projectile.png", Texture.class);

		manager.load("monster/mushroom/mushroom_idle.png", Texture.class);
		manager.load("monster/mushroom/mushroom_move.png", Texture.class);
		manager.load("monster/mushroom/mushroom_attack.png", Texture.class);
		manager.load("monster/mushroom/mushroom_die.png", Texture.class);
		manager.load("monster/mushroom/mushroom_projectile.png", Texture.class);
		
		manager.load("music/menu_music.mp3", Music.class);
		manager.load("music/game_music.mp3", Music.class);

		// register loader
		FileHandleResolver resolver = new InternalFileHandleResolver();
		manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

		FreetypeFontLoader.FreeTypeFontLoaderParameter fontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		fontParameter.fontFileName = "font.ttf";
		fontParameter.fontParameters.size = 16;
		fontParameter.fontParameters.color = Color.WHITE;
		fontParameter.fontParameters.borderColor = Color.BLACK;
		fontParameter.fontParameters.borderWidth = 2;
		fontParameter.fontParameters.flip = true;
		manager.load("font.ttf", BitmapFont.class, fontParameter);

		FreetypeFontLoader.FreeTypeFontLoaderParameter bigfontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		bigfontParameter.fontFileName = "font.ttf";
		bigfontParameter.fontParameters.size = 24;
		bigfontParameter.fontParameters.color = Color.WHITE;
		bigfontParameter.fontParameters.borderColor = Color.BLACK;
		bigfontParameter.fontParameters.borderWidth = 2;
		bigfontParameter.fontParameters.flip = false;
		manager.load("bigfont.ttf", BitmapFont.class, bigfontParameter);

		SkinLoader.SkinParameter skinParam = new SkinLoader.SkinParameter("uiskin.atlas");
		manager.load("uiskin.json", Skin.class, skinParam);
		this.setScreen(new LoadingScreen());
	}

	public void playMenuMusic() {
		Music music = manager.get("music/menu_music.mp3", Music.class);
		music.setLooping(true);
		music.play();
	}

	public void playGameMusic() {
		Music music = manager.get("music/game_music.mp3", Music.class);
		music.setVolume(0.35f);
		music.setLooping(true);
		music.play();
	}

	@Override
	public void render () { super.render(); }

	public static TextureRegion[] createAnimationFrames(Texture tex, int frameWidth, int frameHeight, int frameCount, boolean flipx, boolean flipy) {
		TextureRegion[][] tmp = TextureRegion.split(tex,frameWidth, frameHeight);
		TextureRegion[] frames = new TextureRegion[frameCount];
		int index = 0;
		int row = tex.getHeight() / frameHeight;
		int col = tex.getWidth() / frameWidth;
		for (int i = 0; i < row && index < frameCount; i++) {
			for (int j = 0; j < col && index < frameCount; j++) {
				frames[index] = tmp[i][j];
				frames[index].flip(flipx, flipy);
				index++;
			}
		}
		return frames;
	}

	@Override
	public void resize(int width, int height) { super.resize(width, height); }

	@Override
	public void dispose() { manager.dispose(); }
}
