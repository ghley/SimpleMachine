package dev.simplemachine.util;

import dev.simplemachine.opengl.glenum.DataType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PrettyPrinter {
    public static void printFloatArray(byte[] data) {




    }

    public static void print(DataType type, byte[] data) {
        var buffer = ByteBuffer.wrap(data);
        var strBuffer = new StringBuffer();
        strBuffer.append("[ ");
        System.out.println(type);

        while (buffer.hasRemaining()) {
        switch (type) {
            case FLOAT -> {
                strBuffer.append(buffer.getFloat());
            }
            case U_INT -> {
                strBuffer.append(buffer.getInt());
            }
        }
            if (buffer.hasRemaining()) {
                strBuffer.append(", ");
            }
        }
        strBuffer.append("]");
        System.out.println(strBuffer);
    }
}
