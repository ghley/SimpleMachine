package dev.simplemachine.opengl;

import dev.simplemachine.opengl.objects.OglBuffer;

public class GlobalVariables {
    private static GlobalVariables instance = new GlobalVariables();

    private OglBuffer cameraBuffer;

    private GlobalVariables(){

    }

    public static GlobalVariables getInstance() {
        return instance;
    }

    public void setCameraBuffer(OglBuffer cameraBuffer) {
        this.cameraBuffer = cameraBuffer;
    }

    public OglBuffer getCameraBuffer() {
        return cameraBuffer;
    }
}
