/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.annotation.AnnotationRetention
 *  kotlin.annotation.AnnotationTarget
 *  kotlin.annotation.Retention
 *  kotlin.annotation.Target
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config.Category;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;
import kotlin.annotation.Target;

@Target(allowedTargets={AnnotationTarget.FIELD})
@Retention(value=AnnotationRetention.RUNTIME)
@java.lang.annotation.Retention(value=RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(value={ElementType.FIELD})
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0087\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/config/NodeCategory;", "", "Lcom/cobblemon/mod/common/config/Category;", "category", "()Lcom/cobblemon/mod/common/config/Category;", "<init>", "(Lcom/cobblemon/mod/common/config/Category;)V", "common"})
public @interface NodeCategory {
    public Category category();
}

