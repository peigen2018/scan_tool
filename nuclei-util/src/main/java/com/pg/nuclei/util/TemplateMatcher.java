package com.pg.nuclei.util;

import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public interface TemplateMatcher {

    String TYPE_FIELD_NAME = "type";

    enum Part {
        header, body, all
    }

    enum Condition {
        and, or
    }

    String getType();

    @Nullable
    Part getPart();

    void setPart(Part part);

    Condition getCondition();

    void setCondition(Condition condition);
}