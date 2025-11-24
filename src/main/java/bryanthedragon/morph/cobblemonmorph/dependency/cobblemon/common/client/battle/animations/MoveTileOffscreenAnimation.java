/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.ranges.RangesKt
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations.TileAnimation;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\u0018\u00002\u00020\u0001B\u0011\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0013\u0010\u0012J \u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0096\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\t\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\"\u0010\u000f\u001a\u00020\u00048\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\f\u001a\u0004\b\u0010\u0010\u000e\"\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/client/battle/animations/MoveTileOffscreenAnimation;", "Lcom/cobblemon/mod/common/client/battle/animations/TileAnimation;", "Lcom/cobblemon/mod/common/client/battle/ActiveClientBattlePokemon;", "activeBattlePokemon", "", "deltaTicks", "", "invoke", "(Lcom/cobblemon/mod/common/client/battle/ActiveClientBattlePokemon;F)Z", "shouldHoldUntilNextAnimation", "()Z", "duration", "F", "getDuration", "()F", "passedSeconds", "getPassedSeconds", "setPassedSeconds", "(F)V", "<init>", "common"})
public final class MoveTileOffscreenAnimation
implements TileAnimation {
    private final float duration;
    private float passedSeconds;

    public MoveTileOffscreenAnimation(float duration) {
        this.duration = duration;
    }

    public /* synthetic */ MoveTileOffscreenAnimation(float f, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            f = 0.75f;
        }
        this(f);
    }

    public final float getDuration() {
        return this.duration;
    }

    public final float getPassedSeconds() {
        return this.passedSeconds;
    }

    public final void setPassedSeconds(float f) {
        this.passedSeconds = f;
    }

    @Override
    public boolean shouldHoldUntilNextAnimation() {
        return true;
    }

    @Override
    public boolean invoke(@NotNull ActiveClientBattlePokemon activeBattlePokemon, float deltaTicks) {
        Intrinsics.checkNotNullParameter((Object)activeBattlePokemon, (String)"activeBattlePokemon");
        this.passedSeconds += deltaTicks / (float)20;
        this.passedSeconds = RangesKt.coerceAtMost((float)this.passedSeconds, (float)this.duration);
        float ratio = this.passedSeconds / this.duration;
        float totalMovement = activeBattlePokemon.getInvisibleX() - activeBattlePokemon.getXDisplacement();
        float currentMovement = totalMovement * ratio;
        activeBattlePokemon.setXDisplacement(activeBattlePokemon.getXDisplacement() + currentMovement);
        return this.passedSeconds == this.duration;
    }

    public MoveTileOffscreenAnimation() {
        this(0.0f, 1, null);
    }
}

