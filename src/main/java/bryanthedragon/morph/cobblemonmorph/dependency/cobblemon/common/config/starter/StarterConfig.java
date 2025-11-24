/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.Category;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.LastChangedVersion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.NodeCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.StarterCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.PokemonPropertiesAdapterKt;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0007\u00a2\u0006\u0004\b\u0014\u0010\u0015R\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\"\u0010\t\u001a\u00020\u00028\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR(\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\f8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0012\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/config/starter/StarterConfig;", "", "", "allowStarterOnJoin", "Z", "getAllowStarterOnJoin", "()Z", "setAllowStarterOnJoin", "(Z)V", "promptStarterOnceOnly", "getPromptStarterOnceOnly", "setPromptStarterOnceOnly", "", "Lcom/cobblemon/mod/common/config/starter/StarterCategory;", "starters", "Ljava/util/List;", "getStarters", "()Ljava/util/List;", "setStarters", "(Ljava/util/List;)V", "<init>", "()V", "Companion", "common"})
public final class StarterConfig {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NodeCategory(category=Category.Starter)
    private boolean allowStarterOnJoin = true;
    @NodeCategory(category=Category.Starter)
    @LastChangedVersion(version="1.5.0")
    private boolean promptStarterOnceOnly = true;
    @NodeCategory(category=Category.Starter)
    @NotNull
    private List<StarterCategory> starters;
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().registerTypeAdapter((Type)((Object)PokemonProperties.class), (Object)PokemonPropertiesAdapterKt.getPokemonPropertiesShortAdapter()).create();

    public StarterConfig() {
        Object[] objectArray = new StarterCategory[10];
        Object[] objectArray2 = new PokemonProperties[]{PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Bulbasaur level=10", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Charmander level=10", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Squirtle level=10", null, null, 6, null)};
        objectArray[0] = new StarterCategory("Kanto", "cobblemon.starterselection.category.kanto", CollectionsKt.mutableListOf((Object[])objectArray2));
        objectArray2 = new PokemonProperties[]{PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Chikorita level=10", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Cyndaquil level=10", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Totodile level=10", null, null, 6, null)};
        objectArray[1] = new StarterCategory("Johto", "cobblemon.starterselection.category.johto", CollectionsKt.mutableListOf((Object[])objectArray2));
        objectArray2 = new PokemonProperties[]{PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Treecko level=10", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Torchic level=10", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Mudkip level=10", null, null, 6, null)};
        objectArray[2] = new StarterCategory("Hoenn", "cobblemon.starterselection.category.hoenn", CollectionsKt.mutableListOf((Object[])objectArray2));
        objectArray2 = new PokemonProperties[]{PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Turtwig level=10", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Chimchar level=10", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Piplup level=10", null, null, 6, null)};
        objectArray[3] = new StarterCategory("Sinnoh", "cobblemon.starterselection.category.sinnoh", CollectionsKt.mutableListOf((Object[])objectArray2));
        objectArray2 = new PokemonProperties[]{PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Snivy level=10", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Tepig level=10", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Oshawott level=10", null, null, 6, null)};
        objectArray[4] = new StarterCategory("Unova", "cobblemon.starterselection.category.unova", CollectionsKt.mutableListOf((Object[])objectArray2));
        objectArray2 = new PokemonProperties[]{PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Chespin level=10", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Fennekin level=10", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Froakie level=10", null, null, 6, null)};
        objectArray[5] = new StarterCategory("Kalos", "cobblemon.starterselection.category.kalos", CollectionsKt.mutableListOf((Object[])objectArray2));
        objectArray2 = new PokemonProperties[]{PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Rowlet level=10", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Litten level=10", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Popplio level=10", null, null, 6, null)};
        objectArray[6] = new StarterCategory("Alola", "cobblemon.starterselection.category.alola", CollectionsKt.mutableListOf((Object[])objectArray2));
        objectArray2 = new PokemonProperties[]{PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Grookey level=10", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Scorbunny level=10", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Sobble level=10", null, null, 6, null)};
        objectArray[7] = new StarterCategory("Galar", "cobblemon.starterselection.category.galar", CollectionsKt.mutableListOf((Object[])objectArray2));
        objectArray2 = new PokemonProperties[]{PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Rowlet region_bias=hisui level=10 pokeball=ancient_poke_ball", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Cyndaquil region_bias=hisui level=10 pokeball=ancient_poke_ball", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Oshawott region_bias=hisui level=10 pokeball=ancient_poke_ball", null, null, 6, null)};
        objectArray[8] = new StarterCategory("Hisui Bias", "cobblemon.starterselection.category.hisui_bias", CollectionsKt.mutableListOf((Object[])objectArray2));
        objectArray2 = new PokemonProperties[]{PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Sprigatito level=10", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Fuecoco level=10", null, null, 6, null), PokemonProperties.Companion.parse$default(PokemonProperties.Companion, "Quaxly level=10", null, null, 6, null)};
        objectArray[9] = new StarterCategory("Paldea", "cobblemon.starterselection.category.paldea", CollectionsKt.mutableListOf((Object[])objectArray2));
        this.starters = CollectionsKt.mutableListOf((Object[])objectArray);
    }

    public final boolean getAllowStarterOnJoin() {
        return this.allowStarterOnJoin;
    }

    public final void setAllowStarterOnJoin(boolean bl) {
        this.allowStarterOnJoin = bl;
    }

    public final boolean getPromptStarterOnceOnly() {
        return this.promptStarterOnceOnly;
    }

    public final void setPromptStarterOnceOnly(boolean bl) {
        this.promptStarterOnceOnly = bl;
    }

    @NotNull
    public final List<StarterCategory> getStarters() {
        return this.starters;
    }

    public final void setStarters(@NotNull List<StarterCategory> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.starters = list;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001f\u0010\u0004\u001a\n \u0003*\u0004\u0018\u00010\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/config/starter/StarterConfig$Companion;", "", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "GSON", "Lcom/google/gson/Gson;", "getGSON", "()Lcom/google/gson/Gson;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final Gson getGSON() {
            return GSON;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

