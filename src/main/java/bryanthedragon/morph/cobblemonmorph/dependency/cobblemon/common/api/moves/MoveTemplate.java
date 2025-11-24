/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.SerializedName
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.MoStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.QueryStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.StringValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.animations.ActionEffectTimeline;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategories;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.MoveTarget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.function.Function;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000`\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\b\u0016\u0018\u0000 I2\u00020\u0001:\u0002IJBo\u0012\u0006\u0010/\u001a\u00020.\u0012\u0006\u00103\u001a\u00020\u0005\u0012\u0006\u0010'\u001a\u00020&\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u0012\u0006\u00106\u001a\u00020\n\u0012\u0006\u0010C\u001a\u00020B\u0012\u0006\u0010\u000b\u001a\u00020\n\u0012\u0006\u00108\u001a\u00020\u0005\u0012\u0006\u0010:\u001a\u00020\u0005\u0012\u0006\u0010\u0014\u001a\u00020\n\u0012\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\n0!\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\u0004\bG\u0010HJ\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0015\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0003\u0010\u0007J\u001d\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0003\u0010\tR\u0017\u0010\u000b\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u0019\u0010\u0010\u001a\u0004\u0018\u00010\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0014\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\f\u001a\u0004\b\u0015\u0010\u000eR\u0017\u0010\u0017\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\u001e\u001a\u00020\u001b8F\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010 \u001a\u00020\u001b8F\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010\u001dR\u001d\u0010\"\u001a\b\u0012\u0004\u0012\u00020\n0!8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R\u001a\u0010'\u001a\u00020&8\u0006X\u0087\u0004\u00a2\u0006\f\n\u0004\b'\u0010(\u001a\u0004\b)\u0010*R\u0011\u0010-\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b+\u0010,R\u0017\u0010/\u001a\u00020.8\u0006\u00a2\u0006\f\n\u0004\b/\u00100\u001a\u0004\b1\u00102R\u0017\u00103\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b3\u00104\u001a\u0004\b5\u0010,R\u0017\u00106\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b6\u0010\f\u001a\u0004\b7\u0010\u000eR\u0017\u00108\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b8\u00104\u001a\u0004\b9\u0010,R\u0017\u0010:\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b:\u00104\u001a\u0004\b;\u0010,R\u001b\u0010A\u001a\u00020<8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b=\u0010>\u001a\u0004\b?\u0010@R\u0017\u0010C\u001a\u00020B8\u0006\u00a2\u0006\f\n\u0004\bC\u0010D\u001a\u0004\bE\u0010F\u00a8\u0006K"}, d2={"Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "", "Lcom/cobblemon/mod/common/api/moves/Move;", "create", "()Lcom/cobblemon/mod/common/api/moves/Move;", "", "currentPp", "(I)Lcom/cobblemon/mod/common/api/moves/Move;", "raisedPpStages", "(II)Lcom/cobblemon/mod/common/api/moves/Move;", "", "accuracy", "D", "getAccuracy", "()D", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;", "actionEffect", "Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;", "getActionEffect", "()Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;", "critRatio", "getCritRatio", "Lcom/cobblemon/mod/common/api/moves/categories/DamageCategory;", "damageCategory", "Lcom/cobblemon/mod/common/api/moves/categories/DamageCategory;", "getDamageCategory", "()Lcom/cobblemon/mod/common/api/moves/categories/DamageCategory;", "Lnet/minecraft/network/chat/MutableComponent;", "getDescription", "()Lnet/minecraft/network/chat/MutableComponent;", "description", "getDisplayName", "displayName", "", "effectChances", "[Ljava/lang/Double;", "getEffectChances", "()[Ljava/lang/Double;", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "elementalType", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "getElementalType", "()Lcom/cobblemon/mod/common/api/types/ElementalType;", "getMaxPp", "()I", "maxPp", "", "name", "Ljava/lang/String;", "getName", "()Ljava/lang/String;", "num", "I", "getNum", "power", "getPower", "pp", "getPp", "priority", "getPriority", "Lcom/bedrockk/molang/runtime/struct/MoStruct;", "struct$delegate", "Lkotlin/Lazy;", "getStruct", "()Lcom/bedrockk/molang/runtime/struct/MoStruct;", "struct", "Lcom/cobblemon/mod/common/battles/MoveTarget;", "target", "Lcom/cobblemon/mod/common/battles/MoveTarget;", "getTarget", "()Lcom/cobblemon/mod/common/battles/MoveTarget;", "<init>", "(Ljava/lang/String;ILcom/cobblemon/mod/common/api/types/ElementalType;Lcom/cobblemon/mod/common/api/moves/categories/DamageCategory;DLcom/cobblemon/mod/common/battles/MoveTarget;DIID[Ljava/lang/Double;Lcom/cobblemon/mod/common/api/moves/animations/ActionEffectTimeline;)V", "Companion", "Dummy", "common"})
public class MoveTemplate {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final String name;
    private final int num;
    @SerializedName(value="type")
    @NotNull
    private final ElementalType elementalType;
    @NotNull
    private final DamageCategory damageCategory;
    private final double power;
    @NotNull
    private final MoveTarget target;
    private final double accuracy;
    private final int pp;
    private final int priority;
    private final double critRatio;
    @NotNull
    private final Double[] effectChances;
    @Nullable
    private final ActionEffectTimeline actionEffect;
    @NotNull
    private final Lazy struct$delegate;

    public MoveTemplate(@NotNull String name, int num, @NotNull ElementalType elementalType, @NotNull DamageCategory damageCategory, double power, @NotNull MoveTarget target, double accuracy, int pp, int priority, double critRatio, @NotNull Double[] effectChances, @Nullable ActionEffectTimeline actionEffect) {
        Intrinsics.checkNotNullParameter((Object)name, (String)"name");
        Intrinsics.checkNotNullParameter((Object)elementalType, (String)"elementalType");
        Intrinsics.checkNotNullParameter((Object)damageCategory, (String)"damageCategory");
        Intrinsics.checkNotNullParameter((Object)((Object)target), (String)"target");
        Intrinsics.checkNotNullParameter((Object)effectChances, (String)"effectChances");
        this.name = name;
        this.num = num;
        this.elementalType = elementalType;
        this.damageCategory = damageCategory;
        this.power = power;
        this.target = target;
        this.accuracy = accuracy;
        this.pp = pp;
        this.priority = priority;
        this.critRatio = critRatio;
        this.effectChances = effectChances;
        this.actionEffect = actionEffect;
        this.struct$delegate = LazyKt.lazy((Function0)((Function0)new Function0<QueryStruct>(this){
            final /* synthetic */ MoveTemplate this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            public final QueryStruct invoke() {
                return new QueryStruct(new HashMap<String, Function<MoParams, Object>>()).addFunction("name", arg_0 -> struct.2.invoke$lambda$0(this.this$0, arg_0)).addFunction("type", arg_0 -> struct.2.invoke$lambda$1(this.this$0, arg_0)).addFunction("damage_category", arg_0 -> struct.2.invoke$lambda$2(this.this$0, arg_0)).addFunction("power", arg_0 -> struct.2.invoke$lambda$3(this.this$0, arg_0)).addFunction("target", arg_0 -> struct.2.invoke$lambda$4(this.this$0, arg_0)).addFunction("accuracy", arg_0 -> struct.2.invoke$lambda$5(this.this$0, arg_0)).addFunction("pp", arg_0 -> struct.2.invoke$lambda$6(this.this$0, arg_0)).addFunction("priority", arg_0 -> struct.2.invoke$lambda$7(this.this$0, arg_0)).addFunction("crit_ratio", arg_0 -> struct.2.invoke$lambda$8(this.this$0, arg_0));
            }

            private static final Object invoke$lambda$0(MoveTemplate this$0, MoParams it) {
                Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
                return new StringValue(this$0.getName());
            }

            private static final Object invoke$lambda$1(MoveTemplate this$0, MoParams it) {
                Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
                return new StringValue(this$0.getElementalType().getName());
            }

            private static final Object invoke$lambda$2(MoveTemplate this$0, MoParams it) {
                Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
                return new StringValue(this$0.getDamageCategory().getName());
            }

            private static final Object invoke$lambda$3(MoveTemplate this$0, MoParams it) {
                Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
                return new DoubleValue(this$0.getPower());
            }

            private static final Object invoke$lambda$4(MoveTemplate this$0, MoParams it) {
                Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
                return new StringValue(this$0.getTarget().name());
            }

            private static final Object invoke$lambda$5(MoveTemplate this$0, MoParams it) {
                Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
                return new DoubleValue(this$0.getAccuracy());
            }

            private static final Object invoke$lambda$6(MoveTemplate this$0, MoParams it) {
                Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
                return new DoubleValue(this$0.getPp());
            }

            private static final Object invoke$lambda$7(MoveTemplate this$0, MoParams it) {
                Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
                return new DoubleValue(this$0.getPriority());
            }

            private static final Object invoke$lambda$8(MoveTemplate this$0, MoParams it) {
                Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
                return new DoubleValue(this$0.getCritRatio());
            }
        }));
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final int getNum() {
        return this.num;
    }

    @NotNull
    public final ElementalType getElementalType() {
        return this.elementalType;
    }

    @NotNull
    public final DamageCategory getDamageCategory() {
        return this.damageCategory;
    }

    public final double getPower() {
        return this.power;
    }

    @NotNull
    public final MoveTarget getTarget() {
        return this.target;
    }

    public final double getAccuracy() {
        return this.accuracy;
    }

    public final int getPp() {
        return this.pp;
    }

    public final int getPriority() {
        return this.priority;
    }

    public final double getCritRatio() {
        return this.critRatio;
    }

    @NotNull
    public final Double[] getEffectChances() {
        return this.effectChances;
    }

    @Nullable
    public final ActionEffectTimeline getActionEffect() {
        return this.actionEffect;
    }

    @NotNull
    public final MoStruct getStruct() {
        Lazy lazy = this.struct$delegate;
        Object object = lazy.getValue();
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"<get-struct>(...)");
        return (MoStruct)object;
    }

    @NotNull
    public final MutableComponent getDisplayName() {
        MutableComponent mutableComponent = LocalizationUtilsKt.lang("move." + this.name, new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"move.$name\")");
        return mutableComponent;
    }

    @NotNull
    public final MutableComponent getDescription() {
        MutableComponent mutableComponent = LocalizationUtilsKt.lang("move." + this.name + ".desc", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"move.$name.desc\")");
        return mutableComponent;
    }

    public final int getMaxPp() {
        return 8 * this.pp / 5;
    }

    @NotNull
    public final Move create() {
        return this.create(this.pp);
    }

    @NotNull
    public final Move create(int currentPp) {
        return this.create(currentPp, 0);
    }

    @NotNull
    public final Move create(int currentPp, int raisedPpStages) {
        return new Move(this, currentPp, raisedPpStages);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/moves/MoveTemplate$Companion;", "", "", "name", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate$Dummy;", "dummy", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/moves/MoveTemplate$Dummy;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Dummy dummy(@NotNull String name) {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            return new Dummy(name);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/api/moves/MoveTemplate$Dummy;", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "", "name", "<init>", "(Ljava/lang/String;)V", "common"})
    @SourceDebugExtension(value={"SMAP\nMoveTemplate.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoveTemplate.kt\ncom/cobblemon/mod/common/api/moves/MoveTemplate$Dummy\n+ 2 ArrayIntrinsics.kt\nkotlin/ArrayIntrinsicsKt\n*L\n1#1,117:1\n26#2:118\n*S KotlinDebug\n*F\n+ 1 MoveTemplate.kt\ncom/cobblemon/mod/common/api/moves/MoveTemplate$Dummy\n*L\n88#1:118\n*E\n"})
    public static final class Dummy
    extends MoveTemplate {
        public Dummy(@NotNull String name) {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            boolean $i$f$emptyArray = false;
            super(name, -1, ElementalTypes.INSTANCE.getNORMAL(), DamageCategories.INSTANCE.getSTATUS(), 0.0, MoveTarget.all, 100.0, 5, 0, 0.0, new Double[0], null);
        }
    }
}

