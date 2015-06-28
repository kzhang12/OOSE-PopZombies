package edu.OOSE.cs.jhu.group2.popzombies.test;

import junit.framework.TestCase;
import org.junit.Test;
import edu.OOSE.cs.jhu.group2.PopZombiesModel.Player;
import junit.framework.Assert;

public class PlayerTest extends TestCase {
    private Player p;

    public PlayerTest (){
    	p = new Player(20);
    }

    @Test
    public void testTakeParachute() {
        //Beginning of game, there should be 0 supply
        Assert.assertEquals(10, p.getSupply());
        Assert.assertEquals(true, p.hasSupply());
        p.takeParachute(15);
        //After parachute taken, there should be 15
        Assert.assertEquals(25, p.getSupply());
        Assert.assertEquals(true, p.hasSupply());
    }

    @Test
    public void testMicrowavePopcorn() {
        //Beginning of game, there should be 0 ammo
        Assert.assertEquals(50, p.getAmmo());
        Assert.assertEquals(true, p.hasAmmo());
        p.takeParachute(10);
        p.microwavePopcorn();
        //After parachute taken, there should be 10
        Assert.assertEquals(70, p.getAmmo());
        Assert.assertEquals(true, p.hasAmmo());
    }

    @Test
    public void testHurt() {
        //Beginning of game, there should be 20 health
        Assert.assertEquals(20, p.getCurrentHealth());
        Assert.assertEquals(true, p.isAlive());
        p.hurt();
        //After hurt, there should be 15
        Assert.assertEquals(19, p.getCurrentHealth());
        Assert.assertEquals(true, p.isAlive());
        p.hurt();
        p.hurt();
        p.hurt();
        //when health reaches 0, player should die
        Assert.assertEquals(16, p.getCurrentHealth());
        Assert.assertEquals(true, p.isAlive());
    }

    @Test
    public void testEatSupply() {
        p.takeParachute(10);
        p.microwavePopcorn();
    	p.eatSupply();
        //If health is full, eat supply should not increase health
        Assert.assertEquals(20, p.getCurrentHealth());
        p.takeParachute(10);
        p.microwavePopcorn();
        p.hurt();
        Assert.assertEquals(19, p.getCurrentHealth());
        //If health is not full, eat supply should increase health by 2
    	p.eatSupply();
        Assert.assertEquals(20, p.getCurrentHealth());
    }

    @Test
    public void testThrowPopcorn() {
        //Beginning of game, there should be 0 ammo
        Assert.assertEquals(50, p.getAmmo());
        Assert.assertEquals(true, p.hasAmmo());
        p.takeParachute(10);
        p.microwavePopcorn();
        //After parachute taken, there should be 10
        Assert.assertEquals(70, p.getAmmo());
        Assert.assertEquals(true, p.hasAmmo());
        p.throwPopcorn();
        //Throw popcorn should decrease ammo by 1
        Assert.assertEquals(69, p.getAmmo());        
    }
}
