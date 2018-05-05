package com.ktzouvalidis.www.spacey.engine.rendering;

import android.opengl.GLES20;

import com.ktzouvalidis.www.spacey.engine.core.Matrix4f;
import com.ktzouvalidis.www.spacey.engine.core.Vector3f;
import com.ktzouvalidis.www.spacey.util.CoreUtil;

import java.util.HashMap;

/**
 * Created by Kwstas T on 20-Feb-18.
 */

public class Shader {
    private int program;
    private HashMap<String, Integer> uniforms;

    public Shader() {
        program = GLES20.glCreateProgram();
        uniforms = new HashMap<String, Integer>();

        if(program == 0) {
            System.err.println("Shader creation failed: Could not find valid memory location on construction.");
            System.exit(1);
        }
    }

    public void bind() {
        GLES20.glUseProgram(program);
    }

    public void addVertexShader(String text) {
        addProgram(text, GLES20.GL_VERTEX_SHADER);
    }

    public void addFragmentShader(String text) {
        addProgram(text, GLES20.GL_FRAGMENT_SHADER);
    }

    private void SetAttribLocation(String attributeName, int location)
    {
        GLES20.glBindAttribLocation(program, location, attributeName);
    }

    private void AddAllAttributes(String text) {
        final String ATTRIBUTE_KEYWORD = "attribute";
        int attributeStartLocation = text.indexOf(ATTRIBUTE_KEYWORD);
        int attribNumber = 0;

        while(attributeStartLocation != -1)
        {
            if((Character.isWhitespace(text.charAt(attributeStartLocation)) || text.charAt(attributeStartLocation) == ';')
                    && Character.isWhitespace(text.charAt(attributeStartLocation + ATTRIBUTE_KEYWORD.length()))) {
                attributeStartLocation = text.indexOf(ATTRIBUTE_KEYWORD, attributeStartLocation + ATTRIBUTE_KEYWORD.length());
                continue;
            }
            int begin = attributeStartLocation + ATTRIBUTE_KEYWORD.length() + 1;
            int end = text.indexOf(";", begin);
            String attributeLine = text.substring(begin, end).trim();
            String attributeName = attributeLine.substring(attributeLine.indexOf(' ') + 1, attributeLine.length()).trim();

            SetAttribLocation(attributeName, attribNumber);
            attribNumber++;

            attributeStartLocation = text.indexOf(ATTRIBUTE_KEYWORD, attributeStartLocation + ATTRIBUTE_KEYWORD.length());
        }
    }

    public void addUniform(String uniform) {
        int uniformLocation = GLES20.glGetUniformLocation(program, uniform);
        if(uniformLocation == 0xFFFFFF) {
            System.err.println("Error: Could not find uniform: " + uniform);
            System.exit(1);
        }

        uniforms.put(uniform, uniformLocation);
    }

    public void setUniform(String uniformName, int value) {
        GLES20.glUniform1i(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, Vector3f value) {
        GLES20.glUniform4f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ(), 1.0f);
    }

    public void setUniform(String uniformName, Matrix4f value) {
        float[] vectorValue = CoreUtil.toVectorMatrix(value);
        GLES20.glUniformMatrix4fv(uniforms.get(uniformName), 1, false, vectorValue, 0);
    }

    public void addProgram(String text, int type) {
        int shader = GLES20.glCreateShader(type);

        if(shader == 0) {
            System.err.println("Shader creation failed: Could not find valid memory location when adding shader.");
            System.exit(1);
        }

        AddAllAttributes(text);
        GLES20.glShaderSource(shader, text);
        GLES20.glCompileShader(shader);

        int[] isCompiled = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, isCompiled, 0);
        if(isCompiled[0] == GLES20.GL_FALSE) {
            System.err.println("!!!\n" + GLES20.glGetShaderInfoLog(shader) + "!!!\n");
            System.exit(1);
        }

        GLES20.glAttachShader(program, shader);
    }

    public void compileShader() {
        GLES20.glLinkProgram(program);
        int[] isOK = new int[1];

        GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, isOK, 0);
        if(isOK[0] == GLES20.GL_FALSE) {
            System.err.println(GLES20.glGetShaderInfoLog(program));
            System.exit(1);
        }

        GLES20.glValidateProgram(program);
        GLES20.glGetProgramiv(program, GLES20.GL_VALIDATE_STATUS, isOK, 0);
        if(isOK[0] == GLES20.GL_FALSE) {
            System.err.println(GLES20.glGetShaderInfoLog(program));
            System.exit(1);
        }
    }
}
