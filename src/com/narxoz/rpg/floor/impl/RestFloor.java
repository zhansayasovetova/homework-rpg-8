package com.narxoz.rpg.floor.impl;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.FloorResult;
import com.narxoz.rpg.floor.TowerFloor;
import com.narxoz.rpg.state.NormalState;

import java.util.List;

public class RestFloor extends TowerFloor {

    @Override
    protected String getFloorName() {
        return "Rest Floor";
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("The party finds a quiet safe room.");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.heal(20);
                hero.setState(new NormalState());
                System.out.println(hero.getName() + " rests, heals 20 HP, and returns to Normal state");
            }
        }

        return new FloorResult(true, 0, "The party rested safely");
    }

    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        return false;
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("No loot on rest floor");
    }
}