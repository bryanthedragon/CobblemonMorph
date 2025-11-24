/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.comparisons.ComparisonsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleStartedPostEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleStartedPreEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.helditem.HeldItemProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.CancelableObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleStartError;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleStartResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ErroredBattleStart;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMovesetAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.SuccessfulBattleStart;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.ShowdownService;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleChallengeExpiredPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u00018B\t\b\u0002\u00a2\u0006\u0004\b7\u0010\u0012J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\t\u001a\u0004\u0018\u00010\u00022\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\r\u001a\u0004\u0018\u00010\u00022\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u0010\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u000f\u001a\u00020\u0007\u00a2\u0006\u0004\b\u0010\u0010\nJ\r\u0010\u0011\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0011\u0010\u0012J!\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00072\n\b\u0002\u0010\u0014\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0004\b\u0015\u0010\u0016J/\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u00192\b\b\u0002\u0010\u001d\u001a\u00020\u001c\u00a2\u0006\u0004\b\u001f\u0010 J\u0017\u0010!\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b!\u0010\u0006J\r\u0010\"\u001a\u00020\u0004\u00a2\u0006\u0004\b\"\u0010\u0012J\u0017\u0010&\u001a\u00020%*\b\u0012\u0004\u0012\u00020$0#\u00a2\u0006\u0004\b&\u0010'R \u0010)\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00020(8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b)\u0010*R\u001f\u0010-\u001a\n ,*\u0004\u0018\u00010+0+8\u0006\u00a2\u0006\f\n\u0004\b-\u0010.\u001a\u0004\b/\u00100R#\u00103\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u000202018\u0006\u00a2\u0006\f\n\u0004\b3\u00104\u001a\u0004\b5\u00106\u00a8\u00069"}, d2={"Lcom/cobblemon/mod/common/battles/BattleRegistry;", "", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "", "closeBattle", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "Ljava/util/UUID;", "id", "getBattle", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "Lnet/minecraft/server/level/ServerPlayer;", "serverPlayerEntity", "getBattleByParticipatingPlayer", "(Lnet/minecraft/server/level/ServerPlayer;)Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "playerId", "getBattleByParticipatingPlayerId", "onServerStarted", "()V", "challengerId", "challengeId", "removeChallenge", "(Ljava/util/UUID;Ljava/util/UUID;)V", "Lcom/cobblemon/mod/common/battles/BattleFormat;", "battleFormat", "Lcom/cobblemon/mod/common/battles/BattleSide;", "side1", "side2", "", "silent", "Lcom/cobblemon/mod/common/battles/BattleStartResult;", "startBattle", "(Lcom/cobblemon/mod/common/battles/BattleFormat;Lcom/cobblemon/mod/common/battles/BattleSide;Lcom/cobblemon/mod/common/battles/BattleSide;Z)Lcom/cobblemon/mod/common/battles/BattleStartResult;", "startShowdown", "tick", "", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "", "packTeam", "(Ljava/util/List;)Ljava/lang/String;", "Ljava/util/concurrent/ConcurrentHashMap;", "battleMap", "Ljava/util/concurrent/ConcurrentHashMap;", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "", "Lcom/cobblemon/mod/common/battles/BattleRegistry$BattleChallenge;", "pvpChallenges", "Ljava/util/Map;", "getPvpChallenges", "()Ljava/util/Map;", "<init>", "BattleChallenge", "common"})
@SourceDebugExtension(value={"SMAP\nBattleRegistry.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleRegistry.kt\ncom/cobblemon/mod/common/battles/BattleRegistry\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 5 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable\n+ 6 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 7 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 8 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/CancelableObservable$postThen$1\n+ 9 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n*L\n1#1,240:1\n1549#2:241\n1620#2,3:242\n1549#2:245\n1620#2,3:246\n1603#2,9:249\n1855#2:258\n1856#2:260\n1612#2:261\n1855#2,2:262\n1045#2:264\n1045#2:265\n1#3:259\n1#3:290\n37#4,2:266\n39#5,2:268\n41#5,2:273\n44#5:276\n46#5:286\n47#5:289\n17#6,2:270\n14#6,5:277\n19#6:285\n19#6:288\n13579#7:272\n13579#7:282\n13580#7:284\n13580#7:287\n39#8:275\n14#9:283\n*S KotlinDebug\n*F\n+ 1 BattleRegistry.kt\ncom/cobblemon/mod/common/battles/BattleRegistry\n*L\n114#1:241\n114#1:242,3\n119#1:245\n119#1:246,3\n184#1:249,9\n184#1:258\n184#1:260\n184#1:261\n185#1:262,2\n189#1:264\n194#1:265\n184#1:259\n199#1:266,2\n212#1:268,2\n212#1:273,2\n212#1:276\n212#1:286\n212#1:289\n212#1:270,2\n215#1:277,5\n215#1:285\n212#1:288\n212#1:272\n215#1:282\n215#1:284\n212#1:287\n212#1:275\n215#1:283\n*E\n"})
public final class BattleRegistry {
    @NotNull
    public static final BattleRegistry INSTANCE = new BattleRegistry();
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().registerTypeAdapter((Type)((Object)ShowdownMoveset.class), (Object)ShowdownMovesetAdapter.INSTANCE).create();
    @NotNull
    private static final ConcurrentHashMap<UUID, PokemonBattle> battleMap = new ConcurrentHashMap();
    @NotNull
    private static final Map<UUID, BattleChallenge> pvpChallenges = new LinkedHashMap();

    private BattleRegistry() {
    }

    public final Gson getGson() {
        return gson;
    }

    @NotNull
    public final Map<UUID, BattleChallenge> getPvpChallenges() {
        return pvpChallenges;
    }

    public final void onServerStarted() {
        battleMap.clear();
        pvpChallenges.clear();
    }

    public final void removeChallenge(@NotNull UUID challengerId, @Nullable UUID challengeId) {
        block2: {
            Intrinsics.checkNotNullParameter((Object)challengerId, (String)"challengerId");
            BattleChallenge battleChallenge = pvpChallenges.get(challengerId);
            if (battleChallenge == null) {
                return;
            }
            BattleChallenge existing = battleChallenge;
            if (!Intrinsics.areEqual((Object)existing.getChallengeId(), (Object)challengeId)) {
                return;
            }
            pvpChallenges.remove(challengerId);
            ServerPlayer serverPlayer = PlayerExtensionsKt.getPlayer(existing.getChallengedPlayerUUID());
            if (serverPlayer == null) break block2;
            CobblemonNetwork.INSTANCE.sendPacket(serverPlayer, new BattleChallengeExpiredPacket(existing.getChallengeId()));
        }
    }

    public static /* synthetic */ void removeChallenge$default(BattleRegistry battleRegistry, UUID uUID, UUID uUID2, int n, Object object) {
        if ((n & 2) != 0) {
            uUID2 = null;
        }
        battleRegistry.removeChallenge(uUID, uUID2);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final String packTeam(@NotNull List<? extends BattlePokemon> $this$packTeam) {
        Intrinsics.checkNotNullParameter($this$packTeam, (String)"<this>");
        List team = new ArrayList();
        for (BattlePokemon battlePokemon : $this$packTeam) {
            void $this$mapTo$iv$iv;
            Collection collection;
            void $this$mapTo$iv$iv2;
            String string;
            String string2;
            Pokemon pk = battlePokemon.getEffectedPokemon();
            StringBuilder packedTeamBuilder = new StringBuilder();
            packedTeamBuilder.append(pk.showdownId() + "|");
            packedTeamBuilder.append("|");
            packedTeamBuilder.append(pk.getUuid() + "|");
            packedTeamBuilder.append(pk.getCurrentHealth() + "|");
            if (pk.getStatus() != null) {
                PersistentStatusContainer persistentStatusContainer = pk.getStatus();
                Intrinsics.checkNotNull((Object)persistentStatusContainer);
                string2 = persistentStatusContainer.getStatus().getShowdownName();
            } else {
                string2 = "";
            }
            String showdownStatus = string2;
            packedTeamBuilder.append(showdownStatus + "|");
            Object[] objectArray = new PersistentStatus[]{Statuses.INSTANCE.getSLEEP(), Statuses.INSTANCE.getFROZEN()};
            PersistentStatusContainer persistentStatusContainer = pk.getStatus();
            if (CollectionsKt.contains((Iterable)CollectionsKt.listOf((Object[])objectArray), (Object)(persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null))) {
                packedTeamBuilder.append("2|");
            } else {
                packedTeamBuilder.append("-1|");
            }
            if ((string = HeldItemProvider.INSTANCE.provideShowdownId(battlePokemon)) == null) {
                string = "";
            }
            String heldItemID = string;
            packedTeamBuilder.append(heldItemID + "|");
            packedTeamBuilder.append(StringsKt.replace$default((String)pk.getAbility().getName(), (String)"_", (String)"", (boolean)false, (int)4, null) + "|");
            packedTeamBuilder.append(CollectionsKt.joinToString$default((Iterable)pk.getMoveSet().getMoves(), (CharSequence)",", null, null, (int)0, null, (Function1)packTeam.1.INSTANCE, (int)30, null) + "|");
            packedTeamBuilder.append(CollectionsKt.joinToString$default((Iterable)pk.getMoveSet().getMoves(), (CharSequence)",", null, null, (int)0, null, (Function1)packTeam.2.INSTANCE, (int)30, null) + "|");
            Nature battleNature = pk.getEffectiveNature();
            packedTeamBuilder.append(battleNature.getName().m_135815_() + "|");
            Iterable $this$map$iv = Stats.Companion.getPERMANENT();
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Iterable destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv2) {
                void it;
                Stat stat = (Stat)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl = false;
                collection.add(pk.getEvs().getOrDefault((Stat)it));
            }
            String evsInOrder = CollectionsKt.joinToString$default((Iterable)((List)destination$iv$iv), (CharSequence)",", null, null, (int)0, null, null, (int)62, null);
            packedTeamBuilder.append(evsInOrder + "|");
            packedTeamBuilder.append(pk.getGender().getShowdownName() + "|");
            Iterable $this$map$iv2 = Stats.Companion.getPERMANENT();
            boolean $i$f$map2 = false;
            destination$iv$iv = $this$map$iv2;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv2, (int)10));
            boolean $i$f$mapTo2 = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                Stat bl = (Stat)item$iv$iv;
                collection = destination$iv$iv2;
                boolean bl2 = false;
                collection.add(pk.getIvs().getOrDefault((Stat)it));
            }
            String ivsInOrder = CollectionsKt.joinToString$default((Iterable)((List)destination$iv$iv2), (CharSequence)",", null, null, (int)0, null, null, (int)62, null);
            packedTeamBuilder.append(ivsInOrder + "|");
            packedTeamBuilder.append((pk.getShiny() ? "S" : "") + "|");
            packedTeamBuilder.append(pk.getLevel() + "|");
            packedTeamBuilder.append(pk.getFriendship() + ",");
            String string3 = battlePokemon.getEffectedPokemon().getCaughtBall().getName().m_135815_();
            Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"pokemon.effectedPokemon.caughtBall.name.path");
            String pokeball = StringsKt.replace$default((String)string3, (String)"_", (String)"", (boolean)false, (int)4, null);
            packedTeamBuilder.append(pokeball + ",");
            packedTeamBuilder.append(",");
            packedTeamBuilder.append((pk.getGmaxFactor() ? "G" : "") + ",");
            packedTeamBuilder.append((pk.getDmaxLevel() < 10 ? Integer.valueOf(pk.getDmaxLevel()) : "") + ",");
            packedTeamBuilder.append(battlePokemon.getEffectedPokemon().getTeraType().showdownId() + ",");
            String string4 = packedTeamBuilder.toString();
            Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"packedTeamBuilder.toString()");
            team.add(string4);
        }
        return CollectionsKt.joinToString$default((Iterable)team, (CharSequence)"]", null, null, (int)0, null, null, (int)62, null);
    }

    /*
     * WARNING - void declaration
     */
    private final void startShowdown(PokemonBattle battle2) {
        List messages = new ArrayList();
        messages.add(">start { \"format\": " + battle2.getFormat().toFormatJSON() + " }");
        int actorIndex = 1;
        for (BattleActor actor : battle2.getSide1().getActors()) {
            actor.setShowdownId("p" + actorIndex);
            actorIndex += 2;
        }
        actorIndex = 2;
        for (BattleActor actor : battle2.getSide2().getActors()) {
            actor.setShowdownId("p" + actorIndex);
            actorIndex += 2;
        }
        for (BattleActor actor : battle2.getActors()) {
            void $this$mapNotNullTo$iv$iv;
            int n = battle2.getFormat().getBattleType().getSlotsPerActor();
            int actor2 = 0;
            while (actor2 < n) {
                int it = actor2++;
                boolean bl = false;
                actor.getActivePokemon().add(new ActiveBattlePokemon(actor, null, 2, null));
            }
            Iterable $this$mapNotNull$iv = actor.getPokemonList();
            boolean $i$f$mapNotNull = false;
            Iterable bl = $this$mapNotNull$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$mapNotNullTo = false;
            void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
            boolean $i$f$forEach = false;
            Iterator iterator = $this$forEach$iv$iv$iv.iterator();
            while (iterator.hasNext()) {
                PokemonEntity it$iv$iv;
                Object element$iv$iv$iv;
                Object element$iv$iv = element$iv$iv$iv = iterator.next();
                boolean bl2 = false;
                BattlePokemon it = (BattlePokemon)element$iv$iv;
                boolean bl3 = false;
                if (it.getEntity() == null) continue;
                boolean bl4 = false;
                destination$iv$iv.add(it$iv$iv);
            }
            List entities2 = (List)destination$iv$iv;
            Iterable $this$forEach$iv = entities2;
            boolean $i$f$forEach2 = false;
            for (Object element$iv : $this$forEach$iv) {
                PokemonEntity it = (PokemonEntity)element$iv;
                boolean bl5 = false;
                it.setBattleId(battle2.getBattleId());
            }
        }
        Iterable<BattleActor> $this$sortedBy$iv = battle2.getActors();
        boolean $i$f$sortedBy = false;
        for (BattleActor actor : CollectionsKt.sortedWith($this$sortedBy$iv, (Comparator)new Comparator(){

            public final int compare(T a, T b) {
                BattleActor it = (BattleActor)a;
                boolean bl = false;
                Comparable comparable = (Comparable)((Object)it.getShowdownId());
                it = (BattleActor)b;
                Comparable comparable2 = comparable;
                bl = false;
                return ComparisonsKt.compareValues((Comparable)comparable2, (Comparable)((Comparable)((Object)it.getShowdownId())));
            }
        })) {
            messages.add(">player " + actor.getShowdownId() + " {\"name\":\"" + actor.getUuid() + "\",\"team\":\"" + this.packTeam(actor.getPokemonList()) + "\"}");
        }
        $this$sortedBy$iv = battle2.getActors();
        $i$f$sortedBy = false;
        for (BattleActor actor : CollectionsKt.sortedWith($this$sortedBy$iv, (Comparator)new Comparator(){

            public final int compare(T a, T b) {
                BattleActor it = (BattleActor)a;
                boolean bl = false;
                Comparable comparable = (Comparable)((Object)it.getShowdownId());
                it = (BattleActor)b;
                Comparable comparable2 = comparable;
                bl = false;
                return ComparisonsKt.compareValues((Comparable)comparable2, (Comparable)((Comparable)((Object)it.getShowdownId())));
            }
        })) {
            messages.add(">" + actor.getShowdownId() + " team " + ((Collection)actor.getPokemonList()).size());
        }
        Collection $this$toTypedArray$iv = messages;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        ShowdownService.Companion.getService().startBattle(battle2, thisCollection$iv.toArray(new String[0]));
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final BattleStartResult startBattle(@NotNull BattleFormat battleFormat, @NotNull BattleSide side1, @NotNull BattleSide side2, boolean silent) {
        void this_$iv$iv;
        Intrinsics.checkNotNullParameter((Object)battleFormat, (String)"battleFormat");
        Intrinsics.checkNotNullParameter((Object)side1, (String)"side1");
        Intrinsics.checkNotNullParameter((Object)side2, (String)"side2");
        PokemonBattle battle2 = new PokemonBattle(battleFormat, side1, side2);
        if (silent) {
            return new SuccessfulBattleStart(battle2);
        }
        BattleStartedPreEvent preBattleEvent = new BattleStartedPreEvent(battle2, null, 2, null);
        CancelableObservable<BattleStartedPreEvent> $this$iv = CobblemonEvents.BATTLE_STARTED_PRE;
        boolean $i$f$postThen = false;
        EventObservable eventObservable = $this$iv;
        Cancelable[] cancelableArray = new Cancelable[]{preBattleEvent};
        Cancelable[] events$iv$iv = cancelableArray;
        boolean $i$f$post = false;
        this_$iv$iv.emit(Arrays.copyOf(events$iv$iv, events$iv$iv.length));
        Cancelable[] $this$forEach$iv$iv$iv = events$iv$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            Cancelable it;
            Cancelable element$iv$iv$iv;
            Cancelable it$iv = element$iv$iv$iv = $this$forEach$iv$iv$iv[i];
            boolean bl = false;
            if (!it$iv.isCanceled()) {
                void $this$iv2;
                it = (BattleStartedPreEvent)it$iv;
                boolean bl2 = false;
                Object object = battleMap;
                UUID uUID = battle2.getBattleId();
                Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"battle.battleId");
                object.put(uUID, battle2);
                INSTANCE.startShowdown(battle2);
                object = CobblemonEvents.BATTLE_STARTED_POST;
                BattleStartedPostEvent[] battleStartedPostEventArray = new BattleStartedPostEvent[]{new BattleStartedPostEvent(battle2)};
                BattleStartedPostEvent[] events$iv = battleStartedPostEventArray;
                boolean $i$f$post2 = false;
                $this$iv2.emit(Arrays.copyOf(events$iv, events$iv.length));
                BattleStartedPostEvent[] $this$forEach$iv$iv = events$iv;
                boolean $i$f$forEach2 = false;
                int n2 = $this$forEach$iv$iv.length;
                for (int j = 0; j < n2; ++j) {
                    BattleStartedPostEvent element$iv$iv;
                    BattleStartedPostEvent battleStartedPostEvent = element$iv$iv = $this$forEach$iv$iv[j];
                    boolean bl3 = false;
                    BattleStartedPostEvent it2 = battleStartedPostEvent;
                }
                return new SuccessfulBattleStart(battle2);
            }
            Cancelable cancelable = it$iv;
            boolean bl4 = false;
            it = cancelable;
        }
        Object[] objectArray = new BattleStartError[]{BattleStartError.Companion.canceledByEvent(preBattleEvent.getReason())};
        return new ErroredBattleStart(SetsKt.mutableSetOf((Object[])objectArray), null, 2, null);
    }

    public static /* synthetic */ BattleStartResult startBattle$default(BattleRegistry battleRegistry, BattleFormat battleFormat, BattleSide battleSide, BattleSide battleSide2, boolean bl, int n, Object object) {
        if ((n & 8) != 0) {
            bl = false;
        }
        return battleRegistry.startBattle(battleFormat, battleSide, battleSide2, bl);
    }

    public final void closeBattle(@NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        battleMap.remove(battle2.getBattleId());
    }

    @Nullable
    public final PokemonBattle getBattle(@NotNull UUID id) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        return battleMap.get(id);
    }

    @Nullable
    public final PokemonBattle getBattleByParticipatingPlayer(@NotNull ServerPlayer serverPlayerEntity) {
        Object v1;
        block1: {
            Intrinsics.checkNotNullParameter((Object)serverPlayerEntity, (String)"serverPlayerEntity");
            Collection<PokemonBattle> collection = battleMap.values();
            Intrinsics.checkNotNullExpressionValue(collection, (String)"battleMap.values");
            Iterable iterable = collection;
            for (Object t : iterable) {
                PokemonBattle it = (PokemonBattle)t;
                boolean bl = false;
                if (!(it.getActor(serverPlayerEntity) != null)) continue;
                v1 = t;
                break block1;
            }
            v1 = null;
        }
        return v1;
    }

    @Nullable
    public final PokemonBattle getBattleByParticipatingPlayerId(@NotNull UUID playerId) {
        Object v1;
        block1: {
            Intrinsics.checkNotNullParameter((Object)playerId, (String)"playerId");
            Collection<PokemonBattle> collection = battleMap.values();
            Intrinsics.checkNotNullExpressionValue(collection, (String)"battleMap.values");
            Iterable iterable = collection;
            for (Object t : iterable) {
                PokemonBattle it = (PokemonBattle)t;
                boolean bl = false;
                if (!CollectionsKt.contains(it.getPlayerUUIDs(), (Object)playerId)) continue;
                v1 = t;
                break block1;
            }
            v1 = null;
        }
        return v1;
    }

    public final void tick() {
        battleMap.forEachValue(Long.MAX_VALUE, arg_0 -> BattleRegistry.tick$lambda$10(tick.1.INSTANCE, arg_0));
    }

    private static final void tick$lambda$10(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke(p0);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u000b\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\n\u001a\u00020\u0005\u0012\u0006\u0010\u0019\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0012\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004R\u0017\u0010\u0006\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u0007\u001a\u0004\b\u000b\u0010\tR\u001f\u0010\u000e\u001a\n \r*\u0004\u0018\u00010\f0\f8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\"\u0010\u0013\u001a\u00020\u00128\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0017\u0010\u0019\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u0007\u001a\u0004\b\u001a\u0010\t\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/battles/BattleRegistry$BattleChallenge;", "", "", "isExpired", "()Z", "Ljava/util/UUID;", "challengeId", "Ljava/util/UUID;", "getChallengeId", "()Ljava/util/UUID;", "challengedPlayerUUID", "getChallengedPlayerUUID", "Ljava/time/Instant;", "kotlin.jvm.PlatformType", "challengedTime", "Ljava/time/Instant;", "getChallengedTime", "()Ljava/time/Instant;", "", "expiryTimeSeconds", "I", "getExpiryTimeSeconds", "()I", "setExpiryTimeSeconds", "(I)V", "selectedPokemonId", "getSelectedPokemonId", "<init>", "(Ljava/util/UUID;Ljava/util/UUID;Ljava/util/UUID;I)V", "common"})
    public static final class BattleChallenge {
        @NotNull
        private final UUID challengeId;
        @NotNull
        private final UUID challengedPlayerUUID;
        @NotNull
        private final UUID selectedPokemonId;
        private int expiryTimeSeconds;
        private final Instant challengedTime;

        public BattleChallenge(@NotNull UUID challengeId, @NotNull UUID challengedPlayerUUID, @NotNull UUID selectedPokemonId, int expiryTimeSeconds) {
            Intrinsics.checkNotNullParameter((Object)challengeId, (String)"challengeId");
            Intrinsics.checkNotNullParameter((Object)challengedPlayerUUID, (String)"challengedPlayerUUID");
            Intrinsics.checkNotNullParameter((Object)selectedPokemonId, (String)"selectedPokemonId");
            this.challengeId = challengeId;
            this.challengedPlayerUUID = challengedPlayerUUID;
            this.selectedPokemonId = selectedPokemonId;
            this.expiryTimeSeconds = expiryTimeSeconds;
            this.challengedTime = Instant.now();
        }

        public /* synthetic */ BattleChallenge(UUID uUID, UUID uUID2, UUID uUID3, int n, int n2, DefaultConstructorMarker defaultConstructorMarker) {
            if ((n2 & 8) != 0) {
                n = 60;
            }
            this(uUID, uUID2, uUID3, n);
        }

        @NotNull
        public final UUID getChallengeId() {
            return this.challengeId;
        }

        @NotNull
        public final UUID getChallengedPlayerUUID() {
            return this.challengedPlayerUUID;
        }

        @NotNull
        public final UUID getSelectedPokemonId() {
            return this.selectedPokemonId;
        }

        public final int getExpiryTimeSeconds() {
            return this.expiryTimeSeconds;
        }

        public final void setExpiryTimeSeconds(int n) {
            this.expiryTimeSeconds = n;
        }

        public final Instant getChallengedTime() {
            return this.challengedTime;
        }

        public final boolean isExpired() {
            return Instant.now().isAfter(this.challengedTime.plusSeconds(this.expiryTimeSeconds));
        }
    }
}

