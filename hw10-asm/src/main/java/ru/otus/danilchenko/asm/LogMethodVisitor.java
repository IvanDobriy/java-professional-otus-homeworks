package ru.otus.danilchenko.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class LogMethodVisitor extends MethodVisitor {
    private final MethodVisitor mv;
    private final String internalName;
    private final String interfaceName;
    private final String interfaceDescriptor;
    private final String name;
    private final String descriptor;
    private boolean needHandle = false;

    public LogMethodVisitor(int api, MethodVisitor methodVisitor,
                            String internalName, String interfaceName,
                            String interfaceDescriptor, String name, String descriptor) {
        super(api, null);
        this.mv = methodVisitor;
        this.internalName = internalName;
        this.interfaceName = interfaceName;
        this.interfaceDescriptor = interfaceDescriptor;
        this.name = name;
        this.descriptor = descriptor;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (descriptor.equals(Type.getDescriptor(Log.class))) {
            needHandle = true;
        }
        return mv.visitAnnotation(descriptor, visible);
    }

    @Override
    public void visitCode() {
        mv.visitCode();
        if (needHandle) {
            emitPrint();
        }

        // делегирование: target.method(args)
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitFieldInsn(Opcodes.GETFIELD, internalName, "target", interfaceDescriptor);

        Type[] args = Type.getArgumentTypes(descriptor);
        int slot = 1;
        for (Type t : args) {
            loadArg(mv, t, slot);
            slot += t.getSize();
        }
        mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, interfaceName, name, descriptor, true);

        Type returnType = Type.getMethodType(descriptor).getReturnType();
        if (returnType.equals(Type.VOID_TYPE)) {
            mv.visitInsn(Opcodes.RETURN);
        } else if (returnType.equals(Type.INT_TYPE) || returnType.equals(Type.BOOLEAN_TYPE)
                || returnType.equals(Type.BYTE_TYPE) || returnType.equals(Type.SHORT_TYPE)
                || returnType.equals(Type.CHAR_TYPE)) {
            mv.visitInsn(Opcodes.IRETURN);
        } else if (returnType.equals(Type.LONG_TYPE)) {
            mv.visitInsn(Opcodes.LRETURN);
        } else if (returnType.equals(Type.FLOAT_TYPE)) {
            mv.visitInsn(Opcodes.FRETURN);
        } else if (returnType.equals(Type.DOUBLE_TYPE)) {
            mv.visitInsn(Opcodes.DRETURN);
        } else {
            mv.visitInsn(Opcodes.ARETURN);
        }
        mv.visitMaxs(0, 0);
    }

    private void emitPrint() {
        mv.visitFieldInsn(Opcodes.GETSTATIC,
                "java/lang/System", "out", "Ljava/io/PrintStream;");

        mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL,
                "java/lang/StringBuilder", "<init>", "()V", false);

        mv.visitLdcInsn("[asm] " + name + "(");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/lang/StringBuilder", "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

        int slot = 1;
        Type[] args = Type.getArgumentTypes(descriptor);
        for (int i = 0; i < args.length; i++) {
            if (i > 0) {
                mv.visitLdcInsn(", ");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        "java/lang/StringBuilder", "append",
                        "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
            }
            switch (args[i].getSort()) {
                case Type.INT, Type.BOOLEAN, Type.BYTE, Type.SHORT, Type.CHAR -> {
                    mv.visitVarInsn(Opcodes.ILOAD, slot);
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                            "java/lang/StringBuilder", "append",
                            "(I)Ljava/lang/StringBuilder;", false);
                }
                case Type.LONG -> {
                    mv.visitVarInsn(Opcodes.LLOAD, slot);
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                            "java/lang/StringBuilder", "append",
                            "(J)Ljava/lang/StringBuilder;", false);
                }
                case Type.FLOAT -> {
                    mv.visitVarInsn(Opcodes.FLOAD, slot);
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                            "java/lang/StringBuilder", "append",
                            "(F)Ljava/lang/StringBuilder;", false);
                }
                case Type.DOUBLE -> {
                    mv.visitVarInsn(Opcodes.DLOAD, slot);
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                            "java/lang/StringBuilder", "append",
                            "(D)Ljava/lang/StringBuilder;", false);
                }
                default -> {
                    mv.visitVarInsn(Opcodes.ALOAD, slot);
                    mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                            "java/lang/StringBuilder", "append",
                            "(Ljava/lang/Object;)Ljava/lang/StringBuilder;", false);
                }
            }
            slot += args[i].getSize();
        }

        mv.visitLdcInsn(")");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/lang/StringBuilder", "append",
                "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/lang/StringBuilder", "toString",
                "()Ljava/lang/String;", false);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
    }

    private void loadArg(MethodVisitor mv, Type type, int slot) {
        if (type.equals(Type.INT_TYPE) || type.equals(Type.BOOLEAN_TYPE)
                || type.equals(Type.BYTE_TYPE) || type.equals(Type.SHORT_TYPE) || type.equals(Type.CHAR_TYPE)) {
            mv.visitVarInsn(Opcodes.ILOAD, slot);
        } else if (type.equals(Type.LONG_TYPE)) {
            mv.visitVarInsn(Opcodes.LLOAD, slot);
        } else if (type.equals(Type.FLOAT_TYPE)) {
            mv.visitVarInsn(Opcodes.FLOAD, slot);
        } else if (type.equals(Type.DOUBLE_TYPE)) {
            mv.visitVarInsn(Opcodes.DLOAD, slot);
        } else {
            mv.visitVarInsn(Opcodes.ALOAD, slot);
        }
    }
}