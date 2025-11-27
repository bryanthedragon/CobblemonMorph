/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.DefeatRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.google.gson.JsonObject;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\u0018\u0000 !2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002!\"B\u0007\u00a2\u0006\u0004\b \u0010\u0012J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000f\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u000f\u0010\u0011\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0013\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0015\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0017\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u0018\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0017\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001eR\u0016\u0010\u001c\u001a\u00020\u00028\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\u001f\u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/progress/DefeatEvolutionProgress;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/progress/EvolutionProgress;", "Lcom/cobblemon/mod/common/pokemon/evolution/progress/DefeatEvolutionProgress$Progress;", "currentProgress", "()Lcom/cobblemon/mod/common/pokemon/evolution/progress/DefeatEvolutionProgress$Progress;", "Lnet/minecraft/resources/ResourceLocation;", "id", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/google/gson/JsonObject;", "json", "", "loadFromJson", "(Lcom/google/gson/JsonObject;)V", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)V", "reset", "()V", "saveToJson", "()Lcom/google/gson/JsonObject;", "saveToNBT", "()Lnet/minecraft/nbt/CompoundTag;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "shouldKeep", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "progress", "updateProgress", "(Lcom/cobblemon/mod/common/pokemon/evolution/progress/DefeatEvolutionProgress$Progress;)V", "Lcom/cobblemon/mod/common/pokemon/evolution/progress/DefeatEvolutionProgress$Progress;", "<init>", "Companion", "Progress", "common"})
@SourceDebugExtension(value={"SMAP\nDefeatEvolutionProgress.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DefeatEvolutionProgress.kt\ncom/cobblemon/mod/common/pokemon/evolution/progress/DefeatEvolutionProgress\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,87:1\n1747#2,2:88\n1747#2,3:90\n1749#2:93\n*S KotlinDebug\n*F\n+ 1 DefeatEvolutionProgress.kt\ncom/cobblemon/mod/common/pokemon/evolution/progress/DefeatEvolutionProgress\n*L\n43#1:88,2\n44#1:90,3\n43#1:93\n*E\n"})
public final class DefeatEvolutionProgress
implements EvolutionProgress<Progress> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Progress progress = new Progress(new PokemonProperties(), 0);
    @NotNull
    private static final ResourceLocation ID = MiscUtils.cobblemonResource("defeat");
    @NotNull
    private static final String TARGET = "target";
    @NotNull
    private static final String AMOUNT = "amount";

    @Override
    @NotNull
    public ResourceLocation id() {
        return ID;
    }

    @Override
    @NotNull
    public Progress currentProgress() {
        return this.progress;
    }

    @Override
    public void updateProgress(@NotNull Progress progress2) {
        Intrinsics.checkNotNullParameter((Object)progress2, (String)"progress");
        this.progress = progress2;
    }

    @Override
    public void reset() {
        this.progress = new Progress(new PokemonProperties(), 0);
    }

    @Override
    public boolean shouldKeep(@NotNull Pokemon pokemon) {
        boolean bl;
        block7: {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Iterable $this$any$iv = pokemon.getForm().getEvolutions();
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    boolean bl2;
                    block6: {
                        Evolution evolution = (Evolution)element$iv;
                        boolean bl3 = false;
                        Iterable $this$any$iv2 = evolution.getRequirements();
                        boolean $i$f$any2 = false;
                        if ($this$any$iv2 instanceof Collection && ((Collection)$this$any$iv2).isEmpty()) {
                            bl2 = false;
                        } else {
                            for (Object element$iv2 : $this$any$iv2) {
                                EvolutionRequirement requirement = (EvolutionRequirement)element$iv2;
                                boolean bl4 = false;
                                if (!(requirement instanceof DefeatRequirement && StringsKt.equals((String)((DefeatRequirement)requirement).getTarget().getOriginalString(), (String)this.progress.getTarget().getOriginalString(), (boolean)true))) continue;
                                bl2 = true;
                                break block6;
                            }
                            bl2 = false;
                        }
                    }
                    if (!bl2) continue;
                    bl = true;
                    break block7;
                }
                bl = false;
            }
        }
        return bl;
    }

    @Override
    public void loadFromNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        String string = nbt.m_128461_(TARGET);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"nbt.getString(TARGET)");
        PokemonProperties target = PokemonProperties.Companion.parse$default(PokemonProperties.Companion, string, null, null, 6, null);
        int amount = nbt.m_128451_(AMOUNT);
        this.updateProgress(new Progress(target, amount));
    }

    @Override
    @NotNull
    public CompoundTag saveToNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.m_128359_(TARGET, this.currentProgress().getTarget().getOriginalString());
        nbt.m_128405_(AMOUNT, this.currentProgress().getAmount());
        return nbt;
    }

    @Override
    public void loadFromJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        String string = json.get(TARGET).getAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"json.get(TARGET).asString");
        PokemonProperties target = PokemonProperties.Companion.parse$default(PokemonProperties.Companion, string, null, null, 6, null);
        int amount = json.get(AMOUNT).getAsInt();
        this.updateProgress(new Progress(target, amount));
    }

    @Override
    @NotNull
    public JsonObject saveToJson() {
        JsonObject jObject = new JsonObject();
        jObject.addProperty(TARGET, this.currentProgress().getTarget().getOriginalString());
        jObject.addProperty(AMOUNT, (Number)this.currentProgress().getAmount());
        return jObject;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0017\u0010\u0006\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\n\u0010\u0004\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/progress/DefeatEvolutionProgress$Companion;", "", "", "AMOUNT", "Ljava/lang/String;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "TARGET", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J$\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\b\u001a\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000e\u001a\u00020\r2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0010\u001a\u00020\u0005H\u00d6\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0007J\u0010\u0010\u0012\u001a\u00020\u0011H\u00d6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u0017\u0010\t\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0014\u001a\u0004\b\u0015\u0010\u0007R\u0017\u0010\b\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\u0016\u001a\u0004\b\u0017\u0010\u0004\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/progress/DefeatEvolutionProgress$Progress;", "", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "component1", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "", "component2", "()I", "target", "amount", "copy", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;I)Lcom/cobblemon/mod/common/pokemon/evolution/progress/DefeatEvolutionProgress$Progress;", "other", "", "equals", "(Ljava/lang/Object;)Z", "hashCode", "", "toString", "()Ljava/lang/String;", "I", "getAmount", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getTarget", "<init>", "(Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;I)V", "common"})
    public static final class Progress {
        @NotNull
        private final PokemonProperties target;
        private final int amount;

        public Progress(@NotNull PokemonProperties target, int amount) {
            Intrinsics.checkNotNullParameter((Object)target, (String)DefeatEvolutionProgress.TARGET);
            this.target = target;
            this.amount = amount;
        }

        @NotNull
        public final PokemonProperties getTarget() {
            return this.target;
        }

        public final int getAmount() {
            return this.amount;
        }

        @NotNull
        public final PokemonProperties component1() {
            return this.target;
        }

        public final int component2() {
            return this.amount;
        }

        @NotNull
        public final Progress copy(@NotNull PokemonProperties target, int amount) {
            Intrinsics.checkNotNullParameter((Object)target, (String)DefeatEvolutionProgress.TARGET);
            return new Progress(target, amount);
        }

        public static /* synthetic */ Progress copy$default(Progress progress2, PokemonProperties pokemonProperties, int n, int n2, Object object) {
            if ((n2 & 1) != 0) {
                pokemonProperties = progress2.target;
            }
            if ((n2 & 2) != 0) {
                n = progress2.amount;
            }
            return progress2.copy(pokemonProperties, n);
        }

        @NotNull
        public String toString() {
            return "Progress(target=" + this.target + ", amount=" + this.amount + ")";
        }

        public int hashCode() {
            int result = this.target.hashCode();
            result = result * 31 + Integer.hashCode(this.amount);
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Progress)) {
                return false;
            }
            Progress progress2 = (Progress)other;
            if (!Intrinsics.areEqual((Object)this.target, (Object)progress2.target)) {
                return false;
            }
            return this.amount == progress2.amount;
        }
    }
}

