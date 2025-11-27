/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MissingContext implements BattleContext {
    @NotNull
    private final String id;
    private final int turn;
    @NotNull
    private final BattleContext.Type type;
    @Nullable
    private final BattlePokemon origin;

    public MissingContext(@NotNull String id, int turn, @NotNull BattleContext.Type type, @Nullable BattlePokemon origin) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)((Object)type), (String)"type");
        this.id = id;
        this.turn = turn;
        this.type = type;
        this.origin = origin;
    }

    public /* synthetic */ MissingContext(String string, int n, BattleContext.Type type, BattlePokemon battlePokemon, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        this(string, n, type, battlePokemon);
        if ((n2 & 1) != 0) {
            string = "error";
        }
        if ((n2 & 2) != 0) {
            n = 0;
        }
        if ((n2 & 4) != 0) {
            type = BattleContext.Type.MISC;
        }
        if ((n2 & 8) != 0) {
            battlePokemon = null;
        }
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
    public final MissingContext copy(@NotNull String id, int turn, @NotNull BattleContext.Type type, @Nullable BattlePokemon origin) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter((Object)((Object)type), (String)"type");
        return new MissingContext(id, turn, type, origin);
    }

    public static /* synthetic */ MissingContext copy$default(MissingContext missingContext, String string, int n, BattleContext.Type type, BattlePokemon battlePokemon, int n2, Object object) {
        if ((n2 & 1) != 0) {
            string = missingContext.id;
        }
        if ((n2 & 2) != 0) {
            n = missingContext.turn;
        }
        if ((n2 & 4) != 0) {
            type = missingContext.type;
        }
        if ((n2 & 8) != 0) {
            battlePokemon = missingContext.origin;
        }
        return missingContext.copy(string, n, type, battlePokemon);
    }

    @NotNull
    public String toString() {
        return "MissingContext(id=" + this.id + ", turn=" + this.turn + ", type=" + this.type + ", origin=" + this.origin + ")";
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
        if (!(other instanceof MissingContext)) {
            return false;
        }
        MissingContext missingContext = (MissingContext)other;
        if (!Intrinsics.areEqual((Object)this.id, (Object)missingContext.id)) {
            return false;
        }
        if (this.turn != missingContext.turn) {
            return false;
        }
        if (this.type != missingContext.type) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.origin, (Object)missingContext.origin);
    }

    public MissingContext() {
        this(null, 0, null, null, 15, null);
    }
}

