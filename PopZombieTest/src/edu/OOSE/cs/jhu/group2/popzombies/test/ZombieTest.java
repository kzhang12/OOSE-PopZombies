package edu.OOSE.cs.jhu.group2.popzombies.test;

import org.junit.BeforeClass;

import org.junit.Test;

import edu.OOSE.cs.jhu.group2.PopZombiesModel.Position;
import edu.OOSE.cs.jhu.group2.PopZombiesModel.Zombie;
import junit.framework.Assert;
import junit.framework.TestCase;

public class ZombieTest extends TestCase {
    private Zombie zombie;
    private Position p;

    @BeforeClass
    protected void setUp() {
        zombie = new Zombie(new Position(0,0,0), 50);
    }

    @Test
    public void testHealth() {
        Assert.assertEquals(50, zombie.getTotalHealth());
        Assert.assertEquals(50, zombie.getCurrentHealth());
    }
    
    @Test
    public void testDecreaseHealth() {
        zombie.decreaseHealth();
        Assert.assertEquals(25, zombie.getCurrentHealth());
        Assert.assertEquals(50, zombie.getTotalHealth());
        zombie.decreaseHealth();
        zombie.decreaseHealth();
        Assert.assertEquals(0, zombie.getCurrentHealth());
    }
    
    @Test
    public void testIncreaseHealth() {
        zombie.increaseHealth(100);
        Assert.assertEquals(50, zombie.getCurrentHealth());
        Assert.assertEquals(50, zombie.getTotalHealth());
        zombie.decreaseHealth();
        zombie.increaseHealth(10);
        Assert.assertEquals(35, zombie.getCurrentHealth());
    }

    @Test
    public void testUpdate(){
        float t = 0, z = 0, r = 0;
        zombie.update(500);
        Assert.assertEquals(zombie.getPosition().getTheta(), t);
        Assert.assertEquals(zombie.getPosition().getR(), r);
        Assert.assertEquals(zombie.getPosition().getZ(), z);
    }

}