/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
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
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\n\u0018\u00002\u00020\u00012\u00020\u0002B\t\b\u0016\u00a2\u0006\u0004\b,\u0010-B\u0019\b\u0016\u0012\u0006\u0010 \u001a\u00020\b\u0012\u0006\u0010&\u001a\u00020%\u00a2\u0006\u0004\b,\u0010.B\u000f\u0012\u0006\u0010 \u001a\u00020\b\u00a2\u0006\u0004\b,\u0010$J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u000f\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\r\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u000f\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u000eJ\u0017\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0017\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u001e\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fR\"\u0010 \u001a\u00020\b8\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b \u0010!\u001a\u0004\b\"\u0010\n\"\u0004\b#\u0010$R\"\u0010&\u001a\u00020%8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+\u00a8\u0006/"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/feature/IntSpeciesFeature;", "Lcom/cobblemon/mod/common/api/pokemon/feature/SynchronizedSpeciesFeature;", "Lcom/cobblemon/mod/common/api/properties/CustomPokemonProperty;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "apply", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "", "asString", "()Ljava/lang/String;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "Lcom/google/gson/JsonObject;", "pokemonJSON", "Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeature;", "loadFromJSON", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeature;", "Lnet/minecraft/nbt/CompoundTag;", "pokemonNBT", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/pokemon/feature/SynchronizedSpeciesFeature;", "", "matches", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "saveToJSON", "(Lcom/google/gson/JsonObject;)Lcom/google/gson/JsonObject;", "saveToNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", "name", "Ljava/lang/String;", "getName", "setName", "(Ljava/lang/String;)V", "", "value", "I", "getValue", "()I", "setValue", "(I)V", "<init>", "()V", "(Ljava/lang/String;I)V", "common"})
public final class IntSpeciesFeature
implements SynchronizedSpeciesFeature,
CustomPokemonProperty {
    @NotNull
    private String name;
    private int value;

    public IntSpeciesFeature(@NotNull String name) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        this.name = name;
    }

    @Override
    @NotNull
    public String getName() {
        return this.name;
    }

    public void setName(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.name = string;
    }

    public final int getValue() {
        return this.value;
    }

    public final void setValue(int n) {
        this.value = n;
    }

    public IntSpeciesFeature() {
        this("");
    }

    public IntSpeciesFeature(@NotNull String name, int value2) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        this(name);
        this.value = value2;
    }

    @Override
    @NotNull
    public CompoundTag saveToNBT(@NotNull CompoundTag pokemonNBT) {
        Intrinsics.checkNotNullParameter((Object)pokemonNBT, (String)"pokemonNBT");
        pokemonNBT.m_128405_(this.getName(), this.value);
        return pokemonNBT;
    }

    @Override
    @NotNull
    public SynchronizedSpeciesFeature loadFromNBT(@NotNull CompoundTag pokemonNBT) {
        Intrinsics.checkNotNullParameter((Object)pokemonNBT, (String)"pokemonNBT");
        this.value = pokemonNBT.m_128451_(this.getName());
        return this;
    }

    @Override
    @NotNull
    public JsonObject saveToJSON(@NotNull JsonObject pokemonJSON) {
        Intrinsics.checkNotNullParameter((Object)pokemonJSON, (String)"pokemonJSON");
        pokemonJSON.addProperty(this.getName(), (Number)this.value);
        return pokemonJSON;
    }

    @Override
    @NotNull
    public SpeciesFeature loadFromJSON(@NotNull JsonObject pokemonJSON) {
        Intrinsics.checkNotNullParameter((Object)pokemonJSON, (String)"pokemonJSON");
        this.value = pokemonJSON.get(this.getName()).getAsInt();
        return this;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.writeInt(this.value);
    }

    @Override
    public void decode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.value = buffer.readInt();
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
            IntSpeciesFeature existingFeature = (IntSpeciesFeature)pokemon.getFeature(this.getName());
            if (existingFeature != null) {
                existingFeature.value = this.value;
            } else {
                pokemon.getFeatures().add(new IntSpeciesFeature(this.getName(), this.value));
            }
            pokemon.updateAspects();
        }
    }

    @Override
    public boolean matches(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        IntSpeciesFeature intSpeciesFeature = (IntSpeciesFeature)pokemon.getFeature(this.getName());
        return intSpeciesFeature != null ? intSpeciesFeature.value == this.value : false;
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

