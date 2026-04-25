package com.narxoz.rpg;

import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.floor.TowerFloor;
import com.narxoz.rpg.floor.impl.BattleFloor;
import com.narxoz.rpg.floor.impl.RestFloor;
import com.narxoz.rpg.floor.impl.TrapFloor;
import com.narxoz.rpg.state.BerserkState;
import com.narxoz.rpg.state.StunnedState;
import com.narxoz.rpg.tower.TowerRunner;
import com.narxoz.rpg.tower.TowerRunResult;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Hero aragorn = new Hero("Aragorn", 100, 15, 5);
        Hero legolas = new Hero("Legolas", 85, 18, 3);

        aragorn.setState(new BerserkState());
        legolas.setState(new StunnedState());

        List<Hero> party = Arrays.asList(aragorn, legolas);

        List<TowerFloor> floors = Arrays.asList(
                new BattleFloor(),
                new TrapFloor(),
                new RestFloor(),
                new BattleFloor()
        );

        TowerRunner runner = new TowerRunner(floors);
        TowerRunResult result = runner.run(party);

        System.out.println("\n=== FINAL RESULT ===");
        System.out.println("Floors cleared: " + result.getFloorsCleared());
        System.out.println("Heroes surviving: " + result.getHeroesSurviving());
        System.out.println("Reached top: " + result.isReachedTop());

        System.out.println("\n=== FINAL HERO STATUS ===");
        for (Hero hero : party) {
            System.out.println(hero.getName()
                    + " | HP: " + hero.getHp()
                    + "/" + hero.getMaxHp()
                    + " | State: " + hero.getState().getName());
        }
    }
}