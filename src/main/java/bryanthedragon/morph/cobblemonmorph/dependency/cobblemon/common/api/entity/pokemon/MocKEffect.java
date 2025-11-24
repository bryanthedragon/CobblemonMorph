/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.pokemon.PhysicalEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00028VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004R\u0016\u0010\t\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\r\u001a\u00020\n8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/entity/pokemon/MocKEffect;", "Lcom/cobblemon/mod/common/api/entity/pokemon/PhysicalEffect;", "Lcom/cobblemon/mod/common/pokemon/FormData;", "getExposedForm", "()Lcom/cobblemon/mod/common/pokemon/FormData;", "exposedForm", "Lcom/cobblemon/mod/common/pokemon/Species;", "getExposedSpecies", "()Lcom/cobblemon/mod/common/pokemon/Species;", "exposedSpecies", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "getMock", "()Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "mock", "common"})
public interface MocKEffect
extends PhysicalEffect {
    @NotNull
    public PokemonProperties getMock();

    @Nullable
    public Species getExposedSpecies();

    @Nullable
    public FormData getExposedForm();

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    @SourceDebugExtension(value={"SMAP\nEntityEffect.kt\nKotlin\n*S Kotlin\n*F\n+ 1 EntityEffect.kt\ncom/cobblemon/mod/common/api/entity/pokemon/MocKEffect$DefaultImpls\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,98:1\n1#2:99\n288#3,2:100\n*S KotlinDebug\n*F\n+ 1 EntityEffect.kt\ncom/cobblemon/mod/common/api/entity/pokemon/MocKEffect$DefaultImpls\n*L\n95#1:100,2\n*E\n"})
    public static final class DefaultImpls {
        @Nullable
        public static Species getExposedSpecies(@NotNull MocKEffect $this) {
            Species species;
            String string = $this.getMock().getSpecies();
            if (string != null) {
                String it = string;
                boolean bl = false;
                species = PokemonSpecies.INSTANCE.getByIdentifier(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(it, null, 1, null));
            } else {
                species = null;
            }
            return species;
        }

        @Nullable
        public static FormData getExposedForm(@NotNull MocKEffect $this) {
            Object object;
            block8: {
                block7: {
                    FormData formData;
                    object = $this.getMock().getForm();
                    if (object == null) break block7;
                    String formID = object;
                    boolean bl = false;
                    Object object2 = $this.getExposedSpecies();
                    if (object2 != null && (object2 = ((Species)object2).getForms()) != null) {
                        Object v2;
                        block6: {
                            Iterable $this$firstOrNull$iv = (Iterable)object2;
                            boolean $i$f$firstOrNull = false;
                            for (Object element$iv : $this$firstOrNull$iv) {
                                FormData it = (FormData)element$iv;
                                boolean bl2 = false;
                                if (!StringsKt.equals((String)it.formOnlyShowdownId(), (String)formID, (boolean)true)) continue;
                                v2 = element$iv;
                                break block6;
                            }
                            v2 = null;
                        }
                        formData = v2;
                    } else {
                        formData = null;
                    }
                    object = formData;
                    if (formData != null) break block8;
                }
                Species species = $this.getExposedSpecies();
                object = species != null ? species.getStandardForm() : null;
            }
            return object;
        }
    }
}

