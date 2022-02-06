package Engine;

import Engine.Models.Cube;
import Engine.Models.Mesh;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;

public class GameObject extends Mesh{

    private float scale;
    private Vector3f position;
    private Vector3f rotation;

    private ModelLoader modelLoader;
    private Material material;

    public GameObject(){

        //LOAD MODEL
        this.material = new Material();
        this.modelLoader = new ModelLoader();
        this.modelLoader.LoadModel("House.dae");

        SetMaterial(this.material);

        //GET DATA FROM MODEL
        SetVertices(FloatListToArray(modelLoader.GetVertices()));
        SetTriangles(IntListToArray(modelLoader.GetFaces()));
        SetColours(FloatListToArray(modelLoader.GetColours()));
        SetTextureCoords(FloatListToArray(modelLoader.GetVertices()));
        SetNormals(FloatListToArray(modelLoader.GetNormals()));


        this.position = new Vector3f(0,0,0);
        this.scale = 3f;
        this.rotation = new Vector3f(0,0,0);

    }

    //TRANSFER DATA FROM ARRAYLISTS TO ARRAYS
    private float[] FloatListToArray(ArrayList<Float> list){

        float[] array = new float[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    private int[] IntListToArray(ArrayList<Integer> list){

        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
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

    public Material GetMaterial() { return this.material; }




}