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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u000f\b&\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u001f\u0010\u0015J\u0019\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0019\u0010\n\u001a\u0004\u0018\u00010\u00062\u0006\u0010\t\u001a\u00028\u0000H&\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0019\u0010\r\u001a\u0004\u0018\u00018\u00002\u0006\u0010\f\u001a\u00020\u0006H&\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001d\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00028\u0000\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0015\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u000f\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0014\u0010\u0015J!\u0010\u0016\u001a\u00020\u00112\u0006\u0010\t\u001a\u00028\u00002\b\u0010\f\u001a\u0004\u0018\u00010\u0006H&\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u001d\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u0004\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u0017\u0010\u0005\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/client/storage/ClientStorage;", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "T", "", "Ljava/util/UUID;", "uuid", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "findByUUID", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "position", "get", "(Lcom/cobblemon/mod/common/api/storage/StorePosition;)Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "getPosition", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/api/storage/StorePosition;", "pokemonID", "newPosition", "", "move", "(Ljava/util/UUID;Lcom/cobblemon/mod/common/api/storage/StorePosition;)V", "remove", "(Ljava/util/UUID;)V", "set", "(Lcom/cobblemon/mod/common/api/storage/StorePosition;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "pokemonID1", "pokemonID2", "swap", "(Ljava/util/UUID;Ljava/util/UUID;)V", "Ljava/util/UUID;", "getUuid", "()Ljava/util/UUID;", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nClientStorage.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ClientStorage.kt\ncom/cobblemon/mod/common/client/storage/ClientStorage\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,42:1\n1#2:43\n*E\n"})
public abstract class ClientStorage<T extends StorePosition> {
    @NotNull
    private final UUID uuid;

    public ClientStorage(@NotNull UUID uuid2) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        this.uuid = uuid2;
    }

    @NotNull
    public final UUID getUuid() {
        return this.uuid;
    }

    @Nullable
    public abstract Pokemon findByUUID(@NotNull UUID var1);

    public abstract void set(@NotNull T var1, @Nullable Pokemon var2);

    @Nullable
    public abstract Pokemon get(@NotNull T var1);

    @Nullable
    public abstract T getPosition(@NotNull Pokemon var1);

    public final void swap(@NotNull UUID pokemonID1, @NotNull UUID pokemonID2) {
        block5: {
            T t;
            T t2;
            Intrinsics.checkNotNullParameter((Object)pokemonID1, (String)"pokemonID1");
            Intrinsics.checkNotNullParameter((Object)pokemonID2, (String)"pokemonID2");
            Pokemon pokemon1 = this.findByUUID(pokemonID1);
            Pokemon pokemon2 = this.findByUUID(pokemonID2);
            Pokemon pokemon = pokemon1;
            if (pokemon != null) {
                Pokemon it = pokemon;
                boolean bl = false;
                t2 = this.getPosition(it);
            } else {
                t2 = null;
            }
            T position1 = t2;
            Pokemon pokemon3 = pokemon2;
            if (pokemon3 != null) {
                Pokemon it = pokemon3;
                boolean bl = false;
                t = this.getPosition(it);
            } else {
                t = null;
            }
            T position2 = t;
            T t3 = position1;
            if (t3 != null) {
                T $this$swap_u24lambda_u242 = t3;
                boolean bl = false;
                this.set($this$swap_u24lambda_u242, pokemon2);
            }
            T t4 = position2;
            if (t4 == null) break block5;
            T $this$swap_u24lambda_u243 = t4;
            boolean bl = false;
            this.set($this$swap_u24lambda_u243, pokemon1);
        }
    }

    public final void remove(@NotNull UUID pokemonID) {
        block1: {
            Intrinsics.checkNotNullParameter((Object)pokemonID, (String)"pokemonID");
            Pokemon pokemon = this.findByUUID(pokemonID);
            if (pokemon == null) {
                return;
            }
            Pokemon pokemon2 = pokemon;
            T t = this.getPosition(pokemon2);
            if (t == null) break block1;
            T it = t;
            boolean bl = false;
            this.set(it, null);
        }
    }

    public final void move(@NotNull UUID pokemonID, @NotNull T newPosition) {
        block1: {
            Intrinsics.checkNotNullParameter((Object)pokemonID, (String)"pokemonID");
            Intrinsics.checkNotNullParameter(newPosition, (String)"newPosition");
            Pokemon pokemon = this.findByUUID(pokemonID);
            if (pokemon == null) {
                return;
            }
            Pokemon pokemon2 = pokemon;
            T t = this.getPosition(pokemon2);
            if (t == null) break block1;
            T it = t;
            boolean bl = false;
            this.set(it, null);
            this.set(newPosition, pokemon2);
        }
    }
}

