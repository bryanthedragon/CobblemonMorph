/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PasturePermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SettableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUIConfiguration;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.OpenPasturePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pasture.PasturePokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0012\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR#\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\"\u0010\u0014\u001a\u00020\u00138\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/client/gui/pasture/PasturePCGUIConfiguration;", "Lcom/cobblemon/mod/common/client/gui/pc/PCGUIConfiguration;", "", "limit", "I", "getLimit", "()I", "Ljava/util/UUID;", "pastureId", "Ljava/util/UUID;", "getPastureId", "()Ljava/util/UUID;", "Lcom/cobblemon/mod/common/api/reactive/SettableObservable;", "", "Lcom/cobblemon/mod/common/net/messages/client/pasture/OpenPasturePacket$PasturePokemonDataDTO;", "pasturedPokemon", "Lcom/cobblemon/mod/common/api/reactive/SettableObservable;", "getPasturedPokemon", "()Lcom/cobblemon/mod/common/api/reactive/SettableObservable;", "Lcom/cobblemon/mod/common/api/pasture/PasturePermissions;", "permissions", "Lcom/cobblemon/mod/common/api/pasture/PasturePermissions;", "getPermissions", "()Lcom/cobblemon/mod/common/api/pasture/PasturePermissions;", "setPermissions", "(Lcom/cobblemon/mod/common/api/pasture/PasturePermissions;)V", "<init>", "(Ljava/util/UUID;ILcom/cobblemon/mod/common/api/reactive/SettableObservable;Lcom/cobblemon/mod/common/api/pasture/PasturePermissions;)V", "common"})
public final class PasturePCGUIConfiguration
extends PCGUIConfiguration {
    @NotNull
    private final UUID pastureId;
    private final int limit;
    @NotNull
    private final SettableObservable<List<OpenPasturePacket.PasturePokemonDataDTO>> pasturedPokemon;
    @NotNull
    private PasturePermissions permissions;

    public PasturePCGUIConfiguration(final @NotNull UUID pastureId, int limit, @NotNull SettableObservable<List<OpenPasturePacket.PasturePokemonDataDTO>> pasturedPokemon, final @NotNull PasturePermissions permissions) {
        Intrinsics.checkNotNullParameter((Object)pastureId, (String)"pastureId");
        Intrinsics.checkNotNullParameter(pasturedPokemon, (String)"pasturedPokemon");
        Intrinsics.checkNotNullParameter((Object)permissions, (String)"permissions");
        super((Function1<? super PCGUI, Unit>)((Function1)1.INSTANCE), (Function3<? super PCGUI, ? super StorePosition, ? super Pokemon, Unit>)((Function3)new Function3<PCGUI, StorePosition, Pokemon, Unit>(){

            public final void invoke(@NotNull PCGUI pcGui, @NotNull StorePosition position, @Nullable Pokemon pokemon) {
                Intrinsics.checkNotNullParameter((Object)((Object)pcGui), (String)"pcGui");
                Intrinsics.checkNotNullParameter((Object)position, (String)"position");
                if (pokemon != null && !pokemon.isFainted() && pokemon.getTetheringId() == null && permissions.getCanPasture()) {
                    UUID uUID = pokemon.getUuid();
                    Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"pokemon.uuid");
                    CobblemonNetwork.INSTANCE.sendToServer(new PasturePokemonPacket(uUID, pastureId));
                    pcGui.playSound(CobblemonSounds.PC_CLICK);
                }
            }
        }), false, (Function1<? super Pokemon, Boolean>)((Function1)3.INSTANCE));
        this.pastureId = pastureId;
        this.limit = limit;
        this.pasturedPokemon = pasturedPokemon;
        this.permissions = permissions;
    }

    @NotNull
    public final UUID getPastureId() {
        return this.pastureId;
    }

    public final int getLimit() {
        return this.limit;
    }

    @NotNull
    public final SettableObservable<List<OpenPasturePacket.PasturePokemonDataDTO>> getPasturedPokemon() {
        return this.pasturedPokemon;
    }

    @NotNull
    public final PasturePermissions getPermissions() {
        return this.permissions;
    }

    public final void setPermissions(@NotNull PasturePermissions pasturePermissions) {
        Intrinsics.checkNotNullParameter((Object)pasturePermissions, (String)"<set-?>");
        this.permissions = pasturePermissions;
    }
}

