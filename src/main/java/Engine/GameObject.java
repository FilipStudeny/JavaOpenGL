package Engine;

import Engine.Models.Mesh;
import org.joml.Vector3f;

public class GameObject {

    private Mesh mesh;

    private float scale;
    private Vector3f position;
    private Vector3f rotation;

    public GameObject(Mesh mesh){
        this.mesh = new Mesh();
        this.position = new Vector3f(0,0,0);
        this.scale = 1f;
        this.rotation = new Vector3f(0,0,0);
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

    public Mesh GetMesh(){
        return mesh;
    }
}
