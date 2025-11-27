/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.DefaultVertexFormat
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.color.block.BlockColor
 *  net.minecraft.client.color.item.ItemColor
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.model.geom.ModelLayerLocation
 *  net.minecraft.client.model.geom.builders.LayerDefinition
 *  net.minecraft.client.particle.ParticleProvider
 *  net.minecraft.client.particle.SpriteSet
 *  net.minecraft.client.renderer.ItemBlockRenderTypes
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.ShaderInstance
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderers
 *  net.minecraft.client.renderer.entity.EntityRendererProvider
 *  net.minecraft.client.renderer.entity.EntityRenderers
 *  net.minecraft.client.resources.TextureAtlasHolder
 *  net.minecraft.client.resources.model.ModelResourceLocation
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.PreparableReloadListener
 *  net.minecraft.server.packs.resources.PreparableReloadListener$PreparationBarrier
 *  net.minecraft.server.packs.resources.ReloadableResourceManager
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.util.profiling.ProfilerFiller
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.item.CreativeModeTab
 *  net.minecraft.world.item.CreativeModeTab$TabVisibility
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraftforge.client.ForgeHooksClient
 *  net.minecraftforge.client.event.ModelEvent$RegisterAdditional
 *  net.minecraftforge.client.event.RegisterClientReloadListenersEvent
 *  net.minecraftforge.client.event.RegisterKeyMappingsEvent
 *  net.minecraftforge.client.event.RegisterParticleProvidersEvent
 *  net.minecraftforge.client.event.RegisterShadersEvent
 *  net.minecraftforge.client.event.RenderGuiOverlayEvent$Pre
 *  net.minecraftforge.client.gui.overlay.VanillaGuiOverlay
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.common.util.MutableHashedLinkedMap
 *  net.minecraftforge.event.BuildCreativeModeTabContentsEvent
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  thedarkcolour.kotlinforforge.KotlinModLoadingContext
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.forge.client;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonClientImplementation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonKeyBinds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.atlas.CobblemonAtlases;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.shader.CobblemonShaders;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.compat.LambDynamicLightsCompat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.group.CobblemonItemGroups;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.particle.CobblemonParticles;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.particle.SnowstormParticleType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.cobblemon.mod.forge.client.ForgeClientPlatformEventHandler;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import thedarkcolour.kotlinforforge.KotlinModLoadingContext;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00d4\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001XB\t\b\u0002\u00a2\u0006\u0004\bW\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\r\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u0017\u0010\b\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u0006H\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\f\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\nH\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u000eH\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u0011H\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0017\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u0014H\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0017\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u0017H\u0002\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0017\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u001aH\u0002\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0017\u0010\u001e\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u001dH\u0002\u00a2\u0006\u0004\b\u001e\u0010\u001fJ+\u0010%\u001a\u00020\u00022\u0006\u0010!\u001a\u00020 2\u0012\u0010$\u001a\n\u0012\u0006\b\u0001\u0012\u00020#0\"\"\u00020#H\u0016\u00a2\u0006\u0004\b%\u0010&J7\u0010-\u001a\u00020\u0002\"\b\b\u0000\u0010(*\u00020'2\u000e\u0010*\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000)2\f\u0010,\u001a\b\u0012\u0004\u0012\u00028\u00000+H\u0016\u00a2\u0006\u0004\b-\u0010.J+\u00101\u001a\u00020\u00022\u0006\u00100\u001a\u00020/2\u0012\u0010$\u001a\n\u0012\u0006\b\u0001\u0012\u00020#0\"\"\u00020#H\u0016\u00a2\u0006\u0004\b1\u00102J7\u00106\u001a\u00020\u0002\"\b\b\u0000\u0010(*\u0002032\u000e\u0010*\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u0000042\f\u0010,\u001a\b\u0012\u0004\u0012\u00028\u000005H\u0016\u00a2\u0006\u0004\b6\u00107J+\u0010;\u001a\u00020\u00022\u0006\u0010!\u001a\u0002082\u0012\u0010:\u001a\n\u0012\u0006\b\u0001\u0012\u0002090\"\"\u000209H\u0016\u00a2\u0006\u0004\b;\u0010<J%\u0010B\u001a\u00020\u00022\u0006\u0010>\u001a\u00020=2\f\u0010A\u001a\b\u0012\u0004\u0012\u00020@0?H\u0016\u00a2\u0006\u0004\bB\u0010CJA\u0010I\u001a\u00020\u0002\"\b\b\u0000\u0010(*\u00020D2\f\u0010*\u001a\b\u0012\u0004\u0012\u00028\u00000E2\u0018\u0010,\u001a\u0014\u0012\u0004\u0012\u00020G\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000H0FH\u0016\u00a2\u0006\u0004\bI\u0010JJ\u0017\u0010O\u001a\u00020\u00022\u0006\u0010L\u001a\u00020KH\u0000\u00a2\u0006\u0004\bM\u0010NR$\u0010Q\u001a\u0004\u0018\u00010P8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bQ\u0010R\u001a\u0004\bS\u0010T\"\u0004\bU\u0010V\u00a8\u0006Y"}, d2={"Lcom/cobblemon/mod/forge/client/CobblemonForgeClient;", "Lcom/cobblemon/mod/common/CobblemonClientImplementation;", "", "attemptModCompat", "()V", "init", "Lnet/minecraftforge/event/BuildCreativeModeTabContentsEvent;", "e", "onBuildContents", "(Lnet/minecraftforge/event/BuildCreativeModeTabContentsEvent;)V", "Lnet/minecraftforge/fml/event/lifecycle/FMLClientSetupEvent;", "event", "onClientSetup", "(Lnet/minecraftforge/fml/event/lifecycle/FMLClientSetupEvent;)V", "Lnet/minecraftforge/client/event/RegisterKeyMappingsEvent;", "onKeyMappingRegister", "(Lnet/minecraftforge/client/event/RegisterKeyMappingsEvent;)V", "Lnet/minecraftforge/client/event/RegisterParticleProvidersEvent;", "onRegisterParticleProviders", "(Lnet/minecraftforge/client/event/RegisterParticleProvidersEvent;)V", "Lnet/minecraftforge/client/event/RegisterClientReloadListenersEvent;", "onRegisterReloadListener", "(Lnet/minecraftforge/client/event/RegisterClientReloadListenersEvent;)V", "Lnet/minecraftforge/client/event/RenderGuiOverlayEvent$Pre;", "onRenderGuiOverlayEvent", "(Lnet/minecraftforge/client/event/RenderGuiOverlayEvent$Pre;)V", "Lnet/minecraftforge/client/event/RegisterShadersEvent;", "onShaderRegistration", "(Lnet/minecraftforge/client/event/RegisterShadersEvent;)V", "Lnet/minecraftforge/client/event/ModelEvent$RegisterAdditional;", "register3dPokeballModels", "(Lnet/minecraftforge/client/event/ModelEvent$RegisterAdditional;)V", "Lnet/minecraft/client/color/block/BlockColor;", "provider", "", "Lnet/minecraft/world/level/block/Block;", "blocks", "registerBlockColors", "(Lnet/minecraft/client/color/block/BlockColor;[Lnet/minecraft/world/level/block/Block;)V", "Lnet/minecraft/world/level/block/entity/BlockEntity;", "T", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", "type", "Lnet/minecraft/client/renderer/blockentity/BlockEntityRendererProvider;", "factory", "registerBlockEntityRenderer", "(Lnet/minecraft/world/level/block/entity/BlockEntityType;Lnet/minecraft/client/renderer/blockentity/BlockEntityRendererProvider;)V", "Lnet/minecraft/client/renderer/RenderType;", "layer", "registerBlockRenderType", "(Lnet/minecraft/client/renderer/RenderType;[Lnet/minecraft/world/level/block/Block;)V", "Lnet/minecraft/world/entity/Entity;", "Lnet/minecraft/world/entity/EntityType;", "Lnet/minecraft/client/renderer/entity/EntityRendererProvider;", "registerEntityRenderer", "(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/client/renderer/entity/EntityRendererProvider;)V", "Lnet/minecraft/client/color/item/ItemColor;", "Lnet/minecraft/world/item/Item;", "items", "registerItemColors", "(Lnet/minecraft/client/color/item/ItemColor;[Lnet/minecraft/world/item/Item;)V", "Lnet/minecraft/client/model/geom/ModelLayerLocation;", "modelLayer", "Ljava/util/function/Supplier;", "Lnet/minecraft/client/model/geom/builders/LayerDefinition;", "supplier", "registerLayer", "(Lnet/minecraft/client/model/geom/ModelLayerLocation;Ljava/util/function/Supplier;)V", "Lnet/minecraft/core/particles/ParticleOptions;", "Lnet/minecraft/core/particles/ParticleType;", "Lkotlin/Function1;", "Lnet/minecraft/client/particle/SpriteSet;", "Lnet/minecraft/client/particle/ParticleProvider;", "registerParticleFactory", "(Lnet/minecraft/core/particles/ParticleType;Lkotlin/jvm/functions/Function1;)V", "Lnet/minecraft/server/packs/resources/PreparableReloadListener;", "reloader", "registerResourceReloader$forge", "(Lnet/minecraft/server/packs/resources/PreparableReloadListener;)V", "registerResourceReloader", "", "lastUpdateTime", "Ljava/lang/Long;", "getLastUpdateTime", "()Ljava/lang/Long;", "setLastUpdateTime", "(Ljava/lang/Long;)V", "<init>", "ForgeItemGroupInject", "forge"})
@SourceDebugExtension(value={"SMAP\nCobblemonForgeClient.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonForgeClient.kt\ncom/cobblemon/mod/forge/client/CobblemonForgeClient\n+ 2 Forge.kt\nthedarkcolour/kotlinforforge/forge/ForgeKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 5 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,224:1\n39#2:225\n13309#3,2:226\n1855#4,2:228\n1855#4,2:230\n37#5,2:232\n*S KotlinDebug\n*F\n+ 1 CobblemonForgeClient.kt\ncom/cobblemon/mod/forge/client/CobblemonForgeClient\n*L\n72#1:225\n135#1:226,2\n159#1:228,2\n95#1:230,2\n107#1:232,2\n*E\n"})
public final class CobblemonForgeClient
implements CobblemonClientImplementation {
    @NotNull
    public static final CobblemonForgeClient INSTANCE = new CobblemonForgeClient();
    @Nullable
    private static Long lastUpdateTime;

    private CobblemonForgeClient() {
    }

    public final void init() {
        boolean $i$f$getMOD_BUS = false;
        IEventBus $this$init_u24lambda_u240 = KotlinModLoadingContext.Companion.get().getKEventBus();
        boolean bl = false;
        $this$init_u24lambda_u240.addListener(INSTANCE::onClientSetup);
        $this$init_u24lambda_u240.addListener(INSTANCE::onKeyMappingRegister);
        $this$init_u24lambda_u240.addListener(INSTANCE::onRegisterParticleProviders);
        $this$init_u24lambda_u240.addListener(INSTANCE::register3dPokeballModels);
        $this$init_u24lambda_u240.addListener(INSTANCE::onBuildContents);
        $this$init_u24lambda_u240.addListener(INSTANCE::onRegisterReloadListener);
        $this$init_u24lambda_u240.addListener(INSTANCE::onShaderRegistration);
        MinecraftForge.EVENT_BUS.addListener(this::onRenderGuiOverlayEvent);
    }

    private final void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> CobblemonForgeClient.onClientSetup$lambda$1(this));
        ForgeClientPlatformEventHandler.INSTANCE.register();
    }

    private final void onRegisterReloadListener(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(CobblemonForgeClient::onRegisterReloadListener$lambda$4);
    }

    private final void onShaderRegistration(RegisterShadersEvent event) {
        event.registerShader(new ShaderInstance(event.getResourceProvider(), MiscUtils.cobblemonResource("particle_add"), DefaultVertexFormat.f_85820_), CobblemonForgeClient::onShaderRegistration$lambda$5);
        event.registerShader(new ShaderInstance(event.getResourceProvider(), MiscUtils.cobblemonResource("particle_cutout"), DefaultVertexFormat.f_85820_), CobblemonForgeClient::onShaderRegistration$lambda$6);
    }

    @Override
    public void registerLayer(@NotNull ModelLayerLocation modelLayer, @NotNull Supplier<LayerDefinition> supplier) {
        Intrinsics.checkNotNullParameter((Object)modelLayer, (String)"modelLayer");
        Intrinsics.checkNotNullParameter(supplier, (String)"supplier");
        ForgeHooksClient.registerLayerDefinition((ModelLayerLocation)modelLayer, supplier);
    }

    @Override
    public <T extends ParticleOptions> void registerParticleFactory(@NotNull ParticleType<T> type, @NotNull Function1<? super SpriteSet, ? extends ParticleProvider<T>> factory) {
        Intrinsics.checkNotNullParameter(type, (String)"type");
        Intrinsics.checkNotNullParameter(factory, (String)"factory");
        throw new UnsupportedOperationException("Forge can't store these early, use CobblemonForgeClient#onRegisterParticleProviders");
    }

    @Override
    public void registerBlockRenderType(@NotNull RenderType layer, Block ... blocks) {
        Intrinsics.checkNotNullParameter((Object)layer, (String)"layer");
        Intrinsics.checkNotNullParameter((Object)blocks, (String)"blocks");
        Block[] $this$forEach$iv = blocks;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            Block element$iv;
            Block block = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            ItemBlockRenderTypes.setRenderLayer((Block)block, (RenderType)layer);
        }
    }

    @Override
    public void registerItemColors(@NotNull ItemColor provider, Item ... items) {
        Intrinsics.checkNotNullParameter((Object)provider, (String)"provider");
        Intrinsics.checkNotNullParameter((Object)items, (String)"items");
        Minecraft.m_91087_().f_91041_.m_92689_(provider, (ItemLike[])Arrays.copyOf(items, items.length));
    }

    @Override
    public void registerBlockColors(@NotNull BlockColor provider, Block ... blocks) {
        Intrinsics.checkNotNullParameter((Object)provider, (String)"provider");
        Intrinsics.checkNotNullParameter((Object)blocks, (String)"blocks");
        Minecraft.m_91087_().m_91298_().m_92589_(provider, Arrays.copyOf(blocks, blocks.length));
    }

    @Override
    public <T extends BlockEntity> void registerBlockEntityRenderer(@NotNull BlockEntityType<? extends T> type, @NotNull BlockEntityRendererProvider<T> factory) {
        Intrinsics.checkNotNullParameter(type, (String)"type");
        Intrinsics.checkNotNullParameter(factory, (String)"factory");
        BlockEntityRenderers.m_173590_(type, factory);
    }

    @Override
    public <T extends Entity> void registerEntityRenderer(@NotNull EntityType<? extends T> type, @NotNull EntityRendererProvider<T> factory) {
        Intrinsics.checkNotNullParameter(type, (String)"type");
        Intrinsics.checkNotNullParameter(factory, (String)"factory");
        EntityRenderers.m_174036_(type, factory);
    }

    private final void register3dPokeballModels(ModelEvent.RegisterAdditional event) {
        Iterable $this$forEach$iv = PokeBalls.INSTANCE.all();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            PokeBall pokeball = (PokeBall)element$iv;
            boolean bl = false;
            event.register((ResourceLocation)new ModelResourceLocation(pokeball.getModel3d(), "inventory"));
        }
    }

    private final void onKeyMappingRegister(RegisterKeyMappingsEvent event) {
        CobblemonKeyBinds.INSTANCE.register((Function1<? super KeyMapping, Unit>)((Function1)new Function1<KeyMapping, Unit>((Object)event){

            public final void invoke(KeyMapping p0) {
                ((RegisterKeyMappingsEvent)this.receiver).register(p0);
            }
        }));
    }

    private final void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet((ParticleType)CobblemonParticles.INSTANCE.getSNOWSTORM_PARTICLE_TYPE(), SnowstormParticleType.Factory::new);
    }

    @Nullable
    public final Long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public final void setLastUpdateTime(@Nullable Long l) {
        lastUpdateTime = l;
    }

    private final void onRenderGuiOverlayEvent(RenderGuiOverlayEvent.Pre event) {
        if (Intrinsics.areEqual((Object)event.getOverlay().id(), (Object)VanillaGuiOverlay.CHAT_PANEL.id())) {
            Long lastUpdateTime = CobblemonForgeClient.lastUpdateTime;
            if (lastUpdateTime != null) {
                GuiGraphics guiGraphics = event.getGuiGraphics();
                Intrinsics.checkNotNullExpressionValue((Object)guiGraphics, (String)"event.guiGraphics");
                CobblemonClient.INSTANCE.beforeChatRender(guiGraphics, (float)(System.currentTimeMillis() - lastUpdateTime) / 1000.0f * 20.0f);
            }
            CobblemonForgeClient.lastUpdateTime = System.currentTimeMillis();
        }
    }

    public final void registerResourceReloader$forge(@NotNull PreparableReloadListener reloader) {
        Intrinsics.checkNotNullParameter((Object)reloader, (String)"reloader");
        ResourceManager resourceManager = Minecraft.m_91087_().m_91098_();
        Intrinsics.checkNotNull((Object)resourceManager, (String)"null cannot be cast to non-null type net.minecraft.resource.ReloadableResourceManagerImpl");
        ((ReloadableResourceManager)resourceManager).m_7217_(reloader);
    }

    private final void onBuildContents(BuildCreativeModeTabContentsEvent e) {
        MutableHashedLinkedMap mutableHashedLinkedMap = e.getEntries();
        Intrinsics.checkNotNullExpressionValue((Object)mutableHashedLinkedMap, (String)"e.entries");
        ForgeItemGroupInject forgeInject = new ForgeItemGroupInject((MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility>)mutableHashedLinkedMap);
        ResourceKey resourceKey = e.getTabKey();
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"e.tabKey");
        CobblemonItemGroups.INSTANCE.inject((ResourceKey<CreativeModeTab>)resourceKey, forgeInject);
    }

    private final void attemptModCompat() {
        if (Cobblemon.INSTANCE.getImplementation().isModInstalled("dynamiclightsreforged")) {
            LambDynamicLightsCompat.hookCompat();
            Cobblemon.INSTANCE.getLOGGER().info("Dynamic Lights Reforged compatibility enabled");
        }
    }

    private static final void onClientSetup$lambda$1(CobblemonForgeClient this$0) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        CobblemonClient.INSTANCE.initialize(this$0);
        this$0.attemptModCompat();
    }

    private static final void onRegisterReloadListener$lambda$4$lambda$3(ResourceManager $manager) {
        ResourceManager resourceManager = $manager;
        Intrinsics.checkNotNull((Object)resourceManager);
        CobblemonClient.INSTANCE.reloadCodedAssets(resourceManager);
    }

    private static final CompletableFuture onRegisterReloadListener$lambda$4(PreparableReloadListener.PreparationBarrier synchronizer, ResourceManager manager, ProfilerFiller prepareProfiler, ProfilerFiller applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
        List atlasFutures = new ArrayList();
        Iterable $this$forEach$iv = CobblemonAtlases.INSTANCE.getAtlases();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            TextureAtlasHolder it = (TextureAtlasHolder)element$iv;
            boolean bl = false;
            CompletableFuture completableFuture = it.m_5540_(synchronizer, manager, prepareProfiler, applyProfiler, prepareExecutor, applyExecutor);
            Intrinsics.checkNotNullExpressionValue((Object)completableFuture, (String)"it.reload(\n             \u2026tor\n                    )");
            atlasFutures.add(completableFuture);
        }
        Collection $this$toTypedArray$iv = atlasFutures;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        CompletableFuture[] completableFutureArray = thisCollection$iv.toArray(new CompletableFuture[0]);
        CompletionStage result = CompletableFuture.allOf(Arrays.copyOf(completableFutureArray, completableFutureArray.length)).thenRun(() -> CobblemonForgeClient.onRegisterReloadListener$lambda$4$lambda$3(manager));
        return result;
    }

    private static final void onShaderRegistration$lambda$5(ShaderInstance it) {
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        CobblemonShaders.INSTANCE.setPARTICLE_BLEND(it);
    }

    private static final void onShaderRegistration$lambda$6(ShaderInstance it) {
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        CobblemonShaders.INSTANCE.setPARTICLE_CUTOUT(it);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001f\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001f\u0010\b\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\b\u0010\u0007J\u0017\u0010\t\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\u000b\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000b\u0010\nR \u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/forge/client/CobblemonForgeClient$ForgeItemGroupInject;", "Lcom/cobblemon/mod/common/item/group/CobblemonItemGroups$Injector;", "Lnet/minecraft/world/level/ItemLike;", "item", "target", "", "putAfter", "(Lnet/minecraft/world/level/ItemLike;Lnet/minecraft/world/level/ItemLike;)V", "putBefore", "putFirst", "(Lnet/minecraft/world/level/ItemLike;)V", "putLast", "Lnet/minecraftforge/common/util/MutableHashedLinkedMap;", "Lnet/minecraft/world/item/ItemStack;", "Lnet/minecraft/item/ItemGroup$StackVisibility;", "entries", "Lnet/minecraftforge/common/util/MutableHashedLinkedMap;", "<init>", "(Lnet/minecraftforge/common/util/MutableHashedLinkedMap;)V", "forge"})
    private static final class ForgeItemGroupInject
    implements CobblemonItemGroups.Injector {
        @NotNull
        private final MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries;

        public ForgeItemGroupInject(@NotNull MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries) {
            Intrinsics.checkNotNullParameter(entries, (String)"entries");
            this.entries = entries;
        }

        @Override
        public void putFirst(@NotNull ItemLike item) {
            Intrinsics.checkNotNullParameter((Object)item, (String)"item");
            this.entries.putFirst((Object)new ItemStack(item), (Object)CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }

        @Override
        public void putBefore(@NotNull ItemLike item, @NotNull ItemLike target) {
            Intrinsics.checkNotNullParameter((Object)item, (String)"item");
            Intrinsics.checkNotNullParameter((Object)target, (String)"target");
            this.entries.putBefore((Object)new ItemStack(target), (Object)new ItemStack(item), (Object)CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }

        @Override
        public void putAfter(@NotNull ItemLike item, @NotNull ItemLike target) {
            Intrinsics.checkNotNullParameter((Object)item, (String)"item");
            Intrinsics.checkNotNullParameter((Object)target, (String)"target");
            this.entries.putAfter((Object)new ItemStack(target), (Object)new ItemStack(item), (Object)CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }

        @Override
        public void putLast(@NotNull ItemLike item) {
            Intrinsics.checkNotNullParameter((Object)item, (String)"item");
            this.entries.put((Object)new ItemStack(item), (Object)CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}

