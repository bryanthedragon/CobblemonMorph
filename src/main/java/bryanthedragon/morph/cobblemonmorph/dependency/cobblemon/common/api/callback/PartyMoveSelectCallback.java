/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function5
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectPokemonDTO;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0091\u0001\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u001b\u0012\u001e\u0010\u0017\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00150\u00160\u0015\u0012\u0014\b\u0002\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002\u0012H\u0010\u0011\u001aD\u0012\u0004\u0012\u00020\u0003\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u000e\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00040\t\u00a2\u0006\u0004\b \u0010!R#\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\bRY\u0010\u0011\u001aD\u0012\u0004\u0012\u00020\u0003\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u000e\u0012\u0013\u0012\u00110\n\u00a2\u0006\f\b\u000b\u0012\b\b\f\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00040\t8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R/\u0010\u0017\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00150\u00160\u00158\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u0017\u0010\u001c\u001a\u00020\u001b8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/api/callback/PartyMoveSelectCallback;", "", "Lkotlin/Function1;", "Lnet/minecraft/server/level/ServerPlayer;", "", "cancel", "Lkotlin/jvm/functions/Function1;", "getCancel", "()Lkotlin/jvm/functions/Function1;", "Lkotlin/Function5;", "", "Lkotlin/ParameterName;", "name", "pokemonIndex", "Lcom/cobblemon/mod/common/api/callback/PartySelectPokemonDTO;", "moveIndex", "Lcom/cobblemon/mod/common/api/callback/MoveSelectDTO;", "handler", "Lkotlin/jvm/functions/Function5;", "getHandler", "()Lkotlin/jvm/functions/Function5;", "", "Lkotlin/Pair;", "pokemon", "Ljava/util/List;", "getPokemon", "()Ljava/util/List;", "Ljava/util/UUID;", "uuid", "Ljava/util/UUID;", "getUuid", "()Ljava/util/UUID;", "<init>", "(Ljava/util/UUID;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function5;)V", "common"})
public final class PartyMoveSelectCallback {
    @NotNull
    private final UUID uuid;
    @NotNull
    private final List<Pair<PartySelectPokemonDTO, List<MoveSelectDTO>>> pokemon;
    @NotNull
    private final Function1<ServerPlayer, Unit> cancel;
    @NotNull
    private final Function5<ServerPlayer, Integer, PartySelectPokemonDTO, Integer, MoveSelectDTO, Unit> handler;

    public PartyMoveSelectCallback(@NotNull UUID uuid2, @NotNull List<? extends Pair<? extends PartySelectPokemonDTO, ? extends List<MoveSelectDTO>>> pokemon, @NotNull Function1<? super ServerPlayer, Unit> cancel2, @NotNull Function5<? super ServerPlayer, ? super Integer, ? super PartySelectPokemonDTO, ? super Integer, ? super MoveSelectDTO, Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(cancel2, (String)"cancel");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        this.uuid = uuid2;
        this.pokemon = pokemon;
        this.cancel = cancel2;
        this.handler = handler;
    }

    public /* synthetic */ PartyMoveSelectCallback(UUID uUID, List list, Function1 function1, Function5 function5, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            UUID uUID2 = UUID.randomUUID();
            Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"randomUUID()");
            uUID = uUID2;
        }
        if ((n & 4) != 0) {
            function1 = 1.INSTANCE;
        }
        this(uUID, list, (Function1<? super ServerPlayer, Unit>)function1, (Function5<? super ServerPlayer, ? super Integer, ? super PartySelectPokemonDTO, ? super Integer, ? super MoveSelectDTO, Unit>)function5);
    }

    @NotNull
    public final UUID getUuid() {
        return this.uuid;
    }

    @NotNull
    public final List<Pair<PartySelectPokemonDTO, List<MoveSelectDTO>>> getPokemon() {
        return this.pokemon;
    }

    @NotNull
    public final Function1<ServerPlayer, Unit> getCancel() {
        return this.cancel;
    }

    @NotNull
    public final Function5<ServerPlayer, Integer, PartySelectPokemonDTO, Integer, MoveSelectDTO, Unit> getHandler() {
        return this.handler;
    }
}

