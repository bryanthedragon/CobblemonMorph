/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Axis
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.MultiBufferSource
 *  net.minecraft.client.renderer.blockentity.BlockEntityRenderer
 *  net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider$Context
 *  net.minecraft.core.Direction
 *  net.minecraft.world.item.ItemDisplayContext
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.HealingMachineBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u0015*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003:\u0001\u0015B\u000f\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0013\u0010\u0014J?\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0004\u001a\u00028\u00002\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/client/render/block/HealingMachineRenderer;", "Lnet/minecraft/world/level/block/entity/BlockEntity;", "T", "Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderer;", "blockEntity", "", "tickDelta", "Lcom/mojang/blaze3d/vertex/PoseStack;", "poseStack", "Lnet/minecraft/client/renderer/MultiBufferSource;", "multiBufferSource", "", "light", "overlay", "", "render", "(Lnet/minecraft/world/level/block/entity/BlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", "Lnet/minecraft/client/render/block/entity/BlockEntityRendererFactory$Context;", "ctx", "<init>", "(Lnet/minecraft/client/renderer/blockentity/BlockEntityRendererProvider$Context;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nHealingMachineRenderer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HealingMachineRenderer.kt\ncom/cobblemon/mod/common/client/render/block/HealingMachineRenderer\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,63:1\n215#2,2:64\n*S KotlinDebug\n*F\n+ 1 HealingMachineRenderer.kt\ncom/cobblemon/mod/common/client/render/block/HealingMachineRenderer\n*L\n54#1:64,2\n*E\n"})
public final class HealingMachineRenderer<T extends BlockEntity>
implements BlockEntityRenderer<T> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final List<Pair<Double, Double>> offsets;

    public HealingMachineRenderer(@NotNull BlockEntityRendererProvider.Context ctx) {
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
    }

    public void m_6922_(@NotNull T blockEntity, float tickDelta, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int light, int overlay2) {
        BlockState blockState;
        Intrinsics.checkNotNullParameter(blockEntity, (String)"blockEntity");
        Intrinsics.checkNotNullParameter((Object)poseStack, (String)"poseStack");
        Intrinsics.checkNotNullParameter((Object)multiBufferSource, (String)"multiBufferSource");
        if (!(blockEntity instanceof HealingMachineBlockEntity)) {
            return;
        }
        poseStack.m_85836_();
        if (((HealingMachineBlockEntity)((Object)blockEntity)).m_58904_() != null) {
            blockState = ((HealingMachineBlockEntity)((Object)blockEntity)).m_58900_();
        } else {
            Object object = CobblemonBlocks.HEALING_MACHINE.m_49966_().m_61124_((Property)HorizontalDirectionalBlock.f_54117_, (Comparable)Direction.SOUTH);
            Intrinsics.checkNotNull((Object)object, (String)"null cannot be cast to non-null type net.minecraft.block.BlockState");
            blockState = (BlockState)object;
        }
        BlockState blockState2 = blockState;
        float yRot = ((Direction)blockState2.m_61143_((Property)HorizontalDirectionalBlock.f_54117_)).m_122435_();
        poseStack.m_85837_(0.5, 0.5, 0.5);
        poseStack.m_252781_(Axis.f_252436_.m_252977_(-yRot));
        poseStack.m_85841_(0.65f, 0.65f, 0.65f);
        Map<Integer, PokeBall> $this$forEach$iv = ((HealingMachineBlockEntity)((Object)blockEntity)).pokeBalls();
        boolean $i$f$forEach = false;
        Iterator<Map.Entry<Integer, PokeBall>> iterator = $this$forEach$iv.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, PokeBall> element$iv;
            Map.Entry<Integer, PokeBall> entry = element$iv = iterator.next();
            boolean bl = false;
            int index = ((Number)entry.getKey()).intValue();
            PokeBall pokeBall = entry.getValue();
            poseStack.m_85836_();
            Pair<Double, Double> offset = offsets.get(index);
            poseStack.m_85837_(((Number)offset.getFirst()).doubleValue(), 0.4, ((Number)offset.getSecond()).doubleValue());
            Minecraft.m_91087_().m_91291_().m_269128_(PokeBall.stack$default(pokeBall, 0, 1, null), ItemDisplayContext.GROUND, light, overlay2, poseStack, multiBufferSource, ((HealingMachineBlockEntity)((Object)blockEntity)).m_58904_(), 0);
            poseStack.m_85849_();
        }
        poseStack.m_85849_();
    }

    static {
        Object[] objectArray = new Pair[]{TuplesKt.to((Object)0.2, (Object)0.385), TuplesKt.to((Object)-0.2, (Object)0.385), TuplesKt.to((Object)0.2, (Object)0.0), TuplesKt.to((Object)-0.2, (Object)0.0), TuplesKt.to((Object)0.2, (Object)-0.385), TuplesKt.to((Object)-0.2, (Object)-0.385)};
        offsets = CollectionsKt.listOf((Object[])objectArray);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR&\u0010\u0005\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00040\u00030\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/client/render/block/HealingMachineRenderer$Companion;", "", "", "Lkotlin/Pair;", "", "offsets", "Ljava/util/List;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

