package com.pg.nuclei.util;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.representer.Representer;

public class YamlUtil {
    private static final Yaml YAML_INSTANCE = createYamlInstance();

    private YamlUtil() {
    }

    public static String dump(Object data) {
        return YAML_INSTANCE.dumpAsMap(data);
    }

    public static <T> T load(String data, Class<T> modelClass) {
        return YAML_INSTANCE.loadAs(data, modelClass);
    }

    private static Yaml createYamlInstance() {
        final PropertyUtils propertyUtils = new AnnotatedPropertyUtils();

        final BaseConstructor baseConstructor = new NucleiModelConstructor();
        baseConstructor.setPropertyUtils(propertyUtils);

        final Representer representer = new OrderedRepresenter(propertyUtils);
        final DumperOptions options = createDumperOptions();
        final LoaderOptions loaderOptions = new LoaderOptions();

        return new Yaml(baseConstructor, representer, options, loaderOptions);
//        return new Yaml();
    }

    private static DumperOptions createDumperOptions() {
        final DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setIndicatorIndent(2);
        options.setIndentWithIndicator(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        return options;
    }
}
