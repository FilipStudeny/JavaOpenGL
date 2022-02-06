package Engine.Lighting;

import org.joml.Vector3f;

public class PointLight {

    private Vector3f colour;
    private Vector3f position;

    private float intensity;
    private float constant;
    private float linear;
    private float exponent;


    public PointLight(Vector3f colour, Vector3f position, float instensity, float constant, float linear, float exponent){
        this.colour = colour;
        this.position = position;
        this.intensity = instensity;
        this.constant = constant;
        this.linear = linear;
        this.exponent = exponent;
    }

    public PointLight(Vector3f colour, Vector3f position, float instensity){
        this.colour = colour;
        this.position = position;
        this.intensity = instensity;
        this.constant = 1;
        this.linear = 0;
        this.exponent = 0;
    }


    public Vector3f getColour() {
        return colour;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public float getConstant() {
        return constant;
    }

    public void setConstant(float constant) {
        this.constant = constant;
    }

    public float getLinear() {
        return linear;
    }

    public void setLinear(float linear) {
        this.linear = linear;
    }

    public float getExponent() {
        return exponent;
    }

    public void setExponent(float exponent) {
        this.exponent = exponent;
    }
}
