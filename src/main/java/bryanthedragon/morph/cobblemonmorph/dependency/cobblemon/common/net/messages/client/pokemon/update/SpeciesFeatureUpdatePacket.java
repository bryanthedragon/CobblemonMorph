/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.SingleUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00182\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0018B%\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0014\u0012\u0006\u0010\u0012\u001a\u00020\r\u0012\u0006\u0010\u0015\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001f\u0010\u000b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR\u001a\u0010\u000e\u001a\u00020\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0012\u001a\u00020\r8\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u000f\u001a\u0004\b\u0013\u0010\u0011\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/SpeciesFeatureUpdatePacket;", "Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/SingleUpdatePacket;", "Lcom/cobblemon/mod/common/api/pokemon/feature/SynchronizedSpeciesFeature;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encodeValue", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "value", "set", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/pokemon/feature/SynchronizedSpeciesFeature;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "species", "getSpecies", "Lkotlin/Function0;", "speciesFeature", "<init>", "(Lkotlin/jvm/functions/Function0;Lnet/minecraft/resources/ResourceLocation;Lcom/cobblemon/mod/common/api/pokemon/feature/SynchronizedSpeciesFeature;)V", "Companion", "common"})
public final class SpeciesFeatureUpdatePacket
extends SingleUpdatePacket<SynchronizedSpeciesFeature, SpeciesFeatureUpdatePacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ResourceLocation species;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("species_feature_update");

    public SpeciesFeatureUpdatePacket(@NotNull Function0<? extends Pokemon> pokemon, @NotNull ResourceLocation species, @NotNull SynchronizedSpeciesFeature speciesFeature) {
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)species, (String)"species");
        Intrinsics.checkNotNullParameter((Object)speciesFeature, (String)"speciesFeature");
        super(pokemon, speciesFeature);
        this.species = species;
        this.id = ID;
    }

    @NotNull
    public final ResourceLocation getSpecies() {
        return this.species;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void encodeValue(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130085_(this.species);
        buffer.m_130070_(((SynchronizedSpeciesFeature)this.getValue()).getName());
        ((SynchronizedSpeciesFeature)this.getValue()).encode(buffer);
    }

    @Override
    public void set(@NotNull Pokemon pokemon, @NotNull SynchronizedSpeciesFeature value2) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        pokemon.getFeatures().removeIf(arg_0 -> SpeciesFeatureUpdatePacket.set$lambda$0((Function1)new Function1<SpeciesFeature, Boolean>(value2){
            final /* synthetic */ SynchronizedSpeciesFeature $value;
            {
                this.$value = $value;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@NotNull SpeciesFeature it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return Intrinsics.areEqual((Object)it.getName(), (Object)this.$value.getName());
            }
        }, arg_0));
        pokemon.getFeatures().add(value2);
    }

    private static final boolean set$lambda$0(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/SpeciesFeatureUpdatePacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/SpeciesFeatureUpdatePacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/SpeciesFeatureUpdatePacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nSpeciesFeatureUpdatePacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpeciesFeatureUpdatePacket.kt\ncom/cobblemon/mod/common/net/messages/client/pokemon/update/SpeciesFeatureUpdatePacket$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,53:1\n800#2,11:54\n1#3:65\n*S KotlinDebug\n*F\n+ 1 SpeciesFeatureUpdatePacket.kt\ncom/cobblemon/mod/common/net/messages/client/pokemon/update/SpeciesFeatureUpdatePacket$Companion\n*L\n35#1:54,11\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public final SpeciesFeatureUpdatePacket decode(@NotNull FriendlyByteBuf buffer) {
            Iterator iterator;
            Iterable iterable;
            ResourceLocation speciesIdentifier;
            Function0<Pokemon> pokemon;
            block4: {
                void $this$filterIsInstanceTo$iv$iv;
                Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
                pokemon = PokemonUpdatePacket.Companion.decodePokemon(buffer);
                speciesIdentifier = buffer.m_130281_();
                Intrinsics.checkNotNullExpressionValue((Object)speciesIdentifier, (String)"speciesIdentifier");
                Species species = PokemonSpecies.INSTANCE.getByIdentifier(speciesIdentifier);
                if (species == null) {
                    throw new IllegalStateException("Pok\u00e9mon unable to be found during species feature update packet: " + speciesIdentifier);
                }
                Species species2 = species;
                String speciesFeatureName = buffer.m_130277_();
                Iterable $this$filterIsInstance$iv = SpeciesFeatures.INSTANCE.getFeaturesFor(species2);
                boolean $i$f$filterIsInstance = false;
                iterable = $this$filterIsInstance$iv;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$filterIsInstanceTo = false;
                for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                    if (!(element$iv$iv instanceof SynchronizedSpeciesFeatureProvider)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                List featureProviders = (List)destination$iv$iv;
                for (SynchronizedSpeciesFeatureProvider it : (Iterable)featureProviders) {
                    boolean bl = false;
                    Intrinsics.checkNotNullExpressionValue((Object)speciesFeatureName, (String)"speciesFeatureName");
                    Iterator iterator2 = it.invoke(buffer, speciesFeatureName);
                    if (iterator2 == null) continue;
                    iterator = iterator2;
                    break block4;
                }
                iterator = null;
            }
            iterable = iterator;
            Iterable iterable2 = iterable instanceof SynchronizedSpeciesFeature ? iterable : null;
            if (iterable2 == null) {
                throw new IllegalArgumentException("Couldn't find a feature provider to deserialize this feature. Something's wrong.");
            }
            Iterable feature = iterable2;
            return new SpeciesFeatureUpdatePacket(pokemon, speciesIdentifier, (SynchronizedSpeciesFeature)((Object)feature));
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

