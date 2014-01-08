package com.abstrakti.shooter.io;

import java.util.ArrayList;

import com.abstrakti.shooter.Config;
import com.abstrakti.shooter.map.Level;
import com.abstrakti.shooter.objects.Bullet;
import com.abstrakti.shooter.objects.DynamicObject;
import com.abstrakti.shooter.objects.GameObject;
import com.abstrakti.shooter.objects.Player;
import com.abstrakti.shooter.objects.PlayerState;
import com.abstrakti.shooter.objects.Wall;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {
	private static GameScreen screen;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private OrthogonalTiledMapRenderer renderer;
	private DynamicObject objToFollow;
	private Level currentLevel;
	private PlayerInputProcessor input = new PlayerInputProcessor();
	BitmapFont font;
	SpriteBatch fontSpriteBatch;
	ArrayList<Bullet> bullets;
	public GameState gameState;
	Texture ui_Texture;
	private ShapeRenderer shapeRenderer;

	private GameScreen() {
		Gdx.input.setInputProcessor(input);
		this.batch = new SpriteBatch();
		this.shapeRenderer = new ShapeRenderer();

		this.gameState = GameState.RUNNING;
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		//camera.setToOrtho(false, w / 32, h / 32);	
		camera.setToOrtho(false, w, h);	
		camera.zoom = 1.00f;

		setCursorImage();
		loadFonts(); 
		

	}
	private void loadFonts() {
		this.font = new BitmapFont(Gdx.files.internal("../AbstraktiShooter-desktop/textures/fonts/arial.fnt"),
				Gdx.files.internal("../AbstraktiShooter-desktop/textures/fonts/arial.png"), false);
		this.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		fontSpriteBatch = new SpriteBatch();
		ui_Texture = new Texture(Gdx.files.internal("../AbstraktiShooter-desktop/textures/ui/ui_background.png"));
	}

	private void setCursorImage() {
		Pixmap pm = new Pixmap(Gdx.files.internal("../AbstraktiShooter-desktop/textures/mousecursor/cursor.png"));
		int xHotSpot = pm.getWidth() / 2;
		int yHotSpot = pm.getHeight() / 2;

		Gdx.input.setCursorImage(pm, xHotSpot, yHotSpot);
		pm.dispose();
	}
	@Override
	public void render(float delta) {
		this.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		this.renderer.render();

		checkPlayerState();
		if (this.gameState == GameState.RUNNING) {
			
			batch.begin();
			Array<Body> bodies = new Array<Body>();
			this.currentLevel.getWorld().getBodies(bodies);

			for (Body body: bodies) {
				GameObject obj = (GameObject) body.getUserData();
				if (obj!=null) {
					if (Wall.class.isInstance(obj)) {
						continue;
					}
					obj.draw(batch);
				}
			}
			batch.end();
			
			this.drawUI();
			this.handlePlayerInput(delta);
			this.moveAi(delta);

		} else {
			displayGameOver();
		}
	}

	private void checkPlayerState() {
		if (currentLevel.getPlayer().getStatus() == PlayerState.DEAD) {
			this.gameState = gameState.GAME_OVER;
		}
	}

	private void displayGameOver() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		CharSequence str = "GAME OVER";
		fontSpriteBatch.begin();
		this.font.draw(fontSpriteBatch, str, 400, 400);
		fontSpriteBatch.end();
		/*
		boolean[] MOUSEBUTTONS = input.getMouseButtons();
		if (MOUSEBUTTONS[Buttons.LEFT] == true) {
			this.gameState = GameState.RESET;
		}
		 */
	}



	private void drawUI() {
		CharSequence str = "Ammo: " + currentLevel.getPlayer().getAmmo();
		CharSequence str2 = "Health: " + currentLevel.getPlayer().getHealth();
		
		drawHealth();
		drawAmmo();
		fontSpriteBatch.begin();
		this.fontSpriteBatch.draw(ui_Texture,0,0);
		//this.font.draw(fontSpriteBatch, str, 825, 50);
		//this.font.draw(fontSpriteBatch, str2, 25, 50);

		fontSpriteBatch.end();
	}
	
	private void drawHealth() {
		Gdx.gl.glEnable(GL10.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		float width = (currentLevel.getPlayer().getHealth()/20F) *120;
		 shapeRenderer.begin(ShapeType.Filled);
		 shapeRenderer.setColor(1, 0, 0, 0.6f);
		 shapeRenderer.rect(0, 0, width, 30);
		 shapeRenderer.end();
		 Gdx.gl.glDisable(GL10.GL_BLEND);
		
	}
	private void drawAmmo() {
		float width = (currentLevel.getPlayer().getAmmo()/100F) *120;
		Gdx.gl.glEnable(GL10.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		 shapeRenderer.begin(ShapeType.Filled);
		 shapeRenderer.setColor(0, 0, 1,  0.6f);
		 shapeRenderer.rect(Config.SCREEN_WIDTH-120, 0, width, 30);
		 shapeRenderer.end();
		 Gdx.gl.glDisable(GL10.GL_BLEND);
	}

	void moveAi(float delta) {
		ArrayList<AiController> enemies = currentLevel.getEnemies();

		for (AiController ai : enemies) {
			//System.out.println(ai.getPuppetState());
			ai.act(delta);
		}
	}


	private void handlePlayerInput(float delta) {
		Player p = currentLevel.getPlayer();
		boolean[] KEYS = new boolean[603]; 
		KEYS = input.getKeys();
		boolean[] MOUSEBUTTONS = input.getMouseButtons();

		if (KEYS[Keys.W] == true) {
			p.moveForward(delta);
		} else {
			p.stopMovement();
		}
		if (KEYS[Keys.S] == true) {
			p.moveBackward(delta);
		}
		if (KEYS[Keys.A] == true) {
			p.strafeLeft(delta);
		}
		if (KEYS[Keys.D] == true) {
			p.strafeRight(delta);	
		}
		if (MOUSEBUTTONS[Buttons.LEFT] == true) {
			p.shoot(this.currentLevel.getWorld());
		} else {
			p.releaseTrigger();
		}

		p.setRotation(calculatePlayerAngle(Gdx.input.getX(), Gdx.input.getY(), Config.SCREEN_WIDTH/2, Config.SCREEN_HEIGHT/2));

	}


	private float calculatePlayerAngle(int mouseX, int mouseY, float playerX, float playerY) {
		//System.out.println(Math.toDegrees((float)Math.atan2((float)mouseY-playerY, (float)mouseX-playerX)));
		return ((float)Math.atan2((float)mouseY+10-(playerY), (float)mouseX-(playerX+20)));
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


	public void setLevel(Level currentLevel) {
		this.currentLevel = currentLevel;

	}

	public void lockCameraOn(DynamicObject obj){
		this.objToFollow = obj;
	}

	private void update() {
		if (this.objToFollow != null){
			Vector2 position = this.objToFollow.getPosition();
			this.camera.position.set(position, 0);
		}			
		camera.update();
		this.renderer.setView(camera);
	}
}
