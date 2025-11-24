/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleStartError;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\b\u001a\u00020\u0004\u00a2\u0006\u0004\b\f\u0010\rJ\u001f\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/BusyError;", "Lcom/cobblemon/mod/common/battles/BattleStartError;", "Lnet/minecraft/world/entity/Entity;", "entity", "Lnet/minecraft/network/chat/MutableComponent;", "kotlin.jvm.PlatformType", "getMessageFor", "(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/network/chat/MutableComponent;", "targetName", "Lnet/minecraft/network/chat/MutableComponent;", "getTargetName", "()Lnet/minecraft/network/chat/MutableComponent;", "<init>", "(Lnet/minecraft/network/chat/MutableComponent;)V", "common"})
public final class BusyError
implements BattleStartError {
    @NotNull
    private final MutableComponent targetName;

    public BusyError(@NotNull MutableComponent targetName) {
        Intrinsics.checkNotNullParameter((Object)targetName, (String)"targetName");
        this.targetName = targetName;
    }

    @NotNull
    public final MutableComponent getTargetName() {
        return this.targetName;
    }

    @Override
    public MutableComponent getMessageFor(@NotNull Entity entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Object[] objectArray = new Object[]{this.targetName};
        return LocalizationUtilsKt.battleLang("errors.busy", objectArray);
    }
}

