package com.uax.hundir.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanArrayConverter implements AttributeConverter<boolean[], String> {

    @Override
    public String convertToDatabaseColumn(boolean[] attribute) {
        if(attribute == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < attribute.length; i++) {
            sb.append(attribute[i] ? "1" : "0");
            if(i < attribute.length - 1)
                sb.append(",");
        }
        return sb.toString();
    }

    @Override
    public boolean[] convertToEntityAttribute(String dbData) {
        if(dbData == null || dbData.isEmpty()) return new boolean[0];
        String[] parts = dbData.split(",");
        boolean[] arr = new boolean[parts.length];
        for(int i = 0; i < parts.length; i++){
            arr[i] = parts[i].equals("1");
        }
        return arr;
    }
}
