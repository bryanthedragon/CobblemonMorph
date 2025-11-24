/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.core.Holder
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.biome.Biome
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berry;
import kotlin.Metadata;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J%\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\nH&\u00a2\u0006\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/api/berry/spawncondition/BerrySpawnCondition;", "", "Lcom/cobblemon/mod/common/api/berry/Berry;", "berry", "Lnet/minecraft/core/Holder;", "Lnet/minecraft/world/level/biome/Biome;", "biome", "", "canSpawn", "(Lcom/cobblemon/mod/common/api/berry/Berry;Lnet/minecraft/core/Holder;)Z", "Lnet/minecraft/util/RandomSource;", "random", "", "getGroveSize", "(Lnet/minecraft/util/RandomSource;)I", "common"})
public interface BerrySpawnCondition {
    public int getGroveSize(@NotNull RandomSource var1);

    public boolean canSpawn(@NotNull Berry var1, @NotNull Holder<Biome> var2);
}

