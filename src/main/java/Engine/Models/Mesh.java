package Engine.Models;


import Engine.WorldTransformation;
import org.joml.Math;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.memFree;

public class Mesh {

    private String vertexShaderSrc = "" +
            "#version 330 core\n" +
            "\n" +
            "layout (location=0) in vec3 aPos;\n" +
            "layout (location=1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "uniform mat4 projectionMatrix;\n" +
            "\n" +
            "void main(){\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = projectionMatrix * vec4(aPos, 1.0);\n" +
            "}";

    private String fragmentShaderSrc = "" +
            "#version 330 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 Color;\n" +
            "\n" +
            "void main(){\n" +
            "    Color = fColor;\n" +
            "}";

    private int vertexID;
    private int fragmentID;
    private int shaderProgram;

    private int VAO_ID;
    private int VBO_ID;
    private int EBO_ID;

    private float[] vertices;

    //MUST BE IN COUNTERCLOCKWISE ORDER
    private int[] triangles;

    //CAMERA
    private float FOV = (float) Math.toRadians(60f);
    private float nearPlane = 0.01f;
    private float farPlane = 1000.0f;
    private Matrix4f projectionMatrix;
    private Map<String, Integer> uniforms = new HashMap<>();
    int width = 800, height = 600;

    private WorldTransformation transformation;


    private Mesh(){
        this.transformation = new WorldTransformation();
    }

    public void SetVertices(float[] vertices){
        this.vertices = vertices;
    }
    public void SetTriangles(int[] triangles){
        this.triangles = triangles;
    }

    public void Init(){

        float aspectRation = (float) width / height;
        projectionMatrix = new Matrix4f().perspective(FOV, aspectRation, nearPlane, farPlane);

        //COMPILE AND LINK SHADERS
        //************
        // Vertex Shader
        //************

        vertexID = glCreateShader(GL_VERTEX_SHADER); //Load shader type
        glShaderSource(vertexID, vertexShaderSrc); // Pass shader source to GPU
        glCompileShader(vertexID); // Compile shader

        //Error check in compilation process
        int success = glGetShaderi(vertexID,GL_COMPILE_STATUS);
        if(success == GL_FALSE){
            int lenght = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl: \n\t Vertex shader compilation failed !");
            System.out.println(glGetShaderInfoLog(vertexID,lenght));
        }

        //************
        // Fragment Shader
        //************

        fragmentID = glCreateShader(GL_FRAGMENT_SHADER); //Load shader type
        glShaderSource(fragmentID, fragmentShaderSrc); // Pass shader source to GPU
        glCompileShader(fragmentID); // Compile shader

        //Error check in compilation process
        success = glGetShaderi(fragmentID,GL_COMPILE_STATUS);
        if(success == GL_FALSE){
            int lenght = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl: \n\t Vertex shader compilation failed !");
            System.out.println(glGetShaderInfoLog(fragmentID,lenght));
        }


        //************
        // Link shaders and Check for errors
        //************

        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexID);
        glAttachShader(shaderProgram, fragmentID);
        glLinkProgram(shaderProgram);

        //Check for linking errors
        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);
        if(success == GL_FALSE){
            int lenght = glGetProgrami(shaderProgram, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: 'defaultShader.glsl:' \n\t Linking of shaders failed !");
            System.out.println(glGetProgramInfoLog(fragmentID,lenght));
            assert false : "";
        }

        int uniformLocation = glGetUniformLocation(shaderProgram,"projectionMatrix");
        uniforms.put("projectionMatrix",uniformLocation);

        //************
        // Generate VAO, VBO and EBO and send them to GPU
        //************

        // GENERATE VAO
        VAO_ID = glGenVertexArrays();
        glBindVertexArray(VAO_ID);

        //Create float buffer of vertices
        FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(vertices.length);
        vertexBuffer.put(vertices).flip();

        // GENERATE VBO and upload VertexBuffer
        VBO_ID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, VBO_ID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
        memFree(vertexBuffer);

        //Create Indices and upload them
        IntBuffer elementBuffer = MemoryUtil.memAllocInt(triangles.length);
        elementBuffer.put(triangles).flip();

        //Create EBO
        EBO_ID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO_ID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        //Create vertex attribute pointers
        int positionSize = 3;
        int colorSize = 4;
        int floatSizeInBytes = 4;
        int vertexSizeInBytes = (positionSize + colorSize) * floatSizeInBytes;

        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeInBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeInBytes, positionSize * floatSizeInBytes);
        glEnableVertexAttribArray(1);

        // glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

    }

    public void Render(){

        //Bind shader program
        glUseProgram(shaderProgram);
        SetUniform("projectionMatrix", projectionMatrix);


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
        glUseProgram(0);

    }

    void SetUniform(String uniformName, Matrix4f value){
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            value.get(fb);
            glUniformMatrix4fv(uniforms.get(uniformName), false, fb);
        }
    }


}