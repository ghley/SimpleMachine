package dev.simplemachine.opengl.examples;

import dev.simplemachine.SimpleMachine;
import dev.simplemachine.opengl.glenum.*;
import dev.simplemachine.opengl.objects.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL32.GL_PROGRAM_POINT_SIZE;

public class Example007PointSprites {

    static SimpleMachine machine;

    public static void main(String[] args) {
        machine = new SimpleMachine();
        machine.setInitCallback(Example007PointSprites::init);
        machine.setLoopCallback(Example007PointSprites::loop);
        machine.run();
    }

    static int POINT_COUNT = 4;

    static OglProgram program;
    static OglVertexArray vao;
    static OglTexture texture;

    public static void init() {
        var vs = """
                #version 450
                                
                uniform mat4 model_matrix;
                uniform mat4 projection_matrix;
                                
                layout (location = 0) in vec4 position;
                                
                void main(void)
                {
                    vec4 pos = projection_matrix * (model_matrix * position);
                    gl_PointSize = (1.0 - pos.z / pos.w) * 64.0;
                    gl_Position = pos;
                }""";

        var fs = """
                #version 450
                                
                uniform sampler2D sprite_texture;
                                
                out vec4 color;
                                
                void main(void)
                {
                    color = texture(sprite_texture, vec2(gl_PointCoord.x, 1.0 - gl_PointCoord.y));
                    color = vec4(color.r,1,1,1);
                }""";


        BufferedImage bi = null;
        try {
            bi = ImageIO.read(new File("snowflake.png"));
        }catch (Exception e) {
            e.printStackTrace();
        }

        texture = new OglTexture(TextureType.TEXTURE_2D);
        texture.load(bi);

        program = ProgramBuilder.newInstance().attach(ShaderType.VERTEX_SHADER, vs)
                .attach(ShaderType.FRAGMENT_SHADER, fs)
                .build();

        Vector4f[] positions = new Vector4f[POINT_COUNT];
        for (int q = 0; q < POINT_COUNT; q++) {
            positions[q] = new Vector4f((float)Math.random() * 2.f - 1.f,(float)Math.random() * 2.f - 1.f,
                    (float)Math.random() * 2.f - 1.f, 1.f);
        }

        var fArray = new float[positions.length*4];
        var dArray = Arrays.stream(positions).flatMapToDouble(v->Arrays.stream(new double[]{})).toArray();
        IntStream.range(0, dArray.length).forEach(i->fArray[i] = (float)dArray[i]);

        var buffer = BufferBuilder.newInstance().flag(BufferStorageType.DYNAMIC_STORAGE)
                        .byteLength(4 * fArray.length)
                        .build();
        buffer.setData(fArray);

        vao = VertexArrayBuilder.newInstance()
                .primitiveMode(PrimitiveType.POINTS)
                .addAccessor(0, new VertexArrayAccessor(
                    buffer, 0, 0, 4*4, DataType.FLOAT, 4, false)
                ).build();

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }


    public static void loop() {
        program.use();

        var proj = new Matrix4f()
                .frustum(-1,1,-1,1,1.0f,8.f);
        program.setUniform("projection_matrix", proj);

        texture.bind();

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
        GL11.glEnable(GL_PROGRAM_POINT_SIZE);
        glPointSize(32f);

        var model = new Matrix4f()
                .translate(0, 0, -2)
                .rotate(350f, new Vector3f(0, 1,0))
                .rotate(720, new Vector3f(0, 0, 1));
        program.setUniform("model_matrix", model);

        vao.drawArrays(POINT_COUNT);

    }
}
