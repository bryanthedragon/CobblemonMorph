package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.client

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonClientImplementation
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonKeyBinds
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.shader.CobblemonShaders
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.compat.LambDynamicLightsCompat
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group.CobblemonItemGroups
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.particle.CobblemonParticles
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.particle.SnowstormParticleType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import java.util.ArrayList;
import java.util.Arrays
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import java.util.function.Supplier
import kotlin.jvm.functions.Function1
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import net.minecraft.client.color.block.BlockColor
import net.minecraft.client.color.item.ItemColor
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.SpriteSet
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.ShaderInstance
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.EntityRenderers
import net.minecraft.client.resources.TextureAtlasHolder
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.item.ItemGroup.StackVisibility
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.PreparableReloadListener
import net.minecraft.server.packs.resources.ReloadableResourceManager
import net.minecraft.server.packs.resources.ResourceManager
import net.minecraft.server.packs.resources.PreparableReloadListener.PreparationBarrier
import net.minecraft.util.profiling.ProfilerFiller
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.CreativeModeTab.TabVisibility
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.client.ForgeHooksClient
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent
import net.minecraftforge.client.event.RegisterKeyMappingsEvent
import net.minecraftforge.client.event.RegisterParticleProvidersEvent
import net.minecraftforge.client.event.RegisterShadersEvent
import net.minecraftforge.client.event.ModelEvent.RegisterAdditional
import net.minecraftforge.client.event.RenderGuiOverlayEvent.Pre
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.util.MutableHashedLinkedMap
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.kotlinforforge.KotlinModLoadingContext

@SourceDebugExtension(["SMAP\nCobblemonForgeClient.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonForgeClient.kt\ncom/cobblemon/mod/forge/client/CobblemonForgeClient\n+ 2 Forge.kt\nthedarkcolour/kotlinforforge/forge/ForgeKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 5 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,224:1\n39#2:225\n13309#3,2:226\n1855#4,2:228\n1855#4,2:230\n37#5,2:232\n*S KotlinDebug\n*F\n+ 1 CobblemonForgeClient.kt\ncom/cobblemon/mod/forge/client/CobblemonForgeClient\n*L\n72#1:225\n135#1:226,2\n159#1:228,2\n95#1:230,2\n107#1:232,2\n*E\n"])
public object CobblemonForgeClient : CobblemonClientImplementation {
   public final var lastUpdateTime: Long?

   public fun init() {
      val `$this$init_u24lambda_u240`: IEventBus = KotlinModLoadingContext.Companion.get().getKEventBus();
      `$this$init_u24lambda_u240`.addListener(INSTANCE::onClientSetup);
      `$this$init_u24lambda_u240`.addListener(INSTANCE::onKeyMappingRegister);
      `$this$init_u24lambda_u240`.addListener(INSTANCE::onRegisterParticleProviders);
      `$this$init_u24lambda_u240`.addListener(INSTANCE::register3dPokeballModels);
      `$this$init_u24lambda_u240`.addListener(INSTANCE::onBuildContents);
      `$this$init_u24lambda_u240`.addListener(INSTANCE::onRegisterReloadListener);
      `$this$init_u24lambda_u240`.addListener(INSTANCE::onShaderRegistration);
      MinecraftForge.EVENT_BUS.addListener(this::onRenderGuiOverlayEvent);
   }

   private fun onClientSetup(event: FMLClientSetupEvent) {
      event.enqueueWork(CobblemonForgeClient::onClientSetup$lambda$1);
      ForgeClientPlatformEventHandler.INSTANCE.register();
   }

   private fun onRegisterReloadListener(event: RegisterClientReloadListenersEvent) {
      event.registerReloadListener(CobblemonForgeClient::onRegisterReloadListener$lambda$4);
   }

   private fun onShaderRegistration(event: RegisterShadersEvent) {
      event.registerShader(
         new ShaderInstance(event.getResourceProvider(), MiscUtilsKt.cobblemonResource("particle_add"), DefaultVertexFormat.f_85820_),
         CobblemonForgeClient::onShaderRegistration$lambda$5
      );
      event.registerShader(
         new ShaderInstance(event.getResourceProvider(), MiscUtilsKt.cobblemonResource("particle_cutout"), DefaultVertexFormat.f_85820_),
         CobblemonForgeClient::onShaderRegistration$lambda$6
      );
   }

   public override fun registerLayer(modelLayer: ModelLayerLocation, supplier: Supplier<LayerDefinition>) {
      ForgeHooksClient.registerLayerDefinition(modelLayer, supplier);
   }

   public override fun <T : ParticleOptions> registerParticleFactory(type: ParticleType<Any>, factory: (SpriteSet) -> ParticleProvider<Any>) {
      throw new UnsupportedOperationException("Forge can't store these early, use CobblemonForgeClient#onRegisterParticleProviders");
   }

   public override fun registerBlockRenderType(layer: RenderType, vararg blocks: Block) {
      for (Object element$iv : blocks) {
         ItemBlockRenderTypes.setRenderLayer((Block)`element$iv`, layer);
      }
   }

   public override fun registerItemColors(provider: ItemColor, vararg items: Item) {
      Minecraft.m_91087_().f_91041_.m_92689_(provider, Arrays.copyOf(items, items.length));
   }

   public override fun registerBlockColors(provider: BlockColor, vararg blocks: Block) {
      Minecraft.m_91087_().m_91298_().m_92589_(provider, Arrays.copyOf(blocks, blocks.length));
   }

   public override fun <T : BlockEntity> registerBlockEntityRenderer(type: BlockEntityType<out Any>, factory: BlockEntityRendererProvider<Any>) {
      BlockEntityRenderers.m_173590_(type, factory);
   }

   public override fun <T : Entity> registerEntityRenderer(type: EntityType<out Any>, factory: EntityRendererProvider<Any>) {
      EntityRenderers.m_174036_(type, factory);
   }

   private fun register3dPokeballModels(event: RegisterAdditional) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         event.register((new ModelResourceLocation((`element$iv` as PokeBall).getModel3d(), "inventory")) as ResourceLocation);
      }
   }

   private fun onKeyMappingRegister(event: RegisterKeyMappingsEvent) {
      CobblemonKeyBinds.INSTANCE.register((new Function1<KeyMapping, Unit>(event) {
         {
            super(1, receiver, RegisterKeyMappingsEvent::class.java, "register", "register(Lnet/minecraft/client/option/KeyBinding;)V", 0);
         }

         public final void invoke(KeyMapping p0) {
            (this.receiver as RegisterKeyMappingsEvent).register(p0);
         }
      }) as (KeyMapping?) -> Unit);
   }

   private fun onRegisterParticleProviders(event: RegisterParticleProvidersEvent) {
      event.registerSpriteSet(CobblemonParticles.INSTANCE.getSNOWSTORM_PARTICLE_TYPE(), SnowstormParticleType.Factory::new);
   }

   private fun onRenderGuiOverlayEvent(event: Pre) {
      if (event.getOverlay().id() == VanillaGuiOverlay.CHAT_PANEL.id()) {
         val lastUpdateTime: java.lang.Long = lastUpdateTime;
         if (lastUpdateTime != null) {
            val var10000: CobblemonClient = CobblemonClient.INSTANCE;
            val var10001: GuiGraphics = event.getGuiGraphics();
            var10000.beforeChatRender(var10001, (float)(System.currentTimeMillis() - lastUpdateTime) / 1000.0F * 20.0F);
         }

         lastUpdateTime = System.currentTimeMillis();
      }
   }

   internal fun registerResourceReloader(reloader: PreparableReloadListener) {
      val var10000: ResourceManager = Minecraft.m_91087_().m_91098_();
      (var10000 as ReloadableResourceManager).m_7217_(reloader);
   }

   private fun onBuildContents(e: BuildCreativeModeTabContentsEvent) {
      val var10002: MutableHashedLinkedMap = e.getEntries();
      val forgeInject: CobblemonForgeClient.ForgeItemGroupInject = new CobblemonForgeClient.ForgeItemGroupInject(var10002);
      val var10000: CobblemonItemGroups = CobblemonItemGroups.INSTANCE;
      val var10001: ResourceKey = e.getTabKey();
      var10000.inject(var10001, forgeInject);
   }

   private fun attemptModCompat() {
      if (Cobblemon.INSTANCE.getImplementation().isModInstalled("dynamiclightsreforged")) {
         LambDynamicLightsCompat.hookCompat();
         Cobblemon.INSTANCE.getLOGGER().info("Dynamic Lights Reforged compatibility enabled");
      }
   }

   @JvmStatic
   fun `onClientSetup$lambda$1`(`this$0`: CobblemonForgeClient) {
      CobblemonClient.INSTANCE.initialize(`this$0`);
      `this$0`.attemptModCompat();
   }

   @JvmStatic
   fun `onRegisterReloadListener$lambda$4$lambda$3`(`$manager`: ResourceManager) {
      val var10000: CobblemonClient = CobblemonClient.INSTANCE;
      var10000.reloadCodedAssets(`$manager`);
   }

   @JvmStatic
   fun `onRegisterReloadListener$lambda$4`(
      synchronizer: PreparationBarrier,
      manager: ResourceManager,
      prepareProfiler: ProfilerFiller,
      applyProfiler: ProfilerFiller,
      prepareExecutor: Executor,
      applyExecutor: Executor
   ): CompletableFuture {
      val atlasFutures: java.util.List = new ArrayList();

      val result: java.lang.Iterable;
      for (Object element$iv : result) {
         val var10001: CompletableFuture = (`$i$f$toTypedArray` as TextureAtlasHolder)
            .m_5540_(synchronizer, manager, prepareProfiler, applyProfiler, prepareExecutor, applyExecutor);
         atlasFutures.add(var10001);
      }

      val var14: Array<CompletableFuture> = atlasFutures.toArray(new CompletableFuture[0]);
      return CompletableFuture.allOf(Arrays.copyOf(var14, var14.length)).thenRun(CobblemonForgeClient::onRegisterReloadListener$lambda$4$lambda$3);
   }

   @JvmStatic
   fun `onShaderRegistration$lambda$5`(it: ShaderInstance) {
      val var10000: CobblemonShaders = CobblemonShaders.INSTANCE;
      var10000.setPARTICLE_BLEND(it);
   }

   @JvmStatic
   fun `onShaderRegistration$lambda$6`(it: ShaderInstance) {
      val var10000: CobblemonShaders = CobblemonShaders.INSTANCE;
      var10000.setPARTICLE_CUTOUT(it);
   }

   private class ForgeItemGroupInject(entries: MutableHashedLinkedMap<ItemStack, StackVisibility>) : CobblemonItemGroups.Injector {
      private final val entries: MutableHashedLinkedMap<ItemStack, StackVisibility>

      init {
         this.entries = entries;
      }

      public override fun putFirst(item: ItemLike) {
         this.entries.putFirst(new ItemStack(item), TabVisibility.PARENT_AND_SEARCH_TABS);
      }

      public override fun putBefore(item: ItemLike, target: ItemLike) {
         this.entries.putBefore(new ItemStack(target), new ItemStack(item), TabVisibility.PARENT_AND_SEARCH_TABS);
      }

      public override fun putAfter(item: ItemLike, target: ItemLike) {
         this.entries.putAfter(new ItemStack(target), new ItemStack(item), TabVisibility.PARENT_AND_SEARCH_TABS);
      }

      public override fun putLast(item: ItemLike) {
         this.entries.put(new ItemStack(item), TabVisibility.PARENT_AND_SEARCH_TABS);
      }
   }
}
