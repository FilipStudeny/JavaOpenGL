package Engine;

import org.lwjgl.BufferUtils;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Texture {

    private int textureID;
    private String filePath;


    public Texture(String filePath){
        this.filePath = filePath;

        //GENERATE TEXTURE ON GPU
        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        //TEXTURE PARAMETERS
        //Texture repeating in both directions
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        //Streched textures -> pixelated
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        //Shrinked textures -> pixelated
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        //LOAD TEXTURE IMAGE
        IntBuffer textureWidth = BufferUtils.createIntBuffer(1);
        IntBuffer textureHeight = BufferUtils.createIntBuffer(1);
        IntBuffer colourChannels = BufferUtils.createIntBuffer(1);
        ByteBuffer image = stbi_load(filePath,textureWidth,textureHeight,colourChannels,0);

        //COLOURS
        if(image != null){
            if(colourChannels.get(0) == 3){
                glTexImage2D(GL_TEXTURE_2D,0,GL_RGB, textureWidth.get(0), textureHeight.get(0),
                        0, GL_RGB, GL_UNSIGNED_BYTE, image);
            }else if(colourChannels.get(0) == 4){
                glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA, textureWidth.get(0), textureHeight.get(0),
                        0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            }else{
                assert false: "Error: UNKNOWN NUMBER OF COLOUR CHANNELS IN A TEXTURE: " + colourChannels.get(0) + " !";
            }

        }else{
            assert false: "ERROR COULDN'T LOAD TEXTURE " + filePath + " !";
        }

        stbi_image_free(image); //WITHOUT THIS MEMORY LEAKS WOULD HAPPEN = BAD
    }

    public void BindTexture(){
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    public void UnbindTexture(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
