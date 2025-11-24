/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.resources.language.I18n
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pokemon.update.SetNicknamePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 52\u00020\u0001:\u00015B?\u0012\u0006\u0010\u001a\u001a\u00020\u0019\u0012\u0006\u0010-\u001a\u00020\u0002\u0012\u0006\u0010.\u001a\u00020\u0002\u0012\u0006\u0010/\u001a\u00020\u0002\u0012\u0006\u00100\u001a\u00020\u0002\u0012\u0006\u0010!\u001a\u00020\u0006\u0012\u0006\u00102\u001a\u000201\u00a2\u0006\u0004\b3\u00104J'\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ'\u0010\r\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ/\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u0015\u0010\u001b\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u0019\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0017\u0010\u001f\u001a\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u001dH\u0002\u00a2\u0006\u0004\b\u001f\u0010 R\u0017\u0010!\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b!\u0010#R\"\u0010\u001a\u001a\u00020\u00198\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001a\u0010$\u001a\u0004\b%\u0010&\"\u0004\b'\u0010\u001cR\"\u0010(\u001a\u00020\u001d8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b(\u0010)\u001a\u0004\b*\u0010+\"\u0004\b,\u0010 \u00a8\u00066"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/NicknameEntryWidget;", "Lnet/minecraft/client/gui/components/EditBox;", "", "keyCode", "scanCode", "modifiers", "", "keyPressed", "(III)Z", "", "mouseX", "mouseY", "button", "mouseClicked", "(DDI)Z", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "delta", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "focused", "setFocused", "(Z)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "setSelectedPokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "", "newNickname", "updateNickname", "(Ljava/lang/String;)V", "isParty", "Z", "()Z", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "setPokemon", "pokemonName", "Ljava/lang/String;", "getPokemonName", "()Ljava/lang/String;", "setPokemonName", "x", "y", "width", "height", "Lnet/minecraft/network/chat/Component;", "text", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;IIIIZLnet/minecraft/network/chat/Component;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nNicknameEntryWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NicknameEntryWidget.kt\ncom/cobblemon/mod/common/client/gui/summary/widgets/NicknameEntryWidget\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,109:1\n1#2:110\n*E\n"})
public final class NicknameEntryWidget
extends EditBox {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Pokemon pokemon;
    private final boolean isParty;
    @NotNull
    private String pokemonName;
    private static final int MAX_NAME_LENGTH = 12;

    public NicknameEntryWidget(@NotNull Pokemon pokemon, int x, int y, int width, int height, boolean isParty, @NotNull Component text) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        super(Minecraft.m_91087_().f_91062_, x, y, width, height, text);
        this.pokemon = pokemon;
        this.isParty = isParty;
        this.pokemonName = "";
        this.m_94199_(12);
        this.setSelectedPokemon(this.pokemon);
    }

    @NotNull
    public final Pokemon getPokemon() {
        return this.pokemon;
    }

    public final void setPokemon(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"<set-?>");
        this.pokemon = pokemon;
    }

    public final boolean isParty() {
        return this.isParty;
    }

    @NotNull
    public final String getPokemonName() {
        return this.pokemonName;
    }

    public final void setPokemonName(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.pokemonName = string;
    }

    public final void setSelectedPokemon(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        if (this.m_93696_()) {
            this.m_93692_(false);
        }
        this.pokemon = pokemon;
        String string = I18n.m_118938_((String)pokemon.getSpecies().getTranslatedName().getString(), (Object[])new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"translate(pokemon.species.translatedName.string)");
        this.pokemonName = string;
        this.m_94151_(arg_0 -> NicknameEntryWidget.setSelectedPokemon$lambda$0(this, arg_0));
        this.m_94144_(pokemon.getDisplayName().getString());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean m_6375_(double mouseX, double mouseY, int button) {
        int n = this.m_252754_();
        int n2 = this.m_252754_() + this.f_93618_;
        int n3 = (int)mouseX;
        if (n > n3) return false;
        if (n3 > n2) return false;
        boolean bl = true;
        if (!bl) return false;
        n = this.m_252907_();
        n2 = this.m_252907_() + this.f_93619_;
        n3 = (int)mouseY;
        if (n > n3) return false;
        if (n3 > n2) return false;
        boolean bl2 = true;
        if (!bl2) return false;
        this.m_93692_(true);
        return true;
    }

    public void m_93692_(boolean focused) {
        CharSequence charSequence;
        super.m_93692_(focused);
        NicknameEntryWidget nicknameEntryWidget = this;
        String string = this.m_94155_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"text");
        CharSequence charSequence2 = ((Object)StringsKt.trim((CharSequence)string)).toString();
        if (StringsKt.isBlank((CharSequence)charSequence2)) {
            NicknameEntryWidget nicknameEntryWidget2 = nicknameEntryWidget;
            boolean bl = false;
            charSequence = this.pokemonName;
            nicknameEntryWidget = nicknameEntryWidget2;
        } else {
            charSequence = charSequence2;
        }
        nicknameEntryWidget.m_94144_((String)charSequence);
        if (!focused) {
            String string2 = this.m_94155_();
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"text");
            this.updateNickname(string2);
        }
    }

    private final void updateNickname(String newNickname) {
        block3: {
            block2: {
                if (this.pokemon.getNickname() == null) break block2;
                MutableComponent mutableComponent = this.pokemon.getNickname();
                if (Intrinsics.areEqual((Object)(mutableComponent != null ? mutableComponent.getString() : null), (Object)newNickname)) break block3;
            }
            String effectiveNickname = Intrinsics.areEqual((Object)newNickname, (Object)this.pokemonName) ? null : newNickname;
            UUID uUID = this.pokemon.getUuid();
            boolean bl = this.isParty;
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"uuid");
            CobblemonNetwork.INSTANCE.sendToServer(new SetNicknamePacket(uUID, bl, effectiveNickname));
        }
    }

    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (this.m_94207_() != this.m_94155_().length()) {
            this.m_94201_();
        }
        ResourceLocation resourceLocation = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
        MutableComponent mutableComponent = Component.m_237115_((String)(this.m_93696_() ? this.m_94155_() + "|" : this.m_94155_()));
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"translatable(if (isFocused) \"$text|\" else text)");
        RenderHelperKt.drawScaledText$default(context, resourceLocation, TextKt.bold(mutableComponent), this.m_252754_(), this.m_252907_(), 0.0f, null, 0, 0, false, true, null, null, 7136, null);
    }

    public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            CharSequence charSequence;
            NicknameEntryWidget nicknameEntryWidget = this;
            String string = this.m_94155_();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"text");
            CharSequence charSequence2 = ((Object)StringsKt.trim((CharSequence)string)).toString();
            if (StringsKt.isBlank((CharSequence)charSequence2)) {
                NicknameEntryWidget nicknameEntryWidget2 = nicknameEntryWidget;
                boolean bl = false;
                charSequence = this.pokemonName;
                nicknameEntryWidget = nicknameEntryWidget2;
            } else {
                charSequence = charSequence2;
            }
            nicknameEntryWidget.updateNickname((String)charSequence);
        }
        return super.m_7933_(keyCode, scanCode, modifiers);
    }

    private static final void setSelectedPokemon$lambda$0(NicknameEntryWidget this$0, String it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
        if (!StringsKt.isBlank((CharSequence)it)) {
            this$0.updateNickname(it);
        } else {
            this$0.updateNickname(this$0.pokemonName);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/NicknameEntryWidget$Companion;", "", "", "MAX_NAME_LENGTH", "I", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

