package ru.otus.danilchenko.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.util.Objects;

public class LogClassVisitor extends ClassVisitor {
    private final ClassWriter writer;
    protected LogClassVisitor(int api, ClassWriter writer) {
        Objects.requireNonNull(writer);
        this.writer = writer;
        super(api, null);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if(name.startsWith("<")){
            return null;
        }
        System.out.println(String.format("method name: %s", name));
        final var visitor = writer.visitMethod(access, name, descriptor, signature, exceptions);
        return new LogMethodVisitor(api, access, name, descriptor, visitor);
    }
}
