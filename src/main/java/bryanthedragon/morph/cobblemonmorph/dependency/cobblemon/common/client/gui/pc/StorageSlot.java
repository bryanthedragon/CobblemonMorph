/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.PokemonGuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture.PasturePCGUIConfiguration;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.StorageWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture.OpenPasturePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.QuaternionUtilsKt;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u0000 '2\u00020\u0001:\u0001'B'\u0012\u0006\u0010!\u001a\u00020\u0005\u0012\u0006\u0010\"\u001a\u00020\u0005\u0012\u0006\u0010\u001f\u001a\u00020\u001e\u0012\u0006\u0010$\u001a\u00020#\u00a2\u0006\u0004\b%\u0010&J\u0011\u0010\u0003\u001a\u0004\u0018\u00010\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001d\u0010\t\u001a\u00020\b2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\u0004\b\t\u0010\nJ\u000f\u0010\u000b\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\r\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\r\u0010\fJ\u0017\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J/\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\u0016\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J-\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u0015\u00a2\u0006\u0004\b\u001c\u0010\u0018J\u000f\u0010\u001d\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u001d\u0010\fR\u0014\u0010\u001f\u001a\u00020\u001e8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001f\u0010 \u00a8\u0006("}, d2={"Lcom/cobblemon/mod/common/client/gui/pc/StorageSlot;", "Lnet/minecraft/client/gui/components/Button;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "", "mouseX", "mouseY", "", "isHovered", "(II)Z", "isSelected", "()Z", "isStationary", "Lnet/minecraft/client/sounds/SoundManager;", "soundManager", "", "playDownSound", "(Lnet/minecraft/client/sounds/SoundManager;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "delta", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "posX", "posY", "partialTicks", "renderSlot", "shouldRender", "Lcom/cobblemon/mod/common/client/gui/pc/StorageWidget;", "parent", "Lcom/cobblemon/mod/common/client/gui/pc/StorageWidget;", "x", "y", "Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;", "onPress", "<init>", "(IILcom/cobblemon/mod/common/client/gui/pc/StorageWidget;Lnet/minecraft/client/gui/components/Button$OnPress;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nStorageSlot.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StorageSlot.kt\ncom/cobblemon/mod/common/client/gui/pc/StorageSlot\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,211:1\n2624#2,3:212\n1774#2,4:215\n*S KotlinDebug\n*F\n+ 1 StorageSlot.kt\ncom/cobblemon/mod/common/client/gui/pc/StorageSlot\n*L\n136#1:212,3\n158#1:215,4\n*E\n"})
public class StorageSlot
extends Button {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final StorageWidget parent;
    public static final int SIZE = 25;
    @NotNull
    private static final ResourceLocation genderIconMale = MiscUtilsKt.cobblemonResource("textures/gui/pc/gender_icon_male.png");
    @NotNull
    private static final ResourceLocation genderIconFemale = MiscUtilsKt.cobblemonResource("textures/gui/pc/gender_icon_female.png");
    @NotNull
    private static final ResourceLocation selectPointerResource = MiscUtilsKt.cobblemonResource("textures/gui/pc/pc_pointer.png");
    @NotNull
    private static final ResourceLocation slotOverlayResource = MiscUtilsKt.cobblemonResource("textures/gui/pc/pc_slot_overlay.png");
    @NotNull
    private static final ResourceLocation slotOverlayPastureIconResource = MiscUtilsKt.cobblemonResource("textures/gui/pasture/pc_slot_icon_pasture.png");
    @NotNull
    private static final ResourceLocation slotOverlayMoveIconResource = MiscUtilsKt.cobblemonResource("textures/gui/pasture/pc_slot_icon_move.png");

    public StorageSlot(int x, int y, @NotNull StorageWidget parent, @NotNull Button.OnPress onPress) {
        Intrinsics.checkNotNullParameter((Object)((Object)parent), (String)"parent");
        Intrinsics.checkNotNullParameter((Object)onPress, (String)"onPress");
        super(x, y, 25, 25, (Component)Component.m_237113_((String)"StorageSlot"), onPress, Button.f_252438_);
        this.parent = parent;
    }

    public void m_7435_(@NotNull SoundManager soundManager) {
        Intrinsics.checkNotNullParameter((Object)soundManager, (String)"soundManager");
    }

    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (this.shouldRender()) {
            this.renderSlot(context, this.m_252754_(), this.m_252907_(), delta);
        }
    }

    /*
     * Unable to fully structure code
     */
    public final void renderSlot(@NotNull GuiGraphics context, int posX, int posY, float partialTicks) {
        block16: {
            block15: {
                Intrinsics.checkNotNullParameter((Object)context, (String)"context");
                v0 = this.getPokemon();
                if (v0 == null) {
                    return;
                }
                pokemon = v0;
                matrices = context.m_280168_();
                context.m_280588_(posX - 2, posY + 2, posX + 25 + 4, posY + 25 + 4);
                matrices.m_85836_();
                matrices.m_85837_((double)posX + 12.5, (double)posY + 1.0, 0.0);
                matrices.m_85841_(2.5f, 2.5f, 1.0f);
                v1 = pokemon.asRenderablePokemon();
                Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
                PokemonGuiUtilsKt.drawProfilePokemon(v1, matrices, QuaternionUtilsKt.fromEulerXYZDegrees(new Quaternionf(), new Vector3f(13.0f, 35.0f, 0.0f)), null, partialTicks, 4.5f);
                matrices.m_85849_();
                context.m_280618_();
                matrices.m_85836_();
                matrices.m_85837_(0.0, 0.0, 100.0);
                var8_7 = new Object[]{pokemon.getLevel()};
                var7_11 = LocalizationUtilsKt.lang("ui.lv.number", var8_7);
                var8_8 = posX + 1;
                var9_12 = posY + 1;
                Intrinsics.checkNotNullExpressionValue((Object)var7_11, (String)"lang(\"ui.lv.number\", pokemon.level)");
                RenderHelperKt.drawScaledText$default(context, null, var7_11, var8_8, var9_12, 0.5f, null, 0, 0, false, true, null, null, 7106, null);
                if (pokemon.getGender() != Gender.GENDERLESS) {
                    var7_11 = pokemon.getGender() == Gender.MALE ? StorageSlot.genderIconMale : StorageSlot.genderIconFemale;
                    var8_9 = (float)(posX + 21) / 0.5f;
                    var9_13 = (float)(posY + 1) / 0.5f;
                    GuiUtilsKt.blitk$default(matrices, (ResourceLocation)var7_11, Float.valueOf(var8_9), Float.valueOf(var9_13), 8, 6, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
                }
                if (!(heldItem = pokemon.heldItemNoCopy$common()).m_41619_()) {
                    RenderHelperKt.renderScaledGuiItemIcon$default(heldItem, (double)posX + 16.0, (double)posY + 16.0, 0.5, 0.0f, matrices, 16, null);
                }
                matrices.m_85849_();
                matrices.m_85836_();
                matrices.m_85837_(0.0, 0.0, 500.0);
                config = this.parent.getPcGui().getConfiguration();
                if (pokemon.getTetheringId() == null) break block16;
                if (this.isStationary()) {
                    var9_14 = StorageSlot.slotOverlayResource;
                    GuiUtilsKt.blitk$default(matrices, var9_14, posX, posY, 25, 25, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
                }
                if (!(config instanceof PasturePCGUIConfiguration)) ** GOTO lbl-1000
                $this$none$iv = ((PasturePCGUIConfiguration)config).getPasturedPokemon().get();
                $i$f$none = false;
                if ($this$none$iv instanceof Collection && ((Collection)$this$none$iv).isEmpty()) {
                    v2 = true;
                } else {
                    for (T element$iv : $this$none$iv) {
                        it = (OpenPasturePacket.PasturePokemonDataDTO)element$iv;
                        $i$a$-none-StorageSlot$renderSlot$opacity$1 = false;
                        if (!Intrinsics.areEqual((Object)it.getPokemonId(), (Object)pokemon.getUuid())) continue;
                        v2 = false;
                        break block15;
                    }
                    v2 = true;
                }
            }
            if (v2) {
                v3 = 0.5f;
            } else lbl-1000:
            // 2 sources

            {
                v3 = 1.0f;
            }
            opacity = v3;
            $this$none$iv = ((double)posX + 7.5) / (double)0.5f;
            var12_27 = ((double)posY + 7.5) / (double)0.5f;
            it = StorageSlot.slotOverlayPastureIconResource;
            GuiUtilsKt.blitk$default(matrices, (ResourceLocation)it, $this$none$iv, var12_27, 20, 20, null, null, null, null, null, null, null, null, Float.valueOf(opacity), false, 0.5f, 49088, null);
        }
        if (this.m_198029_()) {
            if (config instanceof PasturePCGUIConfiguration && pokemon.getTetheringId() == null && this.isStationary() && ((PasturePCGUIConfiguration)config).getPermissions().getCanPasture() && ((Boolean)config.getCanSelect().invoke((Object)pokemon)).booleanValue() && ((PasturePCGUIConfiguration)config).getPasturedPokemon().get().size() < ((PasturePCGUIConfiguration)config).getLimit()) {
                $this$count$iv = ((PasturePCGUIConfiguration)config).getPasturedPokemon().get();
                $i$f$count = false;
                if ($this$count$iv instanceof Collection && ((Collection)$this$count$iv).isEmpty()) {
                    v4 = 0;
                } else {
                    count$iv = 0;
                    for (T element$iv : $this$count$iv) {
                        it = (OpenPasturePacket.PasturePokemonDataDTO)element$iv;
                        $i$a$-count-StorageSlot$renderSlot$1 = false;
                        v5 = it.getPlayerId();
                        v6 = Minecraft.m_91087_().f_91074_;
                        Intrinsics.checkNotNull((Object)v6);
                        if (!Intrinsics.areEqual((Object)v5, (Object)v6.m_20148_()) || ++count$iv >= 0) continue;
                        CollectionsKt.throwCountOverflow();
                    }
                    v4 = count$iv;
                }
                if (v4 < ((PasturePCGUIConfiguration)config).getPermissions().getMaxPokemon()) {
                    var9_16 = StorageSlot.slotOverlayResource;
                    GuiUtilsKt.blitk$default(matrices, var9_16, posX, posY, 25, 25, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
                    var9_17 = ((double)posX + 7.5) / (double)0.5f;
                    var11_24 = ((double)posY + 7.5) / (double)0.5f;
                    var13_29 = StorageSlot.slotOverlayMoveIconResource;
                    GuiUtilsKt.blitk$default(matrices, var13_29, var9_17, var11_24, 20, 20, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
                }
            }
            var9_18 = StorageSlot.selectPointerResource;
            var10_22 = (float)(posX + 10) / 0.5f;
            var11_25 = (float)(posY - 3) / 0.5f - (float)this.parent.getPcGui().getSelectPointerOffsetY();
            GuiUtilsKt.blitk$default(matrices, var9_18, Float.valueOf(var10_22), Float.valueOf(var11_25), 8, 11, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        }
        matrices.m_85849_();
    }

    public boolean isStationary() {
        return true;
    }

    @Nullable
    public Pokemon getPokemon() {
        return null;
    }

    public boolean m_198029_() {
        return Intrinsics.areEqual((Object)this.getPokemon(), (Object)this.parent.getPcGui().getPreviewPokemon$common());
    }

    public boolean shouldRender() {
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isHovered(int mouseX, int mouseY) {
        float f = this.m_252754_();
        float f2 = (float)this.m_252754_() + (float)25;
        float f3 = mouseX;
        if (!(f <= f3)) return false;
        if (!(f3 <= f2)) return false;
        boolean bl = true;
        if (!bl) return false;
        f = this.m_252907_();
        f2 = (float)this.m_252907_() + (float)25;
        f3 = mouseY;
        if (!(f <= f3)) return false;
        if (!(f3 <= f2)) return false;
        return true;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00058\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0007R\u0014\u0010\t\u001a\u00020\u00058\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0007R\u0014\u0010\n\u001a\u00020\u00058\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\n\u0010\u0007R\u0014\u0010\u000b\u001a\u00020\u00058\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\u0007R\u0014\u0010\f\u001a\u00020\u00058\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\u0007\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/client/gui/pc/StorageSlot$Companion;", "", "", "SIZE", "I", "Lnet/minecraft/resources/ResourceLocation;", "genderIconFemale", "Lnet/minecraft/resources/ResourceLocation;", "genderIconMale", "selectPointerResource", "slotOverlayMoveIconResource", "slotOverlayPastureIconResource", "slotOverlayResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

