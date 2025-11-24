/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.evolution;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionDisplayEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionDisplay;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.SingleUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.CobblemonEvolutionDisplay;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00172\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0017B\u0019\b\u0016\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\u0012\u00a2\u0006\u0004\b\u0013\u0010\u0014B\u001d\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0015\u0012\u0006\u0010\n\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0016J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001f\u0010\u000b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR\u001a\u0010\u000e\u001a\u00020\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/evolution/AddEvolutionPacket;", "Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/SingleUpdatePacket;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encodeValue", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "value", "set", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;)V", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;)V", "Companion", "common"})
public final class AddEvolutionPacket
extends SingleUpdatePacket<EvolutionDisplay, AddEvolutionPacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("add_evolution");

    public AddEvolutionPacket(@NotNull Function0<? extends Pokemon> pokemon, @NotNull EvolutionDisplay value2) {
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        super(pokemon, value2);
        this.id = ID;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    public AddEvolutionPacket(final @NotNull Pokemon pokemon, @NotNull Evolution value2) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        this((Function0<? extends Pokemon>)((Function0)new Function0<Pokemon>(){

            @NotNull
            public final Pokemon invoke() {
                return pokemon;
            }
        }), Companion.convertToDisplay$common(value2, pokemon));
    }

    @Override
    public void encodeValue(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Companion.encode$common((EvolutionDisplay)this.getValue(), buffer);
    }

    @Override
    public void set(@NotNull Pokemon pokemon, @NotNull EvolutionDisplay value2) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        pokemon.getEvolutionProxy().client().add(value2);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\n\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0002H\u0000\u00a2\u0006\u0004\b\b\u0010\tJ\u001b\u0010\u0010\u001a\u00020\u0007*\u00020\u000b2\u0006\u0010\r\u001a\u00020\fH\u0000\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001b\u0010\u0014\u001a\u00020\u0011*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0002H\u0000\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0016\u001a\u00020\u00158\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/evolution/AddEvolutionPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/evolution/AddEvolutionPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/evolution/AddEvolutionPacket;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;", "decodeDisplay$common", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;", "decodeDisplay", "Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "convertToDisplay$common", "(Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;", "convertToDisplay", "", "encode$common", "(Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nAddEvolutionPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AddEvolutionPacket.kt\ncom/cobblemon/mod/common/net/messages/client/pokemon/update/evolution/AddEvolutionPacket$Companion\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n*L\n1#1,66:1\n14#2,5:67\n19#2:75\n13579#3:72\n13580#3:74\n14#4:73\n*S KotlinDebug\n*F\n+ 1 AddEvolutionPacket.kt\ncom/cobblemon/mod/common/net/messages/client/pokemon/update/evolution/AddEvolutionPacket$Companion\n*L\n47#1:67,5\n47#1:75\n47#1:72\n47#1:74\n47#1:73\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final AddEvolutionPacket decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            return new AddEvolutionPacket(PokemonUpdatePacket.Companion.decodePokemon(buffer), this.decodeDisplay$common(buffer));
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public final EvolutionDisplay convertToDisplay$common(@NotNull Evolution $this$convertToDisplay, @NotNull Pokemon pokemon) {
            void $this$iv;
            Intrinsics.checkNotNullParameter((Object)$this$convertToDisplay, (String)"<this>");
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Pokemon result = Pokemon.clone$default(pokemon, false, false, 3, null);
            $this$convertToDisplay.getResult().apply(result);
            CobblemonEvolutionDisplay expectedDisplay = new CobblemonEvolutionDisplay($this$convertToDisplay.getId(), result);
            EvolutionDisplayEvent event = new EvolutionDisplayEvent(result, expectedDisplay, $this$convertToDisplay);
            EventObservable<EvolutionDisplayEvent> eventObservable = CobblemonEvents.EVOLUTION_DISPLAY;
            EvolutionDisplayEvent[] evolutionDisplayEventArray = new EvolutionDisplayEvent[]{event};
            EvolutionDisplayEvent[] events$iv = evolutionDisplayEventArray;
            boolean $i$f$post = false;
            $this$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
            EvolutionDisplayEvent[] $this$forEach$iv$iv = events$iv;
            boolean $i$f$forEach = false;
            int n = $this$forEach$iv$iv.length;
            for (int i = 0; i < n; ++i) {
                EvolutionDisplayEvent element$iv$iv;
                EvolutionDisplayEvent evolutionDisplayEvent = element$iv$iv = $this$forEach$iv$iv[i];
                boolean bl = false;
                EvolutionDisplayEvent it = evolutionDisplayEvent;
            }
            return event.getDisplay();
        }

        public final void encode$common(@NotNull EvolutionDisplay $this$encode, @NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)$this$encode, (String)"<this>");
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            buffer.m_130070_($this$encode.getId());
            buffer.m_130085_($this$encode.getSpecies().getResourceIdentifier());
            buffer.m_236828_((Collection)$this$encode.getAspects(), Companion::encode$lambda$0);
        }

        @NotNull
        public final EvolutionDisplay decodeDisplay$common(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            String id = buffer.m_130277_();
            ResourceLocation speciesIdentifier = buffer.m_130281_();
            Intrinsics.checkNotNullExpressionValue((Object)speciesIdentifier, (String)"speciesIdentifier");
            Species species = PokemonSpecies.INSTANCE.getByIdentifier(speciesIdentifier);
            if (species == null) {
                throw new IllegalArgumentException("Cannot resolve species from " + speciesIdentifier);
            }
            Species species2 = species;
            List list = buffer.m_236845_(FriendlyByteBuf::m_130277_);
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList(PacketByteBuf::readString)");
            Set aspects = CollectionsKt.toSet((Iterable)list);
            Intrinsics.checkNotNullExpressionValue((Object)id, (String)"id");
            return new CobblemonEvolutionDisplay(id, species2, aspects);
        }

        private static final void encode$lambda$0(FriendlyByteBuf pb, String value2) {
            pb.m_130070_(value2);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

