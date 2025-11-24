/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.jvm.internal.markers.KMappedMarker
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0010)\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0007\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0018\u0010\u0004\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0003H\u0096\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005R\u001f\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/client/storage/ClientBox;", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "", "iterator", "()Ljava/util/Iterator;", "", "slots", "Ljava/util/List;", "getSlots", "()Ljava/util/List;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nClientBox.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClientBox.kt\ncom/cobblemon/mod/common/client/storage/ClientBox\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,16:1\n1#2:17\n*E\n"})
public final class ClientBox
implements Iterable<Pokemon>,
KMappedMarker {
    @NotNull
    private final List<Pokemon> slots;

    public ClientBox() {
        int n = 30;
        ClientBox clientBox = this;
        ArrayList<Object> arrayList = new ArrayList<Object>(n);
        int n2 = 0;
        while (n2 < n) {
            int n3;
            int n4 = n3 = n2++;
            ArrayList<Object> arrayList2 = arrayList;
            boolean bl = false;
            arrayList2.add(null);
        }
        clientBox.slots = arrayList;
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
}

