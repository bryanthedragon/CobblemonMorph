/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.Holder
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.biome.Biome
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition.BerrySpawnCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\f\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0017\u0012\u0006\u0010\u0013\u001a\u00020\f\u0012\u0006\u0010\u000f\u001a\u00020\f\u00a2\u0006\u0004\b\u0015\u0010\u0016J%\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eR\u0017\u0010\u000f\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0013\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0010\u001a\u0004\b\u0014\u0010\u0012\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/api/berry/spawncondition/AllBiomeCondition;", "Lcom/cobblemon/mod/common/api/berry/spawncondition/BerrySpawnCondition;", "Lcom/cobblemon/mod/common/api/berry/Berry;", "berry", "Lnet/minecraft/core/Holder;", "Lnet/minecraft/world/level/biome/Biome;", "biome", "", "canSpawn", "(Lcom/cobblemon/mod/common/api/berry/Berry;Lnet/minecraft/core/Holder;)Z", "Lnet/minecraft/util/RandomSource;", "random", "", "getGroveSize", "(Lnet/minecraft/util/RandomSource;)I", "maxGroveSize", "I", "getMaxGroveSize", "()I", "minGroveSize", "getMinGroveSize", "<init>", "(II)V", "Companion", "common"})
public final class AllBiomeCondition
implements BerrySpawnCondition {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final int minGroveSize;
    private final int maxGroveSize;
    @NotNull
    private static final ResourceLocation ID = MiscUtils.cobblemonResource("all_biome");

    public AllBiomeCondition(int minGroveSize, int maxGroveSize) {
        this.minGroveSize = minGroveSize;
        this.maxGroveSize = maxGroveSize;
    }

    public final int getMinGroveSize() {
        return this.minGroveSize;
    }

    public final int getMaxGroveSize() {
        return this.maxGroveSize;
    }

    @Override
    public boolean canSpawn(@NotNull Berry berry, @NotNull Holder<Biome> biome2) {
        Intrinsics.checkNotNullParameter((Object)berry, (String)"berry");
        Intrinsics.checkNotNullParameter(biome2, (String)"biome");
        return true;
    }

    @Override
    public int getGroveSize(@NotNull RandomSource random) {
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        return random.m_216332_(this.minGroveSize, this.maxGroveSize);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/berry/spawncondition/AllBiomeCondition$Companion;", "", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

