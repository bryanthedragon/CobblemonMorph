/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.ArraysKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.model.geom.ModelPart
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.model.geom.ModelPart;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\u001a!\u0010\u0005\u001a\u00020\u0000*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0006\u001a!\u0010\t\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u0003\u00a2\u0006\u0004\b\t\u0010\n\u001a1\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00000\u000e*\u00020\u00002\u0012\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\u000b\"\u00020\f\u00a2\u0006\u0004\b\u000f\u0010\u0010\u001a\u0011\u0010\u0012\u001a\u00020\u0011*\u00020\u0000\u00a2\u0006\u0004\b\u0012\u0010\u0013\u001a%\u0010\u0014\u001a\u00020\u0000*\u00020\u00002\u0012\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\f0\u000b\"\u00020\f\u00a2\u0006\u0004\b\u0014\u0010\u0015\u001a\u0019\u0010\u0016\u001a\u00020\u0003*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017\u001a\u0019\u0010\u0018\u001a\u00020\u0003*\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u0001\u00a2\u0006\u0004\b\u0018\u0010\u0019\u001a!\u0010\u001b\u001a\u00020\u0000*\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u0003\u00a2\u0006\u0004\b\u001b\u0010\u0006\u001a!\u0010\u001d\u001a\u00020\u0007*\u00020\u00072\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u0003\u00a2\u0006\u0004\b\u001d\u0010\n\u00a8\u0006\u001e"}, d2={"Lnet/minecraft/client/model/geom/ModelPart;", "", "axis", "", "difference", "addPosition", "(Lnet/minecraft/client/model/geom/ModelPart;IF)Lnet/minecraft/client/model/geom/ModelPart;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "differenceInRadians", "addRotation", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;IF)Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;", "", "", "path", "Lkotlin/Pair;", "childNamed", "(Lnet/minecraft/client/model/geom/ModelPart;[Ljava/lang/String;)Lkotlin/Pair;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;", "createTransformation", "(Lnet/minecraft/client/model/geom/ModelPart;)Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;", "getChildOf", "(Lnet/minecraft/client/model/geom/ModelPart;[Ljava/lang/String;)Lnet/minecraft/client/model/geom/ModelPart;", "getPosition", "(Lnet/minecraft/client/model/geom/ModelPart;I)F", "getRotation", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/Bone;I)F", "position", "setPosition", "angleInRadians", "setRotation", "common"})
public final class ModelPartExtensionsKt {
    @NotNull
    public static final ModelPartTransformation createTransformation(@NotNull ModelPart $this$createTransformation) {
        Intrinsics.checkNotNullParameter((Object)$this$createTransformation, (String)"<this>");
        return new ModelPartTransformation($this$createTransformation);
    }

    public static final float getPosition(@NotNull ModelPart $this$getPosition, int axis) {
        Intrinsics.checkNotNullParameter((Object)$this$getPosition, (String)"<this>");
        return switch (axis) {
            case 0 -> $this$getPosition.f_104200_;
            case 1 -> $this$getPosition.f_104201_;
            default -> $this$getPosition.f_104202_;
        };
    }

    public static final float getRotation(@NotNull Bone $this$getRotation, int axis) {
        float f;
        Intrinsics.checkNotNullParameter((Object)$this$getRotation, (String)"<this>");
        if ($this$getRotation instanceof ModelPart) {
            switch (axis) {
                case 0: {
                    f = ((ModelPart)$this$getRotation).f_104203_;
                    break;
                }
                case 1: {
                    f = ((ModelPart)$this$getRotation).f_104204_;
                    break;
                }
                default: {
                    f = ((ModelPart)$this$getRotation).f_104205_;
                    break;
                }
            }
        } else {
            f = 0.0f;
        }
        return f;
    }

    @NotNull
    public static final Bone setRotation(@NotNull Bone $this$setRotation, int axis, float angleInRadians) {
        Intrinsics.checkNotNullParameter((Object)$this$setRotation, (String)"<this>");
        if ($this$setRotation instanceof ModelPart) {
            switch (axis) {
                case 0: {
                    ((ModelPart)$this$setRotation).f_104203_ = angleInRadians;
                    break;
                }
                case 1: {
                    ((ModelPart)$this$setRotation).f_104204_ = angleInRadians;
                    break;
                }
                default: {
                    ((ModelPart)$this$setRotation).f_104205_ = angleInRadians;
                }
            }
        }
        return $this$setRotation;
    }

    @NotNull
    public static final ModelPart setPosition(@NotNull ModelPart $this$setPosition, int axis, float position) {
        Intrinsics.checkNotNullParameter((Object)$this$setPosition, (String)"<this>");
        switch (axis) {
            case 0: {
                $this$setPosition.f_104200_ = position;
                break;
            }
            case 1: {
                $this$setPosition.f_104201_ = position;
                break;
            }
            default: {
                $this$setPosition.f_104202_ = position;
            }
        }
        return $this$setPosition;
    }

    @NotNull
    public static final Bone addRotation(@NotNull Bone $this$addRotation, int axis, float differenceInRadians) {
        Intrinsics.checkNotNullParameter((Object)$this$addRotation, (String)"<this>");
        return ModelPartExtensionsKt.setRotation($this$addRotation, axis, ModelPartExtensionsKt.getRotation($this$addRotation, axis) + differenceInRadians);
    }

    @NotNull
    public static final ModelPart addPosition(@NotNull ModelPart $this$addPosition, int axis, float difference) {
        Intrinsics.checkNotNullParameter((Object)$this$addPosition, (String)"<this>");
        return ModelPartExtensionsKt.setPosition($this$addPosition, axis, ModelPartExtensionsKt.getPosition($this$addPosition, axis) + difference);
    }

    @NotNull
    public static final ModelPart getChildOf(@NotNull ModelPart $this$getChildOf, String ... path) {
        Intrinsics.checkNotNullParameter((Object)$this$getChildOf, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        ModelPart part = $this$getChildOf;
        for (String piece : path) {
            Intrinsics.checkNotNullExpressionValue((Object)part.m_171324_(piece), (String)"part.getChild(piece)");
        }
        return part;
    }

    @NotNull
    public static final Pair<String, ModelPart> childNamed(@NotNull ModelPart $this$childNamed, String ... path) {
        Intrinsics.checkNotNullParameter((Object)$this$childNamed, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        String string = (String)ArraysKt.last((Object[])path);
        return TuplesKt.to((Object)string, (Object)ModelPartExtensionsKt.getChildOf($this$childNamed, Arrays.copyOf(path, path.length)));
    }
}

