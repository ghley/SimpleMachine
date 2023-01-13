package dev.simplemachine.opengl.gltf;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.simplemachine.model.StaticMesh;
import dev.simplemachine.opengl.glenum.BufferStorageType;
import dev.simplemachine.opengl.glenum.DataType;
import dev.simplemachine.opengl.glenum.PrimitiveType;
import dev.simplemachine.opengl.glenum.TextureType;
import dev.simplemachine.opengl.objects.*;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.*;
import java.util.logging.Logger;

public final class GLBLoader {

    private GLBLoader() {

    }

    private Gltf gltf = null;
    private byte[] glbPayload = null;

    Map<String, Integer> bindingMap = Map.of(
            "POSITION", 0,
            "NORMAL", 1,
            "TEXCOORD_0", 2,
            "COLOR_0", 3
    );

    Map<Integer, PrimitiveType> primitiveMode = Map.of(
            0, PrimitiveType.POINTS,
            1, PrimitiveType.LINES,
            2, PrimitiveType.LINE_LOOP,
            3, PrimitiveType.LINE_STRIP,
            4, PrimitiveType.TRIANGLES,
            5, PrimitiveType.TRIANGLE_STRIP,
            6, PrimitiveType.TRIANGLE_FAN
    );

    Map<Integer, DataType> componentTypeMap = Map.of(
            5120, DataType.BYTE,
            5121, DataType.U_BYTE,
            5122, DataType.SHORT,
            5123, DataType.U_SHORT,
            5125, DataType.U_INT,
            5126, DataType.FLOAT
    );

    Map<String, Integer> typeSize = Map.of(
            "SCALAR", 1,
            "VEC2", 2,
            "VEC3", 3,
            "VEC4", 4,
            "MAT2", 4,
            "MAT3", 9,
            "MAT4", 16
    );


    public static GLBLoader getInstance() {
        return new GLBLoader();
    }

    public void load(InputStream stream) {

    }

    public void load(File file) {

    }

    public void load(String file) {

    }

    public Map<String, StaticMesh> load(byte[] glbByteArray) {
        var byteBuffer = ByteBuffer.wrap(glbByteArray).order(ByteOrder.LITTLE_ENDIAN);
        int magic = byteBuffer.getInt();
        if (magic != 0x46546C67) {
            Logger.getAnonymousLogger().info(String.format("Magic Number is not: 0x46546C67 but 0x%X", magic));
            return null;
        }
        int version = byteBuffer.getInt();
        if (version != 2) {
            Logger.getAnonymousLogger().info(String.format("Can only read Version 2.0 but this file is %d", version));
            return null;
        }
        int length = byteBuffer.getInt();
        if (glbByteArray.length != length) {
            Logger.getAnonymousLogger().info(String.format("File length is %d but header claims %d", glbByteArray.length, length));
            return null;
        }

        record Chunk(int length, int type, byte[] data) {
        }

        List<Chunk> chunks = new ArrayList<>();

        while (byteBuffer.remaining() > 0) {
            int chunkLength = byteBuffer.getInt();
            int chunkType = byteBuffer.getInt();
            byte[] data = new byte[chunkLength];
            byteBuffer.get(data);
            chunks.add(new Chunk(chunkLength, chunkType, data));
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setVisibility(
                    mapper.getSerializationConfig()
                            .getDefaultVisibilityChecker()
                            .withFieldVisibility(JsonAutoDetect.Visibility.ANY));

            gltf = mapper.readValue(chunks.get(0).data, Gltf.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (chunks.size() > 1) {
            glbPayload = chunks.get(1).data;
            invertEndian(glbPayload);
        }

        Map<String, StaticMesh> vaoMap = new HashMap<>();
        Map<Integer, OglBuffer> vboMap = new HashMap<>();

        for (int q = 0; q < gltf.meshes.length; q++) {

            var mesh = gltf.meshes[q];
            for (int r = 0; r < mesh.primitives.length; r++) {
                var staticMesh = new StaticMesh();
                VertexArrayBuilder vaoBuilder = VertexArrayBuilder.newInstance();
                var primitive = mesh.primitives[r];
                vaoBuilder.primitiveMode(primitiveMode.get(primitive.mode == null ? 4 : primitive.mode));
                var mode = primitive.mode == null ? 4 : primitive.mode;

                if (primitive.material != null) {
                    var material = gltf.materials[primitive.material];
                    if (material.pbrMetallicRoughness != null) {
                        if (material.pbrMetallicRoughness.baseColorTexture != null) {
                            var baseColorFactor = material.pbrMetallicRoughness.baseColorTexture.index;
                            var baseTexture = gltf.textures[baseColorFactor];
                            var source = gltf.images[baseTexture.source];
                            var bufferView = gltf.bufferViews[source.bufferView];
                            // ridiculous, I have no idea why the whole endian stuff doesn't work as its supposed to
                            invertEndian(glbPayload);
                            var array = Arrays.copyOfRange(glbPayload,
                                    bufferView.byteOffset,
                                    bufferView.byteOffset + bufferView.byteLength);
                            invertEndian(glbPayload);
                            try {
                                var stream = new ByteArrayInputStream(array);
                                BufferedImage image = ImageIO.read(stream);
                                // load texture into opengl and put a reference somewhere, need to figure this out
                                OglTexture tex = new OglTexture(TextureType.TEXTURE_2D);
                                tex.load(image);
                                staticMesh.getTextures().put(0, tex);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }

                for (var entry : primitive.attributes.entrySet()) {
                    var gltfAccessor = gltf.accessors[entry.getValue()];
                    var gltfBufferView = gltf.bufferViews[gltfAccessor.bufferView];
                    extract(vboMap, gltfAccessor);
                    var oglBuffer = vboMap.get(gltfAccessor.bufferView);

                    int bindingIndex = bindingMap.get(entry.getKey());

                    int stride = gltfBufferView.byteStride == null ?
                            (componentTypeMap.get(gltfAccessor.componentType).bitSize / 8 * typeSize.get(gltfAccessor.type))
                            : gltfBufferView.byteStride;

                    // we have to pad to match buffer layout
                    if (stride % 16 != 0 && stride % 8 != 0) {
                        stride += 16 - (stride % 16);
                    }
                    var accessor = new VertexArrayAccessor(
                            oglBuffer,
                            0,
                            gltfAccessor.byteOffset == null ? 0 : gltfAccessor.byteOffset,
                            stride,
                            componentTypeMap.get(gltfAccessor.componentType),
                            typeSize.get(gltfAccessor.type),
                            gltfAccessor.normalized == null ? false : gltfAccessor.normalized
                    );
                    System.out.println(accessor);
                    vaoBuilder.addAccessor(bindingIndex, accessor);
                }
                if (primitive.indices != null) {
                    var gltfAccessor = gltf.accessors[primitive.indices];
                    var gltfBufferView = gltf.bufferViews[gltfAccessor.bufferView];
                    extract(vboMap, gltfAccessor);
                    var oglBuffer = vboMap.get(primitive.indices);

                    int stride = gltfBufferView.byteStride == null ?
                            (componentTypeMap.get(gltfAccessor.componentType).bitSize / 8 * typeSize.get(gltfAccessor.type))
                            : gltfBufferView.byteStride;


                    vaoBuilder.addElementBuffer(new VertexArrayAccessor(
                            oglBuffer,
                            0,
                            gltfAccessor.byteOffset == null ? 0 : gltfAccessor.byteOffset,
                            stride,
                            componentTypeMap.get(gltfAccessor.componentType),
                            typeSize.get(gltfAccessor.type),
                            gltfAccessor.normalized == null ? false : gltfAccessor.normalized
                    ));
                }
                staticMesh.setVao(vaoBuilder.build());
                vaoMap.put(mesh.name, staticMesh);
            }
        }
        return vaoMap;
    }

    private void invertEndian(byte[] array) {
        for (int q = 0; q < array.length; q += 4) {
            byte tmp = array[q];
            array[q] = array[q + 3];
            array[q + 3] = tmp;
            tmp = array[q + 1];
            array[q + 1] = array[q + 2];
            array[q + 2] = tmp;
        }
    }

    /**
     * Buffers with a stride unequal 16 bytes will be padded!
     *
     * @param vboMap
     * @param gltfAccessor
     */
    private void extract(Map<Integer, OglBuffer> vboMap, GltfAccessor gltfAccessor) {
        System.out.println(gltfAccessor);
        var gltfBufferView = gltf.bufferViews[gltfAccessor.bufferView];
        if (!vboMap.containsKey(gltfAccessor.bufferView)) {
            var array = Arrays.copyOfRange(glbPayload,
                    gltfBufferView.byteOffset,
                    gltfBufferView.byteOffset + gltfBufferView.byteLength);

            int[] intArray = new int[array.length / 4];
            ByteBuffer.wrap(array).asIntBuffer().get(intArray);
            int size = typeSize.get(gltfAccessor.type) * componentTypeMap.get(gltfAccessor.componentType).bitSize / 8;
            if (size == 12) { // TODO only pad vec3, maybe create interleaved objects?
                int newSize = size + (16 - size % 16);
                var temp = intArray;
                intArray = new int[newSize / 4 * gltfAccessor.count];
                for (int q = 0; q < gltfAccessor.count; q++) {
                    System.arraycopy(temp, q * size / 4, intArray, q * newSize / 4, size / 4);
                }
            }
            var buffer = BufferBuilder.newInstance().flag(BufferStorageType.DYNAMIC_STORAGE)
                    .byteLength(intArray.length * 4).build();
            buffer.setData(intArray);
            vboMap.put(gltfAccessor.bufferView, buffer);
        }
    }

}
