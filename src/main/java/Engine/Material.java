package Engine;


import Engine.Lighting.DirectionalLight;
import Engine.Lighting.PointLight;
import Engine.Lighting.Spotlight;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Material {

    private Vector4f ambientColour;
    private Vector4f diffuseColour;
    private Vector4f specularColour;

    private float reflectance;
    private boolean hasTexture;

    private Vector3f ambientLight;
    private Vector4f DEFAULT_COLOUR;

    private Texture texture;

    private float specularPower;

    private DirectionalLight directionalLight;
    private PointLight pointLight;
    private Spotlight spotlight;

    public Material(){

        this.texture = new Texture("src/textures/dorime.png");

        this.DEFAULT_COLOUR = new Vector4f(0.5f,0.5f,0.5f,1f);
        this.ambientLight = new Vector3f(0.3f , 0.3f, 0.3f);
        this.ambientColour = DEFAULT_COLOUR;
        this.diffuseColour = DEFAULT_COLOUR;
        this.specularColour = DEFAULT_COLOUR;
        this.hasTexture = true;
        this.reflectance = 1;
        this.specularPower = 10f;
    }

    public Texture GetTexture() { return this.texture; }
    public boolean ContainsTexture() { return this.hasTexture; }


    public Vector4f getAmbientColour() {
        return ambientColour;
    }


    public Vector4f getDiffuseColour() {
        return diffuseColour;
    }


    public Vector4f getSpecularColour() {
        return specularColour;
    }


    public float getReflectance() {
        return reflectance;
    }


    public Vector3f getAmbientLight() {
        return ambientLight;
    }



    public float getSpecularPower() {
        return specularPower;
    }


    public DirectionalLight getDirectionalLight() {
        return directionalLight;
    }

    public void setDirectionalLight(DirectionalLight directionalLight) {
        this.directionalLight = directionalLight;
    }

    public PointLight getPointLight() {
        return pointLight;
    }

    public void setPointLight(PointLight pointLight) {
        this.pointLight = pointLight;
    }

    public Spotlight getSpotlight() {
        return spotlight;
    }

    public void setSpotlight(Spotlight spotlight) {
        this.spotlight = spotlight;
    }
}
