package Engine.Models;


import Engine.Shader;
import Engine.Texture;
import Engine.Time;
import Engine.WorldTransformation;
import org.joml.Math;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.stbi_load;
import static org.lwjgl.system.MemoryUtil.memFree;

public class Mesh {

    private int VAO_ID;
    private int VBO_ID;
    private int EBO_ID;


    private float[] vertices;
    private int[] triangles;    //MUST BE IN COUNTERCLOCKWISE ORDER


    private Matrix4f projectionMatrix;
    private Matrix4f worldMatrix;

    private Shader shader;
    private Texture texture;

    public Mesh(){
        this.shader = new Shader("Assets/Shaders/default.glsl");
        this.texture = new Texture("src/textures/dorime.png");
    }


    public void SetVertices(float[] vertices){
        this.vertices = vertices;
    }
    public void SetTriangles(int[] triangles){
        this.triangles = triangles;
    }

    public void Init(){

        shader.CompileShader();

        //************
        // Generate VAO, VBO and EBO and send them to GPU
        //************

        // ============================================================
        // Generate VAO, VBO, and EBO buffer objects, and send to GPU
        // ============================================================
        VAO_ID = glGenVertexArrays();
        glBindVertexArray(VAO_ID);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();

        // Create VBO upload the vertex buffer
        VBO_ID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO_ID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(triangles.length);
        elementBuffer.put(triangles).flip();

        EBO_ID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO_ID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Add the vertex attribute pointers
        int positionsSize = 3;
        int colorSize = 4;
        int UVsize = 2;
        int vertexSizeBytes = (positionsSize + colorSize + UVsize) * Float.BYTES;
        glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, UVsize, GL_FLOAT, false, vertexSizeBytes, (positionsSize + colorSize) * Float.BYTES);
        glEnableVertexAttribArray(2);

        memFree(vertexBuffer);
        memFree(elementBuffer);


    }

    public void Render(){

        shader.UseShader();

        //SHADER UPLOADS
        shader.UploadTexture("textureSampler",0); //UPLOAD TEXTURE TO SHADER
        glActiveTexture(GL_TEXTURE0); //
        texture.BindTexture(); //BIND TEXTURE

        shader.SetMetrix("projectionMatrix",projectionMatrix);
        shader.SetMetrix("worldMatrix",worldMatrix);
        shader.UploadFloat("uTime", Time.GetTime());
        //Bind VAO currently in use
        glBindVertexArray(VAO_ID);

        //Enable vertex atribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        //Draw triangles
        glDrawElements(GL_TRIANGLES,triangles.length, GL_UNSIGNED_INT,0);

        //Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        shader.DetachShader();

    }

    public void SetWorldMetrix(Matrix4f worldMatrix){
        this.worldMatrix = worldMatrix;
    }

    public void SetProjectionMatrix(Matrix4f projectionMatrix){
        this.projectionMatrix = projectionMatrix;
    }

}