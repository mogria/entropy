package org.playentropy.circuit;

import org.springframework.core.convert.converter.Converter;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Exception;
import java.lang.NumberFormatException;
import org.springframework.stereotype.Component;

@Component
public class StringToVectorConverter implements Converter<String, Vector> {
    public Vector convert(String string)
        throws IllegalArgumentException {
        String[] parts = string.split(",");

        if(parts.length != 2) throwException(string, null);
        else try {
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            return new Vector(x, y);
        } catch (NumberFormatException ex) {
            throwException(string, ex);
        }
        return new Vector(0, 0);
    }

    private void throwException(String string, Exception ex)
        throws IllegalArgumentException {
        throw new IllegalArgumentException("Could not convert string '" + string + "' to vector");
    }
}
