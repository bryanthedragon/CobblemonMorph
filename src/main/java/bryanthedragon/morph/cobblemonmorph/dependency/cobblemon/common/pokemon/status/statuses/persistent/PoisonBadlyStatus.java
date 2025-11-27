/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.random.Random
 *  kotlin.ranges.IntRange
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/PoisonBadlyStatus;", "Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lkotlin/random/Random;", "random", "", "onSecondPassed", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/pokemon/Pokemon;Lkotlin/random/Random;)V", "<init>", "()V", "common"})
public final class PoisonBadlyStatus
extends PersistentStatus {
    public PoisonBadlyStatus() {
        super(MiscUtils.cobblemonResource("poisonbadly"), "tox", "cobblemon.status.poisonbadly.apply", "cobblemon.status.poison.cure", new IntRange(180, 300));
    }

    @Override
    public void onSecondPassed(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull Random random) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        if (!pokemon.isFainted() && random.nextInt(15) == 0) {
            pokemon.setCurrentHealth(pokemon.getCurrentHealth() - Math.max(1, (int)Math.rint((double)pokemon.getHp() * 0.1)) * (Intrinsics.areEqual((Object)pokemon.getAbility().getTemplate().getName(), (Object)"poisonheal") ? -1 : 1));
            if (pokemon.getCurrentHealth() == pokemon.getHp()) {
                pokemon.setStatus(null);
            }
        }
    }
}

