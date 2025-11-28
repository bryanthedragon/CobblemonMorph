package com.cobblemon.mod.relocations.ibm.icu.impl;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ICURWLock {
   private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
   private ICURWLock.Stats stats = null;

   public synchronized ICURWLock.Stats resetStats() {
      ICURWLock.Stats result = this.stats;
      this.stats = new ICURWLock.Stats();
      return result;
   }

   public synchronized ICURWLock.Stats clearStats() {
      ICURWLock.Stats result = this.stats;
      this.stats = null;
      return result;
   }

   public synchronized ICURWLock.Stats getStats() {
      return this.stats == null ? null : new ICURWLock.Stats(this.stats);
   }

   public void acquireRead() {
      if (this.stats != null) {
         synchronized (this) {
            this.stats._rc++;
            if (this.rwl.getReadLockCount() > 0) {
               this.stats._mrc++;
            }

            if (this.rwl.isWriteLocked()) {
               this.stats._wrc++;
            }
         }
      }

      this.rwl.readLock().lock();
   }

   public void releaseRead() {
      this.rwl.readLock().unlock();
   }

   public void acquireWrite() {
      if (this.stats != null) {
         synchronized (this) {
            this.stats._wc++;
            if (this.rwl.getReadLockCount() > 0 || this.rwl.isWriteLocked()) {
               this.stats._wwc++;
            }
         }
      }

      this.rwl.writeLock().lock();
   }

   public void releaseWrite() {
      this.rwl.writeLock().unlock();
   }

   public static final class Stats {
      public int _rc;
      public int _mrc;
      public int _wrc;
      public int _wc;
      public int _wwc;

      private Stats() {
      }

      private Stats(int rc, int mrc, int wrc, int wc, int wwc) {
         this._rc = rc;
         this._mrc = mrc;
         this._wrc = wrc;
         this._wc = wc;
         this._wwc = wwc;
      }

      private Stats(ICURWLock.Stats rhs) {
         this(rhs._rc, rhs._mrc, rhs._wrc, rhs._wc, rhs._wwc);
      }

      @Override
      public String toString() {
         return " rc: " + this._rc + " mrc: " + this._mrc + " wrc: " + this._wrc + " wc: " + this._wc + " wwc: " + this._wwc;
      }
   }
}
