/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractSelectionList$Entry
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.gui.components.ObjectSelectionList$Entry
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionDisplay;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.PokemonGuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.TypeIcon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.SummaryButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.common.SummaryScrollList;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.QuaternionUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 \u001c2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u001c\u001dB\u001f\u0012\u0006\u0010\u0018\u001a\u00020\u0004\u0012\u0006\u0010\u0019\u001a\u00020\u0004\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J/\u0010\u000e\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u0016\u0010\u0011\u001a\u00020\u00108\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/EvolutionSelectScreen;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/common/SummaryScrollList;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/EvolutionSelectScreen$EvolveSlot;", "entry", "", "addEntry", "(Lcom/cobblemon/mod/common/client/gui/summary/widgets/EvolutionSelectScreen$EvolveSlot;)I", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "mouseX", "mouseY", "", "partialTicks", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "", "entriesCreated", "Z", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "x", "y", "<init>", "(IILcom/cobblemon/mod/common/pokemon/Pokemon;)V", "Companion", "EvolveSlot", "common"})
@SourceDebugExtension(value={"SMAP\nEvolutionSelectScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EvolutionSelectScreen.kt\ncom/cobblemon/mod/common/client/gui/summary/widgets/EvolutionSelectScreen\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,157:1\n1549#2:158\n1620#2,3:159\n1855#2,2:162\n*S KotlinDebug\n*F\n+ 1 EvolutionSelectScreen.kt\ncom/cobblemon/mod/common/client/gui/summary/widgets/EvolutionSelectScreen\n*L\n58#1:158\n58#1:159,3\n58#1:162,2\n*E\n"})
public final class EvolutionSelectScreen
extends SummaryScrollList<EvolveSlot> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Pokemon pokemon;
    private boolean entriesCreated;
    public static final int SLOT_HEIGHT = 25;
    public static final int SLOT_SPACING = 5;
    public static final int PORTRAIT_DIAMETER = 25;
    @NotNull
    private static final ResourceLocation slotResource = MiscUtils.cobblemonResource("textures/gui/summary/summary_evolve_slot.png");
    @NotNull
    private static final ResourceLocation buttonResource = MiscUtils.cobblemonResource("textures/gui/summary/summary_evolve_select_button.png");

    public EvolutionSelectScreen(int x, int y, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.evolution", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.evolution\")");
        super(x, y, mutableComponent, 30);
        this.pokemon = pokemon;
    }

    @NotNull
    public final Pokemon getPokemon() {
        return this.pokemon;
    }

    public int addEntry(@NotNull EvolveSlot entry) {
        Intrinsics.checkNotNullParameter((Object)((Object)entry), (String)"entry");
        return super.m_7085_((AbstractSelectionList.Entry)entry);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float partialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (!this.entriesCreated) {
            void $this$forEach$iv;
            void $this$mapTo$iv$iv;
            this.entriesCreated = true;
            Iterable $this$map$iv = this.pokemon.getEvolutionProxy().client();
            boolean $i$f$map = false;
            Iterable iterable = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                EvolutionDisplay evolutionDisplay = (EvolutionDisplay)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(new EvolveSlot(this.pokemon, (EvolutionDisplay)it));
            }
            $this$map$iv = (List)destination$iv$iv;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                EvolveSlot entry = (EvolveSlot)((Object)element$iv);
                boolean bl = false;
                this.addEntry(entry);
            }
        }
        super.m_88315_(context, mouseX, mouseY, partialTicks);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004R\u0014\u0010\b\u001a\u00020\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\n\u0010\t\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/EvolutionSelectScreen$Companion;", "", "", "PORTRAIT_DIAMETER", "I", "SLOT_HEIGHT", "SLOT_SPACING", "Lnet/minecraft/resources/ResourceLocation;", "buttonResource", "Lnet/minecraft/resources/ResourceLocation;", "slotResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0017\u0012\u0006\u0010*\u001a\u00020)\u0012\u0006\u0010\"\u001a\u00020!\u00a2\u0006\u0004\b1\u00102J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J'\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ_\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\u0017H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u0017\u0010\u001d\u001a\u00020\u001c8\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\u0014\u0010\"\u001a\u00020!8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\"\u0010#R\u0017\u0010%\u001a\u00020$8\u0006\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(R\u0014\u0010*\u001a\u00020)8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b*\u0010+R\u0017\u0010-\u001a\u00020,8\u0006\u00a2\u0006\f\n\u0004\b-\u0010.\u001a\u0004\b/\u00100\u00a8\u00063"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/EvolutionSelectScreen$EvolveSlot;", "Lnet/minecraft/client/gui/widget/AlwaysSelectedEntryListWidget$Entry;", "Lnet/minecraft/network/chat/MutableComponent;", "getNarration", "()Lnet/minecraft/network/chat/MutableComponent;", "", "d", "e", "", "i", "", "mouseClicked", "(DDI)Z", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "index", "rowTop", "rowLeft", "rowWidth", "rowHeight", "mouseX", "mouseY", "isHovered", "", "partialTicks", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIIIIIIZF)V", "Lnet/minecraft/client/Minecraft;", "client", "Lnet/minecraft/client/Minecraft;", "getClient", "()Lnet/minecraft/client/Minecraft;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;", "evolution", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;", "Lcom/cobblemon/mod/common/pokemon/FormData;", "form", "Lcom/cobblemon/mod/common/pokemon/FormData;", "getForm", "()Lcom/cobblemon/mod/common/pokemon/FormData;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/client/gui/summary/SummaryButton;", "selectButton", "Lcom/cobblemon/mod/common/client/gui/summary/SummaryButton;", "getSelectButton", "()Lcom/cobblemon/mod/common/client/gui/summary/SummaryButton;", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;)V", "common"})
    public static final class EvolveSlot
    extends ObjectSelectionList.Entry<EvolveSlot> {
        @NotNull
        private final Pokemon pokemon;
        @NotNull
        private final EvolutionDisplay evolution;
        @NotNull
        private final Minecraft client;
        @NotNull
        private final FormData form;
        @NotNull
        private final SummaryButton selectButton;

        public EvolveSlot(@NotNull Pokemon pokemon, @NotNull EvolutionDisplay evolution) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            Intrinsics.checkNotNullParameter((Object)evolution, (String)"evolution");
            this.pokemon = pokemon;
            this.evolution = evolution;
            Minecraft minecraft = Minecraft.m_91087_();
            Intrinsics.checkNotNullExpressionValue((Object)minecraft, (String)"getInstance()");
            this.client = minecraft;
            this.form = this.evolution.getSpecies().getForm(this.evolution.getAspects());
            Number number = 40;
            Number number2 = 10;
            Button.OnPress onPress = arg_0 -> EvolveSlot.selectButton$lambda$0(this, arg_0);
            MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.evolve", new Object[0]);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.evolve\")");
            this.selectButton = new SummaryButton(0.0f, 0.0f, number, number2, onPress, mutableComponent, buttonResource, null, null, null, false, false, true, false, 0.5f, 3968, null);
        }

        @NotNull
        public final Minecraft getClient() {
            return this.client;
        }

        @NotNull
        public final FormData getForm() {
            return this.form;
        }

        @NotNull
        public final SummaryButton getSelectButton() {
            return this.selectButton;
        }

        @NotNull
        public MutableComponent getNarration() {
            return this.evolution.getSpecies().getTranslatedName();
        }

        public void m_6311_(@NotNull GuiGraphics context, int index, int rowTop, int rowLeft, int rowWidth, int rowHeight, int mouseX, int mouseY, boolean isHovered, float partialTicks) {
            Intrinsics.checkNotNullParameter((Object)context, (String)"context");
            int x = rowLeft - 3;
            int y = rowTop;
            PoseStack matrices = context.m_280168_();
            Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
            GuiUtilsKt.blitk$default(matrices, slotResource, x, y, 25, rowWidth, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
            RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), TextKt.bold(this.evolution.getSpecies().getTranslatedName()), x + 4, y + 2, 0.0f, null, 0, 0, false, true, null, null, 7136, null);
            int n = x + 12;
            double d = (double)y + 13.5;
            ElementalType elementalType = this.form.getPrimaryType();
            ElementalType elementalType2 = this.form.getSecondaryType();
            new TypeIcon(n, d, elementalType, elementalType2, true, true, 9.5f, 5.0f, 0.0f, 256, null).render(context);
            this.selectButton.setPosFloat((float)x + 23.0f, (float)y + 13.0f);
            this.selectButton.m_88315_(context, mouseX, mouseY, partialTicks);
            matrices.m_85836_();
            matrices.m_85837_((double)(x + 12) + 65.0, (double)y - 5.0, 0.0);
            matrices.m_85841_(2.5f, 2.5f, 1.0f);
            ResourceLocation resourceLocation = this.evolution.getSpecies().getResourceIdentifier();
            Set<String> set2 = this.evolution.getAspects();
            Quaternionf quaternionf = QuaternionUtilsKt.fromEulerXYZDegrees(new Quaternionf(), new Vector3f(13.0f, 35.0f, 0.0f));
            PokemonGuiUtilsKt.drawProfilePokemon(resourceLocation, set2, matrices, quaternionf, null, partialTicks, 6.0f);
            matrices.m_85849_();
        }

        public boolean m_6375_(double d, double e, int i) {
            if (this.selectButton.m_274382_()) {
                this.selectButton.m_5691_();
                return true;
            }
            return false;
        }

        private static final void selectButton$lambda$0(EvolveSlot this$0, Button it) {
            Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
            LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
            if (localPlayer != null) {
                localPlayer.m_108763_();
            }
            LocalPlayer localPlayer2 = Minecraft.m_91087_().f_91074_;
            if (localPlayer2 != null) {
                Object[] objectArray = new Object[]{this$0.pokemon.getDisplayName(), this$0.evolution.getSpecies().getTranslatedName()};
                localPlayer2.m_213846_((Component)LocalizationUtilsKt.lang("ui.evolve.into", objectArray));
            }
            this$0.pokemon.getEvolutionProxy().client().start((EvolutionDisplay)((EvolutionLike)this$0.evolution));
        }
    }
}

