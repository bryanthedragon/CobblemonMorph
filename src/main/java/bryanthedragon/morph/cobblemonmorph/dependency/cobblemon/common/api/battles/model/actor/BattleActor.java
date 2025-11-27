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

    @SuppressWarnings({"unused", "rawtypes", "unchecked"})
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
    @SuppressWarnings("rawtypes")
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
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public final void upkeep() {
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
        Iterable $this$forEachIndexed$iv$iv$iv = $this$mapIndexedNotNull$iv;
        boolean $i$f$forEachIndexed = false;
        int index$iv$iv$iv = 0;
        for (Object item$iv$iv$iv : $this$forEachIndexed$iv$iv$iv) {
            ActiveBattlePokemon it$iv$iv;
            boolean b;
            Object element$iv$iv;
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
    @SuppressWarnings({ "rawtypes", "unchecked" })
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

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public final void writeShowdownResponse() {
        List showdownMessages = new ArrayList();
        Ref.IntRef index = new Ref.IntRef();
        ShowdownActionRequest showdownActionRequest = this.request;
        Intrinsics.checkNotNull((Object)showdownActionRequest);
        showdownActionRequest.iterate(this.activePokemon, (Function3)new Function3<ActiveBattlePokemon, ShowdownMoveset, Boolean, Integer>((List<String>)showdownMessages, this, index){
            super(3);
            final /* synthetic */ List<String> $showdownMessages;
            final /* synthetic */ BattleActor this$0;
            final /* synthetic */ Ref.IntRef $index;
            {
                this.$showdownMessages = $showdownMessages;
                this.this$0 = $receiver;
                this.$index = $index;
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

