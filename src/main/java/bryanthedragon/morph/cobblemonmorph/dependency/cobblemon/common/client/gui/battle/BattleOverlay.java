/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.Lighting
 *  com.mojang.blaze3d.systems.RenderSystem
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.mojang.math.Axis
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Triple
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.ChatScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.LightTexture
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.client.renderer.texture.OverlayTexture
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.Mth
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.Schedulable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.ScheduledTask;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingTracker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ActiveClientBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBallDisplay;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.widgets.BattleMessagePane;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CurrentKeyAccessorKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.PartySendBinding;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokeball.PokeBallModel;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Pose;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokeBallModelRepository;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.wavefunction.WaveFunctionKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 X2\u00020\u00012\u00020\u0002:\u0001XB\u0007\u00a2\u0006\u0004\bW\u00103J\u00c5\u0001\u0010$\u001a\u00020#2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\r2\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\u00142\b\u0010\u0017\u001a\u0004\u0018\u00010\u00162\u000e\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u0019\u0018\u00010\u00182\u001a\u0010\u001c\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u001b2\u0006\u0010\u001d\u001a\u00020\u00052\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\u001e2\u0006\u0010 \u001a\u00020\r2\u0006\u0010!\u001a\u00020\u00052\u0006\u0010\"\u001a\u00020\t\u00a2\u0006\u0004\b$\u0010%J;\u0010)\u001a\u00020#2\u0006\u0010\u001a\u001a\u00020\u001e2\u0006\u0010'\u001a\u00020&2\b\b\u0002\u0010(\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\tH\u0002\u00a2\u0006\u0004\b)\u0010*J5\u00100\u001a\u00020#2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010+\u001a\u00020\u00052\u0006\u0010-\u001a\u00020,2\u0006\u0010.\u001a\u00020\t2\u0006\u0010/\u001a\u00020\r\u00a2\u0006\u0004\b0\u00101J\r\u00102\u001a\u00020#\u00a2\u0006\u0004\b2\u00103J\u001f\u00104\u001a\u00020#2\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010+\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b4\u00105R$\u00107\u001a\u0004\u0018\u0001068\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b7\u00108\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\"\u0010>\u001a\u00020=8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b>\u0010?\u001a\u0004\b@\u0010A\"\u0004\bB\u0010CR\"\u0010\u001d\u001a\u00020D8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001d\u0010E\u001a\u0004\bF\u0010G\"\u0004\bH\u0010IR\u0011\u0010K\u001a\u00020D8F\u00a2\u0006\u0006\u001a\u0004\bJ\u0010GR\"\u0010L\u001a\u00020\u00058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bL\u0010M\u001a\u0004\bN\u0010O\"\u0004\bP\u0010QR\u001a\u0010S\u001a\u00020R8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bS\u0010T\u001a\u0004\bU\u0010V\u00a8\u0006Y"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/BattleOverlay;", "Lnet/minecraft/client/gui/Gui;", "Lcom/cobblemon/mod/common/api/scheduling/Schedulable;", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "x", "y", "partialTicks", "", "reversed", "Lcom/cobblemon/mod/common/pokemon/Species;", "species", "", "level", "", "", "aspects", "Lnet/minecraft/network/chat/MutableComponent;", "displayName", "Lcom/cobblemon/mod/common/pokemon/Gender;", "gender", "Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;", "status", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "state", "Lkotlin/Triple;", "colour", "opacity", "Lcom/cobblemon/mod/common/client/battle/ClientBallDisplay;", "ballState", "maxHealth", "health", "isFlatHealth", "", "drawBattleTile", "(Lnet/minecraft/client/gui/GuiGraphics;FFFZLcom/cobblemon/mod/common/pokemon/Species;ILjava/util/Set;Lnet/minecraft/network/chat/MutableComponent;Lcom/cobblemon/mod/common/pokemon/Gender;Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;Lkotlin/Triple;FLcom/cobblemon/mod/common/client/battle/ClientBallDisplay;IFZ)V", "Lcom/mojang/blaze3d/vertex/PoseStack;", "matrixStack", "scale", "drawPokeBall", "(Lcom/cobblemon/mod/common/client/battle/ClientBallDisplay;Lcom/mojang/blaze3d/vertex/PoseStack;FFZ)V", "tickDelta", "Lcom/cobblemon/mod/common/client/battle/ActiveClientBattlePokemon;", "activeBattlePokemon", "left", "rank", "drawTile", "(Lnet/minecraft/client/gui/GuiGraphics;FLcom/cobblemon/mod/common/client/battle/ActiveClientBattlePokemon;ZI)V", "onLogout", "()V", "render", "(Lnet/minecraft/client/gui/GuiGraphics;F)V", "Ljava/util/UUID;", "lastKnownBattle", "Ljava/util/UUID;", "getLastKnownBattle", "()Ljava/util/UUID;", "setLastKnownBattle", "(Ljava/util/UUID;)V", "Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleMessagePane;", "messagePane", "Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleMessagePane;", "getMessagePane", "()Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleMessagePane;", "setMessagePane", "(Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleMessagePane;)V", "", "D", "getOpacity", "()D", "setOpacity", "(D)V", "getOpacityRatio", "opacityRatio", "passedSeconds", "F", "getPassedSeconds", "()F", "setPassedSeconds", "(F)V", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "schedulingTracker", "Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "getSchedulingTracker", "()Lcom/cobblemon/mod/common/api/scheduling/SchedulingTracker;", "<init>", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nBattleOverlay.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleOverlay.kt\ncom/cobblemon/mod/common/client/gui/battle/BattleOverlay\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,439:1\n1747#2,3:440\n1864#2,3:443\n1864#2,3:446\n1#3:449\n*S KotlinDebug\n*F\n+ 1 BattleOverlay.kt\ncom/cobblemon/mod/common/client/gui/battle/BattleOverlay\n*L\n106#1:440,3\n109#1:443,3\n110#1:446,3\n*E\n"})
public final class BattleOverlay
extends Gui
implements Schedulable {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private double opacity = 0.5;
    private float passedSeconds;
    @Nullable
    private UUID lastKnownBattle;
    public BattleMessagePane messagePane;
    @NotNull
    private final SchedulingTracker schedulingTracker = new SchedulingTracker();
    public static final double MAX_OPACITY = 1.0;
    public static final double MIN_OPACITY = 0.5;
    public static final double OPACITY_CHANGE_PER_SECOND = 0.1;
    public static final int HORIZONTAL_INSET = 12;
    public static final int VERTICAL_INSET = 10;
    public static final int HORIZONTAL_SPACING = 15;
    public static final int VERTICAL_SPACING = 40;
    public static final int INFO_OFFSET_X = 7;
    public static final int TILE_WIDTH = 131;
    public static final int TILE_HEIGHT = 40;
    public static final int PORTRAIT_DIAMETER = 28;
    public static final int PORTRAIT_OFFSET_X = 5;
    public static final int PORTRAIT_OFFSET_Y = 8;
    @NotNull
    private static final Function1<Float, Float> PROMPT_TEXT_OPACITY_CURVE = WaveFunctionKt.sineFunction$default(0.5f, 4.0f, 0.0f, 0.5f, 4, null);
    @NotNull
    private static final ResourceLocation battleInfoBase = MiscUtils.cobblemonResource("textures/gui/battle/battle_info_base.png");
    @NotNull
    private static final ResourceLocation battleInfoBaseFlipped = MiscUtils.cobblemonResource("textures/gui/battle/battle_info_base_flipped.png");
    @NotNull
    private static final ResourceLocation battleInfoRole = MiscUtils.cobblemonResource("textures/gui/battle/battle_info_role.png");
    @NotNull
    private static final ResourceLocation battleInfoRoleFlipped = MiscUtils.cobblemonResource("textures/gui/battle/battle_info_role_flipped.png");
    @NotNull
    private static final ResourceLocation battleInfoUnderlay = MiscUtils.cobblemonResource("textures/gui/battle/battle_info_underlay.png");

    public BattleOverlay() {
        super(Minecraft.m_91087_(), Minecraft.m_91087_().m_91291_());
    }

    public final double getOpacity() {
        return this.opacity;
    }

    public final void setOpacity(double d) {
        this.opacity = d;
    }

    public final double getOpacityRatio() {
        return (this.opacity - 0.5) / 0.5;
    }

    public final float getPassedSeconds() {
        return this.passedSeconds;
    }

    public final void setPassedSeconds(float f) {
        this.passedSeconds = f;
    }

    @Nullable
    public final UUID getLastKnownBattle() {
        return this.lastKnownBattle;
    }

    public final void setLastKnownBattle(@Nullable UUID uUID) {
        this.lastKnownBattle = uUID;
    }

    @NotNull
    public final BattleMessagePane getMessagePane() {
        BattleMessagePane battleMessagePane = this.messagePane;
        if (battleMessagePane != null) {
            return battleMessagePane;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"messagePane");
        return null;
    }

    public final void setMessagePane(@NotNull BattleMessagePane battleMessagePane) {
        Intrinsics.checkNotNullParameter((Object)((Object)battleMessagePane), (String)"<set-?>");
        this.messagePane = battleMessagePane;
    }

    @Override
    @NotNull
    public SchedulingTracker getSchedulingTracker() {
        return this.schedulingTracker;
    }

    public void m_280421_(@NotNull GuiGraphics context, float tickDelta) {
        Screen currentScreen;
        ActiveClientBattlePokemon activeClientBattlePokemon;
        int index;
        int n;
        boolean bl;
        ClientBattle battle2;
        block13: {
            Intrinsics.checkNotNullParameter((Object)context, (String)"context");
            this.getSchedulingTracker().update(tickDelta / 20.0f);
            this.passedSeconds += tickDelta / (float)20;
            if (this.passedSeconds > 100.0f) {
                this.passedSeconds -= (float)100;
            }
            ClientBattle clientBattle = CobblemonClient.INSTANCE.getBattle();
            if (clientBattle == null) {
                return;
            }
            battle2 = clientBattle;
            this.opacity = battle2.getMinimised() ? Double.max(this.opacity - (double)tickDelta * 0.1, 0.5) : Double.min(this.opacity + (double)tickDelta * 0.1, 1.0);
            LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
            UUID uUID = localPlayer != null ? localPlayer.m_20148_() : null;
            if (uUID == null) {
                return;
            }
            UUID playerUUID = uUID;
            Iterable $this$any$iv = battle2.getSide1().getActors();
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    ClientBattleActor it = (ClientBattleActor)element$iv;
                    boolean bl2 = false;
                    if (!Intrinsics.areEqual((Object)it.getUuid(), (Object)playerUUID)) continue;
                    bl = true;
                    break block13;
                }
                bl = false;
            }
        }
        ClientBattleSide side1 = bl ? battle2.getSide1() : battle2.getSide2();
        ClientBattleSide side2 = Intrinsics.areEqual((Object)side1, (Object)battle2.getSide1()) ? battle2.getSide2() : battle2.getSide1();
        Iterable<ActiveClientBattlePokemon> $this$forEachIndexed$iv = side1.getActiveClientBattlePokemon();
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        for (ActiveClientBattlePokemon item$iv : $this$forEachIndexed$iv) {
            if ((n = index$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            ActiveClientBattlePokemon activeClientBattlePokemon2 = item$iv;
            index = n;
            boolean bl3 = false;
            this.drawTile(context, tickDelta, activeClientBattlePokemon, true, index);
        }
        $this$forEachIndexed$iv = side2.getActiveClientBattlePokemon();
        $i$f$forEachIndexed = false;
        index$iv = 0;
        for (ActiveClientBattlePokemon item$iv : $this$forEachIndexed$iv) {
            if ((n = index$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            activeClientBattlePokemon = item$iv;
            index = n;
            boolean bl4 = false;
            this.drawTile(context, tickDelta, activeClientBattlePokemon, false, index);
        }
        if (!(Minecraft.m_91087_().f_91080_ instanceof BattleGUI) && battle2.getMustChoose()) {
            float textOpacity = ((Number)PROMPT_TEXT_OPACITY_CURVE.invoke((Object)Float.valueOf(this.passedSeconds))).floatValue();
            Object[] objectArray = new Object[1];
            Intrinsics.checkNotNullExpressionValue((Object)CurrentKeyAccessorKt.boundKey(PartySendBinding.INSTANCE).m_84875_(), (String)"PartySendBinding.boundKey().localizedText");
            MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("ui.actions_label", objectArray);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"ui.actions_l\u2026boundKey().localizedText)");
            RenderHelperKt.drawScaledText$default(context, null, mutableComponent, Minecraft.m_91087_().m_91268_().m_85445_() / 2, Minecraft.m_91087_().m_91268_().m_85446_() / 5, 0.0f, Float.valueOf(textOpacity), 0, 0, true, false, null, null, 7586, null);
        }
        if ((currentScreen = Minecraft.m_91087_().f_91080_) == null || currentScreen instanceof ChatScreen) {
            if (!Intrinsics.areEqual((Object)this.lastKnownBattle, (Object)battle2.getBattleId())) {
                this.lastKnownBattle = battle2.getBattleId();
                ClientBattle clientBattle = CobblemonClient.INSTANCE.getBattle();
                Intrinsics.checkNotNull((Object)clientBattle);
                this.setMessagePane(new BattleMessagePane(clientBattle.getMessages()));
            }
            this.getMessagePane().setOpacity(0.3f);
            this.getMessagePane().m_88315_(context, 0, 0, 0.0f);
        }
    }

    public final void drawTile(@NotNull GuiGraphics context, float tickDelta, @NotNull ActiveClientBattlePokemon activeBattlePokemon, boolean left, int rank) {
        Object v2;
        float b;
        float g;
        float r;
        int y;
        float x;
        ClientBattlePokemon battlePokemon;
        block3: {
            Intrinsics.checkNotNullParameter((Object)context, (String)"context");
            Intrinsics.checkNotNullParameter((Object)activeBattlePokemon, (String)"activeBattlePokemon");
            Minecraft mc = Minecraft.m_91087_();
            ClientBattlePokemon clientBattlePokemon = activeBattlePokemon.getBattlePokemon();
            if (clientBattlePokemon == null) {
                return;
            }
            battlePokemon = clientBattlePokemon;
            x = (float)12 + (float)rank * 15.0f;
            y = 10 + rank * 40;
            if (!left) {
                x = (float)mc.m_91268_().m_85445_() - x - (float)131;
            }
            float invisibleX = left ? -132.0f : (float)mc.m_91268_().m_85445_();
            activeBattlePokemon.setInvisibleX(invisibleX);
            activeBattlePokemon.setXDisplacement(x);
            activeBattlePokemon.animate(tickDelta);
            x = activeBattlePokemon.getXDisplacement();
            int hue = activeBattlePokemon.getHue();
            r = (float)(hue >> 16 & 0xFF) / 255.0f;
            g = (float)(hue >> 8 & 0xFF) / 255.0f;
            b = (float)(hue & 0xFF) / 255.0f;
            Iterable iterable = activeBattlePokemon.getActor().getPokemon();
            for (Object t : iterable) {
                Pokemon it = (Pokemon)t;
                boolean bl = false;
                ClientBattlePokemon clientBattlePokemon2 = activeBattlePokemon.getBattlePokemon();
                if (!Intrinsics.areEqual((Object)it.getUuid(), (Object)(clientBattlePokemon2 != null ? clientBattlePokemon2.getUuid() : null))) continue;
                v2 = t;
                break block3;
            }
            v2 = null;
        }
        Pokemon truePokemon = v2;
        this.drawBattleTile(context, x, y, tickDelta, !left, battlePokemon.getSpecies(), battlePokemon.getLevel(), battlePokemon.getAspects(), battlePokemon.getDisplayName(), battlePokemon.getGender(), battlePokemon.getStatus(), battlePokemon.getState(), (Triple<Float, Float, Float>)new Triple((Object)Float.valueOf(r), (Object)Float.valueOf(g), (Object)Float.valueOf(b)), (float)this.opacity, activeBattlePokemon.getBallCapturing(), (int)battlePokemon.getMaxHp(), battlePokemon.getHpValue(), battlePokemon.isHpFlat());
    }

    public final void drawBattleTile(@NotNull GuiGraphics context, float x, float y, float partialTicks, boolean reversed, @NotNull Species species, int level, @NotNull Set<String> aspects, @NotNull MutableComponent displayName, @NotNull Gender gender, @Nullable PersistentStatus status, @Nullable PoseableEntityState<PokemonEntity> state, @Nullable Triple<Float, Float, Float> colour, float opacity, @Nullable ClientBallDisplay ballState, int maxHealth, float health, boolean isFlatHealth) {
        float f;
        float f2;
        ResourceLocation resourceLocation;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Intrinsics.checkNotNullParameter((Object)species, (String)"species");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
        Intrinsics.checkNotNullParameter((Object)((Object)gender), (String)"gender");
        float portraitStartX = x + (float)(!reversed ? 5 : 98);
        PoseStack matrices = context.m_280168_();
        ResourceLocation resourceLocation2 = battleInfoUnderlay;
        float f3 = y + (float)8;
        Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
        GuiUtilsKt.blitk$default(matrices, resourceLocation2, Float.valueOf(portraitStartX), Float.valueOf(f3), 28, 28, null, null, null, null, null, null, null, null, Float.valueOf(opacity), false, 0.0f, 114624, null);
        context.m_280588_((int)portraitStartX, (int)(y + (float)8), (int)(portraitStartX + (float)28), (int)(y + (float)28 + (float)8));
        PoseStack matrixStack = new PoseStack();
        matrixStack.m_85837_((double)portraitStartX + 14.0, (double)y + (double)8 - 5.0, 0.0);
        matrixStack.m_85836_();
        if (ballState != null && ballState.getStateEmitter().get() == EmptyPokeBallEntity.CaptureState.SHAKE) {
            BattleOverlay.drawPokeBall$default(this, ballState, matrixStack, 0.0f, partialTicks, false, 20, null);
        } else {
            matrixStack.m_85836_();
            ClientBallDisplay clientBallDisplay = ballState;
            GuiUtilsKt.drawPortraitPokemon(species, aspects, matrixStack, 18.0f * (clientBallDisplay != null ? clientBallDisplay.getScale() : 1.0f), reversed, state, partialTicks);
            matrixStack.m_85849_();
        }
        matrixStack.m_85849_();
        context.m_280618_();
        GuiUtilsKt.blitk$default(matrices, reversed ? battleInfoBaseFlipped : battleInfoBase, Float.valueOf(x), Float.valueOf(y), 40, 131, null, null, null, null, null, null, null, null, Float.valueOf(opacity), false, 0.0f, 114624, null);
        if (colour != null) {
            float r = ((Number)colour.component1()).floatValue();
            float g = ((Number)colour.component2()).floatValue();
            float b = ((Number)colour.component3()).floatValue();
            resourceLocation = reversed ? battleInfoRoleFlipped : battleInfoRole;
            f2 = x + (float)(reversed ? 93 : 11);
            f = y + 1.0f;
            GuiUtilsKt.blitk$default(matrices, resourceLocation, Float.valueOf(f2), Float.valueOf(f), 3, 27, null, null, null, null, null, Float.valueOf(r), Float.valueOf(g), Float.valueOf(b), Float.valueOf(opacity), false, 0.0f, 100288, null);
        }
        if (status != null) {
            int statusWidth = 37;
            GuiUtilsKt.blitk$default(matrices, MiscUtils.cobblemonResource("textures/gui/battle/battle_status_" + status.getShowdownName() + ".png"), Float.valueOf(x + (float)(reversed ? 56 : 38)), Float.valueOf(y + (float)28), 7, statusWidth, reversed ? 0 : statusWidth, null, statusWidth * 2, null, null, null, null, null, Float.valueOf(opacity), false, 0.0f, 114304, null);
            ResourceLocation resourceLocation3 = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.status." + status.getShowdownName(), new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.status.\" + status.showdownName)");
            RenderHelperKt.drawScaledText$default(context, resourceLocation3, TextKt.bold(mutableComponent), Float.valueOf(x + (float)(reversed ? 78 : 42)), Float.valueOf(y + (float)27), 0.0f, Float.valueOf(opacity), 0, 0, false, false, null, null, 8096, null);
        }
        float infoBoxX = x + (float)(!reversed ? 40 : 7);
        RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), TextKt.bold(displayName), Float.valueOf(infoBoxX), Float.valueOf(y + (float)7), 0.0f, Float.valueOf(opacity), 0, 0, false, true, null, null, 7072, null);
        if (gender != Gender.GENDERLESS) {
            boolean isMale = gender == Gender.MALE;
            MutableComponent textSymbol = isMale ? TextKt.bold(TextKt.text("\u2642")) : TextKt.bold(TextKt.text("\u2640"));
            resourceLocation = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
            f2 = infoBoxX + (float)53;
            f = y + (float)7;
            int n = isMale ? 3329023 : 16536660;
            RenderHelperKt.drawScaledText$default(context, resourceLocation, textSymbol, Float.valueOf(f2), Float.valueOf(f), 0.0f, Float.valueOf(opacity), 0, n, false, true, null, null, 6816, null);
        }
        ResourceLocation resourceLocation4 = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
        MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.lv", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.lv\")");
        RenderHelperKt.drawScaledText$default(context, resourceLocation4, TextKt.bold(mutableComponent), Float.valueOf(infoBoxX + (float)59), Float.valueOf(y + (float)7), 0.0f, Float.valueOf(opacity), 0, 0, false, true, null, null, 7072, null);
        RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), TextKt.bold(TextKt.text(String.valueOf(level))), Float.valueOf(infoBoxX + (float)72), Float.valueOf(y + (float)7), 0.0f, Float.valueOf(opacity), 0, 0, false, true, null, null, 7072, null);
        float hpRatio = isFlatHealth ? health / (float)maxHealth : health;
        Pair pair = RenderHelperKt.getDepletableRedGreen$default(hpRatio, 0.0f, 0.0f, 6, null);
        float healthRed = ((Number)pair.component1()).floatValue();
        float healthGreen = ((Number)pair.component2()).floatValue();
        int fullWidth = 83;
        float barWidth = hpRatio * (float)fullWidth;
        float barX = !reversed ? infoBoxX - (float)2 : infoBoxX + (float)3 + ((float)fullWidth - barWidth);
        GuiUtilsKt.blitk$default(matrices, CobblemonResources.INSTANCE.getWHITE(), Float.valueOf(barX), Float.valueOf(y + (float)22), 4, Float.valueOf(barWidth), null, null, null, null, null, Float.valueOf(healthRed * 0.8f), Float.valueOf(healthGreen * 0.8f), Float.valueOf(0.27f), null, false, 0.0f, 116672, null);
        MutableComponent text = TextKt.text(isFlatHealth ? (int)health + "/" + maxHealth : Mth.m_14167_((float)(health * (float)100)) + "%");
        RenderHelperKt.drawScaledText$default(context, null, text, (double)infoBoxX + (!reversed ? 39.5 : 44.5), Float.valueOf(y + (float)22), 0.5f, Float.valueOf(opacity), 0, 0, true, true, null, null, 6530, null);
    }

    public static /* synthetic */ void drawBattleTile$default(BattleOverlay battleOverlay2, GuiGraphics guiGraphics, float f, float f2, float f3, boolean bl, Species species, int n, Set set2, MutableComponent mutableComponent, Gender gender, PersistentStatus persistentStatus, PoseableEntityState poseableEntityState, Triple triple, float f4, ClientBallDisplay clientBallDisplay, int n2, float f5, boolean bl2, int n3, Object object) {
        if ((n3 & 0x4000) != 0) {
            clientBallDisplay = null;
        }
        battleOverlay2.drawBattleTile(guiGraphics, f, f2, f3, bl, species, n, set2, mutableComponent, gender, persistentStatus, poseableEntityState, (Triple<Float, Float, Float>)triple, f4, clientBallDisplay, n2, f5, bl2);
    }

    private final void drawPokeBall(ClientBallDisplay state, PoseStack matrixStack, float scale, float partialTicks, boolean reversed) {
        PokeBallModel model = (PokeBallModel)PokeBallModelRepository.INSTANCE.getPoser(state.getPokeBall().getName(), state.getAspects());
        ResourceLocation texture = PokeBallModelRepository.INSTANCE.getTexture(state.getPokeBall().getName(), state.getAspects(), state.getAnimationSeconds());
        RenderType renderType = model.m_103119_(texture);
        RenderSystem.applyModelViewMatrix();
        Quaternionf quaternion1 = Axis.f_252436_.m_252977_(-32.0f * (reversed ? -1.0f : 1.0f));
        Quaternionf quaternion2 = Axis.f_252529_.m_252977_(5.0f);
        Pose pose = model.getPose(PoseType.PORTRAIT);
        if (pose != null) {
            Pose it = pose;
            boolean bl = false;
            state.setPose(it.getPoseName());
        }
        state.setTimeEnteredPose(0.0f);
        state.updatePartialTicks(partialTicks);
        model.setupAnimStateful(null, state, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        matrixStack.m_85841_(scale, scale, -scale);
        matrixStack.m_85837_(0.0, 5.5, -4.0);
        matrixStack.m_85836_();
        matrixStack.m_85841_(scale * state.getScale(), scale * state.getScale(), 0.1f);
        matrixStack.m_252781_(quaternion1);
        matrixStack.m_252781_(quaternion2);
        Vector3f light1 = new Vector3f(2.2f, 4.0f, -4.0f);
        Vector3f light2 = new Vector3f(1.1f, -4.0f, 7.0f);
        RenderSystem.setShaderLights((Vector3f)light1, (Vector3f)light2);
        quaternion1.conjugate();
        MultiBufferSource.BufferSource immediate = Minecraft.m_91087_().m_91269_().m_110104_();
        VertexConsumer buffer = immediate.m_6299_(renderType);
        int packedLight = LightTexture.m_109885_((int)11, (int)7);
        Intrinsics.checkNotNullExpressionValue((Object)buffer, (String)"buffer");
        model.m_7695_(matrixStack, buffer, packedLight, OverlayTexture.f_118083_, 1.0f, 1.0f, 1.0f, 1.0f);
        immediate.m_109911_();
        matrixStack.m_85849_();
        Lighting.m_84931_();
    }

    static /* synthetic */ void drawPokeBall$default(BattleOverlay battleOverlay2, ClientBallDisplay clientBallDisplay, PoseStack poseStack, float f, float f2, boolean bl, int n, Object object) {
        if ((n & 4) != 0) {
            f = 5.0f;
        }
        if ((n & 0x10) != 0) {
            bl = false;
        }
        battleOverlay2.drawPokeBall(clientBallDisplay, poseStack, f, f2, bl);
    }

    public final void onLogout() {
        this.opacity = 0.5;
        this.passedSeconds = 0.0f;
        this.lastKnownBattle = null;
    }

    @Override
    @NotNull
    public ScheduledTask momentarily(@NotNull Function0<Unit> action2) {
        return Schedulable.DefaultImpls.momentarily(this, action2);
    }

    @Override
    @NotNull
    public ScheduledTask after(float seconds, @NotNull Function0<Unit> action2) {
        return Schedulable.DefaultImpls.after(this, seconds, action2);
    }

    @Override
    @NotNull
    public ScheduledTask lerp(float seconds, @NotNull Function1<? super Float, Unit> action2) {
        return Schedulable.DefaultImpls.lerp(this, seconds, action2);
    }

    @Override
    @NotNull
    public ScheduledTask.Builder taskBuilder() {
        return Schedulable.DefaultImpls.taskBuilder(this);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000f\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b%\u0010&R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004R\u0014\u0010\b\u001a\u00020\u00078\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u00078\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\n\u0010\tR\u0014\u0010\u000b\u001a\u00020\u00078\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\tR\u0014\u0010\f\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\f\u0010\u0004R\u0014\u0010\r\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\r\u0010\u0004R\u0014\u0010\u000e\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\u0004R$\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00100\u000fj\u0002`\u00118\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0004R\u0014\u0010\u0015\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\u0004R\u0014\u0010\u0016\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0004R\u0014\u0010\u0017\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0004R\u0017\u0010\u0019\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u0017\u0010\u001d\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001a\u001a\u0004\b\u001e\u0010\u001cR\u0017\u0010\u001f\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010\u001a\u001a\u0004\b \u0010\u001cR\u0017\u0010!\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b!\u0010\u001a\u001a\u0004\b\"\u0010\u001cR\u0017\u0010#\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b#\u0010\u001a\u001a\u0004\b$\u0010\u001c\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/BattleOverlay$Companion;", "", "", "HORIZONTAL_INSET", "I", "HORIZONTAL_SPACING", "INFO_OFFSET_X", "", "MAX_OPACITY", "D", "MIN_OPACITY", "OPACITY_CHANGE_PER_SECOND", "PORTRAIT_DIAMETER", "PORTRAIT_OFFSET_X", "PORTRAIT_OFFSET_Y", "Lkotlin/Function1;", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/wavefunction/WaveFunction;", "PROMPT_TEXT_OPACITY_CURVE", "Lkotlin/jvm/functions/Function1;", "TILE_HEIGHT", "TILE_WIDTH", "VERTICAL_INSET", "VERTICAL_SPACING", "Lnet/minecraft/resources/ResourceLocation;", "battleInfoBase", "Lnet/minecraft/resources/ResourceLocation;", "getBattleInfoBase", "()Lnet/minecraft/resources/ResourceLocation;", "battleInfoBaseFlipped", "getBattleInfoBaseFlipped", "battleInfoRole", "getBattleInfoRole", "battleInfoRoleFlipped", "getBattleInfoRoleFlipped", "battleInfoUnderlay", "getBattleInfoUnderlay", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getBattleInfoBase() {
            return battleInfoBase;
        }

        @NotNull
        public final ResourceLocation getBattleInfoBaseFlipped() {
            return battleInfoBaseFlipped;
        }

        @NotNull
        public final ResourceLocation getBattleInfoRole() {
            return battleInfoRole;
        }

        @NotNull
        public final ResourceLocation getBattleInfoRoleFlipped() {
            return battleInfoRoleFlipped;
        }

        @NotNull
        public final ResourceLocation getBattleInfoUnderlay() {
            return battleInfoUnderlay;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

