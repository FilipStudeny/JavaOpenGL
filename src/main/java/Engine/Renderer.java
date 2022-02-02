package Engine;

import org.joml.Math;
import org.joml.Matrix4f;

import java.util.ArrayList;

public class Renderer {

    private float FOV = Math.toRadians(60f);
    private float nearPlane = 0.01f;
    private float farPlane = 1000.0f;
    private int width = 800;
    private int height = 600;

    ArrayList<GameObject> gameObjects;
    private WorldTransformation transformation;

    public Renderer(){
        this.transformation = new WorldTransformation();
        this.gameObjects = new ArrayList<GameObject>();
    }

    public void InitializeObjects(ArrayList<GameObject> gameObjects){
        for (GameObject gameObject : gameObjects){
           // gameObject.Init();
            gameObject.SetProjectionMatrix(transformation.GetProjectionMatrix(FOV,width,height,nearPlane,farPlane));
           // gameObject.Init();
            try{
                gameObject.Init();
            }catch ( Exception e){
                System.out.println(e);
            }

            this.gameObjects.add(gameObject);
        }
    }

    public void RenderObjects(){
        for (GameObject gameObject : this.gameObjects){
            //Set worldMatrix for object
            Matrix4f worldMatrix = transformation.GetWorldMatrix(
                    gameObject.GetPosition(),
                    gameObject.GetRotation(),
                    gameObject.GetScale()
            );

            float rotation = gameObject.GetRotation().x + 1.5f;
            if ( rotation > 360 ) {
                rotation = 0;
            }

            float rotationY = gameObject.GetRotation().y + 1.5f;
            if ( rotationY > 360 ) {
                rotationY = 0;
            }

            float rotationZ = gameObject.GetRotation().z + 1.5f;
            if ( rotationZ > 360 ) {
                rotationZ = 0;
            }


           gameObject.SetRotation(0,rotationY,0);
            gameObject.SetWorldMetrix(worldMatrix);
            gameObject.Render();
        }
    }
}
