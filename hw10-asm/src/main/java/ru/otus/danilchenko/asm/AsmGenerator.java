package ru.otus.danilchenko.asm;

import org.objectweb.asm.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public class AsmGenerator {

    private static byte[] readClassBytes(Class<?> clazz) {
        String path = "/" + clazz.getName().replace('.', '/') + ".class";
        try (var in = clazz.getResourceAsStream(path)) {
            if (in == null) throw new IllegalStateException("Class not found: " + path);
            return in.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T createProxy(Class<?> proxyClazz, Class<?> interfaceClass, T instance) {
        try {
            return (T) proxyClazz.getConstructor(interfaceClass).newInstance(instance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void createConstructor(ClassWriter writer, String internalName, String interfaceName, String interfaceDescriptor) {
        // Объявляем класс: public, имя MathServiceProxy, родитель Object,
        // реализует интерфейс MathService.
        writer.visit(Opcodes.V25, Opcodes.ACC_PUBLIC, internalName, null,
                "java/lang/Object", new String[]{interfaceName});

        // Поле target — реальный сервис, которому делегируем вызовы.
        // private final MathService target;
        FieldVisitor field = writer.visitField(Opcodes.ACC_PRIVATE | Opcodes.ACC_FINAL,
                "target", interfaceDescriptor, null, null);
        field.visitEnd();

        // Конструктор: MathServiceProxy(MathService target)
        MethodVisitor ctor = writer.visitMethod(Opcodes.ACC_PUBLIC, "<init>",
                "(" + interfaceDescriptor + ")V", null, null);
        ctor.visitCode();
        // super() — вызов конструктора Object
        ctor.visitVarInsn(Opcodes.ALOAD, 0);                 // this
        ctor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        // this.target = target
        ctor.visitVarInsn(Opcodes.ALOAD, 0);                 // this
        ctor.visitVarInsn(Opcodes.ALOAD, 1);                 // аргумент target
        ctor.visitFieldInsn(Opcodes.PUTFIELD, internalName, "target", interfaceDescriptor);
        ctor.visitInsn(Opcodes.RETURN);
        ctor.visitMaxs(0, 0); // значения пересчитаются автоматически
        ctor.visitEnd();
    }

    public static <T> T generateProxy( T obj,  Class<T> interfaceClass)  {
        final String internalProxyName = Type.getInternalName(obj.getClass()) + "Proxy";
        final String interfaceInternalName = Type.getInternalName(interfaceClass);
        final String interfaceDescriptor = Type.getDescriptor(interfaceClass);

        byte[] bytes = readClassBytes(obj.getClass());
        final var reader = new ClassReader(bytes);
        final var writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        createConstructor(writer, internalProxyName, interfaceInternalName, interfaceDescriptor);
        reader.accept(new LogClassVisitor(Opcodes.ASM9, writer, internalProxyName, interfaceInternalName, interfaceDescriptor), 0);
        writer.visitEnd();

        byte[] bytecode  = writer.toByteArray();

        try (FileOutputStream out = new FileOutputStream("CalculatorProxy.class")) {
            out.write(bytecode);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        Class<?> proxyClass = new ByteArrayClassLoader(Thread.currentThread().getContextClassLoader())
                .defineAndLoad(obj.getClass().getName() + "Proxy", bytecode);
        return createProxy(proxyClass, interfaceClass, obj);
    }
}