package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonClientImplementation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonEntities
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berries
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ClientTaskTracker
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.PartyOverlay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleOverlay
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.BedrockParticleEffectRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.BerryBlockRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.DisplayCaseRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.FossilAnalyzerRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.GildedChestBlockRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.HealingMachineRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.RestorationTankRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.boat.CobblemonBoatRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.generic.GenericBedrockRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item.CobblemonBuiltinItemRendererRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item.PokemonItemRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.PokemonOnShoulderRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockAnimationRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.BerryModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.BlockEntityModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.FossilModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.GenericBedrockEntityModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.MiscModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokeBallModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokeball.PokeBallRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokemon.PokemonRenderer
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.battle.BattleMusicController
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.starter.ClientPlayerData
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientStorageManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade.ClientTrade
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.data.CobblemonDataProvider
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonBoatType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ItemTooltipEvent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import kotlin.jvm.internal.SpreadBuilder
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.model.BoatModel
import net.minecraft.client.model.ChestBoatModel
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.HangingSignRenderer
import net.minecraft.client.renderer.blockentity.SignRenderer
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.LivingEntityRenderer
import net.minecraft.client.renderer.entity.RenderLayerParent
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context
import net.minecraft.locale.Language
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceKey
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DoorBlock
import net.minecraft.world.level.block.FlowerPotBlock
import net.minecraft.world.level.block.TrapDoorBlock
import net.minecraft.world.level.block.entity.BlockEntityType
import org.jetbrains.annotations.NotNull

@SourceDebugExtension(["SMAP\nCobblemonClient.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonClient.kt\ncom/cobblemon/mod/common/client/CobblemonClient\n+ 2 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,310:1\n37#2,2:311\n13579#3,2:313\n*S KotlinDebug\n*F\n+ 1 CobblemonClient.kt\ncom/cobblemon/mod/common/client/CobblemonClient\n*L\n208#1:311,2\n303#1:313,2\n*E\n"])
public object CobblemonClient {
   public final var battle: ClientBattle?

   public final val battleOverlay: BattleOverlay by LazyKt.lazy(<unrepresentable>.INSTANCE)
      public final get() {
         return battleOverlay$delegate.getValue() as BattleOverlay;
      }


   public final var checkedStarterScreen: Boolean
   public final var clientPlayerData: ClientPlayerData = new ClientPlayerData(false, false, false, null, 15, null)
   public final lateinit var implementation: CobblemonClientImplementation

   public final val overlay: PartyOverlay by LazyKt.lazy(<unrepresentable>.INSTANCE)
      public final get() {
         return overlay$delegate.getValue() as PartyOverlay;
      }


   public final var requests: ClientPlayerActionRequests = new ClientPlayerActionRequests()
   public final val storage: ClientStorageManager = new ClientStorageManager()
   public final var trade: ClientTrade?

   public fun onLogin() {
      clientPlayerData = new ClientPlayerData(false, false, false, null, 15, null);
      requests = new ClientPlayerActionRequests();
      storage.onLogin();
      CobblemonDataProvider.INSTANCE.setCanReload$common(false);
   }

   public fun onLogout() {
      storage.onLogout();
      battle = null;
      this.getBattleOverlay().onLogout();
      ClientTaskTracker.INSTANCE.clear();
      checkedStarterScreen = false;
      CobblemonDataProvider.INSTANCE.setCanReload$common(true);
   }

   public fun initialize(implementation: CobblemonClientImplementation) {
      Cobblemon.INSTANCE.getLOGGER().info("Initializing Cobblemon client");
      this.setImplementation(implementation);
      Observable.DefaultImpls.subscribe$default(PlatformEvents.CLIENT_PLAYER_LOGIN, null, <unrepresentable>.INSTANCE, 1, null);
      Observable.DefaultImpls.subscribe$default(PlatformEvents.CLIENT_PLAYER_LOGOUT, null, <unrepresentable>.INSTANCE, 1, null);
      this.registerBlockEntityRenderers();
      this.registerBlockRenderTypes();
      this.registerFlywheelRenderers();
      this.registerEntityRenderers();
      Observable.DefaultImpls.subscribe$default(Berries.INSTANCE.getObservable(), null, <unrepresentable>.INSTANCE, 1, null);
      Cobblemon.INSTANCE.getLOGGER().info("Registering custom BuiltinItemRenderers");
      CobblemonBuiltinItemRendererRegistry.INSTANCE.register(CobblemonItems.POKEMON_MODEL, new PokemonItemRenderer());
      Observable.DefaultImpls.subscribe$default(
         PlatformEvents.CLIENT_ITEM_TOOLTIP,
         null,
         (
            new Function1<ItemTooltipEvent, Unit>(this) {
               {
                  super(1);
                  this.this$0 = `$receiver`;
               }

               public final void invoke(@NotNull ItemTooltipEvent event) {
                  val stack: ItemStack = event.getStack();
                  val lines: java.util.List = event.getLines();
                  if (stack.m_41720_().m_204114_().m_203543_().isPresent()
                     && (stack.m_41720_().m_204114_().m_203543_().get() as ResourceKey).m_135782_().m_135827_() == "cobblemon") {
                     val var10000: CompoundTag = stack.m_41783_();
                     if (var10000 != null && var10000.m_128471_("HideTooltip")) {
                        return;
                     }

                     val language: Language = Language.m_128107_();
                     val key: java.lang.String = CobblemonClient.access$baseLangKeyForItem(this.this$0, stack);
                     val offset: Int = if (lines.size() > 1) 1 else 0;
                     if (language.m_6722_(key)) {
                        val var10001: Int = lines.size() - offset;
                        val var10002: MutableComponent = MiscUtilsKt.asTranslated(key);
                        lines.add(var10001, TextKt.gray(var10002));
                     }

                     var i: Int = 1;

                     for (java.lang.String listKey = key + "_" + 1; language.m_6722_(listKey); listKey = key + "_" + ++i) {
                        val var9: Int = lines.size() - offset;
                        val var10: MutableComponent = MiscUtilsKt.asTranslated(listKey);
                        lines.add(var9, TextKt.gray(var10));
                     }
                  }
               }
            }
         ) as Function1,
         1,
         null
      );
   }

   public fun registerFlywheelRenderers() {
   }

   private fun registerBlockRenderTypes() {
      var var10000: CobblemonClientImplementation = this.getImplementation();
      var var10001: RenderType = RenderType.m_110457_();
      var10000.registerBlockRenderType(var10001, CobblemonBlocks.APRICORN_LEAVES);
      var10000 = this.getImplementation();
      var10001 = RenderType.m_110463_();
      val var5: SpreadBuilder = new SpreadBuilder(56);
      var5.add(CobblemonBlocks.GILDED_CHEST);
      var5.add(CobblemonBlocks.FOSSIL_ANALYZER);
      val var10003: DoorBlock = CobblemonBlocks.APRICORN_DOOR;
      var5.add(var10003);
      val var8: TrapDoorBlock = CobblemonBlocks.APRICORN_TRAPDOOR;
      var5.add(var8);
      var5.add(CobblemonBlocks.APRICORN_SIGN);
      var5.add(CobblemonBlocks.APRICORN_WALL_SIGN);
      var5.add(CobblemonBlocks.APRICORN_HANGING_SIGN);
      var5.add(CobblemonBlocks.APRICORN_WALL_HANGING_SIGN);
      var5.add(CobblemonBlocks.BLACK_APRICORN_SAPLING);
      var5.add(CobblemonBlocks.BLUE_APRICORN_SAPLING);
      var5.add(CobblemonBlocks.GREEN_APRICORN_SAPLING);
      var5.add(CobblemonBlocks.PINK_APRICORN_SAPLING);
      var5.add(CobblemonBlocks.RED_APRICORN_SAPLING);
      var5.add(CobblemonBlocks.WHITE_APRICORN_SAPLING);
      var5.add(CobblemonBlocks.YELLOW_APRICORN_SAPLING);
      var5.add(CobblemonBlocks.BLACK_APRICORN);
      var5.add(CobblemonBlocks.BLUE_APRICORN);
      var5.add(CobblemonBlocks.GREEN_APRICORN);
      var5.add(CobblemonBlocks.PINK_APRICORN);
      var5.add(CobblemonBlocks.RED_APRICORN);
      var5.add(CobblemonBlocks.WHITE_APRICORN);
      var5.add(CobblemonBlocks.YELLOW_APRICORN);
      var5.add(CobblemonBlocks.HEALING_MACHINE);
      var5.add(CobblemonBlocks.MEDICINAL_LEEK);
      var5.add(CobblemonBlocks.HEALING_MACHINE);
      var5.add(CobblemonBlocks.INSTANCE.getRED_MINT());
      var5.add(CobblemonBlocks.BLUE_MINT);
      var5.add(CobblemonBlocks.CYAN_MINT);
      var5.add(CobblemonBlocks.PINK_MINT);
      var5.add(CobblemonBlocks.GREEN_MINT);
      var5.add(CobblemonBlocks.WHITE_MINT);
      var5.add(CobblemonBlocks.PASTURE);
      var5.add(CobblemonBlocks.ENERGY_ROOT);
      var5.add(CobblemonBlocks.BIG_ROOT);
      var5.add(CobblemonBlocks.REVIVAL_HERB);
      var5.add(CobblemonBlocks.VIVICHOKE_SEEDS);
      var5.add(CobblemonBlocks.PEP_UP_FLOWER);
      val var9: FlowerPotBlock = CobblemonBlocks.POTTED_PEP_UP_FLOWER;
      var5.add(var9);
      var5.add(CobblemonBlocks.REVIVAL_HERB);
      var5.addSpread(CobblemonBlocks.INSTANCE.berries().values().toArray(new BerryBlock[0]));
      val var10: FlowerPotBlock = CobblemonBlocks.POTTED_PEP_UP_FLOWER;
      var5.add(var10);
      var5.add(CobblemonBlocks.RESTORATION_TANK);
      var5.add(CobblemonBlocks.SMALL_BUDDING_TUMBLESTONE);
      var5.add(CobblemonBlocks.MEDIUM_BUDDING_TUMBLESTONE);
      var5.add(CobblemonBlocks.LARGE_BUDDING_TUMBLESTONE);
      var5.add(CobblemonBlocks.TUMBLESTONE_CLUSTER);
      var5.add(CobblemonBlocks.SMALL_BUDDING_BLACK_TUMBLESTONE);
      var5.add(CobblemonBlocks.MEDIUM_BUDDING_BLACK_TUMBLESTONE);
      var5.add(CobblemonBlocks.LARGE_BUDDING_BLACK_TUMBLESTONE);
      var5.add(CobblemonBlocks.BLACK_TUMBLESTONE_CLUSTER);
      var5.add(CobblemonBlocks.SMALL_BUDDING_SKY_TUMBLESTONE);
      var5.add(CobblemonBlocks.MEDIUM_BUDDING_SKY_TUMBLESTONE);
      var5.add(CobblemonBlocks.LARGE_BUDDING_SKY_TUMBLESTONE);
      var5.add(CobblemonBlocks.SKY_TUMBLESTONE_CLUSTER);
      var5.add(CobblemonBlocks.GIMMIGHOUL_CHEST);
      var5.add(CobblemonBlocks.DISPLAY_CASE);
      var10000.registerBlockRenderType(var10001, var5.toArray(new Block[var5.size()]) as Array<Block>);
      this.createBoatModelLayers();
   }

   public fun beforeChatRender(context: GuiGraphics, partialDeltaTicks: Float) {
      if (battle == null) {
         this.getOverlay().m_280421_(context, partialDeltaTicks);
      } else {
         this.getBattleOverlay().m_280421_(context, partialDeltaTicks);
      }
   }

   public fun onAddLayer(skinMap: Map<String, EntityRenderer<out Player>>?) {
      val var10000: EntityRenderer = if (skinMap != null) skinMap.get("default") as EntityRenderer else null;
      (var10000 as LivingEntityRenderer).m_115326_(new PokemonOnShoulderRenderer((var10000 as LivingEntityRenderer) as RenderLayerParent));
      val var3: LivingEntityRenderer = skinMap.get("slim") as LivingEntityRenderer;
      if (var3 != null) {
         var3.m_115326_(new PokemonOnShoulderRenderer(var3 as RenderLayerParent));
      }
   }

   private fun registerBlockEntityRenderers() {
      this.getImplementation().registerBlockEntityRenderer(CobblemonBlockEntities.HEALING_MACHINE, HealingMachineRenderer::new);
      val var10000: CobblemonClientImplementation = this.getImplementation();
      val var10001: BlockEntityType = CobblemonBlockEntities.BERRY;
      var10000.registerBlockEntityRenderer(var10001, BerryBlockRenderer::new);
      this.getImplementation().registerBlockEntityRenderer(CobblemonBlockEntities.SIGN, SignRenderer::new);
      this.getImplementation().registerBlockEntityRenderer(CobblemonBlockEntities.HANGING_SIGN, HangingSignRenderer::new);
      this.getImplementation().registerBlockEntityRenderer(CobblemonBlockEntities.FOSSIL_ANALYZER, FossilAnalyzerRenderer::new);
      this.getImplementation().registerBlockEntityRenderer(CobblemonBlockEntities.RESTORATION_TANK, RestorationTankRenderer::new);
      this.getImplementation().registerBlockEntityRenderer(CobblemonBlockEntities.GILDED_CHEST, GildedChestBlockRenderer::new);
      this.getImplementation().registerBlockEntityRenderer(CobblemonBlockEntities.DISPLAY_CASE, DisplayCaseRenderer::new);
   }

   private fun registerEntityRenderers() {
      Cobblemon.INSTANCE.getLOGGER().info("Registering Pokémon renderer");
      this.getImplementation().registerEntityRenderer(CobblemonEntities.POKEMON, PokemonRenderer::new);
      Cobblemon.INSTANCE.getLOGGER().info("Registering PokéBall renderer");
      this.getImplementation().registerEntityRenderer(CobblemonEntities.EMPTY_POKEBALL, PokeBallRenderer::new);
      Cobblemon.INSTANCE.getLOGGER().info("Registering Boat renderer");
      this.getImplementation().registerEntityRenderer(CobblemonEntities.BOAT, CobblemonClient::registerEntityRenderers$lambda$0);
      Cobblemon.INSTANCE.getLOGGER().info("Registering Boat with Chest renderer");
      this.getImplementation().registerEntityRenderer(CobblemonEntities.CHEST_BOAT, CobblemonClient::registerEntityRenderers$lambda$1);
      Cobblemon.INSTANCE.getLOGGER().info("Registering Generic Bedrock Entity renderer");
      this.getImplementation().registerEntityRenderer(CobblemonEntities.GENERIC_BEDROCK_ENTITY, GenericBedrockRenderer::new);
   }

   public fun reloadCodedAssets(resourceManager: ResourceManager) {
      Cobblemon.INSTANCE.getLOGGER().info("Loading assets...");
      BedrockParticleEffectRepository.INSTANCE.loadEffects(resourceManager);
      BedrockAnimationRepository.INSTANCE
         .loadAnimations(
            resourceManager,
            CollectionsKt.plus(
               CollectionsKt.plus(
                  CollectionsKt.plus(
                     CollectionsKt.plus(PokemonModelRepository.INSTANCE.getAnimationDirectories(), PokeBallModelRepository.INSTANCE.getAnimationDirectories()),
                     FossilModelRepository.INSTANCE.getAnimationDirectories()
                  ),
                  BlockEntityModelRepository.INSTANCE.getAnimationDirectories()
               ),
               GenericBedrockEntityModelRepository.INSTANCE.getAnimationDirectories()
            )
         );
      PokemonModelRepository.INSTANCE.reload(resourceManager);
      PokeBallModelRepository.INSTANCE.reload(resourceManager);
      BerryModelRepository.INSTANCE.reload(resourceManager);
      FossilModelRepository.INSTANCE.reload(resourceManager);
      BlockEntityModelRepository.INSTANCE.reload(resourceManager);
      GenericBedrockEntityModelRepository.INSTANCE.reload(resourceManager);
      MiscModelRepository.INSTANCE.reload(resourceManager);
      Cobblemon.INSTANCE.getLOGGER().info("Loaded assets");
   }

   public fun endBattle() {
      battle = null;
      this.getBattleOverlay().setLastKnownBattle(null);
      BattleMusicController.INSTANCE.endMusic();
   }

   private fun baseLangKeyForItem(stack: ItemStack): String {
      if (stack.m_41720_() is PokeBallItem) {
         val var10000: Item = stack.m_41720_();
         return "item.${(var10000 as PokeBallItem).getPokeBall().getName().m_135827_()}.${(var10000 as PokeBallItem).getPokeBall().getName().m_135815_()}.tooltip";
      } else {
         return "${stack.m_41778_()}.tooltip";
      }
   }

   private fun createBoatModelLayers() {
      val `$this$forEach$iv`: Any;
      for (Object element$iv : $this$forEach$iv) {
         this.getImplementation()
            .registerLayer(CobblemonBoatRenderer.Companion.createBoatModelLayer$common((CobblemonBoatType)`element$iv`, false), BoatModel::m_246613_);
         this.getImplementation()
            .registerLayer(CobblemonBoatRenderer.Companion.createBoatModelLayer$common((CobblemonBoatType)`element$iv`, true), ChestBoatModel::m_247175_);
      }
   }

   @JvmStatic
   fun `registerEntityRenderers$lambda$0`(ctx: Context): EntityRenderer {
      return new CobblemonBoatRenderer(ctx, false);
   }

   @JvmStatic
   fun `registerEntityRenderers$lambda$1`(ctx: Context): EntityRenderer {
      return new CobblemonBoatRenderer(ctx, true);
   }
}
