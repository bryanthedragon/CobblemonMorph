/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.trade;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stats;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.ExitButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.TypeIcon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.Summary;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.common.NatureInfoUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.trade.ModelWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.trade.PartySlot;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.trade.TradeButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade.ClientTrade;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeStartedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.CancelTradePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.ChangeTradeAcceptancePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.UpdateTradeOfferPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 _2\u00020\u0001:\u0001_B?\u0012\u0006\u0010M\u001a\u00020L\u0012\u0006\u0010R\u001a\u00020Q\u0012\u0006\u0010W\u001a\u00020V\u0012\u000e\u0010[\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010201\u0012\u000e\u00103\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010201\u00a2\u0006\u0004\b]\u0010^J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0005\u001a\u00020\u0002H\u0014\u00a2\u0006\u0004\b\u0005\u0010\u0004J'\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000f\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\rH\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J/\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J'\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u0006H\u0002\u00a2\u0006\u0004\b\u001b\u0010\u001cJI\u0010 \u001a\u00020\u00022\b\u0010\u001e\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u001f\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u0006H\u0002\u00a2\u0006\u0004\b \u0010!J#\u0010\"\u001a\u00020\u00022\b\u0010\u001e\u001a\u0004\u0018\u00010\u001d2\b\b\u0002\u0010\u001f\u001a\u00020\nH\u0002\u00a2\u0006\u0004\b\"\u0010#J\u000f\u0010$\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b$\u0010\u0004R$\u0010%\u001a\u0004\u0018\u00010\u001d8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(\"\u0004\b\"\u0010)R\u0018\u0010+\u001a\u0004\u0018\u00010*8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b+\u0010,R$\u0010-\u001a\u0004\u0018\u00010\u001d8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b-\u0010&\u001a\u0004\b.\u0010(\"\u0004\b/\u0010)R\u0018\u00100\u001a\u0004\u0018\u00010*8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b0\u0010,R\u001f\u00103\u001a\n\u0012\u0006\u0012\u0004\u0018\u000102018\u0006\u00a2\u0006\f\n\u0004\b3\u00104\u001a\u0004\b5\u00106R\"\u00107\u001a\u00020\u00068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b7\u00108\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\"\u0010=\u001a\u00020\u00068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b=\u00108\u001a\u0004\b>\u0010:\"\u0004\b?\u0010<R\"\u0010@\u001a\u00020\n8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b@\u0010A\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\"\u0010F\u001a\u00020\u00068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bF\u00108\u001a\u0004\bG\u0010:\"\u0004\bH\u0010<R\"\u0010I\u001a\u00020\u00068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bI\u00108\u001a\u0004\bJ\u0010:\"\u0004\bK\u0010<R\u0017\u0010M\u001a\u00020L8\u0006\u00a2\u0006\f\n\u0004\bM\u0010N\u001a\u0004\bO\u0010PR\u0017\u0010R\u001a\u00020Q8\u0006\u00a2\u0006\f\n\u0004\bR\u0010S\u001a\u0004\bT\u0010UR\u0017\u0010W\u001a\u00020V8\u0006\u00a2\u0006\f\n\u0004\bW\u0010X\u001a\u0004\bY\u0010ZR\u001f\u0010[\u001a\n\u0012\u0006\u0012\u0004\u0018\u000102018\u0006\u00a2\u0006\f\n\u0004\b[\u00104\u001a\u0004\b\\\u00106\u00a8\u0006`"}, d2={"Lcom/cobblemon/mod/common/client/gui/trade/TradeGUI;", "Lnet/minecraft/client/gui/screens/Screen;", "", "close", "()V", "init", "", "keyCode", "scanCode", "modifiers", "", "keyPressed", "(III)Z", "Lnet/minecraft/sounds/SoundEvent;", "soundEvent", "playSound", "(Lnet/minecraft/sounds/SoundEvent;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "mouseX", "mouseY", "", "delta", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "x", "y", "renderInfoLabels", "(Lnet/minecraft/client/gui/GuiGraphics;II)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "isOpposing", "renderPokemonInfo", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;ZLnet/minecraft/client/gui/GuiGraphics;IIII)V", "setOfferedPokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Z)V", "tick", "offeredPokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getOfferedPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "Lcom/cobblemon/mod/common/client/gui/trade/ModelWidget;", "offeredPokemonModel", "Lcom/cobblemon/mod/common/client/gui/trade/ModelWidget;", "opposingOfferedPokemon", "getOpposingOfferedPokemon", "setOpposingOfferedPokemon", "opposingOfferedPokemonModel", "", "Lcom/cobblemon/mod/common/net/messages/client/trade/TradeStartedPacket$TradeablePokemon;", "party", "Ljava/util/List;", "getParty", "()Ljava/util/List;", "protectiveTicks", "I", "getProtectiveTicks", "()I", "setProtectiveTicks", "(I)V", "readyProgress", "getReadyProgress", "setReadyProgress", "selectPointerOffsetIncrement", "Z", "getSelectPointerOffsetIncrement", "()Z", "setSelectPointerOffsetIncrement", "(Z)V", "selectPointerOffsetY", "getSelectPointerOffsetY", "setSelectPointerOffsetY", "ticksElapsed", "getTicksElapsed", "setTicksElapsed", "Lcom/cobblemon/mod/common/client/trade/ClientTrade;", "trade", "Lcom/cobblemon/mod/common/client/trade/ClientTrade;", "getTrade", "()Lcom/cobblemon/mod/common/client/trade/ClientTrade;", "Ljava/util/UUID;", "traderId", "Ljava/util/UUID;", "getTraderId", "()Ljava/util/UUID;", "Lnet/minecraft/network/chat/MutableComponent;", "traderName", "Lnet/minecraft/network/chat/MutableComponent;", "getTraderName", "()Lnet/minecraft/network/chat/MutableComponent;", "traderParty", "getTraderParty", "<init>", "(Lcom/cobblemon/mod/common/client/trade/ClientTrade;Ljava/util/UUID;Lnet/minecraft/network/chat/MutableComponent;Ljava/util/List;Ljava/util/List;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nTradeGUI.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TradeGUI.kt\ncom/cobblemon/mod/common/client/gui/trade/TradeGUI\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,912:1\n1#2:913\n*E\n"})
public final class TradeGUI
extends Screen {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ClientTrade trade;
    @NotNull
    private final UUID traderId;
    @NotNull
    private final MutableComponent traderName;
    @NotNull
    private final List<TradeStartedPacket.TradeablePokemon> traderParty;
    @NotNull
    private final List<TradeStartedPacket.TradeablePokemon> party;
    @Nullable
    private ModelWidget offeredPokemonModel;
    @Nullable
    private ModelWidget opposingOfferedPokemonModel;
    @Nullable
    private Pokemon offeredPokemon;
    @Nullable
    private Pokemon opposingOfferedPokemon;
    private int ticksElapsed;
    private int selectPointerOffsetY;
    private int readyProgress;
    private boolean selectPointerOffsetIncrement;
    private int protectiveTicks;
    public static final int BASE_WIDTH = 293;
    public static final int BASE_HEIGHT = 212;
    public static final int BASE_BACKGROUND_WIDTH = 157;
    public static final int BASE_BACKGROUND_HEIGHT = 85;
    public static final int PARTY_SLOT_PADDING = 4;
    public static final int PORTRAIT_SIZE = 78;
    public static final int TYPE_SPACER_WIDTH = 134;
    public static final int TYPE_SPACER_HEIGHT = 12;
    public static final int TRADE_READY_WIDTH = 28;
    public static final int TRADE_READY_HEIGHT = 6;
    public static final int TRADE_READY_TOP_HEIGHT = 5;
    public static final int READY_PROGRESS_LIMIT = 6;
    public static final float SCALE = 0.5f;
    @NotNull
    private static final ResourceLocation baseResource = MiscUtilsKt.cobblemonResource("textures/gui/trade/trade_base.png");
    @NotNull
    private static final ResourceLocation baseBackgroundResource = MiscUtilsKt.cobblemonResource("textures/gui/trade/trade_background.png");
    @NotNull
    private static final ResourceLocation typeSpacerResource = MiscUtilsKt.cobblemonResource("textures/gui/trade/type_spacer.png");
    @NotNull
    private static final ResourceLocation typeSpacerSingleResource = MiscUtilsKt.cobblemonResource("textures/gui/trade/type_spacer_single.png");
    @NotNull
    private static final ResourceLocation typeSpacerDoubleResource = MiscUtilsKt.cobblemonResource("textures/gui/trade/type_spacer_double.png");
    @NotNull
    private static final ResourceLocation tradeReadyResource = MiscUtilsKt.cobblemonResource("textures/gui/trade/trade_ready.png");
    @NotNull
    private static final ResourceLocation tradeReadyTopResource = MiscUtilsKt.cobblemonResource("textures/gui/trade/trade_ready_top.png");
    @NotNull
    private static final ResourceLocation opposingTradeReadyResource = MiscUtilsKt.cobblemonResource("textures/gui/trade/trade_ready_opposing.png");
    @NotNull
    private static final ResourceLocation opposingTradeReadyTopResource = MiscUtilsKt.cobblemonResource("textures/gui/trade/trade_ready_top_opposing.png");

    public TradeGUI(@NotNull ClientTrade trade2, @NotNull UUID traderId, @NotNull MutableComponent traderName, @NotNull List<TradeStartedPacket.TradeablePokemon> traderParty, @NotNull List<TradeStartedPacket.TradeablePokemon> party) {
        Intrinsics.checkNotNullParameter((Object)trade2, (String)"trade");
        Intrinsics.checkNotNullParameter((Object)traderId, (String)"traderId");
        Intrinsics.checkNotNullParameter((Object)traderName, (String)"traderName");
        Intrinsics.checkNotNullParameter(traderParty, (String)"traderParty");
        Intrinsics.checkNotNullParameter(party, (String)"party");
        super((Component)LocalizationUtilsKt.lang("trade.gui.title", new Object[0]));
        this.trade = trade2;
        this.traderId = traderId;
        this.traderName = traderName;
        this.traderParty = traderParty;
        this.party = party;
        Observable.DefaultImpls.subscribe$default(this.trade.getCancelEmitter(), null, (Function1)new Function1<Unit, Unit>(){

            public final void invoke(@NotNull Unit it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                TradeGUI.super.m_7379_();
            }
        }, 1, null);
        Observable.DefaultImpls.subscribe$default(this.trade.getCompletedEmitter(), null, (Function1)new Function1<Pair<? extends UUID, ? extends UUID>, Unit>(){

            public final void invoke(@NotNull Pair<UUID, UUID> it) {
                Object v3;
                TradeStartedPacket.TradeablePokemon myTradedPokemon;
                block4: {
                    Object object;
                    Object object22;
                    UUID pokemonId2;
                    block3: {
                        Intrinsics.checkNotNullParameter(it, (String)"it");
                        UUID pokemonId1 = (UUID)it.component1();
                        pokemonId2 = (UUID)it.component2();
                        Iterable iterable = this.getParty();
                        for (Object object22 : iterable) {
                            TradeStartedPacket.TradeablePokemon it2 = (TradeStartedPacket.TradeablePokemon)object22;
                            boolean bl = false;
                            TradeStartedPacket.TradeablePokemon tradeablePokemon = it2;
                            if (!Intrinsics.areEqual((Object)(tradeablePokemon != null ? tradeablePokemon.getPokemonId() : null), (Object)pokemonId1)) continue;
                            object = object22;
                            break block3;
                        }
                        object = null;
                    }
                    myTradedPokemon = (TradeStartedPacket.TradeablePokemon)object;
                    Iterable iterable = this.getTraderParty();
                    object22 = iterable.iterator();
                    while (object22.hasNext()) {
                        Object e = object22.next();
                        TradeStartedPacket.TradeablePokemon it3 = (TradeStartedPacket.TradeablePokemon)e;
                        boolean bl = false;
                        TradeStartedPacket.TradeablePokemon tradeablePokemon = it3;
                        if (!Intrinsics.areEqual((Object)(tradeablePokemon != null ? tradeablePokemon.getPokemonId() : null), (Object)pokemonId2)) continue;
                        v3 = e;
                        break block4;
                    }
                    v3 = null;
                }
                TradeStartedPacket.TradeablePokemon theirTradedPokemon = v3;
                if (myTradedPokemon == null || theirTradedPokemon == null) {
                    CobblemonNetwork.INSTANCE.sendToServer(new CancelTradePacket());
                    this.m_7379_();
                    return;
                }
                int i1 = this.getParty().indexOf(myTradedPokemon);
                int i2 = this.getTraderParty().indexOf(theirTradedPokemon);
                this.getParty().set(i1, theirTradedPokemon);
                this.getTraderParty().set(i2, myTradedPokemon);
                this.setOfferedPokemon(null);
                this.setOpposingOfferedPokemon(null);
                this.setTicksElapsed(0);
                this.setReadyProgress(0);
                this.getTrade().getOppositeAcceptedMyOffer().set(false);
                this.setOfferedPokemon(null, true);
                this.setOfferedPokemon(null, false);
                this.m_232761_();
            }
        }, 1, null);
        Observable.DefaultImpls.subscribe$default(this.trade.getOppositeOffer(), null, (Function1)new Function1<Pokemon, Unit>(){

            public final void invoke(@Nullable Pokemon newOffer) {
                this.setOfferedPokemon(newOffer, true);
            }
        }, 1, null);
        Observable.DefaultImpls.subscribe$default(this.trade.getMyOffer(), null, (Function1)new Function1<Pokemon, Unit>(){

            public final void invoke(@Nullable Pokemon myOffer) {
                TradeGUI.setOfferedPokemon$default(this, myOffer, false, 2, null);
            }
        }, 1, null);
        Observable.DefaultImpls.subscribe$default(this.trade.getOppositeAcceptedMyOffer(), null, (Function1)new Function1<Boolean, Unit>(){

            public final void invoke(boolean it) {
                this.setTicksElapsed(0);
                this.setReadyProgress(0);
            }
        }, 1, null);
    }

    @NotNull
    public final ClientTrade getTrade() {
        return this.trade;
    }

    @NotNull
    public final UUID getTraderId() {
        return this.traderId;
    }

    @NotNull
    public final MutableComponent getTraderName() {
        return this.traderName;
    }

    @NotNull
    public final List<TradeStartedPacket.TradeablePokemon> getTraderParty() {
        return this.traderParty;
    }

    @NotNull
    public final List<TradeStartedPacket.TradeablePokemon> getParty() {
        return this.party;
    }

    @Nullable
    public final Pokemon getOfferedPokemon() {
        return this.offeredPokemon;
    }

    public final void setOfferedPokemon(@Nullable Pokemon pokemon) {
        this.offeredPokemon = pokemon;
    }

    @Nullable
    public final Pokemon getOpposingOfferedPokemon() {
        return this.opposingOfferedPokemon;
    }

    public final void setOpposingOfferedPokemon(@Nullable Pokemon pokemon) {
        this.opposingOfferedPokemon = pokemon;
    }

    public final int getTicksElapsed() {
        return this.ticksElapsed;
    }

    public final void setTicksElapsed(int n) {
        this.ticksElapsed = n;
    }

    public final int getSelectPointerOffsetY() {
        return this.selectPointerOffsetY;
    }

    public final void setSelectPointerOffsetY(int n) {
        this.selectPointerOffsetY = n;
    }

    public final int getReadyProgress() {
        return this.readyProgress;
    }

    public final void setReadyProgress(int n) {
        this.readyProgress = n;
    }

    public final boolean getSelectPointerOffsetIncrement() {
        return this.selectPointerOffsetIncrement;
    }

    public final void setSelectPointerOffsetIncrement(boolean bl) {
        this.selectPointerOffsetIncrement = bl;
    }

    public final int getProtectiveTicks() {
        return this.protectiveTicks;
    }

    public final void setProtectiveTicks(int n) {
        this.protectiveTicks = n;
    }

    protected void m_7856_() {
        int offsetY;
        int slotY;
        int slotX;
        int partyIndex;
        int x = (this.f_96543_ - 293) / 2;
        int y = (this.f_96544_ - 212) / 2;
        this.m_142416_((GuiEventListener)new ExitButton(x + 265, y + 6, arg_0 -> TradeGUI.init$lambda$0(this, arg_0)));
        this.m_142416_((GuiEventListener)new TradeButton(x + 120, y + 119, this, arg_0 -> TradeGUI.init$lambda$1(this, arg_0)));
        for (partyIndex = 0; partyIndex < 6; ++partyIndex) {
            PartySlot offsetIndex2;
            slotX = x + 9;
            slotY = y + 38;
            if (partyIndex > 0) {
                boolean isEven = partyIndex % 2 == 0;
                int offsetIndex2 = (partyIndex - (isEven ? 0 : 1)) / 2;
                int offsetX = isEven ? 0 : 29;
                offsetY = isEven ? 0 : -8;
                slotX += offsetX;
                slotY += 29 * offsetIndex2 + offsetY;
            }
            TradeStartedPacket.TradeablePokemon pokemon = this.party.get(partyIndex);
            PartySlot widget = offsetIndex2 = new PartySlot(slotX, slotY, pokemon, this, false, arg_0 -> TradeGUI.init$lambda$3(this, pokemon, partyIndex, arg_0), 16, null);
            boolean bl = false;
            this.m_142416_((GuiEventListener)widget);
        }
        for (partyIndex = 0; partyIndex < 6; ++partyIndex) {
            PartySlot partySlot;
            slotX = x + 230;
            slotY = y + 30;
            if (partyIndex > 0) {
                boolean isEven = partyIndex % 2 == 0;
                int offsetIndex = (partyIndex - (isEven ? 0 : 1)) / 2;
                int offsetX = isEven ? 0 : 29;
                offsetY = isEven ? 0 : 8;
                slotX += offsetX;
                slotY += 29 * offsetIndex + offsetY;
            }
            PartySlot widget = partySlot = new PartySlot(slotX, slotY, this.traderParty.get(partyIndex), this, true, TradeGUI::init$lambda$5);
            boolean bl = false;
            this.m_142416_((GuiEventListener)widget);
        }
        this.setOfferedPokemon(this.offeredPokemon, false);
        this.setOfferedPokemon(this.opposingOfferedPokemon, true);
    }

    /*
     * Unable to fully structure code
     */
    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        block11: {
            block10: {
                Intrinsics.checkNotNullParameter((Object)context, (String)"context");
                x = (this.f_96543_ - 293) / 2;
                y = (this.f_96544_ - 212) / 2;
                matrices = context.m_280168_();
                backgroundX = x + 68;
                backgroundY = y + 23;
                var10_10 = TradeGUI.baseBackgroundResource;
                Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
                GuiUtilsKt.blitk$default(matrices, var10_10, backgroundX, backgroundY, 85, 157, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
                context.m_280588_(backgroundX, backgroundY, backgroundX + 157, backgroundY + 85);
                v0 = this.offeredPokemonModel;
                if (v0 != null) {
                    v0.m_88315_(context, mouseX, mouseY, delta);
                }
                v1 = this.opposingOfferedPokemonModel;
                if (v1 != null) {
                    v1.m_88315_(context, mouseX, mouseY, delta);
                }
                context.m_280618_();
                var10_10 = TradeGUI.baseResource;
                GuiUtilsKt.blitk$default(matrices, var10_10, x, y, 212, 293, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
                this.renderInfoLabels(context, x, y);
                this.renderPokemonInfo(this.offeredPokemon, false, context, x, y, mouseX, mouseY);
                this.renderPokemonInfo(this.opposingOfferedPokemon, true, context, x, y, mouseX, mouseY);
                if (this.trade.getAcceptedOppositeOffer()) {
                    var10_10 = TradeGUI.tradeReadyResource;
                    var11_13 = x + 85;
                    var12_14 = y + 126;
                    var13_15 = 6 * this.readyProgress;
                    GuiUtilsKt.blitk$default(matrices, var10_10, var11_13, var12_14, 6, 28, null, var13_15, null, 36, null, null, null, null, null, false, 0.0f, 130368, null);
                    var10_10 = TradeGUI.tradeReadyTopResource;
                    var11_13 = x + 112;
                    var12_14 = y + 2;
                    var13_15 = 5 * this.readyProgress;
                    GuiUtilsKt.blitk$default(matrices, var10_10, var11_13, var12_14, 5, 28, null, var13_15, null, 30, null, null, null, null, null, false, 0.0f, 130368, null);
                }
                if (this.trade.getOppositeAcceptedMyOffer().get().booleanValue()) {
                    var10_10 = TradeGUI.opposingTradeReadyResource;
                    var11_13 = x + 180;
                    var12_14 = y + 126;
                    var13_15 = 6 * this.readyProgress;
                    GuiUtilsKt.blitk$default(matrices, var10_10, var11_13, var12_14, 6, 28, null, var13_15, null, 36, null, null, null, null, null, false, 0.0f, 130368, null);
                    var10_10 = TradeGUI.opposingTradeReadyTopResource;
                    var11_13 = x + 153;
                    var12_14 = y + 2;
                    var13_15 = 5 * this.readyProgress;
                    GuiUtilsKt.blitk$default(matrices, var10_10, var11_13, var12_14, 5, 28, null, var13_15, null, 30, null, null, null, null, null, false, 0.0f, 130368, null);
                }
                v2 = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
                v3 = Minecraft.m_91087_().m_91094_().m_92546_();
                Intrinsics.checkNotNullExpressionValue((Object)v3, (String)"getInstance().session.username");
                RenderHelperKt.drawScaledText$default(context, v2, TextKt.bold(TextKt.text(v3)), x + 57, (double)y - 10.5, 0.0f, null, 0, 0, true, true, null, null, 6624, null);
                RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), TextKt.bold(this.traderName), x + 237, (double)y - 10.5, 0.0f, null, 0, 0, true, true, null, null, 6624, null);
                super.m_88315_(context, mouseX, mouseY, delta);
                if (this.offeredPokemon == null) break block10;
                v4 = this.offeredPokemon;
                Intrinsics.checkNotNull((Object)v4);
                if (v4.heldItemNoCopy$common().m_41619_()) break block10;
                itemX = x + 50;
                itemY = y + 125;
                var13_16 = itemX;
                var14_18 = (float)itemX + (float)16;
                var15_19 = mouseX;
                v5 = var13_16 <= var15_19 ? var15_19 <= var14_18 : false;
                if (!v5) ** GOTO lbl-1000
                var13_16 = itemY;
                var14_18 = (float)itemY + (float)16;
                var15_19 = mouseY;
                v6 = var13_16 <= var15_19 ? var15_19 <= var14_18 : false;
                if (v6) {
                    v7 = true;
                } else lbl-1000:
                // 2 sources

                {
                    v7 = itemHovered = false;
                }
                if (itemHovered) {
                    v8 = Minecraft.m_91087_().f_91062_;
                    v9 = this.offeredPokemon;
                    Intrinsics.checkNotNull((Object)v9);
                    context.m_280153_(v8, v9.heldItemNoCopy$common(), mouseX, mouseY);
                }
            }
            if (this.opposingOfferedPokemon == null) break block11;
            v10 = this.opposingOfferedPokemon;
            Intrinsics.checkNotNull((Object)v10);
            if (v10.heldItemNoCopy$common().m_41619_()) break block11;
            itemX = x + 227;
            itemY = y + 125;
            var13_17 = itemX;
            var14_18 = (float)itemX + (float)16;
            var15_19 = mouseX;
            v11 = var13_17 <= var15_19 ? var15_19 <= var14_18 : false;
            if (!v11) ** GOTO lbl-1000
            var13_17 = itemY;
            var14_18 = (float)itemY + (float)16;
            var15_19 = mouseY;
            v12 = var13_17 <= var15_19 ? var15_19 <= var14_18 : false;
            if (v12) {
                v13 = true;
            } else lbl-1000:
            // 2 sources

            {
                v13 = itemHovered = false;
            }
            if (itemHovered) {
                v14 = Minecraft.m_91087_().f_91062_;
                v15 = this.opposingOfferedPokemon;
                Intrinsics.checkNotNull((Object)v15);
                context.m_280153_(v14, v15.heldItemNoCopy$common(), mouseX, mouseY);
            }
        }
    }

    public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            new CancelTradePacket().sendToServer();
        }
        return super.m_7933_(keyCode, scanCode, modifiers);
    }

    public void m_86600_() {
        int delayFactor;
        int n = this.ticksElapsed;
        this.ticksElapsed = n + 1;
        if (this.protectiveTicks > 0) {
            n = this.protectiveTicks;
            this.protectiveTicks = n + -1;
        }
        if (this.ticksElapsed % (2 * (delayFactor = 3)) == 0) {
            boolean bl = this.selectPointerOffsetIncrement = !this.selectPointerOffsetIncrement;
        }
        if (this.ticksElapsed % delayFactor == 0) {
            this.selectPointerOffsetY += this.selectPointerOffsetIncrement ? 1 : -1;
        }
        if (this.ticksElapsed % 6 == 0) {
            this.readyProgress = this.readyProgress == 6 ? 0 : this.readyProgress + 1;
        }
    }

    public void m_7379_() {
        CobblemonNetwork.INSTANCE.sendToServer(new CancelTradePacket());
        super.m_7379_();
    }

    private final void setOfferedPokemon(Pokemon pokemon, boolean isOpposing) {
        this.protectiveTicks = 20;
        int x = (this.f_96543_ - 293) / 2;
        int y = (this.f_96544_ - 212) / 2;
        if (isOpposing) {
            this.opposingOfferedPokemon = pokemon;
            this.opposingOfferedPokemonModel = pokemon != null ? new ModelWidget(x + 147, y + 30, 78, 78, pokemon.asRenderablePokemon(), 2.0f, 35.0f, -10.0) : null;
            this.trade.setAcceptedOppositeOffer(false);
        } else {
            this.offeredPokemon = pokemon;
            this.offeredPokemonModel = pokemon != null ? new ModelWidget(x + 68, y + 30, 78, 78, pokemon.asRenderablePokemon(), 2.0f, 325.0f, -10.0) : null;
        }
    }

    static /* synthetic */ void setOfferedPokemon$default(TradeGUI tradeGUI, Pokemon pokemon, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = false;
        }
        tradeGUI.setOfferedPokemon(pokemon, bl);
    }

    private final void playSound(SoundEvent soundEvent) {
        Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)soundEvent, (float)1.0f));
    }

    private final void renderPokemonInfo(Pokemon pokemon, boolean isOpposing, GuiGraphics context, int x, int y, int mouseX, int mouseY) {
        if (pokemon != null) {
            PoseStack matrices = context.m_280168_();
            int levelXOffset = isOpposing ? 117 : 0;
            ResourceLocation resourceLocation = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.lv", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.lv\")");
            RenderHelperKt.drawScaledText$default(context, resourceLocation, TextKt.bold(mutableComponent), x + 76 + levelXOffset, (double)y + 1.5, 0.0f, null, 0, 0, false, true, null, null, 7136, null);
            RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), TextKt.bold(TextKt.text(String.valueOf(pokemon.getLevel()))), x + 89 + levelXOffset, (double)y + 1.5, 0.0f, null, 0, 0, false, true, null, null, 7136, null);
            int nameXOffset = isOpposing ? 75 : 0;
            ResourceLocation ballResource = MiscUtilsKt.cobblemonResource("textures/item/poke_balls/" + pokemon.getCaughtBall().getName().m_135815_() + ".png");
            PoseStack poseStack = context.m_280168_();
            double d = ((double)x + 73.5 + (double)nameXOffset) / (double)0.5f;
            float f = (float)(y + 12) / 0.5f;
            Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
            GuiUtilsKt.blitk$default(poseStack, ballResource, d, Float.valueOf(f), 16, 16, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
            RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), TextKt.bold(pokemon.getDisplayName()), x + 82 + nameXOffset, (double)y + 11.5, 0.0f, null, 0, 0, false, true, null, null, 7136, null);
            if (pokemon.getGender() != Gender.GENDERLESS) {
                boolean isMale = pokemon.getGender() == Gender.MALE;
                MutableComponent textSymbol = isMale ? TextKt.bold(TextKt.text("\u2642")) : TextKt.bold(TextKt.text("\u2640"));
                RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), textSymbol, x + 139 + nameXOffset, (double)y + 11.5, 0.0f, null, 0, isMale ? 3329023 : 16536660, false, true, null, null, 6880, null);
            }
            ItemStack heldItem2 = pokemon.heldItemNoCopy$common();
            int itemX = x + (isOpposing ? 227 : 50);
            int itemY = y + 125;
            if (!heldItem2.m_41619_()) {
                Font textRenderer22 = Minecraft.m_91087_().f_91062_;
                context.m_280480_(heldItem2, itemX, itemY);
                context.m_280370_(textRenderer22, heldItem2, itemX, itemY);
            }
            if (pokemon.getShiny()) {
                ResourceLocation textRenderer22 = Summary.Companion.getIconShinyResource();
                double d2 = ((double)x + (isOpposing ? 214.5 : 71.5)) / (double)0.5f;
                double d3 = ((double)y + 33.5) / (double)0.5f;
                Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
                GuiUtilsKt.blitk$default(matrices, textRenderer22, d2, d3, 14, 14, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
            }
            ResourceLocation textRenderer22 = pokemon.getSecondaryType() != null ? typeSpacerDoubleResource : typeSpacerSingleResource;
            float f2 = (float)(x + (isOpposing ? 153 : 73)) / 0.5f;
            double d4 = ((double)y + 113.5) / (double)0.5f;
            int n = isOpposing ? 12 : 0;
            Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
            GuiUtilsKt.blitk$default(matrices, textRenderer22, Float.valueOf(f2), d4, 12, 134, null, n, null, 24, null, null, null, null, null, false, 0.5f, 64832, null);
            int textRenderer22 = x + (isOpposing ? 187 : 106);
            int n2 = y + 112;
            ElementalType elementalType = pokemon.getPrimaryType();
            ElementalType elementalType2 = pokemon.getSecondaryType();
            new TypeIcon(textRenderer22, n2, elementalType, elementalType2, true, true, 10.0f, 5.0f, 0.0f, 256, null).render(context);
            int labelXOffset = isOpposing ? 77 : 0;
            MutableComponent natureText = NatureInfoUtilsKt.reformatNatureTextIfMinted(pokemon);
            int n3 = x + 108 + labelXOffset;
            double d5 = (double)y + 146.5;
            RenderHelperKt.drawScaledText$default(context, null, natureText, n3, d5, 0.5f, null, 0, 0, true, true, mouseX, mouseY, 450, null);
            MutableComponent mutableComponent2 = MiscUtilsKt.asTranslated(pokemon.getAbility().getDisplayName());
            int n4 = x + 108 + labelXOffset;
            double d6 = (double)y + 163.5;
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"asTranslated()");
            RenderHelperKt.drawScaledText$default(context, null, mutableComponent2, n4, d6, 0.5f, null, 0, 0, true, true, null, null, 6594, null);
            List<Move> moves = pokemon.getMoveSet().getMoves();
            int n5 = moves.size();
            for (int i = 0; i < n5; ++i) {
                MutableComponent mutableComponent3 = moves.get(i).getDisplayName();
                int n6 = x + 108 + labelXOffset;
                double d7 = (double)y + 180.5 + (double)(7 * i);
                RenderHelperKt.drawScaledText$default(context, null, mutableComponent3, n6, d7, 0.5f, null, 0, 0, true, true, null, null, 6594, null);
            }
            int ivXOffset = isOpposing ? 205 : -13;
            RenderHelperKt.drawScaledText$default(context, null, TextKt.text(String.valueOf(pokemon.getIvs().getOrDefault(Stats.HP))), x + 60 + ivXOffset, (double)y + 155.5, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
            RenderHelperKt.drawScaledText$default(context, null, TextKt.text(String.valueOf(pokemon.getIvs().getOrDefault(Stats.ATTACK))), x + 60 + ivXOffset, (double)y + 163.5, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
            RenderHelperKt.drawScaledText$default(context, null, TextKt.text(String.valueOf(pokemon.getIvs().getOrDefault(Stats.DEFENCE))), x + 60 + ivXOffset, (double)y + 171.5, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
            RenderHelperKt.drawScaledText$default(context, null, TextKt.text(String.valueOf(pokemon.getIvs().getOrDefault(Stats.SPECIAL_ATTACK))), x + 60 + ivXOffset, (double)y + 179.5, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
            RenderHelperKt.drawScaledText$default(context, null, TextKt.text(String.valueOf(pokemon.getIvs().getOrDefault(Stats.SPECIAL_DEFENCE))), x + 60 + ivXOffset, (double)y + 187.5, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
            RenderHelperKt.drawScaledText$default(context, null, TextKt.text(String.valueOf(pokemon.getIvs().getOrDefault(Stats.SPEED))), x + 60 + ivXOffset, (double)y + 195.5, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
            int evXOffset = isOpposing ? 221 : 3;
            RenderHelperKt.drawScaledText$default(context, null, TextKt.text(String.valueOf(pokemon.getEvs().getOrDefault(Stats.HP))), x + 60 + evXOffset, (double)y + 155.5, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
            RenderHelperKt.drawScaledText$default(context, null, TextKt.text(String.valueOf(pokemon.getEvs().getOrDefault(Stats.ATTACK))), x + 60 + evXOffset, (double)y + 163.5, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
            RenderHelperKt.drawScaledText$default(context, null, TextKt.text(String.valueOf(pokemon.getEvs().getOrDefault(Stats.DEFENCE))), x + 60 + evXOffset, (double)y + 171.5, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
            RenderHelperKt.drawScaledText$default(context, null, TextKt.text(String.valueOf(pokemon.getEvs().getOrDefault(Stats.SPECIAL_ATTACK))), x + 60 + evXOffset, (double)y + 179.5, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
            RenderHelperKt.drawScaledText$default(context, null, TextKt.text(String.valueOf(pokemon.getEvs().getOrDefault(Stats.SPECIAL_DEFENCE))), x + 60 + evXOffset, (double)y + 187.5, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
            RenderHelperKt.drawScaledText$default(context, null, TextKt.text(String.valueOf(pokemon.getEvs().getOrDefault(Stats.SPEED))), x + 60 + evXOffset, (double)y + 195.5, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
        } else {
            PoseStack poseStack = context.m_280168_();
            ResourceLocation resourceLocation = typeSpacerResource;
            float f = (float)(x + (isOpposing ? 153 : 73)) / 0.5f;
            double d = ((double)y + 113.5) / (double)0.5f;
            int n = isOpposing ? 12 : 0;
            Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
            GuiUtilsKt.blitk$default(poseStack, resourceLocation, Float.valueOf(f), d, 12, 134, null, n, null, 24, null, null, null, null, null, false, 0.5f, 64832, null);
        }
    }

    private final void renderInfoLabels(GuiGraphics context, int x, int y) {
        ResourceLocation resourceLocation = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
        MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.party", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.party\")");
        RenderHelperKt.drawScaledText$default(context, resourceLocation, TextKt.bold(mutableComponent), (double)x + 25.5, y + 7, 0.0f, null, 0, 0, true, false, null, null, 7648, null);
        MutableComponent mutableComponent2 = LocalizationUtilsKt.lang("ui.info.nature", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"lang(\"ui.info.nature\")");
        MutableComponent mutableComponent3 = TextKt.bold(mutableComponent2);
        int n = x + 108;
        double d = (double)y + 139.5;
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent3, n, d, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
        MutableComponent mutableComponent4 = LocalizationUtilsKt.lang("ui.info.ability", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent4, (String)"lang(\"ui.info.ability\")");
        mutableComponent3 = TextKt.bold(mutableComponent4);
        n = x + 108;
        d = (double)y + 156.5;
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent3, n, d, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
        MutableComponent mutableComponent5 = LocalizationUtilsKt.lang("ui.moves", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent5, (String)"lang(\"ui.moves\")");
        mutableComponent3 = TextKt.bold(mutableComponent5);
        n = x + 108;
        d = (double)y + 173.5;
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent3, n, d, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
        MutableComponent mutableComponent6 = LocalizationUtilsKt.lang("held_item", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent6, (String)"lang(\"held_item\")");
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent6, (double)x + 22.5, (double)y + 135.5, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
        MutableComponent mutableComponent7 = LocalizationUtilsKt.lang("ui.stats.ivs", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent7, (String)"lang(\"ui.stats.ivs\")");
        mutableComponent3 = TextKt.bold(mutableComponent7);
        n = x + 47;
        d = (double)y + 147.5;
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent3, n, d, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
        MutableComponent mutableComponent8 = LocalizationUtilsKt.lang("ui.stats.evs", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent8, (String)"lang(\"ui.stats.evs\")");
        mutableComponent3 = TextKt.bold(mutableComponent8);
        double d2 = (double)x + 62.5;
        double d3 = (double)y + 147.5;
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent3, d2, d3, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
        MutableComponent mutableComponent9 = LocalizationUtilsKt.lang("ui.stats.hp", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent9, (String)"lang(\"ui.stats.hp\")");
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent9, (double)x + 9.5, (double)y + 155.5, 0.5f, null, 0, 0, false, false, null, null, 8130, null);
        MutableComponent mutableComponent10 = LocalizationUtilsKt.lang("ui.stats.atk", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent10, (String)"lang(\"ui.stats.atk\")");
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent10, (double)x + 9.5, (double)y + 163.5, 0.5f, null, 0, 0, false, false, null, null, 8130, null);
        MutableComponent mutableComponent11 = LocalizationUtilsKt.lang("ui.stats.def", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent11, (String)"lang(\"ui.stats.def\")");
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent11, (double)x + 9.5, (double)y + 171.5, 0.5f, null, 0, 0, false, false, null, null, 8130, null);
        MutableComponent mutableComponent12 = LocalizationUtilsKt.lang("ui.stats.sp_atk", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent12, (String)"lang(\"ui.stats.sp_atk\")");
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent12, (double)x + 9.5, (double)y + 179.5, 0.5f, null, 0, 0, false, false, null, null, 8130, null);
        MutableComponent mutableComponent13 = LocalizationUtilsKt.lang("ui.stats.sp_def", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent13, (String)"lang(\"ui.stats.sp_def\")");
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent13, (double)x + 9.5, (double)y + 187.5, 0.5f, null, 0, 0, false, false, null, null, 8130, null);
        MutableComponent mutableComponent14 = LocalizationUtilsKt.lang("ui.stats.speed", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent14, (String)"lang(\"ui.stats.speed\")");
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent14, (double)x + 9.5, (double)y + 195.5, 0.5f, null, 0, 0, false, false, null, null, 8130, null);
        MutableComponent mutableComponent15 = LocalizationUtilsKt.lang("ui.info.nature", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent15, (String)"lang(\"ui.info.nature\")");
        mutableComponent3 = TextKt.bold(mutableComponent15);
        int n2 = x + 185;
        d = (double)y + 139.5;
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent3, n2, d, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
        MutableComponent mutableComponent16 = LocalizationUtilsKt.lang("ui.info.ability", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent16, (String)"lang(\"ui.info.ability\")");
        mutableComponent3 = TextKt.bold(mutableComponent16);
        n2 = x + 185;
        d = (double)y + 156.5;
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent3, n2, d, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
        MutableComponent mutableComponent17 = LocalizationUtilsKt.lang("ui.moves", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent17, (String)"lang(\"ui.moves\")");
        mutableComponent3 = TextKt.bold(mutableComponent17);
        n2 = x + 185;
        d = (double)y + 173.5;
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent3, n2, d, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
        MutableComponent mutableComponent18 = LocalizationUtilsKt.lang("held_item", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent18, (String)"lang(\"held_item\")");
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent18, (double)x + 270.5, (double)y + 135.5, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
        MutableComponent mutableComponent19 = LocalizationUtilsKt.lang("ui.stats.ivs", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent19, (String)"lang(\"ui.stats.ivs\")");
        mutableComponent3 = TextKt.bold(mutableComponent19);
        n2 = x + 265;
        int n3 = y + 148;
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent3, n2, n3, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
        MutableComponent mutableComponent20 = LocalizationUtilsKt.lang("ui.stats.evs", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent20, (String)"lang(\"ui.stats.evs\")");
        mutableComponent3 = TextKt.bold(mutableComponent20);
        double d4 = (double)x + 280.5;
        int n4 = y + 148;
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent3, d4, n4, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
        MutableComponent mutableComponent21 = LocalizationUtilsKt.lang("ui.stats.hp", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent21, (String)"lang(\"ui.stats.hp\")");
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent21, (double)x + 227.5, (double)y + 155.5, 0.5f, null, 0, 0, false, false, null, null, 8130, null);
        MutableComponent mutableComponent22 = LocalizationUtilsKt.lang("ui.stats.atk", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent22, (String)"lang(\"ui.stats.atk\")");
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent22, (double)x + 227.5, (double)y + 163.5, 0.5f, null, 0, 0, false, false, null, null, 8130, null);
        MutableComponent mutableComponent23 = LocalizationUtilsKt.lang("ui.stats.def", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent23, (String)"lang(\"ui.stats.def\")");
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent23, (double)x + 227.5, (double)y + 171.5, 0.5f, null, 0, 0, false, false, null, null, 8130, null);
        MutableComponent mutableComponent24 = LocalizationUtilsKt.lang("ui.stats.sp_atk", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent24, (String)"lang(\"ui.stats.sp_atk\")");
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent24, (double)x + 227.5, (double)y + 179.5, 0.5f, null, 0, 0, false, false, null, null, 8130, null);
        MutableComponent mutableComponent25 = LocalizationUtilsKt.lang("ui.stats.sp_def", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent25, (String)"lang(\"ui.stats.sp_def\")");
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent25, (double)x + 227.5, (double)y + 187.5, 0.5f, null, 0, 0, false, false, null, null, 8130, null);
        MutableComponent mutableComponent26 = LocalizationUtilsKt.lang("ui.stats.speed", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent26, (String)"lang(\"ui.stats.speed\")");
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent26, (double)x + 227.5, (double)y + 195.5, 0.5f, null, 0, 0, false, false, null, null, 8130, null);
    }

    private static final void init$lambda$0(TradeGUI this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        this$0.playSound(CobblemonSounds.GUI_CLICK);
        this$0.m_7379_();
        Minecraft.m_91087_().m_91152_(null);
    }

    private static final void init$lambda$1(TradeGUI this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        if (this$0.offeredPokemon != null && this$0.opposingOfferedPokemon != null && this$0.protectiveTicks <= 0) {
            this$0.ticksElapsed = 0;
            if (this$0.trade.getAcceptedOppositeOffer()) {
                this$0.readyProgress = 0;
                Pokemon pokemon = this$0.opposingOfferedPokemon;
                Intrinsics.checkNotNull((Object)pokemon);
                UUID uUID = pokemon.getUuid();
                Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"opposingOfferedPokemon!!.uuid");
                CobblemonNetwork.INSTANCE.sendToServer(new ChangeTradeAcceptancePacket(uUID, false));
            } else {
                this$0.readyProgress = 0;
                Pokemon pokemon = this$0.opposingOfferedPokemon;
                Intrinsics.checkNotNull((Object)pokemon);
                UUID uUID = pokemon.getUuid();
                Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"opposingOfferedPokemon!!.uuid");
                CobblemonNetwork.INSTANCE.sendToServer(new ChangeTradeAcceptancePacket(uUID, true));
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private static final void init$lambda$3(TradeGUI this$0, TradeStartedPacket.TradeablePokemon $pokemon, int $partyIndex, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        if (!this$0.trade.getAcceptedOppositeOffer()) {
            Pair pair;
            Pokemon pokemon = this$0.offeredPokemon;
            TradeStartedPacket.TradeablePokemon tradeablePokemon = $pokemon;
            TradeStartedPacket.TradeablePokemon pk = Intrinsics.areEqual((Object)(pokemon != null ? pokemon.getUuid() : null), (Object)(tradeablePokemon != null ? tradeablePokemon.getPokemonId() : null)) ? null : $pokemon;
            CobblemonNetwork cobblemonNetwork = CobblemonNetwork.INSTANCE;
            TradeStartedPacket.TradeablePokemon tradeablePokemon2 = pk;
            if (tradeablePokemon2 != null) {
                void it2;
                TradeStartedPacket.TradeablePokemon tradeablePokemon3 = tradeablePokemon2;
                CobblemonNetwork cobblemonNetwork2 = cobblemonNetwork;
                boolean bl = false;
                pair = TuplesKt.to((Object)it2.getPokemonId(), (Object)new PartyPosition($partyIndex));
                cobblemonNetwork = cobblemonNetwork2;
            } else {
                pair = null;
            }
            Pair pair2 = pair;
            cobblemonNetwork.sendToServer(new UpdateTradeOfferPacket(pair2));
        }
    }

    private static final void init$lambda$5(Button it) {
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\r\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001e\u0010\u001fR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\u0004R\u0014\u0010\b\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0004R\u0014\u0010\t\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0004R\u0014\u0010\n\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\n\u0010\u0004R\u0014\u0010\f\u001a\u00020\u000b8\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\u0004R\u0014\u0010\u000f\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u0004R\u0014\u0010\u0010\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0004R\u0014\u0010\u0011\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0004R\u0014\u0010\u0012\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0004R\u0014\u0010\u0014\u001a\u00020\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0015R\u0014\u0010\u0017\u001a\u00020\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0015R\u0014\u0010\u0018\u001a\u00020\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\u0015R\u0014\u0010\u0019\u001a\u00020\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\u0015R\u0014\u0010\u001a\u001a\u00020\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\u0015R\u0014\u0010\u001b\u001a\u00020\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001b\u0010\u0015R\u0014\u0010\u001c\u001a\u00020\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\u0015R\u0014\u0010\u001d\u001a\u00020\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001d\u0010\u0015\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/client/gui/trade/TradeGUI$Companion;", "", "", "BASE_BACKGROUND_HEIGHT", "I", "BASE_BACKGROUND_WIDTH", "BASE_HEIGHT", "BASE_WIDTH", "PARTY_SLOT_PADDING", "PORTRAIT_SIZE", "READY_PROGRESS_LIMIT", "", "SCALE", "F", "TRADE_READY_HEIGHT", "TRADE_READY_TOP_HEIGHT", "TRADE_READY_WIDTH", "TYPE_SPACER_HEIGHT", "TYPE_SPACER_WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "baseBackgroundResource", "Lnet/minecraft/resources/ResourceLocation;", "baseResource", "opposingTradeReadyResource", "opposingTradeReadyTopResource", "tradeReadyResource", "tradeReadyTopResource", "typeSpacerDoubleResource", "typeSpacerResource", "typeSpacerSingleResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

