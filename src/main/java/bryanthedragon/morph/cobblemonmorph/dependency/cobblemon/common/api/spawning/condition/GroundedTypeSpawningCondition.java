/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.world.level.block.Block
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.AreaTypeSpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.AreaSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.FlooredSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.GroundedSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Merger;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b&\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003B\u0007\u00a2\u0006\u0004\b\u0018\u0010\u0019J#\u0010\t\u001a\u00020\b2\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00028\u0000H\u0014\u00a2\u0006\u0004\b\r\u0010\u000eR0\u0010\u0012\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u0010\u0018\u00010\u000f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/api/spawning/condition/GroundedTypeSpawningCondition;", "Lcom/cobblemon/mod/common/api/spawning/context/GroundedSpawningContext;", "T", "Lcom/cobblemon/mod/common/api/spawning/condition/AreaTypeSpawningCondition;", "Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;", "other", "Lcom/cobblemon/mod/common/util/Merger;", "merger", "", "copyFrom", "(Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;Lcom/cobblemon/mod/common/util/Merger;)V", "ctx", "", "fits", "(Lcom/cobblemon/mod/common/api/spawning/context/GroundedSpawningContext;)Z", "", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "Lnet/minecraft/world/level/block/Block;", "neededBaseBlocks", "Ljava/util/List;", "getNeededBaseBlocks", "()Ljava/util/List;", "setNeededBaseBlocks", "(Ljava/util/List;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nGroundedSpawningCondition.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GroundedSpawningCondition.kt\ncom/cobblemon/mod/common/api/spawning/condition/GroundedTypeSpawningCondition\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,56:1\n2624#2,3:57\n*S KotlinDebug\n*F\n+ 1 GroundedSpawningCondition.kt\ncom/cobblemon/mod/common/api/spawning/condition/GroundedTypeSpawningCondition\n*L\n34#1:57,3\n*E\n"})
public abstract class GroundedTypeSpawningCondition<T extends GroundedSpawningContext>
extends AreaTypeSpawningCondition<T> {
    @Nullable
    private List<RegistryLikeCondition<Block>> neededBaseBlocks;

    @Nullable
    public final List<RegistryLikeCondition<Block>> getNeededBaseBlocks() {
        return this.neededBaseBlocks;
    }

    public final void setNeededBaseBlocks(@Nullable List<RegistryLikeCondition<Block>> list) {
        this.neededBaseBlocks = list;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected boolean fits(@NotNull T ctx) {
        Block block;
        RegistryLikeCondition it;
        Intrinsics.checkNotNullParameter(ctx, (String)"ctx");
        if (!super.fits((AreaSpawningContext)ctx)) {
            return false;
        }
        if (this.getMinHeight() != null) {
            int n = ((AreaSpawningContext)ctx).getHeight();
            Integer n2 = this.getMinHeight();
            Intrinsics.checkNotNull((Object)n2);
            if (n < n2) {
                return false;
            }
        }
        if (this.getMaxHeight() != null) {
            int n = ((AreaSpawningContext)ctx).getHeight();
            Integer n3 = this.getMaxHeight();
            Intrinsics.checkNotNull((Object)n3);
            if (n > n3) {
                return false;
            }
        }
        if (this.neededBaseBlocks == null) return true;
        List<RegistryLikeCondition<Block>> list = this.neededBaseBlocks;
        Intrinsics.checkNotNull(list);
        Iterable $this$none$iv = list;
        boolean $i$f$none = false;
        if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
            return false;
        }
        Iterator iterator = $this$none$iv.iterator();
        do {
            if (!iterator.hasNext()) return false;
            Object element$iv = iterator.next();
            it = (RegistryLikeCondition)element$iv;
            boolean bl = false;
            block = ((FlooredSpawningContext)ctx).getBaseBlock().m_60734_();
            Intrinsics.checkNotNullExpressionValue((Object)block, (String)"ctx.baseBlock.block");
        } while (!it.fits(block, ((SpawningContext)ctx).getBlockRegistry()));
        return true;
    }

    @Override
    public void copyFrom(@NotNull SpawningCondition<?> other, @NotNull Merger merger) {
        Intrinsics.checkNotNullParameter(other, (String)"other");
        Intrinsics.checkNotNullParameter((Object)merger, (String)"merger");
        super.copyFrom(other, merger);
        if (other instanceof GroundedTypeSpawningCondition) {
            Collection collection = merger.merge((Collection)this.neededBaseBlocks, (Collection)((GroundedTypeSpawningCondition)other).neededBaseBlocks);
            this.neededBaseBlocks = collection != null ? CollectionsKt.toMutableList(collection) : null;
        }
    }
}

