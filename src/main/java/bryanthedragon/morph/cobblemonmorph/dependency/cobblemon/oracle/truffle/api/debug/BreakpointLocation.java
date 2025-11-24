
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.debug.DebuggerTags;
import com.oracle.truffle.api.debug.SourceElement;
import com.oracle.truffle.api.debug.SuspendAnchor;
import com.oracle.truffle.api.debug.SuspendableLocationFinder;
import com.oracle.truffle.api.debug.SuspensionFilter;
import com.oracle.truffle.api.instrumentation.SourceFilter;
import com.oracle.truffle.api.instrumentation.SourceSectionFilter;
import com.oracle.truffle.api.instrumentation.TruffleInstrument;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import java.net.URI;
import java.util.function.Predicate;

abstract class BreakpointLocation {
    protected final SourceElement[] sourceElements;
    static final BreakpointLocation ANY = new BreakpointSourceLocation();
    static final URI ANY_SOURCE = URI.create("");

    protected BreakpointLocation(SourceElement[] sourceElements) {
        this.sourceElements = sourceElements;
    }

    static BreakpointLocation create(Object key, SourceElement[] sourceElements, SourceSection sourceSection) {
        return new BreakpointSourceLocation(key, sourceElements, sourceSection);
    }

    static BreakpointLocation create(Object key, SourceElement[] sourceElements, int line, int column) {
        return new BreakpointSourceLocation(key, sourceElements, line, column);
    }

    static BreakpointLocation create(SourceElement[] sourceElements, SuspensionFilter filter) {
        return new BreakpointFilteredLocation(sourceElements, filter);
    }

    final boolean containsRoot() {
        if (this.sourceElements == null) {
            return true;
        }
        for (SourceElement elem : this.sourceElements) {
            if (SourceElement.ROOT != elem) continue;
            return true;
        }
        return false;
    }

    abstract SourceFilter createSourceFilter();

    abstract Predicate<Source> createSourcePredicate();

    abstract boolean canAdjustLocation();

    abstract SourceSection adjustLocation(Source var1, TruffleInstrument.Env var2, SuspendAnchor var3);

    abstract SourceSectionFilter createLocationFilter(Source var1, SuspendAnchor var2);

    private static void setTags(SourceSectionFilter.Builder f, SourceElement[] sourceElements) {
        Class[] elementTags = new Class[sourceElements.length];
        for (int i = 0; i < elementTags.length; ++i) {
            elementTags[i] = sourceElements[i].getTag();
        }
        f.tagIs(elementTags);
    }

    private static final class BreakpointFilteredLocation
    extends BreakpointLocation {
        private final SuspensionFilter filter;

        BreakpointFilteredLocation(SourceElement[] sourceElements, SuspensionFilter filter) {
            super(sourceElements);
            this.filter = filter;
        }

        @Override
        SourceFilter createSourceFilter() {
            return null;
        }

        @Override
        Predicate<Source> createSourcePredicate() {
            return null;
        }

        @Override
        boolean canAdjustLocation() {
            return false;
        }

        @Override
        SourceSection adjustLocation(Source source, TruffleInstrument.Env env, SuspendAnchor suspendAnchor) {
            return null;
        }

        @Override
        SourceSectionFilter createLocationFilter(Source source, SuspendAnchor suspendAnchor) {
            SourceSectionFilter.Builder f = SourceSectionFilter.newBuilder();
            SourceFilter.Builder sourceFilterBuilder = SourceFilter.newBuilder();
            if (this.filter != null) {
                Predicate<Source> sourcePredicate = this.filter.getSourcePredicate();
                if (sourcePredicate != null) {
                    sourceFilterBuilder.sourceIs(sourcePredicate);
                }
                sourceFilterBuilder.includeInternal(this.filter.isInternalIncluded());
            }
            SourceFilter sourceFilter = sourceFilterBuilder.build();
            f.sourceFilter(sourceFilter);
            BreakpointLocation.setTags(f, this.sourceElements);
            return f.build();
        }
    }

    private static final class BreakpointSourceLocation
    extends BreakpointLocation {
        private final Object key;
        private final SourceSection sourceSection;
        private int line;
        private int column;

        BreakpointSourceLocation(Object key, SourceElement[] sourceElements, SourceSection sourceSection) {
            super(sourceElements);
            assert (key instanceof Source || key instanceof URI);
            this.key = key;
            this.sourceSection = sourceSection;
            this.line = -1;
            this.column = -1;
        }

        BreakpointSourceLocation(Object key, SourceElement[] sourceElements, int line, int column) {
            super(sourceElements);
            assert (key instanceof Source || key instanceof URI);
            assert (line > 0 || line == -1);
            assert (column > 0 || column == -1);
            this.key = key;
            this.line = line;
            this.column = column;
            this.sourceSection = null;
        }

        private BreakpointSourceLocation() {
            super(null);
            this.key = null;
            this.line = -1;
            this.column = -1;
            this.sourceSection = null;
        }

        @Override
        SourceFilter createSourceFilter() {
            if (this.key == null) {
                return null;
            }
            SourceFilter.Builder f = SourceFilter.newBuilder();
            if (this.key instanceof URI) {
                f.sourceIs(this.createSourcePredicate());
            } else {
                assert (this.key instanceof Source);
                Source s = (Source)this.key;
                f.sourceIs(s);
            }
            return f.build();
        }

        @Override
        Predicate<Source> createSourcePredicate() {
            if (this.key == null) {
                return null;
            }
            if (this.key instanceof URI) {
                if (this.key == ANY_SOURCE) {
                    return new Predicate<Source>(){

                        @Override
                        public boolean test(Source s) {
                            return true;
                        }
                    };
                }
                final URI sourceUri = (URI)this.key;
                final String sourceRawPath = sourceUri.getRawPath() != null ? sourceUri.getRawPath() : sourceUri.getRawSchemeSpecificPart();
                return new Predicate<Source>(){

                    @Override
                    public boolean test(Source s) {
                        URI uri = s.getURI();
                        if (uri.isAbsolute()) {
                            return sourceUri.equals(uri);
                        }
                        return sourceRawPath != null && sourceRawPath.endsWith(uri.getRawPath());
                    }

                    public String toString() {
                        return "URI equals " + sourceUri;
                    }
                };
            }
            assert (this.key instanceof Source);
            final Source source = (Source)this.key;
            return new Predicate<Source>(){

                @Override
                public boolean test(Source s) {
                    return source.equals(s);
                }
            };
        }

        @Override
        boolean canAdjustLocation() {
            return this.key != null;
        }

        @Override
        SourceSection adjustLocation(Source source, TruffleInstrument.Env env, SuspendAnchor suspendAnchor) {
            if (this.sourceSection != null) {
                return this.sourceSection;
            }
            if (this.key == null) {
                return null;
            }
            if (this.line == -1) {
                return source.createUnavailableSection();
            }
            boolean hasColumn = this.column > 0;
            SourceSection location = SuspendableLocationFinder.findNearest(source, this.sourceElements, this.line, this.column, suspendAnchor, env);
            if (location != null) {
                switch (suspendAnchor) {
                    case BEFORE: {
                        this.line = location.getStartLine();
                        if (!hasColumn) break;
                        this.column = location.getStartColumn();
                        break;
                    }
                    case AFTER: {
                        this.line = location.getEndLine();
                        if (!hasColumn) break;
                        this.column = location.getEndColumn();
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException("Unknown suspend anchor: " + suspendAnchor);
                    }
                }
            }
            return location;
        }

        @Override
        SourceSectionFilter createLocationFilter(Source source, SuspendAnchor suspendAnchor) {
            SourceSectionFilter.Builder f = SourceSectionFilter.newBuilder();
            if (this.key == null) {
                return f.tagIs(DebuggerTags.AlwaysHalt.class).build();
            }
            if (this.key != ANY_SOURCE) {
                if (source != null) {
                    f.sourceIs(source);
                } else {
                    f.sourceFilter(this.createSourceFilter());
                }
            }
            if (this.line != -1) {
                switch (suspendAnchor) {
                    case BEFORE: {
                        f.lineStartsIn(SourceSectionFilter.IndexRange.byLength(this.line, 1));
                        if (this.column == -1) break;
                        f.columnStartsIn(SourceSectionFilter.IndexRange.byLength(this.column, 1));
                        break;
                    }
                    case AFTER: {
                        f.lineEndsIn(SourceSectionFilter.IndexRange.byLength(this.line, 1));
                        if (this.column == -1) break;
                        f.columnEndsIn(SourceSectionFilter.IndexRange.byLength(this.column, 1));
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException(suspendAnchor.name());
                    }
                }
            }
            if (this.sourceSection != null) {
                f.sourceSectionEquals(this.sourceSection);
            }
            BreakpointLocation.setTags(f, this.sourceElements);
            return f.build();
        }

        public String toString() {
            Object keyDescription = this.key == null ? "AlwaysHalt" : (this.key instanceof Source ? "sourceName=" + ((Source)this.key).getName() : (this.key instanceof URI ? "uri=" + ((URI)this.key).toString() : this.key.toString()));
            return (String)keyDescription + ", line=" + this.line + ", column=" + this.column;
        }
    }
}

