/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition;
import com.google.gson.JsonElement;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0016\u0018\u0000 \u0010*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002:\u0001\u0010B\u000f\u0012\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u000e\u0010\u000fJ%\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00028\u00002\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\n\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/api/conditional/RegistryLikeIdentifierCondition;", "T", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "t", "Lnet/minecraft/core/Registry;", "registry", "", "fits", "(Ljava/lang/Object;Lnet/minecraft/core/Registry;)Z", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "Lnet/minecraft/resources/ResourceLocation;", "getIdentifier", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "(Lnet/minecraft/resources/ResourceLocation;)V", "Companion", "common"})
public class RegistryLikeIdentifierCondition<T>
implements RegistryLikeCondition<T> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ResourceLocation identifier;

    public RegistryLikeIdentifierCondition(@NotNull ResourceLocation identifier) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        this.identifier = identifier;
    }

    @NotNull
    public final ResourceLocation getIdentifier() {
        return this.identifier;
    }

    @Override
    public boolean fits(T t, @NotNull Registry<T> registry) {
        Intrinsics.checkNotNullParameter(registry, (String)"registry");
        return Intrinsics.areEqual((Object)registry.m_7981_(t), (Object)this.identifier);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bJA\u0010\b\u001a\u0016\u0012\u0004\u0012\u00020\u0007\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\u00050\u0003\"\u0004\b\u0001\u0010\u00022\u0018\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00050\u0003\u00a2\u0006\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/api/conditional/RegistryLikeIdentifierCondition$Companion;", "", "T", "Lkotlin/Function1;", "Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeIdentifierCondition;", "constructor", "Lcom/google/gson/JsonElement;", "resolver", "(Lkotlin/jvm/functions/Function1;)Lkotlin/jvm/functions/Function1;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final <T> Function1<JsonElement, RegistryLikeIdentifierCondition<T>> resolver(@NotNull Function1<? super ResourceLocation, ? extends RegistryLikeIdentifierCondition<T>> constructor) {
            Intrinsics.checkNotNullParameter(constructor, (String)"constructor");
            return new Function1<JsonElement, RegistryLikeIdentifierCondition<T>>(constructor){
                final /* synthetic */ Function1<ResourceLocation, RegistryLikeIdentifierCondition<T>> $constructor;
                {
                    this.$constructor = $constructor;
                    super(1);
                }

                @NotNull
                public final RegistryLikeIdentifierCondition<T> invoke(@NotNull JsonElement it) {
                    Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                    return (RegistryLikeIdentifierCondition)this.$constructor.invoke((Object)new ResourceLocation(it.getAsString()));
                }
            };
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

