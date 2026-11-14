package ru.otus.danilchenko.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class LogMethodVisitor extends MethodVisitor {
    private boolean needHandle = false;
    private static final String logDescriptorName = Type.getDescriptor(Log.class);
    private final int access;
    private final String name;
    private final String descriptor;




    protected LogMethodVisitor(int api,  int access, String name, String descriptor, MethodVisitor methodVisitor) {
        this.access = access;
        this.name = name;
        this.descriptor = descriptor;

        super(api, methodVisitor);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if(descriptor.equals(logDescriptorName)){
           needHandle = true;
        }
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public void visitCode() {
        if (!needHandle) {
            super.visitCode();
            return;
        }
        super.visitCode();   // сначала открыть тело метода
        emitPrint();
    }

    private void emitPrint() {
        // System.out
        super.visitFieldInsn(Opcodes.GETSTATIC,
                "java/lang/System", "out", "Ljava/io/PrintStream;");

        // new StringBuilder
        super.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
        super.visitInsn(Opcodes.DUP);
        super.visitMethodInsn(Opcodes.INVOKESPECIAL,
                "java/lang/StringBuilder", "<init>", "()V", false);

        // "[asm] name("
        super.visitLdcInsn("[asm] " + name + "(");
        super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/lang/StringBuilder", "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

        // параметры
        int slot = (access & Opcodes.ACC_STATIC) == 0 ? 1 : 0;
        Type[] args = Type.getArgumentTypes(descriptor);
        for (int i = 0; i < args.length; i++) {
            if (i > 0) {
                super.visitLdcInsn(", ");
                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        "java/lang/StringBuilder", "append",
                        "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            }
            switch (args[i].getSort()) {
                case Type.INT, Type.BOOLEAN, Type.BYTE, Type.SHORT, Type.CHAR -> {
                    super.visitVarInsn(Opcodes.ILOAD, slot);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                            "java/lang/StringBuilder", "append",
                            "(I)Ljava/lang/StringBuilder;", false);
                }
                case Type.LONG -> {
                    super.visitVarInsn(Opcodes.LLOAD, slot);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                            "java/lang/StringBuilder", "append",
                            "(J)Ljava/lang/StringBuilder;", false);
                }
                case Type.FLOAT -> {
                    super.visitVarInsn(Opcodes.FLOAD, slot);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                            "java/lang/StringBuilder", "append",
                            "(F)Ljava/lang/StringBuilder;", false);
                }
                case Type.DOUBLE -> {
                    super.visitVarInsn(Opcodes.DLOAD, slot);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                            "java/lang/StringBuilder", "append",
                            "(D)Ljava/lang/StringBuilder;", false);
                }
                default -> {
                    super.visitVarInsn(Opcodes.ALOAD, slot);
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                            "java/lang/StringBuilder", "append",
                            "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false);
                }
            }
            slot += args[i].getSize();
        }

        // ")"
        super.visitLdcInsn(")");
        super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/lang/StringBuilder", "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

        // toString + println
        super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/lang/StringBuilder", "toString",
                "()Ljava/lang/String;", false);
        super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    }


}
