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
    private Camera camera;

    public Renderer(Camera camera){
        this.transformation = new WorldTransformation();
        this.gameObjects = new ArrayList<GameObject>();
        this.camera = camera;
    }

    public void InitializeObjects(ArrayList<GameObject> gameObjects){
        for (GameObject gameObject : gameObjects){
            gameObject.SetProjectionMatrix(transformation.GetProjectionMatrix(FOV,width,height,nearPlane,farPlane));
            //Set worldMatrix for object


            gameObject.Init();

            this.gameObjects.add(gameObject);
        }
    }

    public void RenderObjects(){
        for (GameObject gameObject : this.gameObjects){

            Matrix4f viewMatrix = transformation.GetWievMatrix(camera);

            Matrix4f worldMatrix = transformation.GetModelViewMatrix(gameObject,viewMatrix);
            gameObject.SetWorldMetrix(worldMatrix);

            float rotation = gameObject.GetRotation().x + 1.5f;
            if ( rotation > 360 ) {
                rotation = 0;
            }
            gameObject.SetViewMatrix(viewMatrix);
            //gameObject.SetRotation(rotation,rotation,rotation);
            gameObject.Render();
        }
    }
}