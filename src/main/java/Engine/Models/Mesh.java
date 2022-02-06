package Engine.Models;

import Engine.Material;
import Engine.Shader;
import Engine.Texture;
import Engine.Time;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.memAddress;
import static org.lwjgl.system.MemoryUtil.memFree;

public class Mesh {

    private int VAO_ID;
    private int EBO_ID;


    private float[] vertices;
    private int[] triangles;    //MUST BE IN COUNTERCLOCKWISE ORDER
    private float[] textureCoords;
    private float[] colours;
    private float[] normals;


    private Matrix4f projectionMatrix;
    private Matrix4f worldMatrix;
    private Matrix4f vieMatrix;

    private Shader shader;
    private Material material;

    public Mesh(){
        this.shader = new Shader("Assets/Shaders/default.glsl");
    }

    public void SetVertices(float[] vertices){
        this.vertices = vertices;
    }
    public void SetTriangles(int[] triangles){
        this.triangles = triangles;
    }
    public void SetTextureCoords(float[] textureCoords) { this.textureCoords = textureCoords; }
    public void SetColours(float[] colours) { this.colours = colours; }
    public void SetNormals(float[] normals) { this.normals = normals; }
    public void SetMaterial(Material material) { this.material = material; }
    public void Init(){

        shader.CompileShader();

        // ============================================================
        // Generate VAO, VBO, and EBO buffer objects, and send to GPU
        // ============================================================
        VAO_ID = glGenVertexArrays();
        glBindVertexArray(VAO_ID);

        //VBO GENERATION
        CreateVBO(0,3,vertices);
        CreateVBO(1,4,colours);
        CreateVBO(2,2,textureCoords);
        CreateVBO(3,2,normals);

        // Create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(triangles.length);
        elementBuffer.put(triangles).flip();

        EBO_ID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO_ID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        memFree(elementBuffer);
    }

    //CREATE VBO
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

        this.material.GetTexture().BindTexture();

        shader.SetMetrix("projectionMatrix",projectionMatrix);
        shader.SetMetrix("worldMatrix",worldMatrix);
        shader.UploadFloat("uTime", Time.GetTime());

        shader.UploadVector4Float("ambient", material.getAmbientColour() );
        shader.UploadVector4Float("specular", material.getSpecularColour());
        shader.UploadVector4Float("diffuse", material.getDiffuseColour());


        shader.UploadVector3Float("ambientLight", material.getAmbientLight());
        shader.UploadInt("hasTexture", material.ContainsTexture() ? 1 : 0);

        shader.UploadVector3Float("directionalLightColour", material.getDirectionalLight().getColour());
        shader.UploadVector3Float("directionalLightDirection", material.getDirectionalLight().getDirection());
        shader.UploadFloat("directionalLightIntensity", material.getDirectionalLight().getIntensity());

        shader.UploadFloat("reflectance", material.getReflectance());
        shader.UploadFloat("specularPower", material.getSpecularPower());

        shader.UploadVector3Float("pointLightColour", material.getPointLight().getColour());
        shader.UploadVector3Float("pointLightPosition", material.getPointLight().getPosition());
        shader.UploadFloat("pointLightIntensity", material.getPointLight().getIntensity());
        shader.UploadFloat("constant", material.getPointLight().getConstant());
        shader.UploadFloat("linear", material.getPointLight().getLinear());
        shader.UploadFloat("exponent", material.getPointLight().getExponent());

        shader.UploadVector3Float("coneDirection", material.getSpotlight().getConeDirection());
        shader.UploadFloat("cutOff", material.getSpotlight().getConeCutoff());


        //Bind VAO currently in use
        glBindVertexArray(VAO_ID);

        //Enable vertex atribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);


        //Draw triangles
        glDrawElements(GL_TRIANGLES,triangles.length, GL_UNSIGNED_INT,0);

        //Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);

        this.material.GetTexture().UnbindTexture();

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

    public float GetLightIntensitry(float lightIntentsity) { return lightIntentsity; }
    public Vector3f GetLightDirection(Vector3f lightDirection) { return lightDirection; }
    public Vector3f GetLightColour(Vector3f lightColour) { return lightColour; }
}