/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.random.Random
 *  kotlin.ranges.IntRange
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0016\u001a\u00020\u0015\u0012\u0006\u0010\u0017\u001a\u00020\u0003\u0012\u0006\u0010\u0018\u001a\u00020\u0003\u0012\u0006\u0010\u0019\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0004\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0019\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J'\u0010\u000e\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ'\u0010\u0010\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u000fJ\r\u0010\u0011\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00048\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u0014\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;", "Lcom/cobblemon/mod/common/api/pokemon/status/Status;", "Lkotlin/Pair;", "", "Lkotlin/ranges/IntRange;", "configEntry", "()Lkotlin/Pair;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lkotlin/random/Random;", "random", "", "onSecondPassed", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/pokemon/Pokemon;Lkotlin/random/Random;)V", "onStatusExpire", "statusPeriod", "()Lkotlin/ranges/IntRange;", "defaultDuration", "Lkotlin/ranges/IntRange;", "Lnet/minecraft/resources/ResourceLocation;", "name", "showdownName", "applyMessage", "removeMessage", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/ranges/IntRange;)V", "common"})
public class PersistentStatus
extends Status {
    @NotNull
    private final IntRange defaultDuration;

    public PersistentStatus(@NotNull ResourceLocation name, @NotNull String showdownName, @NotNull String applyMessage, @NotNull String removeMessage, @NotNull IntRange defaultDuration) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)showdownName, (String)"showdownName");
        Intrinsics.checkNotNullParameter((Object)applyMessage, (String)"applyMessage");
        Intrinsics.checkNotNullParameter((Object)removeMessage, (String)"removeMessage");
        Intrinsics.checkNotNullParameter((Object)defaultDuration, (String)"defaultDuration");
        super(name, showdownName, applyMessage, removeMessage);
        this.defaultDuration = defaultDuration;
    }

    public /* synthetic */ PersistentStatus(ResourceLocation resourceLocation, String string, String string2, String string3, IntRange intRange, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 0x10) != 0) {
            intRange = new IntRange(0, 0);
        }
        this(resourceLocation, string, string2, string3, intRange);
    }

    public void onStatusExpire(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull Random random) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        Object[] objectArray = new Object[]{pokemon.getDisplayName()};
        player.m_213846_((Component)MiscUtilsKt.asTranslated(this.getRemoveMessage(), objectArray));
    }

    public void onSecondPassed(@NotNull ServerPlayer player, @NotNull Pokemon pokemon, @NotNull Random random) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
    }

    @NotNull
    public final IntRange statusPeriod() {
        IntRange intRange = Cobblemon.INSTANCE.getConfig().getPassiveStatuses().get(this.getName().toString());
        if (intRange == null) {
            intRange = this.defaultDuration;
        }
        return intRange;
    }

    @NotNull
    public final Pair<String, IntRange> configEntry() {
        String string = this.getName().toString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"name.toString()");
        return TuplesKt.to((Object)string, (Object)this.defaultDuration);
    }
}

