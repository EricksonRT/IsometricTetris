package nico.tetris.engine.math;

import java.nio.FloatBuffer;

public class Matrix4f {

    public float m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33;

    /**Create a matrix and set it to be the identity matrix.
     */
    public Matrix4f() {
        this.m00 = 1.0f; this.m01 = 0.0f; this.m02 = 0.0f; this.m03 = 0.0f;
        this.m10 = 0.0f; this.m11 = 1.0f; this.m12 = 0.0f; this.m13 = 0.0f;
        this.m20 = 0.0f; this.m21 = 0.0f; this.m22 = 1.0f; this.m23 = 0.0f;
        this.m30 = 0.0f; this.m31 = 0.0f; this.m32 = 0.0f; this.m33 = 1.0f;
    }

    /**Store this matrix in a float buffer <br>
     * The matrix is stored in column major (openGL) order. <br>
     * @param buf - The buffer to store this matrix in
     */
    public void store(FloatBuffer buf) {
        buf.put(m00); buf.put(m01); buf.put(m02); buf.put(m03);
        buf.put(m10); buf.put(m11); buf.put(m12); buf.put(m13);
        buf.put(m20); buf.put(m21); buf.put(m22); buf.put(m23);
        buf.put(m30); buf.put(m31); buf.put(m32); buf.put(m33);
    }

    /**Translate the matrix
     * @param vec - The vector to translate by
     */
    public Matrix4f translate(Vector3f vec) {
        this.m30 += this.m00 * vec.x + this.m10 * vec.y + this.m20 * vec.z;
        this.m31 += this.m01 * vec.x + this.m11 * vec.y + this.m21 * vec.z;
        this.m32 += this.m02 * vec.x + this.m12 * vec.y + this.m22 * vec.z;
        this.m33 += this.m03 * vec.x + this.m13 * vec.y + this.m23 * vec.z;
        return this;
    }

    /**Translate the matrix
     * @param vec - The vector to translate by
     */
    public Matrix4f translate(Vector2f vec) {
        this.m30 += this.m00 * vec.x + this.m10 * vec.y;
        this.m31 += this.m01 * vec.x + this.m11 * vec.y;
        this.m32 += this.m02 * vec.x + this.m12 * vec.y;
        this.m33 += this.m03 * vec.x + this.m13 * vec.y;
        return this;
    }

    /**Scales the matrix
     * @param vec The vector to scale by
     */
    public Matrix4f scale(Vector3f vec) {
        this.m00 = this.m00 * vec.x;
        this.m01 = this.m01 * vec.x;
        this.m02 = this.m02 * vec.x;
        this.m03 = this.m03 * vec.x;
        this.m10 = this.m10 * vec.y;
        this.m11 = this.m11 * vec.y;
        this.m12 = this.m12 * vec.y;
        this.m13 = this.m13 * vec.y;
        this.m20 = this.m20 * vec.z;
        this.m21 = this.m21 * vec.z;
        this.m22 = this.m22 * vec.z;
        this.m23 = this.m23 * vec.z;
        return this;
    }

    /**Scales the matrix
     * @param vec The vector to scale by
     */
    public Matrix4f scale(Vector2f vec) {
        this.m00 = this.m00 * vec.x;
        this.m01 = this.m01 * vec.x;
        this.m02 = this.m02 * vec.x;
        this.m03 = this.m03 * vec.x;
        this.m10 = this.m10 * vec.y;
        this.m11 = this.m11 * vec.y;
        this.m12 = this.m12 * vec.y;
        this.m13 = this.m13 * vec.y;
        return this;
    }

    public float determinant() {
        float f = m00 * ((m11 * m22 * m33 + m12 * m23 * m31 + m13 * m21 * m32) - m13 * m22 * m31 - m11 * m23 * m32 - m12 * m21 * m33);
        f -= m01 * ((m10 * m22 * m33 + m12 * m23 * m30 + m13 * m20 * m32) - m13 * m22 * m30 - m10 * m23 * m32 - m12 * m20 * m33);
        f += m02 * ((m10 * m21 * m33 + m11 * m23 * m30 + m13 * m20 * m31) - m13 * m21 * m30 - m10 * m23 * m31 - m11 * m20 * m33);
        f -= m03 * ((m10 * m21 * m32 + m11 * m22 * m30 + m12 * m20 * m31) - m12 * m21 * m30 - m10 * m22 * m31 - m11 * m20 * m32);
        return f;
    }

    private float determinant3x3(float t00, float t01, float t02, float t10, float t11, float t12, float t20, float t21, float t22) {
        return t00 * (t11 * t22 - t12 * t21) + t01 * (t12 * t20 - t10 * t22) + t02 * (t10 * t21 - t11 * t20);
    }

    public Matrix4f invert() {
        float determinant = this.determinant();

        if (determinant != 0) {
            float determinant_inv = 1.0f / determinant;

            // first row
            float t00 = determinant3x3(this.m11, this.m12, this.m13, this.m21, this.m22, this.m23, this.m31, this.m32, this.m33);
            float t01 = -determinant3x3(this.m10, this.m12, this.m13, this.m20, this.m22, this.m23, this.m30, this.m32, this.m33);
            float t02 = determinant3x3(this.m10, this.m11, this.m13, this.m20, this.m21, this.m23, this.m30, this.m31, this.m33);
            float t03 = -determinant3x3(this.m10, this.m11, this.m12, this.m20, this.m21, this.m22, this.m30, this.m31, this.m32);
            // second row
            float t10 = -determinant3x3(this.m01, this.m02, this.m03, this.m21, this.m22, this.m23, this.m31, this.m32, this.m33);
            float t11 = determinant3x3(this.m00, this.m02, this.m03, this.m20, this.m22, this.m23, this.m30, this.m32, this.m33);
            float t12 = -determinant3x3(this.m00, this.m01, this.m03, this.m20, this.m21, this.m23, this.m30, this.m31, this.m33);
            float t13 = determinant3x3(this.m00, this.m01, this.m02, this.m20, this.m21, this.m22, this.m30, this.m31, this.m32);
            // third row
            float t20 = determinant3x3(this.m01, this.m02, this.m03, this.m11, this.m12, this.m13, this.m31, this.m32, this.m33);
            float t21 = -determinant3x3(this.m00, this.m02, this.m03, this.m10, this.m12, this.m13, this.m30, this.m32, this.m33);
            float t22 = determinant3x3(this.m00, this.m01, this.m03, this.m10, this.m11, this.m13, this.m30, this.m31, this.m33);
            float t23 = -determinant3x3(this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m30, this.m31, this.m32);
            // fourth row
            float t30 = -determinant3x3(this.m01, this.m02, this.m03, this.m11, this.m12, this.m13, this.m21, this.m22, this.m23);
            float t31 = determinant3x3(this.m00, this.m02, this.m03, this.m10, this.m12, this.m13, this.m20, this.m22, this.m23);
            float t32 = -determinant3x3(this.m00, this.m01, this.m03, this.m10, this.m11, this.m13, this.m20, this.m21, this.m23);
            float t33 = determinant3x3(this.m00, this.m01, this.m02, this.m10, this.m11, this.m12, this.m20, this.m21, this.m22);

            // transpose and divide by the determinant
            this.m00 = t00*determinant_inv;
            this.m11 = t11*determinant_inv;
            this.m22 = t22*determinant_inv;
            this.m33 = t33*determinant_inv;
            this.m01 = t10*determinant_inv;
            this.m10 = t01*determinant_inv;
            this.m20 = t02*determinant_inv;
            this.m02 = t20*determinant_inv;
            this.m12 = t21*determinant_inv;
            this.m21 = t12*determinant_inv;
            this.m03 = t30*determinant_inv;
            this.m30 = t03*determinant_inv;
            this.m13 = t31*determinant_inv;
            this.m31 = t13*determinant_inv;
            this.m32 = t23*determinant_inv;
            this.m23 = t32*determinant_inv;
        }
        return this;
    }

    public void rotate(float angle, Vector3f axis) {
        float c = (float) Math.cos(angle);
        float s = (float) Math.sin(angle);
        float oneminusc = 1.0f - c;
        float xy = axis.x*axis.y;
        float yz = axis.y*axis.z;
        float xz = axis.x*axis.z;
        float xs = axis.x*s;
        float ys = axis.y*s;
        float zs = axis.z*s;

        float f00 = axis.x*axis.x*oneminusc+c;
        float f01 = xy*oneminusc+zs;
        float f02 = xz*oneminusc-ys;
        // n[3] not used
        float f10 = xy*oneminusc-zs;
        float f11 = axis.y*axis.y*oneminusc+c;
        float f12 = yz*oneminusc+xs;
        // n[7] not used
        float f20 = xz*oneminusc+ys;
        float f21 = yz*oneminusc-xs;
        float f22 = axis.z*axis.z*oneminusc+c;

        float t00 = this.m00 * f00 + this.m10 * f01 + this.m20 * f02;
        float t01 = this.m01 * f00 + this.m11 * f01 + this.m21 * f02;
        float t02 = this.m02 * f00 + this.m12 * f01 + this.m22 * f02;
        float t03 = this.m03 * f00 + this.m13 * f01 + this.m23 * f02;
        float t10 = this.m00 * f10 + this.m10 * f11 + this.m20 * f12;
        float t11 = this.m01 * f10 + this.m11 * f11 + this.m21 * f12;
        float t12 = this.m02 * f10 + this.m12 * f11 + this.m22 * f12;
        float t13 = this.m03 * f10 + this.m13 * f11 + this.m23 * f12;
        this.m20 = this.m00 * f20 + this.m10 * f21 + this.m20 * f22;
        this.m21 = this.m01 * f20 + this.m11 * f21 + this.m21 * f22;
        this.m22 = this.m02 * f20 + this.m12 * f21 + this.m22 * f22;
        this.m23 = this.m03 * f20 + this.m13 * f21 + this.m23 * f22;
        this.m00 = t00;
        this.m01 = t01;
        this.m02 = t02;
        this.m03 = t03;
        this.m10 = t10;
        this.m11 = t11;
        this.m12 = t12;
        this.m13 = t13;
    }

    public static Matrix4f createTransformationMatrix(Vector3f translation, Vector2f scale) {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(translation);
        matrix.scale(scale);
        return matrix;
    }

    public static Matrix4f createProjectionMatrix(float fov, float zNear, float zFar) {
        float aspectRatio = (float) 1000 / (float) 600; //Should be global variables but ok
        float yScale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))) * aspectRatio);
        float xScale = yScale / aspectRatio;
        float frustumLength = zFar - zNear;

        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = xScale;
        projectionMatrix.m11 = yScale;
        projectionMatrix.m22 = -((zFar + zNear) / frustumLength);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * zNear * zFar) / frustumLength);
        projectionMatrix.m33 = 0;

        return projectionMatrix;
    }

    public static Matrix4f createViewMatrix(Vector3f cameraPos, float pitch, float yaw) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.rotate(pitch, new Vector3f(1, 0, 0));
        viewMatrix.rotate(yaw, new Vector3f(0, 1, 0));
        viewMatrix.translate(cameraPos.negated());
        return viewMatrix;
    }
}
