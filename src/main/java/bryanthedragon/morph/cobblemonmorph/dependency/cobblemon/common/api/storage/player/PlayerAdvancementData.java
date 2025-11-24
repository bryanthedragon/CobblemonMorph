/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.advancements.Advancement
 *  net.minecraft.advancements.Criterion
 *  net.minecraft.advancements.CriterionTriggerInstance
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.AspectCriterionCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0010\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0002\b\u001d\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b;\u0010\u000fJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001d\u0010\f\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\f\u0010\rJ\r\u0010\u000e\u001a\u00020\u000b\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\r\u0010\u0010\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0010\u0010\u000fJ\u0015\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u0011\u0010\u0012J\r\u0010\u0013\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0013\u0010\u000fJ\r\u0010\u0014\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0014\u0010\u000fJ\r\u0010\u0015\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0015\u0010\u000fJ\r\u0010\u0016\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0016\u0010\u000fJ\r\u0010\u0017\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0017\u0010\u000fJ\r\u0010\u0018\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0018\u0010\u000fJ\r\u0010\u0019\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0019\u0010\u000fJ\u0015\u0010\u001a\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u001a\u0010\u001bRH\u0010!\u001a\u0014\u0012\u0004\u0012\u00020\u001d\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u001e0\u001c2\u0018\u0010 \u001a\u0014\u0012\u0004\u0012\u00020\u001d\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001f0\u001e0\u001c8\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$R$\u0010%\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u00048\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(R$\u0010)\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u00048\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b)\u0010&\u001a\u0004\b*\u0010(R\"\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u00040\u001c8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b+\u0010\"R$\u0010,\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u00048\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b,\u0010&\u001a\u0004\b-\u0010(R$\u0010.\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u00048\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b.\u0010&\u001a\u0004\b/\u0010(R$\u00100\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u00048\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b0\u0010&\u001a\u0004\b1\u0010(R$\u00102\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u00048\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b2\u0010&\u001a\u0004\b3\u0010(R$\u00104\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u00048\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b4\u0010&\u001a\u0004\b5\u0010(R$\u00106\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u00048\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b6\u0010&\u001a\u0004\b7\u0010(R$\u00108\u001a\u00020\u00042\u0006\u0010 \u001a\u00020\u00048\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\b8\u0010&\u001a\u0004\b9\u0010(R\"\u0010:\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u00040\u001c8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b:\u0010\"\u00a8\u0006<"}, d2={"Lcom/cobblemon/mod/common/api/storage/player/PlayerAdvancementData;", "", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "type", "", "getTotalTypeCaptureCount", "(Lcom/cobblemon/mod/common/api/types/ElementalType;)I", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "updateAspectsCollected", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "updateTotalBattleVictoryCount", "()V", "updateTotalCaptureCount", "updateTotalDefeatedCount", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "updateTotalEggsHatched", "updateTotalEvolvedCount", "updateTotalPvNBattleVictoryCount", "updateTotalPvPBattleVictoryCount", "updateTotalPvWBattleVictoryCount", "updateTotalShinyCaptureCount", "updateTotalTradedCount", "updateTotalTypeCaptureCount", "(Lcom/cobblemon/mod/common/api/types/ElementalType;)V", "", "Lnet/minecraft/resources/ResourceLocation;", "", "", "<set-?>", "aspectsCollected", "Ljava/util/Map;", "getAspectsCollected", "()Ljava/util/Map;", "totalBattleVictoryCount", "I", "getTotalBattleVictoryCount", "()I", "totalCaptureCount", "getTotalCaptureCount", "totalDefeatedCounts", "totalEggsHatched", "getTotalEggsHatched", "totalEvolvedCount", "getTotalEvolvedCount", "totalPvNBattleVictoryCount", "getTotalPvNBattleVictoryCount", "totalPvPBattleVictoryCount", "getTotalPvPBattleVictoryCount", "totalPvWBattleVictoryCount", "getTotalPvWBattleVictoryCount", "totalShinyCaptureCount", "getTotalShinyCaptureCount", "totalTradedCount", "getTotalTradedCount", "totalTypeCaptureCounts", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nPlayerAdvancementData.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PlayerAdvancementData.kt\ncom/cobblemon/mod/common/api/storage/player/PlayerAdvancementData\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,119:1\n1360#2:120\n1446#2,5:121\n1603#2,9:126\n1855#2:135\n1856#2:137\n1612#2:138\n800#2,11:139\n766#2:150\n857#2,2:151\n1360#2:153\n1446#2,5:154\n766#2:166\n857#2,2:167\n1855#2,2:169\n1#3:136\n361#4,7:159\n*S KotlinDebug\n*F\n+ 1 PlayerAdvancementData.kt\ncom/cobblemon/mod/common/api/storage/player/PlayerAdvancementData\n*L\n106#1:120\n106#1:121,5\n107#1:126,9\n107#1:135\n107#1:137\n107#1:138\n108#1:139,11\n111#1:150\n111#1:151,2\n112#1:153\n112#1:154,5\n116#1:166\n116#1:167,2\n116#1:169,2\n107#1:136\n115#1:159,7\n*E\n"})
public final class PlayerAdvancementData {
    private int totalCaptureCount;
    private int totalEggsHatched;
    private int totalEvolvedCount;
    private int totalBattleVictoryCount;
    private int totalPvPBattleVictoryCount;
    private int totalPvWBattleVictoryCount;
    private int totalPvNBattleVictoryCount;
    private int totalShinyCaptureCount;
    private int totalTradedCount;
    @NotNull
    private Map<String, Integer> totalTypeCaptureCounts = new LinkedHashMap();
    @NotNull
    private Map<ResourceLocation, Integer> totalDefeatedCounts = new LinkedHashMap();
    @NotNull
    private Map<ResourceLocation, Set<String>> aspectsCollected = new LinkedHashMap();

    public final int getTotalCaptureCount() {
        return this.totalCaptureCount;
    }

    public final int getTotalEggsHatched() {
        return this.totalEggsHatched;
    }

    public final int getTotalEvolvedCount() {
        return this.totalEvolvedCount;
    }

    public final int getTotalBattleVictoryCount() {
        return this.totalBattleVictoryCount;
    }

    public final int getTotalPvPBattleVictoryCount() {
        return this.totalPvPBattleVictoryCount;
    }

    public final int getTotalPvWBattleVictoryCount() {
        return this.totalPvWBattleVictoryCount;
    }

    public final int getTotalPvNBattleVictoryCount() {
        return this.totalPvNBattleVictoryCount;
    }

    public final int getTotalShinyCaptureCount() {
        return this.totalShinyCaptureCount;
    }

    public final int getTotalTradedCount() {
        return this.totalTradedCount;
    }

    @NotNull
    public final Map<ResourceLocation, Set<String>> getAspectsCollected() {
        return this.aspectsCollected;
    }

    public final void updateTotalCaptureCount() {
        int n = this.totalCaptureCount;
        this.totalCaptureCount = n + 1;
    }

    public final void updateTotalEggsHatched() {
        int n = this.totalEggsHatched;
        this.totalEggsHatched = n + 1;
    }

    public final void updateTotalEvolvedCount() {
        int n = this.totalEvolvedCount;
        this.totalEvolvedCount = n + 1;
    }

    public final void updateTotalBattleVictoryCount() {
        int n = this.totalBattleVictoryCount;
        this.totalBattleVictoryCount = n + 1;
    }

    public final void updateTotalPvPBattleVictoryCount() {
        int n = this.totalPvPBattleVictoryCount;
        this.totalPvPBattleVictoryCount = n + 1;
    }

    public final void updateTotalPvWBattleVictoryCount() {
        int n = this.totalPvWBattleVictoryCount;
        this.totalPvWBattleVictoryCount = n + 1;
    }

    public final void updateTotalPvNBattleVictoryCount() {
        int n = this.totalPvNBattleVictoryCount;
        this.totalPvNBattleVictoryCount = n + 1;
    }

    public final void updateTotalShinyCaptureCount() {
        int n = this.totalShinyCaptureCount;
        this.totalShinyCaptureCount = n + 1;
    }

    public final void updateTotalTradedCount() {
        int n = this.totalTradedCount;
        this.totalTradedCount = n + 1;
    }

    public final int getTotalTypeCaptureCount(@NotNull ElementalType type) {
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        if (!this.totalTypeCaptureCounts.containsKey(type.getName())) {
            this.totalTypeCaptureCounts.put(type.getName(), 0);
        }
        Integer n = this.totalTypeCaptureCounts.get(type.getName());
        return n != null ? n : 0;
    }

    public final void updateTotalTypeCaptureCount(@NotNull ElementalType type) {
        int count;
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Integer n = this.totalTypeCaptureCounts.get(type.getName());
        int n2 = count = n != null ? n : 0;
        if (count == 0) {
            this.totalTypeCaptureCounts.put(type.getName(), 1);
        } else {
            this.totalTypeCaptureCounts.replace(type.getName(), count + 1);
        }
    }

    public final void updateTotalDefeatedCount(@NotNull Pokemon pokemon) {
        int count;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Integer n = this.totalDefeatedCounts.get(pokemon.getSpecies().getResourceIdentifier());
        int n2 = count = n != null ? n : 0;
        if (count == 0) {
            this.totalDefeatedCounts.put(pokemon.getSpecies().getResourceIdentifier(), 1);
        } else {
            this.totalDefeatedCounts.replace(pokemon.getSpecies().getResourceIdentifier(), count + 1);
        }
    }

    /*
     * WARNING - void declaration
     */
    public final void updateAspectsCollected(@NotNull ServerPlayer player, @NotNull Pokemon pokemon) {
        void $this$flatMapTo$iv$iv;
        void $this$flatMap$iv;
        void $this$filterTo$iv$iv;
        Iterable $this$filter$iv;
        void $this$filterIsInstanceTo$iv$iv;
        void $this$filterIsInstance$iv;
        void $this$mapNotNullTo$iv$iv;
        Iterable $this$mapNotNull$iv;
        Object list$iv$iv;
        void $this$flatMapTo$iv$iv2;
        Iterable $this$flatMap$iv2;
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Iterable iterable = player.m_8960_().f_263740_.keySet();
        boolean $i$f$flatMap = false;
        void var6_6 = $this$flatMap$iv2;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv2) {
            Advancement it = (Advancement)element$iv$iv;
            boolean bl = false;
            list$iv$iv = it.m_138325_().values();
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        $this$flatMap$iv2 = (List)destination$iv$iv;
        boolean $i$f$mapNotNull = false;
        $this$flatMapTo$iv$iv2 = $this$mapNotNull$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$mapNotNullTo = false;
        Iterator $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
        boolean $i$f$forEach = false;
        list$iv$iv = $this$forEach$iv$iv$iv.iterator();
        while (list$iv$iv.hasNext()) {
            CriterionTriggerInstance it$iv$iv;
            Object element$iv$iv$iv;
            Object element$iv$iv = element$iv$iv$iv = list$iv$iv.next();
            boolean bl = false;
            Criterion it = (Criterion)element$iv$iv;
            boolean bl2 = false;
            if (it.m_11416_() == null) continue;
            boolean bl3 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        $this$mapNotNull$iv = (List)destination$iv$iv;
        boolean $i$f$filterIsInstance22 = false;
        $this$mapNotNullTo$iv$iv = $this$filterIsInstance$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof AspectCriterionCondition)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        List aspectConditions = (List)destination$iv$iv;
        Iterable $i$f$filterIsInstance22 = aspectConditions;
        boolean $i$f$filter = false;
        destination$iv$iv = $this$filter$iv;
        Collection destination$iv$iv2 = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            AspectCriterionCondition it = (AspectCriterionCondition)((Object)element$iv$iv);
            boolean bl = false;
            if (!Intrinsics.areEqual((Object)it.getPokemon(), (Object)pokemon.getSpecies().getResourceIdentifier())) continue;
            destination$iv$iv2.add(element$iv$iv);
        }
        $this$filter$iv = (List)destination$iv$iv2;
        boolean $i$f$flatMap22 = false;
        $this$filterTo$iv$iv = $this$flatMap$iv;
        destination$iv$iv2 = new ArrayList();
        boolean $i$f$flatMapTo2 = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            AspectCriterionCondition it = (AspectCriterionCondition)((Object)element$iv$iv);
            boolean bl = false;
            Iterable list$iv$iv2 = it.getAspects();
            CollectionsKt.addAll((Collection)destination$iv$iv2, (Iterable)list$iv$iv2);
        }
        List trackedAspects = (List)destination$iv$iv2;
        if (!((Collection)trackedAspects).isEmpty()) {
            void $this$forEach$iv;
            void $this$filterTo$iv$iv2;
            Object object;
            void $this$getOrPut$iv;
            Map<ResourceLocation, Set<String>> $i$f$flatMap22 = this.aspectsCollected;
            ResourceLocation key$iv = pokemon.getSpecies().getResourceIdentifier();
            boolean $i$f$getOrPut22 = false;
            Object value$iv = $this$getOrPut$iv.get(key$iv);
            if (value$iv == null) {
                boolean bl = false;
                Set answer$iv = new LinkedHashSet();
                $this$getOrPut$iv.put(key$iv, answer$iv);
                object = answer$iv;
            } else {
                object = value$iv;
            }
            Set collectedAspects = (Set)object;
            Iterable $this$filter$iv2 = pokemon.getAspects();
            boolean $i$f$filter2 = false;
            Iterable $i$f$getOrPut22 = $this$filter$iv2;
            Collection destination$iv$iv3 = new ArrayList();
            boolean $i$f$filterTo2 = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv2) {
                String p0 = (String)element$iv$iv;
                boolean bl = false;
                if (!trackedAspects.contains(p0)) continue;
                destination$iv$iv3.add(element$iv$iv);
            }
            $this$filter$iv2 = (List)destination$iv$iv3;
            boolean $i$f$forEach2 = false;
            for (Object element$iv : $this$forEach$iv) {
                String p0 = (String)element$iv;
                boolean bl = false;
                collectedAspects.add(p0);
            }
        }
    }
}

