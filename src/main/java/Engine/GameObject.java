package Engine;

import Engine.Models.Cube;
import Engine.Models.Mesh;
import Engine.Models.Square;
import Engine.Models.Triangle;
import org.joml.Vector3f;

import java.util.PrimitiveIterator;

public class GameObject extends Mesh{

    private float scale;
    private Vector3f position;
    private Vector3f rotation;

    //BODY
    private Cube cube;
    private Triangle triangle;
    private Square square;


    public GameObject(){
        this.cube = new Cube();
        this.triangle = new Triangle();
        this.square = new Square();

        this.SetTriangles(this.square.GetTriangles());
        this.SetVertices(this.square.GetVertices());


        this.position = new Vector3f(0,0,-15);
        this.scale = 5f;
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
}