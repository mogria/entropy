package org.playentropy.circuit;

import java.beans.PropertyEditorSupport;
import org.springframework.stereotype.Component;

@Component
public class VectorEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String s) {
        setValue(new StringToVectorConverter().convert(s));
    }

    @Override
    public String getAsText() {
        return ((Vector)getValue()).toString();
    }
}
