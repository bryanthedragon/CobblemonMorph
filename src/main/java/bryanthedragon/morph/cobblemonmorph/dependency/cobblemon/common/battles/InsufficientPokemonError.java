/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleStartError;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u0011\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\t\u001a\u0004\b\u0012\u0010\u000b\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/battles/InsufficientPokemonError;", "Lcom/cobblemon/mod/common/battles/BattleStartError;", "Lnet/minecraft/world/entity/Entity;", "entity", "Lnet/minecraft/network/chat/MutableComponent;", "getMessageFor", "(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/network/chat/MutableComponent;", "", "hadCount", "I", "getHadCount", "()I", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "()Lnet/minecraft/server/level/ServerPlayer;", "requiredCount", "getRequiredCount", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;II)V", "common"})
public final class InsufficientPokemonError
implements BattleStartError {
    @NotNull
    private final ServerPlayer player;
    private final int requiredCount;
    private final int hadCount;

    public InsufficientPokemonError(@NotNull ServerPlayer player, int requiredCount, int hadCount) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        this.player = player;
        this.requiredCount = requiredCount;
        this.hadCount = hadCount;
    }

    @NotNull
    public final ServerPlayer getPlayer() {
        return this.player;
    }

    public final int getRequiredCount() {
        return this.requiredCount;
    }

    public final int getHadCount() {
        return this.hadCount;
    }

    @Override
    @NotNull
    public MutableComponent getMessageFor(@NotNull Entity entity2) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        if (Intrinsics.areEqual((Object)this.player, (Object)entity2)) {
            String key = this.hadCount == 0 ? "no_pokemon" : "insufficient_pokemon.personal";
            Object[] objectArray = new Object[]{this.requiredCount, this.hadCount};
            MutableComponent mutableComponent2 = LocalizationUtilsKt.battleLang("error." + key, objectArray);
            mutableComponent = mutableComponent2;
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"{\n            val key = \u2026t\n            )\n        }");
        } else {
            Object[] objectArray = new Object[3];
            Intrinsics.checkNotNullExpressionValue((Object)this.player.m_5446_(), (String)"player.displayName");
            objectArray[1] = this.requiredCount;
            objectArray[2] = this.hadCount;
            MutableComponent mutableComponent3 = LocalizationUtilsKt.battleLang("error.insufficient_pokemon", objectArray);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent3, (String)"{\n            battleLang\u2026t\n            )\n        }");
            mutableComponent = mutableComponent3;
        }
        return mutableComponent;
    }
}

