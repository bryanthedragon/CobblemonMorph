
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.impl.IntTrie;
import com.cobblemon.mod.relocations.ibm.icu.impl.IntTrieBuilder;
import com.cobblemon.mod.relocations.ibm.icu.impl.PVecToTrieCompactHandler;
import com.cobblemon.mod.relocations.ibm.icu.impl.Trie;
import com.cobblemon.mod.relocations.ibm.icu.impl.TrieBuilder;
import java.util.Arrays;
import java.util.Comparator;

public class PropsVectors {
    private int[] v;
    private int columns;
    private int maxRows;
    private int rows;
    private int prevRow;
    private boolean isCompacted;
    public static final int FIRST_SPECIAL_CP = 0x110000;
    public static final int INITIAL_VALUE_CP = 0x110000;
    public static final int ERROR_VALUE_CP = 0x110001;
    public static final int MAX_CP = 0x110001;
    public static final int INITIAL_ROWS = 4096;
    public static final int MEDIUM_ROWS = 65536;
    public static final int MAX_ROWS = 0x110002;

    private boolean areElementsSame(int index1, int[] target, int index2, int length) {
        for (int i = 0; i < length; ++i) {
            if (this.v[index1 + i] == target[index2 + i]) continue;
            return false;
        }
        return true;
    }

    private int findRow(int rangeStart) {
        int index = 0;
        index = this.prevRow * this.columns;
        if (rangeStart >= this.v[index]) {
            if (rangeStart < this.v[index + 1]) {
                return index;
            }
            if (rangeStart < this.v[(index += this.columns) + 1]) {
                ++this.prevRow;
                return index;
            }
            if (rangeStart < this.v[(index += this.columns) + 1]) {
                this.prevRow += 2;
                return index;
            }
            if (rangeStart - this.v[index + 1] < 10) {
                this.prevRow += 2;
                do {
                    ++this.prevRow;
                } while (rangeStart >= this.v[(index += this.columns) + 1]);
                return index;
            }
        } else if (rangeStart < this.v[1]) {
            this.prevRow = 0;
            return 0;
        }
        int start2 = 0;
        int mid = 0;
        int limit = this.rows;
        while (start2 < limit - 1) {
            mid = (start2 + limit) / 2;
            index = this.columns * mid;
            if (rangeStart < this.v[index]) {
                limit = mid;
                continue;
            }
            if (rangeStart < this.v[index + 1]) {
                this.prevRow = mid;
                return index;
            }
            start2 = mid;
        }
        this.prevRow = start2;
        index = start2 * this.columns;
        return index;
    }

    public PropsVectors(int numOfColumns) {
        if (numOfColumns < 1) {
            throw new IllegalArgumentException("numOfColumns need to be no less than 1; but it is " + numOfColumns);
        }
        this.columns = numOfColumns + 2;
        this.v = new int[4096 * this.columns];
        this.maxRows = 4096;
        this.rows = 3;
        this.prevRow = 0;
        this.isCompacted = false;
        this.v[0] = 0;
        this.v[1] = 0x110000;
        int index = this.columns;
        for (int cp = 0x110000; cp <= 0x110001; ++cp) {
            this.v[index] = cp;
            this.v[index + 1] = cp + 1;
            index += this.columns;
        }
    }

    public void setValue(int start2, int end2, int column, int value2, int mask) {
        boolean splitLastRow;
        if (start2 < 0 || start2 > end2 || end2 > 0x110001 || column < 0 || column >= this.columns - 2) {
            throw new IllegalArgumentException();
        }
        if (this.isCompacted) {
            throw new IllegalStateException("Shouldn't be called aftercompact()!");
        }
        int limit = end2 + 1;
        int firstRow = this.findRow(start2);
        int lastRow = this.findRow(end2);
        boolean splitFirstRow = start2 != this.v[firstRow] && (value2 &= mask) != (this.v[firstRow + (column += 2)] & mask);
        boolean bl = splitLastRow = limit != this.v[lastRow + 1] && value2 != (this.v[lastRow + column] & mask);
        if (splitFirstRow || splitLastRow) {
            int count;
            int rowsToExpand = 0;
            if (splitFirstRow) {
                ++rowsToExpand;
            }
            if (splitLastRow) {
                ++rowsToExpand;
            }
            int newMaxRows = 0;
            if (this.rows + rowsToExpand > this.maxRows) {
                if (this.maxRows < 65536) {
                    newMaxRows = 65536;
                } else if (this.maxRows < 0x110002) {
                    newMaxRows = 0x110002;
                } else {
                    throw new IndexOutOfBoundsException("MAX_ROWS exceeded! Increase it to a higher valuein the implementation");
                }
                int[] temp = new int[newMaxRows * this.columns];
                System.arraycopy(this.v, 0, temp, 0, this.rows * this.columns);
                this.v = temp;
                this.maxRows = newMaxRows;
            }
            if ((count = this.rows * this.columns - (lastRow + this.columns)) > 0) {
                System.arraycopy(this.v, lastRow + this.columns, this.v, lastRow + (1 + rowsToExpand) * this.columns, count);
            }
            this.rows += rowsToExpand;
            if (splitFirstRow) {
                count = lastRow - firstRow + this.columns;
                System.arraycopy(this.v, firstRow, this.v, firstRow + this.columns, count);
                lastRow += this.columns;
                int n = start2;
                this.v[firstRow + this.columns] = n;
                this.v[firstRow + 1] = n;
                firstRow += this.columns;
            }
            if (splitLastRow) {
                System.arraycopy(this.v, lastRow, this.v, lastRow + this.columns, this.columns);
                int n = limit;
                this.v[lastRow + this.columns] = n;
                this.v[lastRow + 1] = n;
            }
        }
        this.prevRow = lastRow / this.columns;
        firstRow += column;
        lastRow += column;
        mask ^= 0xFFFFFFFF;
        while (true) {
            this.v[firstRow] = this.v[firstRow] & mask | value2;
            if (firstRow == lastRow) break;
            firstRow += this.columns;
        }
    }

    public int getValue(int c, int column) {
        if (this.isCompacted || c < 0 || c > 0x110001 || column < 0 || column >= this.columns - 2) {
            return 0;
        }
        int index = this.findRow(c);
        return this.v[index + 2 + column];
    }

    public int[] getRow(int rowIndex) {
        if (this.isCompacted) {
            throw new IllegalStateException("Illegal Invocation of the method after compact()");
        }
        if (rowIndex < 0 || rowIndex > this.rows) {
            throw new IllegalArgumentException("rowIndex out of bound!");
        }
        int[] rowToReturn = new int[this.columns - 2];
        System.arraycopy(this.v, rowIndex * this.columns + 2, rowToReturn, 0, this.columns - 2);
        return rowToReturn;
    }

    public int getRowStart(int rowIndex) {
        if (this.isCompacted) {
            throw new IllegalStateException("Illegal Invocation of the method after compact()");
        }
        if (rowIndex < 0 || rowIndex > this.rows) {
            throw new IllegalArgumentException("rowIndex out of bound!");
        }
        return this.v[rowIndex * this.columns];
    }

    public int getRowEnd(int rowIndex) {
        if (this.isCompacted) {
            throw new IllegalStateException("Illegal Invocation of the method after compact()");
        }
        if (rowIndex < 0 || rowIndex > this.rows) {
            throw new IllegalArgumentException("rowIndex out of bound!");
        }
        return this.v[rowIndex * this.columns + 1] - 1;
    }

    public void compact(CompactHandler compactor) {
        if (this.isCompacted) {
            return;
        }
        this.isCompacted = true;
        int valueColumns = this.columns - 2;
        Integer[] indexArray = new Integer[this.rows];
        for (int i = 0; i < this.rows; ++i) {
            indexArray[i] = this.columns * i;
        }
        Arrays.sort(indexArray, new Comparator<Integer>(){

            @Override
            public int compare(Integer o1, Integer o2) {
                int indexOfRow1 = o1;
                int indexOfRow2 = o2;
                int count = PropsVectors.this.columns;
                int index = 2;
                do {
                    if (PropsVectors.this.v[indexOfRow1 + index] != PropsVectors.this.v[indexOfRow2 + index]) {
                        return PropsVectors.this.v[indexOfRow1 + index] < PropsVectors.this.v[indexOfRow2 + index] ? -1 : 1;
                    }
                    if (++index != PropsVectors.this.columns) continue;
                    index = 0;
                } while (--count > 0);
                return 0;
            }
        });
        int count = -valueColumns;
        for (int i = 0; i < this.rows; ++i) {
            int start2 = this.v[indexArray[i]];
            if (count < 0 || !this.areElementsSame(indexArray[i] + 2, this.v, indexArray[i - 1] + 2, valueColumns)) {
                count += valueColumns;
            }
            if (start2 == 0x110000) {
                compactor.setRowIndexForInitialValue(count);
                continue;
            }
            if (start2 != 0x110001) continue;
            compactor.setRowIndexForErrorValue(count);
        }
        compactor.startRealValues(count += valueColumns);
        int[] temp = new int[count];
        count = -valueColumns;
        for (int i = 0; i < this.rows; ++i) {
            int start3 = this.v[indexArray[i]];
            int limit = this.v[indexArray[i] + 1];
            if (count < 0 || !this.areElementsSame(indexArray[i] + 2, temp, count, valueColumns)) {
                System.arraycopy(this.v, indexArray[i] + 2, temp, count += valueColumns, valueColumns);
            }
            if (start3 >= 0x110000) continue;
            compactor.setRowIndexForRange(start3, limit - 1, count);
        }
        this.v = temp;
        this.rows = count / valueColumns + 1;
    }

    public int[] getCompactedArray() {
        if (!this.isCompacted) {
            throw new IllegalStateException("Illegal Invocation of the method before compact()");
        }
        return this.v;
    }

    public int getCompactedRows() {
        if (!this.isCompacted) {
            throw new IllegalStateException("Illegal Invocation of the method before compact()");
        }
        return this.rows;
    }

    public int getCompactedColumns() {
        if (!this.isCompacted) {
            throw new IllegalStateException("Illegal Invocation of the method before compact()");
        }
        return this.columns - 2;
    }

    public IntTrie compactToTrieWithRowIndexes() {
        PVecToTrieCompactHandler compactor = new PVecToTrieCompactHandler();
        this.compact(compactor);
        return compactor.builder.serialize(new DefaultGetFoldedValue(compactor.builder), new DefaultGetFoldingOffset());
    }

    public static interface CompactHandler {
        public void setRowIndexForRange(int var1, int var2, int var3);

        public void setRowIndexForInitialValue(int var1);

        public void setRowIndexForErrorValue(int var1);

        public void startRealValues(int var1);
    }

    private static class DefaultGetFoldedValue
    implements TrieBuilder.DataManipulate {
        private IntTrieBuilder builder;

        public DefaultGetFoldedValue(IntTrieBuilder inBuilder) {
            this.builder = inBuilder;
        }

        @Override
        public int getFoldedValue(int start2, int offset) {
            int initialValue = this.builder.m_initialValue_;
            int limit = start2 + 1024;
            while (start2 < limit) {
                boolean[] inBlockZero = new boolean[1];
                int value2 = this.builder.getValue(start2, inBlockZero);
                if (inBlockZero[0]) {
                    start2 += 32;
                    continue;
                }
                if (value2 != initialValue) {
                    return offset;
                }
                ++start2;
            }
            return 0;
        }
    }

    private static class DefaultGetFoldingOffset
    implements Trie.DataManipulate {
        private DefaultGetFoldingOffset() {
        }

        @Override
        public int getFoldingOffset(int value2) {
            return value2;
        }
    }
}

