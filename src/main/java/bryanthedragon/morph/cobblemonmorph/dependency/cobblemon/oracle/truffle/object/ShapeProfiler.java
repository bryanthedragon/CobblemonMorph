
package com.oracle.truffle.object;

import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.object.ObjectStorageOptions;
import com.oracle.truffle.object.ShapeImpl;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

class ShapeProfiler {
    private static final String LINE_SEPARATOR = "***********************************************";
    private static final String BULLET = "* ";
    private static final String TOKEN_SEPARATOR = "\t";
    private final ConcurrentLinkedQueue<DynamicObject> queue = new ConcurrentLinkedQueue();
    private static final ShapeProfiler shapeProf;

    ShapeProfiler() {
    }

    public void track(DynamicObject obj) {
        this.queue.add(obj);
    }

    public void dump(PrintWriter out) {
        ShapeStats globalStats = new ShapeStats("Cumulative results for all shapes");
        for (DynamicObject obj : this.queue) {
            Shape shape = obj.getShape();
            globalStats.profile(shape);
        }
        globalStats.dump(out);
    }

    public void dump(PrintWriter out, int topResults) {
        if (topResults > 0) {
            IdentityHashMap<Shape, ShapeStats> shapeMap = new IdentityHashMap<Shape, ShapeStats>();
            for (DynamicObject obj : this.queue) {
                Shape shape = obj.getShape();
                ShapeStats stats = (ShapeStats)shapeMap.get(shape);
                if (stats == null) {
                    stats = new ShapeStats(ShapeProfiler.createLabel(shape));
                    shapeMap.put(shape, stats);
                }
                stats.profile(shape);
            }
            ArrayList allStats = new ArrayList(shapeMap.values());
            Collections.sort(allStats, new Comparator<ShapeStats>(){

                @Override
                public int compare(ShapeStats a, ShapeStats b) {
                    return Long.compare(b.objects, a.objects);
                }
            });
            int top = Math.min(topResults, allStats.size());
            ShapeStats avgStats = new ShapeStats("Cumulative results for top " + top + " shapes");
            for (int i = 0; i < top; ++i) {
                ShapeStats stats = (ShapeStats)allStats.get(i);
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
            Runtime.getRuntime().addShutdownHook(new Thread(){

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
            ++this.objects;
            this.oac += (long)((ShapeImpl)shape).getObjectArrayCapacity();
            this.oas += (long)((ShapeImpl)shape).getObjectArraySize();
            this.ofs += (long)((ShapeImpl)shape).getObjectFieldSize();
            this.pac += (long)((ShapeImpl)shape).getPrimitiveArrayCapacity();
            this.pas += (long)((ShapeImpl)shape).getPrimitiveArraySize();
            this.pfs += (long)((ShapeImpl)shape).getPrimitiveFieldSize();
        }

        public void add(ShapeStats stats) {
            this.objects += stats.objects;
            this.oac += stats.oac;
            this.oas += stats.oas;
            this.ofs += stats.ofs;
            this.pac += stats.pac;
            this.pas += stats.pas;
            this.pfs += stats.pfs;
        }

        public void dump(PrintWriter out) {
            DecimalFormat format = new DecimalFormat("###.####");
            out.println(ShapeProfiler.LINE_SEPARATOR);
            out.println(ShapeProfiler.BULLET + this.label);
            out.println(ShapeProfiler.LINE_SEPARATOR);
            out.println("* Allocated objects:\t" + this.objects);
            out.println("* Total object array capacity:\t" + this.oac);
            out.println("* Total object array size:\t" + this.oas);
            out.println("* Total object field size:\t" + this.ofs);
            out.println("* Average object array capacity:\t" + this.avgOAC(format));
            out.println("* Average object array size:\t" + this.avgOAS(format));
            out.println("* Average object field size:\t" + this.avgOFS(format));
            out.println(ShapeProfiler.LINE_SEPARATOR);
            out.println("* Total primitive array capacity:\t" + this.pac);
            out.println("* Total primitive array size:\t" + this.pas);
            out.println("* Total primitive field size:\t" + this.pfs);
            out.println("* Average primitive array capacity:\t" + this.avgPAC(format));
            out.println("* Average primitive array size:\t" + this.avgPAS(format));
            out.println("* Average primitive field size:\t" + this.avgPFS(format));
            out.println(ShapeProfiler.LINE_SEPARATOR);
            out.println(ShapeProfiler.BULLET + this.toString());
            out.println("***********************************************\n");
            out.flush();
        }

        public String toString() {
            DecimalFormat format = new DecimalFormat("###.####");
            return "{" + this.label + "}\t" + this.objects + ShapeProfiler.TOKEN_SEPARATOR + this.avgOAC(format) + ShapeProfiler.TOKEN_SEPARATOR + this.avgOAS(format) + ShapeProfiler.TOKEN_SEPARATOR + this.avgOFS(format) + ShapeProfiler.TOKEN_SEPARATOR + this.avgPAC(format) + ShapeProfiler.TOKEN_SEPARATOR + this.avgPAS(format) + ShapeProfiler.TOKEN_SEPARATOR + this.avgPFS(format);
        }

        private String avgOAC(DecimalFormat format) {
            return format.format((double)this.oac / (double)this.objects);
        }

        private String avgOAS(DecimalFormat format) {
            return format.format((double)this.oas / (double)this.objects);
        }

        private String avgOFS(DecimalFormat format) {
            return format.format((double)this.ofs / (double)this.objects);
        }

        private String avgPAC(DecimalFormat format) {
            return format.format((double)this.pac / (double)this.objects);
        }

        private String avgPAS(DecimalFormat format) {
            return format.format((double)this.pas / (double)this.objects);
        }

        private String avgPFS(DecimalFormat format) {
            return format.format((double)this.pfs / (double)this.objects);
        }
    }
}

