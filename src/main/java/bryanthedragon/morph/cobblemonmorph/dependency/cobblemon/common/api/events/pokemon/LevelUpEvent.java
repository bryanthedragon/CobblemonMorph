/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\f\u001a\u00020\u000b\u0012\u0006\u0010\t\u001a\u00020\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011R\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0017\u0010\t\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u0006R\u0017\u0010\f\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/LevelUpEvent;", "", "", "newLevel", "I", "getNewLevel", "()I", "setNewLevel", "(I)V", "oldLevel", "getOldLevel", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;II)V", "common"})
public final class LevelUpEvent {
    @NotNull
    private final Pokemon pokemon;
    private final int oldLevel;
    private int newLevel;

    public LevelUpEvent(@NotNull Pokemon pokemon, int oldLevel, int newLevel) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this.pokemon = pokemon;
        this.oldLevel = oldLevel;
        this.newLevel = newLevel;
    }

    @NotNull
    public final Pokemon getPokemon() {
        return this.pokemon;
    }

    public final int getOldLevel() {
        return this.oldLevel;
    }

    public final int getNewLevel() {
        return this.newLevel;
    }

    public final void setNewLevel(int n) {
        this.newLevel = n;
    }
}

