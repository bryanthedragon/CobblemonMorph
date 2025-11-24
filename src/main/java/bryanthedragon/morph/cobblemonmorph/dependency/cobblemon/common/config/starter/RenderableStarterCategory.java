/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\n\u001a\u00020\u0002\u0012\u0006\u0010\u000b\u001a\u00020\u0002\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\u0004\b\"\u0010#J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003\u00a2\u0006\u0004\b\b\u0010\tJ4\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\n\u001a\u00020\u00022\b\b\u0002\u0010\u000b\u001a\u00020\u00022\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0001\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001a\u0010\u0011\u001a\u00020\u00102\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0010\u0010\u0014\u001a\u00020\u0013H\u00d6\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0010\u0010\u0016\u001a\u00020\u0002H\u00d6\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0004R\u0017\u0010\u000b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\u0017\u001a\u0004\b\u0018\u0010\u0004R\u001f\u0010\u001b\u001a\n \u001a*\u0004\u0018\u00010\u00190\u00198\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR\u0017\u0010\n\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u0017\u001a\u0004\b\u001f\u0010\u0004R\u001d\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068\u0006\u00a2\u0006\f\n\u0004\b\f\u0010 \u001a\u0004\b!\u0010\t\u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/config/starter/RenderableStarterCategory;", "", "", "component1", "()Ljava/lang/String;", "component2", "", "Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "component3", "()Ljava/util/List;", "name", "displayName", "pokemon", "copy", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)Lcom/cobblemon/mod/common/config/starter/RenderableStarterCategory;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "toString", "Ljava/lang/String;", "getDisplayName", "Lnet/minecraft/network/chat/MutableComponent;", "kotlin.jvm.PlatformType", "displayNameText", "Lnet/minecraft/network/chat/MutableComponent;", "getDisplayNameText", "()Lnet/minecraft/network/chat/MutableComponent;", "getName", "Ljava/util/List;", "getPokemon", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V", "common"})
public final class RenderableStarterCategory {
    @NotNull
    private final String name;
    @NotNull
    private final String displayName;
    @NotNull
    private final List<RenderablePokemon> pokemon;
    private final MutableComponent displayNameText;

    public RenderableStarterCategory(@NotNull String name, @NotNull String displayName, @NotNull List<RenderablePokemon> pokemon) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        this.name = name;
        this.displayName = displayName;
        this.pokemon = pokemon;
        this.displayNameText = MiscUtilsKt.asTranslated(this.displayName);
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final String getDisplayName() {
        return this.displayName;
    }

    @NotNull
    public final List<RenderablePokemon> getPokemon() {
        return this.pokemon;
    }

    public final MutableComponent getDisplayNameText() {
        return this.displayNameText;
    }

    @NotNull
    public final String component1() {
        return this.name;
    }

    @NotNull
    public final String component2() {
        return this.displayName;
    }

    @NotNull
    public final List<RenderablePokemon> component3() {
        return this.pokemon;
    }

    @NotNull
    public final RenderableStarterCategory copy(@NotNull String name, @NotNull String displayName, @NotNull List<RenderablePokemon> pokemon) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        return new RenderableStarterCategory(name, displayName, pokemon);
    }

    public static /* synthetic */ RenderableStarterCategory copy$default(RenderableStarterCategory renderableStarterCategory, String string, String string2, List list, int n, Object object) {
        if ((n & 1) != 0) {
            string = renderableStarterCategory.name;
        }
        if ((n & 2) != 0) {
            string2 = renderableStarterCategory.displayName;
        }
        if ((n & 4) != 0) {
            list = renderableStarterCategory.pokemon;
        }
        return renderableStarterCategory.copy(string, string2, list);
    }

    @NotNull
    public String toString() {
        return "RenderableStarterCategory(name=" + this.name + ", displayName=" + this.displayName + ", pokemon=" + this.pokemon + ")";
    }

    public int hashCode() {
        int result = this.name.hashCode();
        result = result * 31 + this.displayName.hashCode();
        result = result * 31 + ((Object)this.pokemon).hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RenderableStarterCategory)) {
            return false;
        }
        RenderableStarterCategory renderableStarterCategory = (RenderableStarterCategory)other;
        if (!Intrinsics.areEqual((Object)this.name, (Object)renderableStarterCategory.name)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.displayName, (Object)renderableStarterCategory.displayName)) {
            return false;
        }
        return Intrinsics.areEqual(this.pokemon, renderableStarterCategory.pokemon);
    }
}

