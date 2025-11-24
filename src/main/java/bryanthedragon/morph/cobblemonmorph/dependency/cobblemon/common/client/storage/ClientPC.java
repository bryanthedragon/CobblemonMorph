/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientBox;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientStorage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0018\u001a\u00020\u0017\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0019\u0010\u0006\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0019\u0010\t\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0019\u0010\f\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u000b\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\f\u0010\rJ!\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\u00022\b\u0010\u000b\u001a\u0004\u0018\u00010\u0005H\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/client/storage/ClientPC;", "Lcom/cobblemon/mod/common/client/storage/ClientStorage;", "Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;", "Ljava/util/UUID;", "uuid", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "findByUUID", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "position", "get", "(Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "getPosition", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;", "", "set", "(Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "", "Lcom/cobblemon/mod/common/client/storage/ClientBox;", "boxes", "Ljava/util/List;", "getBoxes", "()Ljava/util/List;", "", "boxCount", "<init>", "(Ljava/util/UUID;I)V", "common"})
@SourceDebugExtension(value={"SMAP\nClientPC.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClientPC.kt\ncom/cobblemon/mod/common/client/storage/ClientPC\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,55:1\n1#2:56\n1855#3:57\n1855#3,2:58\n1856#3:60\n*S KotlinDebug\n*F\n+ 1 ClientPC.kt\ncom/cobblemon/mod/common/client/storage/ClientPC\n*L\n18#1:57\n19#1:58,2\n18#1:60\n*E\n"})
public final class ClientPC
extends ClientStorage<PCPosition> {
    @NotNull
    private final List<ClientBox> boxes;

    public ClientPC(@NotNull UUID uuid2, int boxCount) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        super(uuid2);
        ClientPC clientPC = this;
        ArrayList<ClientBox> arrayList = new ArrayList<ClientBox>(boxCount);
        int n = 0;
        while (n < boxCount) {
            int n2;
            int n3 = n2 = n++;
            ArrayList<ClientBox> arrayList2 = arrayList;
            boolean bl = false;
            arrayList2.add(new ClientBox());
        }
        clientPC.boxes = arrayList;
    }

    @NotNull
    public final List<ClientBox> getBoxes() {
        return this.boxes;
    }

    @Override
    @Nullable
    public Pokemon findByUUID(@NotNull UUID uuid2) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        Iterable $this$forEach$iv = this.boxes;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ClientBox it = (ClientBox)element$iv;
            boolean bl = false;
            Iterable $this$forEach$iv2 = it;
            boolean $i$f$forEach2 = false;
            for (Object element$iv2 : $this$forEach$iv2) {
                Pokemon it2 = (Pokemon)element$iv2;
                boolean bl2 = false;
                if (it2 == null || !Intrinsics.areEqual((Object)it2.getUuid(), (Object)uuid2)) continue;
                return it2;
            }
        }
        return null;
    }

    @Override
    public void set(@NotNull PCPosition position, @Nullable Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        if (this.boxes.size() <= position.getBox()) {
            return;
        }
        ClientBox box = this.boxes.get(position.getBox());
        if (position.getSlot() >= 30) {
            return;
        }
        box.getSlots().set(position.getSlot(), pokemon);
    }

    @Override
    @Nullable
    public Pokemon get(@NotNull PCPosition position) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        if (position.getSlot() >= 30 || position.getBox() >= this.boxes.size()) {
            return null;
        }
        return this.boxes.get(position.getBox()).getSlots().get(position.getSlot());
    }

    @Override
    @Nullable
    public PCPosition getPosition(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        int n = this.boxes.size();
        for (int boxNumber = 0; boxNumber < n; ++boxNumber) {
            ClientBox box = this.boxes.get(boxNumber);
            int n2 = box.getSlots().size();
            for (int slotNumber = 0; slotNumber < n2; ++slotNumber) {
                if (!Intrinsics.areEqual((Object)box.getSlots().get(slotNumber), (Object)pokemon)) continue;
                return new PCPosition(boxNumber, slotNumber);
            }
        }
        return null;
    }
}

