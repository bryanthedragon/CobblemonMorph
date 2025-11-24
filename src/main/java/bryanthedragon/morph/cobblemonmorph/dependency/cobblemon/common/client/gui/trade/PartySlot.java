/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.trade;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.PokemonGuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.trade.TradeGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeStartedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.QuaternionUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u0000 #2\u00020\u0001:\u0001#B;\u0012\u0006\u0010\u001d\u001a\u00020\u0005\u0012\u0006\u0010\u001e\u001a\u00020\u0005\u0012\b\u0010\u001b\u001a\u0004\u0018\u00010\u001a\u0012\u0006\u0010\u0018\u001a\u00020\u0017\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0002\u0012\u0006\u0010 \u001a\u00020\u001f\u00a2\u0006\u0004\b!\u0010\"J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001d\u0010\b\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ/\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\u0016R\u0014\u0010\u0018\u001a\u00020\u00178\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\u0019R\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u001a8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001b\u0010\u001c\u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/client/gui/trade/PartySlot;", "Lnet/minecraft/client/gui/components/Button;", "", "hasSelected", "()Z", "", "mouseX", "mouseY", "isHovered", "(II)Z", "Lnet/minecraft/client/sounds/SoundManager;", "soundManager", "", "playDownSound", "(Lnet/minecraft/client/sounds/SoundManager;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "delta", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "isOpposing", "Z", "Lcom/cobblemon/mod/common/client/gui/trade/TradeGUI;", "parent", "Lcom/cobblemon/mod/common/client/gui/trade/TradeGUI;", "Lcom/cobblemon/mod/common/net/messages/client/trade/TradeStartedPacket$TradeablePokemon;", "pokemon", "Lcom/cobblemon/mod/common/net/messages/client/trade/TradeStartedPacket$TradeablePokemon;", "x", "y", "Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;", "onPress", "<init>", "(IILcom/cobblemon/mod/common/net/messages/client/trade/TradeStartedPacket$TradeablePokemon;Lcom/cobblemon/mod/common/client/gui/trade/TradeGUI;ZLnet/minecraft/client/gui/components/Button$OnPress;)V", "Companion", "common"})
public class PartySlot
extends Button {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @Nullable
    private final TradeStartedPacket.TradeablePokemon pokemon;
    @NotNull
    private final TradeGUI parent;
    private final boolean isOpposing;
    public static final int SIZE = 25;
    @NotNull
    private static final ResourceLocation hoverBackgroundResource = MiscUtilsKt.cobblemonResource("textures/gui/trade/trade_party_slot_hover.png");
    @NotNull
    private static final ResourceLocation genderIconMale = MiscUtilsKt.cobblemonResource("textures/gui/pc/gender_icon_male.png");
    @NotNull
    private static final ResourceLocation genderIconFemale = MiscUtilsKt.cobblemonResource("textures/gui/pc/gender_icon_female.png");
    @NotNull
    private static final ResourceLocation selectPointerResource = MiscUtilsKt.cobblemonResource("textures/gui/pc/pc_pointer.png");
    @NotNull
    private static final ResourceLocation untradeableResource = MiscUtilsKt.cobblemonResource("textures/gui/trade/trade_slot_icon_locked.png");

    public PartySlot(int x, int y, @Nullable TradeStartedPacket.TradeablePokemon pokemon, @NotNull TradeGUI parent, boolean isOpposing, @NotNull Button.OnPress onPress) {
        Intrinsics.checkNotNullParameter((Object)((Object)parent), (String)"parent");
        Intrinsics.checkNotNullParameter((Object)onPress, (String)"onPress");
        super(x, y, 25, 25, (Component)Component.m_237113_((String)"PartySlot"), onPress, Button.f_252438_);
        this.pokemon = pokemon;
        this.parent = parent;
        this.isOpposing = isOpposing;
    }

    public /* synthetic */ PartySlot(int n, int n2, TradeStartedPacket.TradeablePokemon tradeablePokemon, TradeGUI tradeGUI, boolean bl, Button.OnPress onPress, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 0x10) != 0) {
            bl = false;
        }
        this(n, n2, tradeablePokemon, tradeGUI, bl, onPress);
    }

    public void m_7435_(@NotNull SoundManager soundManager) {
        Intrinsics.checkNotNullParameter((Object)soundManager, (String)"soundManager");
    }

    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        int n;
        Object object;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        PoseStack matrices = context.m_280168_();
        if (!this.isOpposing && this.isHovered(mouseX, mouseY)) {
            object = hoverBackgroundResource;
            int n2 = this.m_252754_();
            n = this.m_252907_();
            Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
            GuiUtilsKt.blitk$default(matrices, object, n2, n, 25, 25, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        }
        if (this.pokemon != null) {
            ItemStack heldItem2;
            context.m_280588_(this.m_252754_() - 2, this.m_252907_() + 2, this.m_252754_() + 25 + 4, this.m_252907_() + 25 + 4);
            matrices.m_85836_();
            matrices.m_85837_((double)this.m_252754_() + 12.5, (double)this.m_252907_() + 1.0, 0.0);
            matrices.m_85841_(2.5f, 2.5f, 1.0f);
            object = this.pokemon.asRenderablePokemon();
            Object[] objectArray = QuaternionUtilsKt.fromEulerXYZDegrees(new Quaternionf(), new Vector3f(13.0f, 35.0f, 0.0f));
            Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
            PokemonGuiUtilsKt.drawProfilePokemon((RenderablePokemon)object, matrices, (Quaternionf)objectArray, null, delta, 4.5f);
            matrices.m_85849_();
            context.m_280618_();
            matrices.m_85836_();
            matrices.m_85837_(0.0, 0.0, 100.0);
            objectArray = new Object[]{this.pokemon.getLevel()};
            object = LocalizationUtilsKt.lang("ui.lv.number", objectArray);
            int n3 = this.m_252754_() + 1;
            n = this.m_252907_() + 1;
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"lang(\"ui.lv.number\", pokemon.level)");
            RenderHelperKt.drawScaledText$default(context, null, (MutableComponent)object, n3, n, 0.5f, null, 0, 0, false, true, null, null, 7106, null);
            if (this.pokemon.getGender() != Gender.GENDERLESS) {
                object = this.pokemon.getGender() == Gender.MALE ? genderIconMale : genderIconFemale;
                float f = (float)(this.m_252754_() + 21) / 0.5f;
                float f2 = (float)(this.m_252907_() + 1) / 0.5f;
                GuiUtilsKt.blitk$default(matrices, object, Float.valueOf(f), Float.valueOf(f2), 8, 6, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
            }
            if (!this.pokemon.getTradeable()) {
                matrices.m_85836_();
                matrices.m_252880_(0.0f, 0.0f, 10.0f);
                object = untradeableResource;
                float f = (float)(this.m_252754_() + 8) / 0.5f;
                float f3 = (float)(this.m_252907_() + 8) / 0.5f;
                GuiUtilsKt.blitk$default(matrices, object, Float.valueOf(f), Float.valueOf(f3), 20, 20, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
                matrices.m_85849_();
            }
            matrices.m_85849_();
            if (this.hasSelected()) {
                object = selectPointerResource;
                float f = (float)(this.m_252754_() + 10) / 0.5f;
                float f4 = (float)(this.m_252907_() - 3) / 0.5f - (float)this.parent.getSelectPointerOffsetY();
                GuiUtilsKt.blitk$default(matrices, object, Float.valueOf(f), Float.valueOf(f4), 8, 11, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
            }
            if (!(heldItem2 = this.pokemon.getHeldItem()).m_41619_()) {
                RenderHelperKt.renderScaledGuiItemIcon$default(heldItem2, (double)this.m_252754_() + 16.0, (double)this.m_252907_() + 16.0, 0.5, 0.0f, matrices, 16, null);
            }
        }
    }

    public boolean hasSelected() {
        Pokemon offeredPokemon = this.isOpposing ? this.parent.getTrade().getOppositeOffer().get() : this.parent.getTrade().getMyOffer().get();
        TradeStartedPacket.TradeablePokemon tradeablePokemon = this.pokemon;
        Pokemon pokemon = offeredPokemon;
        return Intrinsics.areEqual((Object)(tradeablePokemon != null ? tradeablePokemon.getPokemonId() : null), (Object)(pokemon != null ? pokemon.getUuid() : null)) && this.pokemon != null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isHovered(int mouseX, int mouseY) {
        boolean bl;
        TradeStartedPacket.TradeablePokemon tradeablePokemon = this.pokemon;
        if (tradeablePokemon != null) {
            if (!tradeablePokemon.getTradeable()) {
                return false;
            }
            bl = false;
        } else {
            bl = false;
        }
        if (bl) return false;
        float f = this.m_252754_();
        float f2 = (float)this.m_252754_() + (float)25;
        float f3 = mouseX;
        if (!(f <= f3)) return false;
        if (!(f3 <= f2)) return false;
        boolean bl2 = true;
        if (!bl2) return false;
        f = this.m_252907_();
        f2 = (float)this.m_252907_() + (float)25;
        f3 = mouseY;
        if (!(f <= f3)) return false;
        if (!(f3 <= f2)) return false;
        return true;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00058\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0007R\u0014\u0010\t\u001a\u00020\u00058\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0007R\u0014\u0010\n\u001a\u00020\u00058\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\n\u0010\u0007R\u0014\u0010\u000b\u001a\u00020\u00058\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\u0007\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/client/gui/trade/PartySlot$Companion;", "", "", "SIZE", "I", "Lnet/minecraft/resources/ResourceLocation;", "genderIconFemale", "Lnet/minecraft/resources/ResourceLocation;", "genderIconMale", "hoverBackgroundResource", "selectPointerResource", "untradeableResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

