/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
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
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.partyselect;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectPokemonDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.ExitButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.partyselect.PartySelectConfiguration;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.partyselect.PartySlotButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.party.PartyPokemonSelectedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.party.PartySelectCancelledPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 .2\u00020\u0001:\u0001.B'\b\u0016\u0012\u0006\u0010'\u001a\u00020&\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070(\u0012\u0006\u0010*\u001a\u00020)\u00a2\u0006\u0004\b+\u0010,B\u000f\u0012\u0006\u0010\"\u001a\u00020!\u00a2\u0006\u0004\b+\u0010-J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\r\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0002H\u0014\u00a2\u0006\u0004\b\u0006\u0010\u0004J\u0017\u0010\t\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u0015\u0010\r\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\r\u0010\u000eJ/\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0014H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u000f\u0010\u0019\u001a\u00020\u0018H\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u000f\u0010\u001b\u001a\u00020\u0018H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u001aR\"\u0010\u001c\u001a\u00020\u00188\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001a\"\u0004\b\u001f\u0010 R\u0017\u0010\"\u001a\u00020!8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\u00a8\u0006/"}, d2={"Lcom/cobblemon/mod/common/client/gui/interact/partyselect/PartySelectGUI;", "Lnet/minecraft/client/gui/screens/Screen;", "", "close", "()V", "closeProperly", "init", "Lcom/cobblemon/mod/common/api/callback/PartySelectPokemonDTO;", "pokemon", "onPress", "(Lcom/cobblemon/mod/common/api/callback/PartySelectPokemonDTO;)V", "Lnet/minecraft/sounds/SoundEvent;", "soundEvent", "playSound", "(Lnet/minecraft/sounds/SoundEvent;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "mouseX", "mouseY", "", "partialTicks", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "", "shouldCloseOnEsc", "()Z", "shouldPause", "closed", "Z", "getClosed", "setClosed", "(Z)V", "Lcom/cobblemon/mod/common/client/gui/interact/partyselect/PartySelectConfiguration;", "config", "Lcom/cobblemon/mod/common/client/gui/interact/partyselect/PartySelectConfiguration;", "getConfig", "()Lcom/cobblemon/mod/common/client/gui/interact/partyselect/PartySelectConfiguration;", "Lnet/minecraft/network/chat/MutableComponent;", "title", "", "Ljava/util/UUID;", "uuid", "<init>", "(Lnet/minecraft/network/chat/MutableComponent;Ljava/util/List;Ljava/util/UUID;)V", "(Lcom/cobblemon/mod/common/client/gui/interact/partyselect/PartySelectConfiguration;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nPartySelectGUI.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PartySelectGUI.kt\ncom/cobblemon/mod/common/client/gui/interact/partyselect/PartySelectGUI\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,179:1\n1864#2,3:180\n*S KotlinDebug\n*F\n+ 1 PartySelectGUI.kt\ncom/cobblemon/mod/common/client/gui/interact/partyselect/PartySelectGUI\n*L\n78#1:180,3\n*E\n"})
public final class PartySelectGUI
extends Screen {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final PartySelectConfiguration config;
    private boolean closed;
    public static final int WIDTH = 163;
    public static final int HEIGHT = 132;
    public static final float SCALE = 0.5f;
    @NotNull
    private static final ResourceLocation baseBackgroundResource = MiscUtils.cobblemonResource("textures/gui/interact/party_select.png");
    @NotNull
    private static final ResourceLocation spacerResource = MiscUtils.cobblemonResource("textures/gui/interact/party_select_spacer.png");

    public PartySelectGUI(@NotNull PartySelectConfiguration config) {
        Intrinsics.checkNotNullParameter((Object)config, (String)"config");
        super((Component)Component.m_237115_((String)"cobblemon.ui.interact.moveselect"));
        this.config = config;
    }

    @NotNull
    public final PartySelectConfiguration getConfig() {
        return this.config;
    }

    public final boolean getClosed() {
        return this.closed;
    }

    public final void setClosed(boolean bl) {
        this.closed = bl;
    }

    public PartySelectGUI(@NotNull MutableComponent title, final @NotNull List<? extends PartySelectPokemonDTO> pokemon, final @NotNull UUID uuid2) {
        Intrinsics.checkNotNullParameter((Object)title, (String)"title");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        this(new PartySelectConfiguration(title, pokemon, (Function1<? super PartySelectGUI, Unit>)((Function1)new Function1<PartySelectGUI, Unit>(){

            public final void invoke(@NotNull PartySelectGUI it) {
                Intrinsics.checkNotNullParameter((Object)((Object)it), (String)"it");
                CobblemonNetwork.INSTANCE.sendToServer(new PartySelectCancelledPacket(uuid2));
            }
        }), (Function1<? super PartySelectGUI, Unit>)((Function1)2.INSTANCE), (Function2<? super PartySelectGUI, ? super PartySelectPokemonDTO, Unit>)((Function2)new Function2<PartySelectGUI, PartySelectPokemonDTO, Unit>(){

            public final void invoke(@NotNull PartySelectGUI gui, @NotNull PartySelectPokemonDTO it) {
                Intrinsics.checkNotNullParameter((Object)((Object)gui), (String)"gui");
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                CobblemonNetwork.INSTANCE.sendToServer(new PartyPokemonSelectedPacket(uuid2, pokemon.indexOf(it)));
                gui.closeProperly();
            }
        })));
    }

    public final void closeProperly() {
        this.closed = true;
        this.m_7379_();
    }

    /*
     * WARNING - void declaration
     */
    protected void m_7856_() {
        int x = (this.f_96543_ - 163) / 2;
        int y = (this.f_96544_ - 132) / 2;
        Iterable $this$forEachIndexed$iv = this.config.getPokemon();
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            void pokemon;
            int n;
            if ((n = index$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            PartySelectPokemonDTO partySelectPokemonDTO = (PartySelectPokemonDTO)item$iv;
            int index = n;
            boolean bl = false;
            int slotX = x + 11;
            int slotY = y + 23;
            if (index > 0) {
                boolean isEven = index % 2 == 0;
                int offsetIndex = (index - (isEven ? 0 : 1)) / 2;
                int offsetX = isEven ? 0 : 74;
                int offsetY = isEven ? 0 : -8;
                slotX += offsetX;
                slotY += 31 * offsetIndex + offsetY;
            }
            int n2 = slotX;
            int n3 = slotY;
            PokemonProperties pokemonProperties = pokemon.getPokemonProperties();
            Set<String> set2 = pokemon.getAspects();
            ItemStack itemStack = pokemon.getHeldItem();
            int n4 = pokemon.getCurrentHealth();
            int n5 = pokemon.getMaxHealth();
            boolean bl2 = pokemon.getEnabled();
            Button.OnPress onPress = arg_0 -> PartySelectGUI.init$lambda$1$lambda$0(this, (PartySelectPokemonDTO)pokemon, arg_0);
            this.m_142416_((GuiEventListener)new PartySlotButton(n2, n3, pokemonProperties, set2, n4, n5, itemStack, bl2, this, onPress));
        }
        this.m_142416_((GuiEventListener)new ExitButton(x + 134, y + 116, arg_0 -> PartySelectGUI.init$lambda$2(this, arg_0)));
        super.m_7856_();
    }

    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float partialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        int x = (this.f_96543_ - 163) / 2;
        int y = (this.f_96544_ - 132) / 2;
        PoseStack poseStack = context.m_280168_();
        ResourceLocation resourceLocation = baseBackgroundResource;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, x, y, 132, 163, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), TextKt.bold(this.config.getTitle()), x + 37, (double)y + 1.5, 0.0f, null, 0, 0, true, false, null, null, 7648, null);
        poseStack = context.m_280168_();
        resourceLocation = spacerResource;
        double d = ((double)x + 86.5) / (double)0.5f;
        float f = (float)(y + 111) / 0.5f;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, d, Float.valueOf(f), 12, 79, null, null, null, null, null, null, null, null, null, false, 0.5f, 65472, null);
        super.m_88315_(context, mouseX, mouseY, partialTicks);
    }

    private final void onPress(PartySelectPokemonDTO pokemon) {
        if (!pokemon.getEnabled()) {
            return;
        }
        this.playSound(CobblemonSounds.GUI_CLICK);
        this.config.getOnSelect().invoke((Object)this, (Object)pokemon);
    }

    public void m_7379_() {
        if (!this.closed) {
            this.config.getOnCancel().invoke((Object)this);
        }
        super.m_7379_();
    }

    public boolean m_6913_() {
        return true;
    }

    public boolean m_7043_() {
        return false;
    }

    public final void playSound(@NotNull SoundEvent soundEvent) {
        Intrinsics.checkNotNullParameter((Object)soundEvent, (String)"soundEvent");
        Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)soundEvent, (float)1.0f));
    }

    private static final void init$lambda$1$lambda$0(PartySelectGUI this$0, PartySelectPokemonDTO $pokemon, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)$pokemon, (String)"$pokemon");
        this$0.onPress($pokemon);
    }

    private static final void init$lambda$2(PartySelectGUI this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        this$0.playSound(CobblemonSounds.GUI_CLICK);
        this$0.config.getOnBack().invoke((Object)this$0);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0004R\u0014\u0010\n\u001a\u00020\t8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\t8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\u000b\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/client/gui/interact/partyselect/PartySelectGUI$Companion;", "", "", "HEIGHT", "I", "", "SCALE", "F", "WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "baseBackgroundResource", "Lnet/minecraft/resources/ResourceLocation;", "spacerResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

