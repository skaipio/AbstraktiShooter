package com.abstrakti.shooter.io;

import java.util.ArrayList;

import com.abstrakti.shooter.Config;
import com.abstrakti.shooter.Level;
import com.abstrakti.shooter.objects.DynamicObject;
import com.abstrakti.shooter.objects.GameObject;
import com.abstrakti.shooter.objects.Player;
import com.abstrakti.shooter.objects.PlayerState;
import com.abstrakti.shooter.objects.Wall;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
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

	private GameScreen() {
		Gdx.input.setInputProcessor(input);
		this.batch = new SpriteBatch();
		
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

		//batch.setProjectionMatrix(camera.combined);
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
	}
	
	private void drawUI() {
		SpriteBatch spriteBatch = new SpriteBatch();

		CharSequence str = "Ammo: " + currentLevel.getPlayer().getAmmo();
		CharSequence str2 = "Health: " + currentLevel.getPlayer().getHealth();
		spriteBatch.begin();
		this.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		this.font.draw(spriteBatch, str, 825, 50);
		this.font.draw(spriteBatch, str2, 25, 50);
		spriteBatch.end();
	}

	void moveAi(float delta) {
		ArrayList<AiController> enemies = currentLevel.getEnemies();
		
		for (AiController ai : enemies) {
			//System.out.println(ai.getPuppetState());
			ai.act();
		}
		
	}
	
	
	/* Collision listener
	private void createCollisionListener() {
		World world = currentLevel.getWorld();
		
		world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                Gdx.app.log("beginContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
                Gdx.app.log("endContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
            }

        });
	}
	
	private void detectContacts() {
		World world = currentLevel.getWorld();
		   int numContacts = world.getContactCount();
	        if (numContacts > 0) {
	            Gdx.app.log("contact", "start of contact list");
	            for (Contact contact : world.getContactList()) {
	                Fixture fixtureA = contact.getFixtureA();
	                Fixture fixtureB = contact.getFixtureB();
	                Gdx.app.log("contact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
	            }
	            Gdx.app.log("contact", "end of contact list");
	        }
		
	}
*/
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
			p.shoot();
		} else {
			p.releaseTrigger();
		}
		
		p.setRotation(calculatePlayerAngle(Gdx.input.getX(), Gdx.input.getY(), Config.SCREEN_WIDTH/2, Config.SCREEN_HEIGHT/2));
		
	}


	private float calculatePlayerAngle(int mouseX, int mouseY, float playerX, float playerY) {
		//System.out.println(Math.toDegrees((float)Math.atan2((float)mouseY-playerY, (float)mouseX-playerX)));
		return ((float)Math.atan2((float)mouseY-playerY, (float)mouseX-playerX));
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
