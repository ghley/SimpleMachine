package dev.simplemachine;

import dev.simplemachine.ecs.ECS;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

public class SimpleMachine {

    private long window;

    private ECS ecs;

    private Runnable initCallback = ()->{};

    private Runnable loopCallback = ()->{};
    public void run() {
        init();
        initCallback.run();
        loop();
        destroy();
    }

    private void init() {
        ecs = new ECS();

        GLFWErrorCallback.createPrint(System.err).set();

        if (!GLFW.glfwInit()) {
            throw new RuntimeException("glfwInit() failed");
        }

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);

        window = GLFW.glfwCreateWindow(1024, 786, "Simple Machine", 0, 0);
        if (window == 0) {
            throw new RuntimeException("glfwCreateWindow() failed");
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            var width = stack.mallocInt(1);
            var height = stack.mallocInt(1);

            GLFW.glfwGetWindowSize(window, width, height);

            var vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

            GLFW.glfwSetWindowPos(window,
                    (vidMode.width() - width.get(0))/2,
                    (vidMode.height() - height.get(0))/2);
        }

        GLFW.glfwMakeContextCurrent(window);

        // v-sync
        GLFW.glfwSwapInterval(1);

        GLFW.glfwShowWindow(window);

        GL.createCapabilities();
    }

    private void loop() {

        GL11.glClearColor(0.51f, 0.58f, 0.65f, 1.0f);

        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            loopCallback.run();

            GLFW.glfwSwapBuffers(window);

            GLFW.glfwPollEvents();
        }
    }

    private void destroy() {
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    public ECS getEcs() {
        return ecs;
    }

    public void setInitCallback(Runnable initCallback) {
        this.initCallback = initCallback;
    }

    public void setLoopCallback(Runnable loopCallback) {
        this.loopCallback = loopCallback;
    }
}
