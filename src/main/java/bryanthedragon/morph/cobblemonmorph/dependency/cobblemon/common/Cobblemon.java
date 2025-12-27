package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;
;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.CobblemonCriteria;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.EvolvePokemonContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionTrigger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.Priority;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.SeasonResolver;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.CommandDropEntry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropEntry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.ItemDropEntry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionCompleteEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers.IdentifierDataSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers.PoseTypeDataSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers.StringSetDataSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers.Vec3DataSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.permission.PermissionValidator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.calculators.CaptureCalculators;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.ShoulderEffectRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PreEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.ExperienceGroups;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.experience.StandardExperienceCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.ChoiceSpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.FlagSpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.IntSpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.helditem.HeldItemProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.EvCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Generation8EvCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.StatProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.BestSpawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.CobblemonSpawningProspector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.WorldSlice;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.AreaContextResolver;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.AreaSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.calculators.AreaSpawningContextCalculator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.prospecting.SpawningProspector;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.spawner.Spawner;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.starter.StarterHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStoreManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StoreCoordinates;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.molang.NbtMoLangDataStoreFactory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerDataStoreManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BagItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownThread;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.DialogueArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.MoveArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PartySlotArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonPropertiesArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.PokemonStoreArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument.SpawnBucketArgumentType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.CobblemonConfig;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.LastChangedVersion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.constraint.IntConstraint;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.StarterConfig;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.data.CobblemonDataProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.events.AdvancementHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.settings.ServerSettingsPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.permission.LaxPermissionValidator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.aspects.PokemonAspectsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.feature.TagSeasonResolver;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.helditem.CobblemonHeldItemManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.HiddenAbilityPropertyType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.UncatchableProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.tags.PokemonFlagProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.stat.CobblemonStatProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.starter.CobblemonStarterHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerInventoryExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.StringExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature.CobblemonPlacedFeatures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature.ore.CobblemonOrePlacedFeatures;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.NonNullList;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jetbrains.annotations.NotNull;

public Object Cobblemon {
   public const val CONFIG_PATH: String = "config/cobblemon/main.json"
   public final val LOGGER: Logger
   public const val MODID: String = "cobblemon"
   public const val VERSION: String = "1.5.2"

   public final var areaContextResolver: AreaContextResolver =
      (
         new AreaContextResolver() {
            @NotNull @Override public java.util.List<AreaSpawningContext> resolve(@NotNull Spawner spawner, @NotNull java.util.List<? extends AreaSpawningContextCalculator<?>> contextCalculators, @NotNull WorldSlice slice) {
               return AreaContextResolver.DefaultImpls.resolve(this, spawner, contextCalculators, slice);
            }
         }
      ) as AreaContextResolver

   public final val battleRegistry: BattleRegistry = BattleRegistry.INSTANCE
   public final val bestSpawner: BestSpawner = BestSpawner.INSTANCE

   public final var captureCalculator: CaptureCalculator
      public final get() {
         return this.getConfig().getCaptureCalculator();
      }

      public final set(value) {
         this.getConfig().setCaptureCalculator(value);
      }


   public final lateinit var config: CobblemonConfig
   public final val dataProvider: DataProvider = CobblemonDataProvider.INSTANCE as DataProvider
   public final var evYieldCalculator: EvCalculator = Generation8EvCalculator.INSTANCE as EvCalculator
   public final var experienceCalculator: ExperienceCalculator = StandardExperienceCalculator.INSTANCE as ExperienceCalculator
   public final lateinit var implementation: CobblemonImplementation
   public final var isDedicatedServer: Boolean
   public final var molangData: NbtMoLangDataStoreFactory = NbtMoLangDataStoreFactory.INSTANCE

   public final var permissionValidator: PermissionValidator
      public final get() {
         return permissionValidator$delegate.getValue(this, $$delegatedProperties[0]) as PermissionValidator;
      }

      public final set(<set-?>) {
         permissionValidator$delegate.setValue(this, $$delegatedProperties[0], `<set-?>`);
      }


   public final lateinit var playerData: PlayerDataStoreManager
   public final var prospector: SpawningProspector = CobblemonSpawningProspector.INSTANCE as SpawningProspector
   public final var seasonResolver: SeasonResolver = TagSeasonResolver.INSTANCE as SeasonResolver
   public final val showdownThread: ShowdownThread = new ShowdownThread()
   public final lateinit var starterConfig: StarterConfig
   public final var starterHandler: StarterHandler = (new CobblemonStarterHandler()) as StarterHandler
   public final var statProvider: StatProvider = CobblemonStatProvider.INSTANCE as StatProvider
   public final var storage: PokemonStoreManager = new PokemonStoreManager()

   public fun preInitialize(implementation: CobblemonImplementation) {
      this.setImplementation(implementation);
      LOGGER.info("Launching Cobblemon 1.5.2 ");
      implementation.registerPermissionValidator();
      implementation.registerSoundEvents();
      implementation.registerBlocks();
      implementation.registerItems();
      implementation.registerEntityTypes();
      implementation.registerEntityAttributes();
      implementation.registerBlockEntityTypes();
      implementation.registerWorldGenFeatures();
      implementation.registerParticles();
      DropEntry.Companion.register$default(DropEntry.Companion, "command", CommandDropEntry::class.java, false, 4, null);
      DropEntry.Companion.register("item", ItemDropEntry::class.java, true);
      ExperienceGroups.INSTANCE.registerDefaults();
      CaptureCalculators.INSTANCE.registerDefaults$common();
      this.loadConfig();
      CobblemonOrePlacedFeatures.INSTANCE.register();
      CobblemonPlacedFeatures.INSTANCE.register();
      this.registerArgumentTypes();
      ShoulderEffectRegistry.INSTANCE.register$common();
      Observable.DefaultImpls.subscribe$default(
         CobblemonEvents.DATA_SYNCHRONIZED,
         null,
         (
            new Function1<ServerPlayer, Unit>(this) {
               {
                  super(1);
                  this.this$0 = `$receiver`;
               }

               public final void invoke(@NotNull ServerPlayer it) {
                  Cobblemon.INSTANCE.getStorage().onPlayerDataSync(it);
                  Cobblemon.INSTANCE.getPlayerData().get(it as Player).sendToPlayer(it);
                  Cobblemon.INSTANCE.getStarterHandler().handleJoin(it);
                  new ServerSettingsPacket(this.this$0.getConfig().getPreventCompletePartyDeposit(), this.this$0.getConfig().getDisplayEntityLevelLabel()).sendToPlayer(it);
               }
            }
         ) as Function1,
         1,
         null
      );
      Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_PLAYER_LOGOUT, null, <unrepresentable>.INSTANCE, 1, null);
      Observable.DefaultImpls.subscribe$default(PlatformEvents.PLAYER_DEATH, null, <unrepresentable>.INSTANCE, 1, null);
      Observable.DefaultImpls.subscribe$default(PlatformEvents.RIGHT_CLICK_ENTITY, null, <unrepresentable>.INSTANCE, 1, null);
      Observable.DefaultImpls.subscribe$default(PlatformEvents.RIGHT_CLICK_BLOCK, null, <unrepresentable>.INSTANCE, 1, null);
      Observable.DefaultImpls.subscribe$default(PlatformEvents.RIGHT_CLICK_BLOCK, null, <unrepresentable>.INSTANCE, 1, null);
      Observable.DefaultImpls.subscribe$default(PlatformEvents.CHANGE_DIMENSION, null, <unrepresentable>.INSTANCE, 1, null);
      EntityDataSerializers.m_135050_(Vec3DataSerializer.INSTANCE);
      EntityDataSerializers.m_135050_(StringSetDataSerializer.INSTANCE);
      EntityDataSerializers.m_135050_(PoseTypeDataSerializer.INSTANCE);
      EntityDataSerializers.m_135050_(IdentifierDataSerializer.INSTANCE);
      CobblemonEvents.FRIENDSHIP_UPDATED.subscribe(Priority.LOWEST, <unrepresentable>.INSTANCE);
      HeldItemProvider.INSTANCE.register(CobblemonHeldItemManager.INSTANCE, Priority.LOWEST);
   }

   public fun initialize() {
      showdownThread.launch();
      CobblemonDataProvider.INSTANCE.registerDefaults();
      PokemonAspectsKt.getSHINY_ASPECT().register();
      PokemonAspectsKt.getGENDER_ASPECT().register();
      SpeciesFeatures.INSTANCE.getTypes().put("choice", ChoiceSpeciesFeatureProvider::class.java);
      SpeciesFeatures.INSTANCE.getTypes().put("flag", FlagSpeciesFeatureProvider::class.java);
      SpeciesFeatures.INSTANCE.getTypes().put("integer", IntSpeciesFeatureProvider::class.java);
      SpeciesFeatures.INSTANCE.register("milkable", new FlagSpeciesFeatureProvider(CollectionsKt.listOf("milkable"), true));
      SpeciesFeatures.INSTANCE.register("sheared", new FlagSpeciesFeatureProvider(CollectionsKt.listOf("sheared"), false));
      CustomPokemonProperty.Companion.register(UncatchableProperty.INSTANCE);
      CustomPokemonProperty.Companion.register(PokemonFlagProperty.INSTANCE);
      CustomPokemonProperty.Companion.register(HiddenAbilityPropertyType.INSTANCE);
      DistributionUtilsKt.ifDedicatedServer(Cobblemon::initialize$lambda$2);
      Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_TICK_POST, null, <unrepresentable>.INSTANCE, 1, null);
      Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_TICK_PRE, null, <unrepresentable>.INSTANCE, 1, null);
      Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_STARTING, null, <unrepresentable>.INSTANCE, 1, null);
      Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_STOPPED, null, <unrepresentable>.INSTANCE, 1, null);
      Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_STARTED, null, <unrepresentable>.INSTANCE, 1, null);
      Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_TICK_POST, null, <unrepresentable>.INSTANCE, 1, null);
      Observable.DefaultImpls.subscribe$default(CobblemonEvents.POKEMON_CAPTURED, null, <unrepresentable>.INSTANCE, 1, null);
      Observable.DefaultImpls.subscribe$default(CobblemonEvents.BATTLE_VICTORY, null, <unrepresentable>.INSTANCE, 1, null);
      CobblemonEvents.EVOLUTION_COMPLETE
         .subscribe(
            Priority.LOWEST,
            (
               new Function1<EvolutionCompleteEvent, Unit>(this) {
                  {
                     super(1);
                     this.this$0 = `$receiver`;
                  }

                  public final void invoke(@NotNull EvolutionCompleteEvent event) {
                     AdvancementHandler.INSTANCE.onEvolve(event);
                     val pokemon: Pokemon = event.getPokemon();
                     if (this.this$0.getConfig().getNinjaskCreatesShedinja()
                        && pokemon.getSpecies().getResourceIdentifier() == MiscUtilsKt.cobblemonResource("ninjask")
                        && PokemonSpecies.INSTANCE.getByIdentifier(Pokemon.Companion.getSHEDINJA$common()) != null) {
                        val var10000: ServerPlayer = pokemon.getOwnerPlayer();
                        if (var10000 == null) {
                           return;
                        }

                        if (var10000.m_7500_() || var10000.m_150109_().m_216874_(<unrepresentable>::invoke$lambda$0)) {
                           var var18: Any = Items.f_41852_;

                           val properties: java.lang.Iterable;
                           for (Object element$iv : properties) {
                              val it: NonNullList = `element$iv` as NonNullList;

                              val `$this$forEach$iv`: java.lang.Iterable;
                              for (Object element$ivx : $this$forEach$iv) {
                                 val itemStack: ItemStack = `element$ivx` as ItemStack;
                                 if ((`element$ivx` as ItemStack).m_41720_() is PokeBallItem && var18 == Items.f_41852_) {
                                    val var22: Item = itemStack.m_41720_();
                                    var18 = var22 as PokeBallItem;
                                 }
                              }
                           }

                           if (!var10000.m_7500_()) {
                              val var23: Inventory = var10000.m_150109_();
                              PlayerInventoryExtensionsKt.removeAmountIf(var23, 1, <unrepresentable>::invoke$lambda$3);
                           }

                           if (var18 == Items.f_41852_) {
                              var18 = CobblemonItems.POKE_BALL;
                           }

                           val var19: PokemonProperties = event.getEvolution().getResult().copy();
                           var19.setSpecies(Pokemon.Companion.getSHEDINJA$common().toString());
                           val var20: Pokemon = Pokemon.clone$default(pokemon, false, false, 3, null);
                           var20.removeHeldItem();
                           var19.apply(var20);
                           var20.setCaughtBall((var18 as PokeBallItem).getPokeBall());
                           val var24: StoreCoordinates = pokemon.getStoreCoordinates().get();
                           if (var24 != null) {
                              val var25: PokemonStore = var24.getStore();
                              if (var25 != null) {
                                 var25.add(var20);
                              }
                           }

                           val var26: SimpleCriterionTrigger = CobblemonCriteria.INSTANCE.getEVOLVE_POKEMON();
                           val var10004: PreEvolution = event.getPokemon().getPreEvolution();
                           var26.trigger(var10000, new EvolvePokemonContext(var10004.getSpecies().getResourceIdentifier(), var20.getSpecies().getResourceIdentifier(), Cobblemon.INSTANCE.getPlayerData().get(var10000 as Player).getAdvancementData().getTotalEvolvedCount()));
                        }
                     }
                  }

                  private static final boolean invoke$lambda$0(ItemStack it) {
                     return it.m_41720_() is PokeBallItem;
                  }

                  private static final boolean invoke$lambda$3(ItemStack it) {
                     return it.m_41720_() is PokeBallItem;
                  }
               }
            ) as (EvolutionCompleteEvent?) -> Unit
         );
      Observable.DefaultImpls.subscribe$default(CobblemonEvents.LEVEL_UP_EVENT, null, <unrepresentable>.INSTANCE, 1, null);
      Observable.DefaultImpls.subscribe$default(CobblemonEvents.TRADE_COMPLETED, null, <unrepresentable>.INSTANCE, 1, null);
      Observable.DefaultImpls.subscribe$default(BagItems.INSTANCE.getObservable(), null, <unrepresentable>.INSTANCE, 1, null);
   }

   public fun getLevel(dimension: ResourceKey<Level>): Level? {
      if (isDedicatedServer) {
         val var5: MinecraftServer = DistributionUtilsKt.server();
         return (if (var5 != null) var5.m_129880_(dimension) else null) as Level;
      } 
      else {
         val mc: Minecraft = Minecraft.m_91087_();
         val var10000: IntegratedServer = mc.m_91092_();
         if (var10000 != null) {
            val var3: ServerLevel = var10000.m_129880_(dimension);
            if (var3 != null) {
               return var3 as Level;
            }
         }

         return mc.f_91073_ as Level;
      }
   }

   public fun loadConfig() {
      val configFile: File = new File("config/cobblemon/main.json");
      configFile.getParentFile().mkdirs();
      if (configFile.exists()) {
         try {
            val defaultConfig: FileReader = new FileReader(configFile);
            val var10001: Any = CobblemonConfig.Companion.getGSON().fromJson(defaultConfig, CobblemonConfig.class);
            this.setConfig(var10001 as CobblemonConfig);
            defaultConfig.close();
         } 
         catch (var20: Exception) {
            LOGGER.error("Failed to load the config! Using default config until the following has been addressed:");
            this.setConfig(new CobblemonConfig());
            var20.printStackTrace();
         }

         val var21: CobblemonConfig = new CobblemonConfig();

         val `$this$forEach$iv`: java.lang.Iterable;
         for (Object element$iv : $this$forEach$iv) {
            val it: KProperty1 = `element$iv` as KProperty1;
            val var10000: Field = ReflectJvmMapping.getJavaField((`element$iv` as KProperty1) as KProperty);
            val field: Field = var10000;
            KCallablesJvm.setAccessible(it as KCallable, true);

            val `$this$forEach$ivx`: Array<Any>;
            for (Object element$ivx : $this$forEach$ivx) {
               val itx: Annotation = `element$ivx` as Annotation;
               if (`element$ivx` as Annotation is LastChangedVersion) {
                  if (StringExtensionsKt.isLaterVersion((itx as LastChangedVersion).version(), INSTANCE.getConfig().getLastSavedVersion())) {
                     field.set(INSTANCE.getConfig(), field.get(var21));
                  }
               } 
               else if (itx is IntConstraint) {
                  val var22: Any = field.get(INSTANCE.getConfig());
                  if (var22 is Int) {
                     field.set(
                        INSTANCE.getConfig(),
                        RangesKt.coerceIn((var22 as java.lang.Number).intValue(), (itx as IntConstraint).min(), (itx as IntConstraint).max())
                     );
                  }
               }
            }
         }
      } 
      else {
         this.setConfig(new CobblemonConfig());
      }

      this.getConfig().setLastSavedVersion("1.5.2");
      this.saveConfig();
      bestSpawner.loadConfig();
      Observable.DefaultImpls.subscribe$default(PokemonSpecies.INSTANCE.getObservable(), null, (new Function1<PokemonSpecies, Unit>(this) {
         {
            super(1);
            this.this$0 = `$receiver`;
         }

         public final void invoke(@NotNull PokemonSpecies it) {
            Cobblemon.INSTANCE.setStarterConfig(this.this$0.loadStarterConfig());
         }
      }) as Function1, 1, null);
   }

   public fun loadStarterConfig(): StarterConfig {
      if (this.getConfig().getExportStarterConfig()) {
         val file: File = new File("config/cobblemon/starters.json");
         file.getParentFile().mkdirs();
         if (!file.exists()) {
            val var4: StarterConfig = new StarterConfig();
            val var5: PrintWriter = new PrintWriter(file);
            StarterConfig.Companion.getGSON().toJson(var4, var5);
            var5.close();
            return var4;
         } 
         else {
            val reader: FileReader = new FileReader(file);
            val config: StarterConfig = StarterConfig.Companion.getGSON().fromJson(reader, StarterConfig.class) as StarterConfig;
            reader.close();
            return config;
         }
      } 
      else {
         return new StarterConfig();
      }
   }

   public fun saveConfig() {
      try {
         val fileWriter: FileWriter = new FileWriter(new File("config/cobblemon/main.json"));
         CobblemonConfig.Companion.getGSON().toJson(this.getConfig(), fileWriter);
         fileWriter.flush();
         fileWriter.close();
      } 
      catch (var3: Exception) {
         LOGGER.error("Failed to save the config! Please consult the following stack trace:");
         var3.printStackTrace();
      }
   }

   private fun registerArgumentTypes() {
      var var10000: CobblemonImplementation = this.getImplementation();
      var var10001: ResourceLocation = MiscUtilsKt.cobblemonResource("pokemon");
      var var10002: KClass = PokemonArgumentType::class;
      var var10003: SingletonArgumentInfo = SingletonArgumentInfo.m_235451_(PokemonArgumentType.Companion::pokemon);
      var10000.registerCommandArgument(var10001, var10002, var10003 as ArgumentTypeInfo);
      var10000 = this.getImplementation();
      var10001 = MiscUtilsKt.cobblemonResource("pokemon_properties");
      var10002 = PokemonPropertiesArgumentType::class;
      var10003 = SingletonArgumentInfo.m_235451_(PokemonPropertiesArgumentType.Companion::properties);
      var10000.registerCommandArgument(var10001, var10002, var10003 as ArgumentTypeInfo);
      var10000 = this.getImplementation();
      var10001 = MiscUtilsKt.cobblemonResource("spawn_bucket");
      var10002 = SpawnBucketArgumentType::class;
      var10003 = SingletonArgumentInfo.m_235451_(SpawnBucketArgumentType.Companion::spawnBucket);
      var10000.registerCommandArgument(var10001, var10002, var10003 as ArgumentTypeInfo);
      var10000 = this.getImplementation();
      var10001 = MiscUtilsKt.cobblemonResource("move");
      var10002 = MoveArgumentType::class;
      var10003 = SingletonArgumentInfo.m_235451_(MoveArgumentType.Companion::move);
      var10000.registerCommandArgument(var10001, var10002, var10003 as ArgumentTypeInfo);
      var10000 = this.getImplementation();
      var10001 = MiscUtilsKt.cobblemonResource("party_slot");
      var10002 = PartySlotArgumentType::class;
      var10003 = SingletonArgumentInfo.m_235451_(PartySlotArgumentType.Companion::partySlot);
      var10000.registerCommandArgument(var10001, var10002, var10003 as ArgumentTypeInfo);
      var10000 = this.getImplementation();
      var10001 = MiscUtilsKt.cobblemonResource("pokemon_store");
      var10002 = PokemonStoreArgumentType::class;
      var10003 = SingletonArgumentInfo.m_235451_(PokemonStoreArgumentType.Companion::pokemonStore);
      var10000.registerCommandArgument(var10001, var10002, var10003 as ArgumentTypeInfo);
      var10000 = this.getImplementation();
      var10001 = MiscUtilsKt.cobblemonResource("dialogue");
      var10002 = DialogueArgumentType::class;
      var10003 = SingletonArgumentInfo.m_235451_(DialogueArgumentType.Companion::dialogue);
      var10000.registerCommandArgument(var10001, var10002, var10003 as ArgumentTypeInfo);
   }

   @JvmStatic
   fun `initialize$lambda$2`() {
      isDedicatedServer = true;
   }

   @JvmStatic
   fun {
      val var10000: Logger = LogManager.getLogger();
      LOGGER = var10000;
      val var4: Delegates = Delegates.INSTANCE;
      val `initialValue$iv`: LaxPermissionValidator = new LaxPermissionValidator();
      `initialValue$iv`.initialize();
      permissionValidator$delegate = (new Cobblemon$special$$inlined$observable$1(`initialValue$iv`)) as ReadWriteProperty;
   }
}
