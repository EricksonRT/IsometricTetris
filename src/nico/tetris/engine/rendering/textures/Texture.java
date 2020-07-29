package nico.tetris.engine.rendering.textures;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Texture {

    private int id;

    public Texture(File file) {
        try {
            this.id = this.loadTexture(file);
        } catch(IOException e) {
            System.err.println("Couldn't load texture " + file.getName());
        }
    }

    public void bind() {
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

    protected void delete() {
        GL15.glDeleteTextures(id);
    }

    private int loadTexture(File file) throws IOException {
        int textureId;
        PNGDecoder decoder = new PNGDecoder(new FileInputStream(file)); //load png file
        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight()); //create a byte buffer big enough to store RGBA values

        decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA); //decode
        buffer.flip(); //flip the buffer so its ready to read

        textureId = GL11.glGenTextures(); //create a texture
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId); //bind the texture
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1); //tell openGL how to unpack bytes

        //set the texture parameters, can be GL_LINEAR or GL_NEAREST
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        //upload texture
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

        //this.width = decoder.getWidth();
        //this.height = decoder.getHeight();
        return textureId;
    }
}
