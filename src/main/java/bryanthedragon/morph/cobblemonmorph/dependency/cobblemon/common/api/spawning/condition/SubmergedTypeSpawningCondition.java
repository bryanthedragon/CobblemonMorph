/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.level.material.Fluid
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.AreaTypeSpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.AreaSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SubmergedSpawningContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Merger;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\f\b&\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003B\u0007\u00a2\u0006\u0004\b'\u0010(J#\u0010\t\u001a\u00020\b2\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00028\u0000H\u0014\u00a2\u0006\u0004\b\r\u0010\u000eR*\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R$\u0010\u0017\u001a\u0004\u0018\u00010\f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR$\u0010\u001e\u001a\u0004\u0018\u00010\u001d8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R$\u0010$\u001a\u0004\u0018\u00010\u001d8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b$\u0010\u001f\u001a\u0004\b%\u0010!\"\u0004\b&\u0010#\u00a8\u0006)"}, d2={"Lcom/cobblemon/mod/common/api/spawning/condition/SubmergedTypeSpawningCondition;", "Lcom/cobblemon/mod/common/api/spawning/context/SubmergedSpawningContext;", "T", "Lcom/cobblemon/mod/common/api/spawning/condition/AreaTypeSpawningCondition;", "Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;", "other", "Lcom/cobblemon/mod/common/util/Merger;", "merger", "", "copyFrom", "(Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;Lcom/cobblemon/mod/common/util/Merger;)V", "ctx", "", "fits", "(Lcom/cobblemon/mod/common/api/spawning/context/SubmergedSpawningContext;)Z", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "Lnet/minecraft/world/level/material/Fluid;", "fluid", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "getFluid", "()Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "setFluid", "(Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;)V", "fluidIsSource", "Ljava/lang/Boolean;", "getFluidIsSource", "()Ljava/lang/Boolean;", "setFluidIsSource", "(Ljava/lang/Boolean;)V", "", "maxDepth", "Ljava/lang/Integer;", "getMaxDepth", "()Ljava/lang/Integer;", "setMaxDepth", "(Ljava/lang/Integer;)V", "minDepth", "getMinDepth", "setMinDepth", "<init>", "()V", "common"})
public abstract class SubmergedTypeSpawningCondition<T extends SubmergedSpawningContext>
extends AreaTypeSpawningCondition<T> {
    @Nullable
    private Integer minDepth;
    @Nullable
    private Integer maxDepth;
    @Nullable
    private Boolean fluidIsSource;
    @Nullable
    private RegistryLikeCondition<Fluid> fluid;

    @Nullable
    public final Integer getMinDepth() {
        return this.minDepth;
    }

    public final void setMinDepth(@Nullable Integer n) {
        this.minDepth = n;
    }

    @Nullable
    public final Integer getMaxDepth() {
        return this.maxDepth;
    }

    public final void setMaxDepth(@Nullable Integer n) {
        this.maxDepth = n;
    }

    @Nullable
    public final Boolean getFluidIsSource() {
        return this.fluidIsSource;
    }

    public final void setFluidIsSource(@Nullable Boolean bl) {
        this.fluidIsSource = bl;
    }

    @Nullable
    public final RegistryLikeCondition<Fluid> getFluid() {
        return this.fluid;
    }

    public final void setFluid(@Nullable RegistryLikeCondition<Fluid> registryLikeCondition) {
        this.fluid = registryLikeCondition;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected boolean fits(@NotNull T ctx) {
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
        if (this.minDepth != null) {
            int n = ((SubmergedSpawningContext)ctx).getDepth();
            Integer n4 = this.minDepth;
            Intrinsics.checkNotNull((Object)n4);
            if (n < n4) {
                return false;
            }
        }
        if (this.maxDepth != null) {
            int n = ((SubmergedSpawningContext)ctx).getDepth();
            Integer n5 = this.maxDepth;
            Intrinsics.checkNotNull((Object)n5);
            if (n > n5) {
                return false;
            }
        }
        if (this.fluidIsSource != null) {
            boolean bl = ((SubmergedSpawningContext)ctx).getFluid().m_76170_();
            Boolean bl2 = this.fluidIsSource;
            Intrinsics.checkNotNull((Object)bl2);
            if (bl != bl2) {
                return false;
            }
        }
        if (((SubmergedSpawningContext)ctx).getFluid().m_76178_()) return false;
        if (this.fluid == null) return true;
        RegistryLikeCondition<Fluid> registryLikeCondition = this.fluid;
        Intrinsics.checkNotNull(registryLikeCondition);
        Fluid fluid = ((SubmergedSpawningContext)ctx).getFluid().m_76152_();
        Intrinsics.checkNotNullExpressionValue((Object)fluid, (String)"ctx.fluid.fluid");
        if (!registryLikeCondition.fits(fluid, ((SpawningContext)ctx).getFluidRegistry())) return false;
        return true;
    }

    @Override
    public void copyFrom(@NotNull SpawningCondition<?> other, @NotNull Merger merger) {
        Intrinsics.checkNotNullParameter(other, (String)"other");
        Intrinsics.checkNotNullParameter((Object)merger, (String)"merger");
        super.copyFrom(other, merger);
        if (other instanceof SubmergedTypeSpawningCondition) {
            this.minDepth = merger.mergeSingle(this.minDepth, ((SubmergedTypeSpawningCondition)other).minDepth);
            this.maxDepth = merger.mergeSingle(this.minDepth, ((SubmergedTypeSpawningCondition)other).minDepth);
            this.fluidIsSource = merger.mergeSingle(this.fluidIsSource, ((SubmergedTypeSpawningCondition)other).fluidIsSource);
            this.fluid = merger.mergeSingle(this.fluid, ((SubmergedTypeSpawningCondition)other).fluid);
        }
    }
}

