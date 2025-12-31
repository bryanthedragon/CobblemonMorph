/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon.LOGGER
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.Despawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.AreaSpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.BasicSpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.FishingSpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.GroundedSpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SeafloorSpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SubmergedSpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SurfaceSpawningCondition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.NPCSpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PokemonHerdSpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.PokemonSpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnAction
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.detail.SpawnDetail
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.BucketMultiplyingInfluence
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.influence.SpawningInfluence
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.AreaSpawnablePositionResolver
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.FishingSpawnablePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.GroundedSpawnablePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.LavafloorSpawnablePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.SeafloorSpawnablePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.SpawnablePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.SubmergedSpawnablePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.SurfaceSpawnablePosition
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.calculators.GroundedSpawnablePositionCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.calculators.LavafloorSpawnablePositionCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.calculators.SeafloorSpawnablePositionCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.calculators.SpawnablePositionCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.calculators.SubmergedSpawnablePositionCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.calculators.SurfaceSpawnablePositionCalculator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset.BasicSpawnDetailPreset
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset.BestSpawnerConfig
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.preset.PokemonSpawnDetailPreset
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.selection.SpawningSelector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.BasicSpawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.FixedAreaSpawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.PlayerSpawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.PlayerSpawnerFactory
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.CobblemonAgingDespawner
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import net.minecraft.server.MinecraftServer

/**
 * A grouping of all the overarching behaviours of the Best Spawner system. This is a convenient accessor to
 * the configuration and many other properties used by the spawner.
 *
 * The Best Spawner (in world spawning) works in distinct stages that are:
 * - Spawning zone generation (see: [SpawningZoneGenerator])
 * - Spawnable position resolving (see: [AreaSpawnablePositionResolver])
 * - Spawn selection (see: [SpawningSelector])
 * - Spawn action (see: [SpawnAction])
 *
 * In the case of more specialized use, the creation of a [SpawnablePosition] that motivates most of the spawn
 * process can be created manually, skipping the first two steps.
 *
 * An individually spawnable thing is defined as a [SpawnDetail]. A processor handling this process is a [Spawner].
 * Some subclasses exist for more specialized cases. If you are spawning in a fixed area and the spawner controlling
 * that area is unmoving then it is a [FixedAreaSpawner] whereas if it is actively following the player it is a
 * [PlayerSpawner]. You can also use a [BasicSpawner] for more general-purpose spawning without any issue.
 *
 * PlayerSpawners are stored inside ServerPlayers using a mixin and tick at the end of a player's tick. The [PlayerSpawnerFactory]
 * is used to build those. An extension function exists for getting the spawner for a player.
 *
 * Spawners and spawnable positions are often put under the effects of [SpawningInfluence]s which can be used to make
 * temporary or lasting changes to spawning for whatever component they are attached to (whether that is a spawner or a
 * spawnable position). This pairs strongly with edits to the influence builders inside the [PlayerSpawnerFactory]. The
 * range of effects an influence can exert is very broad.
 *
 * Broad configuration of this spawning system is found in [BestSpawner.config].
 *
 * @author Hiroku
 * @since July 8th, 2022
 */
public final class BestSpawner {
    var config = BestSpawnerConfig()

    lateinit var defaultPokemonDespawner: Despawner<PokemonEntity>
    lateinit var fishingSpawner: BasicSpawner

    fun init() {
        LOGGER.info("Starting the Best Spawner...")

        SpawningCondition.register(BasicSpawningCondition.NAME, BasicSpawningCondition.class)
        SpawningCondition.register(AreaSpawningCondition.NAME, AreaSpawningCondition.class)
        SpawningCondition.register(SubmergedSpawningCondition.NAME, SubmergedSpawningCondition.class)
        SpawningCondition.register(GroundedSpawningCondition.NAME, GroundedSpawningCondition.class)
        SpawningCondition.register(SurfaceSpawningCondition.NAME, SurfaceSpawningCondition.class)
        SpawningCondition.register(SeafloorSpawningCondition.NAME, SeafloorSpawningCondition.class)
        SpawningCondition.register(FishingSpawningCondition.NAME, FishingSpawningCondition.class)

        LOGGER.info("Loaded ${SpawningCondition.conditionTypes.size} spawning condition types.")

        SpawnablePositionCalculator.register(GroundedSpawnablePositionCalculator)
        SpawnablePositionCalculator.register(SeafloorSpawnablePositionCalculator)
        SpawnablePositionCalculator.register(LavafloorSpawnablePositionCalculator)
        SpawnablePositionCalculator.register(SubmergedSpawnablePositionCalculator)
        SpawnablePositionCalculator.register(SurfaceSpawnablePositionCalculator)

        SpawnablePosition.register(name = "grounded", clazz = GroundedSpawnablePosition.class, defaultCondition = GroundedSpawningCondition.NAME)
        SpawnablePosition.register(name = "seafloor", clazz = SeafloorSpawnablePosition.class, defaultCondition = SeafloorSpawningCondition.NAME)
        SpawnablePosition.register(name = "lavafloor", clazz = LavafloorSpawnablePosition.class, defaultCondition = GroundedSpawningCondition.NAME)
        SpawnablePosition.register(name = "submerged", clazz = SubmergedSpawnablePosition.class, defaultCondition = SubmergedSpawningCondition.NAME)
        SpawnablePosition.register(name = "surface", clazz = SurfaceSpawnablePosition.class, defaultCondition = SurfaceSpawningCondition.NAME)
        SpawnablePosition.register(name = "fishing", clazz = FishingSpawnablePosition.class, defaultCondition = FishingSpawningCondition.NAME)

        LOGGER.info("Loaded ${SpawnablePosition.spawnablePositionTypes.size} spawnable position types.")

        SpawnDetail.registerSpawnType(name = PokemonSpawnDetail.TYPE, PokemonSpawnDetail.class)
        SpawnDetail.registerSpawnType(name = NPCSpawnDetail.TYPE, NPCSpawnDetail.class)
        SpawnDetail.registerSpawnType(name = PokemonHerdSpawnDetail.TYPE, PokemonHerdSpawnDetail.class)
        LOGGER.info("Loaded ${SpawnDetail.spawnDetailTypes.size} spawn detail types.")

        loadConfig()

        SpawnDetailPresets.registerPresetType(BasicSpawnDetailPreset.NAME, BasicSpawnDetailPreset.class)
        SpawnDetailPresets.registerPresetType(PokemonSpawnDetailPreset.NAME, PokemonSpawnDetailPreset.class)
    }

    fun loadConfig() {
        defaultPokemonDespawner = CobblemonAgingDespawner(getAgeTicks = { it.ticksLived })
        config = BestSpawnerConfig.load()
    }

    fun reloadConfig() {
        loadConfig()
    }

    fun onServerStarted(server: MinecraftServer) {
        CobblemonSpawnPools.onServerLoad(server)
        fishingSpawner = BasicSpawner(
            name = "fishing",
            spawnPool = CobblemonSpawnPools.WORLD_SPAWN_POOL
        ).also {
            it.influences.add(
                BucketMultiplyingInfluence(
                    multipliers = mapOf(
                        "uncommon" to 2.25f,
                        "rare" to 5.5f,
                        "ultra-rare" to 5.5f,
                    )
                )
            )
        }
    }
}