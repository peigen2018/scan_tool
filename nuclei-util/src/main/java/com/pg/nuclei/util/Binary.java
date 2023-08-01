package com.pg.nuclei.util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@YamlPropertyOrder({"type", "part", "condition", "binary"})
public class Binary implements TemplateMatcher{
    @YamlProperty
    private final String type = Binary.class.getSimpleName().toLowerCase();

    @YamlProperty
    private List<String> binary;

    @YamlProperty
    private Part part = Part.body;

    @YamlProperty
    private Condition condition;

    public Binary() {
    }

    public Binary(byte[]... binary) {
        this.binary = Stream.of(binary).map(Binary::toHex).collect(Collectors.toList());
    }

    public List<String> getBinary() {
        return this.binary;
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

    public static String toHex(byte[] input) {
        return IntStream.range(0, input.length).mapToObj(i -> String.format("%02x", input[i])).collect(Collectors.joining());
    }
}
