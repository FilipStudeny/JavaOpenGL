package Engine.Models;

public class Triangle{

    private float[] vertices = {
            //Position
            -0.5f, -0.5f, 0.0f,  1.0f, 1.0f, 0.0f, 1.0f,    0.0f, 0.0f,
            0.5f, -0.5f, 0.0f,   1.0f, 0.0f, 0.0f, 1.0f,    1.0f, 0.0f,
            0f,  0.5f, 0.0f,     0.0f, 1.0f, 0.0f, 1.0f,    0.5f, 1.0f,
    };

    private int[] triangles = {
            2,1,0, //Top right triangle
    };

    public float[] GetVertices(){
        return this.vertices;
    }

    public int[] GetTriangles(){
        return this.triangles;
    }


}
