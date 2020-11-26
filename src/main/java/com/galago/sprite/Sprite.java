package com.galago.sprite;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.texture.Texture;
import com.jme3.util.BufferUtils;

/**
 *
 * This is a sprite mesh and will handle the rendering of sprite image with
 * there uv coords in 2d.
 *
 * @author NideBruyn
 */
public class Sprite extends Mesh {

    private float width;
    private float height;
    private int rows = 1;
    private int columns = 1;
    private int colPosition = 0;
    private int rowPosition = 0;
    private float uvSpacing = 0.001f;
    private Vector2f[] texCoord = new Vector2f[4];

    /**
     * Serialization only. Do not use.
     */
    public Sprite() {
    }

    public Sprite(float width, float height) {
        this(width, height, 1, 1, 0, 0);
    }

    public Sprite(float width, float height, int columns, int rows, int colPosition, int rowPosition) {
        texCoord = new Vector2f[4];

        this.width = width;
        this.height = height;
        this.columns = columns;
        this.rows = rows;
        this.colPosition = colPosition;
        this.rowPosition = rowPosition;

        float colSize = 1f / (float) columns;
        float rowSize = 1f / (float) rows;

        texCoord[0] = new Vector2f(colSize * colPosition + uvSpacing, rowSize * rowPosition + rowSize - uvSpacing);
        texCoord[1] = new Vector2f(colSize * colPosition + colSize - uvSpacing, rowSize * rowPosition + rowSize - uvSpacing);
        texCoord[2] = new Vector2f(colSize * colPosition + uvSpacing, rowSize * rowPosition + uvSpacing);
        texCoord[3] = new Vector2f(colSize * colPosition + colSize - uvSpacing, rowSize * rowPosition + uvSpacing);

        initializeMesh(texCoord);

    }

    public Sprite(float width, float height, int x, int y, int w, int h, Texture t) {
        texCoord = new Vector2f[4];
        this.width = width;
        this.height = height;

        float imgH = t.getImage().getHeight();
        float imgW = t.getImage().getWidth();

        texCoord[0] = new Vector2f(x / imgW, (y + h) / imgH);
        texCoord[1] = new Vector2f((x + w) / imgW, (y + h) / imgH);
        texCoord[2] = new Vector2f(x / imgW, y / imgH);
        texCoord[3] = new Vector2f((x + w) / imgW, y / imgH);

        initializeMesh(texCoord);
    }

    private void initializeMesh(Vector2f[] texCoord) {
        // Vertex positions in space
        Vector3f[] vertices = new Vector3f[4];
        vertices[0] = new Vector3f(-width * 0.5f, -height * 0.5f, 0f);
        vertices[1] = new Vector3f(width * 0.5f, -height * 0.5f, 0f);
        vertices[2] = new Vector3f(-width * 0.5f, height * 0.5f, 0f);
        vertices[3] = new Vector3f(width * 0.5f, height * 0.5f, 0f);

        // Indexes. We define the order in which mesh should be constructed
        short[] indexes = {2, 0, 1, 1, 3, 2};

        // Setting buffers
        setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
        setBuffer(VertexBuffer.Type.Normal, 3, new float[]{0, 0, 1,
            0, 0, 1,
            0, 0, 1,
            0, 0, 1});
        setBuffer(VertexBuffer.Type.Index, 1, BufferUtils.createShortBuffer(indexes));
        updateBound();

    }

    /**
     * This method can be called if the user wants to flip the coordinates.
     *
     * @param flip
     */
    public void flipXCoords() {
        Vector2f tmp = texCoord[0];
        texCoord[0] = texCoord[1];
        texCoord[1] = tmp;
        tmp = texCoord[2];
        texCoord[2] = texCoord[3];
        texCoord[3] = tmp;
        setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
    }

    public void flipYCoords() {
        Vector2f tmp = texCoord[0];
        texCoord[0] = texCoord[2];
        texCoord[2] = tmp;
        tmp = texCoord[1];
        texCoord[1] = texCoord[3];
        texCoord[3] = tmp;
        setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoord));
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getColPosition() {
        return colPosition;
    }

    public int getRowPosition() {
        return rowPosition;
    }
}
