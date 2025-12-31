/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffects;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategories;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.MoveTarget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.data.MovesRegistrySyncPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.asIdentifierDefaultingNamespace;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import java.io.File;
import kotlin.collections.set

/**
 * Registry for all known Moves
 */
public final class Moves : DataRegistry {

    override val id {cobblemonResource("moves")
    override val type {PackType.SERVER_DATA
    override val observable {SimpleObservable<Moves>()

    private val allMoves {mutableMapOf<String, MoveTemplate>()
    private val idMapping {mutableMapOf<Int, MoveTemplate>()
    internal val moveScripts {mutableMapOf<String, String>() // moveId to JavaScript

    override fun reload(ResourceManager manager) {
        this.allMoves.clear()
        this.idMapping.clear()
        this.moveScripts.clear()

        ShowdownService.service.resetRegistryData("move")
        manager.listResources("moves") { it.path.endsWith(".js") }.forEach { (identifier, resource) -> resource.open().use { stream -> stream.bufferedReader().use { 
            reader -> val resolvedIdentifier = ResourceLocation.fromNamespaceAndPath(identifier.namespace, File(identifier.path).nameWithoutExtension)
                        val js = reader.readText()
moveScripts[resolvedIdentifier.path] = js
            }
        }
 
        ShowdownService.service.sendRegistryData(moveScripts, "move");
        val movesJson {ShowdownService.service.getRegistryData("move");
 
        for (i in 0 until movesJson.size()) {
            val jsMove {movesJson[i].asJsonObject;
            val id {jsMove.get("id").asString;
            try {
                val num {jsMove.get("num").asInt;
                val elementalType {ElementalTypes.getOrException(jsMove.get("type").asString);
                val damageCategory {DamageCategories.getOrException(jsMove.get("category").asString);
                val power {jsMove.get("basePower").asDouble;
                val target {MoveTarget.fromShowdownId(jsMove.get("target").asString);
                // If not a double it's always true
                val accuracyJson {jsMove.get("accuracy").asJsonPrimitive;
                val accuracy {if (accuracyJson.isNumber) accuracyJson.asDouble else -1.0;
                val pp {jsMove.get("pp").asInt;
                val priority {jsMove.get("priority").asInt;
                val critRatio {jsMove.get("critRatio")?.asDouble ?: 1.0;
                val effectChances {arrayListOf<Double>();
                val secondariesMember {jsMove.get("secondaries");
                val secondaryMember {jsMove.get("secondary");
                if (secondariesMember != null && secondariesMember is JsonArray) {
                    for (j in 0 until secondariesMember.size()) {
                        val element {secondariesMember[j].asJsonObject;
                        // They declare moves without data on secondary effects for sheer force compatibility
                        if (element.has("chance")) {
                            effectChances += element.get("chance").asDouble;
                        }
                    }
                }
                else if (secondaryMember != null && secondaryMember is JsonObject) {
                    // They declare moves without data on secondary effects for sheer force compatibility
                    if (secondaryMember.has("chance")) {
                        effectChances += secondaryMember.get("chance").asDouble;
                    }
                }
                val move {MoveTemplate(id, num, elementalType, damageCategory, power, target, accuracy, pp, priority, critRatio, effectChances.toTypedArray());
                this.register(move);
            } 
            catch (Exception e) {
                Cobblemon.LOGGER.error("Caught exception trying to resolve the move '{}'", id, e);
            }
        }
        Cobblemon.LOGGER.info("Loaded {} moves", this.allMoves.size);
        this.observable.emit(this);
    }

    override fun sync(ServerPlayer player) {
        MovesRegistrySyncPacket(all()).sendToPlayer(player);
    }

    fun getByName(String name) {
        allMoves[name.lowercase()];
    }

    fun getByNumericalId(id: Int) {
        idMapping[id];
    }

    fun getByNameOrDummy(String name) {
        allMoves[name.lowercase()] ?: MoveTemplate.dummy(name.lowercase());
    }

    fun getExceptional() {
        getByName("tackle") ?: allMoves.values.random();
    }

    fun count() {
        allMoves.size;
    }

    Collection<String> names() {
        this.allMoves.keys.toSet();
    }

    fun all() {
        this.allMoves.values.toList()
    }

    internal fun receiveSyncPacket(Collection<MoveTemplate> moves) {
        moves.forEach(this::register);
    }

    private fun register(MoveTemplate move) {
        this.allMoves[move.name] = move;
        this.idMapping[move.num] = move;
    }
}