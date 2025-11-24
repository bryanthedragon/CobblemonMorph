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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonFloatingState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000x\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001Bc\u0012\u0006\u0010R\u001a\u00020Q\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\u0006\u00101\u001a\u000200\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u001d\u001a\u00020\u001c\u0012\u0006\u0010-\u001a\u00020\u001c\u0012\u0006\u0010$\u001a\u00020#\u0012\b\u0010K\u001a\u0004\u0018\u00010J\u0012\u0012\u0010=\u001a\u000e\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u00020)0;\u00a2\u0006\u0004\bV\u0010WR\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR(\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\"\u0010\u0012\u001a\u00020\u00118\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0011\u0010\u001b\u001a\u00020\u00188F\u00a2\u0006\u0006\u001a\u0004\b\u0019\u0010\u001aR\"\u0010\u001d\u001a\u00020\u001c8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\"\u0010$\u001a\u00020#8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b$\u0010%\u001a\u0004\b$\u0010&\"\u0004\b'\u0010(R\u0011\u0010,\u001a\u00020)8F\u00a2\u0006\u0006\u001a\u0004\b*\u0010+R\"\u0010-\u001a\u00020\u001c8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b-\u0010\u001e\u001a\u0004\b.\u0010 \"\u0004\b/\u0010\"R\"\u00101\u001a\u0002008\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b1\u00102\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u0011\u0010:\u001a\u0002078F\u00a2\u0006\u0006\u001a\u0004\b8\u00109R.\u0010=\u001a\u000e\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u00020)0;8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b=\u0010>\u001a\u0004\b?\u0010@\"\u0004\bA\u0010BR\"\u0010D\u001a\u00020C8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bD\u0010E\u001a\u0004\bF\u0010G\"\u0004\bH\u0010IR$\u0010K\u001a\u0004\u0018\u00010J8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bK\u0010L\u001a\u0004\bM\u0010N\"\u0004\bO\u0010PR\u0017\u0010R\u001a\u00020Q8\u0006\u00a2\u0006\f\n\u0004\bR\u0010S\u001a\u0004\bT\u0010U\u00a8\u0006X"}, d2={"Lcom/cobblemon/mod/common/client/battle/ClientBattlePokemon;", "", "Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;", "actor", "Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;", "getActor", "()Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;", "setActor", "(Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;)V", "", "", "aspects", "Ljava/util/Set;", "getAspects", "()Ljava/util/Set;", "setAspects", "(Ljava/util/Set;)V", "Lnet/minecraft/network/chat/MutableComponent;", "displayName", "Lnet/minecraft/network/chat/MutableComponent;", "getDisplayName", "()Lnet/minecraft/network/chat/MutableComponent;", "setDisplayName", "(Lnet/minecraft/network/chat/MutableComponent;)V", "Lcom/cobblemon/mod/common/pokemon/Gender;", "getGender", "()Lcom/cobblemon/mod/common/pokemon/Gender;", "gender", "", "hpValue", "F", "getHpValue", "()F", "setHpValue", "(F)V", "", "isHpFlat", "Z", "()Z", "setHpFlat", "(Z)V", "", "getLevel", "()I", "level", "maxHp", "getMaxHp", "setMaxHp", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "properties", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getProperties", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "setProperties", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;)V", "Lcom/cobblemon/mod/common/pokemon/Species;", "getSpecies", "()Lcom/cobblemon/mod/common/pokemon/Species;", "species", "", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "statChanges", "Ljava/util/Map;", "getStatChanges", "()Ljava/util/Map;", "setStatChanges", "(Ljava/util/Map;)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonFloatingState;", "state", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonFloatingState;", "getState", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonFloatingState;", "setState", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonFloatingState;)V", "Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;", "status", "Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;", "getStatus", "()Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;", "setStatus", "(Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;)V", "Ljava/util/UUID;", "uuid", "Ljava/util/UUID;", "getUuid", "()Ljava/util/UUID;", "<init>", "(Ljava/util/UUID;Lnet/minecraft/network/chat/MutableComponent;Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;Ljava/util/Set;FFZLcom/cobblemon/mod/common/pokemon/status/PersistentStatus;Ljava/util/Map;)V", "common"})
public final class ClientBattlePokemon {
    @NotNull
    private final UUID uuid;
    @NotNull
    private MutableComponent displayName;
    @NotNull
    private PokemonProperties properties;
    @NotNull
    private Set<String> aspects;
    private float hpValue;
    private float maxHp;
    private boolean isHpFlat;
    @Nullable
    private PersistentStatus status;
    @NotNull
    private Map<Stat, Integer> statChanges;
    public ClientBattleActor actor;
    @NotNull
    private PokemonFloatingState state;

    public ClientBattlePokemon(@NotNull UUID uuid2, @NotNull MutableComponent displayName, @NotNull PokemonProperties properties2, @NotNull Set<String> aspects, float hpValue, float maxHp, boolean isHpFlat, @Nullable PersistentStatus status, @NotNull Map<Stat, Integer> statChanges) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
        Intrinsics.checkNotNullParameter((Object)properties2, (String)"properties");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        Intrinsics.checkNotNullParameter(statChanges, (String)"statChanges");
        this.uuid = uuid2;
        this.displayName = displayName;
        this.properties = properties2;
        this.aspects = aspects;
        this.hpValue = hpValue;
        this.maxHp = maxHp;
        this.isHpFlat = isHpFlat;
        this.status = status;
        this.statChanges = statChanges;
        this.state = new PokemonFloatingState();
    }

    @NotNull
    public final UUID getUuid() {
        return this.uuid;
    }

    @NotNull
    public final MutableComponent getDisplayName() {
        return this.displayName;
    }

    public final void setDisplayName(@NotNull MutableComponent mutableComponent) {
        Intrinsics.checkNotNullParameter((Object)mutableComponent, (String)"<set-?>");
        this.displayName = mutableComponent;
    }

    @NotNull
    public final PokemonProperties getProperties() {
        return this.properties;
    }

    public final void setProperties(@NotNull PokemonProperties pokemonProperties) {
        Intrinsics.checkNotNullParameter((Object)pokemonProperties, (String)"<set-?>");
        this.properties = pokemonProperties;
    }

    @NotNull
    public final Set<String> getAspects() {
        return this.aspects;
    }

    public final void setAspects(@NotNull Set<String> set2) {
        Intrinsics.checkNotNullParameter(set2, (String)"<set-?>");
        this.aspects = set2;
    }

    public final float getHpValue() {
        return this.hpValue;
    }

    public final void setHpValue(float f) {
        this.hpValue = f;
    }

    public final float getMaxHp() {
        return this.maxHp;
    }

    public final void setMaxHp(float f) {
        this.maxHp = f;
    }

    public final boolean isHpFlat() {
        return this.isHpFlat;
    }

    public final void setHpFlat(boolean bl) {
        this.isHpFlat = bl;
    }

    @Nullable
    public final PersistentStatus getStatus() {
        return this.status;
    }

    public final void setStatus(@Nullable PersistentStatus persistentStatus) {
        this.status = persistentStatus;
    }

    @NotNull
    public final Map<Stat, Integer> getStatChanges() {
        return this.statChanges;
    }

    public final void setStatChanges(@NotNull Map<Stat, Integer> map) {
        Intrinsics.checkNotNullParameter(map, (String)"<set-?>");
        this.statChanges = map;
    }

    @NotNull
    public final ClientBattleActor getActor() {
        ClientBattleActor clientBattleActor = this.actor;
        if (clientBattleActor != null) {
            return clientBattleActor;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"actor");
        return null;
    }

    public final void setActor(@NotNull ClientBattleActor clientBattleActor) {
        Intrinsics.checkNotNullParameter((Object)clientBattleActor, (String)"<set-?>");
        this.actor = clientBattleActor;
    }

    @NotNull
    public final Species getSpecies() {
        String string = this.properties.getSpecies();
        Intrinsics.checkNotNull((Object)string);
        Species species = PokemonSpecies.INSTANCE.getByIdentifier(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(string, null, 1, null));
        Intrinsics.checkNotNull((Object)species);
        return species;
    }

    public final int getLevel() {
        Integer n = this.properties.getLevel();
        return n != null ? n : 0;
    }

    @NotNull
    public final Gender getGender() {
        Gender gender = this.properties.getGender();
        if (gender == null) {
            gender = Gender.GENDERLESS;
        }
        return gender;
    }

    @NotNull
    public final PokemonFloatingState getState() {
        return this.state;
    }

    public final void setState(@NotNull PokemonFloatingState pokemonFloatingState) {
        Intrinsics.checkNotNullParameter((Object)pokemonFloatingState, (String)"<set-?>");
        this.state = pokemonFloatingState;
    }
}

