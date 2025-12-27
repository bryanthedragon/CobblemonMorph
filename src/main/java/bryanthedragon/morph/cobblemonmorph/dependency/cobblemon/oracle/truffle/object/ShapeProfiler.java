package com.oracle.truffle.object;

import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.Shape;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

class ShapeProfiler {
   private static final String LINE_SEPARATOR = "***********************************************";
   private static final String BULLET = "* ";
   private static final String TOKEN_SEPARATOR = "\t";
   private final ConcurrentLinkedQueue<DynamicObject> queue = new ConcurrentLinkedQueue<>();
   private static final ShapeProfiler shapeProf;

   public void track(DynamicObject obj) {
      this.queue.add(obj);
   }

   public void dump(PrintWriter out) {
      ShapeProfiler.ShapeStats globalStats = new ShapeProfiler.ShapeStats("Cumulative results for all shapes");

      for (DynamicObject obj : this.queue) {
         Shape shape = obj.getShape();
         globalStats.profile(shape);
      }

      globalStats.dump(out);
   }

   public void dump(PrintWriter out, int topResults) {
      if (topResults > 0) {
         IdentityHashMap<Shape, ShapeProfiler.ShapeStats> shapeMap = new IdentityHashMap<>();

         for (DynamicObject obj : this.queue) {
            Shape shape = obj.getShape();
            ShapeProfiler.ShapeStats stats = shapeMap.get(shape);
            if (stats == null) {
               shapeMap.put(shape, stats = new ShapeProfiler.ShapeStats(createLabel(shape)));
            }

            stats.profile(shape);
         }

         List<ShapeProfiler.ShapeStats> allStats = new ArrayList<>(shapeMap.values());
         Collections.sort(allStats, new Comparator<ShapeProfiler.ShapeStats>() {
            public int compare(ShapeProfiler.ShapeStats a, ShapeProfiler.ShapeStats b) {
               return Long.compare(b.objects, a.objects);
            }
         });
         int top = Math.min(topResults, allStats.size());
         ShapeProfiler.ShapeStats avgStats = new ShapeProfiler.ShapeStats("Cumulative results for top " + top + " shapes");

         for (int i = 0; i < top; i++) {
            ShapeProfiler.ShapeStats stats = allStats.get(i);
            stats.setLabel("Shape " + (i + 1) + ": " + stats.getLabel());
            stats.dump(out);
            avgStats.add(stats);
         }

         avgStats.dump(out);
      }

      this.dump(out);
   }

   private static String createLabel(Shape shape) {
      String label = shape.toString();
      return label.substring(label.indexOf(123) + 1, label.lastIndexOf(125));
   }

   public static ShapeProfiler getInstance() {
      return shapeProf;
   }

   static {
      if (ObjectStorageOptions.Profile) {
         shapeProf = new ShapeProfiler();
         Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
               ShapeProfiler.getInstance().dump(new PrintWriter(System.out), ObjectStorageOptions.ProfileTopResults);
            }
         });
      } else {
         shapeProf = null;
      }
   }

   private static class ShapeStats {
      private String label;
      private long objects;
      private long oac;
      private long oas;
      private long ofs;
      private long pac;
      private long pas;
      private long pfs;

      ShapeStats(String label) {
         this.label = label;
      }

      public String getLabel() {
         return this.label;
      }

      public void setLabel(String label) {
         this.label = label;
      }

      public void profile(Shape shape) {
         this.objects++;
         this.oac = this.oac + ((ShapeImpl)shape).getObjectArrayCapacity();
         this.oas = this.oas + ((ShapeImpl)shape).getObjectArraySize();
         this.ofs = this.ofs + ((ShapeImpl)shape).getObjectFieldSize();
         this.pac = this.pac + ((ShapeImpl)shape).getPrimitiveArrayCapacity();
         this.pas = this.pas + ((ShapeImpl)shape).getPrimitiveArraySize();
         this.pfs = this.pfs + ((ShapeImpl)shape).getPrimitiveFieldSize();
      }

      public void add(ShapeProfiler.ShapeStats stats) {
         this.objects = this.objects + stats.objects;
         this.oac = this.oac + stats.oac;
         this.oas = this.oas + stats.oas;
         this.ofs = this.ofs + stats.ofs;
         this.pac = this.pac + stats.pac;
         this.pas = this.pas + stats.pas;
         this.pfs = this.pfs + stats.pfs;
      }

      public void dump(PrintWriter out) {
         DecimalFormat format = new DecimalFormat("###.####");
         out.println("***********************************************");
         out.println("* " + this.label);
         out.println("***********************************************");
         out.println("* Allocated objects:\t" + this.objects);
         out.println("* Total object array capacity:\t" + this.oac);
         out.println("* Total object array size:\t" + this.oas);
         out.println("* Total object field size:\t" + this.ofs);
         out.println("* Average object array capacity:\t" + this.avgOAC(format));
         out.println("* Average object array size:\t" + this.avgOAS(format));
         out.println("* Average object field size:\t" + this.avgOFS(format));
         out.println("***********************************************");
         out.println("* Total primitive array capacity:\t" + this.pac);
         out.println("* Total primitive array size:\t" + this.pas);
         out.println("* Total primitive field size:\t" + this.pfs);
         out.println("* Average primitive array capacity:\t" + this.avgPAC(format));
         out.println("* Average primitive array size:\t" + this.avgPAS(format));
         out.println("* Average primitive field size:\t" + this.avgPFS(format));
         out.println("***********************************************");
         out.println("* " + this.toString());
         out.println("***********************************************\n");
         out.flush();
      }

      @Override
      public String toString() {
         DecimalFormat format = new DecimalFormat("###.####");
         return "{"
            + this.label
            + "}\t"
            + this.objects
            + "\t"
            + this.avgOAC(format)
            + "\t"
            + this.avgOAS(format)
            + "\t"
            + this.avgOFS(format)
            + "\t"
            + this.avgPAC(format)
            + "\t"
            + this.avgPAS(format)
            + "\t"
            + this.avgPFS(format);
      }

      private String avgOAC(DecimalFormat format) {
         return format.format((double)this.oac / this.objects);
      }

      private String avgOAS(DecimalFormat format) {
         return format.format((double)this.oas / this.objects);
      }

      private String avgOFS(DecimalFormat format) {
         return format.format((double)this.ofs / this.objects);
      }

      private String avgPAC(DecimalFormat format) {
         return format.format((double)this.pac / this.objects);
      }

      private String avgPAS(DecimalFormat format) {
         return format.format((double)this.pas / this.objects);
      }

      private String avgPFS(DecimalFormat format) {
         return format.format((double)this.pfs / this.objects);
      }
   }
}
