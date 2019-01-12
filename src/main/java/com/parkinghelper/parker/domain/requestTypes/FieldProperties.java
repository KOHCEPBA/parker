package com.parkinghelper.parker.domain.requestTypes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

public class FieldProperties {
    public FieldProperties() {
    }

    @Getter
    @JsonIgnore
    static private String symbol = "%";

    public void setSymbol(String symbol) {
        if (symbol != null) FieldProperties.symbol = symbol;
    }

    @Getter
    @Setter
    private String field;

    @Getter
    @Setter
    private Mode conformityMode;
    @Setter
    private String value;

    public String getValue() {
        switch (conformityMode){
            case any: return symbol;
            case startWith: return value + symbol;
            case finishedOn: return symbol + value;
            case contain: return symbol + value + symbol;
            default: return value;
        }
    }


    private enum Mode{
        equals,
        startWith,
        finishedOn,
        contain,
        any
    }

    static public void copyFields(Object target,Iterable<FieldProperties> fieldProperties) throws NoSuchFieldException {
        Class clazz = target.getClass();

        //Copy fields to target
        for (FieldProperties fieldProp: fieldProperties
                ) {
            Field field = clazz.getDeclaredField(fieldProp.getField());
            field.setAccessible(true);
            try {
                if (field.getType() == String.class)
                    field.set(target, fieldProp.getValue());
                if (field.getType() == Integer.class)
                    field.set(target, Integer.parseInt(fieldProp.getValue()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        //Set symbol "any" to empty fields
        for (Field field: clazz.getDeclaredFields()
                ){
            field.setAccessible(true);
            try {
                if (field.get(target) == null)
                    if (field.getType() == String.class) field.set(target, FieldProperties.symbol);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
