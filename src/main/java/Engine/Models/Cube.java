package Engine.Models;

public class Cube {

    private float[] vertices = {

            //FRONT FACE
            -1f, -1f, 1f,
            -1f,  1f, 1f,
             1f, -1f, 1f,
             1f,  1f, 1f,

            //BACK FACE
            -1f, -1f, -1f,
            -1f,  1f, -1f,
             1f, -1f, -1f,
             1f,  1f, -1f,

            //LEFT FACE
            -1f, -1f,  1f,
            -1f,  1f,  1f,
            -1f, -1f, -1f,
            -1f,  1f, -1f,

            //RIGHT FACE
            1f, -1f,  1f,
            1f,  1f,  1f,
            1f, -1f, -1f,
            1f,  1f, -1f,

            //TOP FACE
            -1f, 1f,  1f,
            -1f, 1f, -1f,
             1f, 1f,  1f,
             1f, 1f, -1f,

            //BOTTOM FACE
            -1f, -1f,  1f,
            -1f, -1f, -1f,
             1f, -1f,  1f,
             1f, -1f, -1f,
     };

    private float[] colours = {
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
    };

    private float[] textureCoords = {
            0f, 1f,
            0f, 0f,
            1f, 1f,
            1f, 0f,

            1f, 1f,
            1f, 0f,
            0f, 1f,
            0f, 0f,

            1f, 1f,
            1f, 0f,
            0f, 1f,
            0f, 0f,

            0f, 1f,
            0f, 0f,
            1f, 1f,
            1f, 0f,

            0f, 1f,
            0f, 0f,
            1f, 1f,
            1f, 0f,

            0f, 1f,
            0f, 0f,
            1f, 1f,
            1f, 0f,
    };

    private int[] triangles = {
            0,1,2,
            2,1,3,

            4,5,6,
            6,5,7,

            8,9,10,
            10,9,11,

            12,13,14,
            14,13,15,

            16,17,18,
            18,17,19,

            20,21,22,
            22,21,23
    };

    public float[] GetVertices(){
        return this.vertices;
    }

    public int[] GetTriangles(){
        return this.triangles;
    }

    public float[] GetColours() { return this.colours; }

    public float[] GetTextureCoords() { return this.textureCoords; }
}

