/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.progress;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.DamageTakenRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.google.gson.JsonObject;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\u0018\u0000 !2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002!\"B\u0007\u00a2\u0006\u0004\b \u0010\u0012J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000f\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u000f\u0010\u0011\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0013\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0015\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0017\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u0018\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0017\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u001d\u0010\u001eR\u0016\u0010\u001c\u001a\u00020\u00028\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\u001f\u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/progress/DamageTakenEvolutionProgress;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/progress/EvolutionProgress;", "Lcom/cobblemon/mod/common/pokemon/evolution/progress/DamageTakenEvolutionProgress$Progress;", "currentProgress", "()Lcom/cobblemon/mod/common/pokemon/evolution/progress/DamageTakenEvolutionProgress$Progress;", "Lnet/minecraft/resources/ResourceLocation;", "id", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/google/gson/JsonObject;", "json", "", "loadFromJson", "(Lcom/google/gson/JsonObject;)V", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)V", "reset", "()V", "saveToJson", "()Lcom/google/gson/JsonObject;", "saveToNBT", "()Lnet/minecraft/nbt/CompoundTag;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "shouldKeep", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "progress", "updateProgress", "(Lcom/cobblemon/mod/common/pokemon/evolution/progress/DamageTakenEvolutionProgress$Progress;)V", "Lcom/cobblemon/mod/common/pokemon/evolution/progress/DamageTakenEvolutionProgress$Progress;", "<init>", "Companion", "Progress", "common"})
@SourceDebugExtension(value={"SMAP\nDamageTakenEvolutionProgress.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DamageTakenEvolutionProgress.kt\ncom/cobblemon/mod/common/pokemon/evolution/progress/DamageTakenEvolutionProgress\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,74:1\n1#2:75\n*E\n"})
public final class DamageTakenEvolutionProgress
implements EvolutionProgress<Progress> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Progress progress = new Progress(0);
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("damage_taken");
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
        this.updateProgress(new Progress(0));
    }

    @Override
    public boolean shouldKeep(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return Companion.supports(pokemon);
    }

    @Override
    public void loadFromNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        int amount = nbt.m_128451_(AMOUNT);
        this.updateProgress(new Progress(amount));
    }

    @Override
    @NotNull
    public CompoundTag saveToNBT() {
        CompoundTag compoundTag;
        CompoundTag $this$saveToNBT_u24lambda_u240 = compoundTag = new CompoundTag();
        boolean bl = false;
        $this$saveToNBT_u24lambda_u240.m_128405_(AMOUNT, this.currentProgress().getAmount());
        return compoundTag;
    }

    @Override
    public void loadFromJson(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        int amount = json.get(AMOUNT).getAsInt();
        this.updateProgress(new Progress(amount));
    }

    @Override
    @NotNull
    public JsonObject saveToJson() {
        JsonObject jsonObject;
        JsonObject $this$saveToJson_u24lambda_u241 = jsonObject = new JsonObject();
        boolean bl = false;
        $this$saveToJson_u24lambda_u241.addProperty(AMOUNT, (Number)this.currentProgress().getAmount());
        return jsonObject;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\b\u001a\u00020\u00078\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\b\u0010\tR\u0017\u0010\u000b\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/progress/DamageTakenEvolutionProgress$Companion;", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "supports", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "", "AMOUNT", "Ljava/lang/String;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nDamageTakenEvolutionProgress.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DamageTakenEvolutionProgress.kt\ncom/cobblemon/mod/common/pokemon/evolution/progress/DamageTakenEvolutionProgress$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,74:1\n1747#2,2:75\n1747#2,3:77\n1749#2:80\n*S KotlinDebug\n*F\n+ 1 DamageTakenEvolutionProgress.kt\ncom/cobblemon/mod/common/pokemon/evolution/progress/DamageTakenEvolutionProgress$Companion\n*L\n65#1:75,2\n66#1:77,3\n65#1:80\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        public final boolean supports(@NotNull Pokemon pokemon) {
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
                                    if (!(requirement instanceof DamageTakenRequirement)) continue;
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

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\n\u001a\u00020\t2\b\u0010\b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0010\u0010\f\u001a\u00020\u0002H\u00d6\u0001\u00a2\u0006\u0004\b\f\u0010\u0004J\u0010\u0010\u000e\u001a\u00020\rH\u00d6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0005\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0010\u001a\u0004\b\u0011\u0010\u0004\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/progress/DamageTakenEvolutionProgress$Progress;", "", "", "component1", "()I", "amount", "copy", "(I)Lcom/cobblemon/mod/common/pokemon/evolution/progress/DamageTakenEvolutionProgress$Progress;", "other", "", "equals", "(Ljava/lang/Object;)Z", "hashCode", "", "toString", "()Ljava/lang/String;", "I", "getAmount", "<init>", "(I)V", "common"})
    public static final class Progress {
        private final int amount;

        public Progress(int amount) {
            this.amount = amount;
        }

        public final int getAmount() {
            return this.amount;
        }

        public final int component1() {
            return this.amount;
        }

        @NotNull
        public final Progress copy(int amount) {
            return new Progress(amount);
        }

        public static /* synthetic */ Progress copy$default(Progress progress2, int n, int n2, Object object) {
            if ((n2 & 1) != 0) {
                n = progress2.amount;
            }
            return progress2.copy(n);
        }

        @NotNull
        public String toString() {
            return "Progress(amount=" + this.amount + ")";
        }

        public int hashCode() {
            return Integer.hashCode(this.amount);
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Progress)) {
                return false;
            }
            Progress progress2 = (Progress)other;
            return this.amount == progress2.amount;
        }
    }
}

