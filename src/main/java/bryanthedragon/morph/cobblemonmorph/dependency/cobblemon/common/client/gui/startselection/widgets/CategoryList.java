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
 *  net.minecraft.client.gui.components.ObjectSelectionList
 *  net.minecraft.client.gui.components.ObjectSelectionList$Entry
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.startselection.widgets;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.startselection.StarterSelectionScreen;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.starter.RenderableStarterCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u0000 12\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u000221Bg\u0012\u0006\u0010$\u001a\u00020\t\u0012\u0006\u0010#\u001a\u00020\t\u0012\u0006\u0010,\u001a\u00020\t\u0012\u0006\u0010-\u001a\u00020\t\u0012\u0006\u0010\u001e\u001a\u00020\t\u0012\u0006\u0010.\u001a\u00020\t\u0012\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u0006\u0012\u0006\u0010(\u001a\u00020\t\u0012\u0006\u0010*\u001a\u00020\t\u0012\b\b\u0002\u0010!\u001a\u00020 \u0012\u0006\u0010&\u001a\u00020%\u00a2\u0006\u0004\b/\u00100J\u000f\u0010\u0004\u001a\u00020\u0003H\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0019\u0010\u0007\u001a\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0006H\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\f\u001a\u00020\tH\u0014\u00a2\u0006\u0004\b\f\u0010\u000bJ/\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\tH\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\u001aR\u0016\u0010\u001c\u001a\u00020\u001b8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\t8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001e\u0010\u001fR\u0014\u0010!\u001a\u00020 8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b!\u0010\"R\u0014\u0010#\u001a\u00020\t8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b#\u0010\u001fR\u0014\u0010$\u001a\u00020\t8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b$\u0010\u001fR\u0014\u0010&\u001a\u00020%8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b&\u0010'R\u0017\u0010(\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b(\u0010\u001f\u001a\u0004\b)\u0010\u000bR\u0017\u0010*\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b*\u0010\u001f\u001a\u0004\b+\u0010\u000b\u00a8\u00063"}, d2={"Lcom/cobblemon/mod/common/client/gui/startselection/widgets/CategoryList;", "Lnet/minecraft/client/gui/components/ObjectSelectionList;", "Lcom/cobblemon/mod/common/client/gui/startselection/widgets/CategoryList$Category;", "", "correctSize", "()V", "", "createEntries", "()Ljava/util/List;", "", "getRowWidth", "()I", "getScrollbarPositionX", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "mouseX", "mouseY", "", "delta", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "n", "scale", "(I)I", "Lcom/cobblemon/mod/common/config/starter/RenderableStarterCategory;", "categories", "Ljava/util/List;", "", "entriesCreated", "Z", "entryWidth", "I", "Lnet/minecraft/client/Minecraft;", "minecraft", "Lnet/minecraft/client/Minecraft;", "paneHeight", "paneWidth", "Lcom/cobblemon/mod/common/client/gui/startselection/StarterSelectionScreen;", "starterSelectionScreen", "Lcom/cobblemon/mod/common/client/gui/startselection/StarterSelectionScreen;", "x", "getX", "y", "getY", "topOffset", "bottomOffset", "entryHeight", "<init>", "(IIIIIILjava/util/List;IILnet/minecraft/client/Minecraft;Lcom/cobblemon/mod/common/client/gui/startselection/StarterSelectionScreen;)V", "Companion", "Category", "common"})
@SourceDebugExtension(value={"SMAP\nCategoryList.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CategoryList.kt\ncom/cobblemon/mod/common/client/gui/startselection/widgets/CategoryList\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,144:1\n1549#2:145\n1620#2,3:146\n1855#2,2:149\n*S KotlinDebug\n*F\n+ 1 CategoryList.kt\ncom/cobblemon/mod/common/client/gui/startselection/widgets/CategoryList\n*L\n60#1:145\n60#1:146,3\n66#1:149,2\n*E\n"})
public final class CategoryList
extends ObjectSelectionList<Category> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final int paneWidth;
    private final int paneHeight;
    private final int entryWidth;
    @NotNull
    private final List<RenderableStarterCategory> categories;
    private final int x;
    private final int y;
    @NotNull
    private final Minecraft minecraft;
    @NotNull
    private final StarterSelectionScreen starterSelectionScreen;
    private boolean entriesCreated;
    private static final float CATEGORY_BUTTON_WIDTH = 51.5f;
    private static final float CATEGORY_BUTTON_HEIGHT = 16.0f;
    private static final float ENTRY_X_OFFSET = 10.0f;
    @NotNull
    private static final ResourceLocation categoryResource = MiscUtils.cobblemonResource("textures/gui/starterselection/starterselection_slot.png");

    public CategoryList(int paneWidth, int paneHeight, int topOffset, int bottomOffset, int entryWidth, int entryHeight, @NotNull List<RenderableStarterCategory> categories, int x, int y, @NotNull Minecraft minecraft, @NotNull StarterSelectionScreen starterSelectionScreen) {
        Intrinsics.checkNotNullParameter(categories, (String)"categories");
        Intrinsics.checkNotNullParameter((Object)minecraft, (String)"minecraft");
        Intrinsics.checkNotNullParameter((Object)((Object)starterSelectionScreen), (String)"starterSelectionScreen");
        super(minecraft, paneWidth, paneHeight, topOffset, bottomOffset, entryHeight);
        this.paneWidth = paneWidth;
        this.paneHeight = paneHeight;
        this.entryWidth = entryWidth;
        this.categories = categories;
        this.x = x;
        this.y = y;
        this.minecraft = minecraft;
        this.starterSelectionScreen = starterSelectionScreen;
        this.correctSize();
        this.m_93496_(false);
        this.m_93488_(false);
        this.m_93471_(false);
    }

    public /* synthetic */ CategoryList(int n, int n2, int n3, int n4, int n5, int n6, List list, int n7, int n8, Minecraft minecraft, StarterSelectionScreen starterSelectionScreen, int n9, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n9 & 0x200) != 0) {
            Minecraft minecraft2 = Minecraft.m_91087_();
            Intrinsics.checkNotNullExpressionValue((Object)minecraft2, (String)"getInstance()");
            minecraft = minecraft2;
        }
        this(n, n2, n3, n4, n5, n6, list, n7, n8, minecraft, starterSelectionScreen);
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }

    /*
     * WARNING - void declaration
     */
    private final List<Category> createEntries() {
        void $this$mapTo$iv$iv;
        Iterable $this$map$iv = this.categories;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            RenderableStarterCategory renderableStarterCategory = (RenderableStarterCategory)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(new Category((RenderableStarterCategory)it));
        }
        return (List)destination$iv$iv;
    }

    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (!this.entriesCreated) {
            Iterable $this$forEach$iv = this.createEntries();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                Category it = (Category)((Object)element$iv);
                boolean bl = false;
                this.m_7085_((AbstractSelectionList.Entry)it);
            }
            this.entriesCreated = true;
        }
        context.m_280588_(this.x, this.y, this.x + this.f_93388_, this.y + this.f_93389_);
        super.m_88315_(context, mouseX, mouseY, delta);
        context.m_280618_();
    }

    private final void correctSize() {
        this.m_93437_(this.paneWidth, this.paneHeight, this.y, this.y + this.paneHeight);
        this.m_93507_(this.x);
    }

    private final int scale(int n) {
        return (int)(this.f_93386_.m_91268_().m_85449_() * (double)n);
    }

    public int m_5759_() {
        return this.entryWidth;
    }

    protected int m_5756_() {
        return this.f_93393_ + this.f_93388_ - 5;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\f\u0012\b\u0012\u00060\u0000R\u00020\u00020\u0001B\u000f\u0012\u0006\u0010\u001c\u001a\u00020\u001b\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J'\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ_\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0007\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u0016H\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001c\u001a\u00020\u001b8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\u001d\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/client/gui/startselection/widgets/CategoryList$Category;", "Lnet/minecraft/client/gui/widget/AlwaysSelectedEntryListWidget$Entry;", "Lcom/cobblemon/mod/common/client/gui/startselection/widgets/CategoryList;", "Lnet/minecraft/network/chat/Component;", "getNarration", "()Lnet/minecraft/network/chat/Component;", "", "mouseX", "mouseY", "", "button", "", "mouseClicked", "(DDI)Z", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "index", "y", "x", "entryWidth", "entryHeight", "hovered", "", "tickDelta", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIIIIIIZF)V", "Lcom/cobblemon/mod/common/config/starter/RenderableStarterCategory;", "category", "Lcom/cobblemon/mod/common/config/starter/RenderableStarterCategory;", "<init>", "(Lcom/cobblemon/mod/common/client/gui/startselection/widgets/CategoryList;Lcom/cobblemon/mod/common/config/starter/RenderableStarterCategory;)V", "common"})
    public final class Category
    extends ObjectSelectionList.Entry<Category> {
        @NotNull
        private final RenderableStarterCategory category;

        public Category(RenderableStarterCategory category) {
            Intrinsics.checkNotNullParameter((Object)category, (String)"category");
            this.category = category;
        }

        public void m_6311_(@NotNull GuiGraphics context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            boolean isHovered;
            Intrinsics.checkNotNullParameter((Object)context, (String)"context");
            PoseStack matrices = context.m_280168_();
            boolean bl = isHovered = mouseX >= x && mouseY >= y && mouseX < x + entryWidth && mouseY < y + (entryHeight - 1);
            if (isHovered) {
                var13_13 = (float)x + 2.5f;
                var14_15 = categoryResource;
                Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
                GuiUtilsKt.blitk$default(matrices, var14_15, Float.valueOf(var13_13), y, Float.valueOf(16.0f), Float.valueOf(51.5f), null, null, null, null, null, Float.valueOf(0.75f), Float.valueOf(0.75f), Float.valueOf(0.75f), null, false, 0.0f, 116672, null);
            } else {
                var13_13 = (float)x + 2.5f;
                var14_15 = categoryResource;
                Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
                GuiUtilsKt.blitk$default(matrices, var14_15, Float.valueOf(var13_13), y, Float.valueOf(16.0f), Float.valueOf(51.5f), null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
            }
            MutableComponent mutableComponent = this.category.getDisplayNameText();
            int n = x + 28;
            float f = (float)y + 4.5f;
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"displayNameText");
            RenderHelperKt.drawScaledText$default(context, null, mutableComponent, n, Float.valueOf(f), 1.0f, null, 50, 0, true, true, null, null, 6466, null);
        }

        public boolean m_6375_(double mouseX, double mouseY, int button) {
            CategoryList.this.starterSelectionScreen.changeCategory(this.category);
            CategoryList.this.minecraft.m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)CobblemonSounds.GUI_CLICK, (float)1.0f));
            return true;
        }

        @NotNull
        public Component m_142172_() {
            Component component = Component.m_130674_((String)"Yes");
            Intrinsics.checkNotNullExpressionValue((Object)component, (String)"of(\"Yes\")");
            return component;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004R\u0014\u0010\b\u001a\u00020\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/client/gui/startselection/widgets/CategoryList$Companion;", "", "", "CATEGORY_BUTTON_HEIGHT", "F", "CATEGORY_BUTTON_WIDTH", "ENTRY_X_OFFSET", "Lnet/minecraft/resources/ResourceLocation;", "categoryResource", "Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

