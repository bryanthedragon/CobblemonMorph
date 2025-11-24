/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.ArraysKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$IntRef
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.ActorType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ForcePassActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionRequest;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.exception.IllegalActionChoiceException;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleApplyPassResponsePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMakeChoicePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleMessagePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\n\b&\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010h\u001a\u00020\u0014\u0012\f\u0010M\u001a\b\u0012\u0004\u0012\u00020\u000206\u00a2\u0006\u0004\bl\u0010mJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\r\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0015\u0010\u000e\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u000f\u0010\u0011\u001a\u00020\u0010H&\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0015\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J\r\u0010\u0018\u001a\u00020\u0017\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0017\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001b\u001a\u00020\u001aH\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010 \u001a\u00020\t2\u0006\u0010\u001f\u001a\u00020\u001eH\u0016\u00a2\u0006\u0004\b \u0010!J\u0017\u0010$\u001a\u00020\u00102\u0006\u0010#\u001a\u00020\"H&\u00a2\u0006\u0004\b$\u0010%J\u0017\u0010(\u001a\u00020\u00062\u0006\u0010'\u001a\u00020&H\u0016\u00a2\u0006\u0004\b(\u0010)J\u001b\u0010,\u001a\u00020\u00062\n\u0010+\u001a\u0006\u0012\u0002\b\u00030*H\u0016\u00a2\u0006\u0004\b,\u0010-J\u001b\u00100\u001a\u00020\u00062\f\u0010/\u001a\b\u0012\u0004\u0012\u00020\f0.\u00a2\u0006\u0004\b0\u00101J\r\u00102\u001a\u00020\u0006\u00a2\u0006\u0004\b2\u00103J\r\u00104\u001a\u00020\u0006\u00a2\u0006\u0004\b4\u00103J\r\u00105\u001a\u00020\u0006\u00a2\u0006\u0004\b5\u00103R\u001d\u00108\u001a\b\u0012\u0004\u0012\u000207068\u0006\u00a2\u0006\f\n\u0004\b8\u00109\u001a\u0004\b:\u0010;R\"\u0010=\u001a\u00020<8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b=\u0010>\u001a\u0004\b?\u0010@\"\u0004\bA\u0010BR\"\u0010C\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bC\u0010D\u001a\u0004\bE\u0010\u000b\"\u0004\bF\u0010GR\u001d\u0010H\u001a\b\u0012\u0004\u0012\u00020\f068\u0006\u00a2\u0006\f\n\u0004\bH\u00109\u001a\u0004\bI\u0010;R\"\u0010J\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bJ\u0010D\u001a\u0004\bK\u0010\u000b\"\u0004\bL\u0010GR\u001d\u0010M\u001a\b\u0012\u0004\u0012\u00020\u0002068\u0006\u00a2\u0006\f\n\u0004\bM\u00109\u001a\u0004\bN\u0010;R$\u0010P\u001a\u0004\u0018\u00010O8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bP\u0010Q\u001a\u0004\bR\u0010S\"\u0004\bT\u0010UR(\u0010/\u001a\b\u0012\u0004\u0012\u00020\f068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b/\u00109\u001a\u0004\bV\u0010;\"\u0004\bW\u00101R\"\u0010X\u001a\u00020\"8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bX\u0010Y\u001a\u0004\bZ\u0010[\"\u0004\b\\\u0010]R\"\u0010^\u001a\u00020\u00048\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b^\u0010_\u001a\u0004\b`\u0010a\"\u0004\bb\u0010cR\u0014\u0010g\u001a\u00020d8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\be\u0010fR\u0017\u0010h\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\bh\u0010i\u001a\u0004\bj\u0010k\u00a8\u0006n"}, d2={"Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "battlePokemon", "", "experience", "", "awardExperience", "(Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;I)V", "", "canFitForcedAction", "()Z", "Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "response", "forceChoose", "(Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;)V", "Lnet/minecraft/network/chat/MutableComponent;", "getName", "()Lnet/minecraft/network/chat/MutableComponent;", "", "Ljava/util/UUID;", "getPlayerUUIDs", "()Ljava/lang/Iterable;", "Lcom/cobblemon/mod/common/battles/BattleSide;", "getSide", "()Lcom/cobblemon/mod/common/battles/BattleSide;", "Lnet/minecraft/server/level/ServerPlayer;", "serverPlayerEntity", "isForPlayer", "(Lnet/minecraft/server/level/ServerPlayer;)Z", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "isForPokemon", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Z", "", "name", "nameOwned", "(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;", "Lnet/minecraft/network/chat/Component;", "component", "sendMessage", "(Lnet/minecraft/network/chat/Component;)V", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "packet", "sendUpdate", "(Lcom/cobblemon/mod/common/api/net/NetworkPacket;)V", "", "responses", "setActionResponses", "(Ljava/util/List;)V", "turn", "()V", "upkeep", "writeShowdownResponse", "", "Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;", "activePokemon", "Ljava/util/List;", "getActivePokemon", "()Ljava/util/List;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "getBattle", "()Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "setBattle", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "canDynamax", "Z", "getCanDynamax", "setCanDynamax", "(Z)V", "expectingPassActions", "getExpectingPassActions", "mustChoose", "getMustChoose", "setMustChoose", "pokemonList", "getPokemonList", "Lcom/cobblemon/mod/common/battles/ShowdownActionRequest;", "request", "Lcom/cobblemon/mod/common/battles/ShowdownActionRequest;", "getRequest", "()Lcom/cobblemon/mod/common/battles/ShowdownActionRequest;", "setRequest", "(Lcom/cobblemon/mod/common/battles/ShowdownActionRequest;)V", "getResponses", "setResponses", "showdownId", "Ljava/lang/String;", "getShowdownId", "()Ljava/lang/String;", "setShowdownId", "(Ljava/lang/String;)V", "stillSendingOutCount", "I", "getStillSendingOutCount", "()I", "setStillSendingOutCount", "(I)V", "Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "getType", "()Lcom/cobblemon/mod/common/api/battles/model/actor/ActorType;", "type", "uuid", "Ljava/util/UUID;", "getUuid", "()Ljava/util/UUID;", "<init>", "(Ljava/util/UUID;Ljava/util/List;)V", "common"})
@SourceDebugExtension(value={"SMAP\nBattleActor.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleActor.kt\ncom/cobblemon/mod/common/api/battles/model/actor/BattleActor\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,148:1\n1855#2,2:149\n1774#2,4:151\n1747#2,3:155\n1569#2,11:158\n1864#2,2:169\n1866#2:172\n1580#2:173\n1864#2,2:174\n1866#2:177\n1#3:171\n1#3:176\n*S KotlinDebug\n*F\n+ 1 BattleActor.kt\ncom/cobblemon/mod/common/api/battles/model/actor/BattleActor\n*L\n33#1:149,2\n53#1:151,4\n66#1:155,3\n84#1:158,11\n84#1:169,2\n84#1:172\n84#1:173\n96#1:174,2\n96#1:177\n84#1:171\n*E\n"})
public abstract class BattleActor {
    @NotNull
    private final UUID uuid;
    @NotNull
    private final List<BattlePokemon> pokemonList;
    public String showdownId;
    public PokemonBattle battle;
    @NotNull
    private final List<ActiveBattlePokemon> activePokemon;
    private boolean canDynamax;
    @Nullable
    private ShowdownActionRequest request;
    @NotNull
    private List<ShowdownActionResponse> responses;
    @NotNull
    private final List<ShowdownActionResponse> expectingPassActions;
    private boolean mustChoose;
    private int stillSendingOutCount;

    public BattleActor(@NotNull UUID uuid2, @NotNull List<BattlePokemon> pokemonList) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        Intrinsics.checkNotNullParameter(pokemonList, (String)"pokemonList");
        this.uuid = uuid2;
        this.pokemonList = pokemonList;
        Iterable $this$forEach$iv = this.pokemonList;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            BattlePokemon it = (BattlePokemon)element$iv;
            boolean bl = false;
            it.setActor(this);
        }
        this.activePokemon = new ArrayList();
        this.responses = new ArrayList();
        this.expectingPassActions = new ArrayList();
    }

    @NotNull
    public final UUID getUuid() {
        return this.uuid;
    }

    @NotNull
    public final List<BattlePokemon> getPokemonList() {
        return this.pokemonList;
    }

    @NotNull
    public final String getShowdownId() {
        String string = this.showdownId;
        if (string != null) {
            return string;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"showdownId");
        return null;
    }

    public final void setShowdownId(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.showdownId = string;
    }

    @NotNull
    public final PokemonBattle getBattle() {
        PokemonBattle pokemonBattle = this.battle;
        if (pokemonBattle != null) {
            return pokemonBattle;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"battle");
        return null;
    }

    public final void setBattle(@NotNull PokemonBattle pokemonBattle) {
        Intrinsics.checkNotNullParameter((Object)pokemonBattle, (String)"<set-?>");
        this.battle = pokemonBattle;
    }

    @NotNull
    public final List<ActiveBattlePokemon> getActivePokemon() {
        return this.activePokemon;
    }

    public final boolean getCanDynamax() {
        return this.canDynamax;
    }

    public final void setCanDynamax(boolean bl) {
        this.canDynamax = bl;
    }

    @Nullable
    public final ShowdownActionRequest getRequest() {
        return this.request;
    }

    public final void setRequest(@Nullable ShowdownActionRequest showdownActionRequest) {
        this.request = showdownActionRequest;
    }

    @NotNull
    public final List<ShowdownActionResponse> getResponses() {
        return this.responses;
    }

    public final void setResponses(@NotNull List<ShowdownActionResponse> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.responses = list;
    }

    @NotNull
    public final List<ShowdownActionResponse> getExpectingPassActions() {
        return this.expectingPassActions;
    }

    public final boolean getMustChoose() {
        return this.mustChoose;
    }

    public final void setMustChoose(boolean bl) {
        this.mustChoose = bl;
    }

    public final int getStillSendingOutCount() {
        return this.stillSendingOutCount;
    }

    public final void setStillSendingOutCount(int n) {
        this.stillSendingOutCount = n;
    }

    @NotNull
    public abstract ActorType getType();

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean canFitForcedAction() {
        int n;
        void $this$count$iv;
        if (!this.mustChoose) return false;
        ShowdownActionRequest showdownActionRequest = this.request;
        if (showdownActionRequest == null) return false;
        ShowdownActionRequest request = showdownActionRequest;
        boolean bl = false;
        List<ShowdownMoveset> list = request.getActive();
        Iterable iterable = request.getForceSwitch();
        int n2 = list != null ? ((Collection)list).size() : 0;
        boolean $i$f$count = false;
        if ($this$count$iv instanceof Collection && ((Collection)$this$count$iv).isEmpty()) {
            n = 0;
        } else {
            int count$iv = 0;
            for (Object element$iv : $this$count$iv) {
                boolean it = (Boolean)element$iv;
                boolean bl2 = false;
                if (!it || ++count$iv >= 0) continue;
                CollectionsKt.throwCountOverflow();
            }
            n = count$iv;
        }
        int n3 = n;
        int countMovable = n2 - n3;
        if (countMovable <= this.expectingPassActions.size()) return false;
        if (this.getBattle().getEnded()) return false;
        return true;
    }

    public final void forceChoose(@NotNull ShowdownActionResponse response) {
        Intrinsics.checkNotNullParameter((Object)response, (String)"response");
        this.expectingPassActions.add(response);
        this.sendUpdate(new BattleApplyPassResponsePacket());
    }

    @NotNull
    public final BattleSide getSide() {
        return ArraysKt.contains((Object[])this.getBattle().getSide1().getActors(), (Object)this) ? this.getBattle().getSide1() : this.getBattle().getSide2();
    }

    @NotNull
    public Iterable<UUID> getPlayerUUIDs() {
        return CollectionsKt.emptyList();
    }

    public boolean isForPlayer(@NotNull ServerPlayer serverPlayerEntity) {
        Intrinsics.checkNotNullParameter((Object)serverPlayerEntity, (String)"serverPlayerEntity");
        return CollectionsKt.contains(this.getPlayerUUIDs(), (Object)serverPlayerEntity.m_20148_());
    }

    public boolean isForPokemon(@NotNull PokemonEntity pokemonEntity) {
        boolean bl;
        block3: {
            Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
            Iterable $this$any$iv = this.activePokemon;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    ActiveBattlePokemon it = (ActiveBattlePokemon)element$iv;
                    boolean bl2 = false;
                    Object object = it.getBattlePokemon();
                    if (!Intrinsics.areEqual((Object)(object != null && (object = ((BattlePokemon)object).getEffectedPokemon()) != null ? ((Pokemon)object).getEntity() : null), (Object)pokemonEntity)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    public final void turn() {
        ShowdownActionRequest showdownActionRequest = this.request;
        if (showdownActionRequest == null) {
            return;
        }
        ShowdownActionRequest request = showdownActionRequest;
        this.responses.clear();
        this.mustChoose = true;
        this.sendUpdate(new BattleMakeChoicePacket());
        List<ShowdownMoveset> requestActive = request.getActive();
        if (requestActive == null || requestActive.isEmpty() || request.getWait()) {
            this.request = null;
            this.expectingPassActions.clear();
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    public final void upkeep() {
        void $this$mapIndexedNotNullTo$iv$iv;
        ShowdownActionRequest showdownActionRequest = this.request;
        if (showdownActionRequest == null) {
            return;
        }
        ShowdownActionRequest request = showdownActionRequest;
        Iterable $this$mapIndexedNotNull$iv = request.getForceSwitch();
        boolean $i$f$mapIndexedNotNull = false;
        Iterable iterable = $this$mapIndexedNotNull$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$mapIndexedNotNullTo = false;
        void $this$forEachIndexed$iv$iv$iv = $this$mapIndexedNotNullTo$iv$iv;
        boolean $i$f$forEachIndexed = false;
        int index$iv$iv$iv = 0;
        for (Object item$iv$iv$iv : $this$forEachIndexed$iv$iv$iv) {
            ActiveBattlePokemon it$iv$iv;
            void b;
            void element$iv$iv;
            int n;
            if ((n = index$iv$iv$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            Object t = item$iv$iv$iv;
            int index$iv$iv = n;
            boolean bl = false;
            boolean bl2 = (Boolean)element$iv$iv;
            int index = index$iv$iv;
            boolean bl3 = false;
            if ((b != false ? this.activePokemon.get(index) : null) == null) continue;
            it$iv$iv = it$iv$iv;
            boolean bl4 = false;
            destination$iv$iv.add(it$iv$iv);
        }
        List forceSwitchPokemon = (List)destination$iv$iv;
        if (forceSwitchPokemon.isEmpty()) {
            return;
        }
        this.sendUpdate(new BattleMakeChoicePacket());
        this.mustChoose = true;
    }

    /*
     * WARNING - void declaration
     */
    public final void setActionResponses(@NotNull List<? extends ShowdownActionResponse> responses) {
        Intrinsics.checkNotNullParameter(responses, (String)"responses");
        ShowdownActionRequest showdownActionRequest = this.request;
        if (showdownActionRequest == null) {
            return;
        }
        ShowdownActionRequest request = showdownActionRequest;
        List originalPassActions = CollectionsKt.toList((Iterable)this.expectingPassActions);
        Iterable $this$forEachIndexed$iv = responses;
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            void response;
            ShowdownMoveset showdownMoveset;
            List<Object> it;
            int n;
            if ((n = index$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ShowdownActionResponse showdownActionResponse = (ShowdownActionResponse)item$iv;
            int index = n;
            boolean bl = false;
            List<ActiveBattlePokemon> it2 = this.activePokemon;
            boolean bl2 = false;
            if (it2.size() <= index) {
                return;
            }
            ActiveBattlePokemon activeBattlePokemon = it2.get(index);
            List<ShowdownMoveset> list = request.getActive();
            if (list != null) {
                it = list;
                boolean bl3 = false;
                showdownMoveset = it.size() > index ? (ShowdownMoveset)it.get(index) : null;
            } else {
                showdownMoveset = null;
            }
            ShowdownMoveset showdownMoveSet = showdownMoveset;
            it = request.getForceSwitch();
            boolean bl4 = false;
            boolean forceSwitch = it.size() > index ? (Boolean)it.get(index) : false;
            if (!response.isValid(activeBattlePokemon, showdownMoveSet, forceSwitch)) {
                this.expectingPassActions.clear();
                this.expectingPassActions.addAll(originalPassActions);
                BattlePokemon battlePokemon = activeBattlePokemon.getBattlePokemon();
                Intrinsics.checkNotNull((Object)battlePokemon);
                throw new IllegalActionChoiceException(this, "Invalid action choice for " + battlePokemon.getName().getString() + ": " + (ShowdownActionResponse)response);
            }
            if (response instanceof ForcePassActionResponse) {
                this.responses.add(this.expectingPassActions.remove(0));
                continue;
            }
            this.responses.add((ShowdownActionResponse)response);
        }
        if (this.expectingPassActions.size() > 0) {
            throw new IllegalActionChoiceException(this, "Invalid action choice: a capture was expected. Are you hacking me?");
        }
        this.mustChoose = false;
        this.getBattle().checkForInputDispatch();
    }

    public final void writeShowdownResponse() {
        List showdownMessages = new ArrayList();
        Ref.IntRef index = new Ref.IntRef();
        ShowdownActionRequest showdownActionRequest = this.request;
        Intrinsics.checkNotNull((Object)showdownActionRequest);
        showdownActionRequest.iterate(this.activePokemon, (Function3)new Function3<ActiveBattlePokemon, ShowdownMoveset, Boolean, Integer>((List<String>)showdownMessages, this, index){
            final /* synthetic */ List<String> $showdownMessages;
            final /* synthetic */ BattleActor this$0;
            final /* synthetic */ Ref.IntRef $index;
            {
                this.$showdownMessages = $showdownMessages;
                this.this$0 = $receiver;
                this.$index = $index;
                super(3);
            }

            @NotNull
            public final Integer invoke(@NotNull ActiveBattlePokemon activeBattlePokemon, @Nullable ShowdownMoveset showdownMoveSet, boolean forceSwitch) {
                Intrinsics.checkNotNullParameter((Object)activeBattlePokemon, (String)"activeBattlePokemon");
                this.$showdownMessages.add(this.this$0.getResponses().get(this.$index.element).toShowdownString(activeBattlePokemon, showdownMoveSet));
                int n = this.$index.element;
                this.$index.element = n + 1;
                return n;
            }
        });
        this.responses.clear();
        this.request = null;
        this.expectingPassActions.clear();
        String[] stringArray = new String[]{">" + this.getShowdownId() + " " + CollectionsKt.joinToString$default((Iterable)showdownMessages, null, null, null, (int)0, null, null, (int)63, null)};
        this.getBattle().writeShowdownAction(stringArray);
    }

    @NotNull
    public abstract MutableComponent getName();

    @NotNull
    public abstract MutableComponent nameOwned(@NotNull String var1);

    public void sendMessage(@NotNull Component component) {
        Intrinsics.checkNotNullParameter((Object)component, (String)"component");
        Component[] componentArray = new Component[]{component};
        this.sendUpdate(new BattleMessagePacket(componentArray));
    }

    public void awardExperience(@NotNull BattlePokemon battlePokemon, int experience) {
        Intrinsics.checkNotNullParameter((Object)battlePokemon, (String)"battlePokemon");
    }

    public void sendUpdate(@NotNull NetworkPacket<?> packet) {
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
    }
}

