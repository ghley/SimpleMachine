package dev.simplemachine.opengl.glenum;

import org.lwjgl.opengl.GL11;

public enum DataType {
    BYTE(GL11.GL_BYTE, 8),
    SHORT(GL11.GL_SHORT, 16),
    INT(GL11.GL_INT, 32),
    FLOAT(GL11.GL_FLOAT, 32),
    DOUBLE(GL11.GL_DOUBLE, 64),
    U_CHAR(GL11.GL_UNSIGNED_BYTE, 8),
    U_SHORT(GL11.GL_UNSIGNED_SHORT, 16),
    U_INT(GL11.GL_UNSIGNED_INT, 32);

    public int constant, bitSize;
    DataType(int constant, int bitSize) {
        this.constant = constant;
        this.bitSize = bitSize;
    }
}
