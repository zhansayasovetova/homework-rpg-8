package com.narxoz.rpg.floor.impl;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.FloorResult;
import com.narxoz.rpg.floor.TowerFloor;
import com.narxoz.rpg.state.PoisonedState;

import java.util.List;

public class TrapFloor extends TowerFloor {

    @Override
    protected String getFloorName() {
        return "Poison Trap Floor";
    }

    @Override
    protected void setup(List<Hero> party) {
        System.out.println("The party hears a suspicious click...");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int damageTaken = 0;

        for (Hero hero : party) {
            if (!hero.isAlive()) continue;

            hero.takeDamage(10);
            hero.setState(new PoisonedState());
            damageTaken += 10;

            System.out.println(hero.getName() + " is hit by poison darts and loses 10 HP");
        }

        return new FloorResult(true, damageTaken, "The poison trap was triggered");
    }

    @Override
    protected boolean shouldAwardLoot(FloorResult result) {
        return false;
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        System.out.println("No loot on a trap floor");
    }
}