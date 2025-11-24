/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000f\b\u0086\b\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u000e\u001a\u00020\u0002\u0012\u0006\u0010\u000f\u001a\u00020\u0005\u0012\u0006\u0010\u0010\u001a\u00020\b\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0004\b#\u0010$J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\t\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\nJ\u0012\u0010\f\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003\u00a2\u0006\u0004\b\f\u0010\rJ:\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u000e\u001a\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\u00052\b\b\u0002\u0010\u0010\u001a\u00020\b2\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u000bH\u00c6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001a\u0010\u0017\u001a\u00020\u00162\b\u0010\u0015\u001a\u0004\u0018\u00010\u0014H\u00d6\u0003\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0010\u0010\u0019\u001a\u00020\u0005H\u00d6\u0001\u00a2\u0006\u0004\b\u0019\u0010\u0007J\u0010\u0010\u001a\u001a\u00020\u0002H\u00d6\u0001\u00a2\u0006\u0004\b\u001a\u0010\u0004R\u001a\u0010\u000e\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u001b\u001a\u0004\b\u001c\u0010\u0004R\u001c\u0010\u0011\u001a\u0004\u0018\u00010\u000b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0011\u0010\u001d\u001a\u0004\b\u001e\u0010\rR\u001a\u0010\u000f\u001a\u00020\u00058\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000f\u0010\u001f\u001a\u0004\b \u0010\u0007R\u001a\u0010\u0010\u001a\u00020\b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0010\u0010!\u001a\u0004\b\"\u0010\n\u00a8\u0006%"}, d2={"Lcom/cobblemon/mod/common/api/battles/interpreter/BasicContext;", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext;", "", "component1", "()Ljava/lang/String;", "", "component2", "()I", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext$Type;", "component3", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext$Type;", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "component4", "()Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "id", "turn", "type", "origin", "copy", "(Ljava/lang/String;ILcom/cobblemon/mod/common/api/battles/interpreter/BattleContext$Type;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)Lcom/cobblemon/mod/common/api/battles/interpreter/BasicContext;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "hashCode", "toString", "Ljava/lang/String;", "getId", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "getOrigin", "I", "getTurn", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext$Type;", "getType", "<init>", "(Ljava/lang/String;ILcom/cobblemon/mod/common/api/battles/interpreter/BattleContext$Type;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;)V", "common"})
public final class BasicContext
implements BattleContext {
    @NotNull
    private final String id;
    private final int turn;
    @NotNull
    private final BattleContext.Type type;
    @Nullable
    private final BattlePokemon origin;

    public BasicContext(@NotNull String id, int turn, @NotNull BattleContext.Type type, @Nullable BattlePokemon origin) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)((Object)type), (String)"type");
        this.id = id;
        this.turn = turn;
        this.type = type;
        this.origin = origin;
    }

    @Override
    @NotNull
    public String getId() {
        return this.id;
    }

    @Override
    public int getTurn() {
        return this.turn;
    }

    @Override
    @NotNull
    public BattleContext.Type getType() {
        return this.type;
    }

    @Override
    @Nullable
    public BattlePokemon getOrigin() {
        return this.origin;
    }

    @NotNull
    public final String component1() {
        return this.id;
    }

    public final int component2() {
        return this.turn;
    }

    @NotNull
    public final BattleContext.Type component3() {
        return this.type;
    }

    @Nullable
    public final BattlePokemon component4() {
        return this.origin;
    }

    @NotNull
    public final BasicContext copy(@NotNull String id, int turn, @NotNull BattleContext.Type type, @Nullable BattlePokemon origin) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)((Object)type), (String)"type");
        return new BasicContext(id, turn, type, origin);
    }

    public static /* synthetic */ BasicContext copy$default(BasicContext basicContext, String string, int n, BattleContext.Type type, BattlePokemon battlePokemon, int n2, Object object) {
        if ((n2 & 1) != 0) {
            string = basicContext.id;
        }
        if ((n2 & 2) != 0) {
            n = basicContext.turn;
        }
        if ((n2 & 4) != 0) {
            type = basicContext.type;
        }
        if ((n2 & 8) != 0) {
            battlePokemon = basicContext.origin;
        }
        return basicContext.copy(string, n, type, battlePokemon);
    }

    @NotNull
    public String toString() {
        return "BasicContext(id=" + this.id + ", turn=" + this.turn + ", type=" + this.type + ", origin=" + this.origin + ")";
    }

    public int hashCode() {
        int result = this.id.hashCode();
        result = result * 31 + Integer.hashCode(this.turn);
        result = result * 31 + this.type.hashCode();
        result = result * 31 + (this.origin == null ? 0 : this.origin.hashCode());
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BasicContext)) {
            return false;
        }
        BasicContext basicContext = (BasicContext)other;
        if (!Intrinsics.areEqual((Object)this.id, (Object)basicContext.id)) {
            return false;
        }
        if (this.turn != basicContext.turn) {
            return false;
        }
        if (this.type != basicContext.type) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.origin, (Object)basicContext.origin);
    }
}

