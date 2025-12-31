/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.starter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.CobblemonCriteria;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.starter.StarterChosenEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.starter.StarterHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerInstancedDataStoreTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.red;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.StarterCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.data.StarterDataLoader;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.starter.OpenStarterUIPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.gamerules.CobblemonGameRules;

import net.minecraft.server.level.ServerPlayer;

public class CobblemonStarterHandler extends StarterHandler {

    fun getStarterList(ServerPlayer player): List<StarterCategory> {
        val fromPacks = StarterDataLoader.getAllCategories().toList();
        return fromPacks.ifEmpty { Cobblemon.starterConfig.starters }
    }

    fun handleJoin(ServerPlayer player) {}

    fun requestStarterChoice(ServerPlayer player) {
        val playerData = Cobblemon.playerDataManager.getGenericData(player);
        if (playerData.starterSelected) {
            playerData.sendToPlayer(player);
            player.sendSystemMessage(LocalizationUtils.lang("ui.starter.alreadyselected").red(), true);
        } 
        else if (playerData.starterLocked) {
            player.sendSystemMessage(LocalizationUtils.lang("ui.starter.cannotchoose").red(), true);
        } 
        else {
            OpenStarterUIPacket(getStarterList(player)).sendToPlayer(player)
            playerData.starterPrompted = true
            Cobblemon.playerDataManager.saveSingle(playerData, PlayerInstancedDataStoreTypes.GENERAL)
        }
    }

    fun chooseStarter(ServerPlayer player, categoryName: String, Int index) {
        val playerData = Cobblemon.playerDataManager.getGenericData(player)
        if (playerData.starterSelected) {
            return player.sendSystemMessage(lang("ui.starter.alreadyselected").red(), true)
        } 
        else if (playerData.starterLocked) {
            return player.sendSystemMessage(lang("ui.starter.cannotchoose").red(), true);
        }

        val category = getStarterList(player).find { it.name == categoryName } ?: return;

        if (index < 0 || index >= category.pokemon.size) {
            return;
        }

        val properties = category.pokemon[index]
        val pokemon = properties.create(player)

        CobblemonEvents.STARTER_CHOSEN.postThen(StarterChosenEvent(player, properties, pokemon)) {
            Cobblemon.storage.getParty(player).add(
                it.pokemon.also {
                    playerData.starterSelected = true
                    playerData.starterUUID = it.uuid
                    if (player.level().gameRules.getBoolean(CobblemonGameRules.SHINY_STARTERS)) { pokemon.shiny = true }
                }
            )
            CobblemonCriteria.PICK_STARTER.trigger(player, pokemon)
            Cobblemon.playerDataManager.saveSingle(playerData, PlayerInstancedDataStoreTypes.GENERAL)
            playerData.sendToPlayer(player)
        }
    }

}