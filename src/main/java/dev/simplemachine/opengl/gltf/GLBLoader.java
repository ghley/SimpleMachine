package dev.simplemachine.opengl.gltf;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.simplemachine.opengl.glenum.DataType;
import dev.simplemachine.opengl.glenum.PrimitiveType;
import dev.simplemachine.opengl.objects.BufferBuilder;
import dev.simplemachine.opengl.objects.GLTFBuilder;
import dev.simplemachine.opengl.objects.OglBuffer;

import java.io.*;
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
            "TEXCOORD_0", 2
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

    Map<Integer, DataType> dataTypeMap = Map.of(
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
//
//    public OglGLTF load(byte[] glbByteArray) {
//        var byteBuffer = ByteBuffer.wrap(glbByteArray).order(ByteOrder.LITTLE_ENDIAN);
//        int magic = byteBuffer.getInt();
//        if (magic != 0x46546C67) {
//            Logger.getAnonymousLogger().info(String.format("Magic Number is not: 0x46546C67 but 0x%X",magic));
//            return null;
//        }
//        int version = byteBuffer.getInt();
//        if (version != 2) {
//            Logger.getAnonymousLogger().info(String.format("Can only read Version 2.0 but this file is %d", version));
//            return null;
//        }
//        int length = byteBuffer.getInt();
//        if (glbByteArray.length != length) {
//            Logger.getAnonymousLogger().info(String.format("File length is %d but header claims %d", glbByteArray.length, length));
//            return null;
//        }
//
//        record Chunk(int length, int type, byte[] data) {
//        }
//
//        List<Chunk> chunks = new ArrayList<>();
//
//        while (byteBuffer.remaining() > 0) {
//            int chunkLength = byteBuffer.getInt();
//            int chunkType = byteBuffer.getInt();
//            byte[] data = new byte[chunkLength];
//            byteBuffer.get(data);
//            chunks.add(new Chunk(chunkLength, chunkType, data));
//        }
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//            mapper.setVisibility(
//                    mapper.getSerializationConfig()
//                            .getDefaultVisibilityChecker()
//                            .withFieldVisibility(JsonAutoDetect.Visibility.ANY));
//
//            gltf = mapper.readValue(chunks.get(0).data, Gltf.class);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        if (chunks.size() > 1) {
//            glbPayload = chunks.get(1).data;
//        }
//
//
//        GLTFBuilder builder = GLTFBuilder.newInstance();
//
//        for (int q = 0; q < gltf.bufferViews.length; q++) {
//            var bufferView = gltf.bufferViews[q];
//            var buffer = gltf.buffers[bufferView.buffer];
//            var array = buffer.uri == null ? glbPayload : null; // TODO load external data
//            System.out.println(bufferView.byteOffset + "  "+bufferView.byteLength);
//            array = Arrays.copyOfRange(array, bufferView.byteOffset,
//                    bufferView.byteOffset + bufferView.byteLength);
//            builder.addBuffer(q, array);
//        }
//
//        for (int q = 0; q < gltf.meshes.length; q++) {
//            var mesh = gltf.meshes[q];
//            for (int r = 0; r < mesh.primitives.length; r++) {
//                var primitive = mesh.primitives[r];
//                var mode = primitive.mode == null ? 4 : primitive.mode;
//
//                for (var entry : primitive.attributes.entrySet()) {
//                    var type = entry.getKey();
//                    var index = entry.getValue();
//                    var accessor = gltf.accessors[index];
//                    var offset = accessor.byteOffset == null ? 0 : accessor.byteOffset;
//                    var dataType = accessor.type;
//                    var compType =  accessor.componentType;
//                    var view = accessor.bufferView;
//                    var count = accessor.count;
//                    var normalized = accessor.normalized;
//                    var bufferView = gltf.bufferViews[accessor.bufferView];
//                    var stride = bufferView.byteStride == null ? typeSize.get(dataType) * 4 : bufferView.byteStride;
//                    builder.bind(bindingMap.get(type), view, offset, stride, typeSize.get(dataType), dataTypeMap.get(compType).constant);
//                }
//
//                var indices = primitive.indices;
//                if (indices != null) {
//                    var accessor = gltf.accessors[indices];
//                    var view = accessor.bufferView;
//                    builder.bindElementBuffer(view);
//                }
//            }
//        }
//        return builder.build();
//    }
//
//    public OglBuffer getData(GltfAccessor accessor) {
//        var bufferView = gltf.bufferViews[accessor.bufferView];
//        var buffer = gltf.buffers[bufferView.buffer];
//        var array = buffer.uri == null ? glbPayload : null; // TODO load external data
//        array = Arrays.copyOfRange(array, bufferView.byteOffset, bufferView.byteLength);
//        array = Arrays.copyOfRange(array, accessor.byteOffset, array.length - accessor.byteOffset);
//
//        var dataType = dataTypeMap.get(accessor.componentType);
//        var size = typeSize.get(accessor.type);
//
//        var builder = BufferBuilder.newInstance();
//
//        builder.dataType(dataTypeMap.get(dataType));
//        builder.numberOfEntries(typeSize.get(size));
//
//
//
//
//
//
//
//        var data = ByteBuffer.wrap(array);
//
//
//        return null;
//    }

}
