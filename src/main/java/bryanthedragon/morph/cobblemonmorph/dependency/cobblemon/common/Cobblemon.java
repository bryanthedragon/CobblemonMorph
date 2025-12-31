/*
 * Copyright (C) 2023 Cobblemon Contributors
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.SeasonResolver;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.CommandDropEntry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropEntry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.EvolutionItemDropEntry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.ItemDropEntry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.interaction.RequestManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangLoadedFilesCache;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ObjectValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.PermissionValidator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculators;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.ShoulderEffectRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceGroups;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.StandardExperienceCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.ChoiceSpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.helditem.HeldItemProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.StatProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ServerRealTimeTaskTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ServerTaskTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.BestSpawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.CobblemonSpawningZoneGenerator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.position.AreaSpawnablePositionResolver;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawningZoneGenerator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.starter.StarterHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.stats.CobblemonStats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStoreManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.conversions.ReforgedConversion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.database.MongoDBStoreAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile.FileStoreAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile.JSONStoreAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.adapter.flatfile.NBTStoreAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.factory.FileBackedPokemonStoreFactory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.molang.NbtMoLangDataStoreFactory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.link.PCLinkManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerInstancedDataStoreManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerInstancedDataStoreTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter.DexDataMongoBackend;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter.DexDataNbtBackend;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter.PlayerDataJsonBackend;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.adapter.PlayerDataMongoBackend;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.factory.CachedPlayerDataStoreFactory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonEntityTypeTags;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonItemTags;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PokemonBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.DispenserBehaviorRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonPack;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.arguments.*;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.CobblemonConfig;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.LastChangedVersion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.constraint.IntConstraint;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.StarterConfig;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.data.CobblemonDataProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.events.AdvancementHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.events.EntityCallbackHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.events.CallbackHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.events.PokedexHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.events.ServerTickHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.events.StatHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.settings.ServerSettingsPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.permission.LaxPermissionValidator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.BlockClickEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.feature.SlowpokeTailRegrowthSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.helditem.CobblemonHeldItemManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.AspectPropertyType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.FreezeFrameProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.HiddenAbilityPropertyType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.NoAIProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.tags.PokemonFlagProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.stat.CobblemonStatProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.starter.CobblemonStarterHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature.CobblemonPlacedFeatures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature.ore.CobblemonOrePlacedFeatures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.gamerules.CobblemonGameRules;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBuildDetails;

import net.minecraft.client.Minecraft;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.NameTagItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;


public class Cobblemon {
    const val MODID = CobblemonBuildDetails.MOD_ID;
    const val VERSION = CobblemonBuildDetails.VERSION;
    const val CONFIG_PATH = "config/$MODID/main.json";

    Logger log = Cobblemon.LOGGER;

    lateinit CobblemonImplementation  implementation;
    var CaptureCalculator captureCalculator
        get() = this.config.captureCalculator
        set(value) {
            this.config.captureCalculator = value
        }
    var experienceCalculator: ExperienceCalculator = StandardExperienceCalculator
    var evYieldCalculator: EvCalculator = Generation8EvCalculator
    var starterHandler: StarterHandler = CobblemonStarterHandler()
    var isDedicatedServer = false
    val showdownThread = ShowdownThread()
    lateinit var config: CobblemonConfig
    var spawningZoneGenerator: SpawningZoneGenerator = CobblemonSpawningZoneGenerator
    var areaSpawnablePositionResolver: AreaSpawnablePositionResolver = object : AreaSpawnablePositionResolver {}
    val bestSpawner = BestSpawner
    val battleRegistry = BattleRegistry
    var storage = PokemonStoreManager()
    var molangData = NbtMoLangDataStoreFactory
    lateinit var playerDataManager: PlayerInstancedDataStoreManager
    lateinit var starterConfig: StarterConfig
    val dataProvider: DataProvider = CobblemonDataProvider
    var permissionValidator: PermissionValidator by Delegates.observable(LaxPermissionValidator().also { it.initialize() }) { _, _, newValue -> newValue.initialize() }
    var statProvider: StatProvider = CobblemonStatProvider
    var seasonResolver: SeasonResolver = TagSeasonResolver
    var wallpapers = mutableMapOf<UUID, Set<ResourceLocation>>()

    val serverPlayerStructs = mutableMapOf<UUID, ObjectValue<Player>>()

    val statistics: CobblemonStats = CobblemonStats

    @JvmStatic
    val builtinPacks = listOf<CobblemonPack>(
        CobblemonPack(id = "adorncompatibility", name = "Adorn Compatibility", packType = PackType.CLIENT_RESOURCES, activationBehaviour = ResourcePackActivationBehaviour.ALWAYS_ENABLED, neededMods = setOf("adorn")),
        CobblemonPack(id = "gyaradosjump", name = "Gyarados Jump Patterns", packType = PackType.CLIENT_RESOURCES, activationBehaviour = ResourcePackActivationBehaviour.DEFAULT_ENABLED),
        CobblemonPack(id = "regionbiasforms", name = "Region Bias Forms", packType = PackType.CLIENT_RESOURCES, activationBehaviour = ResourcePackActivationBehaviour.DEFAULT_ENABLED),
        CobblemonPack(id = "uniqueshinyforms", name = "Shinies for Magikarp Jump", packType = PackType.CLIENT_RESOURCES, activationBehaviour = ResourcePackActivationBehaviour.NORMAL),

        CobblemonPack(id = "repurposedstructurescobblemon", name = "Repurposed Structures Cobblemon", packType = PackType.SERVER_DATA, activationBehaviour = ResourcePackActivationBehaviour.DEFAULT_ENABLED, neededMods = setOf("repurposed_structures")),
    )

    fun preInitialize(implementation: CobblemonImplementation) {
        this.implementation = implementation

        this.LOGGER.info("Launching Cobblemon ${CobblemonBuildDetails.VERSION}${if(CobblemonBuildDetails.SNAPSHOT) "-SNAPSHOT" else ""} ")
        if (CobblemonBuildDetails.SNAPSHOT) {
            this.LOGGER.info("  - Git Commit: ${smallCommitHash()} (https://gitlab.com/cable-mc/cobblemon/-/commit/${CobblemonBuildDetails.GIT_COMMIT})")
            this.LOGGER.info("  - Branch: ${CobblemonBuildDetails.BRANCH}")
        }

        implementation.registerRecipeSerializers()
        implementation.registerRecipeTypes()
        implementation.registerPermissionValidator()
        implementation.registerSoundEvents()
        implementation.registerDataComponents()
        implementation.registerBlocks()
        implementation.registerItems()
        implementation.registerEntityTypes()
        implementation.registerEntityAttributes()
        implementation.registerBlockEntityTypes()
        implementation.registerPoiTypes()
        implementation.registerVillagers()
        implementation.registerWorldGenFeatures()
        implementation.registerParticles()
        implementation.registerMenu()
        implementation.registerEntityDataSerializers()
        implementation.registerCriteria()
        implementation.registerEntitySubPredicates()
        DispenserBehaviorRegistry.registerDispenserBehaviors()

        DropEntry.register("command", CommandDropEntry.class)
        DropEntry.register("item", ItemDropEntry.class, isDefault = true)
        DropEntry.register("evolution", EvolutionItemDropEntry.class)

        ExperienceGroups.registerDefaults()
        CaptureCalculators.registerDefaults()

        this.loadConfig()
//        CobblemonBlockPredicates.touch()
        CobblemonOrePlacedFeatures.register()
        CobblemonPlacedFeatures.register()
        this.registerArgumentTypes()

        CobblemonGameRules // Init fields and register

        ShoulderEffectRegistry.register()

        DATA_SYNCHRONIZED.subscribe {
            storage.onPlayerDataSync(it)
            playerDataManager.syncAllToPlayer(it)
            starterHandler.handleJoin(it)
            it.requestWallpapers()
            sendServerSettingsPacketToPlayer(it)
        }
        PlatformEvents.SERVER_PLAYER_LOGOUT.subscribe {
            PCLinkManager.removeLink(it.player.uuid)
            BattleRegistry.onPlayerDisconnect(it.player)
            storage.onPlayerDisconnect(it.player)
            playerDataManager.onPlayerDisconnect(it.player)
            RequestManager.onLogoff(it.player)
            serverPlayerStructs.remove(it.player.uuid)
        }
        PlatformEvents.PLAYER_DEATH.subscribe {
            PCLinkManager.removeLink(it.player.uuid)
            battleRegistry.getBattleByParticipatingPlayer(it.player)?.stop()
        }

        PlatformEvents.RIGHT_CLICK_ENTITY.subscribe { event ->
            if (event.player.getItemInHand(event.hand).item is NameTagItem && event.entity.type.`is`(CobblemonEntityTypeTags.CANNOT_HAVE_NAME_TAG)) {
                event.cancel()
            }
        }
        PlatformEvents.RIGHT_CLICK_BLOCK.subscribe { event ->
            val player = event.player
            val block = player.level().getBlockState(event.pos).block
            player.party().forEach { pokemon ->
                pokemon.lockedEvolutions
                    .filterIsInstance<BlockClickEvolution>()
                    .forEach { evolution ->
                        evolution.attemptEvolution(pokemon, BlockClickEvolution.BlockInteractionContext(block, player.level()))
                    }
            }
        }

        PlatformEvents.CHANGE_DIMENSION.subscribe {
            it.player.party().forEach { pokemon -> pokemon.entity?.recallWithAnimation() }
        }

        // Lowest priority because this applies after luxury ball bonus as of gen 4
        CobblemonEvents.FRIENDSHIP_UPDATED.subscribe(Priority.LOWEST) { event ->
            var increment = (event.newFriendship - event.pokemon.friendship).toFloat()
            if (increment <= 0) //these affects are only meant to affect positive gains
                return@subscribe
            // Our Luxury ball spec is diff from official, but we will still assume these stack
            if (event.pokemon.heldItemNoCopy().`is`(CobblemonItemTags.IS_FRIENDSHIP_BOOSTER)) {
                increment += increment * 0.5F
            }
            event.newFriendship = event.pokemon.friendship + increment.roundToInt()
        }

        HeldItemProvider.register(CobblemonHeldItemManager, Priority.LOWEST)
    }

    fun sendServerSettingsPacketToPlayer(ServerPlayer player) {
        ServerSettingsPacket(
            this.config.preventCompletePartyDeposit,
            this.config.displayEntityLevelLabel,
            this.config.displayEntityNameLabel,
            this.config.maxPokemonLevel,
            this.config.maxPokemonFriendship,
            this.config.maxDynamaxLevel,
        ).sendToPlayer(player)
    }

    fun initialize() {
        showdownThread.launch()

        // Start up the data provider.
        CobblemonDataProvider.registerDefaults()

        SHINY_ASPECT.register()
        GENDER_ASPECT.register()
        COSMETIC_SLOT_ASPECT.register()
        CHARACTERISTIC_RAINBOW_ASPECT.register()

        SpeciesFeatures.types["choice"] = ChoiceSpeciesFeatureProvider.class
        SpeciesFeatures.types["flag"] = FlagSpeciesFeatureProvider.class
        SpeciesFeatures.types["integer"] = IntSpeciesFeatureProvider.class

        SpeciesFeatures.register(
            DataKeys.HAS_BEEN_SHEARED,
            FlagSpeciesFeatureProvider(keys = listOf(DataKeys.HAS_BEEN_SHEARED), default = false)
        )

        SpeciesFeatures.register(
            SlowpokeTailRegrowthSpeciesFeature.NAME,
            SlowpokeTailRegrowthSpeciesFeatureProvider
        )

        CustomPokemonProperty.register(UncatchableProperty)
        CustomPokemonProperty.register(BattleCloneProperty)
        CustomPokemonProperty.register(PokemonFlagProperty)
        CustomPokemonProperty.register(HiddenAbilityPropertyType)
        CustomPokemonProperty.register(AspectPropertyType)
        CustomPokemonProperty.register(UnaspectPropertyType)
        CustomPokemonProperty.register(FreezeFrameProperty)
        CustomPokemonProperty.register(NoAIProperty)

        CobblemonEvents.POKEMON_PROPERTY_INITIALISED.emit(Unit)

        CallbackHandler.setup()
        EntityCallbackHandler.setup()

        ifDedicatedServer {
            isDedicatedServer = true
        }

        PlatformEvents.SERVER_TICK_POST.subscribe {
            ServerTaskTracker.update(1/20F)
            ServerRealTimeTaskTracker.update()
        }
        PlatformEvents.SERVER_TICK_PRE.subscribe {
            ServerRealTimeTaskTracker.update()
        }
        PlatformEvents.SERVER_STARTING.subscribe { event ->
            val server = event.server
            MoLangLoadedFilesCache.initialize(server)
            playerDataManager = PlayerInstancedDataStoreManager().also { it.setup(server) }

            val mongoClient: MongoClient?

            val pokemonStoreRoot = server.getWorldPath(LevelResource.ROOT).resolve("pokemon").toFile()
            val storeAdapter = when (config.storageFormat) {
                "nbt", "json" -> {
                    val generalJsonFactory = CachedPlayerDataStoreFactory(PlayerDataJsonBackend())
                    generalJsonFactory.setup(server)

                    val pokedexNbtFactory = CachedPlayerDataStoreFactory(DexDataNbtBackend())
                    pokedexNbtFactory.setup(server)

                    playerDataManager.setFactory(generalJsonFactory, PlayerInstancedDataStoreTypes.GENERAL)
                    playerDataManager.setFactory(pokedexNbtFactory, PlayerInstancedDataStoreTypes.POKEDEX)

                    if (config.storageFormat == "nbt") {
                        NBTStoreAdapter(pokemonStoreRoot.absolutePath, useNestedFolders = true, folderPerClass = true)
                    } else {
                        JSONStoreAdapter(
                            pokemonStoreRoot.absolutePath,
                            useNestedFolders = true,
                            folderPerClass = true
                        )
                    }
                }

                "mongodb" -> {
                    try {
                        Class.forName("com.mongodb.client.MongoClient")

                        val mongoClientSettings = MongoClientSettings.builder()
                            .applyConnectionString(ConnectionString(config.mongoDBConnectionString))
                            .build()
                        mongoClient = MongoClients.create(mongoClientSettings)
                        val generalMongoFactory = CachedPlayerDataStoreFactory(PlayerDataMongoBackend(mongoClient, config.mongoDBDatabaseName, "PlayerDataCollection"))
                        generalMongoFactory.setup(server)

                        val pokedexMongoFactory = CachedPlayerDataStoreFactory(DexDataMongoBackend(mongoClient, config.mongoDBDatabaseName, "PokeDexCollection"))
                        pokedexMongoFactory.setup(server)

                        playerDataManager.setFactory(generalMongoFactory, PlayerInstancedDataStoreTypes.GENERAL)
                        playerDataManager.setFactory(pokedexMongoFactory, PlayerInstancedDataStoreTypes.POKEDEX)
                        MongoDBStoreAdapter(mongoClient, config.mongoDBDatabaseName)
                    } catch (e: ClassNotFoundException) {
                        LOGGER.error("MongoDB driver not found.")
                        throw e
                    }

                }


                else -> throw IllegalArgumentException("Unsupported storageFormat: ${config.storageFormat}")
            }
                .with(ReforgedConversion(server.getWorldPath(LevelResource.ROOT))) as FileStoreAdapter<*>

            storage.registerFactory(
                priority = Priority.LOWEST,
                factory = FileBackedPokemonStoreFactory(
                    adapter = storeAdapter,
                    createIfMissing = true,
                    pcConstructor = { uuid -> PCStore(uuid).also { it.resize(config.defaultBoxCount) } }
                )
            )
        }

        PlatformEvents.SERVER_STOPPED.subscribe {
            storage.unregisterAll(it.server.registryAccess())
            playerDataManager.saveAllStores()
            playerDataManager.saveExecutor.shutdown()
            playerDataManager.saveExecutor.awaitTermination(30L, TimeUnit.SECONDS)
        }
        PlatformEvents.SERVER_STARTED.subscribe { event ->
            bestSpawner.onServerStarted(event.server)
            battleRegistry.onServerStarted()
        }
        PlatformEvents.SERVER_TICK_POST.subscribe { ServerTickHandler.onTick(it.server) }

        BagItems.observable.subscribe {
            LOGGER.info("Starting dummy Showdown battle to force it to pre-load data.")
            battleRegistry.startBattle(
                BattleFormat.GEN_9_SINGLES,
                BattleSide(PokemonBattleActor(UUID.randomUUID(), BattlePokemon(Pokemon().initialize()), -1F)),
                BattleSide(PokemonBattleActor(UUID.randomUUID(), BattlePokemon(Pokemon().initialize()), -1F)),
                false
            ).ifSuccessful {
                it.mute = true
            }.ifErrored {
                val errors = it.errors.joinToString("\n") { it.javaClass.name }
                LOGGER.error("Failed to start dummy Showdown battle: $errors")
            }
        }

        registerEventHandlers()

        CobblemonEvents.COBBLEMON_INITIALISED.emit(Unit)

    }

    fun registerEventHandlers() {
        AdvancementHandler.registerListeners()
        PokedexHandler.registerListeners()
        StatHandler.registerListeners()
    }

    fun getLevel(dimension: ResourceKey<Level>): Level? {
        return if (isDedicatedServer) {
            server()?.getLevel(dimension)
        } else {
            val mc = Minecraft.getInstance()
            return mc.singleplayerServer?.getLevel(dimension) ?: mc.level
        }
    }

    private fun initializeConfig() {
        loadCobblemonConfig()
        saveConfig(this.config)
        PokemonSpecies.observable.subscribe { starterConfig = this.loadStarterConfig() }
    }

    fun loadConfig() {
        initializeConfig()
        bestSpawner.init()
    }

    fun reloadConfig() {
        initializeConfig()
        bestSpawner.reloadConfig()
    }

    private fun loadCobblemonConfig() {
        val configFile = File(CONFIG_PATH)
        configFile.parentFile.mkdirs()

        // Check config existence and load if it exists, otherwise create default.
        if (configFile.exists()) {
            try {
                val fileReader = FileReader(configFile)
                this.config = CobblemonConfig.GSON.fromJson(fileReader, CobblemonConfig.class)
                fileReader.close()
            } catch (Exception exception) {
                LOGGER.error("Failed to load the config! Using default config until the following has been addressed:")
                this.config = CobblemonConfig()
                exception.printStackTrace()
            }

            val defaultConfig = CobblemonConfig()

            CobblemonConfig::class.memberProperties.forEach {
                val field = it.javaField!!
                it.isAccessible = true
                field.annotations.forEach {
                    when (it) {
                        is LastChangedVersion -> {
                            val defaultChangedVersion = it.version
                            val lastSavedVersion = config.lastSavedVersion
                            if (defaultChangedVersion.isLaterVersion(lastSavedVersion)) {
                                field.set(config, field.get(defaultConfig))
                            }
                        }
                        is IntConstraint -> {
                            var value = field.get(config)
                            if (value is Int) {
                                value = value.coerceIn(it.min, it.max)
                                field.set(config, value)
                            }
                        }
                    }
                }
            }
        } else {
            this.config = CobblemonConfig()
        }
    }

    fun loadStarterConfig(): StarterConfig {
        if (config.exportStarterConfig) {
            val file = File("config/cobblemon/starters.json")
            file.parentFile.mkdirs()
            if (!file.exists()) {
                val config = StarterConfig()
                val pw = PrintWriter(file)
                StarterConfig.GSON.toJson(config, pw)
                pw.close()
                return config
            }
            val reader = FileReader(file)
            val config = StarterConfig.GSON.fromJson(reader, StarterConfig.class)
            reader.close()
            return config
        } else {
            return StarterConfig()
        }
    }

    fun saveConfig(config: CobblemonConfig) {
        config.lastSavedVersion = VERSION

        try {
            val configFile = File(CONFIG_PATH)
            val fileWriter = FileWriter(configFile)
            // Put the config to json then flush the writer to commence writing.
            CobblemonConfig.GSON.toJson(config, fileWriter)
            fileWriter.flush()
            fileWriter.close()
        } catch (Exception exception) {
            LOGGER.error("Failed to save the config! Please consult the following stack trace:")
            exception.printStackTrace()
        }
    }

    private fun registerArgumentTypes() {
        this.implementation.registerCommandArgument(cobblemonResource("pokemon"), SpeciesArgumentType::class, SingletonArgumentInfo.contextFree(SpeciesArgumentType::species))
        this.implementation.registerCommandArgument(cobblemonResource("pokemon_properties"), PokemonPropertiesArgumentType::class, SingletonArgumentInfo.contextFree(PokemonPropertiesArgumentType::properties))
        this.implementation.registerCommandArgument(cobblemonResource("spawn_bucket"), SpawnBucketArgumentType::class, SingletonArgumentInfo.contextFree(SpawnBucketArgumentType::spawnBucket))
        this.implementation.registerCommandArgument(cobblemonResource("move"), MoveArgumentType::class, SingletonArgumentInfo.contextFree(MoveArgumentType::move))
        this.implementation.registerCommandArgument(cobblemonResource("party_slot"), PartySlotArgumentType::class, SingletonArgumentInfo.contextFree(PartySlotArgumentType::partySlot))
        this.implementation.registerCommandArgument(cobblemonResource("pokemon_store"), PokemonStoreArgumentType::class, SingletonArgumentInfo.contextFree(PokemonStoreArgumentType::pokemonStore))
        this.implementation.registerCommandArgument(cobblemonResource("dialogue"), DialogueArgumentType::class, SingletonArgumentInfo.contextFree(DialogueArgumentType::dialogue))
        this.implementation.registerCommandArgument(cobblemonResource("form"), FormArgumentType::class, SingletonArgumentInfo.contextFree(FormArgumentType::form))
        this.implementation.registerCommandArgument(cobblemonResource("dex"), DexArgumentType::class, SingletonArgumentInfo.contextFree (DexArgumentType::dex))
        this.implementation.registerCommandArgument(cobblemonResource("npc_class"), NPCClassArgumentType::class, SingletonArgumentInfo.contextFree(NPCClassArgumentType::npcClass))
        this.implementation.registerCommandArgument(cobblemonResource("wallpaper"), UnlockablePCBoxWallpaperArgumentType::class, SingletonArgumentInfo.contextFree(UnlockablePCBoxWallpaperArgumentType::wallpaper))
        this.implementation.registerCommandArgument(cobblemonResource("mark"), MarkArgumentType::class, SingletonArgumentInfo.contextFree(MarkArgumentType::mark))
        this.implementation.registerCommandArgument(cobblemonResource("transform_type"), TransformTypeArgumentType::class, SingletonArgumentInfo.contextFree(TransformTypeArgumentType::transformType))
        this.implementation.registerCommandArgument(cobblemonResource("model_part"), ModelPartArgumentType::class, SingletonArgumentInfo.contextFree(ModelPartArgumentType::modelPart))
    }

}
