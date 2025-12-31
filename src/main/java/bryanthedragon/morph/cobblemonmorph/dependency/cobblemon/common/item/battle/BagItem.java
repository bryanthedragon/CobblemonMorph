/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerPlayer;

public interface BagItem {

    // Kotlin properties become getters in Java
    String getItemName();
    Item getReturnItem();

    boolean canUse(ItemStack stack, PokemonBattle battle, BattlePokemon target);

    String getShowdownInput(
        BattleActor actor,
        BattlePokemon battlePokemon,
        String data
    );

    // Companion object → static inner class
    final class Companion {

        public static final BagItem EMPTY = new BagItem() {

            @Override
            public String getItemName() {
                return "name";
            }

            @Override
            public Item getReturnItem() {
                return Items.AIR;
            }

            @Override
            public boolean canUse(
                ItemStack stack,
                PokemonBattle battle,
                BattlePokemon target
            ) {
                return true;
            }

            @Override
            public String getShowdownInput(
                BattleActor actor,
                BattlePokemon battlePokemon,
                String data
            ) {
                return "none";
            }
        };

        private Companion() {} // Kotlin-style static holder
    }
}
