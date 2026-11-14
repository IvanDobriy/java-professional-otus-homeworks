package ru.otus.danilchenko.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class LogClassVisitor extends ClassVisitor {
    protected LogClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if(name.startsWith("<")){
            return null;
        }
        System.out.println(String.format("method name: %s", name));
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }
}
