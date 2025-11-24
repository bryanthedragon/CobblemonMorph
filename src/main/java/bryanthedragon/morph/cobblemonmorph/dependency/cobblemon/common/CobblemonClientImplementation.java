/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  net.minecraft.client.color.block.BlockColor
 *  net.minecraft.client.color.item.ItemColor
 *  net.minecraft.client.model.geom.ModelLayerLocation
 *  net.minecraft.client.model.geom.builders.LayerDefinition
 *  net.minecraft.client.particle.ParticleProvider
 *  net.minecraft.client.particle.SpriteSet
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
 *  net.minecraft.client.renderer.entity.EntityRendererProvider
 *  net.minecraft.core.particles.ParticleOptions
 *  net.minecraft.core.particles.ParticleType
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J+\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\"\u00020\u0005H&\u00a2\u0006\u0004\b\b\u0010\tJ7\u0010\u0010\u001a\u00020\u0007\"\b\b\u0000\u0010\u000b*\u00020\n2\u000e\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\f2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000eH&\u00a2\u0006\u0004\b\u0010\u0010\u0011J+\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u00122\u0012\u0010\u0006\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\"\u00020\u0005H&\u00a2\u0006\u0004\b\u0014\u0010\u0015J7\u0010\u0019\u001a\u00020\u0007\"\b\b\u0000\u0010\u000b*\u00020\u00162\u000e\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u00172\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0018H&\u00a2\u0006\u0004\b\u0019\u0010\u001aJ+\u0010\u001e\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u001b2\u0012\u0010\u001d\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001c0\u0004\"\u00020\u001cH&\u00a2\u0006\u0004\b\u001e\u0010\u001fJ%\u0010%\u001a\u00020\u00072\u0006\u0010!\u001a\u00020 2\f\u0010$\u001a\b\u0012\u0004\u0012\u00020#0\"H&\u00a2\u0006\u0004\b%\u0010&JA\u0010,\u001a\u00020\u0007\"\b\b\u0000\u0010\u000b*\u00020'2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000(2\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020*\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000+0)H&\u00a2\u0006\u0004\b,\u0010-\u00a8\u0006."}, d2={"Lcom/cobblemon/mod/common/CobblemonClientImplementation;", "", "Lnet/minecraft/client/color/block/BlockColor;", "provider", "", "Lnet/minecraft/world/level/block/Block;", "blocks", "", "registerBlockColors", "(Lnet/minecraft/client/color/block/BlockColor;[Lnet/minecraft/world/level/block/Block;)V", "Lnet/minecraft/world/level/block/entity/BlockEntity;", "T", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", "type", "Lnet/minecraft/client/renderer/blockentity/BlockEntityRendererProvider;", "factory", "registerBlockEntityRenderer", "(Lnet/minecraft/world/level/block/entity/BlockEntityType;Lnet/minecraft/client/renderer/blockentity/BlockEntityRendererProvider;)V", "Lnet/minecraft/client/renderer/RenderType;", "layer", "registerBlockRenderType", "(Lnet/minecraft/client/renderer/RenderType;[Lnet/minecraft/world/level/block/Block;)V", "Lnet/minecraft/world/entity/Entity;", "Lnet/minecraft/world/entity/EntityType;", "Lnet/minecraft/client/renderer/entity/EntityRendererProvider;", "registerEntityRenderer", "(Lnet/minecraft/world/entity/EntityType;Lnet/minecraft/client/renderer/entity/EntityRendererProvider;)V", "Lnet/minecraft/client/color/item/ItemColor;", "Lnet/minecraft/world/item/Item;", "items", "registerItemColors", "(Lnet/minecraft/client/color/item/ItemColor;[Lnet/minecraft/world/item/Item;)V", "Lnet/minecraft/client/model/geom/ModelLayerLocation;", "modelLayer", "Ljava/util/function/Supplier;", "Lnet/minecraft/client/model/geom/builders/LayerDefinition;", "supplier", "registerLayer", "(Lnet/minecraft/client/model/geom/ModelLayerLocation;Ljava/util/function/Supplier;)V", "Lnet/minecraft/core/particles/ParticleOptions;", "Lnet/minecraft/core/particles/ParticleType;", "Lkotlin/Function1;", "Lnet/minecraft/client/particle/SpriteSet;", "Lnet/minecraft/client/particle/ParticleProvider;", "registerParticleFactory", "(Lnet/minecraft/core/particles/ParticleType;Lkotlin/jvm/functions/Function1;)V", "common"})
public interface CobblemonClientImplementation {
    public void registerLayer(@NotNull ModelLayerLocation var1, @NotNull Supplier<LayerDefinition> var2);

    public <T extends ParticleOptions> void registerParticleFactory(@NotNull ParticleType<T> var1, @NotNull Function1<? super SpriteSet, ? extends ParticleProvider<T>> var2);

    public void registerBlockRenderType(@NotNull RenderType var1, Block ... var2);

    public void registerItemColors(@NotNull ItemColor var1, Item ... var2);

    public void registerBlockColors(@NotNull BlockColor var1, Block ... var2);

    public <T extends BlockEntity> void registerBlockEntityRenderer(@NotNull BlockEntityType<? extends T> var1, @NotNull BlockEntityRendererProvider<T> var2);

    public <T extends Entity> void registerEntityRenderer(@NotNull EntityType<? extends T> var1, @NotNull EntityRendererProvider<T> var2);
}

