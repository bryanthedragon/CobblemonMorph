/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectPokemonDTO;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B^\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0018\u0012\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012\u0012\u0014\b\u0002\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002\u0012'\u0010\u000e\u001a#\u0012\u0004\u0012\u00020\u0003\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00040\t\u00a2\u0006\u0004\b\u001d\u0010\u001eR#\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\bR8\u0010\u000e\u001a#\u0012\u0004\u0012\u00020\u0003\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00040\t8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001d\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0019\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001c\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/api/callback/PartySelectCallback;", "", "Lkotlin/Function1;", "Lnet/minecraft/server/level/ServerPlayer;", "", "cancel", "Lkotlin/jvm/functions/Function1;", "getCancel", "()Lkotlin/jvm/functions/Function1;", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "index", "handler", "Lkotlin/jvm/functions/Function2;", "getHandler", "()Lkotlin/jvm/functions/Function2;", "", "Lcom/cobblemon/mod/common/api/callback/PartySelectPokemonDTO;", "shownPokemon", "Ljava/util/List;", "getShownPokemon", "()Ljava/util/List;", "Ljava/util/UUID;", "uuid", "Ljava/util/UUID;", "getUuid", "()Ljava/util/UUID;", "<init>", "(Ljava/util/UUID;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)V", "common"})
public final class PartySelectCallback {
    @NotNull
    private final UUID uuid;
    @NotNull
    private final List<PartySelectPokemonDTO> shownPokemon;
    @NotNull
    private final Function1<ServerPlayer, Unit> cancel;
    @NotNull
    private final Function2<ServerPlayer, Integer, Unit> handler;

    public PartySelectCallback(@NotNull UUID uuid2, @NotNull List<? extends PartySelectPokemonDTO> shownPokemon, @NotNull Function1<? super ServerPlayer, Unit> cancel2, @NotNull Function2<? super ServerPlayer, ? super Integer, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        Intrinsics.checkNotNullParameter(shownPokemon, (String)"shownPokemon");
        Intrinsics.checkNotNullParameter(cancel2, (String)"cancel");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        this.uuid = uuid2;
        this.shownPokemon = shownPokemon;
        this.cancel = cancel2;
        this.handler = handler;
    }

    public /* synthetic */ PartySelectCallback(UUID uUID, List list, Function1 function1, Function2 function2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            UUID uUID2 = UUID.randomUUID();
            Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"randomUUID()");
            uUID = uUID2;
        }
        if ((n & 4) != 0) {
            function1 = 1.INSTANCE;
        }
        this(uUID, list, (Function1<? super ServerPlayer, Unit>)function1, (Function2<? super ServerPlayer, ? super Integer, Unit>)function2);
    }

    @NotNull
    public final UUID getUuid() {
        return this.uuid;
    }

    @NotNull
    public final List<PartySelectPokemonDTO> getShownPokemon() {
        return this.shownPokemon;
    }

    @NotNull
    public final Function1<ServerPlayer, Unit> getCancel() {
        return this.cancel;
    }

    @NotNull
    public final Function2<ServerPlayer, Integer, Unit> getHandler() {
        return this.handler;
    }
}

