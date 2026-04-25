package com.narxoz.rpg.floor.impl;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.combatant.Monster;
import com.narxoz.rpg.floor.FloorResult;
import com.narxoz.rpg.floor.TowerFloor;

import java.util.List;

public class BattleFloor extends TowerFloor {

    private Monster monster;

    @Override
    protected String getFloorName() {
        return "Battle Floor";
    }

    @Override
    protected void setup(List<Hero> party) {
        monster = new Monster("Skeleton", 40, 10);
        System.out.println("A wild " + monster.getName() + " appears!");
    }

    @Override
    protected FloorResult resolveChallenge(List<Hero> party) {
        int damageTaken = 0;

        while (monster.isAlive() && party.stream().anyMatch(Hero::isAlive)) {
            for (Hero hero : party) {
                if (!hero.isAlive()) continue;

                hero.onTurnStart();

                if (hero.canAct()) {
                    int damage = hero.dealDamage();
                    monster.takeDamage(damage);
                    System.out.println(hero.getName() + " hits " + monster.getName() + " for " + damage);
                }

                hero.onTurnEnd();
            }

            if (monster.isAlive()) {
                for (Hero hero : party) {
                    if (!hero.isAlive()) continue;

                    int beforeHp = hero.getHp();
                    monster.attack(hero);
                    int actualDamage = beforeHp - hero.getHp();
                    damageTaken += actualDamage;

                    System.out.println(monster.getName() + " attacks " + hero.getName()
                            + " for " + actualDamage + " damage");
                }
            }
        }

        boolean cleared = !monster.isAlive();
        return new FloorResult(cleared, damageTaken, "Battle with " + monster.getName() + " finished");
    }

    @Override
    protected void awardLoot(List<Hero> party, FloorResult result) {
        if (!result.isCleared()) return;

        for (Hero hero : party) {
            if (hero.isAlive()) {
                hero.heal(10);
                System.out.println(hero.getName() + " receives loot and heals 10 HP");
            }
        }
    }
}