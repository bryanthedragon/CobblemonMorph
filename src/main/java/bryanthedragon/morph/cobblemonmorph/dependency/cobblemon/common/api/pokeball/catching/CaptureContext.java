/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.catching;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u0000 \u001a2\u00020\u0001:\u0001\u001aB\u001f\u0012\u0006\u0010\t\u001a\u00020\u0002\u0012\u0006\u0010\n\u001a\u00020\u0005\u0012\u0006\u0010\u000b\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\b\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\b\u0010\u0007J.\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\t\u001a\u00020\u00022\b\b\u0002\u0010\n\u001a\u00020\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\f\u0010\rJ\u001a\u0010\u000f\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0011\u001a\u00020\u0002H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0004J\u0010\u0010\u0013\u001a\u00020\u0012H\u00d6\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014R\u0017\u0010\u000b\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\u0015\u001a\u0004\b\u000b\u0010\u0007R\u0017\u0010\n\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u0015\u001a\u0004\b\n\u0010\u0007R\u0017\u0010\t\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0016\u001a\u0004\b\u0017\u0010\u0004\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "", "", "component1", "()I", "", "component2", "()Z", "component3", "numberOfShakes", "isSuccessfulCapture", "isCriticalCapture", "copy", "(IZZ)Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "other", "equals", "(Ljava/lang/Object;)Z", "hashCode", "", "toString", "()Ljava/lang/String;", "Z", "I", "getNumberOfShakes", "<init>", "(IZZ)V", "Companion", "common"})
public final class CaptureContext {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final int numberOfShakes;
    private final boolean isSuccessfulCapture;
    private final boolean isCriticalCapture;

    public CaptureContext(int numberOfShakes, boolean isSuccessfulCapture, boolean isCriticalCapture) {
        this.numberOfShakes = numberOfShakes;
        this.isSuccessfulCapture = isSuccessfulCapture;
        this.isCriticalCapture = isCriticalCapture;
    }

    public final int getNumberOfShakes() {
        return this.numberOfShakes;
    }

    public final boolean isSuccessfulCapture() {
        return this.isSuccessfulCapture;
    }

    public final boolean isCriticalCapture() {
        return this.isCriticalCapture;
    }

    public final int component1() {
        return this.numberOfShakes;
    }

    public final boolean component2() {
        return this.isSuccessfulCapture;
    }

    public final boolean component3() {
        return this.isCriticalCapture;
    }

    @NotNull
    public final CaptureContext copy(int numberOfShakes, boolean isSuccessfulCapture, boolean isCriticalCapture) {
        return new CaptureContext(numberOfShakes, isSuccessfulCapture, isCriticalCapture);
    }

    public static /* synthetic */ CaptureContext copy$default(CaptureContext captureContext, int n, boolean bl, boolean bl2, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = captureContext.numberOfShakes;
        }
        if ((n2 & 2) != 0) {
            bl = captureContext.isSuccessfulCapture;
        }
        if ((n2 & 4) != 0) {
            bl2 = captureContext.isCriticalCapture;
        }
        return captureContext.copy(n, bl, bl2);
    }

    @NotNull
    public String toString() {
        return "CaptureContext(numberOfShakes=" + this.numberOfShakes + ", isSuccessfulCapture=" + this.isSuccessfulCapture + ", isCriticalCapture=" + this.isCriticalCapture + ")";
    }

    public int hashCode() {
        int result = Integer.hashCode(this.numberOfShakes);
        int n = this.isSuccessfulCapture ? 1 : 0;
        if (n != 0) {
            n = 1;
        }
        result = result * 31 + n;
        int n2 = this.isCriticalCapture ? 1 : 0;
        if (n2 != 0) {
            n2 = 1;
        }
        result = result * 31 + n2;
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CaptureContext)) {
            return false;
        }
        CaptureContext captureContext = (CaptureContext)other;
        if (this.numberOfShakes != captureContext.numberOfShakes) {
            return false;
        }
        if (this.isSuccessfulCapture != captureContext.isSuccessfulCapture) {
            return false;
        }
        return this.isCriticalCapture == captureContext.isCriticalCapture;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext$Companion;", "", "", "critical", "Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "successful", "(Z)Lcom/cobblemon/mod/common/api/pokeball/catching/CaptureContext;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final CaptureContext successful(boolean critical) {
            if (critical) {
                return new CaptureContext(1, true, true);
            }
            return new CaptureContext(4, true, false);
        }

        public static /* synthetic */ CaptureContext successful$default(Companion companion, boolean bl, int n, Object object) {
            if ((n & 1) != 0) {
                bl = false;
            }
            return companion.successful(bl);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

