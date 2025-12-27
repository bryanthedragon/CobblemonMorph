package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonCommands
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonEntities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonImplementation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonTradeOffers
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Environment
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.ModAPI
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.NetworkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.ResourcePackActivationBehaviour
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group.CobblemonItemGroups
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.loot.LootInjector
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.particle.CobblemonParticles
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.sherds.CobblemonSherds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.IdentifierExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.CobblemonStructures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature.CobblemonFeatures
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.placementmodifier.CobblemonPlacementModifierTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.predicate.CobblemonBlockPredicates
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.structureprocessors.CobblemonProcessorTypes
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.structureprocessors.CobblemonStructureProcessorListOverrides
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.brewing.CobblemonForgeBrewingRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.client.CobblemonForgeClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.net.CobblemonForgeNetworkManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.permission.ForgePermissionValidator
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.worldgen.CobblemonBiomeModifiers
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.ArgumentType
import java.io.BufferedReader
import java.io.Closeable
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.nio.file.Path
import java.util.ArrayList;
import java.util.HashMap
import java.util.HashSet
import java.util.UUID
import java.util.Map.Entry
import java.util.concurrent.ExecutionException
import java.util.function.Consumer
import kotlin.jvm.functions.Function0
import kotlin.jvm.functions.Function1
import kotlin.jvm.functions.Function2
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.reflect.KClass
import net.minecraft.advancements.CriteriaTriggers
import net.minecraft.advancements.CriterionTrigger
import net.minecraft.command.argument.serialize.ArgumentSerializer.ArgumentTypeProperties
import net.minecraft.commands.CommandBuildContext
import net.minecraft.commands.Commands.CommandSelection
import net.minecraft.commands.synchronization.ArgumentTypeInfo
import net.minecraft.commands.synchronization.ArgumentTypeInfos
import net.minecraft.core.particles.ParticleType
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer
import net.minecraft.server.packs.PackResources
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.repository.Pack
import net.minecraft.server.packs.resources.PreparableReloadListener
import net.minecraft.server.packs.resources.Resource
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.sounds.SoundEvent
import net.minecraft.tags.TagKey
import net.minecraft.world.GameRules.Category
import net.minecraft.world.GameRules.Key
import net.minecraft.world.GameRules.Rule
import net.minecraft.world.GameRules.Type
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.ai.attributes.Attribute
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.schedule.Activity
import net.minecraft.world.gen.GenerationStep.Feature
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.GameRules
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.biome.Biome
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.ComposterBlock
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.levelgen.placement.PlacedFeature
import net.minecraft.world.level.storage.loot.LootPool.Builder
import net.minecraftforge.common.ForgeMod
import net.minecraftforge.common.ToolActions
import net.minecraftforge.event.AddPackFindersEvent
import net.minecraftforge.event.AddReloadListenerEvent
import net.minecraftforge.event.LootTableLoadEvent
import net.minecraftforge.event.OnDatapackSyncEvent
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.event.entity.EntityAttributeCreationEvent
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent
import net.minecraftforge.event.level.BlockEvent.BlockToolModificationEvent
import net.minecraftforge.event.server.ServerAboutToStartEvent
import net.minecraftforge.event.village.VillagerTradesEvent
import net.minecraftforge.event.village.WandererTradesEvent
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.InterModComms
import net.minecraftforge.fml.ModList
import net.minecraftforge.fml.DistExecutor.SafeRunnable
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import net.minecraftforge.fml.loading.FMLEnvironment
import net.minecraftforge.forgespi.locating.IModFile
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.IForgeRegistry
import net.minecraftforge.registries.RegisterEvent
import net.minecraftforge.registries.RegisterEvent.RegisterHelper
import net.minecraftforge.resource.PathPackResources
import net.minecraftforge.server.ServerLifecycleHooks
import org.jetbrains.annotations.NotNull
import thedarkcolour.kotlinforforge.KotlinModLoadingContext

@Mod("cobblemon")
@SourceDebugExtension(["SMAP\nCobblemonForge.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonForge.kt\ncom/cobblemon/mod/forge/CobblemonForge\n+ 2 Forge.kt\nthedarkcolour/kotlinforforge/forge/ForgeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,400:1\n39#2:401\n39#2:402\n39#2:403\n39#2:404\n39#2:405\n39#2:406\n39#2:407\n39#2:408\n39#2:409\n1855#3,2:410\n1855#3,2:414\n1855#3,2:416\n1855#3,2:418\n1855#3,2:420\n1855#3,2:422\n215#4,2:412\n*S KotlinDebug\n*F\n+ 1 CobblemonForge.kt\ncom/cobblemon/mod/forge/CobblemonForge\n*L\n97#1:401\n194#1:402\n202#1:403\n210#1:404\n226#1:405\n249#1:406\n257#1:407\n268#1:408\n276#1:409\n309#1:410,2\n356#1:414,2\n374#1:416,2\n381#1:418,2\n142#1:420,2\n165#1:422,2\n317#1:412,2\n*E\n"])
public class CobblemonForge : CobblemonImplementation {
   private final val commandArgumentTypes: DeferredRegister<ArgumentTypeInfo<*, *>> = DeferredRegister.create(Registries.f_256982_, "cobblemon")
   private final val hasBeenSynced: HashSet<UUID> = new HashSet()
   public open val modAPI: ModAPI = ModAPI.FORGE
   public open val networkManager: NetworkManager = CobblemonForgeNetworkManager.INSTANCE as NetworkManager
   private final val queuedBuiltinResourcePacks: ArrayList<Triple<ResourceLocation, Component, ResourcePackActivationBehaviour>> = new ArrayList()
   private final val queuedWork: ArrayList<() -> Unit> = new ArrayList()
   private final val reloadableResources: ArrayList<PreparableReloadListener> = new ArrayList()

   public fun addCobblemonStructures(event: ServerAboutToStartEvent) {
      val var10000: CobblemonStructures = CobblemonStructures.INSTANCE;
      var var10001: MinecraftServer = event.getServer();
      var10000.registerJigsaws(var10001);
      val var2: CobblemonStructureProcessorListOverrides = CobblemonStructureProcessorListOverrides.INSTANCE;
      var10001 = event.getServer();
      var2.register(var10001);
   }

   public fun wakeUp(event: PlayerWakeUpEvent) {
      val var3: Player = event.getEntity();
      val var10000: ServerPlayer = var3 as? ServerPlayer;
      if ((var3 as? ServerPlayer) != null) {
         PlayerExtensionsKt.didSleep(var10000);
      }
   }

   public fun serverInit(event: FMLDedicatedServerSetupEvent) {
   }

   public fun initialize(event: FMLCommonSetupEvent) {
      Cobblemon.INSTANCE.getLOGGER().info("Initializing...");
      this.getNetworkManager().registerClientBound();
      this.getNetworkManager().registerServerBound();
      event.enqueueWork(CobblemonForge::initialize$lambda$4);
      Cobblemon.INSTANCE.initialize();
   }

   public fun on(event: RegisterEvent) {
      event.register(Registries.f_256774_, CobblemonForge::on$lambda$5);
      event.register(Registries.f_256843_, CobblemonForge::on$lambda$6);
      event.register(Registries.f_271200_, CobblemonForge::on$lambda$7);
      event.register(Registries.f_256983_, CobblemonForge::on$lambda$8);
      event.register(Registries.f_257025_, CobblemonForge::on$lambda$10);
   }

   public fun onDataPackSync(event: OnDatapackSyncEvent) {
      val var10000: DataProvider = Cobblemon.INSTANCE.getDataProvider();
      val var10001: ServerPlayer = event.getPlayer();
      if (var10001 != null) {
         var10000.sync(var10001);
      }
   }

   public fun onLogin(event: PlayerLoggedInEvent) {
      this.hasBeenSynced.add(event.getEntity().m_20148_());
   }

   public fun onLogout(event: PlayerLoggedOutEvent) {
      this.hasBeenSynced.remove(event.getEntity().m_20148_());
   }

   public override fun isModInstalled(id: String): Boolean {
      return ModList.get().isLoaded(id);
   }

   public override fun environment(): Environment {
      return if (FMLEnvironment.dist.isClient()) Environment.CLIENT else Environment.SERVER;
   }

   public override fun registerPermissionValidator() {
      Cobblemon.INSTANCE.setPermissionValidator(ForgePermissionValidator.INSTANCE);
   }

   public override fun registerSoundEvents() {
      KotlinModLoadingContext.Companion.get().getKEventBus().addListener(CobblemonForge::registerSoundEvents$lambda$12);
   }

   public override fun registerBlocks() {
      KotlinModLoadingContext.Companion.get().getKEventBus().addListener(CobblemonForge::registerBlocks$lambda$14);
   }

   public override fun registerParticles() {
      KotlinModLoadingContext.Companion.get().getKEventBus().addListener(CobblemonForge::registerParticles$lambda$16);
   }

   private fun handleBlockStripping(e: BlockToolModificationEvent) {
      if (e.getToolAction() == ToolActions.AXE_STRIP) {
         val var10000: Block = CobblemonBlocks.INSTANCE.strippedBlocks().get(e.getState().m_60734_());
         if (var10000 == null) {
            return;
         }

         e.setFinalState(var10000.m_152465_(e.getState()));
      }
   }

   public override fun registerItems() {
      val `$this$registerItems_u24lambda_u2421`: IEventBus = KotlinModLoadingContext.Companion.get().getKEventBus();
      `$this$registerItems_u24lambda_u2421`.addListener(CobblemonForge::registerItems$lambda$21$lambda$18);
      `$this$registerItems_u24lambda_u2421`.addListener(CobblemonForge::registerItems$lambda$21$lambda$20);
   }

   public override fun registerEntityTypes() {
      KotlinModLoadingContext.Companion.get().getKEventBus().addListener(CobblemonForge::registerEntityTypes$lambda$23);
   }

   public override fun registerEntityAttributes() {
      KotlinModLoadingContext.Companion.get().getKEventBus().addListener(CobblemonForge::registerEntityAttributes$lambda$24);
   }

   public override fun registerBlockEntityTypes() {
      KotlinModLoadingContext.Companion.get().getKEventBus().addListener(CobblemonForge::registerBlockEntityTypes$lambda$26);
   }

   public override fun registerWorldGenFeatures() {
      KotlinModLoadingContext.Companion.get().getKEventBus().addListener(CobblemonForge::registerWorldGenFeatures$lambda$28);
   }

   public override fun addFeatureToWorldGen(feature: ResourceKey<PlacedFeature>, step: Feature, validTag: TagKey<Biome>?) {
      CobblemonBiomeModifiers.INSTANCE.add(feature, step, validTag);
   }

   public override fun <A : ArgumentType<*>, T : ArgumentTypeProperties<Any>> registerCommandArgument(
      identifier: ResourceLocation,
      argumentClass: KClass<Any>,
      serializer: ArgumentTypeInfo<Any, Any>
   ) {
      this.commandArgumentTypes.register(identifier.m_135815_(), CobblemonForge::registerCommandArgument$lambda$29);
   }

   private fun registerCommands(e: RegisterCommandsEvent) {
      val var10000: CobblemonCommands = CobblemonCommands.INSTANCE;
      val var10001: CommandDispatcher = e.getDispatcher();
      val var10002: CommandBuildContext = e.getBuildContext();
      val var10003: CommandSelection = e.getCommandSelection();
      var10000.register(var10001, var10002, var10003);
   }

   public override fun <T : Rule<Any>> registerGameRule(name: String, category: Category, type: Type<Any>): Key<Any> {
      val var10000: net.minecraft.world.level.GameRules.Key = GameRules.m_46189_(name, category, type);
      return var10000;
   }

   public override fun <T : CriterionTrigger<*>> registerCriteria(criteria: Any): Any {
      val var10000: CriterionTrigger = CriteriaTriggers.m_10595_(criteria);
      return (T)var10000;
   }

   public override fun registerResourceReloader(
      identifier: ResourceLocation,
      reloader: PreparableReloadListener,
      type: PackType,
      dependencies: Collection<ResourceLocation>
   ) {
      if (type === PackType.SERVER_DATA) {
         this.reloadableResources.add(reloader);
      } else {
         CobblemonForgeClient.INSTANCE.registerResourceReloader$forge(reloader);
      }
   }

   private fun onReload(e: AddReloadListenerEvent) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         e.addListener(`element$iv` as PreparableReloadListener);
      }
   }

   public override fun server(): MinecraftServer? {
      return ServerLifecycleHooks.getCurrentServer();
   }

   public override fun <T> reloadJsonRegistry(registry: JsonDataRegistry<Any>, manager: ResourceManager): HashMap<ResourceLocation, Any> {
      label115: {
         val data: HashMap = new HashMap();
         val var10000: java.util.Map = manager.m_214159_(registry.getResourcePath(), CobblemonForge::reloadJsonRegistry$lambda$30);

         for (Entry element$iv : var10000.entrySet()) {
            val identifier: ResourceLocation = `element$iv`.getKey() as ResourceLocation;
            val resource: Resource = `element$iv`.getValue() as Resource;
            if (!(identifier.m_135827_() == "pixelmon")) {
               val var12: Closeable = resource.m_215507_();
               var var13: java.lang.Throwable = null;

               try {
                  try {
                     val stream: InputStream = var12 as InputStream;
                     val reader: Reader = new InputStreamReader(stream, Charsets.UTF_8);
                     val var16: Closeable = if (reader is BufferedReader) reader as BufferedReader else new BufferedReader(reader, 8192);
                     var var37: java.lang.Throwable = null;

                     try {
                        try {
                           val var38: BufferedReader = var16 as BufferedReader;
                           val resolvedIdentifier: ResourceLocation = new ResourceLocation(
                              identifier.m_135827_(), FilesKt.getNameWithoutExtension(new File(identifier.m_135815_()))
                           );

                           try {
                              data.put(resolvedIdentifier, registry.getGson().fromJson(var38, registry.getTypeToken().getType()));
                           } catch (var22: Exception) {
                              throw new ExecutionException("Error loading JSON for data: $identifier", var22);
                           }
                        } catch (var23: java.lang.Throwable) {
                           var37 = var23;
                           throw var23;
                        }
                     } catch (var24: java.lang.Throwable) {
                        CloseableKt.closeFinally(var16, var37);
                     }

                     CloseableKt.closeFinally(var16, null);
                  } catch (var25: java.lang.Throwable) {
                     var13 = var25;
                     throw var25;
                  }
               } catch (var26: java.lang.Throwable) {
                  CloseableKt.closeFinally(var12, var13);
               }

               CloseableKt.closeFinally(var12, null);
            }
         }

         return data;
      }
   }

   public override fun registerCompostable(item: ItemLike, chance: Float) {
      this.queuedWork.add(new Function0<Unit>(item, chance) {
         {
            super(0);
            this.$item = `$item`;
            this.$chance = `$chance`;
         }

         public final void invoke() {
            ComposterBlock.f_51914_.put(this.$item, this.$chance);
         }
      });
   }

   public override fun registerBuiltinResourcePack(id: ResourceLocation, title: Component, activationBehaviour: ResourcePackActivationBehaviour) {
      this.queuedBuiltinResourcePacks.add(new Triple(id, title, activationBehaviour));
   }

   public fun onAddPackFindersEvent(event: AddPackFindersEvent) {
      if (event.getPackType() === PackType.CLIENT_RESOURCES) {
         if (this.isModInstalled("adorn")) {
            val var10001: ResourceLocation = MiscUtilsKt.cobblemonResource("adorncompatibility");
            val var10002: MutableComponent = Component.m_237113_("Adorn Compatibility");
            this.registerBuiltinResourcePack(var10001, var10002 as Component, ResourcePackActivationBehaviour.ALWAYS_ENABLED);
         }

         val modFile: IModFile = ModList.get().getModFileById("cobblemon").getFile();

         val `$this$forEach$iv`: java.lang.Iterable;
         for (Object element$iv : $this$forEach$iv) {
            val id: ResourceLocation = (`element$iv` as Triple).component1() as ResourceLocation;
            event.addRepositorySource(CobblemonForge::onAddPackFindersEvent$lambda$36$lambda$35);
         }
      }
   }

   private fun onVillagerTradesRegistry(e: VillagerTradesEvent) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val tradeOffer: CobblemonTradeOffers.VillagerTradeOffer = `element$iv` as CobblemonTradeOffers.VillagerTradeOffer;
         val var8: java.util.List = e.getTrades().get((`element$iv` as CobblemonTradeOffers.VillagerTradeOffer).getRequiredLevel()) as java.util.List;
         if (var8 != null) {
            var8.addAll(tradeOffer.getTradeOffers());
         }
      }
   }

   private fun onWanderingTraderRegistry(e: WandererTradesEvent) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val tradeOffer: CobblemonTradeOffers.WandererTradeOffer = `element$iv` as CobblemonTradeOffers.WandererTradeOffer;
         if ((`element$iv` as CobblemonTradeOffers.WandererTradeOffer).isRareTrade()) {
            e.getRareTrades().addAll(tradeOffer.getTradeOffers());
         } else {
            e.getGenericTrades().addAll(tradeOffer.getTradeOffers());
         }
      }
   }

   private fun onLootTableLoad(e: LootTableLoadEvent) {
      val var10000: LootInjector = LootInjector.INSTANCE;
      val var10001: ResourceLocation = e.getName();
      var10000.attemptInjection(var10001, (new Function1<Builder, Unit>(e) {
         {
            super(1);
            this.$e = `$e`;
         }

         public final void invoke(@NotNull Builder builder) {
            this.$e.getTable().addPool(builder.m_79082_());
         }
      }) as (Builder?) -> Unit);
   }

   private fun attemptModCompat() {
      if (this.isModInstalled("carryon")) {
         InterModComms.sendTo("carryon", "blacklistEntity", CobblemonForge::attemptModCompat$lambda$39);
         InterModComms.sendTo("carryon", "blacklistEntity", CobblemonForge::attemptModCompat$lambda$40);
      }
   }

   @JvmStatic
   fun `_init_$lambda$2`(): SafeRunnable {
      return CobblemonForgeClient.INSTANCE::init;
   }

   @JvmStatic
   fun `initialize$lambda$4`(`this$0`: CobblemonForge) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as Function0).invoke();
      }

      CobblemonForgeBrewingRegistry.INSTANCE.register();
   }

   @JvmStatic
   fun `on$lambda$5`(it: RegisterHelper) {
      CobblemonBlockPredicates.INSTANCE.touch();
   }

   @JvmStatic
   fun `on$lambda$6`(it: RegisterHelper) {
      CobblemonPlacementModifierTypes.INSTANCE.touch();
   }

   @JvmStatic
   fun `on$lambda$7`(it: RegisterHelper) {
      CobblemonSherds.INSTANCE.registerSherds();
   }

   @JvmStatic
   fun `on$lambda$8`(it: RegisterHelper) {
      CobblemonProcessorTypes.INSTANCE.touch();
   }

   @JvmStatic
   fun `on$lambda$10`(it: RegisterHelper) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         val itx: Activity = `element$iv` as Activity;
         val var10000: IForgeRegistry = ForgeRegistries.ACTIVITIES;
         val var10001: java.lang.String = itx.m_37998_();
         var10000.register(MiscUtilsKt.cobblemonResource(var10001), itx);
      }
   }

   @JvmStatic
   fun `registerSoundEvents$lambda$12$lambda$11`(helper: RegisterHelper) {
      CobblemonSounds.INSTANCE.register((new Function2<ResourceLocation, SoundEvent, Unit>(helper) {
         {
            super(2);
            this.$helper = `$helper`;
         }

         public final void invoke(@NotNull ResourceLocation identifier, @NotNull SoundEvent sounds) {
            this.$helper.register(identifier, sounds);
         }
      }) as (ResourceLocation?, SoundEvent?) -> Unit);
   }

   @JvmStatic
   fun `registerSoundEvents$lambda$12`(event: RegisterEvent) {
      event.register(CobblemonSounds.INSTANCE.getRegistryKey(), CobblemonForge::registerSoundEvents$lambda$12$lambda$11);
   }

   @JvmStatic
   fun `registerBlocks$lambda$14$lambda$13`(helper: RegisterHelper) {
      CobblemonBlocks.INSTANCE.register((new Function2<ResourceLocation, Block, Unit>(helper) {
         {
            super(2);
            this.$helper = `$helper`;
         }

         public final void invoke(@NotNull ResourceLocation identifier, @NotNull Block block) {
            this.$helper.register(identifier, block);
         }
      }) as (ResourceLocation?, Block?) -> Unit);
   }

   @JvmStatic
   fun `registerBlocks$lambda$14`(event: RegisterEvent) {
      event.register(CobblemonBlocks.INSTANCE.getRegistryKey(), CobblemonForge::registerBlocks$lambda$14$lambda$13);
   }

   @JvmStatic
   fun `registerParticles$lambda$16$lambda$15`(helper: RegisterHelper) {
      CobblemonParticles.INSTANCE.register((new Function2<ResourceLocation, ParticleType<?>, Unit>(helper) {
         {
            super(2);
            this.$helper = `$helper`;
         }

         public final void invoke(@NotNull ResourceLocation identifier, @NotNull ParticleType<?> particleType) {
            this.$helper.register(identifier, particleType);
         }
      }) as (ResourceLocation?, ParticleType<?>?) -> Unit);
   }

   @JvmStatic
   fun `registerParticles$lambda$16`(event: RegisterEvent) {
      event.register(CobblemonParticles.INSTANCE.getRegistryKey(), CobblemonForge::registerParticles$lambda$16$lambda$15);
   }

   @JvmStatic
   fun `registerItems$lambda$21$lambda$18$lambda$17`(helper: RegisterHelper) {
      CobblemonItems.INSTANCE.register((new Function2<ResourceLocation, Item, Unit>(helper) {
         {
            super(2);
            this.$helper = `$helper`;
         }

         public final void invoke(@NotNull ResourceLocation identifier, @NotNull Item item) {
            this.$helper.register(identifier, item);
         }
      }) as (ResourceLocation?, Item?) -> Unit);
   }

   @JvmStatic
   fun `registerItems$lambda$21$lambda$18`(event: RegisterEvent) {
      event.register(CobblemonItems.INSTANCE.getRegistryKey(), CobblemonForge::registerItems$lambda$21$lambda$18$lambda$17);
   }

   @JvmStatic
   fun `registerItems$lambda$21$lambda$20$lambda$19`(helper: RegisterHelper) {
      CobblemonItemGroups.INSTANCE
         .register(
            (
               new Function1<CobblemonItemGroups.ItemGroupHolder, CreativeModeTab>(helper) {
                  {
                     super(1);
                     this.$helper = `$helper`;
                  }

                  @NotNull
                  public final CreativeModeTab invoke(@NotNull CobblemonItemGroups.ItemGroupHolder holder) {
                     val itemGroup: CreativeModeTab = CreativeModeTab.builder()
                        .m_257941_(holder.getDisplayName())
                        .m_257737_(<unrepresentable>::invoke$lambda$0)
                        .m_257501_(holder.getEntryCollector())
                        .m_257652_();
                     this.$helper.register(holder.getKey(), itemGroup);
                     return itemGroup;
                  }

                  private static final ItemStack invoke$lambda$0(Function0 $tmp0) {
                     return `$tmp0`.invoke() as ItemStack;
                  }
               }
            ) as (CobblemonItemGroups.ItemGroupHolder?) -> CreativeModeTab
         );
   }

   @JvmStatic
   fun `registerItems$lambda$21$lambda$20`(event: RegisterEvent) {
      event.register(Registries.f_279569_, CobblemonForge::registerItems$lambda$21$lambda$20$lambda$19);
   }

   @JvmStatic
   fun `registerEntityTypes$lambda$23$lambda$22`(helper: RegisterHelper) {
      CobblemonEntities.INSTANCE.register((new Function2<ResourceLocation, EntityType<?>, Unit>(helper) {
         {
            super(2);
            this.$helper = `$helper`;
         }

         public final void invoke(@NotNull ResourceLocation identifier, @NotNull EntityType<?> type) {
            this.$helper.register(identifier, type);
         }
      }) as (ResourceLocation?, EntityType<?>?) -> Unit);
   }

   @JvmStatic
   fun `registerEntityTypes$lambda$23`(event: RegisterEvent) {
      event.register(CobblemonEntities.INSTANCE.getRegistryKey(), CobblemonForge::registerEntityTypes$lambda$23$lambda$22);
   }

   @JvmStatic
   fun `registerEntityAttributes$lambda$24`(event: EntityAttributeCreationEvent) {
      CobblemonEntities.INSTANCE
         .registerAttributes(
            (
               new Function2<EntityType<? extends LivingEntity>, net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder, Unit>(event) {
                  {
                     super(2);
                     this.$event = `$event`;
                  }

                  public final void invoke(
                     @NotNull EntityType<? extends LivingEntity> entityType,
                     @NotNull net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder builder
                  ) {
                     builder.m_22266_(ForgeMod.ENTITY_GRAVITY.get() as Attribute)
                        .m_22266_(ForgeMod.NAMETAG_DISTANCE.get() as Attribute)
                        .m_22266_(ForgeMod.SWIM_SPEED.get() as Attribute);
                     this.$event.put(entityType, builder.m_22265_());
                  }
               }
            ) as (EntityType<? extends LivingEntity>?, net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder?) -> Unit
         );
   }

   @JvmStatic
   fun `registerBlockEntityTypes$lambda$26$lambda$25`(helper: RegisterHelper) {
      CobblemonBlockEntities.INSTANCE.register((new Function2<ResourceLocation, BlockEntityType<?>, Unit>(helper) {
         {
            super(2);
            this.$helper = `$helper`;
         }

         public final void invoke(@NotNull ResourceLocation identifier, @NotNull BlockEntityType<?> type) {
            this.$helper.register(identifier, type);
         }
      }) as (ResourceLocation?, BlockEntityType<?>?) -> Unit);
   }

   @JvmStatic
   fun `registerBlockEntityTypes$lambda$26`(event: RegisterEvent) {
      event.register(CobblemonBlockEntities.INSTANCE.getRegistryKey(), CobblemonForge::registerBlockEntityTypes$lambda$26$lambda$25);
   }

   @JvmStatic
   fun `registerWorldGenFeatures$lambda$28$lambda$27`(helper: RegisterHelper) {
      CobblemonFeatures.INSTANCE.register((new Function2<ResourceLocation, net.minecraft.world.level.levelgen.feature.Feature<?>, Unit>(helper) {
         {
            super(2);
            this.$helper = `$helper`;
         }

         public final void invoke(@NotNull ResourceLocation identifier, @NotNull net.minecraft.world.level.levelgen.feature.Feature<?> feature) {
            this.$helper.register(identifier, feature);
         }
      }) as (ResourceLocation?, net.minecraft.world.level.levelgen.featureFeature<?>?) -> Unit);
   }

   @JvmStatic
   fun `registerWorldGenFeatures$lambda$28`(event: RegisterEvent) {
      event.register(CobblemonFeatures.INSTANCE.getRegistryKey(), CobblemonForge::registerWorldGenFeatures$lambda$28$lambda$27);
   }

   @JvmStatic
   fun `registerCommandArgument$lambda$29`(`$argumentClass`: KClass, `$serializer`: ArgumentTypeInfo): ArgumentTypeInfo {
      return ArgumentTypeInfos.registerByClass(JvmClassMappingKt.getJavaClass(`$argumentClass`), `$serializer`);
   }

   @JvmStatic
   fun `reloadJsonRegistry$lambda$30`(path: ResourceLocation): Boolean {
      return IdentifierExtensionsKt.endsWith(path, ".json");
   }

   @JvmStatic
   fun `onAddPackFindersEvent$lambda$36$lambda$34`(`$path`: Path, name: java.lang.String): PackResources {
      return (new PathPackResources(name, true, `$path`)) as PackResources;
   }

   @JvmStatic
   fun `onAddPackFindersEvent$lambda$36$lambda$35`(`$profile`: Pack, consumer: Consumer) {
      consumer.accept(`$profile`);
   }

   @JvmStatic
   fun `attemptModCompat$lambda$39`(): Any {
      return CobblemonEntities.POKEMON_KEY.toString();
   }

   @JvmStatic
   fun `attemptModCompat$lambda$40`(): Any {
      return CobblemonEntities.EMPTY_POKEBALL_KEY.toString();
   }
}
