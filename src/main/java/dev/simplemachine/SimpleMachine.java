package dev.simplemachine;

import dev.simplemachine.ecs.ECS;
import dev.simplemachine.ecs.components.CCamera;
import dev.simplemachine.ecs.systems.SStaticMeshRenderer;
import dev.simplemachine.ecs.systems.SUpdateCameraBuffer;
import dev.simplemachine.opengl.GlobalVariables;
import dev.simplemachine.opengl.glenum.BufferStorageType;
import dev.simplemachine.opengl.objects.BufferBuilder;
import org.joml.Matrix4f;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL45;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL31C.GL_UNIFORM_BUFFER;

public class SimpleMachine {

    private long window;

    private ECS ecs;

    private Runnable initCallback = ()->{};

    private Runnable loopCallback = ()->{};

    private final Vector2i dimension = new Vector2i();

    public SimpleMachine() {
        this(new SimpleMachineConfig(512,512));
    }

    public SimpleMachine(SimpleMachineConfig config) {
        dimension.x = config.width();
        dimension.y = config.height();
    }

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

        window = GLFW.glfwCreateWindow(dimension.x, dimension.y, "Simple Machine", 0, 0);

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

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        var globalVariables = GlobalVariables.getInstance();
        var cameraBuffer = BufferBuilder.newInstance()
                .byteLength(16 * 4 * 2 + 4 * 4) // 2 x mat4 + 1 x vec3 (padded with 1 byte)
                .flag(BufferStorageType.DYNAMIC_STORAGE)
                .build();
        globalVariables.setCameraBuffer(cameraBuffer);

        cameraBuffer.setData(0, new Matrix4f().identity().get(new float[16]));
        cameraBuffer.setData(16 * 4, new Matrix4f().identity().get(new float[16]));
        cameraBuffer.bindUniformBufferBase(10);

        ecs.registerSystem(SUpdateCameraBuffer.class);
        ecs.registerSystem(SStaticMeshRenderer.class);

        var cameraEntity = ecs.createEntity();
        ecs.addComponent(cameraEntity, CCamera.class);
        var camera = cameraEntity.getComponent(CCamera.class);
        camera.setProjectionMatrix(new Matrix4f().perspective((float)Math.toRadians(90), 1, 0.01f, 1000f));
        camera.setActive(true);
        camera.setPosition(new Vector3f(0, 0, -10));
        camera.setLookAt(new Vector3f(0, 0, -0.0001f));

        ecs.updateAll();
    }

    private void loop() {

        GL11.glClearColor(0.51f, 0.58f, 0.65f, 1.0f);

        while (!GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            ecs.updateAll();
            loopCallback.run();

            GLFW.glfwSwapBuffers(window);

            GLFW.glfwPollEvents();
            // FIXME for now fixed time step
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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

    public Vector2i getDimension() {
        return dimension;
    }
}
