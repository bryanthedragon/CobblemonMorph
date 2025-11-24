/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import io.netty.buffer.ByteBuf;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\r\b\u0086\b\u0018\u0000 *2\u00020\u0001:\u0001*B\u001d\u0012\u0006\u0010\t\u001a\u00020\u0002\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0004\b(\u0010)J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0007\u0010\bJ*\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\t\u001a\u00020\u00022\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001a\u0010\u000f\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0012\u001a\u00020\u0011H\u00d6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0015\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0018\u001a\u00020\u0006H\u00d6\u0001\u00a2\u0006\u0004\b\u0018\u0010\u0019R(\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\n\u0010\u001a\u001a\u0004\b\u001b\u0010\b\"\u0004\b\u001c\u0010\u001dR\u001b\u0010#\u001a\u00020\u001e8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"R\"\u0010\t\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\t\u0010$\u001a\u0004\b%\u0010\u0004\"\u0004\b&\u0010'\u00a8\u0006+"}, d2={"Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "", "Lcom/cobblemon/mod/common/pokemon/Species;", "component1", "()Lcom/cobblemon/mod/common/pokemon/Species;", "", "", "component2", "()Ljava/util/Set;", "species", "aspects", "copy", "(Lcom/cobblemon/mod/common/pokemon/Species;Ljava/util/Set;)Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/network/FriendlyByteBuf;", "toString", "()Ljava/lang/String;", "Ljava/util/Set;", "getAspects", "setAspects", "(Ljava/util/Set;)V", "Lcom/cobblemon/mod/common/pokemon/FormData;", "form$delegate", "Lkotlin/Lazy;", "getForm", "()Lcom/cobblemon/mod/common/pokemon/FormData;", "form", "Lcom/cobblemon/mod/common/pokemon/Species;", "getSpecies", "setSpecies", "(Lcom/cobblemon/mod/common/pokemon/Species;)V", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Species;Ljava/util/Set;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nRenderablePokemon.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RenderablePokemon.kt\ncom/cobblemon/mod/common/pokemon/RenderablePokemon\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,43:1\n1855#2,2:44\n*S KotlinDebug\n*F\n+ 1 RenderablePokemon.kt\ncom/cobblemon/mod/common/pokemon/RenderablePokemon\n*L\n29#1:44,2\n*E\n"})
public final class RenderablePokemon {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private Species species;
    @NotNull
    private Set<String> aspects;
    @NotNull
    private final Lazy form$delegate;

    public RenderablePokemon(@NotNull Species species, @NotNull Set<String> aspects) {
        Intrinsics.checkNotNullParameter((Object)species, (String)"species");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        this.species = species;
        this.aspects = aspects;
        this.form$delegate = LazyKt.lazy((Function0)((Function0)new Function0<FormData>(this){
            final /* synthetic */ RenderablePokemon this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final FormData invoke() {
                return this.this$0.getSpecies().getForm(this.this$0.getAspects());
            }
        }));
    }

    @NotNull
    public final Species getSpecies() {
        return this.species;
    }

    public final void setSpecies(@NotNull Species species) {
        Intrinsics.checkNotNullParameter((Object)species, (String)"<set-?>");
        this.species = species;
    }

    @NotNull
    public final Set<String> getAspects() {
        return this.aspects;
    }

    public final void setAspects(@NotNull Set<String> set2) {
        Intrinsics.checkNotNullParameter(set2, (String)"<set-?>");
        this.aspects = set2;
    }

    @NotNull
    public final FormData getForm() {
        Lazy lazy = this.form$delegate;
        return (FormData)lazy.getValue();
    }

    @NotNull
    public final FriendlyByteBuf saveToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130085_(this.species.getResourceIdentifier());
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.aspects.size());
        Iterable $this$forEach$iv = this.aspects;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String p0 = (String)element$iv;
            boolean bl = false;
            buffer.m_130070_(p0);
        }
        return buffer;
    }

    @NotNull
    public final Species component1() {
        return this.species;
    }

    @NotNull
    public final Set<String> component2() {
        return this.aspects;
    }

    @NotNull
    public final RenderablePokemon copy(@NotNull Species species, @NotNull Set<String> aspects) {
        Intrinsics.checkNotNullParameter((Object)species, (String)"species");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        return new RenderablePokemon(species, aspects);
    }

    public static /* synthetic */ RenderablePokemon copy$default(RenderablePokemon renderablePokemon, Species species, Set set2, int n, Object object) {
        if ((n & 1) != 0) {
            species = renderablePokemon.species;
        }
        if ((n & 2) != 0) {
            set2 = renderablePokemon.aspects;
        }
        return renderablePokemon.copy(species, set2);
    }

    @NotNull
    public String toString() {
        return "RenderablePokemon(species=" + this.species + ", aspects=" + this.aspects + ")";
    }

    public int hashCode() {
        int result = this.species.hashCode();
        result = result * 31 + ((Object)this.aspects).hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RenderablePokemon)) {
            return false;
        }
        RenderablePokemon renderablePokemon = (RenderablePokemon)other;
        if (!Intrinsics.areEqual((Object)this.species, (Object)renderablePokemon.species)) {
            return false;
        }
        return Intrinsics.areEqual(this.aspects, renderablePokemon.aspects);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/pokemon/RenderablePokemon$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final RenderablePokemon loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            ResourceLocation resourceLocation = buffer.m_130281_();
            Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"buffer.readIdentifier()");
            Species species = PokemonSpecies.INSTANCE.getByIdentifier(resourceLocation);
            Intrinsics.checkNotNull((Object)species);
            Species species2 = species;
            Set aspects = new LinkedHashSet();
            int n = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
            int n2 = 0;
            while (n2 < n) {
                int it = n2++;
                boolean bl = false;
                String string = buffer.m_130277_();
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
                aspects.add(string);
            }
            return new RenderablePokemon(species2, aspects);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

