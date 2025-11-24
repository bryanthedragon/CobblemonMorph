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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.AreaSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Merger;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b&\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003B\u0007\u00a2\u0006\u0004\b!\u0010\"J#\u0010\b\u001a\u00020\u00072\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00028\u0000H\u0014\u00a2\u0006\u0004\b\f\u0010\rR$\u0010\u000f\u001a\u0004\u0018\u00010\u000e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R$\u0010\u0015\u001a\u0004\u0018\u00010\u000e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0015\u0010\u0010\u001a\u0004\b\u0016\u0010\u0012\"\u0004\b\u0017\u0010\u0014R0\u0010\u001b\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u0019\u0018\u00010\u00188\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 \u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/api/spawning/condition/AreaTypeSpawningCondition;", "Lcom/cobblemon/mod/common/api/spawning/context/AreaSpawningContext;", "T", "Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;", "other", "Lcom/cobblemon/mod/common/util/Merger;", "merger", "", "copyFrom", "(Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;Lcom/cobblemon/mod/common/util/Merger;)V", "ctx", "", "fits", "(Lcom/cobblemon/mod/common/api/spawning/context/AreaSpawningContext;)Z", "", "maxHeight", "Ljava/lang/Integer;", "getMaxHeight", "()Ljava/lang/Integer;", "setMaxHeight", "(Ljava/lang/Integer;)V", "minHeight", "getMinHeight", "setMinHeight", "", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "Lnet/minecraft/world/level/block/Block;", "neededNearbyBlocks", "Ljava/util/List;", "getNeededNearbyBlocks", "()Ljava/util/List;", "setNeededNearbyBlocks", "(Ljava/util/List;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nAreaSpawningCondition.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AreaSpawningCondition.kt\ncom/cobblemon/mod/common/api/spawning/condition/AreaTypeSpawningCondition\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,64:1\n2624#2,2:65\n1747#2,3:67\n2626#2:70\n*S KotlinDebug\n*F\n+ 1 AreaSpawningCondition.kt\ncom/cobblemon/mod/common/api/spawning/condition/AreaTypeSpawningCondition\n*L\n36#1:65,2\n36#1:67,3\n36#1:70\n*E\n"})
public abstract class AreaTypeSpawningCondition<T extends AreaSpawningContext>
extends SpawningCondition<T> {
    @Nullable
    private Integer minHeight;
    @Nullable
    private Integer maxHeight;
    @Nullable
    private List<RegistryLikeCondition<Block>> neededNearbyBlocks;

    @Nullable
    public final Integer getMinHeight() {
        return this.minHeight;
    }

    public final void setMinHeight(@Nullable Integer n) {
        this.minHeight = n;
    }

    @Nullable
    public final Integer getMaxHeight() {
        return this.maxHeight;
    }

    public final void setMaxHeight(@Nullable Integer n) {
        this.maxHeight = n;
    }

    @Nullable
    public final List<RegistryLikeCondition<Block>> getNeededNearbyBlocks() {
        return this.neededNearbyBlocks;
    }

    public final void setNeededNearbyBlocks(@Nullable List<RegistryLikeCondition<Block>> list) {
        this.neededNearbyBlocks = list;
    }

    @Override
    protected boolean fits(@NotNull T ctx) {
        Intrinsics.checkNotNullParameter(ctx, (String)"ctx");
        if (!super.fits((SpawningContext)ctx)) {
            return false;
        }
        if (this.minHeight != null) {
            int n = ((AreaSpawningContext)ctx).getHeight();
            Integer n2 = this.minHeight;
            Intrinsics.checkNotNull((Object)n2);
            if (n < n2) {
                return false;
            }
        }
        if (this.maxHeight != null) {
            int n = ((AreaSpawningContext)ctx).getHeight();
            Integer n3 = this.maxHeight;
            Intrinsics.checkNotNull((Object)n3);
            if (n > n3) {
                return false;
            }
        }
        if (this.neededNearbyBlocks != null) {
            boolean bl;
            block14: {
                List<RegistryLikeCondition<Block>> list = this.neededNearbyBlocks;
                Intrinsics.checkNotNull(list);
                Iterable $this$none$iv = list;
                boolean $i$f$none = false;
                if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                    bl = true;
                } else {
                    for (Object element$iv : $this$none$iv) {
                        boolean bl2;
                        block13: {
                            RegistryLikeCondition cond = (RegistryLikeCondition)element$iv;
                            boolean bl3 = false;
                            Iterable $this$any$iv = ((AreaSpawningContext)ctx).getNearbyBlockTypes();
                            boolean $i$f$any = false;
                            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                                bl2 = false;
                            } else {
                                for (Object element$iv2 : $this$any$iv) {
                                    Block it = (Block)element$iv2;
                                    boolean bl4 = false;
                                    if (!cond.fits(it, ((SpawningContext)ctx).getBlockRegistry())) continue;
                                    bl2 = true;
                                    break block13;
                                }
                                bl2 = false;
                            }
                        }
                        if (!bl2) continue;
                        bl = false;
                        break block14;
                    }
                    bl = true;
                }
            }
            if (bl) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void copyFrom(@NotNull SpawningCondition<?> other, @NotNull Merger merger) {
        Intrinsics.checkNotNullParameter(other, (String)"other");
        Intrinsics.checkNotNullParameter((Object)merger, (String)"merger");
        super.copyFrom(other, merger);
        if (other instanceof AreaTypeSpawningCondition) {
            merger.mergeSingle(this.minHeight, ((AreaTypeSpawningCondition)other).minHeight);
            merger.mergeSingle(this.maxHeight, ((AreaTypeSpawningCondition)other).maxHeight);
            Collection collection = merger.merge((Collection)this.neededNearbyBlocks, (Collection)((AreaTypeSpawningCondition)other).neededNearbyBlocks);
            this.neededNearbyBlocks = collection != null ? CollectionsKt.toMutableList(collection) : null;
        }
    }
}

