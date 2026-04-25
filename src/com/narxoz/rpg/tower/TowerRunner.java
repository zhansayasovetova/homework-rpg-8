package com.narxoz.rpg.tower;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.FloorResult;
import com.narxoz.rpg.floor.TowerFloor;

import java.util.List;

public class TowerRunner {

    private final List<TowerFloor> floors;

    public TowerRunner(List<TowerFloor> floors) {
        this.floors = floors;
    }

    public TowerRunResult run(List<Hero> party) {
        int floorsCleared = 0;

        for (TowerFloor floor : floors) {
            if (party.stream().noneMatch(Hero::isAlive)) {
                break;
            }

            FloorResult result = floor.explore(party);

            System.out.println("Floor result: " + result.getSummary());
            System.out.println("Damage taken: " + result.getDamageTaken());

            if (!result.isCleared()) {
                break;
            }

            floorsCleared++;
        }

        int heroesSurviving = (int) party.stream().filter(Hero::isAlive).count();
        boolean reachedTop = floorsCleared == floors.size();

        return new TowerRunResult(floorsCleared, heroesSurviving, reachedTop);
    }
}