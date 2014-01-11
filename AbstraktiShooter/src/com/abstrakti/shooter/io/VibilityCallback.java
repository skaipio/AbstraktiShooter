package com.abstrakti.shooter.io;

import com.abstrakti.shooter.objects.Player;
import com.abstrakti.shooter.objects.Wall;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

public class VibilityCallback implements RayCastCallback {

    private boolean visible;
    private Player target; 
    
    public VibilityCallback( ) {
    	this.visible = false;
    }
    @Override
    public float reportRayFixture(Fixture humanPlayer, Vector2 point, Vector2 normal, float fraction) {
        if (humanPlayer.getBody().getUserData().equals(target)) {
            return fraction;
        }

        if (humanPlayer.getBody().getUserData() instanceof Wall) {        
                visible = false;
                return fraction;            
        }

        return -1;
    }
 

    public boolean isVisible() {
        return visible;
    }

}