/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon.LOGGER
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonClientImplementation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonEntities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonMenuType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berries
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ClientTaskTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.client.ClientGeneralPlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.client.ClientPokedexManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonItemTags
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.TintBlockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.PartyOverlay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.PartyOverlayDataControl
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.RideControlsOverlay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleOverlay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.cookingpot.CookingPotScreen
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.BedrockParticleOptionsRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.ClientPlayerIcon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.DeferredRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.BerryBlockRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.CampfireBlockEntityRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.DisplayCaseRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.FossilAnalyzerRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.GildedChestBlockRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.HealingMachineRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.LecternBlockEntityRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.PokeSnackBlockEntityRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.RestorationTankRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.boat.CobblemonBoatRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.color.AprijuiceItemColorProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.color.PokeBaitItemColorProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.color.PokeSnackItemColorProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.color.PonigiriItemColorProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.color.SinisterTeaItemColorProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.entity.PokeBobberEntityRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.generic.GenericBedrockRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item.CobblemonBuiltinItemRendererRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item.PokemonItemRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.PokemonOnShoulderRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockAnimationRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.BerryModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.MiscModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.VaryingModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.npc.NPCRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokeball.PokeBallRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokemon.PokemonRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.requests.ClientPlayerActionRequests
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.BattleMusicController
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.EntitySoundTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientStorageManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.tooltips.AprijuiceTooltipGenerator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.tooltips.CobblemonTooltipGenerator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.tooltips.FishingBaitTooltipGenerator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.tooltips.FishingRodTooltipGenerator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.tooltips.PokePuffTooltipGenerator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.tooltips.RecipeSeasoningAbsorptionTooltipGenerator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.tooltips.SeasoningTooltipGenerator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.tooltips.TooltipManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade.ClientTrade
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonBoatType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokedex.scanner.PokedexUsageContext
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.isLookingAt
import net.minecraft.client.Minecraft
import net.minecraft.client.color.block.BlockColor
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.MenuScreens
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.model.BoatModel
import net.minecraft.client.model.ChestBoatModel
import net.minecraft.client.model.PlayerModel
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.HangingSignRenderer
import net.minecraft.client.renderer.blockentity.SignRenderer
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.LivingEntityRenderer
import net.minecraft.client.resources.PlayerSkin
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.AABB
final class CobblemonClient {

    lateinit var implementation: CobblemonClientImplementation
    val storage = ClientStorageManager()
    var trade: ClientTrade? = null
    var battle: ClientBattle? = null
    var clientPlayerData = ClientGeneralPlayerData()
    var clientPokedexData = ClientPokedexManager(mutableMapOf())

    /** If true then we won't bother them anymore about choosing a starter even if it's a thing they can do. */
    var checkedStarterScreen = false
    var lastPcBoxViewed = 0
    var requests = ClientPlayerActionRequests()
    var teamData = ClientPlayerTeamData()
    val overlay: PartyOverlay by lazy { PartyOverlay() }
    val battleOverlay: BattleOverlay by lazy { BattleOverlay() }
    val pokedexUsageContext: PokedexUsageContext by lazy { PokedexUsageContext() }
    val rideControlsOverlay: RideControlsOverlay by lazy { RideControlsOverlay() }

    fun onLogin() {
        clientPlayerData = ClientGeneralPlayerData()
        requests = ClientPlayerActionRequests()
        teamData = ClientPlayerTeamData()
        clientPokedexData = ClientPokedexManager(mutableMapOf())
        storage.onLogin()
//        CobblemonDataProvider.canReload = false
    }

    fun onLogout() {
        storage.onLogout()
        battle = null
        battleOverlay.onLogout()
        ClientTaskTracker.clear()
        checkedStarterScreen = false
//        CobblemonDataProvider.canReload = true
        DeferredRenderer.clearAll()
        ClientPlayerIcon.clear()
    }

    fun initialize(implementation: CobblemonClientImplementation) {
        LOGGER.info("Initializing Cobblemon client")
        this.implementation = implementation

        PlatformEvents.CLIENT_PLAYER_LOGIN.subscribe { onLogin() }
        PlatformEvents.CLIENT_PLAYER_LOGOUT.subscribe { onLogout() }

        this.registerBlockEntityRenderers()
        registerBlockRenderTypes()
        registerColors()
        registerFlywheelRenderers()
        this.registerEntityRenderers()
        this.registerItemColors()
        Berries.observable.subscribe {
            BerryModelRepository.patchModels()
        }
        this.registerTooltipManagers()
        this.registerMenuScreens()

        LOGGER.info("Registering custom BuiltinItemRenderers")
        CobblemonBuiltinItemRendererRegistry.register(CobblemonItems.POKEMON_MODEL, PokemonItemRenderer())

        PlatformEvents.CLIENT_ITEM_TOOLTIP.subscribe { event ->
            val stack = event.stack
            val lines = event.lines
            TooltipManager.generateTooltips(stack, lines, Screen.hasShiftDown())
        }

        PlatformEvents.CLIENT_ENTITY_UNLOAD.subscribe { event -> EntitySoundTracker.clear(event.entity.id) }
        PlatformEvents.CLIENT_TICK_POST.subscribe { event ->
            val player = event.client.player
            if (player != null) {
                var selectedItem = player.inventory.getItem(player.inventory.selected)
                if (pokedexUsageContext.scanningGuiOpen &&
                    !(selectedItem.`is`(CobblemonItemTags.POKEDEX)) &&
                    !(player.offhandItem.`is`(CobblemonItemTags.POKEDEX) &&
                            player.isUsingItem == true &&
                            player.usedItemHand == InteractionHand.OFF_HAND
                            )
                ) {
                    // Stop using Pokédex in main hand if player switches to a different slot in hotbar
                    pokedexUsageContext.stopUsing(PokedexUsageContext.OPEN_SCANNER_BUFFER_TICKS + 1)
                }
                if (event.client.isPaused) {
                    return@subscribe
                }

                val nearbyPokemon = player.level().getEntities(
                    player,
                    AABB.ofSize(player.position(), 16.0, 16.0, 16.0)
                ) { it is PokemonEntity }

                nearbyPokemon?.forEach { entity ->
                    if (entity is PokemonEntity && !entity.isSilent && !entity.passengers.contains(player)) {
                        if (player.isLookingAt(entity) && !player.isSpectator && entity.pokemon.shiny) entity.delegate.spawnShinyParticle(player)
                        entity.delegate.spawnAspectParticle()
                    }
                }
            }
            ClientPlayerIcon.onTick()
            PartyOverlayDataControl.tick(event.client.isPaused)
        }
    }

    private fun registerTooltipManagers() {
        TooltipManager.registerTooltipGenerator(CobblemonTooltipGenerator)
        TooltipManager.registerTooltipGenerator(RecipeSeasoningAbsorptionTooltipGenerator)
        TooltipManager.registerTooltipGenerator(FishingBaitTooltipGenerator)
        TooltipManager.registerTooltipGenerator(SeasoningTooltipGenerator)
        TooltipManager.registerTooltipGenerator(FishingRodTooltipGenerator)
        TooltipManager.registerTooltipGenerator(AprijuiceTooltipGenerator)
        TooltipManager.registerTooltipGenerator(PokePuffTooltipGenerator)
    }

    fun registerFlywheelRenderers() {
//        InstancedRenderRegistry
//            .configure(CobblemonBlockEntities.BERRY)
//            .alwaysSkipRender()
//            .factory(::BerryEntityInstance)
//            .apply()
    }

    fun registerColors() {
        this.implementation.registerBlockColors(BlockColor { blockState, view, blockPos, tintIndex ->
            blockPos?.let { pos ->
                view?.getBlockEntity(pos)?.let { blockEntity ->
                    if (blockEntity is TintBlockEntity) return@BlockColor blockEntity.getTint()
                }
            }
            return@BlockColor 0xFFFFFF
        }, CobblemonBlocks.POKE_SNACK, CobblemonBlocks.POKE_CAKE)
    }

    private fun registerBlockRenderTypes() {

        this.implementation.registerBlockRenderType(
            RenderType.cutoutMipped(),
            CobblemonBlocks.APRICORN_LEAVES,
            CobblemonBlocks.SACCHARINE_LEAVES,
            CobblemonBlocks.POKE_CAKE
        )

        this.implementation.registerBlockRenderType(
            RenderType.cutout(),
            CobblemonBlocks.GILDED_CHEST,
            CobblemonBlocks.FOSSIL_ANALYZER,
            CobblemonBlocks.APRICORN_DOOR,
            CobblemonBlocks.APRICORN_TRAPDOOR,
            CobblemonBlocks.APRICORN_SIGN,
            CobblemonBlocks.APRICORN_WALL_SIGN,
            CobblemonBlocks.APRICORN_HANGING_SIGN,
            CobblemonBlocks.APRICORN_WALL_HANGING_SIGN,
            CobblemonBlocks.BLACK_APRICORN_SAPLING,
            CobblemonBlocks.BLUE_APRICORN_SAPLING,
            CobblemonBlocks.GREEN_APRICORN_SAPLING,
            CobblemonBlocks.PINK_APRICORN_SAPLING,
            CobblemonBlocks.RED_APRICORN_SAPLING,
            CobblemonBlocks.WHITE_APRICORN_SAPLING,
            CobblemonBlocks.YELLOW_APRICORN_SAPLING,
            CobblemonBlocks.POTTED_BLACK_APRICORN_SAPLING,
            CobblemonBlocks.POTTED_BLUE_APRICORN_SAPLING,
            CobblemonBlocks.POTTED_GREEN_APRICORN_SAPLING,
            CobblemonBlocks.POTTED_PINK_APRICORN_SAPLING,
            CobblemonBlocks.POTTED_RED_APRICORN_SAPLING,
            CobblemonBlocks.POTTED_WHITE_APRICORN_SAPLING,
            CobblemonBlocks.POTTED_YELLOW_APRICORN_SAPLING,
            CobblemonBlocks.BLACK_APRICORN,
            CobblemonBlocks.BLUE_APRICORN,
            CobblemonBlocks.GREEN_APRICORN,
            CobblemonBlocks.PINK_APRICORN,
            CobblemonBlocks.RED_APRICORN,
            CobblemonBlocks.WHITE_APRICORN,
            CobblemonBlocks.YELLOW_APRICORN,
            CobblemonBlocks.HEALING_MACHINE,
            CobblemonBlocks.MEDICINAL_LEEK,
            CobblemonBlocks.HEALING_MACHINE,
            CobblemonBlocks.RED_MINT,
            CobblemonBlocks.BLUE_MINT,
            CobblemonBlocks.CYAN_MINT,
            CobblemonBlocks.PINK_MINT,
            CobblemonBlocks.GREEN_MINT,
            CobblemonBlocks.WHITE_MINT,
            CobblemonBlocks.PASTURE,
            CobblemonBlocks.ENERGY_ROOT,
            CobblemonBlocks.BIG_ROOT,
            CobblemonBlocks.REVIVAL_HERB,
            CobblemonBlocks.VIVICHOKE_SEEDS,
            CobblemonBlocks.HEARTY_GRAINS,
            CobblemonBlocks.PEP_UP_FLOWER,
            CobblemonBlocks.POTTED_PEP_UP_FLOWER,
            CobblemonBlocks.REVIVAL_HERB,
            *CobblemonBlocks.berries().values.toTypedArray(),
            CobblemonBlocks.GALARICA_NUT_BUSH,
            CobblemonBlocks.RESTORATION_TANK,
            CobblemonBlocks.SMALL_BUDDING_TUMBLESTONE,
            CobblemonBlocks.MEDIUM_BUDDING_TUMBLESTONE,
            CobblemonBlocks.LARGE_BUDDING_TUMBLESTONE,
            CobblemonBlocks.TUMBLESTONE_CLUSTER,
            CobblemonBlocks.SMALL_BUDDING_BLACK_TUMBLESTONE,
            CobblemonBlocks.MEDIUM_BUDDING_BLACK_TUMBLESTONE,
            CobblemonBlocks.LARGE_BUDDING_BLACK_TUMBLESTONE,
            CobblemonBlocks.BLACK_TUMBLESTONE_CLUSTER,
            CobblemonBlocks.SMALL_BUDDING_SKY_TUMBLESTONE,
            CobblemonBlocks.MEDIUM_BUDDING_SKY_TUMBLESTONE,
            CobblemonBlocks.LARGE_BUDDING_SKY_TUMBLESTONE,
            CobblemonBlocks.SKY_TUMBLESTONE_CLUSTER,
            CobblemonBlocks.GIMMIGHOUL_CHEST,
            CobblemonBlocks.DISPLAY_CASE,
            CobblemonBlocks.SACCHARINE_DOOR,
            CobblemonBlocks.SACCHARINE_TRAPDOOR,
            CobblemonBlocks.SACCHARINE_SIGN,
            CobblemonBlocks.SACCHARINE_WALL_SIGN,
            CobblemonBlocks.SACCHARINE_HANGING_SIGN,
            CobblemonBlocks.SACCHARINE_WALL_HANGING_SIGN,
            CobblemonBlocks.SACCHARINE_SAPLING,
            CobblemonBlocks.POTTED_SACCHARINE_SAPLING,
            CobblemonBlocks.POKE_SNACK,
            CobblemonBlocks.LECTERN,
            CobblemonBlocks.CAMPFIRE,
            CobblemonBlocks.SOUL_CAMPFIRE,
            CobblemonBlocks.BLACK_CAMPFIRE_POT,
            CobblemonBlocks.BLUE_CAMPFIRE_POT,
            CobblemonBlocks.GREEN_CAMPFIRE_POT,
            CobblemonBlocks.PINK_CAMPFIRE_POT,
            CobblemonBlocks.RED_CAMPFIRE_POT,
            CobblemonBlocks.WHITE_CAMPFIRE_POT,
            CobblemonBlocks.YELLOW_CAMPFIRE_POT
        )

        this.createBoatModelLayers()
    }

    fun beforeChatRender(context: GuiGraphics, partialDeltaTicks: Float) {
        val partialDeltaTicks = Minecraft.getInstance().timer // Checking that this even works
//        ClientTaskTracker.update(partialDeltaTicks / 20f)
        if (battle == null) {
            overlay.render(context, partialDeltaTicks)
        } else {
            battleOverlay.render(context, partialDeltaTicks)
        }
        rideControlsOverlay.render(context, partialDeltaTicks)
    }

    @Suppress("UNCHECKED_CAST")
    fun onAddLayer(skinMap: Map<PlayerSkin.Model, EntityRenderer<out Player>>?) {
        var renderer: LivingEntityRenderer<Player, PlayerModel<Player>>? =
            skinMap?.get(PlayerSkin.Model.WIDE) as LivingEntityRenderer<Player, PlayerModel<Player>>
        renderer?.addLayer(PokemonOnShoulderRenderer(renderer))
        renderer = skinMap[PlayerSkin.Model.SLIM] as LivingEntityRenderer<Player, PlayerModel<Player>>?
        renderer?.addLayer(PokemonOnShoulderRenderer(renderer))
    }

    private fun registerMenuScreens() {
        MenuScreens.register(CobblemonMenuType.COOKING_POT, ::CookingPotScreen)
    }

    private fun registerBlockEntityRenderers() {
        this.implementation.registerBlockEntityRenderer(
            CobblemonBlockEntities.HEALING_MACHINE,
            ::HealingMachineRenderer
        )
        this.implementation.registerBlockEntityRenderer(CobblemonBlockEntities.BERRY, ::BerryBlockRenderer)
        this.implementation.registerBlockEntityRenderer(CobblemonBlockEntities.SIGN, ::SignRenderer)
        this.implementation.registerBlockEntityRenderer(CobblemonBlockEntities.HANGING_SIGN, ::HangingSignRenderer)
        this.implementation.registerBlockEntityRenderer(
            CobblemonBlockEntities.FOSSIL_ANALYZER,
            ::FossilAnalyzerRenderer
        )
        this.implementation.registerBlockEntityRenderer(
            CobblemonBlockEntities.RESTORATION_TANK,
            ::RestorationTankRenderer
        )
        this.implementation.registerBlockEntityRenderer(CobblemonBlockEntities.GILDED_CHEST, ::GildedChestBlockRenderer)
        this.implementation.registerBlockEntityRenderer(CobblemonBlockEntities.DISPLAY_CASE, ::DisplayCaseRenderer)
        this.implementation.registerBlockEntityRenderer(CobblemonBlockEntities.LECTERN, ::LecternBlockEntityRenderer)
        this.implementation.registerBlockEntityRenderer(CobblemonBlockEntities.CAMPFIRE, ::CampfireBlockEntityRenderer)
        this.implementation.registerBlockEntityRenderer(CobblemonBlockEntities.POKE_SNACK, ::PokeSnackBlockEntityRenderer)
    }

    private fun registerEntityRenderers() {
        LOGGER.info("Registering Pokémon renderer")
        this.implementation.registerEntityRenderer(CobblemonEntities.POKEMON, ::PokemonRenderer)
        LOGGER.info("Registering PokéBall renderer")
        this.implementation.registerEntityRenderer(CobblemonEntities.EMPTY_POKEBALL, ::PokeBallRenderer)
        LOGGER.info("Registering Boat renderer")
        this.implementation.registerEntityRenderer(CobblemonEntities.BOAT) { ctx -> CobblemonBoatRenderer(ctx, false) }
        LOGGER.info("Registering Boat with Chest renderer")
        this.implementation.registerEntityRenderer(CobblemonEntities.CHEST_BOAT) { ctx ->
            CobblemonBoatRenderer(
                ctx,
                true
            )
        }
        LOGGER.info("Registering Generic Bedrock renderer")
        this.implementation.registerEntityRenderer(CobblemonEntities.GENERIC_BEDROCK_ENTITY, ::GenericBedrockRenderer)
        LOGGER.info("Registering Generic Bedrock Entity renderer")
        this.implementation.registerEntityRenderer(CobblemonEntities.GENERIC_BEDROCK_ENTITY, ::GenericBedrockRenderer)
        LOGGER.info("Registering PokeRod Bobber renderer")
        this.implementation.registerEntityRenderer(CobblemonEntities.POKE_BOBBER) { ctx -> PokeBobberEntityRenderer(ctx) }
        LOGGER.info("Registering NPC renderer")
        this.implementation.registerEntityRenderer(CobblemonEntities.NPC, ::NPCRenderer)
    }

    private fun registerItemColors() {
        implementation.registerItemColors(AprijuiceItemColorProvider, *CobblemonItems.aprijuices.toTypedArray())
        implementation.registerItemColors(PokeSnackItemColorProvider, CobblemonItems.POKE_SNACK, CobblemonItems.POKE_CAKE)
        implementation.registerItemColors(PokeBaitItemColorProvider, CobblemonItems.POKE_BAIT)
        implementation.registerItemColors(PonigiriItemColorProvider, CobblemonItems.PONIGIRI)
        implementation.registerItemColors(SinisterTeaItemColorProvider, CobblemonItems.SINISTER_TEA)
    }

    fun reloadCodedAssets(resourceManager: ResourceManager) {
        LOGGER.info("Loading assets...")
        // Particles come first because animations need them.
        BedrockParticleOptionsRepository.loadEffects(resourceManager)
        // Animations come next because models need them.
        BedrockAnimationRepository.loadAnimations(
            resourceManager = resourceManager,
            directories = VaryingModelRepository.animationDirectories
        )
        VaryingModelRepository.reload(resourceManager)

        BerryModelRepository.reload(resourceManager)
        MiscModelRepository.reload(resourceManager)
        LOGGER.info("Loaded assets")
    }

    fun endBattle() {
        battle = null
        battleOverlay.lastKnownBattle = null
        BattleMusicController.endMusic()
    }

    private fun createBoatModelLayers() {
        CobblemonBoatType.entries.forEach { type ->
            this.implementation.registerLayer(
                CobblemonBoatRenderer.createBoatModelLayer(type, false),
                BoatModel::createBodyModel
            )
            this.implementation.registerLayer(
                CobblemonBoatRenderer.createBoatModelLayer(type, true),
                ChestBoatModel::createBodyModel
            )
        }
    }
}