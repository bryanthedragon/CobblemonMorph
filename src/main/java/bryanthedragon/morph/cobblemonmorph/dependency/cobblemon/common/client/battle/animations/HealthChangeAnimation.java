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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.animations.TileAnimation;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u000f\u001a\u00020\u0004\u0012\b\b\u0002\u0010\r\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0012\u0010\u0013J \u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0096\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\t\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nR\u0016\u0010\u000b\u001a\u00020\u00048\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u00048\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\fR\u0016\u0010\u000e\u001a\u00020\u00048\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\fR\u0014\u0010\u000f\u001a\u00020\u00048\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\fR\u0016\u0010\u0010\u001a\u00020\u00048\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\fR\u0016\u0010\u0011\u001a\u00020\u00048\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\f\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/client/battle/animations/HealthChangeAnimation;", "Lcom/cobblemon/mod/common/client/battle/animations/TileAnimation;", "Lcom/cobblemon/mod/common/client/battle/ActiveClientBattlePokemon;", "activeBattlePokemon", "", "deltaTicks", "", "invoke", "(Lcom/cobblemon/mod/common/client/battle/ActiveClientBattlePokemon;F)Z", "shouldHoldUntilNextAnimation", "()Z", "coercedNewHealth", "F", "duration", "initialHealthRatio", "newHealth", "passedSeconds", "ratioDifference", "<init>", "(FF)V", "common"})
public final class HealthChangeAnimation
implements TileAnimation {
    private final float newHealth;
    private final float duration;
    private float passedSeconds;
    private float initialHealthRatio;
    private float ratioDifference;
    private float coercedNewHealth;

    public HealthChangeAnimation(float newHealth, float duration) {
        this.newHealth = newHealth;
        this.duration = duration;
        this.initialHealthRatio = -1.0f;
        this.coercedNewHealth = -1.0f;
    }

    public /* synthetic */ HealthChangeAnimation(float f, float f2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            f2 = 1.0f;
        }
        this(f, f2);
    }

    @Override
    public boolean shouldHoldUntilNextAnimation() {
        return false;
    }

    @Override
    public boolean invoke(@NotNull ActiveClientBattlePokemon activeBattlePokemon, float deltaTicks) {
        Intrinsics.checkNotNullParameter((Object)activeBattlePokemon, (String)"activeBattlePokemon");
        ClientBattlePokemon clientBattlePokemon = activeBattlePokemon.getBattlePokemon();
        if (clientBattlePokemon == null) {
            return true;
        }
        ClientBattlePokemon pokemon = clientBattlePokemon;
        if (this.coercedNewHealth == -1.0f) {
            this.coercedNewHealth = !pokemon.isHpFlat() ? RangesKt.coerceAtMost((float)this.newHealth, (float)1.0f) : this.newHealth;
        }
        if (this.initialHealthRatio == -1.0f) {
            this.initialHealthRatio = pokemon.getHpValue();
            this.ratioDifference = this.coercedNewHealth - this.initialHealthRatio;
        }
        this.passedSeconds += deltaTicks / (float)20;
        this.passedSeconds = RangesKt.coerceAtMost((float)this.passedSeconds, (float)this.duration);
        float progress2 = this.passedSeconds / this.duration;
        pokemon.setHpValue(this.initialHealthRatio + progress2 * this.ratioDifference);
        return this.passedSeconds == this.duration;
    }
}

