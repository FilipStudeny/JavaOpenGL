package Engine;

import Engine.Input.KeyboardListener;
import Engine.Lighting.DirectionalLight;
import Engine.Lighting.PointLight;
import Engine.Lighting.Spotlight;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Renderer {

    private float FOV = Math.toRadians(60f);
    private float nearPlane = 0.01f;
    private float farPlane = 1000.0f;
    private int width = 800;
    private int height = 600;

    private ArrayList<GameObject> gameObjects;
    private WorldTransformation transformation;
    private DirectionalLight directionalLight;
    private PointLight pointLight;
    private Spotlight spotlight;

    float lightAngle;
    public Renderer( ){
        this.transformation = new WorldTransformation();
        this.gameObjects = new ArrayList<GameObject>();

        Vector3f direction = new Vector3f(-1,10,10);
        Vector3f colour = new Vector3f(1,1,1);
        this.directionalLight = new DirectionalLight(colour, direction, 100);
        lightAngle = -90;

        Vector3f lightPosition = new Vector3f(0,0,-5f);
        Vector3f lightColour = new Vector3f(1,1,1);
        this.pointLight = new PointLight(lightColour,lightPosition,1.0f,0,0,1);

        Vector3f coneDirection = new Vector3f(0,0,1);
        float cutOff = (float)Math.cos(Math.toRadians(180));
        this.spotlight = new Spotlight(new PointLight(lightColour,new Vector3f(0,0,1F),1.0f,0,0,1), coneDirection, cutOff);

    }

    public void InitializeObjects(ArrayList<GameObject> gameObjects){
        for (GameObject gameObject : gameObjects){
            gameObject.SetProjectionMatrix(transformation.GetProjectionMatrix(FOV,width,height,nearPlane,farPlane));

            gameObject.Init();
            this.gameObjects.add(gameObject);
        }
    }

    public void RenderObjects(){
        for (GameObject gameObject : this.gameObjects){

            Matrix4f viewMatrix = Camera.GetLookMatrix();
            Matrix4f worldMatrix = transformation.GetModelViewMatrix(gameObject,Camera.GetLookMatrix());
            gameObject.SetWorldMetrix(worldMatrix);

            float rotation = gameObject.GetRotation().x + 1.5f;
            if ( rotation > 360 ) {
                rotation = 0;
            }


            lightAngle += 0.5f;
            if(lightAngle > 90){
                directionalLight.setIntensity(0);

                if(lightAngle >= 360){
                    lightAngle = -90;

                }
            }else if(lightAngle <= -80 || lightAngle >= 80){
                float factor = 1 - (Math.abs(lightAngle) - 80) / 10.0f;
                directionalLight.setIntensity(factor);
                directionalLight.getColour().y = Math.max(factor,0.9f);
                directionalLight.getColour().z = Math.max(factor,0.5f);
            }else {
                directionalLight.setIntensity(1);
                directionalLight.getColour().x = 1;
                directionalLight.getColour().y = 1;
                directionalLight.getColour().z = 1;
            }

            double angRad = Math.toRadians(lightAngle);
            directionalLight.getDirection().x = (float)Math.sin(angRad);
            directionalLight.getDirection().y = (float)Math.sin(angRad);

            if(KeyboardListener.isKeyPressed(GLFW_KEY_X)){
                pointLight.getPosition().x += 0.1f;
            }

            if(KeyboardListener.isKeyPressed(GLFW_KEY_C)){
                pointLight.getPosition().x -= 0.1f;
            }

            float lightPos = spotlight.getPointLight().getPosition().z;
            if(KeyboardListener.isKeyPressed((GLFW_KEY_G))){
                spotlight.getPointLight().getColour().z = lightPos + 0.1f;
            }

            if(KeyboardListener.isKeyPressed((GLFW_KEY_H))){
                spotlight.getPointLight().getColour().z = lightPos - 0.1f;
            }

            gameObject.GetMaterial().setDirectionalLight(directionalLight);
            gameObject.GetMaterial().setPointLight(pointLight);
            gameObject.GetMaterial().setSpotlight(spotlight);

            gameObject.SetViewMatrix(viewMatrix);
            gameObject.SetRotation(90,0,90);
            gameObject.SetScale(1);
            gameObject.Render();
        }
    }
}