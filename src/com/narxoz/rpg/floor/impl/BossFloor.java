package com.narxoz.rpg.floor.impl;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.floor.FloorResult;
import com.narxoz.rpg.floor.TowerFloor;
import com.narxoz.rpg.state.StunnedState;

import java.util.List;

public class BossFloor extends TowerFloor {

    private Monster boss;

    @Override
    protected String getFloorName() {
        return "Boss Floor";
    }

    @Override
    protected void announce() {
        System.out.println("\n🔥 BOSS FIGHT BEGINS 🔥");
    }

    @Override
    protected void setup(List<Hero> party) {
        boss = new Monster("Dragon", 80, 15);
        System.out.println("A powerful " + boss.getName() + " appears!");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {

        int damageTaken = 0;

        while (boss.isAlive() && party.stream().anyMatch(Hero::isAlive)) {

            for (Hero hero : party) {
                if (!hero.isAlive()) continue;

                hero.onTurnStart();

                if (hero.canAct()) {
                    int dmg = hero.dealDamage();
                    boss.takeDamage(dmg);
                    System.out.println(hero.getName() + " hits boss for " + dmg);
                }

                hero.onTurnEnd();
            }

            if (boss.isAlive()) {
                for (Hero hero : party) {
                    if (!hero.isAlive()) continue;

                    boss.attack(hero);
                    hero.setState(new StunnedState()); // 🔥 қосымша эффект
                    damageTaken += 10;

                    System.out.println("Boss stuns " + hero.getName());
                }
            }
        }

        boolean cleared = !boss.isAlive();
        return new FloorResult(cleared, damageTaken, "Boss fight finished");
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (!result.isCleared()) return;

        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.heal(20);
                System.out.println(hero.getName() + " gets big reward!");
            }
        }
    }
}