/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.QueryStruct;
import java.util.HashMap;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B;\u0012\u0006\u0010\u0010\u001a\u00028\u0000\u0012\u0014\b\u0002\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00060\u000b\u0012\u0014\b\u0002\u0010\f\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00030\u000b\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u000f\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000H\u0016\u00a2\u0006\u0004\b\t\u0010\nR#\u0010\f\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00030\u000b8\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\"\u0010\u0010\u001a\u00028\u00008\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R#\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00060\u000b8\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\r\u001a\u0004\b\u0017\u0010\u000f\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/api/molang/ObjectValue;", "T", "Lcom/bedrockk/molang/runtime/struct/QueryStruct;", "", "asDouble", "()D", "", "asString", "()Ljava/lang/String;", "value", "()Lcom/cobblemon/mod/common/api/molang/ObjectValue;", "Lkotlin/Function1;", "doublify", "Lkotlin/jvm/functions/Function1;", "getDoublify", "()Lkotlin/jvm/functions/Function1;", "obj", "Ljava/lang/Object;", "getObj", "()Ljava/lang/Object;", "setObj", "(Ljava/lang/Object;)V", "stringify", "getStringify", "<init>", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "common"})
public final class ObjectValue<T>
extends QueryStruct {
    private T obj;
    @NotNull
    private final Function1<T, String> stringify;
    @NotNull
    private final Function1<T, Double> doublify;

    public ObjectValue(T obj, @NotNull Function1<? super T, String> stringify, @NotNull Function1<? super T, Double> doublify) {
        Intrinsics.checkNotNullParameter(stringify, (String)"stringify");
        Intrinsics.checkNotNullParameter(doublify, (String)"doublify");
        super(new HashMap<String, Function<MoParams, Object>>());
        this.obj = obj;
        this.stringify = stringify;
        this.doublify = doublify;
    }

    public /* synthetic */ ObjectValue(Object object, Function1 function1, Function1 function12, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            function1 = 1.INSTANCE;
        }
        if ((n & 4) != 0) {
            function12 = 2.INSTANCE;
        }
        this(object, function1, function12);
    }

    public final T getObj() {
        return this.obj;
    }

    public final void setObj(T t) {
        this.obj = t;
    }

    @NotNull
    public final Function1<T, String> getStringify() {
        return this.stringify;
    }

    @NotNull
    public final Function1<T, Double> getDoublify() {
        return this.doublify;
    }

    @Override
    @NotNull
    public ObjectValue<T> value() {
        return this;
    }

    @Override
    public double asDouble() {
        return ((Number)this.doublify.invoke(this.obj)).doubleValue();
    }

    @Override
    @NotNull
    public String asString() {
        return (String)this.stringify.invoke(this.obj);
    }
}

