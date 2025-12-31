/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.data

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon.LOGGER
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBehaviours
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonCallbacks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonCosmeticItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMechanics
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonRideSettings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonUnlockableWallpapers
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.Abilities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berries
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.cooking.Seasonings
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.Dialogues
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fishing.PokeRods
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fishing.SpawnBait
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fishing.SpawnBaitEffects
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.Fossils
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.NaturalMaterials
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.PokemonInteractions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.item.HeldItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mark.Marks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffects
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.NPCClasses
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.npc.NPCPresets
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.DexAdditions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.Dexes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.entry.DexEntries
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokedex.entry.DexEntryAdditions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.GlobalSpeciesFeatures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureAssignments
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scripting.CobblemonScripts
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.CobblemonSpawnPools
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.CobblemonSpawnRules
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnDetailPresets
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.SpeciesAdditions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.PropertiesCompletionProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.cobblemonResource
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ifClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.server
import java.util.UUID
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.ResourceManagerReloadListener
public final class CobblemonDataProvider : DataProvider {

    // Both Forge n Fabric keep insertion order so if a registry depends on another simply register it after
    private val registries = linkedSetOf<DataRegistry>()
    private val reloadableRegistries = linkedSetOf<DataRegistry>()
    private val synchronizedPlayerIds = mutableListOf<UUID>()

    private val scheduledActions = mutableMapOf<UUID, MutableList<() -> Unit>>()

    fun registerDefaults() {
        this.register(CobblemonScripts, reloadable = true)
        this.register(SpeciesFeatures, reloadable = false)
        this.register(GlobalSpeciesFeatures, reloadable = false)
        this.register(SpeciesFeatureAssignments, reloadable = false)
        this.register(ActionEffects, reloadable = true)
        this.register(Moves, reloadable = false)
        this.register(Abilities, reloadable = false)
        this.register(CobblemonBehaviours, reloadable = false)
        this.register(PokemonSpecies, reloadable = false)
        this.register(SpeciesAdditions, reloadable = false)
        this.register(PokeBalls, reloadable = false)
        this.register(PropertiesCompletionProvider, reloadable = false)
        this.register(SpawnDetailPresets, reloadable = true)
        this.register(CobblemonSpawnRules, reloadable = true)
        this.register(CobblemonMechanics, reloadable = true)
        this.register(BagItems, reloadable = false)
        this.register(HeldItems, reloadable = false)
        this.register(Dialogues, reloadable = true)
        this.register(NaturalMaterials, reloadable = true)
        this.register(Fossils, reloadable = true)
        this.register(NPCPresets, reloadable = false)
        this.register(NPCClasses, reloadable = false)
        this.register(DexEntries, reloadable = false)
        this.register(DexEntryAdditions, reloadable = false)
        this.register(Dexes, reloadable = false)
        this.register(DexAdditions, reloadable = false)
        this.register(CobblemonCosmeticItems, reloadable = true)
        this.register(CobblemonCallbacks, reloadable = true)
        this.register(CobblemonUnlockableWallpapers, reloadable = true)
        this.register(Marks, reloadable = false)
        this.register(StarterDataLoader, reloadable = true)

        CobblemonSpawnPools.load()
        this.register(PokeRods, reloadable = false)
        this.register(Berries, reloadable = false)
        this.register(Seasonings, reloadable = false)
        this.register(PokemonInteractions, reloadable = false)
        this.register(SpawnBaitEffects, reloadable = false)
        this.register(CobblemonRideSettings, reloadable = true)
        SpawnBait.Effects.setupEffects()

        PlatformEvents.SERVER_PLAYER_LOGOUT.subscribe {
            synchronizedPlayerIds.remove(it.player.uuid)
        }

        ifClient {
            Cobblemon.implementation.registerResourceReloader(cobblemonResource("client_resources"), SimpleResourceReloader(PackType.CLIENT_RESOURCES), PackType.CLIENT_RESOURCES, emptyList())
        }
        Cobblemon.implementation.registerResourceReloader(cobblemonResource("data_resources"), SimpleResourceReloader(PackType.SERVER_DATA), PackType.SERVER_DATA, emptyList())
    }

    override fun <T : DataRegistry> register(registry: T, reloadable: Boolean): T {
        // Only send message once
        if (this.registries.isEmpty()) {
            LOGGER.info("Note: Cobblemon data registries are only loaded once per server instance as Pokémon species are not safe to reload.")
        }
        this.registries.add(registry)
        if (reloadable) {
            this.reloadableRegistries.add(registry)
        }
        LOGGER.info("Registered the {} registry", registry.id.toString())
        LOGGER.debug("Registered the {} registry of class {}", registry.id.toString(), registry::class.qualifiedName)
        return registry
    }

    override fun fromIdentifier(registryResourceLocation identifier): DataRegistry? = this.registries.find { it.id == registryIdentifier }

    override fun sync(ServerPlayer player) {
        if (!player.connection.connection.isMemoryConnection) {
            this.registries.forEach { registry ->
                registry.sync(player)
            }
        }

        CobblemonEvents.DATA_SYNCHRONIZED.emit(player)
        val waitingActions = this.scheduledActions.remove(player.uuid) ?: return
        waitingActions.forEach { it() }
    }

    override fun doAfterSync(ServerPlayer player, action: () -> Unit) {
        if (player.uuid in synchronizedPlayerIds) {
            action()
        } else {
            this.scheduledActions.computeIfAbsent(player.uuid) { mutableListOf() }.add(action)
        }
    }

    private class SimpleResourceReloader(private val type: PackType) : ResourceManagerReloadListener {
        override fun onResourceManagerReload(ResourceManager manager) {
            // Check for a server running, this is due to the create a world screen triggering datapack reloads, these are fine to happen as many times as needed as players may be in the process of adding their datapacks.
            val reloadAllowed = server()?.isReady != true
            registries.filter { it.type == this.type && (reloadAllowed || it in reloadableRegistries) }
                .forEach { it.reload(manager) }
        }
    }
}
