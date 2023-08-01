package com.pg.nuclei.util;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@SuppressWarnings("unused")
@YamlPropertyOrder({"type", "part", "condition", "status"})
public class Status implements TemplateMatcher {
    @YamlProperty
    private final String type = Status.class.getSimpleName().toLowerCase();

    @YamlProperty
    private List<Integer> status;

    @YamlProperty
    private Condition condition;

    public Status() {
    }

    public Status(Integer... status) {
        this.status = Arrays.asList(status);
    }

    public List<Integer> getStatus() {
        return this.status;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Nullable
    @Override
    public Part getPart() {
        return null; // the part is ignored by the status matcher
    }

    @Override
    public void setPart(Part part) {
        // do nothing
    }

    @Override
    public Condition getCondition() {
        return Optional.ofNullable(this.condition).orElse(Condition.or);
    }

    @Override
    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
