package com.pg.nuclei.util;

import org.yaml.snakeyaml.introspector.FieldProperty;
import org.yaml.snakeyaml.introspector.GenericProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public class CustomNameFieldProperty extends GenericProperty {
    private final FieldProperty fieldProperty;

    CustomNameFieldProperty(String name, Field field) {
        super(name, field.getType(), field.getGenericType());
        this.fieldProperty = new FieldProperty(field);
    }

    @Override
    public void set(Object object, Object value) throws Exception {
        this.fieldProperty.set(object, value);
    }

    @Override
    public Object get(Object object) {
        return this.fieldProperty.get(object);
    }

    @Override
    public List<Annotation> getAnnotations() {
        return this.fieldProperty.getAnnotations();
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return this.fieldProperty.getAnnotation(annotationType);
    }
}
