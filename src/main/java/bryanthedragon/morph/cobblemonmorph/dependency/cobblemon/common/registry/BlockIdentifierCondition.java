/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.level.block.Block
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.registry;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeIdentifierCondition;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u000f\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/registry/BlockIdentifierCondition;", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeIdentifierCondition;", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "<init>", "(Lnet/minecraft/resources/ResourceLocation;)V", "common"})
public final class BlockIdentifierCondition
extends RegistryLikeIdentifierCondition<Block> {
    public BlockIdentifierCondition(@NotNull ResourceLocation identifier) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        super(identifier);
    }
}

