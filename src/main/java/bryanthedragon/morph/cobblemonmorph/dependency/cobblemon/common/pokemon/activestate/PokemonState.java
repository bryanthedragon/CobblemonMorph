/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.InactivePokemonState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.SentOutState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ShoulderedState;
import com.google.gson.JsonObject;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000  2\u00020\u0001:\u0001 B\t\b\u0004\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0019\u0010\u0005\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\t\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\r\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0017\u0010\u0014\u001a\u00020\u00132\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0019\u0010\u0016\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0019\u0010\u0018\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001d\u001a\u00020\u001a8F\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c\u0082\u0001\u0002!\"\u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lnet/minecraft/resources/ResourceLocation;", "getIcon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lnet/minecraft/resources/ResourceLocation;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "readFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;", "Lcom/google/gson/JsonObject;", "json", "readFromJSON", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "readFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;", "", "writeToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "writeToJSON", "(Lcom/google/gson/JsonObject;)Lcom/google/gson/JsonObject;", "writeToNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", "", "getName", "()Ljava/lang/String;", "name", "<init>", "()V", "Companion", "Lcom/cobblemon/mod/common/pokemon/activestate/ActivePokemonState;", "Lcom/cobblemon/mod/common/pokemon/activestate/InactivePokemonState;", "common"})
@SourceDebugExtension(value={"SMAP\nPokemonState.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PokemonState.kt\ncom/cobblemon/mod/common/pokemon/activestate/PokemonState\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,202:1\n1#2:203\n*E\n"})
public abstract class PokemonState {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private static final Map<String, Class<? extends PokemonState>> states;

    private PokemonState() {
    }

    @NotNull
    public final String getName() {
        Object v0;
        block1: {
            Iterable iterable = states.entrySet();
            for (Object t : iterable) {
                Map.Entry it = (Map.Entry)t;
                boolean bl = false;
                if (!Intrinsics.areEqual(it.getValue(), this.getClass())) continue;
                v0 = t;
                break block1;
            }
            v0 = null;
        }
        Intrinsics.checkNotNull(v0);
        return (String)((Map.Entry)v0).getKey();
    }

    @Nullable
    public ResourceLocation getIcon(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return null;
    }

    @Nullable
    public CompoundTag writeToNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        nbt.m_128359_("StateType", this.getName());
        return nbt;
    }

    @NotNull
    public PokemonState readFromNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        return this;
    }

    @Nullable
    public JsonObject writeToJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        return json;
    }

    @NotNull
    public PokemonState readFromJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        return this;
    }

    public void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(this.getName());
    }

    @NotNull
    public PokemonState readFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        return this;
    }

    public /* synthetic */ PokemonState(DefaultConstructorMarker $constructor_marker) {
        this();
    }

    static {
        Pair[] pairArray = new Pair[]{TuplesKt.to((Object)"inactive", InactivePokemonState.class), TuplesKt.to((Object)"sent-out", SentOutState.class), TuplesKt.to((Object)"shouldered", ShoulderedState.class)};
        states = MapsKt.mapOf((Pair[])pairArray);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R+\u0010\n\u001a\u0016\u0012\u0004\u0012\u00020\b\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00040\t0\u00078\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;", "fromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;", "", "", "Ljava/lang/Class;", "states", "Ljava/util/Map;", "getStates", "()Ljava/util/Map;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Map<String, Class<? extends PokemonState>> getStates() {
            return states;
        }

        @NotNull
        public final PokemonState fromBuffer(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            String type = buffer.m_130277_();
            Class<? extends PokemonState> clazz = this.getStates().get(type);
            if (clazz == null || (clazz = clazz.newInstance()) == null || (clazz = ((PokemonState)((Object)clazz)).readFromBuffer(buffer)) == null) {
                clazz = new InactivePokemonState();
            }
            return clazz;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

