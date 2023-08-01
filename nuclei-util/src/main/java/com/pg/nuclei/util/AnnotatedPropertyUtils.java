package com.pg.nuclei.util;

import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

public class AnnotatedPropertyUtils extends PropertyUtils {

    @Override
    protected Map<String, Property> getPropertiesMap(Class<?> type, BeanAccess beanAccess) {
        final Map<String, Property> properties = new LinkedHashMap<>();

        if (beanAccess == BeanAccess.FIELD) {
            return super.getPropertiesMap(type, BeanAccess.FIELD);
        } else {
            for (Class<?> currentClass = type; currentClass != null; currentClass = currentClass.getSuperclass()) {
                for (Field field : currentClass.getDeclaredFields()) {
                    final int modifiers = field.getModifiers();
                    if (!Modifier.isStatic(modifiers) && !Modifier.isTransient(modifiers)) {
                        final YamlProperty yamlPropertyAnnotation = field.getAnnotation(YamlProperty.class);
                        if (yamlPropertyAnnotation != null) {
                            final String yamlPropertyName = yamlPropertyAnnotation.value();
                            if (yamlPropertyName.isEmpty()) {
                                final String transformedFieldName = toSnakeCase(field.getName());
                                properties.put(transformedFieldName, new CustomNameFieldProperty(transformedFieldName, field));
                            } else {
                                properties.put(yamlPropertyName, new CustomNameFieldProperty(yamlPropertyName, field));
                            }
                        }
                    }
                }
            }
        }

        if (properties.isEmpty()) {
            throw new YAMLException(String.format("No JavaBean properties found in '%s'.", type.getName()));
        }
        return properties;
    }

    private static String toSnakeCase(String input) {
        return input.replaceAll("([a-z]+)([A-Z]+)", "$1-$2").toLowerCase();
    }
}
