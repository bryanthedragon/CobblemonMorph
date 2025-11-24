/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.components.Button$OnPress
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.GrabbedStorageSlot;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.StorageSlot;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.StorageWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientParty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.components.Button;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B7\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010\u0015\u001a\u00020\u0013\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0011\u0010\u0003\u001a\u0004\u0018\u00010\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0014\u0010\t\u001a\u00020\b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\nR\u0014\u0010\f\u001a\u00020\u000b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\rR\u0017\u0010\u000f\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/client/gui/pc/PartyStorageSlot;", "Lcom/cobblemon/mod/common/client/gui/pc/StorageSlot;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "", "shouldRender", "()Z", "Lcom/cobblemon/mod/common/client/gui/pc/StorageWidget;", "parent", "Lcom/cobblemon/mod/common/client/gui/pc/StorageWidget;", "Lcom/cobblemon/mod/common/client/storage/ClientParty;", "party", "Lcom/cobblemon/mod/common/client/storage/ClientParty;", "Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;", "position", "Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;", "getPosition", "()Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;", "", "x", "y", "Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;", "onPress", "<init>", "(IILcom/cobblemon/mod/common/client/gui/pc/StorageWidget;Lcom/cobblemon/mod/common/client/storage/ClientParty;Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;Lnet/minecraft/client/gui/components/Button$OnPress;)V", "common"})
public final class PartyStorageSlot
extends StorageSlot {
    @NotNull
    private final StorageWidget parent;
    @NotNull
    private final ClientParty party;
    @NotNull
    private final PartyPosition position;

    public PartyStorageSlot(int x, int y, @NotNull StorageWidget parent, @NotNull ClientParty party, @NotNull PartyPosition position, @NotNull Button.OnPress onPress) {
        Intrinsics.checkNotNullParameter((Object)((Object)parent), (String)"parent");
        Intrinsics.checkNotNullParameter((Object)party, (String)"party");
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        Intrinsics.checkNotNullParameter((Object)onPress, (String)"onPress");
        super(x, y, parent, onPress);
        this.parent = parent;
        this.party = party;
        this.position = position;
    }

    @NotNull
    public final PartyPosition getPosition() {
        return this.position;
    }

    @Override
    @Nullable
    public Pokemon getPokemon() {
        return this.party.get(this.position);
    }

    @Override
    public boolean shouldRender() {
        GrabbedStorageSlot grabbedSlot = this.parent.getGrabbedSlot();
        return grabbedSlot == null ? true : !Intrinsics.areEqual((Object)grabbedSlot.getPokemon(), (Object)this.getPokemon());
    }
}

