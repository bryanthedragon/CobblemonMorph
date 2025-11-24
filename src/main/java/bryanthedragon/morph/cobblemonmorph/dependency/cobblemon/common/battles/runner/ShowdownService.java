/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.graal.GraalShowdownService;
import com.google.gson.JsonArray;
import java.util.UUID;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019J\u000f\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H&\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u000f\u0010\b\u001a\u00020\u0005H&\u00a2\u0006\u0004\b\b\u0010\u0007J\u000f\u0010\t\u001a\u00020\u0005H&\u00a2\u0006\u0004\b\t\u0010\u0007J\u000f\u0010\n\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\n\u0010\u0004J\u000f\u0010\u000b\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u000b\u0010\u0004J\u000f\u0010\f\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\f\u0010\u0004J\u000f\u0010\r\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\r\u0010\u0004J%\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u000e2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H&\u00a2\u0006\u0004\b\u0013\u0010\u0014J%\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0016\u001a\u00020\u00152\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010H&\u00a2\u0006\u0004\b\u0017\u0010\u0018\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/battles/runner/ShowdownService;", "", "", "closeConnection", "()V", "Lcom/google/gson/JsonArray;", "getAbilityIds", "()Lcom/google/gson/JsonArray;", "getItemIds", "getMoves", "indicateSpeciesInitialized", "openConnection", "registerBagItems", "registerSpecies", "Ljava/util/UUID;", "battleId", "", "", "messages", "send", "(Ljava/util/UUID;[Ljava/lang/String;)V", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "startBattle", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;[Ljava/lang/String;)V", "Companion", "common"})
public interface ShowdownService {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService$Companion.$$INSTANCE;

    public void openConnection();

    public void closeConnection();

    public void startBattle(@NotNull PokemonBattle var1, @NotNull String[] var2);

    public void send(@NotNull UUID var1, @NotNull String[] var2);

    @NotNull
    public JsonArray getAbilityIds();

    @NotNull
    public JsonArray getMoves();

    @NotNull
    public JsonArray getItemIds();

    public void registerSpecies();

    public void registerBagItems();

    public void indicateSpeciesInitialized();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001b\u0010\u0007\u001a\u00020\u00028FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/battles/runner/ShowdownService$Companion;", "", "Lcom/cobblemon/mod/common/battles/runner/ShowdownService;", "service$delegate", "Lkotlin/Lazy;", "getService", "()Lcom/cobblemon/mod/common/battles/runner/ShowdownService;", "service", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final Lazy<GraalShowdownService> service$delegate;

        private Companion() {
        }

        @NotNull
        public final ShowdownService getService() {
            Lazy<GraalShowdownService> lazy = service$delegate;
            return (ShowdownService)lazy.getValue();
        }

        static {
            $$INSTANCE = new Companion();
            service$delegate = LazyKt.lazy((Function0)service.2.INSTANCE);
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static void indicateSpeciesInitialized(@NotNull ShowdownService $this) {
        }
    }
}

