/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.partyselect;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.PokemonGuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.partyselect.PartySelectGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.PartySlotWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonFloatingState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.QuaternionUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Set;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 >2\u00020\u0001:\u0001>B_\u0012\u0006\u00108\u001a\u00020\t\u0012\u0006\u00109\u001a\u00020\t\u0012\u0006\u0010,\u001a\u00020+\u0012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010\u0012\u0006\u0010\u0016\u001a\u00020\t\u0012\u0006\u0010$\u001a\u00020\t\u0012\u0006\u0010 \u001a\u00020\u001f\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u001a\u0012\u0006\u0010'\u001a\u00020&\u0012\u0006\u0010;\u001a\u00020:\u00a2\u0006\u0004\b<\u0010=J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J/\u0010\u000e\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u001d\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001b\u001a\u00020\u001a8\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR\u0017\u0010 \u001a\u00020\u001f8\u0006\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#R\u0017\u0010$\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b$\u0010\u0017\u001a\u0004\b%\u0010\u0019R\u0017\u0010'\u001a\u00020&8\u0006\u00a2\u0006\f\n\u0004\b'\u0010(\u001a\u0004\b)\u0010*R\u0017\u0010,\u001a\u00020+8\u0006\u00a2\u0006\f\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/R\u0014\u00101\u001a\u0002008\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b1\u00102R\u0017\u00104\u001a\u0002038\u0006\u00a2\u0006\f\n\u0004\b4\u00105\u001a\u0004\b6\u00107\u00a8\u0006?"}, d2={"Lcom/cobblemon/mod/common/client/gui/interact/partyselect/PartySlotButton;", "Lnet/minecraft/client/gui/components/Button;", "Lnet/minecraft/client/sounds/SoundManager;", "soundManager", "", "playDownSound", "(Lnet/minecraft/client/sounds/SoundManager;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "pMouseX", "pMouseY", "", "pPartialTicks", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "", "", "aspects", "Ljava/util/Set;", "getAspects", "()Ljava/util/Set;", "currentHealth", "I", "getCurrentHealth", "()I", "", "enabled", "Z", "getEnabled", "()Z", "Lnet/minecraft/world/item/ItemStack;", "heldItem", "Lnet/minecraft/world/item/ItemStack;", "getHeldItem", "()Lnet/minecraft/world/item/ItemStack;", "maxHealth", "getMaxHealth", "Lcom/cobblemon/mod/common/client/gui/interact/partyselect/PartySelectGUI;", "parent", "Lcom/cobblemon/mod/common/client/gui/interact/partyselect/PartySelectGUI;", "getParent", "()Lcom/cobblemon/mod/common/client/gui/interact/partyselect/PartySelectGUI;", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "pokemon", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getPokemon", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "renderablePokemon", "Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonFloatingState;", "state", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonFloatingState;", "getState", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonFloatingState;", "x", "y", "Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;", "onPress", "<init>", "(IILcom/cobblemon/mod/common/api/pokemon/PokemonProperties;Ljava/util/Set;IILnet/minecraft/world/item/ItemStack;ZLcom/cobblemon/mod/common/client/gui/interact/partyselect/PartySelectGUI;Lnet/minecraft/client/gui/components/Button$OnPress;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nPartySlotButton.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PartySlotButton.kt\ncom/cobblemon/mod/common/client/gui/interact/partyselect/PartySlotButton\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,202:1\n1#2:203\n*E\n"})
public final class PartySlotButton
extends Button {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final PokemonProperties pokemon;
    @NotNull
    private final Set<String> aspects;
    private final int currentHealth;
    private final int maxHealth;
    @NotNull
    private final ItemStack heldItem;
    private final boolean enabled;
    @NotNull
    private final PartySelectGUI parent;
    @NotNull
    private final PokemonFloatingState state;
    @NotNull
    private final RenderablePokemon renderablePokemon;
    @NotNull
    private static final ResourceLocation slotResource = MiscUtilsKt.cobblemonResource("textures/gui/interact/party_select_slot.png");
    @NotNull
    private static final ResourceLocation slotFaintedResource = MiscUtilsKt.cobblemonResource("textures/gui/interact/party_select_slot_fainted.png");
    public static final int WIDTH = 69;
    public static final int HEIGHT = 27;
    public static final float SCALE = 0.5f;

    /*
     * WARNING - void declaration
     */
    public PartySlotButton(int x, int y, @NotNull PokemonProperties pokemon, @NotNull Set<String> aspects, int currentHealth, int maxHealth, @NotNull ItemStack heldItem2, boolean enabled, @NotNull PartySelectGUI parent, @NotNull Button.OnPress onPress) {
        void it;
        RenderablePokemon renderablePokemon;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        Intrinsics.checkNotNullParameter((Object)heldItem2, (String)"heldItem");
        Intrinsics.checkNotNullParameter((Object)((Object)parent), (String)"parent");
        Intrinsics.checkNotNullParameter((Object)onPress, (String)"onPress");
        super(x, y, 69, 27, (Component)Component.m_237113_((String)"Pokemon"), onPress, PartySlotButton::_init_$lambda$0);
        this.pokemon = pokemon;
        this.aspects = aspects;
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
        this.heldItem = heldItem2;
        this.enabled = enabled;
        this.parent = parent;
        this.state = new PokemonFloatingState();
        RenderablePokemon renderablePokemon2 = renderablePokemon = this.pokemon.asRenderablePokemon();
        PartySlotButton partySlotButton = this;
        boolean bl = false;
        it.setAspects(this.aspects);
        partySlotButton.renderablePokemon = renderablePokemon;
    }

    public /* synthetic */ PartySlotButton(int n, int n2, PokemonProperties pokemonProperties, Set set2, int n3, int n4, ItemStack itemStack, boolean bl, PartySelectGUI partySelectGUI, Button.OnPress onPress, int n5, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n5 & 0x80) != 0) {
            bl = true;
        }
        this(n, n2, pokemonProperties, set2, n3, n4, itemStack, bl, partySelectGUI, onPress);
    }

    @NotNull
    public final PokemonProperties getPokemon() {
        return this.pokemon;
    }

    @NotNull
    public final Set<String> getAspects() {
        return this.aspects;
    }

    public final int getCurrentHealth() {
        return this.currentHealth;
    }

    public final int getMaxHealth() {
        return this.maxHealth;
    }

    @NotNull
    public final ItemStack getHeldItem() {
        return this.heldItem;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    @NotNull
    public final PartySelectGUI getParent() {
        return this.parent;
    }

    @NotNull
    public final PokemonFloatingState getState() {
        return this.state;
    }

    public void m_88315_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        this.f_93622_ = pMouseX >= this.m_252754_() && pMouseY >= this.m_252907_() && pMouseX < this.m_252754_() + this.f_93618_ && pMouseY < this.m_252907_() + this.f_93619_ && this.enabled;
        double alpha = this.enabled ? 1.0 : 0.5;
        PoseStack matrices = context.m_280168_();
        Object object = (float)this.currentHealth <= 0.0f ? slotFaintedResource : slotResource;
        int n = this.m_252754_();
        int n2 = this.m_252907_();
        int n3 = this.f_93618_;
        int n4 = this.f_93619_;
        int n5 = this.f_93622_ ? this.f_93619_ : 0;
        int n6 = this.f_93619_ * 2;
        Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
        GuiUtilsKt.blitk$default(matrices, object, n, n2, n4, n3, null, n5, null, n6, null, null, null, null, alpha, false, 0.0f, 113984, null);
        context.m_280168_().m_85836_();
        context.m_280168_().m_85837_((double)this.m_252754_() + (double)13, (double)this.m_252907_() - (double)2, 0.0);
        object = this.renderablePokemon;
        PoseStack poseStack = context.m_280168_();
        Quaternionf quaternionf = QuaternionUtilsKt.fromEulerXYZDegrees(new Quaternionf(), new Vector3f(13.0f, 35.0f, 0.0f));
        Object[] objectArray = this.state;
        float f = !this.f_93622_ ? 0.0f : pPartialTicks;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        PokemonGuiUtilsKt.drawProfilePokemon((RenderablePokemon)object, poseStack, quaternionf, (PoseableEntityState)objectArray, f, 10.0f);
        context.m_280168_().m_85849_();
        String string = this.pokemon.getPokeball();
        Intrinsics.checkNotNull((Object)string);
        ResourceLocation ballIcon = MiscUtilsKt.cobblemonResource("textures/gui/ball/" + ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(string, null, 1, null).m_135815_() + ".png");
        int ballHeight = 22;
        GuiUtilsKt.blitk$default(matrices, ballIcon, Float.valueOf((float)(this.m_252754_() - 2) / 0.5f), Float.valueOf((float)(this.m_252907_() - 3) / 0.5f), ballHeight, 18, null, null, null, ballHeight * 2, null, null, null, null, null, false, 0.5f, 64960, null);
        objectArray = new Object[1];
        Intrinsics.checkNotNull((Object)this.pokemon.getLevel());
        quaternionf = LocalizationUtilsKt.lang("ui.lv.number", objectArray);
        int n7 = this.m_252754_() + 24;
        double d = (double)this.m_252907_() + 6.5;
        Intrinsics.checkNotNullExpressionValue((Object)quaternionf, (String)"lang(\"ui.lv.number\", pokemon.level!!)");
        RenderHelperKt.drawScaledText$default(context, null, (MutableComponent)quaternionf, n7, d, 0.5f, null, 0, 0, false, true, null, null, 7106, null);
        MutableComponent mutableComponent = this.pokemon.getNickname();
        if (mutableComponent == null) {
            mutableComponent = this.renderablePokemon.getSpecies().getTranslatedName();
        }
        MutableComponent displayName = mutableComponent;
        MutableComponent mutableComponent2 = displayName.m_6881_();
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"displayName.copy()");
        RenderHelperKt.drawScaledText$default(context, null, mutableComponent2, this.m_252754_() + 24, (double)this.m_252907_() + 12.5, 0.5f, null, 0, 0, false, false, null, null, 8130, null);
        if (this.pokemon.getAspects().contains("male") || this.pokemon.getAspects().contains("female")) {
            GuiUtilsKt.blitk$default(matrices, this.pokemon.getAspects().contains("male") ? PartySlotWidget.Companion.getGenderIconMale() : PartySlotWidget.Companion.getGenderIconFemale(), ((double)this.m_252754_() + 60.5) / (double)0.5f, ((double)this.m_252907_() + 12.5) / (double)0.5f, 7, 5, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        }
        float hpRatio = (float)this.currentHealth / (float)this.maxHealth;
        int barWidthMax = 65;
        float barWidth = hpRatio * (float)barWidthMax;
        Pair pair = RenderHelperKt.getDepletableRedGreen$default(hpRatio, 0.0f, 0.0f, 6, null);
        float red = ((Number)pair.component1()).floatValue();
        float green = ((Number)pair.component2()).floatValue();
        ResourceLocation resourceLocation = CobblemonResources.INSTANCE.getWHITE();
        int n8 = this.m_252754_() + 1;
        int n9 = this.m_252907_() + 20;
        float f2 = barWidth / hpRatio;
        float f3 = (float)barWidthMax - barWidth;
        float f4 = red * 0.8f;
        float f5 = green * 0.8f;
        GuiUtilsKt.blitk$default(matrices, resourceLocation, n8, n9, 1, Float.valueOf(barWidth), Float.valueOf(f3), null, Float.valueOf(f2), null, null, Float.valueOf(f4), Float.valueOf(f5), Float.valueOf(0.27f), null, false, 0.0f, 116352, null);
        RenderHelperKt.drawScaledText$default(context, null, TextKt.text(this.currentHealth + "/" + this.maxHealth), this.m_252754_() + 14, (double)this.m_252907_() + 22.5, 0.5f, null, 0, 0, true, false, null, null, 7618, null);
        String status = this.pokemon.getStatus();
        if (hpRatio > 0.0f && status != null) {
            GuiUtilsKt.blitk$default(matrices, MiscUtilsKt.cobblemonResource("textures/gui/interact/party_select_status_" + status + ".png"), this.m_252754_() + 27, this.m_252907_() + 22, 5, 37, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
            MutableComponent mutableComponent3 = LocalizationUtilsKt.lang("ui.status." + status, new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent3, (String)"lang(\"ui.status.$status\")");
            MutableComponent mutableComponent4 = TextKt.bold(mutableComponent3);
            double d2 = (double)this.m_252754_() + 32.5;
            double d3 = (double)this.m_252907_() + 22.5;
            RenderHelperKt.drawScaledText$default(context, null, mutableComponent4, d2, d3, 0.5f, null, 0, 0, false, true, null, null, 7106, null);
        }
        if (!this.heldItem.m_41619_()) {
            RenderHelperKt.renderScaledGuiItemIcon$default(this.heldItem, (double)this.m_252754_() + 14.0, (double)this.m_252907_() + 9.5, 0.5, 0.0f, matrices, 16, null);
        }
    }

    public void m_7435_(@NotNull SoundManager soundManager) {
        Intrinsics.checkNotNullParameter((Object)soundManager, (String)"soundManager");
    }

    private static final MutableComponent _init_$lambda$0(Supplier it) {
        return TextKt.text("");
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0004R\u0014\u0010\n\u001a\u00020\t8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\t8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\u000b\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/client/gui/interact/partyselect/PartySlotButton$Companion;", "", "", "HEIGHT", "I", "", "SCALE", "F", "WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "slotFaintedResource", "Lnet/minecraft/resources/ResourceLocation;", "slotResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

