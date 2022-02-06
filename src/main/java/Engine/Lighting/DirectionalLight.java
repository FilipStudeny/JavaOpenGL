package Engine.Lighting;

import org.joml.Vector3f;

public class DirectionalLight {

    private Vector3f colour;
    private Vector3f direction;
    private float intensity;

    public DirectionalLight(Vector3f colour, Vector3f direction, float intensity){
        this.colour = colour;
        this.direction = direction;
        this.intensity = intensity;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public Vector3f getColour() {
        return colour;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }
}
