/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.JvmField
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.item.Item
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b>\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\bA\u0010BJ;\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR8\u0010\t\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\t\u0010\nR8\u0010\u000b\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\nR8\u0010\f\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\nR8\u0010\r\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\nR8\u0010\u000e\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\nR8\u0010\u000f\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\nR8\u0010\u0010\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\nR8\u0010\u0011\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\nR8\u0010\u0012\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\nR8\u0010\u0013\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\nR8\u0010\u0014\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\nR8\u0010\u0015\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\nR8\u0010\u0016\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\nR8\u0010\u0017\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\nR8\u0010\u0018\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\nR8\u0010\u0019\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\nR8\u0010\u001a\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\nR8\u0010\u001b\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001b\u0010\nR8\u0010\u001c\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\nR8\u0010\u001d\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001d\u0010\nR8\u0010\u001e\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001e\u0010\nR8\u0010\u001f\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001f\u0010\nR8\u0010 \u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b \u0010\nR8\u0010!\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b!\u0010\nR8\u0010\"\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\"\u0010\nR8\u0010#\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b#\u0010\nR8\u0010$\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b$\u0010\nR8\u0010%\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b%\u0010\nR8\u0010&\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b&\u0010\nR8\u0010'\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b'\u0010\nR8\u0010(\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b(\u0010\nR8\u0010)\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b)\u0010\nR8\u0010*\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b*\u0010\nR8\u0010+\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b+\u0010\nR8\u0010,\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b,\u0010\nR8\u0010-\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b-\u0010\nR8\u0010.\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b.\u0010\nR8\u0010/\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b/\u0010\nR8\u00100\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b0\u0010\nR8\u00101\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b1\u0010\nR8\u00102\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b2\u0010\nR8\u00103\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b3\u0010\nR8\u00104\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b4\u0010\nR8\u00105\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b5\u0010\nR8\u00106\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b6\u0010\nR8\u00107\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b7\u0010\nR8\u00108\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b8\u0010\nR8\u00109\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b9\u0010\nR8\u0010:\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b:\u0010\nR8\u0010;\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b;\u0010\nR8\u0010<\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b<\u0010\nR8\u0010=\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b=\u0010\nR8\u0010>\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b>\u0010\nR8\u0010?\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b?\u0010\nR8\u0010@\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b@\u0010\n\u00a8\u0006C"}, d2={"Lcom/cobblemon/mod/common/api/tags/CobblemonItemTags;", "", "", "path", "Lnet/minecraft/tags/TagKey;", "Lnet/minecraft/world/item/Item;", "kotlin.jvm.PlatformType", "create", "(Ljava/lang/String;)Lnet/minecraft/tags/TagKey;", "ABILITY_CHANGERS", "Lnet/minecraft/tags/TagKey;", "ANCIENT_POKE_BALLS", "ANY_HELD_ITEM", "APRICORNS", "APRICORN_LOGS", "APRICORN_SPROUTS", "AZALEA_TREE", "BERRIES", "BOATS", "COBBLEMON_SEEDS", "CONSUMED_IN_NPC_BATTLE", "CONSUMED_IN_PVP_BATTLE", "CONSUMED_IN_WILD_BATTLE", "COOKED_MEAT", "DAWN_STONE_ORES", "DESTINY_KNOT", "DUSK_STONE_ORES", "EVERSTONE", "EVOLUTION_ITEMS", "EVOLUTION_STONES", "EXPERIENCE_CANDIES", "EXPERIENCE_SHARE", "FIRE_STONE_ORES", "FOSSILS", "HANGING_SIGNS", "HERBS", "ICE_STONE_ORES", "IS_FRIENDSHIP_BOOSTER", "LEAF_STONE_ORES", "LEAVES_LEFTOVERS", "LUCKY_EGG", "MINTS", "MINT_LEAF", "MINT_SEEDS", "MOON_STONE_ORES", "MUTATED_BERRIES", "PLANTS", "POKE_BALLS", "POTTERY_SHERDS", "POWER_ANKLET", "POWER_BAND", "POWER_BELT", "POWER_BRACER", "POWER_LENS", "POWER_WEIGHT", "PROTEIN_INGREDIENTS", "RAW_MEAT", "SEEDS", "SHINY_STONE_ORES", "SIGNS", "SUN_STONE_ORES", "THUNDER_STONE_ORES", "TUMBLESTONES", "WATER_STONE_ORES", "ZINC_INGREDIENTS", "<init>", "()V", "common"})
public final class CobblemonItemTags {
    @NotNull
    public static final CobblemonItemTags INSTANCE = new CobblemonItemTags();
    @JvmField
    public static final TagKey<Item> ANCIENT_POKE_BALLS = INSTANCE.create("ancient_poke_balls");
    @JvmField
    public static final TagKey<Item> APRICORN_LOGS = INSTANCE.create("apricorn_logs");
    @JvmField
    public static final TagKey<Item> APRICORN_SPROUTS = INSTANCE.create("apricorn_sprouts");
    @JvmField
    public static final TagKey<Item> APRICORNS = INSTANCE.create("apricorns");
    @JvmField
    public static final TagKey<Item> AZALEA_TREE = INSTANCE.create("azalea_tree");
    @JvmField
    public static final TagKey<Item> BERRIES = INSTANCE.create("berries");
    @JvmField
    public static final TagKey<Item> BOATS = INSTANCE.create("boats");
    @JvmField
    public static final TagKey<Item> COBBLEMON_SEEDS = INSTANCE.create("cobblemon_seeds");
    @JvmField
    public static final TagKey<Item> COOKED_MEAT = INSTANCE.create("cooked_meat");
    @JvmField
    public static final TagKey<Item> DAWN_STONE_ORES = INSTANCE.create("dawn_stone_ores");
    @JvmField
    public static final TagKey<Item> DUSK_STONE_ORES = INSTANCE.create("dusk_stone_ores");
    @JvmField
    public static final TagKey<Item> EVOLUTION_ITEMS = INSTANCE.create("evolution_items");
    @JvmField
    public static final TagKey<Item> EVOLUTION_STONES = INSTANCE.create("evolution_stones");
    @JvmField
    public static final TagKey<Item> EXPERIENCE_CANDIES = INSTANCE.create("experience_candies");
    @JvmField
    public static final TagKey<Item> FIRE_STONE_ORES = INSTANCE.create("fire_stone_ores");
    @JvmField
    public static final TagKey<Item> FOSSILS = INSTANCE.create("fossils");
    @JvmField
    public static final TagKey<Item> HANGING_SIGNS = INSTANCE.create("hanging_signs");
    @JvmField
    public static final TagKey<Item> HERBS = INSTANCE.create("herbs");
    @JvmField
    public static final TagKey<Item> ICE_STONE_ORES = INSTANCE.create("ice_stone_ores");
    @JvmField
    public static final TagKey<Item> LEAF_STONE_ORES = INSTANCE.create("leaf_stone_ores");
    @JvmField
    public static final TagKey<Item> MINT_LEAF = INSTANCE.create("mint_leaf");
    @JvmField
    public static final TagKey<Item> MINT_SEEDS = INSTANCE.create("mint_seeds");
    @JvmField
    public static final TagKey<Item> MINTS = INSTANCE.create("mints");
    @JvmField
    public static final TagKey<Item> MOON_STONE_ORES = INSTANCE.create("moon_stone_ores");
    @JvmField
    public static final TagKey<Item> MUTATED_BERRIES = INSTANCE.create("mutated_berries");
    @JvmField
    public static final TagKey<Item> PLANTS = INSTANCE.create("plants");
    @JvmField
    public static final TagKey<Item> POKE_BALLS = INSTANCE.create("poke_balls");
    @JvmField
    public static final TagKey<Item> PROTEIN_INGREDIENTS = INSTANCE.create("protein_ingredients");
    @JvmField
    public static final TagKey<Item> RAW_MEAT = INSTANCE.create("raw_meat");
    @JvmField
    public static final TagKey<Item> SEEDS = INSTANCE.create("seeds");
    @JvmField
    public static final TagKey<Item> SHINY_STONE_ORES = INSTANCE.create("shiny_stone_ores");
    @JvmField
    public static final TagKey<Item> SIGNS = INSTANCE.create("signs");
    @JvmField
    public static final TagKey<Item> SUN_STONE_ORES = INSTANCE.create("sun_stone_ores");
    @JvmField
    public static final TagKey<Item> THUNDER_STONE_ORES = INSTANCE.create("thunder_stone_ores");
    @JvmField
    public static final TagKey<Item> TUMBLESTONES = INSTANCE.create("tumblestones");
    @JvmField
    public static final TagKey<Item> WATER_STONE_ORES = INSTANCE.create("water_stone_ores");
    @JvmField
    public static final TagKey<Item> ZINC_INGREDIENTS = INSTANCE.create("zinc_ingredients");
    @JvmField
    public static final TagKey<Item> ANY_HELD_ITEM = INSTANCE.create("held/is_held_item");
    @JvmField
    public static final TagKey<Item> EXPERIENCE_SHARE = INSTANCE.create("held/experience_share");
    @JvmField
    public static final TagKey<Item> LUCKY_EGG = INSTANCE.create("held/lucky_egg");
    @JvmField
    public static final TagKey<Item> DESTINY_KNOT = INSTANCE.create("held/destiny_knot");
    @JvmField
    public static final TagKey<Item> EVERSTONE = INSTANCE.create("held/everstone");
    @JvmField
    public static final TagKey<Item> POWER_ANKLET = INSTANCE.create("held/power_anklet");
    @JvmField
    public static final TagKey<Item> POWER_BAND = INSTANCE.create("held/power_band");
    @JvmField
    public static final TagKey<Item> POWER_BELT = INSTANCE.create("held/power_belt");
    @JvmField
    public static final TagKey<Item> POWER_BRACER = INSTANCE.create("held/power_bracer");
    @JvmField
    public static final TagKey<Item> POWER_LENS = INSTANCE.create("held/power_lens");
    @JvmField
    public static final TagKey<Item> POWER_WEIGHT = INSTANCE.create("held/power_weight");
    @JvmField
    public static final TagKey<Item> CONSUMED_IN_NPC_BATTLE = INSTANCE.create("held/consumed_in_npc_battle");
    @JvmField
    public static final TagKey<Item> CONSUMED_IN_PVP_BATTLE = INSTANCE.create("held/consumed_in_pvp_battle");
    @JvmField
    public static final TagKey<Item> CONSUMED_IN_WILD_BATTLE = INSTANCE.create("held/consumed_in_wild_battle");
    @JvmField
    public static final TagKey<Item> LEAVES_LEFTOVERS = INSTANCE.create("held/leaves_leftovers");
    @JvmField
    public static final TagKey<Item> POTTERY_SHERDS = INSTANCE.create("decorated_pot_sherds");
    @JvmField
    public static final TagKey<Item> ABILITY_CHANGERS = INSTANCE.create("ability_changers");
    @JvmField
    public static final TagKey<Item> IS_FRIENDSHIP_BOOSTER = INSTANCE.create("is_friendship_booster");

    private CobblemonItemTags() {
    }

    private final TagKey<Item> create(String path) {
        return TagKey.m_203882_((ResourceKey)Registries.f_256913_, (ResourceLocation)MiscUtilsKt.cobblemonResource(path));
    }
}

