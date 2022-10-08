package dev.simplemachine.opengl.objects;

import dev.simplemachine.opengl.glenum.QueryType;
import org.lwjgl.opengl.GL45;

public class OglQuery extends AbstractOglObject{

    public OglQuery(QueryType queryType) {
        super(GL45.glCreateQueries(queryType.constant));
    }
}
