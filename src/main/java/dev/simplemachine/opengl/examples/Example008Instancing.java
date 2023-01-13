package dev.simplemachine.opengl.examples;

import dev.simplemachine.SimpleMachine;
import dev.simplemachine.opengl.glenum.BufferStorageType;
import dev.simplemachine.opengl.glenum.DataType;
import dev.simplemachine.opengl.glenum.ShaderType;
import dev.simplemachine.opengl.gltf.GLBLoader;
import dev.simplemachine.opengl.objects.*;
import org.joml.Matrix4f;
import org.joml.Vector4f;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Example008Instancing {

    static SimpleMachine machine;
    private static OglVertexArray vao;

    public static void main(String[] args) {
        machine = new SimpleMachine();
        machine.setInitCallback(Example008Instancing::init);
        machine.setLoopCallback(Example008Instancing::loop);
        machine.run();
    }

    static OglProgram program;

    static int INSTANCE_COUNT = 200;

    static  Vector4f[] weights = new Vector4f[INSTANCE_COUNT];
    static OglBuffer colorBuffer;
    static OglBuffer weightsBuffer;

    public static void init() {
        var vert = """
                #version 450 core
                
                uniform mat4 model_matrix[4];
                uniform mat4 projection_matrix;
                
                layout (location = 0) in vec3 position;
                layout (location = 1) in vec3 normal;
                
                layout (location = 6) in vec4 instance_weights;
                layout (location = 7) in vec4 instance_color;
                
                out vec3 vs_fs_normal;
                out vec4 vs_fs_color;
                
                void main() {
                    int n;
                    mat4 m = mat4(0.0);
                    vec4 pos = vec4(position,1);
                    
                    vec4 weights = normalize(instance_weights);
                    for (n = 0; n < 4; n++) {
                        m += (model_matrix[n] * weights[n]);
                    }
                    
                    vs_fs_normal = normalize((m * vec4(normal, 0.0)).xyz);
                    vs_fs_color = instance_color;
                    gl_Position = projection_matrix * (m * pos);
                }
                """;

        var frag = """
                #version 450 core
                
                layout (location = 0) out vec4 color;
                
                in vec3 vs_fs_normal;
                in vec4 vs_fs_color;
                
                void main() {
                    color = vs_fs_color * (0.1 + abs(vs_fs_normal.z)) + vec4(0.8, 0.9, 0.7, 1.0) * pow(abs(vs_fs_normal.z), 40.0);
                }
                """;

        program = ProgramBuilder.newInstance()
                .attach(ShaderType.VERTEX_SHADER, vert)
                .attach(ShaderType.FRAGMENT_SHADER, frag)
                .build();
        program.use();


        byte[] data = null;
        try {
            data = Files.readAllBytes(Path.of("cube.glb"));
        }catch (Exception e) {
            e.printStackTrace();
        }

        var map = GLBLoader.getInstance().load(data);

        vao = map.values().iterator().next().getVao();

        vao.bind();

        for (int q = 0; q < INSTANCE_COUNT; q++) {
            weights[q] = new Vector4f();
        }

        for (int n = 0; n < INSTANCE_COUNT; n++) {
            float a = n / 4.f;
            float b = n / 5.f;
            float c = n / 6.f;
            weights[n].x = 0.5f * (float)Math.sin(a + 1.f) + 1f;
            weights[n].y = 0.5f * (float)Math.sin(b + 2.f) + 1f;
            weights[n].z = 0.5f * (float)Math.sin(c + 3.f) + 1f;
            weights[n].w = 1.f;
        }

        colorBuffer = BufferBuilder.newInstance().byteLength(INSTANCE_COUNT * 4 * 4).flag(BufferStorageType.DYNAMIC_STORAGE).build();
        double[] arr = Arrays.stream(weights).flatMapToDouble(v->Arrays.stream(new double[]{v.x,v.y,v.z,v.w})).toArray();
        float[] fArray = new float[arr.length];
        IntStream.range(0, arr.length).forEach(i->fArray[i] = (float)arr[i]);
        colorBuffer.setData(fArray);
        weightsBuffer = BufferBuilder.newInstance().byteLength(INSTANCE_COUNT * 4 * 4).flag(BufferStorageType.DYNAMIC_STORAGE).build();
        weightsBuffer.setData(fArray);

        vao.addInstanced(6, 1,
                new VertexArrayAccessor(weightsBuffer, 0, 0, 16,
                        DataType.FLOAT,  3,  false));
        vao.addInstanced(7, 1,
                new VertexArrayAccessor(colorBuffer, 0, 0, 16,
                        DataType.FLOAT,  3,  false));
    }

    static long appTime = 0;

    public static void loop() {
        appTime++;
        float t = appTime % 32000;
        float q = 0f;

        for (int n = 0; n < INSTANCE_COUNT; n++) {
            float a = n / 4.f;
            float b = n / 5.f;
            float c = n / 6.f;
            weights[n].x = 0.5f * (float)Math.sin(a + 1.f) + 1f;
            weights[n].y = 0.5f * (float)Math.sin(b + 2.f) + 1f;
            weights[n].z = 0.5f * (float)Math.sin(c + 3.f) + 1f;
            weights[n].w = 1.f;
        }


        double[] arr = Arrays.stream(weights).flatMapToDouble(v->Arrays.stream(new double[]{v.x,v.y,v.z,v.w})).toArray();
        float[] fArray = new float[arr.length];
        IntStream.range(0, arr.length).forEach(i->fArray[i] = (float)arr[i]);
        weightsBuffer.setData(fArray);


        program.use();

        var modelMatrix = new Matrix4f[4];

        for (int i = 0; i < 4; i++) {
            modelMatrix[i] = new Matrix4f()
                    .scale(5.f)
                    .rotate(t * 360 * 40 * (i + 1) * 29f, 0, 1, 0)
                    .rotate(t * 360 * 30 * (i+1)*35, 0, 0, 1)
                    .rotate(t * 350 * 30 + (i+1)*67, 0, 1, 0)
                    .translate(i * 10 -15, 0, 0)
                    .scale(0.01f);
        }

        modelMatrix[0] = new Matrix4f()
                .rotate(t * 0.01f,0,0,1)
                .translate(0, 80, -10);
        modelMatrix[1] = new Matrix4f()
                .rotate(t * 0.05f,0,1,0)
                .translate( -80, 80, -20);
        modelMatrix[2] = new Matrix4f()
                .rotate(t * 0.03f,0,1,0)
                .translate(80, -80, -9);
        modelMatrix[3] = new Matrix4f()
                .rotate(t * 0.02f,0,0,1)
                .translate(-80, 0, -10);

        program.setUniform("model_matrix", modelMatrix);

        var proj = new Matrix4f()
                .frustum(-1,1,-1,1,1,5000)
                .translate(0, 0, -30);
        program.setUniform("projection_matrix", proj);


        vao.drawElementsInstanced(36 , INSTANCE_COUNT );

    }


}
