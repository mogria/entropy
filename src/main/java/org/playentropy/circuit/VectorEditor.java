package org.playentropy.circuit;

import java.beans.PropertyEditorSupport;

public class VectorEditor extends PropertyEditorSupport {
    @Override
    public String getAsText() {
        return ((Vector)getValue()).toString();
    }

    @Override
    public void setAsText(String s) {
        setValue(new StringToVectorConverter().convert(s));
    }
}
