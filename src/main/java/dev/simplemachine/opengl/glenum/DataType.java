package dev.simplemachine.opengl.glenum;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL41;

public enum DataType {
    BYTE(GL11.GL_BYTE, 8),
    U_BYTE(GL11.GL_UNSIGNED_BYTE, 8),
    SHORT(GL11.GL_SHORT, 16),
    U_SHORT(GL11.GL_UNSIGNED_SHORT, 16),
    INT(GL11.GL_INT, 32),
    U_INT(GL11.GL_UNSIGNED_INT, 32),
    FIXED(GL41.GL_FIXED, 32),
    FLOAT(GL11.GL_FLOAT, 32),
    HALF_FLOAT(GL30.GL_HALF_FLOAT, 16),
    DOUBLE(GL11.GL_DOUBLE, 64);


    public int constant, bitSize;
    DataType(int constant, int bitSize) {
        this.constant = constant;
        this.bitSize = bitSize;
    }
}
