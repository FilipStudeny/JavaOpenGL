package Engine.Models;

public class Square{

    private float[] vertices = {
            //POSITIONS
            0.5f, -0.5f, 0.0f,
            -0.5f,  0.5f, 0.0f,
            0.5f,  0.5f, 0.0f ,
            -0.5f, -0.5f, 0.0f,
    };

    private float[] colours = {
            1.0f, 0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
    };

    private float[] textureCoords =  {
            1,1,
            0,0,
            1,0,
            0,1,
    };

    private int[] triangles = {
            2,1,0, //Top right triangle
             0,1,3, //Bottom left triangle
    };

    public float[] GetVertices(){
        return this.vertices;
    }

    public int[] GetTriangles(){
        return this.triangles;
    }

}