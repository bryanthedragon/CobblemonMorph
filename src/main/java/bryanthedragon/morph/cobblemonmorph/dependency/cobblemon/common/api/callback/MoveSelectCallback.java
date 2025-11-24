/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectDTO;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001BU\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0015\u0012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0010\u0012\u0014\b\u0002\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002\u0012\u001e\u0010\f\u001a\u001a\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00040\t\u00a2\u0006\u0004\b\u001a\u0010\u001bR#\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\bR/\u0010\f\u001a\u001a\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00040\t8\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0016\u001a\u00020\u00158\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/api/callback/MoveSelectCallback;", "", "Lkotlin/Function1;", "Lnet/minecraft/server/level/ServerPlayer;", "", "cancel", "Lkotlin/jvm/functions/Function1;", "getCancel", "()Lkotlin/jvm/functions/Function1;", "Lkotlin/Function3;", "", "Lcom/cobblemon/mod/common/api/callback/MoveSelectDTO;", "handler", "Lkotlin/jvm/functions/Function3;", "getHandler", "()Lkotlin/jvm/functions/Function3;", "", "shownMoves", "Ljava/util/List;", "getShownMoves", "()Ljava/util/List;", "Ljava/util/UUID;", "uuid", "Ljava/util/UUID;", "getUuid", "()Ljava/util/UUID;", "<init>", "(Ljava/util/UUID;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function3;)V", "common"})
public final class MoveSelectCallback {
    @NotNull
    private final UUID uuid;
    @NotNull
    private final List<MoveSelectDTO> shownMoves;
    @NotNull
    private final Function1<ServerPlayer, Unit> cancel;
    @NotNull
    private final Function3<ServerPlayer, Integer, MoveSelectDTO, Unit> handler;

    public MoveSelectCallback(@NotNull UUID uuid2, @NotNull List<MoveSelectDTO> shownMoves, @NotNull Function1<? super ServerPlayer, Unit> cancel2, @NotNull Function3<? super ServerPlayer, ? super Integer, ? super MoveSelectDTO, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        Intrinsics.checkNotNullParameter(shownMoves, (String)"shownMoves");
        Intrinsics.checkNotNullParameter(cancel2, (String)"cancel");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        this.uuid = uuid2;
        this.shownMoves = shownMoves;
        this.cancel = cancel2;
        this.handler = handler;
    }

    public /* synthetic */ MoveSelectCallback(UUID uUID, List list, Function1 function1, Function3 function3, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            UUID uUID2 = UUID.randomUUID();
            Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"randomUUID()");
            uUID = uUID2;
        }
        if ((n & 4) != 0) {
            function1 = 1.INSTANCE;
        }
        this(uUID, list, (Function1<? super ServerPlayer, Unit>)function1, (Function3<? super ServerPlayer, ? super Integer, ? super MoveSelectDTO, Unit>)function3);
    }

    @NotNull
    public final UUID getUuid() {
        return this.uuid;
    }

    @NotNull
    public final List<MoveSelectDTO> getShownMoves() {
        return this.shownMoves;
    }

    @NotNull
    public final Function1<ServerPlayer, Unit> getCancel() {
        return this.cancel;
    }

    @NotNull
    public final Function3<ServerPlayer, Integer, MoveSelectDTO, Unit> getHandler() {
        return this.handler;
    }
}

