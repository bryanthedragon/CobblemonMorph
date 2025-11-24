/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Flavor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u001b\u001a\u00020\u001a\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0016\u001a\u0004\u0018\u00010\f\u0012\b\u0010\r\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0004\b\u001f\u0010 J\u001d\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0019\u0010\b\u001a\u0004\u0018\u00010\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0019\u0010\r\u001a\u0004\u0018\u00010\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0019\u0010\u0016\u001a\u0004\u0018\u00010\f8\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u000e\u001a\u0004\b\u0017\u0010\u0010R\u0019\u0010\u0018\u001a\u0004\u0018\u00010\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\t\u001a\u0004\b\u0019\u0010\u000bR\u0017\u0010\u001b\u001a\u00020\u001a8\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/pokemon/Nature;", "", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "stat", "", "value", "modifyStat", "(Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;I)I", "decreasedStat", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "getDecreasedStat", "()Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "Lcom/cobblemon/mod/common/api/berry/Flavor;", "dislikedFlavor", "Lcom/cobblemon/mod/common/api/berry/Flavor;", "getDislikedFlavor", "()Lcom/cobblemon/mod/common/api/berry/Flavor;", "", "displayName", "Ljava/lang/String;", "getDisplayName", "()Ljava/lang/String;", "favoriteFlavor", "getFavoriteFlavor", "increasedStat", "getIncreasedStat", "Lnet/minecraft/resources/ResourceLocation;", "name", "Lnet/minecraft/resources/ResourceLocation;", "getName", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Ljava/lang/String;Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;Lcom/cobblemon/mod/common/api/berry/Flavor;Lcom/cobblemon/mod/common/api/berry/Flavor;)V", "common"})
public final class Nature {
    @NotNull
    private final ResourceLocation name;
    @NotNull
    private final String displayName;
    @Nullable
    private final Stat increasedStat;
    @Nullable
    private final Stat decreasedStat;
    @Nullable
    private final Flavor favoriteFlavor;
    @Nullable
    private final Flavor dislikedFlavor;

    public Nature(@NotNull ResourceLocation name, @NotNull String displayName, @Nullable Stat increasedStat, @Nullable Stat decreasedStat, @Nullable Flavor favoriteFlavor, @Nullable Flavor dislikedFlavor) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
        this.name = name;
        this.displayName = displayName;
        this.increasedStat = increasedStat;
        this.decreasedStat = decreasedStat;
        this.favoriteFlavor = favoriteFlavor;
        this.dislikedFlavor = dislikedFlavor;
    }

    @NotNull
    public final ResourceLocation getName() {
        return this.name;
    }

    @NotNull
    public final String getDisplayName() {
        return this.displayName;
    }

    @Nullable
    public final Stat getIncreasedStat() {
        return this.increasedStat;
    }

    @Nullable
    public final Stat getDecreasedStat() {
        return this.decreasedStat;
    }

    @Nullable
    public final Flavor getFavoriteFlavor() {
        return this.favoriteFlavor;
    }

    @Nullable
    public final Flavor getDislikedFlavor() {
        return this.dislikedFlavor;
    }

    public final int modifyStat(@NotNull Stat stat, int value2) {
        Intrinsics.checkNotNullParameter((Object)stat, (String)"stat");
        Stat stat2 = stat;
        return Intrinsics.areEqual((Object)stat2, (Object)this.increasedStat) ? Mth.m_14107_((double)((double)value2 * 1.1)) : (Intrinsics.areEqual((Object)stat2, (Object)this.decreasedStat) ? Mth.m_14107_((double)((double)value2 * 0.9)) : value2);
    }
}

