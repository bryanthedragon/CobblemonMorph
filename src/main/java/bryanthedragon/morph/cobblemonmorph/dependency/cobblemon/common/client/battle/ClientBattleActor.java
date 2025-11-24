/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0013\u001a\u00020\u0012\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010$\u001a\u00020#\u0012\u0006\u0010\u001f\u001a\u00020\u001e\u00a2\u0006\u0004\b(\u0010)R\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\t\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR(\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000e\u0010\u0005\u001a\u0004\b\u000f\u0010\u0007\"\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0013\u001a\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\"\u0010\u0018\u001a\u00020\u00178\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0017\u0010\u001f\u001a\u00020\u001e8\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"R\u0017\u0010$\u001a\u00020#8\u0006\u00a2\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'\u00a8\u0006*"}, d2={"Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;", "", "", "Lcom/cobblemon/mod/common/client/battle/ActiveClientBattlePokemon;", "activePokemon", "Ljava/util/List;", "getActivePokemon", "()Ljava/util/List;", "Lnet/minecraft/network/chat/MutableComponent;", "displayName", "Lnet/minecraft/network/chat/MutableComponent;", "getDisplayName", "()Lnet/minecraft/network/chat/MutableComponent;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "getPokemon", "setPokemon", "(Ljava/util/List;)V", "", "showdownId", "Ljava/lang/String;", "getShowdownId", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/client/battle/ClientBattleSide;", "side", "Lcom/cobblemon/mod/common/client/battle/ClientBattleSide;", "getSide", "()Lcom/cobblemon/mod/common/client/battle/ClientBattleSide;", "setSide", "(Lcom/cobblemon/mod/common/client/battle/ClientBattleSide;)V", "Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "type", "Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "getType", "()Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "Ljava/util/UUID;", "uuid", "Ljava/util/UUID;", "getUuid", "()Ljava/util/UUID;", "<init>", "(Ljava/lang/String;Lnet/minecraft/network/chat/MutableComponent;Ljava/util/UUID;Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;)V", "common"})
public final class ClientBattleActor {
    @NotNull
    private final String showdownId;
    @NotNull
    private final MutableComponent displayName;
    @NotNull
    private final UUID uuid;
    @NotNull
    private final ActorType type;
    public ClientBattleSide side;
    @NotNull
    private List<Pokemon> pokemon;
    @NotNull
    private final List<ActiveClientBattlePokemon> activePokemon;

    public ClientBattleActor(@NotNull String showdownId, @NotNull MutableComponent displayName, @NotNull UUID uuid2, @NotNull ActorType type) {
        Intrinsics.checkNotNullParameter((Object)showdownId, (String)"showdownId");
        Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        Intrinsics.checkNotNullParameter((Object)((Object)type), (String)"type");
        this.showdownId = showdownId;
        this.displayName = displayName;
        this.uuid = uuid2;
        this.type = type;
        this.pokemon = new ArrayList();
        this.activePokemon = new ArrayList();
    }

    @NotNull
    public final String getShowdownId() {
        return this.showdownId;
    }

    @NotNull
    public final MutableComponent getDisplayName() {
        return this.displayName;
    }

    @NotNull
    public final UUID getUuid() {
        return this.uuid;
    }

    @NotNull
    public final ActorType getType() {
        return this.type;
    }

    @NotNull
    public final ClientBattleSide getSide() {
        ClientBattleSide clientBattleSide = this.side;
        if (clientBattleSide != null) {
            return clientBattleSide;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"side");
        return null;
    }

    public final void setSide(@NotNull ClientBattleSide clientBattleSide) {
        Intrinsics.checkNotNullParameter((Object)clientBattleSide, (String)"<set-?>");
        this.side = clientBattleSide;
    }

    @NotNull
    public final List<Pokemon> getPokemon() {
        return this.pokemon;
    }

    public final void setPokemon(@NotNull List<Pokemon> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.pokemon = list;
    }

    @NotNull
    public final List<ActiveClientBattlePokemon> getActivePokemon() {
        return this.activePokemon;
    }
}

