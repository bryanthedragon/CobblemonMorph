package com.oracle.truffle.api;

public final class InstrumentInfo {
   private final Object polyglotInstrument;
   private final String id;
   private final String name;
   private final String version;

   InstrumentInfo(Object vmObject, String id, String name, String version) {
      this.polyglotInstrument = vmObject;
      this.id = id;
      this.name = name;
      this.version = version;
   }

   public String getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public String getVersion() {
      return this.version;
   }

   Object getPolyglotInstrument() {
      return this.polyglotInstrument;
   }

   @Override
   public String toString() {
      return "InstrumentInfo [id=" + this.id + ", name=" + this.name + ", version=" + this.version + "]";
   }
}
