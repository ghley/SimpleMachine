package dev.simplemachine.opengl;

import dev.simplemachine.model.StaticMesh;
import dev.simplemachine.opengl.gltf.GLBLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StaticMeshDatabase {

    private static final Set<String> packsLoaded = new HashSet<>();
    private static final Map<String, StaticMesh> meshes = new HashMap<>();

    public static void load(String pack) {
        if (packsLoaded.contains(pack)) {
            return;
        }
        byte[] data = null;
        try {
            data = Files.readAllBytes(Path.of(pack));
        } catch (Exception e) {
            e.printStackTrace();
        }
        var meshes = GLBLoader.getInstance().load(data);
        meshes.entrySet().forEach(e->{
            StaticMeshDatabase.meshes.put(pack+"/"+e.getKey(), e.getValue());
            System.out.println("Loaded "+e.getKey());
        });
        packsLoaded.add(pack);
    }

    public static StaticMesh get(String pack, String name) {
        if (!packsLoaded.contains(pack)) {
            load(pack);
        }
        return  meshes.get(pack+"/"+name);
    }

    public static void clear() {
        packsLoaded.clear();
        meshes.clear();
    }

}
