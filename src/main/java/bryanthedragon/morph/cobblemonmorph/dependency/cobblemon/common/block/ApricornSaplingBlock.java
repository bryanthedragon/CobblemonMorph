/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.level.block.SaplingBlock
 *  net.minecraft.world.level.block.grower.AbstractTreeGrower
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.apricorn.Apricorn;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.grower.ApricornTreeGrower;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/block/ApricornSaplingBlock;", "Lnet/minecraft/world/level/block/SaplingBlock;", "Lnet/minecraft/block/AbstractBlock$Settings;", "properties", "Lcom/cobblemon/mod/common/api/apricorn/Apricorn;", "apricorn", "<init>", "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;Lcom/cobblemon/mod/common/api/apricorn/Apricorn;)V", "common"})
public final class ApricornSaplingBlock
extends SaplingBlock {
    public ApricornSaplingBlock(@NotNull BlockBehaviour.Properties properties2, @NotNull Apricorn apricorn) {
        Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
        Intrinsics.checkNotNullParameter((Object)((Object)apricorn), (String)"apricorn");
        super((AbstractTreeGrower)new ApricornTreeGrower(apricorn), properties2);
    }
}

