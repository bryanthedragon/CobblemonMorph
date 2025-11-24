/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionRequest;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleActor;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 !2\u00020\u0001:\u0001!B3\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u001b\u001a\u0004\u0018\u00010\u001a\u0012\b\u0010\u000f\u001a\u0004\u0018\u00010\u000e\u0012\u0006\u0010\f\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\u001f\u0010 R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\t\u001a\u0004\b\r\u0010\u000bR\u0019\u0010\u000f\u001a\u0004\u0018\u00010\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R$\u0010\u0014\u001a\u0004\u0018\u00010\u00138\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0019\u0010\u001b\u001a\u0004\u0018\u00010\u001a8\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/client/battle/SingleActionRequest;", "", "Lcom/cobblemon/mod/common/client/battle/ActiveClientBattlePokemon;", "activePokemon", "Lcom/cobblemon/mod/common/client/battle/ActiveClientBattlePokemon;", "getActivePokemon", "()Lcom/cobblemon/mod/common/client/battle/ActiveClientBattlePokemon;", "", "canCancel", "Z", "getCanCancel", "()Z", "forceSwitch", "getForceSwitch", "Lcom/cobblemon/mod/common/battles/ShowdownMoveset;", "moveSet", "Lcom/cobblemon/mod/common/battles/ShowdownMoveset;", "getMoveSet", "()Lcom/cobblemon/mod/common/battles/ShowdownMoveset;", "Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "response", "Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "getResponse", "()Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "setResponse", "(Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;)V", "Lcom/cobblemon/mod/common/battles/ShowdownSide;", "side", "Lcom/cobblemon/mod/common/battles/ShowdownSide;", "getSide", "()Lcom/cobblemon/mod/common/battles/ShowdownSide;", "<init>", "(Lcom/cobblemon/mod/common/client/battle/ActiveClientBattlePokemon;Lcom/cobblemon/mod/common/battles/ShowdownSide;Lcom/cobblemon/mod/common/battles/ShowdownMoveset;ZZ)V", "Companion", "common"})
public final class SingleActionRequest {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ActiveClientBattlePokemon activePokemon;
    @Nullable
    private final ShowdownSide side;
    @Nullable
    private final ShowdownMoveset moveSet;
    private final boolean forceSwitch;
    private final boolean canCancel;
    @Nullable
    private ShowdownActionResponse response;

    public SingleActionRequest(@NotNull ActiveClientBattlePokemon activePokemon, @Nullable ShowdownSide side, @Nullable ShowdownMoveset moveSet, boolean forceSwitch, boolean canCancel) {
        Intrinsics.checkNotNullParameter((Object)activePokemon, (String)"activePokemon");
        this.activePokemon = activePokemon;
        this.side = side;
        this.moveSet = moveSet;
        this.forceSwitch = forceSwitch;
        this.canCancel = canCancel;
    }

    @NotNull
    public final ActiveClientBattlePokemon getActivePokemon() {
        return this.activePokemon;
    }

    @Nullable
    public final ShowdownSide getSide() {
        return this.side;
    }

    @Nullable
    public final ShowdownMoveset getMoveSet() {
        return this.moveSet;
    }

    public final boolean getForceSwitch() {
        return this.forceSwitch;
    }

    public final boolean getCanCancel() {
        return this.canCancel;
    }

    @Nullable
    public final ShowdownActionResponse getResponse() {
        return this.response;
    }

    public final void setResponse(@Nullable ShowdownActionResponse showdownActionResponse) {
        this.response = showdownActionResponse;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bJ#\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/client/battle/SingleActionRequest$Companion;", "", "Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;", "actor", "Lcom/cobblemon/mod/common/battles/ShowdownActionRequest;", "request", "", "Lcom/cobblemon/mod/common/client/battle/SingleActionRequest;", "composeFrom", "(Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;Lcom/cobblemon/mod/common/battles/ShowdownActionRequest;)Ljava/util/List;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final List<SingleActionRequest> composeFrom(@NotNull ClientBattleActor actor, @NotNull ShowdownActionRequest request) {
            Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
            Intrinsics.checkNotNullParameter((Object)request, (String)"request");
            List singleActionRequests = new ArrayList();
            singleActionRequests.addAll(request.iterate(actor.getActivePokemon(), (Function3)new Function3<ActiveClientBattlePokemon, ShowdownMoveset, Boolean, SingleActionRequest>(request){
                final /* synthetic */ ShowdownActionRequest $request;
                {
                    this.$request = $request;
                    super(3);
                }

                @NotNull
                public final SingleActionRequest invoke(@NotNull ActiveClientBattlePokemon targetable, @Nullable ShowdownMoveset moveSet, boolean forceSwitch) {
                    Intrinsics.checkNotNullParameter((Object)targetable, (String)"targetable");
                    return new SingleActionRequest(targetable, this.$request.getSide(), moveSet, forceSwitch, !this.$request.getNoCancel());
                }
            }));
            return singleActionRequests;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

