/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;

public class CountablePokemonTypeContext extends CountableContext {
    @NotNull
    private String type;

    public CountablePokemonTypeContext(int times2, @NotNull String type) {
        super(times2);
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        this.type = type;
    }

    @NotNull
    public final String getType() {
        return this.type;
    }

    public final void setType(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.type = string;
    }
}

