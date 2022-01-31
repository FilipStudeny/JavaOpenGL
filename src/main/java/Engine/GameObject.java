package Engine;

import Engine.Models.Cube;
import Engine.Models.Mesh;
import Engine.Models.Square;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class GameObject extends Mesh{

    private float scale;
    private Vector3f position;
    private Vector3f rotation;

    private Cube cube;

    public GameObject(){
        this.cube = new Cube();

        this.SetTriangles(this.cube.GetTriangles());
        this.SetVertices(this.cube.GetVertices());

        this.position = new Vector3f(0,0,-10);
        this.scale = 5f;
        this.rotation = new Vector3f(50,50,50);
    }


    public Vector3f GetPosition(){
        return this.position;
    }

    public Vector3f GetRotation(){
        return this.rotation;
    }

    public float GetScale(){
        return this.scale;
    }

    public void SetPosition(float x, float y, float z){
        this.position = new Vector3f(x,y,z);
    }

    public void SetRotation(float x, float y, float z){
        this.rotation = new Vector3f(x,y,z);
    }

    public void SetScale(float scale){
        this.scale = scale;
    }
}
