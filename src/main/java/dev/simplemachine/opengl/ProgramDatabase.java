package dev.simplemachine.opengl;

import dev.simplemachine.opengl.glenum.ShaderType;
import dev.simplemachine.opengl.objects.OglProgram;
import dev.simplemachine.opengl.objects.ProgramBuilder;
import org.joml.Vector3f;

public class ProgramDatabase {

    private static OglProgram instancedStaticMeshProgram;

    private static OglProgram staticMeshProgram;

    private static OglProgram lineRenderer;

    public static OglProgram getOrCreateLineRenderer() {
        if (lineRenderer == null) {
            var builder = ProgramBuilder.newInstance();
            builder.attach(ShaderType.VERTEX_SHADER, """
                    #version 450 core
                                    
                    layout (std140, binding = 10) uniform Globals {
                        mat4 proj;
                        mat4 view;
                        vec3 eyePos;
                    } globals;
                    
                    layout (location = 0) in vec3 position;
                    """);
            builder.attach(ShaderType.FRAGMENT_SHADER, """
                    #version 450 core
                    
                    layout (location = 0) out vec4 color;
                    
                    """);
            lineRenderer = builder.build();
            staticMeshProgram.setUniformBlockBinding("Globals", 10);
        }
        return lineRenderer;
    }

    public static OglProgram getOrCreateStaticMeshProgram() {
        if (staticMeshProgram == null) {
            var builder = ProgramBuilder.newInstance();
            builder.attach(ShaderType.VERTEX_SHADER, """
                    #version 450 core
                                    
                    layout (std140, binding = 10) uniform Globals {
                        mat4 proj;
                        mat4 view;
                        vec3 eyePos;
                    } globals;
                                    
                    layout (location = 0) in vec3 position;
                    layout (location = 1) in vec3 normal;
                    layout (location = 3) in vec4 color;
                                    
                    uniform mat4 model;
                    uniform mat3 modelTI;
                                    
                    out vec4 vs_fs_pos;
                    out vec3 vs_fs_normal;
                    out vec4 vs_fs_color;
                                    
                    void main() {
                        vec4 pos = vec4(position, 1);
                        vs_fs_pos = model * pos;
                        vs_fs_color = color;
                        vs_fs_normal = modelTI * normal;
                        gl_Position = globals.proj * globals.view * vs_fs_pos;
                    }
                    """);
            builder.attach(ShaderType.FRAGMENT_SHADER, """
                    #version 450 core
                                    
                    layout (location = 0) out vec4 color;
                                    
                    uniform vec3 lightDir;
                                    
                    in vec3 vs_fs_normal;
                    in vec4 vs_fs_color;
                    in vec4 vs_fs_pos;
                                    
                    void main() {
                        vec3 N = normalize(vs_fs_normal);
                        vec3 L = lightDir;
                        //vec3 E = normalize(-fragmentPosition);
                        vec3 R = reflect(-L, N);
                        
                        float diffuseTerm = max(dot(N, L), 0.0);
                        //float specularTerm = pow(max(dot(R, E), 0.0), shininess);
                        
                        vec3 diffuse = diffuseTerm * vs_fs_color.xyz;
                        vec3 ambient = vec3(0.0,0.0,0.0);
                        //vec3 specular = specularTerm * specularColor;
                        
                        //vec4 texel = texture(diffuseTex, fragmentTexCoord);
                        color = vec4((ambient + diffuse), 1);
                    }
                    """);

            staticMeshProgram = builder.build();
            // not necessary as it's part of the glsl shader, but for future reference good to know
            staticMeshProgram.setUniformBlockBinding("Globals", 10);
        }
        return staticMeshProgram;
    }

    public static OglProgram createInstancedStaticMeshProgram() {
        if (instancedStaticMeshProgram == null) {
            var builder = ProgramBuilder.newInstance();
            builder.attach(ShaderType.VERTEX_SHADER, """
                    #version 450 core
                                    
                    layout (std140, binding = 4) uniform Globals {
                        mat4 proj;
                        mat4 view;
                        vec3 eyePos;
                    } globals;
                                    
                    layout (location = 0) in vec3 position;
                    layout (location = 1) in vec3 normal;
                    layout (location = 3) in vec3 color;
                                    
                    layout (location = 6) in mat4 model;
                                    
                    out vec4 vs_fs_pos;
                    out vec3 vs_fs_normal;
                    out vec3 vs_fs_color;
                                    
                    void main() {
                        vec4 pos = vec4(position, 1);
                        vs_fs_pos = model * pos;
                        vs_fs_normal = mat3(transpose(inverse(model))) * normal;
                        vs_fs_color = color;
                        gl_Position = global.proj * global.view * model * pos;
                    }
                    """);
            builder.attach(ShaderType.FRAGMENT_SHADER, """
                    #version 450 core
                                    
                    layout (location = 0) out vec4 color;
                                    
                    uniform vec3 lightDir;
                                    
                    in vec3 vs_fs_normal;
                    in vec4 vs_fs_color;
                    in vec4 vs_fs_pos;
                                    
                    void main() {
                        vec3 N = normalize(vs_fs_normal);
                        vec3 L = lightDir;
                        //vec3 E = normalize(-fragmentPosition);
                        vec3 R = reflect(-L, N);
                        
                        float diffuseTerm = max(dot(N, L), 0.0);
                        //float specularTerm = pow(max(dot(R, E), 0.0), shininess);
                        
                        vec3 diffuse = diffuseTerm * vs_fs_color;
                        vec3 ambient = vec3(0.1,0.1,0.1);
                        //vec3 specular = specularTerm * specularColor;
                        
                        //vec4 texel = texture(diffuseTex, fragmentTexCoord);
                        color = vec4((ambient + diffuse), 1);
                    }
                    """);

            instancedStaticMeshProgram = builder.build();
        }
        return instancedStaticMeshProgram;
    }


}
