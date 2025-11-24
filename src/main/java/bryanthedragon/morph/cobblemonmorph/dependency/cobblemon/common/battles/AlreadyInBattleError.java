/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleStartError;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/battles/AlreadyInBattleError;", "Lcom/cobblemon/mod/common/battles/BattleStartError;", "Lnet/minecraft/world/entity/Entity;", "entity", "Lnet/minecraft/network/chat/MutableComponent;", "getMessageFor", "(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/network/chat/MutableComponent;", "Ljava/util/UUID;", "actorUUID", "Ljava/util/UUID;", "getActorUUID", "()Ljava/util/UUID;", "Lnet/minecraft/network/chat/Component;", "name", "Lnet/minecraft/network/chat/Component;", "getName", "()Lnet/minecraft/network/chat/Component;", "<init>", "(Ljava/util/UUID;Lnet/minecraft/network/chat/Component;)V", "common"})
public final class AlreadyInBattleError
implements BattleStartError {
    @NotNull
    private final UUID actorUUID;
    @NotNull
    private final Component name;

    public AlreadyInBattleError(@NotNull UUID actorUUID, @NotNull Component name) {
        Intrinsics.checkNotNullParameter((Object)actorUUID, (String)"actorUUID");
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        this.actorUUID = actorUUID;
        this.name = name;
    }

    @NotNull
    public final UUID getActorUUID() {
        return this.actorUUID;
    }

    @NotNull
    public final Component getName() {
        return this.name;
    }

    @Override
    @NotNull
    public MutableComponent getMessageFor(@NotNull Entity entity2) {
        MutableComponent mutableComponent;
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        if (Intrinsics.areEqual((Object)this.actorUUID, (Object)entity2.m_20148_())) {
            MutableComponent mutableComponent2 = LocalizationUtilsKt.battleLang("error.in_battle.personal", new Object[0]);
            mutableComponent = mutableComponent2;
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"{\n            battleLang\u2026ttle.personal\")\n        }");
        } else {
            Object[] objectArray = new Object[]{this.name};
            MutableComponent mutableComponent3 = LocalizationUtilsKt.battleLang("error.in_battle", objectArray);
            mutableComponent = mutableComponent3;
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent3, (String)"{\n            battleLang\u2026_battle\", name)\n        }");
        }
        return mutableComponent;
    }
}

