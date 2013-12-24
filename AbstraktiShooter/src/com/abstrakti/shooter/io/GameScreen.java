package com.abstrakti.shooter.io;

import com.abstrakti.shooter.components.CMapPlace;
import com.abstrakti.shooter.components.Component;
import com.abstrakti.shooter.objects.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class GameScreen implements Screen {
	private static GameScreen screen;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer renderer;
	private GameObject objToFollow;

	private GameScreen() {		
		this.batch = new SpriteBatch();
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		//camera.setToOrtho(false, w / 32, h / 32);	
		camera.setToOrtho(false, w, h);	
	}

	@Override
	public void render(float delta) {
		this.update();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		this.renderer.render();
		
		//batch.setProjectionMatrix(camera.combined);
		batch.begin();
		objToFollow.draw(batch);
		batch.end();
	}



	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
	}
	
	public static GameScreen getInstance(){
		if (screen == null){
			screen = new GameScreen();
		}
		return screen;
	}
	
	public void setMap(TiledMap map){
		this.renderer = new OrthogonalTiledMapRenderer(map, batch);
	}

	
	public void lockCameraOn(GameObject obj){
		this.objToFollow = obj;
	}
	
	private void update() {
		if (this.objToFollow != null && this.objToFollow.hasComponent(Component.Type.MapPlace)){
			CMapPlace place = (CMapPlace)this.objToFollow.getComponent(Component.Type.MapPlace);
			this.camera.position.set(place.getX(), place.getY(), 0);
			this.camera.update();		
		}		
		camera.update();
		this.renderer.setView(camera);
	}
}
