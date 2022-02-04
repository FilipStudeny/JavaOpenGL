package Engine;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;
import java.util.ArrayList;

public class ModelLoader {

    private ArrayList<Float> vertices;
    private ArrayList<Float> colours;
    private ArrayList<Float> textureCoords;
    private ArrayList<Float> normals;
    private ArrayList<Integer> faces;

    public ModelLoader(){
        this.vertices = new ArrayList<>();
        this.colours = new ArrayList<>();
        this.textureCoords = new ArrayList<>();
        this.normals = new ArrayList<>();
        this.faces = new ArrayList<>();
    }

    public ArrayList<Float> GetVertices(){
        return this.vertices;
    }

    public ArrayList<Float> GetColours(){
        return this.colours;
    }

    public ArrayList<Float> GetTextureCoords(){
        return this.textureCoords;
    }

    public ArrayList<Float> GetNormals(){
        return this.normals;
    }

    public ArrayList<Integer> GetFaces(){
        return this.faces;
    }

    public void LoadModel(String filePath){

        AIScene scene = Assimp.aiImportFile(filePath, Assimp.aiProcess_Triangulate);

        PointerBuffer buffer = scene.mMeshes();
        for (int i = 0; i < buffer.limit(); i++) {
            AIMesh mesh = AIMesh.create(buffer.get(i));
            ProcessMesh(mesh);
        }
    }

    private void ProcessMesh(AIMesh mesh){

        AIVector3D.Buffer vectors = mesh.mVertices();
        for (int i = 0; i < vectors.limit(); i++) {
            AIVector3D vector = vectors.get(i);

            this.vertices.add(vector.x());
            this.vertices.add(vector.y());
            this.vertices.add(vector.z());
        }

        int faceCount = mesh.mNumFaces();
        int[] facesList = new int[faceCount * 3];
        AIFace.Buffer faces = mesh.mFaces();
        for (int i = 0; i < faces.limit(); i++) {
            AIFace face = faces.get(i);

            facesList[i * 3] = face.mIndices().get(0);
            facesList[i * 3 + 1] = face.mIndices().get(1);
            facesList[i * 3 + 2] = face.mIndices().get(2);

            this.faces.add(facesList[i * 3]);
            this.faces.add(facesList[i * 3 + 1]);
            this.faces.add(facesList[i * 3 + 2]);

        }

        AIVector3D.Buffer coords = mesh.mTextureCoords(0);
        for (int i = 0; i < coords.limit(); i++) {
            AIVector3D coord = coords.get(i);

            this.textureCoords.add(coord.x());
            this.textureCoords.add(coord.y());
        }

        AIVector3D.Buffer normals = mesh.mNormals();
        for (int i = 0; i < normals.limit(); i++) {
            AIVector3D normal = normals.get(i);

            this.normals.add(normal.x());
            this.normals.add(normal.y());
            this.normals.add(normal.z());
        }

        AIColor4D.Buffer colors = mesh.mColors(0);
        for (int i = 0; i < colors.limit(); i++) {
            AIColor4D color = colors.get(i);

            this.colours.add(color.r());
            this.colours.add(color.g());
            this.colours.add(color.b());
            this.colours.add(color.a());
        }
    }
}
