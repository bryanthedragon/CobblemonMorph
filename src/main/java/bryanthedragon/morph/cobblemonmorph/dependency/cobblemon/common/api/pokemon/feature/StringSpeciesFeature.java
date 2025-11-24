/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonObject;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0010\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\u0006\u0010 \u001a\u00020\b\u0012\u0006\u0010#\u001a\u00020\b\u00a2\u0006\u0004\b'\u0010(J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u000f\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\r\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u000f\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u000eJ\u0017\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0017\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\u00020\b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010\nR\"\u0010#\u001a\u00020\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b#\u0010!\u001a\u0004\b$\u0010\n\"\u0004\b%\u0010&\u00a8\u0006)"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/feature/StringSpeciesFeature;", "Lcom/cobblemon/mod/common/api/pokemon/feature/SynchronizedSpeciesFeature;", "Lcom/cobblemon/mod/common/api/properties/CustomPokemonProperty;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "apply", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "", "asString", "()Ljava/lang/String;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "Lcom/google/gson/JsonObject;", "pokemonJSON", "Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeature;", "loadFromJSON", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeature;", "Lnet/minecraft/nbt/CompoundTag;", "pokemonNBT", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeature;", "", "matches", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "saveToJSON", "(Lcom/google/gson/JsonObject;)Lcom/google/gson/JsonObject;", "saveToNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", "name", "Ljava/lang/String;", "getName", "value", "getValue", "setValue", "(Ljava/lang/String;)V", "<init>", "(Ljava/lang/String;Ljava/lang/String;)V", "common"})
@SourceDebugExtension(value={"SMAP\nStringSpeciesFeature.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StringSpeciesFeature.kt\ncom/cobblemon/mod/common/api/pokemon/feature/StringSpeciesFeature\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,71:1\n1#2:72\n*E\n"})
public final class StringSpeciesFeature
implements SynchronizedSpeciesFeature,
CustomPokemonProperty {
    @NotNull
    private final String name;
    @NotNull
    private String value;

    public StringSpeciesFeature(@NotNull String name, @NotNull String value2) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        this.name = name;
        this.value = value2;
    }

    @Override
    @NotNull
    public String getName() {
        return this.name;
    }

    @NotNull
    public final String getValue() {
        return this.value;
    }

    public final void setValue(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.value = string;
    }

    @Override
    @NotNull
    public CompoundTag saveToNBT(@NotNull CompoundTag pokemonNBT) {
        Intrinsics.checkNotNullParameter((Object)pokemonNBT, (String)"pokemonNBT");
        pokemonNBT.m_128359_(this.getName(), this.value);
        return pokemonNBT;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public SpeciesFeature loadFromNBT(@NotNull CompoundTag pokemonNBT) {
        String string;
        block4: {
            block3: {
                void it;
                String string2;
                Intrinsics.checkNotNullParameter((Object)pokemonNBT, (String)"pokemonNBT");
                StringSpeciesFeature stringSpeciesFeature = this;
                string = pokemonNBT.m_128461_(this.getName());
                if (string == null) break block3;
                String string3 = string2 = string;
                StringSpeciesFeature stringSpeciesFeature2 = stringSpeciesFeature;
                boolean bl = false;
                boolean bl2 = !StringsKt.isBlank((CharSequence)((CharSequence)it));
                stringSpeciesFeature = stringSpeciesFeature2;
                string = bl2 ? string2 : null;
                if (string == null) break block3;
                String string4 = string.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                string = string4;
                if (string4 != null) break block4;
            }
            return this;
        }
        stringSpeciesFeature.value = string;
        return this;
    }

    @Override
    @NotNull
    public JsonObject saveToJSON(@NotNull JsonObject pokemonJSON) {
        Intrinsics.checkNotNullParameter((Object)pokemonJSON, (String)"pokemonJSON");
        pokemonJSON.addProperty(this.getName(), this.value);
        return pokemonJSON;
    }

    @Override
    @NotNull
    public SpeciesFeature loadFromJSON(@NotNull JsonObject pokemonJSON) {
        Object object;
        block3: {
            block2: {
                Intrinsics.checkNotNullParameter((Object)pokemonJSON, (String)"pokemonJSON");
                object = pokemonJSON.get(this.getName());
                if (object == null || (object = object.getAsString()) == null) break block2;
                String string = ((String)object).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
                object = string;
                if (string != null) break block3;
            }
            return this;
        }
        this.value = object;
        return this;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(this.value);
    }

    @Override
    public void decode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        String string = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
        this.value = string;
    }

    @Override
    @NotNull
    public String asString() {
        return this.getName() + "=" + this.value;
    }

    @Override
    public void apply(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        SpeciesFeatureProvider<? extends SpeciesFeature> speciesFeatureProvider = SpeciesFeatures.INSTANCE.getFeature(this.getName());
        if (speciesFeatureProvider == null) {
            return;
        }
        SpeciesFeatureProvider<? extends SpeciesFeature> featureProvider = speciesFeatureProvider;
        if (SpeciesFeatures.INSTANCE.getFeaturesFor(pokemon.getSpecies()).contains(featureProvider)) {
            StringSpeciesFeature existingFeature = (StringSpeciesFeature)pokemon.getFeature(this.getName());
            if (existingFeature != null) {
                existingFeature.value = this.value;
            } else {
                pokemon.getFeatures().add(new StringSpeciesFeature(this.getName(), this.value));
            }
            pokemon.updateAspects();
        }
    }

    @Override
    public boolean matches(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        StringSpeciesFeature stringSpeciesFeature = (StringSpeciesFeature)pokemon.getFeature(this.getName());
        return Intrinsics.areEqual((Object)(stringSpeciesFeature != null ? stringSpeciesFeature.value : null), (Object)this.value);
    }

    @Override
    public void apply(@NotNull PokemonEntity pokemonEntity) {
        CustomPokemonProperty.DefaultImpls.apply(this, pokemonEntity);
    }

    @Override
    public boolean matches(@NotNull PokemonEntity pokemonEntity) {
        return CustomPokemonProperty.DefaultImpls.matches(this, pokemonEntity);
    }
}

