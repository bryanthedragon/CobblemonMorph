/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleEvent;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\b\u0018\u00002\u00020\u00012\u00020\u0002B\u001b\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0010\u0010\u0004\u001a\u00020\u0003H\u00c6\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003\u00a2\u0006\u0004\b\u0007\u0010\bJ&\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\t\u001a\u00020\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0006H\u00c6\u0001\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001a\u0010\u0010\u001a\u00020\u000f2\b\u0010\u000e\u001a\u0004\u0018\u00010\rH\u00d6\u0003\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0010\u0010\u0013\u001a\u00020\u0012H\u00d6\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0016\u001a\u00020\u0015H\u00d6\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017R\u001a\u0010\t\u001a\u00020\u00038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\t\u0010\u0018\u001a\u0004\b\u0019\u0010\u0005R$\u0010\n\u001a\u0004\u0018\u00010\u00068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u001a\u001a\u0004\b\u001b\u0010\b\"\u0004\b\u001c\u0010\u001d\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/api/events/battles/BattleStartedPreEvent;", "Lcom/cobblemon/mod/common/api/events/battles/BattleEvent;", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "component1", "()Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "Lnet/minecraft/network/chat/MutableComponent;", "component2", "()Lnet/minecraft/network/chat/MutableComponent;", "battle", "reason", "copy", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lnet/minecraft/network/chat/MutableComponent;)Lcom/cobblemon/mod/common/api/events/battles/BattleStartedPreEvent;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "getBattle", "Lnet/minecraft/network/chat/MutableComponent;", "getReason", "setReason", "(Lnet/minecraft/network/chat/MutableComponent;)V", "<init>", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lnet/minecraft/network/chat/MutableComponent;)V", "common"})
public final class BattleStartedPreEvent
extends Cancelable
implements BattleEvent {
    @NotNull
    private final PokemonBattle battle;
    @Nullable
    private MutableComponent reason;

    public BattleStartedPreEvent(@NotNull PokemonBattle battle2, @Nullable MutableComponent reason) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        this.battle = battle2;
        this.reason = reason;
    }

    public /* synthetic */ BattleStartedPreEvent(PokemonBattle pokemonBattle, MutableComponent mutableComponent, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            mutableComponent = null;
        }
        this(pokemonBattle, mutableComponent);
    }

    @Override
    @NotNull
    public PokemonBattle getBattle() {
        return this.battle;
    }

    @Nullable
    public final MutableComponent getReason() {
        return this.reason;
    }

    public final void setReason(@Nullable MutableComponent mutableComponent) {
        this.reason = mutableComponent;
    }

    @NotNull
    public final PokemonBattle component1() {
        return this.battle;
    }

    @Nullable
    public final MutableComponent component2() {
        return this.reason;
    }

    @NotNull
    public final BattleStartedPreEvent copy(@NotNull PokemonBattle battle2, @Nullable MutableComponent reason) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        return new BattleStartedPreEvent(battle2, reason);
    }

    public static /* synthetic */ BattleStartedPreEvent copy$default(BattleStartedPreEvent battleStartedPreEvent, PokemonBattle pokemonBattle, MutableComponent mutableComponent, int n, Object object) {
        if ((n & 1) != 0) {
            pokemonBattle = battleStartedPreEvent.battle;
        }
        if ((n & 2) != 0) {
            mutableComponent = battleStartedPreEvent.reason;
        }
        return battleStartedPreEvent.copy(pokemonBattle, mutableComponent);
    }

    @NotNull
    public String toString() {
        return "BattleStartedPreEvent(battle=" + this.battle + ", reason=" + this.reason + ")";
    }

    public int hashCode() {
        int result = this.battle.hashCode();
        result = result * 31 + (this.reason == null ? 0 : this.reason.hashCode());
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BattleStartedPreEvent)) {
            return false;
        }
        BattleStartedPreEvent battleStartedPreEvent = (BattleStartedPreEvent)other;
        if (!Intrinsics.areEqual((Object)this.battle, (Object)battleStartedPreEvent.battle)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.reason, (Object)battleStartedPreEvent.reason);
    }
}

