package com.abstrakti.shooter.states;

import com.abstrakti.shooter.io.GameScreen;
import com.abstrakti.shooter.managers.AssetManager;
import com.abstrakti.shooter.managers.StateManager;
import com.abstrakti.shooter.map.Level;
import com.abstrakti.shooter.map.Tile;
import com.abstrakti.shooter.objects.Ammunition;
import com.abstrakti.shooter.objects.Bullet;
import com.abstrakti.shooter.objects.Door;
import com.abstrakti.shooter.objects.GameObject;
import com.abstrakti.shooter.objects.Medpack;
import com.abstrakti.shooter.objects.Player;
import com.abstrakti.shooter.objects.Wall;
import com.abstrakti.shooter.triggers.Trigger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

class MainGameState extends State {
	private GameScreen gameScreen;
	private Level currentLevel;
	private Sound bulletFleshSound;
	private Sound bulletWallSound;
	private Sound medpackSound;
	private Sound ammoSound;
	

	public MainGameState(StateManager manager, SpriteBatch batch) {
		super(manager);	
		this.bulletFleshSound = AssetManager.getInstance().getbulletFleshSound();
		this.bulletWallSound = AssetManager.getInstance().getbulletWallSound();
		this.medpackSound = AssetManager.getInstance().getMedpackSound();
		this.ammoSound = AssetManager.getInstance().getAmmoSound();
	}

	@Override
	public void create() {
		
		this.currentLevel = new Level("level1.tmx");
		this.currentLevel.init();
		GameScreen.getInstance().setLevel(currentLevel);
		createCollisionListener();
	}
	
	private void createCollisionListener() {
		World world = currentLevel.getWorld();

		world.setContactListener(new ContactListener() {
			public void beginContact(Contact contact) {
				Fixture fixtureA = contact.getFixtureA();
				Fixture fixtureB = contact.getFixtureB();

				checkCollisionBulletAndWall(fixtureA, fixtureB);
				checkCollisionBulletAndPlayer(fixtureA, fixtureB);
				checkTriggerCollisions(fixtureA, fixtureB);
				checkCollisionAmmunitionAndPlayer(fixtureA, fixtureB);
				checkCollisionMedpackAndPlayer(fixtureA, fixtureB);
				checkCollisionBulletAndDoor(fixtureA, fixtureB);
			}

			
			private void checkCollisionAmmunitionAndPlayer(Fixture fixtureA, Fixture fixtureB) {
				if ((fixtureA.getBody().getUserData() instanceof Player) && (fixtureB.getBody().getUserData() instanceof Ammunition)) {
					System.out.println("ammo and player ");
					Ammunition a = (Ammunition) fixtureB.getBody().getUserData();
					Player p = (Player) fixtureA.getBody().getUserData();
					if (p.getStatus() != PlayerState.DEAD) {
						p.pickAmmunition(a);
						ammoSound.play(0.5f);
					}
					
				}
				if ((fixtureB.getBody().getUserData() instanceof Player) && (fixtureA.getBody().getUserData() instanceof Ammunition)) {
						System.out.println("player and ammo ");
						Ammunition a2 = (Ammunition) fixtureA.getBody().getUserData();
						Player p2 = (Player) fixtureB.getBody().getUserData();
						if (p2.getStatus() != PlayerState.DEAD) {
							p2.pickAmmunition(a2);
							ammoSound.play(0.5f);
						}
				}
					
					
					//Bullet b = (Bullet)fixtureB.getBody().getUserData();
					//b.hurt(1);
					//bulletWallSound.play(1.0f);
				
				/*
                 if ((fixtureA.getBody().getUserData() instanceof Bullet) && (fixtureB.getBody().getUserData() instanceof Wall)) {
                 	System.out.println("bullet and wall");
                 }  */
			}
			
			
			private void checkCollisionMedpackAndPlayer(Fixture fixtureA, Fixture fixtureB) {
				if ((fixtureA.getBody().getUserData() instanceof Player) && (fixtureB.getBody().getUserData() instanceof Medpack)) {
					System.out.println("ammo and player ");
					Medpack a = (Medpack) fixtureB.getBody().getUserData();
					Player p = (Player) fixtureA.getBody().getUserData();
					if (p.getStatus() != PlayerState.DEAD) {
						p.pickMedpack(a);
						medpackSound.play(0.25f);
					}
					
				}
				if ((fixtureB.getBody().getUserData() instanceof Player) && (fixtureA.getBody().getUserData() instanceof Medpack)) {
						System.out.println("player and ammo ");
						Medpack a2 = (Medpack) fixtureA.getBody().getUserData();
						Player p2 = (Player) fixtureB.getBody().getUserData();
						if (p2.getStatus() != PlayerState.DEAD) {
							p2.pickMedpack(a2);
							medpackSound.play(0.25f);
						}
				}
					
					
			}
			private void checkCollisionBulletAndDoor(Fixture fixtureA, Fixture fixtureB) {
					
				if ((fixtureA.getBody().getUserData() instanceof Door) && (fixtureB.getBody().getUserData() instanceof Bullet)) {
					System.out.println("bullet and door ");			
					Bullet b = (Bullet)fixtureB.getBody().getUserData();
					b.hurt(1);
					b.rebound();
				}
				
			}
			
			
			private void checkCollisionBulletAndWall(Fixture fixtureA, Fixture fixtureB) {
				if ((fixtureA.getBody().getUserData() instanceof Wall) && (fixtureB.getBody().getUserData() instanceof Bullet)) {

					Bullet b = (Bullet)fixtureB.getBody().getUserData();
					b.hurt(1);
					b.rebound();
					bulletWallSound.play(0.25f);
				}
				/*
                 if ((fixtureA.getBody().getUserData() instanceof Bullet) && (fixtureB.getBody().getUserData() instanceof Wall)) {
                 	System.out.println("bullet and wall");
                 }  */
			}
			public void checkTriggerCollisions(Fixture fixtureA, Fixture fixtureB) {
//				if ((fixtureA.getUserData() instanceof Trigger && (fixtureB.getBody().getUserData() instanceof GameObject))){
//					Trigger trigger = (Trigger)fixtureA.getUserData();
//					GameObject obj = (GameObject)fixtureB.getBody().getUserData();
//					trigger.contact(obj);
//				}
				if ((fixtureB.getUserData() instanceof Trigger && (fixtureA.getBody().getUserData() instanceof GameObject))){
					Trigger trigger = (Trigger)fixtureB.getUserData();
					GameObject obj = (GameObject)fixtureA.getBody().getUserData();
					trigger.contact(obj);
				}
			}
			public void checkTriggerEndOfContact(Fixture fixtureA, Fixture fixtureB) {
				if (fixtureA == null || fixtureB == null) return;
				if ((fixtureA.getUserData() instanceof Trigger && (fixtureB.getBody().getUserData() instanceof GameObject))){
					Trigger trigger = (Trigger)fixtureA.getUserData();
					GameObject obj = (GameObject)fixtureB.getBody().getUserData();
					trigger.endOfContact(obj);
				}
			}

			public void checkCollisionBulletAndPlayer(Fixture fixtureA, Fixture fixtureB) {  	
//				Object sound;
				if (((fixtureA.getBody().getUserData() instanceof Bullet) && (fixtureB.getUserData() instanceof Player))) {
					System.out.println("bullet and player");
					Bullet b = (Bullet)fixtureA.getBody().getUserData();
					b.hurt(1);
					Player p = (Player)fixtureB.getBody().getUserData();
					p.hurt(1);
					bulletFleshSound.play(1.0f);
				}
				if (((fixtureA.getUserData() instanceof Player) && (fixtureB.getBody().getUserData() instanceof Bullet))) {
					System.out.println("player and bullet");
					Bullet b = (Bullet)fixtureB.getBody().getUserData();
					b.hurt(1);
					Player p = (Player)fixtureA.getBody().getUserData();
					p.hurt(1);
					bulletFleshSound.play(1.0f);
				}
			}

            public void endContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();
               // Gdx.app.log("endContact", "between " + fixtureA.toString() + " and " + fixtureB.toString());
                this.checkTriggerEndOfContact(fixtureA, fixtureB);
            }

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub
				
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

	private void wallContact() {
		
		
	}
	
	private void playerContact() {
		
	}
	@Override
	public void dispose() {
		this.gameScreen.dispose();
	}

	@Override
	public void render() {				
		this.update();
		GameScreen.getInstance().render(Gdx.graphics.getDeltaTime());
	}

	private void update() {
		this.currentLevel.update(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void resize(int width, int height) {
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
	
	
}
