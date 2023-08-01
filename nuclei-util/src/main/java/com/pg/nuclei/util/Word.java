package com.pg.nuclei.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@SuppressWarnings("unused")
@YamlPropertyOrder({"type", "part", "condition", "words"})
public class Word implements TemplateMatcher {
    @YamlProperty
    private final String type = Word.class.getSimpleName().toLowerCase();

    @YamlProperty
    private Part part = Part.all;

    @YamlProperty
    private List<String> words;

    @YamlProperty
    private Condition condition;

    public Word() {
    }

    public Word(String... words) {
        this.words = Arrays.asList(words);
    }

    public List<String> getWords() {
        return this.words;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public Part getPart() {
        return this.part;
    }

    @Override
    public void setPart(Part part) {
        this.part = part;
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
