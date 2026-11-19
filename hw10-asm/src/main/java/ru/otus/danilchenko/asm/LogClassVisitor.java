package ru.otus.danilchenko.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Objects;

public class LogClassVisitor extends ClassVisitor {
    private final ClassWriter writer;
    private final String internalName;
    private final String interfaceName;
    private final String interfaceDescriptor;

    public LogClassVisitor(int api, ClassWriter writer,
                          String internalName, String interfaceName, String interfaceDescriptor) {
        super(api, null);
        Objects.requireNonNull(writer);
        this.writer = writer;
        this.internalName = internalName;
        this.interfaceName = interfaceName;
        this.interfaceDescriptor = interfaceDescriptor;
    }

    @Override
    public void visit(int version, int access, String name, String signature,
                     String superName, String[] interfaces) {
        // заголовок класса уже создан в createConstructor — ничего не делаем
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor,
                                    String signature, String[] exceptions) {
        if (name.startsWith("<")) {
            return null;
        }
        MethodVisitor mv = writer.visitMethod(Opcodes.ACC_PUBLIC, name, descriptor, signature, exceptions);
        return new LogMethodVisitor(api, mv, internalName, interfaceName, interfaceDescriptor, name, descriptor);
    }

    @Override
    public void visitEnd() {
        // ничего дополнительно
    }
}