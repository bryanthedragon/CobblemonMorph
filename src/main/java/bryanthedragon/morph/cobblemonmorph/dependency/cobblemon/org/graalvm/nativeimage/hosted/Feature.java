
package org.graalvm.nativeimage.hosted;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;
import org.graalvm.nativeimage.hosted.FieldValueTransformer;

@Platforms(value={Platform.HOSTED_ONLY.class})
public interface Feature {
    default public String getURL() {
        return null;
    }

    default public String getDescription() {
        return null;
    }

    default public boolean isInConfiguration(IsInConfigurationAccess access) {
        return true;
    }

    default public List<Class<? extends Feature>> getRequiredFeatures() {
        return Collections.emptyList();
    }

    default public void afterRegistration(AfterRegistrationAccess access) {
    }

    default public void duringSetup(DuringSetupAccess access) {
    }

    default public void beforeAnalysis(BeforeAnalysisAccess access) {
    }

    default public void duringAnalysis(DuringAnalysisAccess access) {
    }

    default public void afterAnalysis(AfterAnalysisAccess access) {
    }

    default public void onAnalysisExit(OnAnalysisExitAccess access) {
    }

    default public void beforeUniverseBuilding(BeforeUniverseBuildingAccess access) {
    }

    default public void beforeCompilation(BeforeCompilationAccess access) {
    }

    default public void afterCompilation(AfterCompilationAccess access) {
    }

    default public void afterHeapLayout(AfterHeapLayoutAccess access) {
    }

    default public void beforeImageWrite(BeforeImageWriteAccess access) {
    }

    default public void afterImageWrite(AfterImageWriteAccess access) {
    }

    default public void cleanup() {
    }

    @Platforms(value={Platform.HOSTED_ONLY.class})
    public static interface AfterImageWriteAccess
    extends FeatureAccess {
        public Path getImagePath();
    }

    @Platforms(value={Platform.HOSTED_ONLY.class})
    public static interface BeforeImageWriteAccess
    extends FeatureAccess {
    }

    @Platforms(value={Platform.HOSTED_ONLY.class})
    public static interface AfterHeapLayoutAccess
    extends FeatureAccess {
    }

    @Platforms(value={Platform.HOSTED_ONLY.class})
    public static interface AfterCompilationAccess
    extends CompilationAccess {
    }

    @Platforms(value={Platform.HOSTED_ONLY.class})
    public static interface BeforeCompilationAccess
    extends CompilationAccess {
    }

    @Platforms(value={Platform.HOSTED_ONLY.class})
    public static interface CompilationAccess
    extends FeatureAccess {
        public long objectFieldOffset(Field var1);

        public void registerAsImmutable(Object var1);

        public void registerAsImmutable(Object var1, Predicate<Object> var2);
    }

    @Platforms(value={Platform.HOSTED_ONLY.class})
    public static interface BeforeUniverseBuildingAccess
    extends FeatureAccess {
    }

    @Platforms(value={Platform.HOSTED_ONLY.class})
    public static interface OnAnalysisExitAccess
    extends FeatureAccess {
    }

    @Platforms(value={Platform.HOSTED_ONLY.class})
    public static interface QueryReachabilityAccess
    extends FeatureAccess {
        public boolean isReachable(Class<?> var1);

        public boolean isReachable(Field var1);

        public boolean isReachable(Executable var1);

        public Set<Class<?>> reachableSubtypes(Class<?> var1);

        public Set<Executable> reachableMethodOverrides(Executable var1);
    }

    @Platforms(value={Platform.HOSTED_ONLY.class})
    public static interface AfterAnalysisAccess
    extends QueryReachabilityAccess {
    }

    @Platforms(value={Platform.HOSTED_ONLY.class})
    public static interface DuringAnalysisAccess
    extends BeforeAnalysisAccess,
    QueryReachabilityAccess {
        public void requireAnalysisIteration();
    }

    @Platforms(value={Platform.HOSTED_ONLY.class})
    public static interface BeforeAnalysisAccess
    extends FeatureAccess {
        public void registerAsUsed(Class<?> var1);

        public void registerAsInHeap(Class<?> var1);

        public void registerAsAccessed(Field var1);

        public void registerAsUnsafeAccessed(Field var1);

        public void registerReachabilityHandler(Consumer<DuringAnalysisAccess> var1, Object ... var2);

        public void registerMethodOverrideReachabilityHandler(BiConsumer<DuringAnalysisAccess, Executable> var1, Executable var2);

        public void registerSubtypeReachabilityHandler(BiConsumer<DuringAnalysisAccess, Class<?>> var1, Class<?> var2);

        public void registerClassInitializerReachabilityHandler(Consumer<DuringAnalysisAccess> var1, Class<?> var2);

        public void registerFieldValueTransformer(Field var1, FieldValueTransformer var2);
    }

    @Platforms(value={Platform.HOSTED_ONLY.class})
    public static interface DuringSetupAccess
    extends FeatureAccess {
        public void registerObjectReplacer(Function<Object, Object> var1);
    }

    @Platforms(value={Platform.HOSTED_ONLY.class})
    public static interface AfterRegistrationAccess
    extends FeatureAccess {
    }

    @Platforms(value={Platform.HOSTED_ONLY.class})
    public static interface IsInConfigurationAccess
    extends FeatureAccess {
    }

    @Platforms(value={Platform.HOSTED_ONLY.class})
    public static interface FeatureAccess {
        public Class<?> findClassByName(String var1);

        public List<Path> getApplicationClassPath();

        public List<Path> getApplicationModulePath();

        public ClassLoader getApplicationClassLoader();
    }
}

