/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.model.geom.ModelPart
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.AngleExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.model.geom.ModelPart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0004\n\u0002\b\u0011\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0014\n\u0002\b\u001c\u0018\u0000 R2\u00020\u0001:\u0001RB\u000f\u0012\u0006\u0010/\u001a\u00020.\u00a2\u0006\u0004\bP\u0010QJ\u001d\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0006\u0010\u0007J%\u0010\u0006\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0006\u0010\u000bJ\u001d\u0010\r\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u0004\u00a2\u0006\u0004\b\r\u0010\u0007J%\u0010\r\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004\u00a2\u0006\u0004\b\r\u0010\u000bJ\u001d\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0012\u0010\u0007J%\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0012\u0010\u000bJ\u0015\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u0017\u001a\u00020\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u001d\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0004\u00a2\u0006\u0004\b\u001c\u0010\u0007J%\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u0004\u00a2\u0006\u0004\b\u001c\u0010\u000bJ\r\u0010 \u001a\u00020\u0018\u00a2\u0006\u0004\b \u0010!J\u001d\u0010#\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\"\u001a\u00020\u0004\u00a2\u0006\u0004\b#\u0010\u0007J%\u0010#\u001a\u00020\u00002\u0006\u0010$\u001a\u00020\u00042\u0006\u0010%\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u0004\u00a2\u0006\u0004\b#\u0010\u000bJ\u001d\u0010'\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u0004\u00a2\u0006\u0004\b'\u0010\u0007J%\u0010'\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004\u00a2\u0006\u0004\b'\u0010\u000bJ%\u0010(\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0004\u00a2\u0006\u0004\b(\u0010\u000bJ\u001d\u0010)\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0004\u00a2\u0006\u0004\b)\u0010\u0007J%\u0010)\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u0004\u00a2\u0006\u0004\b)\u0010\u000bJ\u0015\u0010,\u001a\u00020\u00002\u0006\u0010+\u001a\u00020*\u00a2\u0006\u0004\b,\u0010-R\u0017\u0010/\u001a\u00020.8\u0006\u00a2\u0006\f\n\u0004\b/\u00100\u001a\u0004\b1\u00102R$\u0010\u0013\u001a\u00020\u00162\u0006\u00103\u001a\u00020\u00168F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b4\u00105\"\u0004\b6\u0010\u001aR\"\u0010\"\u001a\u0002078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\"\u00108\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R$\u0010\u0015\u001a\u00020\u00162\u0006\u00103\u001a\u00020\u00168F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b=\u00105\"\u0004\b>\u0010\u001aR\"\u0010?\u001a\u0002078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b?\u00108\u001a\u0004\b@\u0010:\"\u0004\bA\u0010<R\u0017\u0010\u001b\u001a\u0002078\u0006\u00a2\u0006\f\n\u0004\b\u001b\u00108\u001a\u0004\bB\u0010:R$\u0010+\u001a\u0004\u0018\u00010*8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b+\u0010C\u001a\u0004\bD\u0010E\"\u0004\bF\u0010GR$\u0010$\u001a\u00020\u00162\u0006\u00103\u001a\u00020\u00168F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\bH\u00105\"\u0004\bI\u0010\u001aR$\u0010%\u001a\u00020\u00162\u0006\u00103\u001a\u00020\u00168F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\bJ\u00105\"\u0004\bK\u0010\u001aR$\u0010\u0014\u001a\u00020\u00162\u0006\u00103\u001a\u00020\u00168F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\bL\u00105\"\u0004\bM\u0010\u001aR$\u0010&\u001a\u00020\u00162\u0006\u00103\u001a\u00020\u00168F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\bN\u00105\"\u0004\bO\u0010\u001a\u00a8\u0006S"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;", "", "", "axis", "", "distance", "addPosition", "(ILjava/lang/Number;)Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;", "xDist", "yDist", "zDist", "(Ljava/lang/Number;Ljava/lang/Number;Ljava/lang/Number;)Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;", "angleRadians", "addRotation", "pitchRadians", "yawRadians", "rollRadians", "angle", "addRotationDegrees", "pitch", "yaw", "roll", "", "intensity", "", "apply", "(F)V", "scale", "multiplyScale", "scaleX", "scaleY", "scaleZ", "set", "()V", "position", "withPosition", "xPos", "yPos", "zPos", "withRotation", "withRotationDegrees", "withScale", "", "visibility", "withVisibility", "(Z)Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;", "Lnet/minecraft/client/model/geom/ModelPart;", "modelPart", "Lnet/minecraft/client/model/geom/ModelPart;", "getModelPart", "()Lnet/minecraft/client/model/geom/ModelPart;", "value", "getPitch", "()F", "setPitch", "", "[F", "getPosition", "()[F", "setPosition", "([F)V", "getRoll", "setRoll", "rotation", "getRotation", "setRotation", "getScale", "Ljava/lang/Boolean;", "getVisibility", "()Ljava/lang/Boolean;", "setVisibility", "(Ljava/lang/Boolean;)V", "getXPos", "setXPos", "getYPos", "setYPos", "getYaw", "setYaw", "getZPos", "setZPos", "<init>", "(Lnet/minecraft/client/model/geom/ModelPart;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nModelPartTransformation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ModelPartTransformation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,165:1\n1#2:166\n*E\n"})
public final class ModelPartTransformation {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ModelPart modelPart;
    @NotNull
    private float[] position;
    @NotNull
    private float[] rotation;
    @NotNull
    private final float[] scale;
    @Nullable
    private Boolean visibility;
    public static final int X_AXIS = 0;
    public static final int Y_AXIS = 1;
    public static final int Z_AXIS = 2;

    public ModelPartTransformation(@NotNull ModelPart modelPart) {
        Intrinsics.checkNotNullParameter((Object)modelPart, (String)"modelPart");
        this.modelPart = modelPart;
        float[] fArray = new float[]{0.0f, 0.0f, 0.0f};
        this.position = fArray;
        fArray = new float[]{0.0f, 0.0f, 0.0f};
        this.rotation = fArray;
        fArray = new float[]{1.0f, 1.0f, 1.0f};
        this.scale = fArray;
    }

    @NotNull
    public final ModelPart getModelPart() {
        return this.modelPart;
    }

    @NotNull
    public final float[] getPosition() {
        return this.position;
    }

    public final void setPosition(@NotNull float[] fArray) {
        Intrinsics.checkNotNullParameter((Object)fArray, (String)"<set-?>");
        this.position = fArray;
    }

    @NotNull
    public final float[] getRotation() {
        return this.rotation;
    }

    public final void setRotation(@NotNull float[] fArray) {
        Intrinsics.checkNotNullParameter((Object)fArray, (String)"<set-?>");
        this.rotation = fArray;
    }

    @NotNull
    public final float[] getScale() {
        return this.scale;
    }

    @Nullable
    public final Boolean getVisibility() {
        return this.visibility;
    }

    public final void setVisibility(@Nullable Boolean bl) {
        this.visibility = bl;
    }

    public final void apply(float intensity) {
        block0: {
            ModelPart modelPart = this.modelPart;
            modelPart.f_104200_ += this.position[0] * intensity;
            modelPart = this.modelPart;
            modelPart.f_104201_ += this.position[1] * intensity;
            modelPart = this.modelPart;
            modelPart.f_104202_ += this.position[2] * intensity;
            modelPart = this.modelPart;
            modelPart.f_104203_ += this.rotation[0] * intensity;
            modelPart = this.modelPart;
            modelPart.f_104204_ += this.rotation[1] * intensity;
            modelPart = this.modelPart;
            modelPart.f_104205_ += this.rotation[2] * intensity;
            modelPart = this.modelPart;
            modelPart.f_233553_ *= (1.0f - this.scale[0]) * intensity + 1.0f;
            modelPart = this.modelPart;
            modelPart.f_233554_ *= (1.0f - this.scale[1]) * intensity + 1.0f;
            modelPart = this.modelPart;
            modelPart.f_233555_ *= (1.0f - this.scale[2]) * intensity + 1.0f;
            Boolean bl = this.visibility;
            if (bl == null) break block0;
            boolean it = bl;
            boolean bl2 = false;
            this.modelPart.f_104207_ = it;
        }
    }

    public final void set() {
        block0: {
            this.modelPart.f_104200_ = this.position[0];
            this.modelPart.f_104201_ = this.position[1];
            this.modelPart.f_104202_ = this.position[2];
            this.modelPart.f_104203_ = this.rotation[0];
            this.modelPart.f_104204_ = this.rotation[1];
            this.modelPart.f_104205_ = this.rotation[2];
            this.modelPart.f_233553_ = this.scale[0];
            this.modelPart.f_233554_ = this.scale[1];
            this.modelPart.f_233555_ = this.scale[2];
            Boolean bl = this.visibility;
            if (bl == null) break block0;
            boolean it = bl;
            boolean bl2 = false;
            this.modelPart.f_104207_ = it;
        }
    }

    @NotNull
    public final ModelPartTransformation withVisibility(boolean visibility) {
        this.visibility = visibility;
        return this;
    }

    public final float getXPos() {
        return this.position[0];
    }

    public final void setXPos(float value2) {
        this.position[0] = value2;
    }

    public final float getYPos() {
        return this.position[1];
    }

    public final void setYPos(float value2) {
        this.position[1] = value2;
    }

    public final float getZPos() {
        return this.position[2];
    }

    public final void setZPos(float value2) {
        this.position[2] = value2;
    }

    public final float getPitch() {
        return this.rotation[0];
    }

    public final void setPitch(float value2) {
        this.rotation[0] = value2;
    }

    public final float getYaw() {
        return this.rotation[1];
    }

    public final void setYaw(float value2) {
        this.rotation[1] = value2;
    }

    public final float getRoll() {
        return this.rotation[2];
    }

    public final void setRoll(float value2) {
        this.rotation[2] = value2;
    }

    @NotNull
    public final ModelPartTransformation withPosition(int axis, @NotNull Number position) {
        Intrinsics.checkNotNullParameter((Object)position, (String)"position");
        this.position[axis] = position.floatValue();
        return this;
    }

    @NotNull
    public final ModelPartTransformation withPosition(@NotNull Number xPos, @NotNull Number yPos, @NotNull Number zPos) {
        Intrinsics.checkNotNullParameter((Object)xPos, (String)"xPos");
        Intrinsics.checkNotNullParameter((Object)yPos, (String)"yPos");
        Intrinsics.checkNotNullParameter((Object)zPos, (String)"zPos");
        return this.withPosition(0, xPos).withPosition(1, yPos).withPosition(2, zPos);
    }

    @NotNull
    public final ModelPartTransformation withRotation(int axis, @NotNull Number angleRadians) {
        Intrinsics.checkNotNullParameter((Object)angleRadians, (String)"angleRadians");
        this.rotation[axis] = angleRadians.floatValue();
        return this;
    }

    @NotNull
    public final ModelPartTransformation withRotation(@NotNull Number pitch, @NotNull Number yaw, @NotNull Number roll) {
        Intrinsics.checkNotNullParameter((Object)pitch, (String)"pitch");
        Intrinsics.checkNotNullParameter((Object)yaw, (String)"yaw");
        Intrinsics.checkNotNullParameter((Object)roll, (String)"roll");
        return this.withRotation(0, pitch).withRotation(1, yaw).withRotation(2, roll);
    }

    @NotNull
    public final ModelPartTransformation addPosition(int axis, @NotNull Number distance) {
        Intrinsics.checkNotNullParameter((Object)distance, (String)"distance");
        return this.withPosition(axis, Float.valueOf(this.position[axis] + distance.floatValue()));
    }

    @NotNull
    public final ModelPartTransformation addPosition(@NotNull Number xDist, @NotNull Number yDist, @NotNull Number zDist) {
        Intrinsics.checkNotNullParameter((Object)xDist, (String)"xDist");
        Intrinsics.checkNotNullParameter((Object)yDist, (String)"yDist");
        Intrinsics.checkNotNullParameter((Object)zDist, (String)"zDist");
        return this.addPosition(0, xDist).addPosition(1, yDist).addPosition(2, zDist);
    }

    @NotNull
    public final ModelPartTransformation addRotation(int axis, @NotNull Number angleRadians) {
        Intrinsics.checkNotNullParameter((Object)angleRadians, (String)"angleRadians");
        return this.withRotation(axis, Float.valueOf(this.rotation[axis] + angleRadians.floatValue()));
    }

    @NotNull
    public final ModelPartTransformation addRotation(@NotNull Number pitchRadians, @NotNull Number yawRadians, @NotNull Number rollRadians) {
        Intrinsics.checkNotNullParameter((Object)pitchRadians, (String)"pitchRadians");
        Intrinsics.checkNotNullParameter((Object)yawRadians, (String)"yawRadians");
        Intrinsics.checkNotNullParameter((Object)rollRadians, (String)"rollRadians");
        return this.addRotation(0, pitchRadians).addRotation(1, yawRadians).addRotation(2, rollRadians);
    }

    @NotNull
    public final ModelPartTransformation addRotationDegrees(@NotNull Number pitch, @NotNull Number yaw, @NotNull Number roll) {
        Intrinsics.checkNotNullParameter((Object)pitch, (String)"pitch");
        Intrinsics.checkNotNullParameter((Object)yaw, (String)"yaw");
        Intrinsics.checkNotNullParameter((Object)roll, (String)"roll");
        return this.addRotation(0, Float.valueOf(AngleExtensionsKt.toRadians(Float.valueOf(pitch.floatValue())))).addRotation(1, Float.valueOf(AngleExtensionsKt.toRadians(Float.valueOf(yaw.floatValue())))).addRotation(2, Float.valueOf(AngleExtensionsKt.toRadians(Float.valueOf(roll.floatValue()))));
    }

    @NotNull
    public final ModelPartTransformation multiplyScale(int axis, @NotNull Number scale) {
        Intrinsics.checkNotNullParameter((Object)scale, (String)"scale");
        return this.withScale(axis, Float.valueOf(scale.floatValue() * this.scale[axis]));
    }

    @NotNull
    public final ModelPartTransformation multiplyScale(@NotNull Number scaleX, @NotNull Number scaleY, @NotNull Number scaleZ) {
        Intrinsics.checkNotNullParameter((Object)scaleX, (String)"scaleX");
        Intrinsics.checkNotNullParameter((Object)scaleY, (String)"scaleY");
        Intrinsics.checkNotNullParameter((Object)scaleZ, (String)"scaleZ");
        return this.multiplyScale(0, scaleX).multiplyScale(1, scaleY).multiplyScale(2, scaleZ);
    }

    @NotNull
    public final ModelPartTransformation withRotationDegrees(@NotNull Number pitch, @NotNull Number yaw, @NotNull Number roll) {
        Intrinsics.checkNotNullParameter((Object)pitch, (String)"pitch");
        Intrinsics.checkNotNullParameter((Object)yaw, (String)"yaw");
        Intrinsics.checkNotNullParameter((Object)roll, (String)"roll");
        return this.withRotation(Float.valueOf(AngleExtensionsKt.toRadians(Float.valueOf(pitch.floatValue()))), Float.valueOf(AngleExtensionsKt.toRadians(Float.valueOf(yaw.floatValue()))), Float.valueOf(AngleExtensionsKt.toRadians(Float.valueOf(roll.floatValue()))));
    }

    @NotNull
    public final ModelPartTransformation addRotationDegrees(int axis, @NotNull Number angle) {
        Intrinsics.checkNotNullParameter((Object)angle, (String)"angle");
        return this.addRotation(axis, Float.valueOf(this.rotation[axis] + AngleExtensionsKt.toRadians(Float.valueOf(angle.floatValue()))));
    }

    @NotNull
    public final ModelPartTransformation withScale(int axis, @NotNull Number scale) {
        Intrinsics.checkNotNullParameter((Object)scale, (String)"scale");
        this.scale[axis] = scale.floatValue();
        return this;
    }

    @NotNull
    public final ModelPartTransformation withScale(@NotNull Number scaleX, @NotNull Number scaleY, @NotNull Number scaleZ) {
        Intrinsics.checkNotNullParameter((Object)scaleX, (String)"scaleX");
        Intrinsics.checkNotNullParameter((Object)scaleY, (String)"scaleY");
        Intrinsics.checkNotNullParameter((Object)scaleZ, (String)"scaleZ");
        return this.withScale(0, scaleX).withScale(1, scaleY).withScale(2, scaleZ);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\b\u001a\u00020\u00078\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u00078\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\n\u0010\tR\u0014\u0010\u000b\u001a\u00020\u00078\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\t\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation$Companion;", "", "Lnet/minecraft/client/model/geom/ModelPart;", "modelPart", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;", "derive", "(Lnet/minecraft/client/model/geom/ModelPart;)Lcom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation;", "", "X_AXIS", "I", "Y_AXIS", "Z_AXIS", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ModelPartTransformation derive(@NotNull ModelPart modelPart) {
            Intrinsics.checkNotNullParameter((Object)modelPart, (String)"modelPart");
            return new ModelPartTransformation(modelPart).withPosition(Float.valueOf(modelPart.f_104200_), Float.valueOf(modelPart.f_104201_), Float.valueOf(modelPart.f_104202_)).withRotation(Float.valueOf(modelPart.f_104203_), Float.valueOf(modelPart.f_104204_), Float.valueOf(modelPart.f_104205_)).withScale(Float.valueOf(modelPart.f_233553_), Float.valueOf(modelPart.f_233554_), Float.valueOf(modelPart.f_233555_)).withVisibility(modelPart.f_104207_);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

