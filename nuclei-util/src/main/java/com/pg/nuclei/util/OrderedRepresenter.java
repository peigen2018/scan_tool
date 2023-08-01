package com.pg.nuclei.util;

import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.util.*;
import java.util.stream.Collectors;

public class OrderedRepresenter extends Representer {
    private final PropertyUtils propertyUtils;

    OrderedRepresenter(PropertyUtils propertyUtils) {
        this.propertyUtils = propertyUtils;
    }

    @Nullable
    @Override
    protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
        if (Objects.isNull(propertyValue)) {
            return null; // skip fields with null value
        }
        return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
    }

    @Override
    protected MappingNode representJavaBean(Set<Property> properties, Object javaBean) {
        if (!this.classTags.containsKey(javaBean.getClass())) {
            addClassTag(javaBean.getClass(), Tag.MAP); // remove class tags (e.g. !!packageName.ClassName)
        }

        return super.representJavaBean(properties, javaBean);
    }

    @Override
    protected Set<Property> getProperties(Class<?> type) {
        Set<Property> propertySet = this.typeDefinitions.containsKey(type) ? this.typeDefinitions.get(type).getProperties()
                : this.propertyUtils.getProperties(type);

        final YamlPropertyOrder annotation = type.getAnnotation(YamlPropertyOrder.class);
        if (annotation != null) {
            final List<String> order = Arrays.asList(annotation.value());
            propertySet = propertySet.stream()
                    .sorted(Comparator.comparingInt(o -> order.indexOf(o.getName())))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }

        return propertySet;
    }
}
