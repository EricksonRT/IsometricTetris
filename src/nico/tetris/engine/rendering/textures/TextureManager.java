package nico.tetris.engine.rendering.textures;

import java.io.File;
import java.util.HashMap;

public class TextureManager {

    private static final HashMap<String, Texture> TEXTURES = new HashMap<>();

    static {
        System.out.println("Loading textures");
        File folder = new File("res/textures");
        for(File file : folder.listFiles()) {
            TEXTURES.put(file.getName(), new Texture(file));
        }
    }

    public static Texture get(String name) {
        return TEXTURES.get(name + ".png");
    }

    public static void deleteTextures() {
        System.out.println("Deleting textures");
        for(Texture texture : TEXTURES.values()) {
            texture.delete();
        }
    }
}
