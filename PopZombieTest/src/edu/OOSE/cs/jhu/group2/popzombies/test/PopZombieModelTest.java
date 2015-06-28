package edu.OOSE.cs.jhu.group2.popzombies.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.OOSE.cs.jhu.group2.PopZombiesModel.Parachute;
import edu.OOSE.cs.jhu.group2.PopZombiesModel.PopZombieModel;
import edu.OOSE.cs.jhu.group2.PopZombiesModel.Popcorn;
import edu.OOSE.cs.jhu.group2.PopZombiesModel.Position;
import edu.OOSE.cs.jhu.group2.PopZombiesModel.Zombie;
import junit.framework.Assert;
import junit.framework.TestCase;

public class PopZombieModelTest extends TestCase {

    private PopZombieModel model;

    @BeforeClass
    protected void setUp() {
        model = new PopZombieModel(500);
    }

    @Test
    public void testMoveZombies() {
        // Store zombies in game and store original positions of zombies
        List<Zombie> zombies = model.getZombies();
        List<Float> positionTheta = new ArrayList<Float>();
        List<Float> positionR = new ArrayList<Float>();
        for (Zombie z : zombies) {
            positionTheta.add(z.getPosition().getTheta());
            positionR.add(z.getPosition().getR());
        }

        // Move zombies and compare new position of zombie to old position 
        model.getState().gameLoop(500);
        zombies = model.getZombies();
        for (int i = 0 ; i < zombies.size(); i++) {
            Assert.assertTrue(zombies.get(i).getPosition().getTheta() == positionTheta.get(i));
            Assert.assertTrue(zombies.get(i).getPosition().getR() != positionR.get(i));
        }
    }

    @Test
    public void testCurrentScore() {
        Assert.assertEquals(0, model.getCurrentScore());
    }

    @Test
    public void testSendParachute() {
        // Parachute initially created in model should have default position
        Assert.assertTrue(model.getParachute().getPosition().getTheta() == -100.0);
        Assert.assertTrue(model.getParachute().getPosition().getR() == 0.0);
        Assert.assertTrue(model.getParachute().getPosition().getZ() == 0.0);
        
        // Before sending, should be inactive
        Assert.assertFalse(model.getActiveParachute());

        // Should be active after sending parachute
        // Parachute is sent after 15000
        try {
        	Thread.sleep(15000);
        } catch(InterruptedException e) {
        	// do nothing
        }

        Assert.assertTrue(model.getActiveParachute());
    }

    @Test
    public void testInactiveParachute() {
        // When parachute is too far out of reach, should be inactive
        model.getParachute().getPosition().setZ(model.getGameHeight() + 1);
       // model.gameLoop();
        Assert.assertFalse(model.getActiveParachute());
    }

    @Test
    public void testMovePopcorns() {
        // Collect current position of popcorns
        List<Popcorn> popcorns = model.getPopcorns();
        List<Position> oldPositions = new ArrayList<Position>();
        for (Popcorn p : popcorns) {
            oldPositions.add(p.getPosition());
        }
        
        // Move popcorn and check to make sure new position is different
        model.getState().gameLoop(500);
        popcorns = model.getPopcorns();
        for (int i = 0; i < popcorns.size(); i++) {
            Assert.assertEquals(popcorns.get(i).getPosition().getTheta(), oldPositions.get(i).getTheta());
            Assert.assertTrue(popcorns.get(i).getPosition().getR() > oldPositions.get(i).getR());
        }
    }

    @Test
    public void testCollisionCheck() {
    	// Tests to make sure that popcorns are properly colliding and the
    	// proper response is invoked
        Zombie zombie = model.addZombieToGame();
        Popcorn popcorn = model.throwPopcorn(zombie.getPosition());
        
        model.collisionCheck();
        
        for (Popcorn p : model.getPopcorns()) {
            Assert.assertNotSame(popcorn, p);
        }
    }
    
    @Test
    public void testRemoveTooFarPopcorn() {
    	// Tests to see if popcorns that are out of range are removed and rendered inactive
    	List<Zombie> zombies = model.getZombies();
    	for (Zombie z : zombies) {
    		// Set position to be beyond the range
    		Position p = new Position(z.getPosition().getTheta(), model.getGameDepth() + 5, z.getPosition().getZ()+500);
    		model.throwPopcorn(p);
    	}

    	model.removeTooFarPopcorn();
    	
    	Assert.assertTrue(model.getPopcorns().size() == 0);
    }
}
