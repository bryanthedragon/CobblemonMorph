/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\u0006R\u0014\u0010\b\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0006R\u0014\u0010\n\u001a\u00020\t8\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\f\u0010\u0006\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/CobblemonBuildDetails;", "", "", "smallCommitHash", "()Ljava/lang/String;", "BRANCH", "Ljava/lang/String;", "GIT_COMMIT", "MOD_ID", "", "SNAPSHOT", "Z", "VERSION", "<init>", "()V", "common"})
public final class CobblemonBuildDetails {
    @NotNull
    public static final CobblemonBuildDetails INSTANCE = new CobblemonBuildDetails();
    @NotNull
    public static final String MOD_ID = "cobblemon";
    @NotNull
    public static final String VERSION = "1.5.2";
    public static final boolean SNAPSHOT = false;
    @NotNull
    public static final String GIT_COMMIT = "df8f078d13702ab9a000438910b822ceffbb2248";
    @NotNull
    public static final String BRANCH = "HEAD";

    private CobblemonBuildDetails() {
    }

    @NotNull
    public final String smallCommitHash() {
        String string = GIT_COMMIT.substring(0, 7);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String\u2026ing(startIndex, endIndex)");
        return string;
    }
}

