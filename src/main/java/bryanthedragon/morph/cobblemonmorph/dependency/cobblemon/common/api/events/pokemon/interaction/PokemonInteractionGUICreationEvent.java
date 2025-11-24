/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Multimap
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.interaction;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel.InteractWheelOption;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel.Orientation;
import com.google.common.collect.Multimap;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\b\u0018\u00002\u00020\u0001B+\u0012\u0006\u0010\u0014\u001a\u00020\u000b\u0012\u0006\u0010\u0015\u001a\u00020\u000e\u0012\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00020\u0011\u00a2\u0006\u0004\b*\u0010+J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001d\u0010\t\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u0010\u0010\f\u001a\u00020\u000bH\u00c6\u0003\u00a2\u0006\u0004\b\f\u0010\rJ\u0010\u0010\u000f\u001a\u00020\u000eH\u00c6\u0003\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u001c\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00020\u0011H\u00c6\u0003\u00a2\u0006\u0004\b\u0012\u0010\u0013J:\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0014\u001a\u00020\u000b2\b\b\u0002\u0010\u0015\u001a\u00020\u000e2\u0014\b\u0002\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00020\u0011H\u00c6\u0001\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u001a\u0010\u001a\u001a\u00020\u000e2\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u000f\u0010\u001c\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0010\u0010\u001f\u001a\u00020\u001eH\u00d6\u0001\u00a2\u0006\u0004\b\u001f\u0010 J\u0010\u0010\"\u001a\u00020!H\u00d6\u0001\u00a2\u0006\u0004\b\"\u0010#R\u0017\u0010\u0015\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010$\u001a\u0004\b%\u0010\u0010R#\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010&\u001a\u0004\b'\u0010\u0013R\u0017\u0010\u0014\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010(\u001a\u0004\b)\u0010\r\u00a8\u0006,"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/interaction/PokemonInteractionGUICreationEvent;", "", "Lcom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelOption;", "option", "", "addFillingOption", "(Lcom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelOption;)V", "Lcom/cobblemon/mod/common/client/gui/interact/wheel/Orientation;", "orientation", "addOption", "(Lcom/cobblemon/mod/common/client/gui/interact/wheel/Orientation;Lcom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelOption;)V", "Ljava/util/UUID;", "component1", "()Ljava/util/UUID;", "", "component2", "()Z", "Lcom/google/common/collect/Multimap;", "component3", "()Lcom/google/common/collect/Multimap;", "pokemonID", "mountShoulder", "options", "copy", "(Ljava/util/UUID;ZLcom/google/common/collect/Multimap;)Lcom/cobblemon/mod/common/api/events/pokemon/interaction/PokemonInteractionGUICreationEvent;", "other", "equals", "(Ljava/lang/Object;)Z", "getNextFreeOrientation", "()Lcom/cobblemon/mod/common/client/gui/interact/wheel/Orientation;", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Z", "getMountShoulder", "Lcom/google/common/collect/Multimap;", "getOptions", "Ljava/util/UUID;", "getPokemonID", "<init>", "(Ljava/util/UUID;ZLcom/google/common/collect/Multimap;)V", "common"})
public final class PokemonInteractionGUICreationEvent {
    @NotNull
    private final UUID pokemonID;
    private final boolean mountShoulder;
    @NotNull
    private final Multimap<Orientation, InteractWheelOption> options;

    public PokemonInteractionGUICreationEvent(@NotNull UUID pokemonID, boolean mountShoulder2, @NotNull Multimap<Orientation, InteractWheelOption> options) {
        Intrinsics.checkNotNullParameter((Object)pokemonID, (String)"pokemonID");
        Intrinsics.checkNotNullParameter(options, (String)"options");
        this.pokemonID = pokemonID;
        this.mountShoulder = mountShoulder2;
        this.options = options;
    }

    @NotNull
    public final UUID getPokemonID() {
        return this.pokemonID;
    }

    public final boolean getMountShoulder() {
        return this.mountShoulder;
    }

    @NotNull
    public final Multimap<Orientation, InteractWheelOption> getOptions() {
        return this.options;
    }

    public final void addFillingOption(@NotNull InteractWheelOption option) {
        Intrinsics.checkNotNullParameter((Object)option, (String)"option");
        this.options.put((Object)this.getNextFreeOrientation(), (Object)option);
    }

    public final void addOption(@NotNull Orientation orientation, @NotNull InteractWheelOption option) {
        Intrinsics.checkNotNullParameter((Object)((Object)orientation), (String)"orientation");
        Intrinsics.checkNotNullParameter((Object)option, (String)"option");
        this.options.put((Object)orientation, (Object)option);
    }

    private final Orientation getNextFreeOrientation() {
        Orientation largest = Orientation.TOP_LEFT;
        for (Orientation orientation : Orientation.values()) {
            if (!this.options.containsKey((Object)orientation)) {
                return orientation;
            }
            if (this.options.get((Object)orientation).size() >= this.options.get((Object)largest).size()) continue;
            largest = orientation;
        }
        return largest;
    }

    @NotNull
    public final UUID component1() {
        return this.pokemonID;
    }

    public final boolean component2() {
        return this.mountShoulder;
    }

    @NotNull
    public final Multimap<Orientation, InteractWheelOption> component3() {
        return this.options;
    }

    @NotNull
    public final PokemonInteractionGUICreationEvent copy(@NotNull UUID pokemonID, boolean mountShoulder2, @NotNull Multimap<Orientation, InteractWheelOption> options) {
        Intrinsics.checkNotNullParameter((Object)pokemonID, (String)"pokemonID");
        Intrinsics.checkNotNullParameter(options, (String)"options");
        return new PokemonInteractionGUICreationEvent(pokemonID, mountShoulder2, options);
    }

    public static /* synthetic */ PokemonInteractionGUICreationEvent copy$default(PokemonInteractionGUICreationEvent pokemonInteractionGUICreationEvent, UUID uUID, boolean bl, Multimap multimap, int n, Object object) {
        if ((n & 1) != 0) {
            uUID = pokemonInteractionGUICreationEvent.pokemonID;
        }
        if ((n & 2) != 0) {
            bl = pokemonInteractionGUICreationEvent.mountShoulder;
        }
        if ((n & 4) != 0) {
            multimap = pokemonInteractionGUICreationEvent.options;
        }
        return pokemonInteractionGUICreationEvent.copy(uUID, bl, multimap);
    }

    @NotNull
    public String toString() {
        return "PokemonInteractionGUICreationEvent(pokemonID=" + this.pokemonID + ", mountShoulder=" + this.mountShoulder + ", options=" + this.options + ")";
    }

    public int hashCode() {
        int result = this.pokemonID.hashCode();
        int n = this.mountShoulder ? 1 : 0;
        if (n != 0) {
            n = 1;
        }
        result = result * 31 + n;
        result = result * 31 + this.options.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PokemonInteractionGUICreationEvent)) {
            return false;
        }
        PokemonInteractionGUICreationEvent pokemonInteractionGUICreationEvent = (PokemonInteractionGUICreationEvent)other;
        if (!Intrinsics.areEqual((Object)this.pokemonID, (Object)pokemonInteractionGUICreationEvent.pokemonID)) {
            return false;
        }
        if (this.mountShoulder != pokemonInteractionGUICreationEvent.mountShoulder) {
            return false;
        }
        return Intrinsics.areEqual(this.options, pokemonInteractionGUICreationEvent.options);
    }
}

