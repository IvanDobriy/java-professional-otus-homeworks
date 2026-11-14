package ru.otus.danilchenko.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class LogClassVisitor extends ClassVisitor {
    protected LogClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
//        System.out.println(String.format("access: %s, name: %s, descriptor: %s, signature %s, exceptions: %s", access, name, descriptor, signature, Arrays.asList(exceptions)));
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }
}
