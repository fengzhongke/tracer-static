package com.ali.trace.statics.analize;

import org.objectweb.asm.Type;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @auther hanlang
 * @date 2019-11-05 12:24
 */
public class MetaFactory {

    private final ConcurrentHashMap<String, ClassMeta> POOL = new ConcurrentHashMap<String, ClassMeta>();

    public String getClasz(String cname) {
        return Type.getType("L" + cname + ";").getClassName();
    }

    public String getMethod(String mname, String descriptor) {
        StringBuilder sb = new StringBuilder();
        String split = "";
        for (Type argType : Type.getArgumentTypes(descriptor)) {
            sb.append(split).append(argType.getClassName());
            split = ",";
        }
        return mname + "(" + sb + ")";
    }

    public ClassMeta getMeta(String cname) {
        cname = getClasz(cname);
        ClassMeta clazz = POOL.get(cname);
        if (clazz == null) {
            ClassMeta oldClazz = POOL.putIfAbsent(cname, clazz = new ClassMeta(cname));
            if (oldClazz != null) {
                clazz = oldClazz;
            }
        }
        return clazz;
    }

    public Map<String, ClassMeta> getMetas() {
        return new TreeMap<String, ClassMeta>(POOL);
    }

    public class ClassMeta {
        private String cname;
        private final ConcurrentHashMap<String, MethodMeta> METHODS = new ConcurrentHashMap<String, MethodMeta>();

        private ClassMeta(String cname) {
            this.cname = cname;
        }

        public Map<String, MethodMeta> getMethods() {
            return METHODS;
        }

        public MethodMeta getMethod(String name) {
            return METHODS.get(name);
        }

        public MethodMeta addMethod(String mname, String descriptor) {
            MethodMeta method = null;
            String name = MetaFactory.this.getMethod(mname, descriptor);
            if ((method = METHODS.get(name)) == null) {
                synchronized (METHODS) {
                    if ((method = METHODS.get(name)) == null) {
                        METHODS.put(name, method = new MethodMeta(this, mname, descriptor));
                    }
                }
            }
            return method;
        }

        public String toString() {
            return cname;
        }
    }

    public class MethodMeta {
        final ClassMeta clazz;
        final String mname;
        final String descriptor;
        Map<String, MethodMeta> invokes = new TreeMap<String, MethodMeta>();
        Map<String, MethodMeta> beInvokes = new TreeMap<String, MethodMeta>();

        private MethodMeta(ClassMeta clazz, String mname, String descriptor) {
            this.clazz = clazz;
            this.mname = mname;
            this.descriptor = descriptor;
        }

        public void addInvoke(MethodMeta invoke) {
            invokes.put(getMethod(invoke.mname, invoke.descriptor), invoke);
            invoke.beInvokes.put(getMethod(mname, descriptor), this);
        }

        public ClassMeta getClazz() {
            return clazz;
        }

        public Map<String, MethodMeta> getInvokes() {
            return invokes;
        }

        public Map<String, MethodMeta> getBeInvokes() {
            return beInvokes;
        }

        public String getRetType() {
            return Type.getReturnType(descriptor).getClassName();
        }

        public String toString() {
            return getMethod(mname, descriptor);
        }
    }
}
