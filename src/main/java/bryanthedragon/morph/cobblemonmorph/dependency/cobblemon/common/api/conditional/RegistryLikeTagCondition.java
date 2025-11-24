/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.Holder$Reference
 *  net.minecraft.core.Registry
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition;
import com.google.gson.JsonElement;
import java.util.Optional;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0016\u0018\u0000 \u0010*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002:\u0001\u0010B\u0015\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\t\u00a2\u0006\u0004\b\u000e\u0010\u000fJ%\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00028\u00002\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR\u001d\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/api/conditional/RegistryLikeTagCondition;", "T", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeCondition;", "t", "Lnet/minecraft/core/Registry;", "registry", "", "fits", "(Ljava/lang/Object;Lnet/minecraft/core/Registry;)Z", "Lnet/minecraft/tags/TagKey;", "tag", "Lnet/minecraft/tags/TagKey;", "getTag", "()Lnet/minecraft/tags/TagKey;", "<init>", "(Lnet/minecraft/tags/TagKey;)V", "Companion", "common"})
public class RegistryLikeTagCondition<T>
implements RegistryLikeCondition<T> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final TagKey<T> tag;
    @NotNull
    public static final String PREFIX = "#";

    public RegistryLikeTagCondition(@NotNull TagKey<T> tag) {
        Intrinsics.checkNotNullParameter(tag, (String)"tag");
        this.tag = tag;
    }

    @NotNull
    public final TagKey<T> getTag() {
        return this.tag;
    }

    @Override
    public boolean fits(T t, @NotNull Registry<T> registry) {
        Intrinsics.checkNotNullParameter(registry, (String)"registry");
        Boolean bl = registry.m_7854_(t).flatMap(arg_0 -> RegistryLikeTagCondition.fits$lambda$0((Function1)new Function1<ResourceKey<T>, Optional<Holder.Reference<T>>>(registry){

            public final Optional<Holder.Reference<T>> invoke(ResourceKey<T> p0) {
                return ((Registry)this.receiver).m_203636_(p0);
            }
        }, arg_0)).map(arg_0 -> RegistryLikeTagCondition.fits$lambda$1((Function1)new Function1<Holder.Reference<T>, Boolean>(this){
            final /* synthetic */ RegistryLikeTagCondition<T> this$0;
            {
                this.this$0 = $receiver;
                super(1);
            }

            public final Boolean invoke(Holder.Reference<T> entry) {
                return entry.m_203656_(this.this$0.getTag());
            }
        }, arg_0)).orElse(false);
        Intrinsics.checkNotNullExpressionValue((Object)bl, (String)"override fun fits(t: T, \u2026     .orElse(false)\n    }");
        return bl;
    }

    private static final Optional fits$lambda$0(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Optional)$tmp0.invoke(p0);
    }

    private static final Boolean fits$lambda$1(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0010\u0010\u0011J[\u0010\u000b\u001a\u0016\u0012\u0004\u0012\u00020\n\u0012\f\u0012\n\u0012\u0004\u0012\u00028\u0001\u0018\u00010\b0\u0006\"\u0004\b\u0001\u0010\u00022\u0012\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00040\u00032\u001e\u0010\t\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\b0\u0006\u00a2\u0006\u0004\b\u000b\u0010\fR\u0014\u0010\u000e\u001a\u00020\r8\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/api/conditional/RegistryLikeTagCondition$Companion;", "", "T", "Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/core/Registry;", "registryKey", "Lkotlin/Function1;", "Lnet/minecraft/tags/TagKey;", "Lcom/cobblemon/mod/common/api/conditional/RegistryLikeTagCondition;", "constructor", "Lcom/google/gson/JsonElement;", "resolver", "(Lnet/minecraft/resources/ResourceKey;Lkotlin/jvm/functions/Function1;)Lkotlin/jvm/functions/Function1;", "", "PREFIX", "Ljava/lang/String;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final <T> Function1<JsonElement, RegistryLikeTagCondition<T>> resolver(@NotNull ResourceKey<Registry<T>> registryKey, @NotNull Function1<? super TagKey<T>, ? extends RegistryLikeTagCondition<T>> constructor) {
            Intrinsics.checkNotNullParameter(registryKey, (String)"registryKey");
            Intrinsics.checkNotNullParameter(constructor, (String)"constructor");
            return new Function1<JsonElement, RegistryLikeTagCondition<T>>(constructor, registryKey){
                final /* synthetic */ Function1<TagKey<T>, RegistryLikeTagCondition<T>> $constructor;
                final /* synthetic */ ResourceKey<Registry<T>> $registryKey;
                {
                    this.$constructor = $constructor;
                    this.$registryKey = $registryKey;
                    super(1);
                }

                @Nullable
                public final RegistryLikeTagCondition<T> invoke(@NotNull JsonElement it) {
                    RegistryLikeTagCondition registryLikeTagCondition;
                    Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                    String string = it.getAsString();
                    Intrinsics.checkNotNullExpressionValue((Object)string, (String)"it.asString");
                    String string2 = string;
                    int n = 0;
                    int n2 = 1;
                    String string3 = string2.substring(n, n2);
                    Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"this as java.lang.String\u2026ing(startIndex, endIndex)");
                    String firstSymbol = string3;
                    if (Intrinsics.areEqual((Object)firstSymbol, (Object)"#")) {
                        String string4 = it.getAsString();
                        Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"it.asString");
                        String string5 = string4;
                        n2 = 1;
                        String string6 = string5.substring(n2);
                        Intrinsics.checkNotNullExpressionValue((Object)string6, (String)"this as java.lang.String).substring(startIndex)");
                        ResourceLocation identifier = new ResourceLocation(string6);
                        TagKey tagKey = TagKey.m_203882_(this.$registryKey, (ResourceLocation)identifier);
                        Intrinsics.checkNotNullExpressionValue((Object)tagKey, (String)"of(registryKey, identifier)");
                        registryLikeTagCondition = (RegistryLikeTagCondition)this.$constructor.invoke((Object)tagKey);
                    } else {
                        registryLikeTagCondition = null;
                    }
                    return registryLikeTagCondition;
                }
            };
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

