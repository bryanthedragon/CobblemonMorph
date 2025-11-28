package org.graalvm.word;

public interface Pointer extends UnsignedWord, PointerBase {
   Object toObject();

   Object toObjectNonNull();

   byte readByte(WordBase offset, LocationIdentity locationIdentity);

   char readChar(WordBase offset, LocationIdentity locationIdentity);

   short readShort(WordBase offset, LocationIdentity locationIdentity);

   int readInt(WordBase offset, LocationIdentity locationIdentity);

   long readLong(WordBase offset, LocationIdentity locationIdentity);

   float readFloat(WordBase offset, LocationIdentity locationIdentity);

   double readDouble(WordBase offset, LocationIdentity locationIdentity);

   <T extends WordBase> T readWord(WordBase offset, LocationIdentity locationIdentity);

   Object readObject(WordBase offset, LocationIdentity locationIdentity);

   byte readByte(int offset, LocationIdentity locationIdentity);

   char readChar(int offset, LocationIdentity locationIdentity);

   short readShort(int offset, LocationIdentity locationIdentity);

   int readInt(int offset, LocationIdentity locationIdentity);

   long readLong(int offset, LocationIdentity locationIdentity);

   float readFloat(int offset, LocationIdentity locationIdentity);

   double readDouble(int offset, LocationIdentity locationIdentity);

   <T extends WordBase> T readWord(int offset, LocationIdentity locationIdentity);

   Object readObject(int offset, LocationIdentity locationIdentity);

   <T extends WordBase> T readWordVolatile(int offset, LocationIdentity locationIdentity);

   void writeByte(WordBase offset, byte val, LocationIdentity locationIdentity);

   void writeChar(WordBase offset, char val, LocationIdentity locationIdentity);

   void writeShort(WordBase offset, short val, LocationIdentity locationIdentity);

   void writeInt(WordBase offset, int val, LocationIdentity locationIdentity);

   void writeLong(WordBase offset, long val, LocationIdentity locationIdentity);

   void writeFloat(WordBase offset, float val, LocationIdentity locationIdentity);

   void writeDouble(WordBase offset, double val, LocationIdentity locationIdentity);

   void writeWord(WordBase offset, WordBase val, LocationIdentity locationIdentity);

   void initializeLong(WordBase offset, long val, LocationIdentity locationIdentity);

   void writeObject(WordBase offset, Object val, LocationIdentity locationIdentity);

   void writeByte(int offset, byte val, LocationIdentity locationIdentity);

   void writeChar(int offset, char val, LocationIdentity locationIdentity);

   void writeShort(int offset, short val, LocationIdentity locationIdentity);

   void writeInt(int offset, int val, LocationIdentity locationIdentity);

   void writeLong(int offset, long val, LocationIdentity locationIdentity);

   void writeFloat(int offset, float val, LocationIdentity locationIdentity);

   void writeDouble(int offset, double val, LocationIdentity locationIdentity);

   void writeWord(int offset, WordBase val, LocationIdentity locationIdentity);

   void initializeLong(int offset, long val, LocationIdentity locationIdentity);

   void writeObject(int offset, Object val, LocationIdentity locationIdentity);

   byte readByte(WordBase offset);

   char readChar(WordBase offset);

   short readShort(WordBase offset);

   int readInt(WordBase offset);

   long readLong(WordBase offset);

   float readFloat(WordBase offset);

   double readDouble(WordBase offset);

   <T extends WordBase> T readWord(WordBase offset);

   Object readObject(WordBase offset);

   byte readByte(int offset);

   char readChar(int offset);

   short readShort(int offset);

   int readInt(int offset);

   long readLong(int offset);

   float readFloat(int offset);

   double readDouble(int offset);

   <T extends WordBase> T readWord(int offset);

   Object readObject(int offset);

   void writeByte(WordBase offset, byte val);

   void writeChar(WordBase offset, char val);

   void writeShort(WordBase offset, short val);

   void writeInt(WordBase offset, int val);

   void writeLong(WordBase offset, long val);

   void writeFloat(WordBase offset, float val);

   void writeDouble(WordBase offset, double val);

   void writeWord(WordBase offset, WordBase val);

   void writeObject(WordBase offset, Object val);

   int compareAndSwapInt(WordBase offset, int expectedValue, int newValue, LocationIdentity locationIdentity);

   long compareAndSwapLong(WordBase offset, long expectedValue, long newValue, LocationIdentity locationIdentity);

   <T extends WordBase> T compareAndSwapWord(WordBase offset, T expectedValue, T newValue, LocationIdentity locationIdentity);

   Object compareAndSwapObject(WordBase offset, Object expectedValue, Object newValue, LocationIdentity locationIdentity);

   boolean logicCompareAndSwapInt(WordBase offset, int expectedValue, int newValue, LocationIdentity locationIdentity);

   boolean logicCompareAndSwapLong(WordBase offset, long expectedValue, long newValue, LocationIdentity locationIdentity);

   boolean logicCompareAndSwapWord(WordBase offset, WordBase expectedValue, WordBase newValue, LocationIdentity locationIdentity);

   boolean logicCompareAndSwapObject(WordBase offset, Object expectedValue, Object newValue, LocationIdentity locationIdentity);

   void writeByte(int offset, byte val);

   void writeChar(int offset, char val);

   void writeShort(int offset, short val);

   void writeInt(int offset, int val);

   void writeLong(int offset, long val);

   void writeFloat(int offset, float val);

   void writeDouble(int offset, double val);

   void writeWord(int offset, WordBase val);

   void writeObject(int offset, Object val);

   void writeWordVolatile(int offset, WordBase val);

   int compareAndSwapInt(int offset, int expectedValue, int newValue, LocationIdentity locationIdentity);

   long compareAndSwapLong(int offset, long expectedValue, long newValue, LocationIdentity locationIdentity);

   <T extends WordBase> T compareAndSwapWord(int offset, T expectedValue, T newValue, LocationIdentity locationIdentity);

   Object compareAndSwapObject(int offset, Object expectedValue, Object newValue, LocationIdentity locationIdentity);

   boolean logicCompareAndSwapInt(int offset, int expectedValue, int newValue, LocationIdentity locationIdentity);

   boolean logicCompareAndSwapLong(int offset, long expectedValue, long newValue, LocationIdentity locationIdentity);

   boolean logicCompareAndSwapWord(int offset, WordBase expectedValue, WordBase newValue, LocationIdentity locationIdentity);

   boolean logicCompareAndSwapObject(int offset, Object expectedValue, Object newValue, LocationIdentity locationIdentity);

   Pointer add(UnsignedWord val);

   Pointer add(int val);

   Pointer subtract(UnsignedWord val);

   Pointer subtract(int val);

   Pointer and(UnsignedWord val);

   Pointer and(int val);

   Pointer or(UnsignedWord val);

   Pointer or(int val);
}
