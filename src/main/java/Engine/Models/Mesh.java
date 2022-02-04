package Engine.Models;

import Engine.Shader;
import Engine.Texture;
import Engine.Time;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.memFree;

public class Mesh {

    private int VAO_ID;
    private int VBO_ID;
    private int EBO_ID;
    private int VBO_ID_normals;


    private float[] vertices;
    private int[] triangles;    //MUST BE IN COUNTERCLOCKWISE ORDER
    private float[] textureCoords;
    private float[] colours;


    private Matrix4f projectionMatrix;
    private Matrix4f worldMatrix;
    private Matrix4f vieMatrix;

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
    public void SetTextureCoords(float[] textureCoords) { this.textureCoords = textureCoords; }
    public void SetColours(float[] colours) { this.colours = colours; }

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

        //VBO GENERATION
        CreateVBO(0,3,vertices);
        CreateVBO(1,4,colours);
        CreateVBO(2,2,textureCoords);

        // Create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(triangles.length);
        elementBuffer.put(triangles).flip();

        EBO_ID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO_ID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        memFree(elementBuffer);
    }

    private void CreateVBO(int i, int size, float[] array){
        int ID = glGenBuffers();
        FloatBuffer buffer = MemoryUtil.memAllocFloat(array.length);
        buffer.put(array).flip();
        glBindBuffer(GL_ARRAY_BUFFER, ID);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glEnableVertexAttribArray(i);
        glVertexAttribPointer(i, size, GL_FLOAT, false, 0, 0);
        memFree(buffer);
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

    public void SetViewMatrix(Matrix4f vieMatrix){
        this.vieMatrix = vieMatrix;
    }

}