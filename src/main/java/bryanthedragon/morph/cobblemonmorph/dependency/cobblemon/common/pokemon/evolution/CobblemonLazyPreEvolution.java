/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PreEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\u0004\b\u0018\u0010\u0019R\u0014\u0010\u0005\u001a\u00020\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004R\u001b\u0010\t\u001a\u00020\u00028BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\u0004R\u001b\u0010\u000e\u001a\u00020\n8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000b\u0010\u0007\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0012\u001a\u00020\u000f8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0014\u001a\u00020\u00138\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0017\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\r\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/CobblemonLazyPreEvolution;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/PreEvolution;", "Lcom/cobblemon/mod/common/pokemon/FormData;", "getForm", "()Lcom/cobblemon/mod/common/pokemon/FormData;", "form", "lazyForm$delegate", "Lkotlin/Lazy;", "getLazyForm", "lazyForm", "Lcom/cobblemon/mod/common/pokemon/Species;", "lazySpecies$delegate", "getLazySpecies", "()Lcom/cobblemon/mod/common/pokemon/Species;", "lazySpecies", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getProperties", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "properties", "", "rawData", "Ljava/lang/String;", "getSpecies", "species", "<init>", "(Ljava/lang/String;)V", "common"})
public final class CobblemonLazyPreEvolution
implements PreEvolution {
    @NotNull
    private final String rawData;
    @NotNull
    private final Lazy lazySpecies$delegate;
    @NotNull
    private final Lazy lazyForm$delegate;

    public CobblemonLazyPreEvolution(@NotNull String rawData) {
        Intrinsics.checkNotNullParameter((Object)rawData, (String)"rawData");
        this.rawData = rawData;
        this.lazySpecies$delegate = LazyKt.lazy((Function0)((Function0)new Function0<Species>(this){
            final /* synthetic */ CobblemonLazyPreEvolution this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Species invoke() {
                Object object;
                block3: {
                    block2: {
                        object = CobblemonLazyPreEvolution.access$getProperties(this.this$0).getSpecies();
                        if (object == null || (object = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default((String)object, null, 1, null)) == null) break block2;
                        String it = object;
                        boolean bl = false;
                        Species species = PokemonSpecies.INSTANCE.getByIdentifier((ResourceLocation)it);
                        object = species;
                        if (species != null) break block3;
                    }
                    throw new IllegalArgumentException("A PreEvolution needs a valid species");
                }
                return object;
            }
        }));
        this.lazyForm$delegate = LazyKt.lazy((Function0)((Function0)new Function0<FormData>(this){
            final /* synthetic */ CobblemonLazyPreEvolution this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final FormData invoke() {
                Object object;
                block5: {
                    block4: {
                        Object v1;
                        block3: {
                            object = CobblemonLazyPreEvolution.access$getProperties(this.this$0).getForm();
                            if (object == null) break block4;
                            String string = object;
                            CobblemonLazyPreEvolution cobblemonLazyPreEvolution = this.this$0;
                            String formId = string;
                            boolean bl = false;
                            Iterable $this$firstOrNull$iv = cobblemonLazyPreEvolution.getSpecies().getForms();
                            boolean $i$f$firstOrNull = false;
                            for (T element$iv : $this$firstOrNull$iv) {
                                FormData it = (FormData)element$iv;
                                boolean bl2 = false;
                                if (!StringsKt.equals((String)it.formOnlyShowdownId(), (String)formId, (boolean)true)) continue;
                                v1 = element$iv;
                                break block3;
                            }
                            v1 = null;
                        }
                        FormData formData = v1;
                        object = formData;
                        if (formData != null) break block5;
                    }
                    object = this.this$0.getSpecies().getStandardForm();
                }
                return object;
            }
        }));
    }

    private final PokemonProperties getProperties() {
        return PokemonProperties.Companion.parse$default(PokemonProperties.Companion, this.rawData, null, null, 6, null);
    }

    private final Species getLazySpecies() {
        Lazy lazy = this.lazySpecies$delegate;
        return (Species)lazy.getValue();
    }

    private final FormData getLazyForm() {
        Lazy lazy = this.lazyForm$delegate;
        return (FormData)lazy.getValue();
    }

    @Override
    @NotNull
    public Species getSpecies() {
        return this.getLazySpecies();
    }

    @Override
    @NotNull
    public FormData getForm() {
        return this.getLazyForm();
    }

    public static final /* synthetic */ PokemonProperties access$getProperties(CobblemonLazyPreEvolution $this) {
        return $this.getProperties();
    }
}

