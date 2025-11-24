/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.MutablePropertyReference1
 *  kotlin.jvm.internal.MutablePropertyReference1Impl
 *  kotlin.jvm.internal.Reflection
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.properties.Delegates
 *  kotlin.properties.ObservableProperty
 *  kotlin.properties.ReadWriteProperty
 *  kotlin.reflect.KProperty
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.categories.DamageCategory;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.types.ElementalType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.properties.Delegates;
import kotlin.properties.ObservableProperty;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KProperty;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0006\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0011\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0016\u0018\u0000 R2\u00020\u0001:\u0001RB!\u0012\u0006\u0010H\u001a\u00020G\u0012\u0006\u0010%\u001a\u00020\u0007\u0012\b\b\u0002\u0010F\u001a\u00020\u0007\u00a2\u0006\u0004\bP\u0010QJ\u001b\u0010\u0005\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0015\u0010\u000e\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0015\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0015\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b\u0016\u0010\u0017J\r\u0010\u0018\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0018\u0010\u0019R\u0011\u0010\u001d\u001a\u00020\u001a8F\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR+\u0010%\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\u00078F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u0011\u0010)\u001a\u00020&8F\u00a2\u0006\u0006\u001a\u0004\b'\u0010(R\u0011\u0010-\u001a\u00020*8F\u00a2\u0006\u0006\u001a\u0004\b+\u0010,R\u0011\u0010/\u001a\u00020*8F\u00a2\u0006\u0006\u001a\u0004\b.\u0010,R\u0017\u00103\u001a\b\u0012\u0004\u0012\u00020\u001a008F\u00a2\u0006\u0006\u001a\u0004\b1\u00102R\u0016\u00104\u001a\u00020\t8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b4\u00105R\u0011\u00107\u001a\u00020\u00078F\u00a2\u0006\u0006\u001a\u0004\b6\u0010\"R\u0011\u0010;\u001a\u0002088F\u00a2\u0006\u0006\u001a\u0004\b9\u0010:R\u001d\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00000<8\u0006\u00a2\u0006\f\n\u0004\b=\u0010>\u001a\u0004\b?\u0010@R\u0011\u0010B\u001a\u00020\u001a8F\u00a2\u0006\u0006\u001a\u0004\bA\u0010\u001cR+\u0010F\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\u00078F@FX\u0086\u008e\u0002\u00a2\u0006\u0012\n\u0004\bC\u0010 \u001a\u0004\bD\u0010\"\"\u0004\bE\u0010$R\u0017\u0010H\u001a\u00020G8\u0006\u00a2\u0006\f\n\u0004\bH\u0010I\u001a\u0004\bJ\u0010KR\u0011\u0010O\u001a\u00020L8F\u00a2\u0006\u0006\u001a\u0004\bM\u0010N\u00a8\u0006S"}, d2={"Lcom/cobblemon/mod/common/api/moves/Move;", "", "Lkotlin/Function0;", "", "action", "doThenUpdate", "(Lkotlin/jvm/functions/Function0;)V", "", "amount", "", "raiseMaxPP", "(I)Z", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lcom/google/gson/JsonObject;", "json", "saveToJSON", "(Lcom/google/gson/JsonObject;)Lcom/google/gson/JsonObject;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "saveToNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", "update", "()V", "", "getAccuracy", "()D", "accuracy", "<set-?>", "currentPp$delegate", "Lkotlin/properties/ReadWriteProperty;", "getCurrentPp", "()I", "setCurrentPp", "(I)V", "currentPp", "Lcom/cobblemon/mod/common/api/moves/categories/DamageCategory;", "getDamageCategory", "()Lcom/cobblemon/mod/common/api/moves/categories/DamageCategory;", "damageCategory", "Lnet/minecraft/network/chat/MutableComponent;", "getDescription", "()Lnet/minecraft/network/chat/MutableComponent;", "description", "getDisplayName", "displayName", "", "getEffectChances", "()[Ljava/lang/Double;", "effectChances", "emit", "Z", "getMaxPp", "maxPp", "", "getName", "()Ljava/lang/String;", "name", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getPower", "power", "raisedPpStages$delegate", "getRaisedPpStages", "setRaisedPpStages", "raisedPpStages", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "template", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "getTemplate", "()Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "Lcom/cobblemon/mod/common/api/types/ElementalType;", "getType", "()Lcom/cobblemon/mod/common/api/types/ElementalType;", "type", "<init>", "(Lcom/cobblemon/mod/common/api/moves/MoveTemplate;II)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nMove.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Move.kt\ncom/cobblemon/mod/common/api/moves/Move\n+ 2 Delegates.kt\nkotlin/properties/Delegates\n*L\n1#1,146:1\n33#2,3:147\n33#2,3:150\n*S KotlinDebug\n*F\n+ 1 Move.kt\ncom/cobblemon/mod/common/api/moves/Move\n*L\n36#1:147,3\n42#1:150,3\n*E\n"})
public class Move {
    @NotNull
    public static final Companion Companion;
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties;
    @NotNull
    private final MoveTemplate template;
    private boolean emit;
    @NotNull
    private final SimpleObservable<Move> observable;
    @NotNull
    private final ReadWriteProperty currentPp$delegate;
    @NotNull
    private final ReadWriteProperty raisedPpStages$delegate;

    public Move(@NotNull MoveTemplate template, int currentPp, int raisedPpStages) {
        Intrinsics.checkNotNullParameter((Object)template, (String)"template");
        this.template = template;
        this.emit = true;
        this.observable = new SimpleObservable();
        Delegates delegates = Delegates.INSTANCE;
        Integer initialValue$iv = currentPp;
        boolean $i$f$observable = false;
        this.currentPp$delegate = (ReadWriteProperty)new ObservableProperty<Integer>((Object)initialValue$iv, this){
            final /* synthetic */ Move this$0;
            {
                this.this$0 = move;
                super($initialValue);
            }

            /*
             * Ignored method signature, as it can't be verified against descriptor
             * WARNING - void declaration
             */
            protected void afterChange(@NotNull KProperty property, Object oldValue, Object newValue) {
                void newValue2;
                Intrinsics.checkNotNullParameter((Object)property, (String)"property");
                int n = ((Number)newValue).intValue();
                int oldValue2 = ((Number)oldValue).intValue();
                boolean bl = false;
                if (oldValue2 != newValue2) {
                    this.this$0.update();
                }
            }
        };
        Delegates this_$iv = Delegates.INSTANCE;
        initialValue$iv = raisedPpStages;
        $i$f$observable = false;
        this.raisedPpStages$delegate = (ReadWriteProperty)new ObservableProperty<Integer>((Object)initialValue$iv, this){
            final /* synthetic */ Move this$0;
            {
                this.this$0 = move;
                super($initialValue);
            }

            /*
             * Ignored method signature, as it can't be verified against descriptor
             * WARNING - void declaration
             */
            protected void afterChange(@NotNull KProperty property, Object oldValue, Object newValue) {
                void newValue2;
                Intrinsics.checkNotNullParameter((Object)property, (String)"property");
                int n = ((Number)newValue).intValue();
                int oldValue2 = ((Number)oldValue).intValue();
                boolean bl = false;
                if (oldValue2 != newValue2) {
                    this.this$0.update();
                }
            }
        };
    }

    public /* synthetic */ Move(MoveTemplate moveTemplate, int n, int n2, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 4) != 0) {
            n2 = 0;
        }
        this(moveTemplate, n, n2);
    }

    @NotNull
    public final MoveTemplate getTemplate() {
        return this.template;
    }

    @NotNull
    public final SimpleObservable<Move> getObservable() {
        return this.observable;
    }

    public final int getCurrentPp() {
        return ((Number)this.currentPp$delegate.getValue((Object)this, $$delegatedProperties[0])).intValue();
    }

    public final void setCurrentPp(int n) {
        this.currentPp$delegate.setValue((Object)this, $$delegatedProperties[0], (Object)n);
    }

    public final int getRaisedPpStages() {
        return ((Number)this.raisedPpStages$delegate.getValue((Object)this, $$delegatedProperties[1])).intValue();
    }

    public final void setRaisedPpStages(int n) {
        this.raisedPpStages$delegate.setValue((Object)this, $$delegatedProperties[1], (Object)n);
    }

    public final void doThenUpdate(@NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        boolean oldEmit = this.emit;
        this.emit = false;
        action2.invoke();
        this.emit = oldEmit;
        this.update();
    }

    public final void update() {
        if (this.emit) {
            Move[] moveArray = new Move[]{this};
            this.observable.emit((Move[])moveArray);
        }
    }

    @NotNull
    public final String getName() {
        return this.template.getName();
    }

    @NotNull
    public final MutableComponent getDisplayName() {
        return this.template.getDisplayName();
    }

    @NotNull
    public final MutableComponent getDescription() {
        return this.template.getDescription();
    }

    @NotNull
    public final ElementalType getType() {
        return this.template.getElementalType();
    }

    @NotNull
    public final DamageCategory getDamageCategory() {
        return this.template.getDamageCategory();
    }

    public final double getPower() {
        return this.template.getPower();
    }

    public final double getAccuracy() {
        return this.template.getAccuracy();
    }

    @NotNull
    public final Double[] getEffectChances() {
        return this.template.getEffectChances();
    }

    public final int getMaxPp() {
        return this.template.getPp() + this.getRaisedPpStages() * this.template.getPp() / 5;
    }

    public final boolean raiseMaxPP(int amount) {
        int oldPp = this.getMaxPp();
        this.doThenUpdate((Function0<Unit>)((Function0)new Function0<Unit>(this, amount){
            final /* synthetic */ Move this$0;
            final /* synthetic */ int $amount;
            {
                this.this$0 = $receiver;
                this.$amount = $amount;
                super(0);
            }

            public final void invoke() {
                float ppRatio = (float)this.this$0.getCurrentPp() / (float)this.this$0.getMaxPp();
                Move move = this.this$0;
                move.setRaisedPpStages(move.getRaisedPpStages() + this.$amount);
                if (this.this$0.getRaisedPpStages() > 3) {
                    this.this$0.setRaisedPpStages(3);
                }
                this.this$0.setCurrentPp((int)Math.ceil(ppRatio * (float)this.this$0.getMaxPp()));
            }
        }));
        return oldPp != this.getMaxPp();
    }

    @NotNull
    public final CompoundTag saveToNBT(@NotNull CompoundTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        nbt.m_128359_("MoveName", this.getName());
        nbt.m_128405_("MovePP", this.getCurrentPp());
        nbt.m_128405_("RaisedPPStages", this.getRaisedPpStages());
        return nbt;
    }

    @NotNull
    public final JsonObject saveToJSON(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        json.addProperty("MoveName", this.getName());
        json.addProperty("MovePP", (Number)this.getCurrentPp());
        json.addProperty("RaisedPPStages", (Number)this.getRaisedPpStages());
        return json;
    }

    public final void saveToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(this.getName());
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.getCurrentPp());
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.getRaisedPpStages());
    }

    static {
        KProperty[] kPropertyArray = new KProperty[]{Reflection.mutableProperty1((MutablePropertyReference1)((MutablePropertyReference1)new MutablePropertyReference1Impl(Move.class, "currentPp", "getCurrentPp()I", 0))), Reflection.mutableProperty1((MutablePropertyReference1)((MutablePropertyReference1)new MutablePropertyReference1Impl(Move.class, "raisedPpStages", "getRaisedPpStages()I", 0)))};
        $$delegatedProperties = kPropertyArray;
        Companion = new Companion(null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\t\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\t\u0010\nJ\u0015\u0010\r\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/api/moves/Move$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/api/moves/Move;", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/api/moves/Move;", "Lcom/google/gson/JsonObject;", "json", "loadFromJSON", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/moves/Move;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/moves/Move;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Move loadFromNBT(@NotNull CompoundTag nbt) {
            Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
            String moveName = nbt.m_128461_("MoveName");
            Intrinsics.checkNotNullExpressionValue((Object)moveName, (String)"moveName");
            MoveTemplate template = Moves.INSTANCE.getByNameOrDummy(moveName);
            return template.create(nbt.m_128451_("MovePP"), nbt.m_128451_("RaisedPPStages"));
        }

        @NotNull
        public final Move loadFromJSON(@NotNull JsonObject json) {
            Intrinsics.checkNotNullParameter((Object)json, (String)"json");
            String moveName = json.get("MoveName").getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)moveName, (String)"moveName");
            MoveTemplate template = Moves.INSTANCE.getByNameOrDummy(moveName);
            int currentPp = json.get("MovePP").getAsInt();
            JsonElement jsonElement = json.get("RaisedPPStages");
            int raisedPpStages = jsonElement != null ? jsonElement.getAsInt() : 0;
            return new Move(template, currentPp, raisedPpStages);
        }

        @NotNull
        public final Move loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            String moveName = buffer.m_130277_();
            int currentPp = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
            int raisedPpStages = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
            Intrinsics.checkNotNullExpressionValue((Object)moveName, (String)"moveName");
            MoveTemplate template = Moves.INSTANCE.getByNameOrDummy(moveName);
            return template.create(currentPp, raisedPpStages);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

