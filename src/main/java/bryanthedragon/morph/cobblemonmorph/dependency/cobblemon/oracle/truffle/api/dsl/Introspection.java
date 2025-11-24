
package com.oracle.truffle.api.dsl;

import com.oracle.truffle.api.dsl.Introspectable;
import com.oracle.truffle.api.nodes.Node;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Introspection {
    private static final List<List<Object>> EMPTY_CACHED = Collections.unmodifiableList(Arrays.asList(Collections.emptyList()));
    private static final List<List<Object>> NO_CACHED = Collections.emptyList();
    private final Object[] data;

    Introspection(Object[] data) {
        this.data = data;
    }

    public static boolean isIntrospectable(Node node) {
        return node instanceof Provider;
    }

    public static SpecializationInfo getSpecialization(Node node, String methodName) {
        return Introspection.getIntrospectionData(node).getSpecialization(methodName);
    }

    public static List<SpecializationInfo> getSpecializations(Node node) {
        return Introspection.getIntrospectionData(node).getSpecializations();
    }

    private static Introspection getIntrospectionData(Node node) {
        if (!(node instanceof Provider)) {
            throw new IllegalArgumentException(String.format("Provided node is not introspectable. Annotate with @%s to make a node introspectable.", Introspectable.class.getSimpleName()));
        }
        return ((Provider)((Object)node)).getIntrospectionData();
    }

    SpecializationInfo getSpecialization(String methodName) {
        this.checkVersion();
        for (int i = 1; i < this.data.length; ++i) {
            Object[] fieldData = Introspection.getIntrospectionData(this.data[i]);
            if (!methodName.equals(fieldData[0])) continue;
            return Introspection.createSpecialization(fieldData);
        }
        return null;
    }

    List<SpecializationInfo> getSpecializations() {
        this.checkVersion();
        ArrayList<SpecializationInfo> specializations = new ArrayList<SpecializationInfo>();
        for (int i = 1; i < this.data.length; ++i) {
            specializations.add(Introspection.createSpecialization(Introspection.getIntrospectionData(this.data[i])));
        }
        return Collections.unmodifiableList(specializations);
    }

    private void checkVersion() {
        int version = -1;
        if (this.data.length > 0 && this.data[0] instanceof Integer) {
            Object objectVersion = this.data[0];
            version = (Integer)objectVersion;
        }
        if (version != 0) {
            throw new IllegalStateException("Unsupported introspection data version: " + version);
        }
    }

    private static Object[] getIntrospectionData(Object specializationData) {
        if (!(specializationData instanceof Object[])) {
            throw new IllegalStateException("Invalid introspection data.");
        }
        Object[] fieldData = (Object[])specializationData;
        if (fieldData.length < 3 || !(fieldData[0] instanceof String) || !(fieldData[1] instanceof Byte) || fieldData[2] != null && !(fieldData[2] instanceof List)) {
            throw new IllegalStateException("Invalid introspection data.");
        }
        return fieldData;
    }

    private static SpecializationInfo createSpecialization(Object[] fieldData) {
        String id = (String)fieldData[0];
        byte state = (Byte)fieldData[1];
        List<List<Object>> cachedData = (List<List<Object>>)fieldData[2];
        if (cachedData == null || cachedData.isEmpty()) {
            cachedData = (state & 1) != 0 ? EMPTY_CACHED : NO_CACHED;
        } else {
            for (int i = 0; i < cachedData.size(); ++i) {
                cachedData.set(i, Collections.unmodifiableList(cachedData.get(i)));
            }
        }
        return new SpecializationInfo(id, state, cachedData);
    }

    public static interface Provider {
        public Introspection getIntrospectionData();

        public static Introspection create(Object ... data) {
            return new Introspection(data);
        }
    }

    public static final class SpecializationInfo {
        private final String methodName;
        private final byte state;
        private final List<List<Object>> cachedData;

        SpecializationInfo(String methodName, byte state, List<List<Object>> cachedData) {
            this.methodName = methodName;
            this.state = state;
            this.cachedData = cachedData;
        }

        public String getMethodName() {
            return this.methodName;
        }

        public boolean isActive() {
            return (this.state & 1) != 0;
        }

        public boolean isExcluded() {
            return (this.state & 2) != 0;
        }

        public int getInstances() {
            return this.cachedData.size();
        }

        public List<Object> getCachedData(int instanceIndex) {
            if (instanceIndex < 0 || instanceIndex >= this.cachedData.size()) {
                throw new IllegalArgumentException("Invalid specialization index");
            }
            return this.cachedData.get(instanceIndex);
        }

        public String toString() {
            return "SpecializationInfo[name=" + this.methodName + ", active=" + this.isActive() + ", excluded" + this.isExcluded() + ", instances=" + this.getInstances() + "]";
        }
    }
}

