/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Unit
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b$\u0010%R\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR(\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R4\u0010\u0013\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00120\u00110\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\f\u001a\u0004\b\u0014\u0010\u000e\"\u0004\b\u0015\u0010\u0010R*\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00170\u00168\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR(\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00020\u00168\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u0019\u001a\u0004\b\u001f\u0010\u001b\"\u0004\b \u0010\u001dR*\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00170\u00168\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b!\u0010\u0019\u001a\u0004\b\"\u0010\u001b\"\u0004\b#\u0010\u001d\u00a8\u0006&"}, d2={"Lcom/cobblemon/mod/common/client/trade/ClientTrade;", "", "", "acceptedOppositeOffer", "Z", "getAcceptedOppositeOffer", "()Z", "setAcceptedOppositeOffer", "(Z)V", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "", "cancelEmitter", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getCancelEmitter", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "setCancelEmitter", "(Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;)V", "Lkotlin/Pair;", "Ljava/util/UUID;", "completedEmitter", "getCompletedEmitter", "setCompletedEmitter", "Lcom/cobblemon/mod/common/api/reactive/SettableObservable;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "myOffer", "Lcom/cobblemon/mod/common/api/reactive/SettableObservable;", "getMyOffer", "()Lcom/cobblemon/mod/common/api/reactive/SettableObservable;", "setMyOffer", "(Lcom/cobblemon/mod/common/api/reactive/SettableObservable;)V", "oppositeAcceptedMyOffer", "getOppositeAcceptedMyOffer", "setOppositeAcceptedMyOffer", "oppositeOffer", "getOppositeOffer", "setOppositeOffer", "<init>", "()V", "common"})
public final class ClientTrade {
    @NotNull
    private SettableObservable<Pokemon> myOffer = new SettableObservable<Object>(null);
    @NotNull
    private SettableObservable<Pokemon> oppositeOffer = new SettableObservable<Object>(null);
    @NotNull
    private SettableObservable<Boolean> oppositeAcceptedMyOffer = new SettableObservable<Boolean>(false);
    private boolean acceptedOppositeOffer;
    @NotNull
    private SimpleObservable<Pair<UUID, UUID>> completedEmitter = new SimpleObservable();
    @NotNull
    private SimpleObservable<Unit> cancelEmitter = new SimpleObservable();

    @NotNull
    public final SettableObservable<Pokemon> getMyOffer() {
        return this.myOffer;
    }

    public final void setMyOffer(@NotNull SettableObservable<Pokemon> settableObservable) {
        Intrinsics.checkNotNullParameter(settableObservable, (String)"<set-?>");
        this.myOffer = settableObservable;
    }

    @NotNull
    public final SettableObservable<Pokemon> getOppositeOffer() {
        return this.oppositeOffer;
    }

    public final void setOppositeOffer(@NotNull SettableObservable<Pokemon> settableObservable) {
        Intrinsics.checkNotNullParameter(settableObservable, (String)"<set-?>");
        this.oppositeOffer = settableObservable;
    }

    @NotNull
    public final SettableObservable<Boolean> getOppositeAcceptedMyOffer() {
        return this.oppositeAcceptedMyOffer;
    }

    public final void setOppositeAcceptedMyOffer(@NotNull SettableObservable<Boolean> settableObservable) {
        Intrinsics.checkNotNullParameter(settableObservable, (String)"<set-?>");
        this.oppositeAcceptedMyOffer = settableObservable;
    }

    public final boolean getAcceptedOppositeOffer() {
        return this.acceptedOppositeOffer;
    }

    public final void setAcceptedOppositeOffer(boolean bl) {
        this.acceptedOppositeOffer = bl;
    }

    @NotNull
    public final SimpleObservable<Pair<UUID, UUID>> getCompletedEmitter() {
        return this.completedEmitter;
    }

    public final void setCompletedEmitter(@NotNull SimpleObservable<Pair<UUID, UUID>> simpleObservable) {
        Intrinsics.checkNotNullParameter(simpleObservable, (String)"<set-?>");
        this.completedEmitter = simpleObservable;
    }

    @NotNull
    public final SimpleObservable<Unit> getCancelEmitter() {
        return this.cancelEmitter;
    }

    public final void setCancelEmitter(@NotNull SimpleObservable<Unit> simpleObservable) {
        Intrinsics.checkNotNullParameter(simpleObservable, (String)"<set-?>");
        this.cancelEmitter = simpleObservable;
    }
}

