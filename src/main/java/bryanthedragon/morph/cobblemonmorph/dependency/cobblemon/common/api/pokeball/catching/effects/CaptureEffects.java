/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.effects;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching.CaptureEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/api/pokeball/catching/effects/CaptureEffects;", "", "", "value", "Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureEffect;", "friendshipSetter", "(I)Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureEffect;", "FULL_RESTORE", "Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureEffect;", "getFULL_RESTORE", "()Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureEffect;", "<init>", "()V", "common"})
public final class CaptureEffects {
    @NotNull
    public static final CaptureEffects INSTANCE = new CaptureEffects();
    @NotNull
    private static final CaptureEffect FULL_RESTORE = CaptureEffects::FULL_RESTORE$lambda$0;

    private CaptureEffects() {
    }

    @NotNull
    public final CaptureEffect getFULL_RESTORE() {
        return FULL_RESTORE;
    }

    @NotNull
    public final CaptureEffect friendshipSetter(int value2) {
        return (arg_0, arg_1) -> CaptureEffects.friendshipSetter$lambda$1(value2, arg_0, arg_1);
    }

    private static final void FULL_RESTORE$lambda$0(LivingEntity livingEntity, Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)livingEntity, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        pokemon.heal();
    }

    private static final void friendshipSetter$lambda$1(int $value, LivingEntity livingEntity, Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)livingEntity, (String)"<anonymous parameter 0>");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Pokemon.setFriendship$default(pokemon, $value, false, 2, null);
    }
}

