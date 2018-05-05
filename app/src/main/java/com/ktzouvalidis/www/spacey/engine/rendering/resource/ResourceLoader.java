package com.ktzouvalidis.www.spacey.engine.rendering.resource;

import com.ktzouvalidis.www.spacey.ContextHandler;
import com.ktzouvalidis.www.spacey.engine.rendering.Mesh;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import de.javagl.obj.Obj;
import de.javagl.obj.ObjData;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;

/**
 * Created by Kwstas T on 19-Feb-18.
 */

public class ResourceLoader {

    public static String loadShader(int shaderResource) {
        String shaderSource = null;
        InputStream shaderStream = ContextHandler.getAppContext().getResources().openRawResource(shaderResource);
        try {
            shaderSource = IOUtils.toString(shaderStream, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return shaderSource;
    }

    public static Mesh loadMesh(String objName) {
        try {
            Mesh mesh = new Mesh();
            InputStream objInputStream = ContextHandler.getAppContext().getAssets().open(objName);
            Obj objModel = ObjReader.read(objInputStream);

            Obj readableObjModel = ObjUtils.convertToRenderable(objModel);
            mesh.addVertices(ObjData.getVertices(readableObjModel), ObjData.getFaceVertexIndices(readableObjModel));

            System.out.println(objModel.getNumVertices());
            System.out.println(objModel.getNumFaces());

            return mesh;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}
