/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ArrayListMultimap
 *  com.google.common.collect.Multimap
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.interaction.PokemonInteractionGUICreationEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleChallenge;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel.InteractWheelGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel.InteractWheelOption;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel.Orientation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade.ClientTradeOffer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PlayerInteractOptionsPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.BattleChallengePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle.SpectateBattlePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.interact.InteractPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.AcceptTradeRequestPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.OfferTradePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000$\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\u001a\u000f\u0010\u0001\u001a\u00020\u0000H\u0002\u00a2\u0006\u0004\b\u0001\u0010\u0002\u001a\u0015\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007\u001a\u001d\u0010\f\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\f\u0010\r\u00a8\u0006\u000e"}, d2={"", "closeGUI", "()V", "Lcom/cobblemon/mod/common/net/messages/client/PlayerInteractOptionsPacket;", "optionsPacket", "Lcom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelGUI;", "createPlayerInteractGui", "(Lcom/cobblemon/mod/common/net/messages/client/PlayerInteractOptionsPacket;)Lcom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelGUI;", "Ljava/util/UUID;", "pokemonID", "", "canMountShoulder", "createPokemonInteractGui", "(Ljava/util/UUID;Z)Lcom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelGUI;", "common"})
@SourceDebugExtension(value={"SMAP\nInteractWheelGuiFactory.kt\nKotlin\n*S Kotlin\n*F\n+ 1 InteractWheelGuiFactory.kt\ncom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelGuiFactoryKt\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,111:1\n14#2,5:112\n19#2:120\n13579#3:117\n13580#3:119\n14#4:118\n1549#5:121\n1620#5,3:122\n*S KotlinDebug\n*F\n+ 1 InteractWheelGuiFactory.kt\ncom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelGuiFactoryKt\n*L\n53#1:112,5\n53#1:120\n53#1:117\n53#1:119\n53#1:118\n95#1:121\n95#1:122,3\n*E\n"})
public final class InteractWheelGuiFactoryKt {
    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final InteractWheelGUI createPokemonInteractGui(@NotNull UUID pokemonID, boolean canMountShoulder) {
        void $this$iv;
        Intrinsics.checkNotNullParameter((Object)pokemonID, (String)"pokemonID");
        InteractWheelOption mountShoulder2 = new InteractWheelOption(MiscUtils.cobblemonResource("textures/gui/interact/icon_shoulder.png"), "cobblemon.ui.interact.mount.shoulder", null, (Function0)new Function0<Unit>(canMountShoulder, pokemonID){
            final /* synthetic */ boolean $canMountShoulder;
            final /* synthetic */ UUID $pokemonID;
            {
                this.$canMountShoulder = $canMountShoulder;
                this.$pokemonID = $pokemonID;
                super(0);
            }

            public final void invoke() {
                if (this.$canMountShoulder) {
                    new InteractPokemonPacket(this.$pokemonID, true).sendToServer();
                    InteractWheelGuiFactoryKt.access$closeGUI();
                }
            }
        }, 4, null);
        InteractWheelOption giveItem2 = new InteractWheelOption(MiscUtils.cobblemonResource("textures/gui/interact/icon_held_item.png"), "cobblemon.ui.interact.give.item", null, (Function0)new Function0<Unit>(pokemonID){
            final /* synthetic */ UUID $pokemonID;
            {
                this.$pokemonID = $pokemonID;
                super(0);
            }

            public final void invoke() {
                new InteractPokemonPacket(this.$pokemonID, false).sendToServer();
                InteractWheelGuiFactoryKt.access$closeGUI();
            }
        }, 4, null);
        ArrayListMultimap arrayListMultimap = ArrayListMultimap.create();
        Intrinsics.checkNotNullExpressionValue((Object)arrayListMultimap, (String)"create()");
        Multimap options = (Multimap)arrayListMultimap;
        options.put((Object)Orientation.TOP_RIGHT, (Object)giveItem2);
        if (canMountShoulder) {
            options.put((Object)Orientation.TOP_LEFT, (Object)mountShoulder2);
        }
        EventObservable<PokemonInteractionGUICreationEvent> eventObservable = CobblemonEvents.POKEMON_INTERACTION_GUI_CREATION;
        PokemonInteractionGUICreationEvent[] pokemonInteractionGUICreationEventArray = new PokemonInteractionGUICreationEvent[]{new PokemonInteractionGUICreationEvent(pokemonID, canMountShoulder, (Multimap<Orientation, InteractWheelOption>)options)};
        PokemonInteractionGUICreationEvent[] events$iv = pokemonInteractionGUICreationEventArray;
        boolean $i$f$post = false;
        $this$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
        PokemonInteractionGUICreationEvent[] $this$forEach$iv$iv = events$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            PokemonInteractionGUICreationEvent element$iv$iv;
            PokemonInteractionGUICreationEvent pokemonInteractionGUICreationEvent = element$iv$iv = $this$forEach$iv$iv[i];
            boolean bl = false;
            PokemonInteractionGUICreationEvent it = pokemonInteractionGUICreationEvent;
        }
        MutableComponent mutableComponent = Component.m_237115_((String)"cobblemon.ui.interact.pokemon");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"translatable(\"cobblemon.ui.interact.pokemon\")");
        return new InteractWheelGUI((Multimap<Orientation, InteractWheelOption>)options, (Component)mutableComponent);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public static final InteractWheelGUI createPlayerInteractGui(@NotNull PlayerInteractOptionsPacket optionsPacket) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)optionsPacket, (String)"optionsPacket");
        ResourceLocation resourceLocation = MiscUtils.cobblemonResource("textures/gui/interact/icon_trade.png");
        InteractWheelOption trade2 = new InteractWheelOption(resourceLocation, "cobblemon.ui.interact.trade", (Function0<? extends Vector3f>)((Function0)new Function0<Vector3f>(optionsPacket){
            final /* synthetic */ PlayerInteractOptionsPacket $optionsPacket;
            {
                this.$optionsPacket = $optionsPacket;
                super(0);
            }

            /*
             * WARNING - void declaration
             */
            @Nullable
            public final Vector3f invoke() {
                boolean bl;
                block3: {
                    void $this$any$iv;
                    Iterable iterable = CobblemonClient.INSTANCE.getRequests().getTradeOffers();
                    PlayerInteractOptionsPacket playerInteractOptionsPacket = this.$optionsPacket;
                    boolean $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        bl = false;
                    } else {
                        for (T element$iv : $this$any$iv) {
                            ClientTradeOffer it = (ClientTradeOffer)element$iv;
                            boolean bl2 = false;
                            if (!Intrinsics.areEqual((Object)it.getTraderId(), (Object)playerInteractOptionsPacket.getTargetId())) continue;
                            bl = true;
                            break block3;
                        }
                        bl = false;
                    }
                }
                return bl ? new Vector3f(0.0f, 0.6f, 0.0f) : null;
            }
        }), (Function0<Unit>)((Function0)new Function0<Unit>(optionsPacket){
            final /* synthetic */ PlayerInteractOptionsPacket $optionsPacket;
            {
                this.$optionsPacket = $optionsPacket;
                super(0);
            }

            public final void invoke() {
                Object v0;
                block3: {
                    Iterable iterable = CobblemonClient.INSTANCE.getRequests().getTradeOffers();
                    PlayerInteractOptionsPacket playerInteractOptionsPacket = this.$optionsPacket;
                    Iterable iterable2 = iterable;
                    for (T t : iterable2) {
                        ClientTradeOffer it = (ClientTradeOffer)t;
                        boolean bl = false;
                        if (!Intrinsics.areEqual((Object)it.getTraderId(), (Object)playerInteractOptionsPacket.getTargetId())) continue;
                        v0 = t;
                        break block3;
                    }
                    v0 = null;
                }
                ClientTradeOffer tradeOffer = v0;
                if (tradeOffer == null) {
                    CobblemonNetwork.INSTANCE.sendToServer(new OfferTradePacket(this.$optionsPacket.getTargetId()));
                } else {
                    ((Collection)CobblemonClient.INSTANCE.getRequests().getTradeOffers()).remove(tradeOffer);
                    CobblemonNetwork.INSTANCE.sendToServer(new AcceptTradeRequestPacket(tradeOffer.getTradeOfferId()));
                }
                InteractWheelGuiFactoryKt.access$closeGUI();
            }
        }));
        ResourceLocation resourceLocation2 = MiscUtils.cobblemonResource("textures/gui/interact/icon_battle.png");
        InteractWheelOption battle2 = new InteractWheelOption(resourceLocation2, "cobblemon.ui.interact.battle", (Function0<? extends Vector3f>)((Function0)new Function0<Vector3f>(optionsPacket){
            final /* synthetic */ PlayerInteractOptionsPacket $optionsPacket;
            {
                this.$optionsPacket = $optionsPacket;
                super(0);
            }

            /*
             * WARNING - void declaration
             */
            @Nullable
            public final Vector3f invoke() {
                boolean bl;
                block3: {
                    void $this$any$iv;
                    Iterable iterable = CobblemonClient.INSTANCE.getRequests().getBattleChallenges();
                    PlayerInteractOptionsPacket playerInteractOptionsPacket = this.$optionsPacket;
                    boolean $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        bl = false;
                    } else {
                        for (T element$iv : $this$any$iv) {
                            ClientBattleChallenge it = (ClientBattleChallenge)element$iv;
                            boolean bl2 = false;
                            if (!Intrinsics.areEqual((Object)it.getChallengerId(), (Object)playerInteractOptionsPacket.getTargetId())) continue;
                            bl = true;
                            break block3;
                        }
                        bl = false;
                    }
                }
                return bl ? new Vector3f(0.0f, 0.6f, 0.0f) : null;
            }
        }), (Function0<Unit>)((Function0)new Function0<Unit>(optionsPacket){
            final /* synthetic */ PlayerInteractOptionsPacket $optionsPacket;
            {
                this.$optionsPacket = $optionsPacket;
                super(0);
            }

            public final void invoke() {
                Object v0;
                block1: {
                    Iterable iterable = CobblemonClient.INSTANCE.getRequests().getBattleChallenges();
                    PlayerInteractOptionsPacket playerInteractOptionsPacket = this.$optionsPacket;
                    Iterable iterable2 = iterable;
                    for (T t : iterable2) {
                        ClientBattleChallenge it = (ClientBattleChallenge)t;
                        boolean bl = false;
                        if (!Intrinsics.areEqual((Object)it.getChallengerId(), (Object)playerInteractOptionsPacket.getTargetId())) continue;
                        v0 = t;
                        break block1;
                    }
                    v0 = null;
                }
                ClientBattleChallenge battleRequest = v0;
                new BattleChallengePacket(this.$optionsPacket.getNumericTargetId(), this.$optionsPacket.getSelectedPokemonId()).sendToServer();
                InteractWheelGuiFactoryKt.access$closeGUI();
            }
        }));
        ResourceLocation resourceLocation3 = MiscUtils.cobblemonResource("textures/gui/interact/icon_spectate_battle.png");
        InteractWheelOption spectate2 = new InteractWheelOption(resourceLocation3, "cobblemon.ui.interact.spectate", (Function0<? extends Vector3f>)((Function0)new Function0<Vector3f>(optionsPacket){
            final /* synthetic */ PlayerInteractOptionsPacket $optionsPacket;
            {
                this.$optionsPacket = $optionsPacket;
                super(0);
            }

            /*
             * WARNING - void declaration
             */
            @Nullable
            public final Vector3f invoke() {
                boolean bl;
                block3: {
                    void $this$any$iv;
                    Iterable iterable = CobblemonClient.INSTANCE.getRequests().getBattleChallenges();
                    PlayerInteractOptionsPacket playerInteractOptionsPacket = this.$optionsPacket;
                    boolean $i$f$any = false;
                    if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                        bl = false;
                    } else {
                        for (T element$iv : $this$any$iv) {
                            ClientBattleChallenge it = (ClientBattleChallenge)element$iv;
                            boolean bl2 = false;
                            if (!Intrinsics.areEqual((Object)it.getChallengerId(), (Object)playerInteractOptionsPacket.getTargetId())) continue;
                            bl = true;
                            break block3;
                        }
                        bl = false;
                    }
                }
                return bl ? new Vector3f(0.0f, 0.6f, 0.0f) : null;
            }
        }), (Function0<Unit>)((Function0)new Function0<Unit>(optionsPacket){
            final /* synthetic */ PlayerInteractOptionsPacket $optionsPacket;
            {
                this.$optionsPacket = $optionsPacket;
                super(0);
            }

            public final void invoke() {
                new SpectateBattlePacket(this.$optionsPacket.getTargetId()).sendToServer();
                InteractWheelGuiFactoryKt.access$closeGUI();
            }
        }));
        ArrayListMultimap arrayListMultimap = ArrayListMultimap.create();
        Intrinsics.checkNotNullExpressionValue((Object)arrayListMultimap, (String)"create()");
        Multimap options = (Multimap)arrayListMultimap;
        Iterable $this$map$iv = optionsPacket.getOptions();
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            PlayerInteractOptionsPacket.Options options2 = (PlayerInteractOptionsPacket.Options)((Object)item$iv$iv);
            Collection collection = destination$iv$iv;
            boolean bl = false;
            if (it.equals((Object)PlayerInteractOptionsPacket.Options.TRADE)) {
                options.put((Object)Orientation.TOP_LEFT, (Object)trade2);
            }
            if (it.equals((Object)PlayerInteractOptionsPacket.Options.BATTLE)) {
                options.put((Object)Orientation.TOP_RIGHT, (Object)battle2);
            }
            if (it.equals((Object)PlayerInteractOptionsPacket.Options.SPECTATE_BATTLE)) {
                options.put((Object)Orientation.TOP_RIGHT, (Object)spectate2);
            }
            collection.add(Unit.INSTANCE);
        }
        List cfr_ignored_0 = (List)destination$iv$iv;
        MutableComponent mutableComponent = Component.m_237115_((String)"cobblemon.ui.interact.player");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"translatable(\"cobblemon.ui.interact.player\")");
        return new InteractWheelGUI((Multimap<Orientation, InteractWheelOption>)options, (Component)mutableComponent);
    }

    private static final void closeGUI() {
        Minecraft.m_91087_().m_91152_(null);
    }

    public static final /* synthetic */ void access$closeGUI() {
        InteractWheelGuiFactoryKt.closeGUI();
    }
}

