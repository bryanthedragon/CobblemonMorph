/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.jvm.internal.markers.KMappedMarker
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientStorage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010)\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003B\u0017\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u001e\u001a\u00020\f\u00a2\u0006\u0004\b\"\u0010#J\u0019\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0019\u0010\n\u001a\u0004\u0018\u00010\u00042\u0006\u0010\t\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\n\u001a\u0004\u0018\u00010\u00042\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\n\u0010\u000eJ\u0019\u0010\u0010\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u000f\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0015\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0010\u0010\u0013J\r\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0018\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0017H\u0096\u0002\u00a2\u0006\u0004\b\u0018\u0010\u0019J!\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\t\u001a\u00020\u00022\b\u0010\u000f\u001a\u0004\u0018\u00010\u0004H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u001cR\u001f\u0010\u001e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u001d8\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/client/storage/ClientParty;", "Lcom/cobblemon/mod/common/client/storage/ClientStorage;", "Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Ljava/util/UUID;", "uuid", "findByUUID", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "position", "get", "(Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "", "slot", "(I)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "getPosition", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;", "pokemonID", "(Ljava/util/UUID;)I", "", "isEmpty", "()Z", "", "iterator", "()Ljava/util/Iterator;", "", "set", "(Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "", "slots", "Ljava/util/List;", "getSlots", "()Ljava/util/List;", "<init>", "(Ljava/util/UUID;I)V", "common"})
@SourceDebugExtension(value={"SMAP\nClientParty.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClientParty.kt\ncom/cobblemon/mod/common/client/storage/ClientParty\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,47:1\n1#2:48\n350#3,7:49\n*S KotlinDebug\n*F\n+ 1 ClientParty.kt\ncom/cobblemon/mod/common/client/storage/ClientParty\n*L\n38#1:49,7\n*E\n"})
public final class ClientParty
extends ClientStorage<PartyPosition>
implements Iterable<Pokemon>,
KMappedMarker {
    @NotNull
    private final List<Pokemon> slots;

    public ClientParty(@NotNull UUID uuid2, int slots) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        super(uuid2);
        ClientParty clientParty = this;
        ArrayList<Object> arrayList = new ArrayList<Object>(slots);
        int n = 0;
        while (n < slots) {
            int n2;
            int n3 = n2 = n++;
            ArrayList<Object> arrayList2 = arrayList;
            boolean bl = false;
            arrayList2.add(null);
        }
        clientParty.slots = arrayList;
    }

    @NotNull
    public final List<Pokemon> getSlots() {
        return this.slots;
    }

    @Override
    @NotNull
    public Iterator<Pokemon> iterator() {
        return this.slots.iterator();
    }

    @Override
    @Nullable
    public Pokemon findByUUID(@NotNull UUID uuid2) {
        Object v1;
        block1: {
            Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
            Iterable iterable = this.slots;
            for (Object t : iterable) {
                Pokemon it = (Pokemon)t;
                boolean bl = false;
                Pokemon pokemon = it;
                if (!Intrinsics.areEqual((Object)(pokemon != null ? pokemon.getUuid() : null), (Object)uuid2)) continue;
                v1 = t;
                break block1;
            }
            v1 = null;
        }
        return v1;
    }

    @Override
    public void set(@NotNull PartyPosition position, @Nullable Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        if (position.getSlot() >= this.slots.size()) {
            return;
        }
        this.slots.set(position.getSlot(), pokemon);
    }

    @Override
    @Nullable
    public final Pokemon get(int slot) {
        return this.get(new PartyPosition(slot));
    }

    @Override
    @Nullable
    public Pokemon get(@NotNull PartyPosition position) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        if (position.getSlot() >= this.slots.size() || position.getSlot() == -1) {
            return null;
        }
        return this.slots.get(position.getSlot());
    }

    public final boolean isEmpty() {
        return this.slots.size() == 0;
    }

    public final int getPosition(@NotNull UUID pokemonID) {
        int n;
        block2: {
            Intrinsics.checkNotNullParameter((Object)pokemonID, (String)"pokemonID");
            List<Pokemon> $this$indexOfFirst$iv = this.slots;
            boolean $i$f$indexOfFirst = false;
            int index$iv = 0;
            Iterator<Pokemon> iterator = $this$indexOfFirst$iv.iterator();
            while (iterator.hasNext()) {
                Pokemon item$iv;
                Pokemon it = item$iv = iterator.next();
                boolean bl = false;
                Pokemon pokemon = it;
                if (Intrinsics.areEqual((Object)(pokemon != null ? pokemon.getUuid() : null), (Object)pokemonID)) {
                    n = index$iv;
                    break block2;
                }
                ++index$iv;
            }
            n = -1;
        }
        return n;
    }

    @Override
    @Nullable
    public PartyPosition getPosition(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        int n = this.slots.size();
        for (int slotNumber = 0; slotNumber < n; ++slotNumber) {
            if (!Intrinsics.areEqual((Object)this.slots.get(slotNumber), (Object)pokemon)) continue;
            return new PartyPosition(slotNumber);
        }
        return null;
    }
}

