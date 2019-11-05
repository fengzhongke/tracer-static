package com.ali.trace.statics.analize;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;

public class ClassViewer extends ClassVisitor {
    MetaFactory factory;
    private MetaFactory.ClassMeta meta;

    public ClassViewer(MetaFactory factory, byte[] bytes) throws IOException {
        super(Opcodes.ASM7, new ClassWriter(ClassWriter.COMPUTE_MAXS));
        this.factory = factory;
        new ClassReader(bytes).accept(this, ClassReader.SKIP_DEBUG);
    }

    public void visit(final int version, final int access, final String cname, final String signature,
                      final String superName, final String[] interfaces) {
        this.meta = factory.getMeta(cname);
        super.visit(version, access, cname, signature, superName, interfaces);
    }

    public MethodVisitor visitMethod(final int access, final String mname, final String descriptor,
                                     final String signature, final String[] exceptions) {
        return new MethodViewer(mname, descriptor,
                cv.visitMethod(access, mname, descriptor, signature, exceptions));
    }

    private class MethodViewer extends MethodVisitor {
        private MetaFactory.MethodMeta method;

        public MethodViewer(String name, String descriptor, MethodVisitor mv) {
            super(Opcodes.ASM7, mv);
            this.method = meta.addMethod(name, descriptor);
        }

        public void visitMethodInsn(final int opcode, final String invokeCname, final String invokeMname,
                                    final String descriptor, final boolean isInterface) {
            method.addInvoke(factory.getMeta(invokeCname).addMethod(invokeMname, descriptor));
            super.visitMethodInsn(opcode, invokeCname, invokeMname, descriptor, isInterface);
        }
    }
}