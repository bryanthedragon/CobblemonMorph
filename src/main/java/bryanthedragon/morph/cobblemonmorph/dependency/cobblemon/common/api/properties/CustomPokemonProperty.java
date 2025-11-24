/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.PropertiesCompletionProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\bf\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\u0005\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH&\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u000e\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\u000e\u0010\u0010\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/api/properties/CustomPokemonProperty;", "", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "", "apply", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "", "asString", "()Ljava/lang/String;", "", "matches", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Z", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "Companion", "common"})
public interface CustomPokemonProperty {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty$Companion.$$INSTANCE;

    @NotNull
    public String asString();

    public void apply(@NotNull Pokemon var1);

    public void apply(@NotNull PokemonEntity var1);

    public boolean matches(@NotNull Pokemon var1);

    public boolean matches(@NotNull PokemonEntity var1);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001f\u0010\u0017J%\u0010\u0007\u001a\u00020\u0006\"\b\b\u0000\u0010\u0003*\u00020\u00022\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u00a2\u0006\u0004\b\u0007\u0010\bJU\u0010\u0007\u001a\u00020\u0006\"\b\b\u0000\u0010\u0003*\u00020\u00022\u0006\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\u000b2\u0016\u0010\u000e\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0006\u0012\u0004\u0018\u00018\u00000\r2\u0012\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00100\u000f\u00a2\u0006\u0004\b\u0007\u0010\u0012J[\u0010\u0007\u001a\u00020\u0006\"\b\b\u0000\u0010\u0003*\u00020\u00022\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\u00132\b\b\u0002\u0010\f\u001a\u00020\u000b2\u0016\u0010\u000e\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0006\u0012\u0004\u0018\u00018\u00000\r2\u0012\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00100\u000f\u00a2\u0006\u0004\b\u0007\u0010\u0015J\u000f\u0010\u0016\u001a\u00020\u0006H\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0019\u0010\u0019\u001a\u00020\u00062\n\u0010\u0018\u001a\u0006\u0012\u0002\b\u00030\u0004\u00a2\u0006\u0004\b\u0019\u0010\bR!\u0010\u001b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00040\u001a8\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/api/properties/CustomPokemonProperty$Companion;", "", "Lcom/cobblemon/mod/common/api/properties/CustomPokemonProperty;", "T", "Lcom/cobblemon/mod/common/api/properties/CustomPokemonPropertyType;", "propertyType", "", "register", "(Lcom/cobblemon/mod/common/api/properties/CustomPokemonPropertyType;)V", "", "name", "", "needsLabel", "Lkotlin/Function1;", "fromString", "Lkotlin/Function0;", "", "examples", "(Ljava/lang/String;ZLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;)V", "", "aliases", "(Ljava/lang/Iterable;ZLkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;)V", "triggerSyncAttempt", "()V", "property", "unregister", "", "properties", "Ljava/util/List;", "getProperties", "()Ljava/util/List;", "<init>", "common"})
    @SourceDebugExtension(value={"SMAP\nCustomPokemonProperty.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CustomPokemonProperty.kt\ncom/cobblemon/mod/common/api/properties/CustomPokemonProperty$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,85:1\n1855#2,2:86\n*S KotlinDebug\n*F\n+ 1 CustomPokemonProperty.kt\ncom/cobblemon/mod/common/api/properties/CustomPokemonProperty$Companion\n*L\n60#1:86,2\n*E\n"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final List<CustomPokemonPropertyType<?>> properties;

        private Companion() {
        }

        @NotNull
        public final List<CustomPokemonPropertyType<?>> getProperties() {
            return properties;
        }

        public final <T extends CustomPokemonProperty> void register(@NotNull CustomPokemonPropertyType<T> propertyType) {
            Intrinsics.checkNotNullParameter(propertyType, (String)"propertyType");
            properties.add(propertyType);
            this.triggerSyncAttempt();
        }

        public final <T extends CustomPokemonProperty> void register(@NotNull String name, boolean needsLabel, @NotNull Function1<? super String, ? extends T> fromString2, @NotNull Function0<? extends Collection<String>> examples) {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Intrinsics.checkNotNullParameter(fromString2, (String)"fromString");
            Intrinsics.checkNotNullParameter(examples, (String)"examples");
            this.register(CollectionsKt.listOf((Object)name), needsLabel, fromString2, examples);
        }

        public static /* synthetic */ void register$default(Companion companion, String string, boolean bl, Function1 function1, Function0 function0, int n, Object object) {
            if ((n & 2) != 0) {
                bl = true;
            }
            companion.register(string, bl, function1, (Function0<? extends Collection<String>>)function0);
        }

        public final <T extends CustomPokemonProperty> void register(@NotNull Iterable<String> aliases, boolean needsLabel, @NotNull Function1<? super String, ? extends T> fromString2, @NotNull Function0<? extends Collection<String>> examples) {
            Intrinsics.checkNotNullParameter(aliases, (String)"aliases");
            Intrinsics.checkNotNullParameter(fromString2, (String)"fromString");
            Intrinsics.checkNotNullParameter(examples, (String)"examples");
            properties.add(new CustomPokemonPropertyType<T>(aliases, needsLabel, fromString2, examples){
                @NotNull
                private final Iterable<String> keys;
                private final boolean needsKey;
                final /* synthetic */ Function1<String, T> $fromString;
                final /* synthetic */ Function0<Collection<String>> $examples;
                {
                    this.$fromString = $fromString;
                    this.$examples = $examples;
                    this.keys = $aliases;
                    this.needsKey = $needsLabel;
                }

                @NotNull
                public Iterable<String> getKeys() {
                    return this.keys;
                }

                public boolean getNeedsKey() {
                    return this.needsKey;
                }

                @Nullable
                public T fromString(@Nullable String value2) {
                    return (T)((CustomPokemonProperty)this.$fromString.invoke((Object)value2));
                }

                @NotNull
                public Collection<String> examples() {
                    return (Collection)this.$examples.invoke();
                }
            });
            this.triggerSyncAttempt();
        }

        public static /* synthetic */ void register$default(Companion companion, Iterable iterable, boolean bl, Function1 function1, Function0 function0, int n, Object object) {
            if ((n & 2) != 0) {
                bl = true;
            }
            companion.register(iterable, bl, function1, (Function0<? extends Collection<String>>)function0);
        }

        public final void unregister(@NotNull CustomPokemonPropertyType<?> property) {
            Intrinsics.checkNotNullParameter(property, (String)"property");
            properties.remove(property);
        }

        private final void triggerSyncAttempt() {
            MinecraftServer minecraftServer = DistributionUtilsKt.server();
            if (minecraftServer == null) {
                return;
            }
            MinecraftServer server = minecraftServer;
            if (!server.m_129792_()) {
                PropertiesCompletionProvider.INSTANCE.reload();
                List list = server.m_6846_().m_11314_();
                Intrinsics.checkNotNullExpressionValue((Object)list, (String)"server.playerManager.playerList");
                Iterable $this$forEach$iv = list;
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    ServerPlayer player = (ServerPlayer)element$iv;
                    boolean bl = false;
                    Intrinsics.checkNotNullExpressionValue((Object)player, (String)"player");
                    PropertiesCompletionProvider.INSTANCE.sync(player);
                }
            }
        }

        static {
            $$INSTANCE = new Companion();
            properties = new ArrayList();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static void apply(@NotNull CustomPokemonProperty $this, @NotNull PokemonEntity pokemonEntity) {
            Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
            $this.apply(pokemonEntity.getPokemon());
        }

        public static boolean matches(@NotNull CustomPokemonProperty $this, @NotNull PokemonEntity pokemonEntity) {
            Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
            return $this.matches(pokemonEntity.getPokemon());
        }
    }
}

