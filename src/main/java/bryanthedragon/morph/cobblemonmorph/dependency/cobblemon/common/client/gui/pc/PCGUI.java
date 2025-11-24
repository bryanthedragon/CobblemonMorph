/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.ExitButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.TypeIcon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture.PastureWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.NavigationButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUIConfiguration;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.StorageWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.Summary;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.ModelWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.common.NatureInfoUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientPC;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.storage.ClientParty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc.UnlinkPlayerFromPCPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 T2\u00020\u0001:\u0001TB\u001f\u0012\u0006\u00109\u001a\u000208\u0012\u0006\u00104\u001a\u000203\u0012\u0006\u0010,\u001a\u00020+\u00a2\u0006\u0004\bR\u0010SJ\u0017\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\u0007\u001a\u00020\u0004H\u0014\u00a2\u0006\u0004\b\u0007\u0010\bJ'\u0010\r\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ7\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u0015\u0010\u0016J'\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u000fH\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0015\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001a\u00a2\u0006\u0004\b\u001c\u0010\u001dJ/\u0010\"\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\t2\u0006\u0010!\u001a\u00020 H\u0016\u00a2\u0006\u0004\b\"\u0010#J\u0017\u0010&\u001a\u00020\u00042\b\u0010%\u001a\u0004\u0018\u00010$\u00a2\u0006\u0004\b&\u0010'J\u000f\u0010(\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b(\u0010)J\u000f\u0010*\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b*\u0010\bR\u0017\u0010,\u001a\u00020+8\u0006\u00a2\u0006\f\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/R\u0018\u00101\u001a\u0004\u0018\u0001008\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b1\u00102R\u0017\u00104\u001a\u0002038\u0006\u00a2\u0006\f\n\u0004\b4\u00105\u001a\u0004\b6\u00107R\u0017\u00109\u001a\u0002088\u0006\u00a2\u0006\f\n\u0004\b9\u0010:\u001a\u0004\b;\u0010<R$\u0010=\u001a\u0004\u0018\u00010$8\u0000@\u0000X\u0080\u000e\u00a2\u0006\u0012\n\u0004\b=\u0010>\u001a\u0004\b?\u0010@\"\u0004\bA\u0010'R\"\u0010B\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bB\u0010C\u001a\u0004\bD\u0010)\"\u0004\bE\u0010\u0006R\"\u0010F\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bF\u0010G\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010KR\u0016\u0010M\u001a\u00020L8\u0002@\u0002X\u0082.\u00a2\u0006\u0006\n\u0004\bM\u0010NR\"\u0010O\u001a\u00020\t8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bO\u0010G\u001a\u0004\bP\u0010I\"\u0004\bQ\u0010K\u00a8\u0006U"}, d2={"Lcom/cobblemon/mod/common/client/gui/pc/PCGUI;", "Lnet/minecraft/client/gui/screens/Screen;", "", "unlink", "", "closeNormally", "(Z)V", "init", "()V", "", "keyCode", "scanCode", "modifiers", "keyPressed", "(III)Z", "", "mouseX", "mouseY", "button", "deltaX", "deltaY", "mouseDragged", "(DDIDD)Z", "amount", "mouseScrolled", "(DDD)Z", "Lnet/minecraft/sounds/SoundEvent;", "soundEvent", "playSound", "(Lnet/minecraft/sounds/SoundEvent;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "delta", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "setPreviewPokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "shouldPause", "()Z", "tick", "Lcom/cobblemon/mod/common/client/gui/pc/PCGUIConfiguration;", "configuration", "Lcom/cobblemon/mod/common/client/gui/pc/PCGUIConfiguration;", "getConfiguration", "()Lcom/cobblemon/mod/common/client/gui/pc/PCGUIConfiguration;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/ModelWidget;", "modelWidget", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/ModelWidget;", "Lcom/cobblemon/mod/common/client/storage/ClientParty;", "party", "Lcom/cobblemon/mod/common/client/storage/ClientParty;", "getParty", "()Lcom/cobblemon/mod/common/client/storage/ClientParty;", "Lcom/cobblemon/mod/common/client/storage/ClientPC;", "pc", "Lcom/cobblemon/mod/common/client/storage/ClientPC;", "getPc", "()Lcom/cobblemon/mod/common/client/storage/ClientPC;", "previewPokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPreviewPokemon$common", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "setPreviewPokemon$common", "selectPointerOffsetIncrement", "Z", "getSelectPointerOffsetIncrement", "setSelectPointerOffsetIncrement", "selectPointerOffsetY", "I", "getSelectPointerOffsetY", "()I", "setSelectPointerOffsetY", "(I)V", "Lcom/cobblemon/mod/common/client/gui/pc/StorageWidget;", "storageWidget", "Lcom/cobblemon/mod/common/client/gui/pc/StorageWidget;", "ticksElapsed", "getTicksElapsed", "setTicksElapsed", "<init>", "(Lcom/cobblemon/mod/common/client/storage/ClientPC;Lcom/cobblemon/mod/common/client/storage/ClientParty;Lcom/cobblemon/mod/common/client/gui/pc/PCGUIConfiguration;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nPCGUI.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PCGUI.kt\ncom/cobblemon/mod/common/client/gui/pc/PCGUI\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,508:1\n1747#2,3:509\n*S KotlinDebug\n*F\n+ 1 PCGUI.kt\ncom/cobblemon/mod/common/client/gui/pc/PCGUI\n*L\n433#1:509,3\n*E\n"})
public final class PCGUI
extends Screen {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ClientPC pc;
    @NotNull
    private final ClientParty party;
    @NotNull
    private final PCGUIConfiguration configuration;
    private StorageWidget storageWidget;
    @Nullable
    private ModelWidget modelWidget;
    @Nullable
    private Pokemon previewPokemon;
    private int ticksElapsed;
    private int selectPointerOffsetY;
    private boolean selectPointerOffsetIncrement;
    public static final int BASE_WIDTH = 349;
    public static final int BASE_HEIGHT = 205;
    public static final int RIGHT_PANEL_WIDTH = 82;
    public static final int RIGHT_PANEL_HEIGHT = 169;
    public static final int TYPE_SPACER_WIDTH = 128;
    public static final int TYPE_SPACER_HEIGHT = 12;
    public static final int PC_SPACER_WIDTH = 342;
    public static final int PC_SPACER_HEIGHT = 14;
    public static final int PORTRAIT_SIZE = 66;
    public static final float SCALE = 0.5f;
    @NotNull
    private static final ResourceLocation baseResource = MiscUtilsKt.cobblemonResource("textures/gui/pc/pc_base.png");
    @NotNull
    private static final ResourceLocation portraitBackgroundResource = MiscUtilsKt.cobblemonResource("textures/gui/pc/portrait_background.png");
    @NotNull
    private static final ResourceLocation topSpacerResource = MiscUtilsKt.cobblemonResource("textures/gui/pc/pc_spacer_top.png");
    @NotNull
    private static final ResourceLocation bottomSpacerResource = MiscUtilsKt.cobblemonResource("textures/gui/pc/pc_spacer_bottom.png");
    @NotNull
    private static final ResourceLocation rightSpacerResource = MiscUtilsKt.cobblemonResource("textures/gui/pc/pc_spacer_right.png");
    @NotNull
    private static final ResourceLocation typeSpacerResource = MiscUtilsKt.cobblemonResource("textures/gui/pc/type_spacer.png");
    @NotNull
    private static final ResourceLocation typeSpacerSingleResource = MiscUtilsKt.cobblemonResource("textures/gui/pc/type_spacer_single.png");
    @NotNull
    private static final ResourceLocation typeSpacerDoubleResource = MiscUtilsKt.cobblemonResource("textures/gui/pc/type_spacer_double.png");

    public PCGUI(@NotNull ClientPC pc, @NotNull ClientParty party, @NotNull PCGUIConfiguration configuration) {
        Intrinsics.checkNotNullParameter((Object)pc, (String)"pc");
        Intrinsics.checkNotNullParameter((Object)party, (String)"party");
        Intrinsics.checkNotNullParameter((Object)configuration, (String)"configuration");
        super((Component)Component.m_237115_((String)"cobblemon.ui.pc.title"));
        this.pc = pc;
        this.party = party;
        this.configuration = configuration;
    }

    @NotNull
    public final ClientPC getPc() {
        return this.pc;
    }

    @NotNull
    public final ClientParty getParty() {
        return this.party;
    }

    @NotNull
    public final PCGUIConfiguration getConfiguration() {
        return this.configuration;
    }

    @Nullable
    public final Pokemon getPreviewPokemon$common() {
        return this.previewPokemon;
    }

    public final void setPreviewPokemon$common(@Nullable Pokemon pokemon) {
        this.previewPokemon = pokemon;
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

    public final boolean getSelectPointerOffsetIncrement() {
        return this.selectPointerOffsetIncrement;
    }

    public final void setSelectPointerOffsetIncrement(boolean bl) {
        this.selectPointerOffsetIncrement = bl;
    }

    protected void m_7856_() {
        int x = (this.f_96543_ - 349) / 2;
        int y = (this.f_96544_ - 205) / 2;
        this.m_142416_((GuiEventListener)new ExitButton(x + 320, y + 186, arg_0 -> PCGUI.init$lambda$0(this, arg_0)));
        this.m_142416_((GuiEventListener)new NavigationButton(x + 221, y + 17, true, arg_0 -> PCGUI.init$lambda$1(this, arg_0)));
        this.m_142416_((GuiEventListener)new NavigationButton(x + 119, y + 17, false, arg_0 -> PCGUI.init$lambda$2(this, arg_0)));
        this.storageWidget = new StorageWidget(x + 85, y + 27, this, this.pc, this.party);
        this.setPreviewPokemon(null);
        StorageWidget storageWidget = this.storageWidget;
        if (storageWidget == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"storageWidget");
            storageWidget = null;
        }
        this.m_142416_((GuiEventListener)storageWidget);
        super.m_7856_();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Object status;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        PoseStack matrices = context.m_280168_();
        this.m_280273_(context);
        int x = (this.f_96543_ - 349) / 2;
        int y = (this.f_96544_ - 205) / 2;
        ResourceLocation resourceLocation = portraitBackgroundResource;
        int n = x + 6;
        int n2 = y + 27;
        Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
        GuiUtilsKt.blitk$default(matrices, resourceLocation, n, n2, 66, 66, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        ModelWidget modelWidget = this.modelWidget;
        if (modelWidget != null) {
            modelWidget.m_88315_(context, mouseX, mouseY, delta);
            v1 = Unit.INSTANCE;
        } else {
            v1 = null;
        }
        resourceLocation = baseResource;
        GuiUtilsKt.blitk$default(matrices, resourceLocation, x, y, 205, 349, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.info.nature", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.info.nature\")");
        resourceLocation = TextKt.bold(mutableComponent);
        n = x + 39;
        double d = (double)y + 129.5;
        RenderHelperKt.drawScaledText$default(context, null, (MutableComponent)resourceLocation, n, d, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
        MutableComponent mutableComponent2 = LocalizationUtilsKt.lang("ui.info.ability", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"lang(\"ui.info.ability\")");
        resourceLocation = TextKt.bold(mutableComponent2);
        n = x + 39;
        d = (double)y + 146.5;
        RenderHelperKt.drawScaledText$default(context, null, (MutableComponent)resourceLocation, n, d, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
        MutableComponent mutableComponent3 = LocalizationUtilsKt.lang("ui.moves", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent3, (String)"lang(\"ui.moves\")");
        resourceLocation = TextKt.bold(mutableComponent3);
        n = x + 39;
        d = (double)y + 163.5;
        RenderHelperKt.drawScaledText$default(context, null, (MutableComponent)resourceLocation, n, d, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
        Pokemon pokemon = this.previewPokemon;
        if (pokemon != null) {
            ResourceLocation resourceLocation2;
            PersistentStatusContainer persistentStatusContainer = pokemon.getStatus();
            PersistentStatus persistentStatus = status = persistentStatusContainer != null ? persistentStatusContainer.getStatus() : null;
            if (pokemon.isFainted() || status != null) {
                String string;
                if (pokemon.isFainted()) {
                    string = "fnt";
                } else {
                    Object object = status;
                    string = object != null ? ((Status)object).getShowdownName() : null;
                }
                String statusName = string;
                GuiUtilsKt.blitk$default(matrices, MiscUtilsKt.cobblemonResource("textures/gui/battle/battle_status_" + statusName + ".png"), x + 34, y + 1, 7, 39, 35, null, 74, null, null, null, null, null, null, false, 0.0f, 130688, null);
                GuiUtilsKt.blitk$default(matrices, MiscUtilsKt.cobblemonResource("textures/gui/summary/status_trim.png"), x + 34, y + 2, 6, 3, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
                ResourceLocation resourceLocation3 = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
                MutableComponent mutableComponent4 = LocalizationUtilsKt.lang("ui.status." + statusName, new Object[0]);
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent4, (String)"lang(\"ui.status.$statusName\")");
                RenderHelperKt.drawScaledText$default(context, resourceLocation3, TextKt.bold(mutableComponent4), x + 39, y, 0.0f, null, 0, 0, false, false, null, null, 8160, null);
            }
            ResourceLocation resourceLocation4 = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
            MutableComponent mutableComponent5 = LocalizationUtilsKt.lang("ui.lv", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent5, (String)"lang(\"ui.lv\")");
            RenderHelperKt.drawScaledText$default(context, resourceLocation4, TextKt.bold(mutableComponent5), x + 6, (double)y + 1.5, 0.0f, null, 0, 0, false, true, null, null, 7136, null);
            RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), TextKt.bold(TextKt.text(String.valueOf(pokemon.getLevel()))), x + 19, (double)y + 1.5, 0.0f, null, 0, 0, false, true, null, null, 7136, null);
            ResourceLocation ballResource22 = MiscUtilsKt.cobblemonResource("textures/item/poke_balls/" + pokemon.getCaughtBall().getName().m_135815_() + ".png");
            double d2 = ((double)x + 3.5) / (double)0.5f;
            float f = (float)(y + 12) / 0.5f;
            GuiUtilsKt.blitk$default(matrices, ballResource22, d2, Float.valueOf(f), 16, 16, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
            RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), TextKt.bold(pokemon.getDisplayName()), x + 12, (double)y + 11.5, 0.0f, null, 0, 0, false, true, null, null, 7136, null);
            if (pokemon.getGender() != Gender.GENDERLESS) {
                boolean isMale = pokemon.getGender() == Gender.MALE;
                MutableComponent textSymbol = isMale ? TextKt.bold(TextKt.text("\u2642")) : TextKt.bold(TextKt.text("\u2640"));
                RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), textSymbol, x + 69, (double)y + 11.5, 0.0f, null, 0, isMale ? 3329023 : 16536660, false, true, null, null, 6880, null);
            }
            ItemStack heldItem2 = pokemon.heldItemNoCopy$common();
            int itemX = x + 3;
            int itemY = y + 98;
            if (!heldItem2.m_41619_()) {
                context.m_280480_(heldItem2, itemX, itemY);
                context.m_280370_(Minecraft.m_91087_().f_91062_, heldItem2, itemX, itemY);
            }
            MutableComponent mutableComponent6 = LocalizationUtilsKt.lang("held_item", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent6, (String)"lang(\"held_item\")");
            RenderHelperKt.drawScaledText$default(context, null, mutableComponent6, x + 27, (double)y + 108.5, 0.5f, null, 0, 0, false, false, null, null, 8130, null);
            if (pokemon.getShiny()) {
                resourceLocation2 = Summary.Companion.getIconShinyResource();
                double d3 = ((double)x + 62.5) / (double)0.5f;
                double d4 = ((double)y + 28.5) / (double)0.5f;
                GuiUtilsKt.blitk$default(matrices, resourceLocation2, d3, d4, 16, 16, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
            }
            resourceLocation2 = pokemon.getSecondaryType() != null ? typeSpacerDoubleResource : typeSpacerSingleResource;
            float f2 = (float)(x + 7) / 0.5f;
            double d5 = ((double)y + 118.5) / (double)0.5f;
            GuiUtilsKt.blitk$default(matrices, resourceLocation2, Float.valueOf(f2), d5, 12, 128, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
            int n3 = x + 39;
            int n4 = y + 117;
            ElementalType elementalType = pokemon.getPrimaryType();
            ElementalType elementalType2 = pokemon.getSecondaryType();
            new TypeIcon(n3, n4, elementalType, elementalType2, true, true, 10.0f, 5.0f, 0.0f, 256, null).render(context);
            MutableComponent natureText = NatureInfoUtilsKt.reformatNatureTextIfMinted(pokemon);
            n4 = x + 39;
            int n5 = y + 137;
            RenderHelperKt.drawScaledText$default(context, null, natureText, n4, n5, 0.5f, null, 0, 0, true, true, mouseX, mouseY, 450, null);
            MutableComponent mutableComponent7 = MiscUtilsKt.asTranslated(pokemon.getAbility().getDisplayName());
            n5 = x + 39;
            int n6 = y + 154;
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent7, (String)"asTranslated()");
            RenderHelperKt.drawScaledText$default(context, null, mutableComponent7, n5, n6, 0.5f, null, 0, 0, true, true, null, null, 6594, null);
            List<Move> moves = pokemon.getMoveSet().getMoves();
            n6 = moves.size();
            for (int i = 0; i < n6; ++i) {
                MutableComponent mutableComponent8 = moves.get(i).getDisplayName();
                int n7 = x + 39;
                double d6 = (double)y + 170.5 + (double)(7 * i);
                RenderHelperKt.drawScaledText$default(context, null, mutableComponent8, n7, d6, 0.5f, null, 0, 0, true, true, null, null, 6594, null);
            }
        } else {
            status = typeSpacerResource;
            float ballResource22 = (float)(x + 7) / 0.5f;
            double heldItem2 = ((double)y + 118.5) / (double)0.5f;
            GuiUtilsKt.blitk$default(matrices, (ResourceLocation)status, Float.valueOf(ballResource22), heldItem2, 12, 128, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        }
        ResourceLocation resourceLocation5 = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
        Object[] ballResource22 = new Object[1];
        StorageWidget storageWidget = this.storageWidget;
        if (storageWidget == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"storageWidget");
            storageWidget = null;
        }
        ballResource22[0] = String.valueOf(storageWidget.getBox() + 1);
        MutableComponent mutableComponent9 = Component.m_237110_((String)"cobblemon.ui.pc.box.title", (Object[])ballResource22);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent9, (String)"translatable(\"cobblemon.\u2026dget.box + 1).toString())");
        RenderHelperKt.drawScaledText$default(context, resourceLocation5, TextKt.bold(mutableComponent9), x + 172, y + 15, 0.0f, null, 0, 0, true, false, null, null, 7648, null);
        status = topSpacerResource;
        double ballResource22 = ((double)x + 86.5) / (double)0.5f;
        float f = (float)(y + 13) / 0.5f;
        GuiUtilsKt.blitk$default(matrices, (ResourceLocation)status, ballResource22, Float.valueOf(f), 14, 342, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        status = bottomSpacerResource;
        ballResource22 = ((double)x + 86.5) / (double)0.5f;
        f = (float)(y + 189) / 0.5f;
        GuiUtilsKt.blitk$default(matrices, (ResourceLocation)status, ballResource22, Float.valueOf(f), 14, 342, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        status = rightSpacerResource;
        ballResource22 = ((double)x + 275.5) / (double)0.5f;
        f = (float)(y + 184) / 0.5f;
        GuiUtilsKt.blitk$default(matrices, (ResourceLocation)status, ballResource22, Float.valueOf(f), 24, 64, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        super.m_88315_(context, mouseX, mouseY, delta);
        if (pokemon == null) return;
        if (pokemon.heldItemNoCopy$common().m_41619_()) return;
        int itemX = x + 3;
        int itemY = y + 98;
        f = itemX;
        float f3 = (float)itemX + (float)16;
        float f4 = mouseX;
        if (!(f <= f4)) return;
        if (!(f4 <= f3)) return;
        boolean bl = true;
        if (!bl) return;
        f = itemY;
        f3 = (float)itemY + (float)16;
        f4 = mouseY;
        if (!(f <= f4)) return;
        if (!(f4 <= f3)) return;
        boolean bl2 = true;
        if (!bl2) return;
        boolean bl3 = true;
        boolean itemHovered = bl3;
        if (!itemHovered) return;
        context.m_280153_(Minecraft.m_91087_().f_91062_, pokemon.heldItemNoCopy$common(), mouseX, mouseY);
    }

    public final void closeNormally(boolean unlink) {
        this.playSound(CobblemonSounds.PC_OFF);
        Minecraft.m_91087_().m_91152_(null);
        if (unlink) {
            new UnlinkPlayerFromPCPacket().sendToServer();
        }
    }

    public static /* synthetic */ void closeNormally$default(PCGUI pCGUI, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            bl = true;
        }
        pCGUI.closeNormally(bl);
    }

    public boolean m_6050_(double mouseX, double mouseY, double amount) {
        boolean bl;
        block6: {
            StorageWidget storageWidget = this.storageWidget;
            if (storageWidget == null) {
                Intrinsics.throwUninitializedPropertyAccessException((String)"storageWidget");
                storageWidget = null;
            }
            if (storageWidget.getPastureWidget() != null) {
                StorageWidget storageWidget2 = this.storageWidget;
                if (storageWidget2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException((String)"storageWidget");
                    storageWidget2 = null;
                }
                PastureWidget pastureWidget = storageWidget2.getPastureWidget();
                Intrinsics.checkNotNull((Object)((Object)pastureWidget));
                pastureWidget.getPastureScrollList().m_6050_(mouseX, mouseY, amount);
            }
            List list = this.m_6702_();
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"children()");
            Iterable $this$any$iv = list;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    GuiEventListener it = (GuiEventListener)element$iv;
                    boolean bl2 = false;
                    if (!it.m_6050_(mouseX, mouseY, amount)) continue;
                    bl = true;
                    break block6;
                }
                bl = false;
            }
        }
        return bl;
    }

    public boolean m_7979_(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        StorageWidget storageWidget = this.storageWidget;
        if (storageWidget == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"storageWidget");
            storageWidget = null;
        }
        if (storageWidget.getPastureWidget() != null) {
            StorageWidget storageWidget2 = this.storageWidget;
            if (storageWidget2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException((String)"storageWidget");
                storageWidget2 = null;
            }
            PastureWidget pastureWidget = storageWidget2.getPastureWidget();
            Intrinsics.checkNotNull((Object)((Object)pastureWidget));
            pastureWidget.getPastureScrollList().m_7979_(mouseX, mouseY, button, deltaX, deltaY);
        }
        return super.m_7979_(mouseX, mouseY, button, deltaX, deltaY);
    }

    public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
        switch (keyCode) {
            case 256: {
                this.playSound(CobblemonSounds.PC_OFF);
                new UnlinkPlayerFromPCPacket().sendToServer();
                break;
            }
            case 262: {
                this.playSound(CobblemonSounds.PC_CLICK);
                StorageWidget storageWidget = this.storageWidget;
                if (storageWidget == null) {
                    Intrinsics.throwUninitializedPropertyAccessException((String)"storageWidget");
                    storageWidget = null;
                }
                StorageWidget storageWidget2 = storageWidget;
                storageWidget2.setBox(storageWidget2.getBox() + 1);
                break;
            }
            case 263: {
                this.playSound(CobblemonSounds.PC_CLICK);
                StorageWidget storageWidget = this.storageWidget;
                if (storageWidget == null) {
                    Intrinsics.throwUninitializedPropertyAccessException((String)"storageWidget");
                    storageWidget = null;
                }
                StorageWidget storageWidget3 = storageWidget;
                storageWidget3.setBox(storageWidget3.getBox() - 1);
            }
        }
        return super.m_7933_(keyCode, scanCode, modifiers);
    }

    public boolean m_7043_() {
        return false;
    }

    public void m_86600_() {
        int n = this.ticksElapsed;
        this.ticksElapsed = n + 1;
        int delayFactor = 3;
        if (this.ticksElapsed % (2 * delayFactor) == 0) {
            boolean bl = this.selectPointerOffsetIncrement = !this.selectPointerOffsetIncrement;
        }
        if (this.ticksElapsed % delayFactor == 0) {
            this.selectPointerOffsetY += this.selectPointerOffsetIncrement ? 1 : -1;
        }
    }

    public final void playSound(@NotNull SoundEvent soundEvent) {
        Intrinsics.checkNotNullParameter((Object)soundEvent, (String)"soundEvent");
        Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)soundEvent, (float)1.0f));
    }

    public final void setPreviewPokemon(@Nullable Pokemon pokemon) {
        if (pokemon != null) {
            this.previewPokemon = pokemon;
            int x = (this.f_96543_ - 349) / 2;
            int y = (this.f_96544_ - 205) / 2;
            this.modelWidget = new ModelWidget(x + 6, y + 27, 66, 66, pokemon.asRenderablePokemon(), 2.0f, 325.0f, -10.0);
        } else {
            this.previewPokemon = null;
            this.modelWidget = null;
        }
    }

    private static final void init$lambda$0(PCGUI this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        this$0.configuration.getExitFunction().invoke((Object)this$0);
    }

    private static final void init$lambda$1(PCGUI this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        StorageWidget storageWidget = this$0.storageWidget;
        if (storageWidget == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"storageWidget");
            storageWidget = null;
        }
        StorageWidget storageWidget2 = storageWidget;
        storageWidget2.setBox(storageWidget2.getBox() + 1);
    }

    private static final void init$lambda$2(PCGUI this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        StorageWidget storageWidget = this$0.storageWidget;
        if (storageWidget == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"storageWidget");
            storageWidget = null;
        }
        StorageWidget storageWidget2 = storageWidget;
        storageWidget2.setBox(storageWidget2.getBox() - 1);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\u0004R\u0014\u0010\b\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0004R\u0014\u0010\t\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0004R\u0014\u0010\n\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\n\u0010\u0004R\u0014\u0010\f\u001a\u00020\u000b8\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\u0004R\u0014\u0010\u000f\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u0004R\u0014\u0010\u0011\u001a\u00020\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u0012R\u0014\u0010\u0014\u001a\u00020\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0012R\u0014\u0010\u0015\u001a\u00020\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\u0012R\u0014\u0010\u0016\u001a\u00020\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0012R\u0014\u0010\u0017\u001a\u00020\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0012R\u0014\u0010\u0018\u001a\u00020\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\u0012R\u0014\u0010\u0019\u001a\u00020\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\u0012\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/client/gui/pc/PCGUI$Companion;", "", "", "BASE_HEIGHT", "I", "BASE_WIDTH", "PC_SPACER_HEIGHT", "PC_SPACER_WIDTH", "PORTRAIT_SIZE", "RIGHT_PANEL_HEIGHT", "RIGHT_PANEL_WIDTH", "", "SCALE", "F", "TYPE_SPACER_HEIGHT", "TYPE_SPACER_WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "baseResource", "Lnet/minecraft/resources/ResourceLocation;", "bottomSpacerResource", "portraitBackgroundResource", "rightSpacerResource", "topSpacerResource", "typeSpacerDoubleResource", "typeSpacerResource", "typeSpacerSingleResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

