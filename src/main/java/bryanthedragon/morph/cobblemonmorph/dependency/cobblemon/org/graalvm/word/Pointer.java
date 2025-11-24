
package org.graalvm.word;

import org.graalvm.word.LocationIdentity;
import org.graalvm.word.PointerBase;
import org.graalvm.word.UnsignedWord;
import org.graalvm.word.WordBase;

public interface Pointer
extends UnsignedWord,
PointerBase {
    public Object toObject();

    public Object toObjectNonNull();

    public byte readByte(WordBase var1, LocationIdentity var2);

    public char readChar(WordBase var1, LocationIdentity var2);

    public short readShort(WordBase var1, LocationIdentity var2);

    public int readInt(WordBase var1, LocationIdentity var2);

    public long readLong(WordBase var1, LocationIdentity var2);

    public float readFloat(WordBase var1, LocationIdentity var2);

    public double readDouble(WordBase var1, LocationIdentity var2);

    public <T extends WordBase> T readWord(WordBase var1, LocationIdentity var2);

    public Object readObject(WordBase var1, LocationIdentity var2);

    public byte readByte(int var1, LocationIdentity var2);

    public char readChar(int var1, LocationIdentity var2);

    public short readShort(int var1, LocationIdentity var2);

    public int readInt(int var1, LocationIdentity var2);

    public long readLong(int var1, LocationIdentity var2);

    public float readFloat(int var1, LocationIdentity var2);

    public double readDouble(int var1, LocationIdentity var2);

    public <T extends WordBase> T readWord(int var1, LocationIdentity var2);

    public Object readObject(int var1, LocationIdentity var2);

    public <T extends WordBase> T readWordVolatile(int var1, LocationIdentity var2);

    public void writeByte(WordBase var1, byte var2, LocationIdentity var3);

    public void writeChar(WordBase var1, char var2, LocationIdentity var3);

    public void writeShort(WordBase var1, short var2, LocationIdentity var3);

    public void writeInt(WordBase var1, int var2, LocationIdentity var3);

    public void writeLong(WordBase var1, long var2, LocationIdentity var4);

    public void writeFloat(WordBase var1, float var2, LocationIdentity var3);

    public void writeDouble(WordBase var1, double var2, LocationIdentity var4);

    public void writeWord(WordBase var1, WordBase var2, LocationIdentity var3);

    public void initializeLong(WordBase var1, long var2, LocationIdentity var4);

    public void writeObject(WordBase var1, Object var2, LocationIdentity var3);

    public void writeByte(int var1, byte var2, LocationIdentity var3);

    public void writeChar(int var1, char var2, LocationIdentity var3);

    public void writeShort(int var1, short var2, LocationIdentity var3);

    public void writeInt(int var1, int var2, LocationIdentity var3);

    public void writeLong(int var1, long var2, LocationIdentity var4);

    public void writeFloat(int var1, float var2, LocationIdentity var3);

    public void writeDouble(int var1, double var2, LocationIdentity var4);

    public void writeWord(int var1, WordBase var2, LocationIdentity var3);

    public void initializeLong(int var1, long var2, LocationIdentity var4);

    public void writeObject(int var1, Object var2, LocationIdentity var3);

    public byte readByte(WordBase var1);

    public char readChar(WordBase var1);

    public short readShort(WordBase var1);

    public int readInt(WordBase var1);

    public long readLong(WordBase var1);

    public float readFloat(WordBase var1);

    public double readDouble(WordBase var1);

    public <T extends WordBase> T readWord(WordBase var1);

    public Object readObject(WordBase var1);

    public byte readByte(int var1);

    public char readChar(int var1);

    public short readShort(int var1);

    public int readInt(int var1);

    public long readLong(int var1);

    public float readFloat(int var1);

    public double readDouble(int var1);

    public <T extends WordBase> T readWord(int var1);

    public Object readObject(int var1);

    public void writeByte(WordBase var1, byte var2);

    public void writeChar(WordBase var1, char var2);

    public void writeShort(WordBase var1, short var2);

    public void writeInt(WordBase var1, int var2);

    public void writeLong(WordBase var1, long var2);

    public void writeFloat(WordBase var1, float var2);

    public void writeDouble(WordBase var1, double var2);

    public void writeWord(WordBase var1, WordBase var2);

    public void writeObject(WordBase var1, Object var2);

    public int compareAndSwapInt(WordBase var1, int var2, int var3, LocationIdentity var4);

    public long compareAndSwapLong(WordBase var1, long var2, long var4, LocationIdentity var6);

    public <T extends WordBase> T compareAndSwapWord(WordBase var1, T var2, T var3, LocationIdentity var4);

    public Object compareAndSwapObject(WordBase var1, Object var2, Object var3, LocationIdentity var4);

    public boolean logicCompareAndSwapInt(WordBase var1, int var2, int var3, LocationIdentity var4);

    public boolean logicCompareAndSwapLong(WordBase var1, long var2, long var4, LocationIdentity var6);

    public boolean logicCompareAndSwapWord(WordBase var1, WordBase var2, WordBase var3, LocationIdentity var4);

    public boolean logicCompareAndSwapObject(WordBase var1, Object var2, Object var3, LocationIdentity var4);

    public void writeByte(int var1, byte var2);

    public void writeChar(int var1, char var2);

    public void writeShort(int var1, short var2);

    public void writeInt(int var1, int var2);

    public void writeLong(int var1, long var2);

    public void writeFloat(int var1, float var2);

    public void writeDouble(int var1, double var2);

    public void writeWord(int var1, WordBase var2);

    public void writeObject(int var1, Object var2);

    public void writeWordVolatile(int var1, WordBase var2);

    public int compareAndSwapInt(int var1, int var2, int var3, LocationIdentity var4);

    public long compareAndSwapLong(int var1, long var2, long var4, LocationIdentity var6);

    public <T extends WordBase> T compareAndSwapWord(int var1, T var2, T var3, LocationIdentity var4);

    public Object compareAndSwapObject(int var1, Object var2, Object var3, LocationIdentity var4);

    public boolean logicCompareAndSwapInt(int var1, int var2, int var3, LocationIdentity var4);

    public boolean logicCompareAndSwapLong(int var1, long var2, long var4, LocationIdentity var6);

    public boolean logicCompareAndSwapWord(int var1, WordBase var2, WordBase var3, LocationIdentity var4);

    public boolean logicCompareAndSwapObject(int var1, Object var2, Object var3, LocationIdentity var4);

    @Override
    public Pointer add(UnsignedWord var1);

    @Override
    public Pointer add(int var1);

    @Override
    public Pointer subtract(UnsignedWord var1);

    @Override
    public Pointer subtract(int var1);

    @Override
    public Pointer and(UnsignedWord var1);

    @Override
    public Pointer and(int var1);

    @Override
    public Pointer or(UnsignedWord var1);

    @Override
    public Pointer or(int var1);
}

