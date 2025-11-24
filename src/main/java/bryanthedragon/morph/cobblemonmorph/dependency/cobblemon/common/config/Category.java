/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.config;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\f\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/config/Category;", "", "<init>", "(Ljava/lang/String;I)V", "Starter", "Pokemon", "Spawning", "Battles", "PassiveStatus", "Healing", "Storage", "World", "Debug", "common"})
public final class Category
extends Enum<Category> {
    public static final /* enum */ Category Starter = new Category();
    public static final /* enum */ Category Pokemon = new Category();
    public static final /* enum */ Category Spawning = new Category();
    public static final /* enum */ Category Battles = new Category();
    public static final /* enum */ Category PassiveStatus = new Category();
    public static final /* enum */ Category Healing = new Category();
    public static final /* enum */ Category Storage = new Category();
    public static final /* enum */ Category World = new Category();
    public static final /* enum */ Category Debug = new Category();
    private static final /* synthetic */ Category[] $VALUES;

    public static Category[] values() {
        return (Category[])$VALUES.clone();
    }

    public static Category valueOf(String value2) {
        return Enum.valueOf(Category.class, value2);
    }

    static {
        $VALUES = categoryArray = new Category[]{Category.Starter, Category.Pokemon, Category.Spawning, Category.Battles, Category.PassiveStatus, Category.Healing, Category.Storage, Category.World, Category.Debug};
    }
}

