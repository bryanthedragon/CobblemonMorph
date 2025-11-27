/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.jvm.internal.SpreadBuilder
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.model.BoatModel
 *  net.minecraft.client.model.ChestBoatModel
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.blockentity.HangingSignRenderer
 *  net.minecraft.client.renderer.blockentity.SignRenderer
 *  net.minecraft.client.renderer.entity.EntityRenderer
 *  net.minecraft.client.renderer.entity.EntityRendererProvider$Context
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.client.renderer.entity.RenderLayerParent
 *  net.minecraft.client.renderer.entity.layers.RenderLayer
 *  net.minecraft.locale.Language
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.server.packs.resources.ResourceManager
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.DoorBlock
 *  net.minecraft.world.level.block.FlowerPotBlock
 *  net.minecraft.world.level.block.TrapDoorBlock
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlockEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonClientImplementation;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonEntities;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berries;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ClientTaskTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.ClientPlayerActionRequests;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.PartyOverlay;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleOverlay;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.particle.BedrockParticleEffectRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.BerryBlockRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.DisplayCaseRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.FossilAnalyzerRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.GildedChestBlockRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.HealingMachineRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block.RestorationTankRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.boat.CobblemonBoatRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.generic.GenericBedrockRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item.CobblemonBuiltinItemRendererRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item.PokemonItemRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.layer.PokemonOnShoulderRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation.BedrockAnimationRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.BerryModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.BlockEntityModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.FossilModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.GenericBedrockEntityModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.MiscModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokeBallModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokeball.PokeBallRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.pokemon.PokemonRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.battle.BattleMusicController;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.starter.ClientPlayerData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientStorageManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade.ClientTrade;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.data.CobblemonDataProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonBoatType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ItemTooltipEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.SpreadBuilder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.locale.Language;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\\\u0010\u000fJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001d\u0010\f\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\f\u0010\rJ\u000f\u0010\u000e\u001a\u00020\u000bH\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\r\u0010\u0010\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0010\u0010\u000fJ\u0015\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0013\u0010\u0014J+\u0010\u0019\u001a\u00020\u000b2\u001c\u0010\u0018\u001a\u0018\u0012\u0004\u0012\u00020\u0004\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00170\u0016\u0018\u00010\u0015\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\r\u0010\u001b\u001a\u00020\u000b\u00a2\u0006\u0004\b\u001b\u0010\u000fJ\r\u0010\u001c\u001a\u00020\u000b\u00a2\u0006\u0004\b\u001c\u0010\u000fJ\u000f\u0010\u001d\u001a\u00020\u000bH\u0002\u00a2\u0006\u0004\b\u001d\u0010\u000fJ\u000f\u0010\u001e\u001a\u00020\u000bH\u0002\u00a2\u0006\u0004\b\u001e\u0010\u000fJ\u000f\u0010\u001f\u001a\u00020\u000bH\u0002\u00a2\u0006\u0004\b\u001f\u0010\u000fJ\r\u0010 \u001a\u00020\u000b\u00a2\u0006\u0004\b \u0010\u000fJ\u0015\u0010#\u001a\u00020\u000b2\u0006\u0010\"\u001a\u00020!\u00a2\u0006\u0004\b#\u0010$R$\u0010&\u001a\u0004\u0018\u00010%8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001b\u00101\u001a\u00020,8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b-\u0010.\u001a\u0004\b/\u00100R\"\u00103\u001a\u0002028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b3\u00104\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\"\u0010:\u001a\u0002098\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b:\u0010;\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\"\u0010\u0012\u001a\u00020\u00118\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u0012\u0010@\u001a\u0004\bA\u0010B\"\u0004\bC\u0010\u0014R\u001b\u0010H\u001a\u00020D8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\bE\u0010.\u001a\u0004\bF\u0010GR\"\u0010J\u001a\u00020I8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bJ\u0010K\u001a\u0004\bL\u0010M\"\u0004\bN\u0010OR\u0017\u0010Q\u001a\u00020P8\u0006\u00a2\u0006\f\n\u0004\bQ\u0010R\u001a\u0004\bS\u0010TR$\u0010V\u001a\u0004\u0018\u00010U8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bV\u0010W\u001a\u0004\bX\u0010Y\"\u0004\bZ\u0010[\u00a8\u0006]"}, d2={"Lcom/cobblemon/mod/common/client/CobblemonClient;", "", "Lnet/minecraft/world/item/ItemStack;", "stack", "", "baseLangKeyForItem", "(Lnet/minecraft/world/item/ItemStack;)Ljava/lang/String;", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "partialDeltaTicks", "", "beforeChatRender", "(Lnet/minecraft/client/gui/GuiGraphics;F)V", "createBoatModelLayers", "()V", "endBattle", "Lcom/cobblemon/mod/common/CobblemonClientImplementation;", "implementation", "initialize", "(Lcom/cobblemon/mod/common/CobblemonClientImplementation;)V", "", "Lnet/minecraft/client/renderer/entity/EntityRenderer;", "Lnet/minecraft/world/entity/player/Player;", "skinMap", "onAddLayer", "(Ljava/util/Map;)V", "onLogin", "onLogout", "registerBlockEntityRenderers", "registerBlockRenderTypes", "registerEntityRenderers", "registerFlywheelRenderers", "Lnet/minecraft/server/packs/resources/ResourceManager;", "resourceManager", "reloadCodedAssets", "(Lnet/minecraft/server/packs/resources/ResourceManager;)V", "Lcom/cobblemon/mod/common/client/battle/ClientBattle;", "battle", "Lcom/cobblemon/mod/common/client/battle/ClientBattle;", "getBattle", "()Lcom/cobblemon/mod/common/client/battle/ClientBattle;", "setBattle", "(Lcom/cobblemon/mod/common/client/battle/ClientBattle;)V", "Lcom/cobblemon/mod/common/client/gui/battle/BattleOverlay;", "battleOverlay$delegate", "Lkotlin/Lazy;", "getBattleOverlay", "()Lcom/cobblemon/mod/common/client/gui/battle/BattleOverlay;", "battleOverlay", "", "checkedStarterScreen", "Z", "getCheckedStarterScreen", "()Z", "setCheckedStarterScreen", "(Z)V", "Lcom/cobblemon/mod/common/client/starter/ClientPlayerData;", "clientPlayerData", "Lcom/cobblemon/mod/common/client/starter/ClientPlayerData;", "getClientPlayerData", "()Lcom/cobblemon/mod/common/client/starter/ClientPlayerData;", "setClientPlayerData", "(Lcom/cobblemon/mod/common/client/starter/ClientPlayerData;)V", "Lcom/cobblemon/mod/common/CobblemonClientImplementation;", "getImplementation", "()Lcom/cobblemon/mod/common/CobblemonClientImplementation;", "setImplementation", "Lcom/cobblemon/mod/common/client/gui/PartyOverlay;", "overlay$delegate", "getOverlay", "()Lcom/cobblemon/mod/common/client/gui/PartyOverlay;", "overlay", "Lcom/cobblemon/mod/common/client/ClientPlayerActionRequests;", "requests", "Lcom/cobblemon/mod/common/client/ClientPlayerActionRequests;", "getRequests", "()Lcom/cobblemon/mod/common/client/ClientPlayerActionRequests;", "setRequests", "(Lcom/cobblemon/mod/common/client/ClientPlayerActionRequests;)V", "Lcom/cobblemon/mod/common/client/storage/ClientStorageManager;", "storage", "Lcom/cobblemon/mod/common/client/storage/ClientStorageManager;", "getStorage", "()Lcom/cobblemon/mod/common/client/storage/ClientStorageManager;", "Lcom/cobblemon/mod/common/client/trade/ClientTrade;", "trade", "Lcom/cobblemon/mod/common/client/trade/ClientTrade;", "getTrade", "()Lcom/cobblemon/mod/common/client/trade/ClientTrade;", "setTrade", "(Lcom/cobblemon/mod/common/client/trade/ClientTrade;)V", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonClient.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonClient.kt\ncom/cobblemon/mod/common/client/CobblemonClient\n+ 2 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,310:1\n37#2,2:311\n13579#3,2:313\n*S KotlinDebug\n*F\n+ 1 CobblemonClient.kt\ncom/cobblemon/mod/common/client/CobblemonClient\n*L\n208#1:311,2\n303#1:313,2\n*E\n"})
public final class CobblemonClient {
    @NotNull
    public static final CobblemonClient INSTANCE = new CobblemonClient();
    public static CobblemonClientImplementation implementation;
    @NotNull
    private static final ClientStorageManager storage;
    @Nullable
    private static ClientTrade trade;
    @Nullable
    private static ClientBattle battle;
    @NotNull
    private static ClientPlayerData clientPlayerData;
    private static boolean checkedStarterScreen;
    @NotNull
    private static ClientPlayerActionRequests requests;
    @NotNull
    private static final Lazy overlay$delegate;
    @NotNull
    private static final Lazy battleOverlay$delegate;

    private CobblemonClient() {
    }

    @NotNull
    public final CobblemonClientImplementation getImplementation() {
        CobblemonClientImplementation cobblemonClientImplementation = implementation;
        if (cobblemonClientImplementation != null) {
            return cobblemonClientImplementation;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"implementation");
        return null;
    }

    public final void setImplementation(@NotNull CobblemonClientImplementation cobblemonClientImplementation) {
        Intrinsics.checkNotNullParameter((Object)cobblemonClientImplementation, (String)"<set-?>");
        implementation = cobblemonClientImplementation;
    }

    @NotNull
    public final ClientStorageManager getStorage() {
        return storage;
    }

    @Nullable
    public final ClientTrade getTrade() {
        return trade;
    }

    public final void setTrade(@Nullable ClientTrade clientTrade) {
        trade = clientTrade;
    }

    @Nullable
    public final ClientBattle getBattle() {
        return battle;
    }

    public final void setBattle(@Nullable ClientBattle clientBattle) {
        battle = clientBattle;
    }

    @NotNull
    public final ClientPlayerData getClientPlayerData() {
        return clientPlayerData;
    }

    public final void setClientPlayerData(@NotNull ClientPlayerData clientPlayerData) {
        Intrinsics.checkNotNullParameter((Object)clientPlayerData, (String)"<set-?>");
        CobblemonClient.clientPlayerData = clientPlayerData;
    }

    public final boolean getCheckedStarterScreen() {
        return checkedStarterScreen;
    }

    public final void setCheckedStarterScreen(boolean bl) {
        checkedStarterScreen = bl;
    }

    @NotNull
    public final ClientPlayerActionRequests getRequests() {
        return requests;
    }

    public final void setRequests(@NotNull ClientPlayerActionRequests clientPlayerActionRequests) {
        Intrinsics.checkNotNullParameter((Object)clientPlayerActionRequests, (String)"<set-?>");
        requests = clientPlayerActionRequests;
    }

    @NotNull
    public final PartyOverlay getOverlay() {
        Lazy lazy = overlay$delegate;
        return (PartyOverlay)((Object)lazy.getValue());
    }

    @NotNull
    public final BattleOverlay getBattleOverlay() {
        Lazy lazy = battleOverlay$delegate;
        return (BattleOverlay)lazy.getValue();
    }

    public final void onLogin() {
        clientPlayerData = new ClientPlayerData(false, false, false, null, 15, null);
        requests = new ClientPlayerActionRequests();
        storage.onLogin();
        CobblemonDataProvider.INSTANCE.setCanReload$common(false);
    }

    public final void onLogout() {
        storage.onLogout();
        battle = null;
        this.getBattleOverlay().onLogout();
        ClientTaskTracker.INSTANCE.clear();
        checkedStarterScreen = false;
        CobblemonDataProvider.INSTANCE.setCanReload$common(true);
    }

    public final void initialize(@NotNull CobblemonClientImplementation implementation) {
        Intrinsics.checkNotNullParameter((Object)implementation, (String)"implementation");
        Cobblemon.INSTANCE.getLOGGER().info("Initializing Cobblemon client");
        this.setImplementation(implementation);
        Observable.DefaultImpls.subscribe$default(PlatformEvents.CLIENT_PLAYER_LOGIN, null, initialize.1.INSTANCE, 1, null);
        Observable.DefaultImpls.subscribe$default(PlatformEvents.CLIENT_PLAYER_LOGOUT, null, initialize.2.INSTANCE, 1, null);
        this.registerBlockEntityRenderers();
        this.registerBlockRenderTypes();
        this.registerFlywheelRenderers();
        this.registerEntityRenderers();
        Observable.DefaultImpls.subscribe$default(Berries.INSTANCE.getObservable(), null, initialize.3.INSTANCE, 1, null);
        Cobblemon.INSTANCE.getLOGGER().info("Registering custom BuiltinItemRenderers");
        CobblemonBuiltinItemRendererRegistry.INSTANCE.register(CobblemonItems.POKEMON_MODEL, new PokemonItemRenderer());
        Observable.DefaultImpls.subscribe$default(PlatformEvents.CLIENT_ITEM_TOOLTIP, null, (Function1)new Function1<ItemTooltipEvent, Unit>(this){
            final /* synthetic */ CobblemonClient this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            public final void invoke(@NotNull ItemTooltipEvent event) {
                Intrinsics.checkNotNullParameter((Object)event, (String)"event");
                ItemStack stack = event.getStack();
                List<Component> lines = event.getLines();
                if (stack.m_41720_().m_204114_().m_203543_().isPresent() && Intrinsics.areEqual((Object)((ResourceKey)stack.m_41720_().m_204114_().m_203543_().get()).m_135782_().m_135827_(), (Object)"cobblemon")) {
                    int offset;
                    CompoundTag compoundTag = stack.m_41783_();
                    boolean bl = compoundTag != null ? compoundTag.m_128471_("HideTooltip") : false;
                    if (bl) {
                        return;
                    }
                    Language language = Language.m_128107_();
                    String key = CobblemonClient.access$baseLangKeyForItem(this.this$0, stack);
                    int n = offset = lines.size() > 1 ? 1 : 0;
                    if (language.m_6722_(key)) {
                        int n2 = lines.size() - offset;
                        MutableComponent mutableComponent = MiscUtils.asTranslated(key);
                        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"key.asTranslated()");
                        lines.add(n2, (Component)TextKt.gray(mutableComponent));
                    }
                    int i = 1;
                    String listKey = key + "_" + i;
                    while (language.m_6722_(listKey)) {
                        int n3 = lines.size() - offset;
                        MutableComponent mutableComponent = MiscUtils.asTranslated(listKey);
                        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"listKey.asTranslated()");
                        lines.add(n3, (Component)TextKt.gray(mutableComponent));
                        listKey = key + "_" + ++i;
                    }
                }
            }
        }, 1, null);
    }

    public final void registerFlywheelRenderers() {
    }

    private final void registerBlockRenderTypes() {
        CobblemonClientImplementation cobblemonClientImplementation = this.getImplementation();
        RenderType renderType = RenderType.m_110457_();
        Intrinsics.checkNotNullExpressionValue((Object)renderType, (String)"getCutoutMipped()");
        SpreadBuilder spreadBuilder = new SpreadBuilder[]{CobblemonBlocks.APRICORN_LEAVES};
        cobblemonClientImplementation.registerBlockRenderType(renderType, (Block[])spreadBuilder);
        CobblemonClientImplementation cobblemonClientImplementation2 = this.getImplementation();
        RenderType renderType2 = RenderType.m_110463_();
        Intrinsics.checkNotNullExpressionValue((Object)renderType2, (String)"getCutout()");
        spreadBuilder = new SpreadBuilder(56);
        spreadBuilder.add((Object)CobblemonBlocks.GILDED_CHEST);
        spreadBuilder.add((Object)CobblemonBlocks.FOSSIL_ANALYZER);
        DoorBlock doorBlock = CobblemonBlocks.APRICORN_DOOR;
        Intrinsics.checkNotNullExpressionValue((Object)doorBlock, (String)"APRICORN_DOOR");
        spreadBuilder.add((Object)doorBlock);
        TrapDoorBlock trapDoorBlock = CobblemonBlocks.APRICORN_TRAPDOOR;
        Intrinsics.checkNotNullExpressionValue((Object)trapDoorBlock, (String)"APRICORN_TRAPDOOR");
        spreadBuilder.add((Object)trapDoorBlock);
        spreadBuilder.add((Object)CobblemonBlocks.APRICORN_SIGN);
        spreadBuilder.add((Object)CobblemonBlocks.APRICORN_WALL_SIGN);
        spreadBuilder.add((Object)CobblemonBlocks.APRICORN_HANGING_SIGN);
        spreadBuilder.add((Object)CobblemonBlocks.APRICORN_WALL_HANGING_SIGN);
        spreadBuilder.add((Object)CobblemonBlocks.BLACK_APRICORN_SAPLING);
        spreadBuilder.add((Object)CobblemonBlocks.BLUE_APRICORN_SAPLING);
        spreadBuilder.add((Object)CobblemonBlocks.GREEN_APRICORN_SAPLING);
        spreadBuilder.add((Object)CobblemonBlocks.PINK_APRICORN_SAPLING);
        spreadBuilder.add((Object)CobblemonBlocks.RED_APRICORN_SAPLING);
        spreadBuilder.add((Object)CobblemonBlocks.WHITE_APRICORN_SAPLING);
        spreadBuilder.add((Object)CobblemonBlocks.YELLOW_APRICORN_SAPLING);
        spreadBuilder.add((Object)CobblemonBlocks.BLACK_APRICORN);
        spreadBuilder.add((Object)CobblemonBlocks.BLUE_APRICORN);
        spreadBuilder.add((Object)CobblemonBlocks.GREEN_APRICORN);
        spreadBuilder.add((Object)CobblemonBlocks.PINK_APRICORN);
        spreadBuilder.add((Object)CobblemonBlocks.RED_APRICORN);
        spreadBuilder.add((Object)CobblemonBlocks.WHITE_APRICORN);
        spreadBuilder.add((Object)CobblemonBlocks.YELLOW_APRICORN);
        spreadBuilder.add((Object)CobblemonBlocks.HEALING_MACHINE);
        spreadBuilder.add((Object)CobblemonBlocks.MEDICINAL_LEEK);
        spreadBuilder.add((Object)CobblemonBlocks.HEALING_MACHINE);
        spreadBuilder.add((Object)CobblemonBlocks.INSTANCE.getRED_MINT());
        spreadBuilder.add((Object)CobblemonBlocks.BLUE_MINT);
        spreadBuilder.add((Object)CobblemonBlocks.CYAN_MINT);
        spreadBuilder.add((Object)CobblemonBlocks.PINK_MINT);
        spreadBuilder.add((Object)CobblemonBlocks.GREEN_MINT);
        spreadBuilder.add((Object)CobblemonBlocks.WHITE_MINT);
        spreadBuilder.add((Object)CobblemonBlocks.PASTURE);
        spreadBuilder.add((Object)CobblemonBlocks.ENERGY_ROOT);
        spreadBuilder.add((Object)CobblemonBlocks.BIG_ROOT);
        spreadBuilder.add((Object)CobblemonBlocks.REVIVAL_HERB);
        spreadBuilder.add((Object)CobblemonBlocks.VIVICHOKE_SEEDS);
        spreadBuilder.add((Object)CobblemonBlocks.PEP_UP_FLOWER);
        FlowerPotBlock flowerPotBlock = CobblemonBlocks.POTTED_PEP_UP_FLOWER;
        Intrinsics.checkNotNullExpressionValue((Object)flowerPotBlock, (String)"POTTED_PEP_UP_FLOWER");
        spreadBuilder.add((Object)flowerPotBlock);
        spreadBuilder.add((Object)CobblemonBlocks.REVIVAL_HERB);
        Collection<BerryBlock> $this$toTypedArray$iv = CobblemonBlocks.INSTANCE.berries().values();
        boolean $i$f$toTypedArray = false;
        Collection<BerryBlock> thisCollection$iv = $this$toTypedArray$iv;
        spreadBuilder.addSpread((Object)thisCollection$iv.toArray(new BerryBlock[0]));
        FlowerPotBlock flowerPotBlock2 = CobblemonBlocks.POTTED_PEP_UP_FLOWER;
        Intrinsics.checkNotNullExpressionValue((Object)flowerPotBlock2, (String)"POTTED_PEP_UP_FLOWER");
        spreadBuilder.add((Object)flowerPotBlock2);
        spreadBuilder.add((Object)CobblemonBlocks.RESTORATION_TANK);
        spreadBuilder.add((Object)CobblemonBlocks.SMALL_BUDDING_TUMBLESTONE);
        spreadBuilder.add((Object)CobblemonBlocks.MEDIUM_BUDDING_TUMBLESTONE);
        spreadBuilder.add((Object)CobblemonBlocks.LARGE_BUDDING_TUMBLESTONE);
        spreadBuilder.add((Object)CobblemonBlocks.TUMBLESTONE_CLUSTER);
        spreadBuilder.add((Object)CobblemonBlocks.SMALL_BUDDING_BLACK_TUMBLESTONE);
        spreadBuilder.add((Object)CobblemonBlocks.MEDIUM_BUDDING_BLACK_TUMBLESTONE);
        spreadBuilder.add((Object)CobblemonBlocks.LARGE_BUDDING_BLACK_TUMBLESTONE);
        spreadBuilder.add((Object)CobblemonBlocks.BLACK_TUMBLESTONE_CLUSTER);
        spreadBuilder.add((Object)CobblemonBlocks.SMALL_BUDDING_SKY_TUMBLESTONE);
        spreadBuilder.add((Object)CobblemonBlocks.MEDIUM_BUDDING_SKY_TUMBLESTONE);
        spreadBuilder.add((Object)CobblemonBlocks.LARGE_BUDDING_SKY_TUMBLESTONE);
        spreadBuilder.add((Object)CobblemonBlocks.SKY_TUMBLESTONE_CLUSTER);
        spreadBuilder.add((Object)CobblemonBlocks.GIMMIGHOUL_CHEST);
        spreadBuilder.add((Object)CobblemonBlocks.DISPLAY_CASE);
        cobblemonClientImplementation2.registerBlockRenderType(renderType2, (Block[])spreadBuilder.toArray((Object[])new Block[spreadBuilder.size()]));
        this.createBoatModelLayers();
    }

    public final void beforeChatRender(@NotNull GuiGraphics context, float partialDeltaTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (battle == null) {
            this.getOverlay().m_280421_(context, partialDeltaTicks);
        } else {
            this.getBattleOverlay().m_280421_(context, partialDeltaTicks);
        }
    }

    public final void onAddLayer(@Nullable Map<String, ? extends EntityRenderer<? extends Player>> skinMap) {
        block0: {
            Map<String, ? extends EntityRenderer<? extends Player>> map = skinMap;
            EntityRenderer<? extends Player> entityRenderer = map != null ? map.get("default") : null;
            Intrinsics.checkNotNull(entityRenderer, (String)"null cannot be cast to non-null type net.minecraft.client.render.entity.LivingEntityRenderer<net.minecraft.entity.player.PlayerEntity, net.minecraft.client.render.entity.model.PlayerEntityModel<net.minecraft.entity.player.PlayerEntity>>");
            LivingEntityRenderer renderer = (LivingEntityRenderer)entityRenderer;
            renderer.m_115326_((RenderLayer)new PokemonOnShoulderRenderer((RenderLayerParent)renderer));
            LivingEntityRenderer livingEntityRenderer = renderer = (LivingEntityRenderer)skinMap.get("slim");
            if (livingEntityRenderer == null) break block0;
            livingEntityRenderer.m_115326_((RenderLayer)new PokemonOnShoulderRenderer((RenderLayerParent)renderer));
        }
    }

    private final void registerBlockEntityRenderers() {
        this.getImplementation().registerBlockEntityRenderer(CobblemonBlockEntities.HEALING_MACHINE, HealingMachineRenderer::new);
        CobblemonClientImplementation cobblemonClientImplementation = this.getImplementation();
        BlockEntityType<BerryBlockEntity> blockEntityType = CobblemonBlockEntities.BERRY;
        Intrinsics.checkNotNullExpressionValue(blockEntityType, (String)"BERRY");
        cobblemonClientImplementation.registerBlockEntityRenderer(blockEntityType, BerryBlockRenderer::new);
        this.getImplementation().registerBlockEntityRenderer(CobblemonBlockEntities.SIGN, SignRenderer::new);
        this.getImplementation().registerBlockEntityRenderer(CobblemonBlockEntities.HANGING_SIGN, HangingSignRenderer::new);
        this.getImplementation().registerBlockEntityRenderer(CobblemonBlockEntities.FOSSIL_ANALYZER, FossilAnalyzerRenderer::new);
        this.getImplementation().registerBlockEntityRenderer(CobblemonBlockEntities.RESTORATION_TANK, RestorationTankRenderer::new);
        this.getImplementation().registerBlockEntityRenderer(CobblemonBlockEntities.GILDED_CHEST, GildedChestBlockRenderer::new);
        this.getImplementation().registerBlockEntityRenderer(CobblemonBlockEntities.DISPLAY_CASE, DisplayCaseRenderer::new);
    }

    private final void registerEntityRenderers() {
        Cobblemon.INSTANCE.getLOGGER().info("Registering Pok\u00e9mon renderer");
        this.getImplementation().registerEntityRenderer(CobblemonEntities.POKEMON, PokemonRenderer::new);
        Cobblemon.INSTANCE.getLOGGER().info("Registering Pok\u00e9Ball renderer");
        this.getImplementation().registerEntityRenderer(CobblemonEntities.EMPTY_POKEBALL, PokeBallRenderer::new);
        Cobblemon.INSTANCE.getLOGGER().info("Registering Boat renderer");
        this.getImplementation().registerEntityRenderer(CobblemonEntities.BOAT, CobblemonClient::registerEntityRenderers$lambda$0);
        Cobblemon.INSTANCE.getLOGGER().info("Registering Boat with Chest renderer");
        this.getImplementation().registerEntityRenderer(CobblemonEntities.CHEST_BOAT, CobblemonClient::registerEntityRenderers$lambda$1);
        Cobblemon.INSTANCE.getLOGGER().info("Registering Generic Bedrock Entity renderer");
        this.getImplementation().registerEntityRenderer(CobblemonEntities.GENERIC_BEDROCK_ENTITY, GenericBedrockRenderer::new);
    }

    public final void reloadCodedAssets(@NotNull ResourceManager resourceManager) {
        Intrinsics.checkNotNullParameter((Object)resourceManager, (String)"resourceManager");
        Cobblemon.INSTANCE.getLOGGER().info("Loading assets...");
        BedrockParticleEffectRepository.INSTANCE.loadEffects(resourceManager);
        BedrockAnimationRepository.INSTANCE.loadAnimations(resourceManager, CollectionsKt.plus((Collection)CollectionsKt.plus((Collection)CollectionsKt.plus((Collection)CollectionsKt.plus((Collection)PokemonModelRepository.INSTANCE.getAnimationDirectories(), (Iterable)PokeBallModelRepository.INSTANCE.getAnimationDirectories()), (Iterable)FossilModelRepository.INSTANCE.getAnimationDirectories()), (Iterable)BlockEntityModelRepository.INSTANCE.getAnimationDirectories()), (Iterable)GenericBedrockEntityModelRepository.INSTANCE.getAnimationDirectories()));
        PokemonModelRepository.INSTANCE.reload(resourceManager);
        PokeBallModelRepository.INSTANCE.reload(resourceManager);
        BerryModelRepository.INSTANCE.reload(resourceManager);
        FossilModelRepository.INSTANCE.reload(resourceManager);
        BlockEntityModelRepository.INSTANCE.reload(resourceManager);
        GenericBedrockEntityModelRepository.INSTANCE.reload(resourceManager);
        MiscModelRepository.INSTANCE.reload(resourceManager);
        Cobblemon.INSTANCE.getLOGGER().info("Loaded assets");
    }

    public final void endBattle() {
        battle = null;
        this.getBattleOverlay().setLastKnownBattle(null);
        BattleMusicController.INSTANCE.endMusic();
    }

    private final String baseLangKeyForItem(ItemStack stack) {
        if (stack.m_41720_() instanceof PokeBallItem) {
            Item item = stack.m_41720_();
            Intrinsics.checkNotNull((Object)item, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem");
            PokeBallItem asPokeball = (PokeBallItem)item;
            return "item." + asPokeball.getPokeBall().getName().m_135827_() + "." + asPokeball.getPokeBall().getName().m_135815_() + ".tooltip";
        }
        return stack.m_41778_() + ".tooltip";
    }

    private final void createBoatModelLayers() {
        CobblemonBoatType[] $this$forEach$iv = CobblemonBoatType.values();
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            CobblemonBoatType element$iv;
            CobblemonBoatType type = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            this.getImplementation().registerLayer(CobblemonBoatRenderer.Companion.createBoatModelLayer$common(type, false), BoatModel::m_246613_);
            this.getImplementation().registerLayer(CobblemonBoatRenderer.Companion.createBoatModelLayer$common(type, true), ChestBoatModel::m_247175_);
        }
    }

    private static final EntityRenderer registerEntityRenderers$lambda$0(EntityRendererProvider.Context ctx) {
        Intrinsics.checkNotNullExpressionValue((Object)ctx, (String)"ctx");
        return new CobblemonBoatRenderer(ctx, false);
    }

    private static final EntityRenderer registerEntityRenderers$lambda$1(EntityRendererProvider.Context ctx) {
        Intrinsics.checkNotNullExpressionValue((Object)ctx, (String)"ctx");
        return new CobblemonBoatRenderer(ctx, true);
    }

    public static final /* synthetic */ String access$baseLangKeyForItem(CobblemonClient $this, ItemStack stack) {
        return $this.baseLangKeyForItem(stack);
    }

    static {
        storage = new ClientStorageManager();
        clientPlayerData = new ClientPlayerData(false, false, false, null, 15, null);
        requests = new ClientPlayerActionRequests();
        overlay$delegate = LazyKt.lazy((Function0)overlay.2.INSTANCE);
        battleOverlay$delegate = LazyKt.lazy((Function0)battleOverlay.2.INSTANCE);
    }
}

