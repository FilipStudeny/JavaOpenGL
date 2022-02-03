package Engine;

import org.joml.*;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;

public class Shader {

    private int shaderProgramID;
    private String filePath;

    private String vertexShaderSRC;
    private String fragmenrShaderSRC;

    private boolean isUsed = false;


    //CAMERA STUFF
    private Map<String, Integer> uniforms = new HashMap<>();
    private Map<String, Integer> uniformsPosition = new HashMap<>();

    public Shader(String filePath){
        this.filePath = filePath;

        //*************************
        // NO FUCKIN IDEA HOW THIS WORKS, BUT IT LOADS SHADERS AND MAKES THEM GO VRUUUUUUUUM
        //*************************

        try{
            String source = new String(Files.readAllBytes(Paths.get(filePath)));

            String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");

            int index = source.indexOf("#type") + 6;
            int endOfTheLine = source.indexOf("\r\n", index);
            String firstPattern = source.substring(index, endOfTheLine).trim();

            index = source.indexOf("#type",endOfTheLine) + 6;
            endOfTheLine = source.indexOf("\r\n", index);
            String secondPattern = source.substring(index,endOfTheLine).trim();

            if(firstPattern.equals("vertex")){
                vertexShaderSRC = splitString[1];
            }else if(firstPattern.equals("fragment")){
                fragmenrShaderSRC = splitString[1];
            }else {
                throw new IOException("Unexpected token: " + firstPattern + " ! ");
            }

            if(secondPattern.equals("vertex")){
                vertexShaderSRC = splitString[2];
            }else if(secondPattern.equals("fragment")){
                fragmenrShaderSRC = splitString[2];
            }else {
                throw new IOException("Unexpected token: " + secondPattern + " ! ");
            }

        }catch (IOException e){
            e.printStackTrace();
            assert false: "ERROR: COULDN'T OPEN SHADER FILE " + filePath +" !";
        }
    }

    public void CompileShader(){
        int vertexID;
        int fragmentID;

        //COMPILE AND LINK SHADERS
        //************
        // Vertex Shader
        //************

        vertexID = glCreateShader(GL_VERTEX_SHADER); //Load shader type
        glShaderSource(vertexID, vertexShaderSRC); // Pass shader source to GPU
        glCompileShader(vertexID); // Compile shader

        //Error check in compilation process
        int success = glGetShaderi(vertexID,GL_COMPILE_STATUS);
        if(success == GL_FALSE){
            int lenght = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: " + filePath + ": \n\t Vertex shader compilation failed !");
            System.out.println(glGetShaderInfoLog(vertexID,lenght));
        }

        //************
        // Fragment Shader
        //************

        fragmentID = glCreateShader(GL_FRAGMENT_SHADER); //Load shader type
        glShaderSource(fragmentID, fragmenrShaderSRC); // Pass shader source to GPU
        glCompileShader(fragmentID); // Compile shader

        //Error check in compilation process
        success = glGetShaderi(fragmentID,GL_COMPILE_STATUS);
        if(success == GL_FALSE){
            int lenght = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: " + filePath + ": \n\t Vertex shader compilation failed !");
            System.out.println(glGetShaderInfoLog(fragmentID,lenght));
        }

        //************
        // Link shaders and Check for errors
        //************
        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID, fragmentID);
        glLinkProgram(shaderProgramID);

        //Check for linking errors
        success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
        if(success == GL_FALSE){
            int lenght = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.println("ERROR: " + filePath + ":  \n\t Linking of shaders failed !");
            System.out.println(glGetProgramInfoLog(fragmentID,lenght));
            assert false : "";
        }

    }

    public void UseShader(){
        //Bind shader program
        if(!isUsed){
            glUseProgram(shaderProgramID);
            isUsed = true;
        }

    }

    public void DetachShader(){
        glUseProgram(0);
        isUsed = false;
    }

    //SET MATRIX
    public void UploadMat3f(String name, Matrix3f matrix){
        int location = glGetUniformLocation(shaderProgramID,name);
        UseShader();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9); //Matrices 3x3x3 -> this line makes aray of 9 variables
        matrix.get(matBuffer); //Fill matBuffer array with mat4 values
        glUniformMatrix3fv(location,false,matBuffer); //Send data to uniform in shader
    }

    public void SetMetrix(String uniformName, Matrix4f mat4){
        int location = glGetUniformLocation(shaderProgramID,uniformName);
        UseShader();
        FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16); //Matrices 4x4x4 -> this line makes aray of 16 variables
        mat4.get(matBuffer); //Fill matBuffer array with mat4 values
        glUniformMatrix4fv(location,false,matBuffer); //Send data to uniform in shader
    }

    public void UploadVector2Float(String name, Vector2f vector){
        int location = glGetUniformLocation(shaderProgramID, name);
        UseShader();
        glUniform2f(location,vector.x,vector.y);
    }

    public void UploadVector3Float(String name, Vector3f vector){
        int location = glGetUniformLocation(shaderProgramID, name);
        UseShader();
        glUniform3f(location,vector.x,vector.y,vector.z);
    }

    public void UploadVector4Float(String name, Vector4f vector){
        int location = glGetUniformLocation(shaderProgramID, name);
        UseShader();
        glUniform4f(location,vector.x,vector.y,vector.z,vector.w);
    }

    public void UploadFloat(String name, float value){
        int location = glGetUniformLocation(shaderProgramID, name);
        UseShader();
        glUniform1f(location, value);
    }

    public void UploadInt(String name, int value){
        int location = glGetUniformLocation(shaderProgramID, name);
        UseShader();
        glUniform1i(location, value);
    }

    public void UploadTexture(String name,int textureSlot){
        int location = glGetUniformLocation(shaderProgramID, name);
        UseShader();
        glUniform1i(location, textureSlot);
    }



}
