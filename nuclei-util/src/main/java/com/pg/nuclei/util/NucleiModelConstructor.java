package com.pg.nuclei.util;

import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;

import java.util.List;
import java.util.stream.Stream;

class NucleiModelConstructor extends Constructor {

    @Override
    protected Object newInstance(Node node) {
        if (node.getType() == TemplateMatcher.class) {
            try {
                final List<NodeTuple> templateMatcherNodeValues = ((MappingNode) node).getValue();

                for (NodeTuple templateMatcherNodeValue : templateMatcherNodeValues) {
                    final String attributeName = ((ScalarNode) templateMatcherNodeValue.getKeyNode()).getValue();
                    if (TemplateMatcher.TYPE_FIELD_NAME.equalsIgnoreCase(attributeName)) {
                        final ScalarNode templateMatcherScalarNodeValue = (ScalarNode) templateMatcherNodeValue.getValueNode();
                        final String attributeValue = templateMatcherScalarNodeValue.getValue();

                        // it would only worth generalizing this, if there would be multiple deserializable interface types
                        Stream.of(Word.class, Status.class, Binary.class)
                                .filter(templateMatcherClass -> templateMatcherClass.getSimpleName().equalsIgnoreCase(attributeValue))
                                .findFirst()
                                .map(templateMatcherClass -> {
                                    node.setType(templateMatcherClass);
                                    return templateMatcherClass;
                                })
                                .orElseThrow(() -> new YAMLException(String.format("Instantiation of the '%s' TemplateMatcher not yet implemented!", attributeValue)));
                        break; // the type has been found, we can break from the for
                    }
                }
            } catch (final ClassCastException e) {

                throw new YAMLException("Could not instantiate TemplateMatcher");
            }
        }

        return super.newInstance(node);
    }
}