package com.pg.nuclei.util;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Requests {
    public enum MatchersCondition {
        and, or
    }

    public enum AttackType {
        batteringram, pitchfork, clusterbomb
    }

    @YamlProperty("matchers-condition")
    private MatchersCondition matchersCondition;
    @YamlProperty
    private List<String> raw;
    @YamlProperty
    private AttackType attack;
    @YamlProperty
    private Map<String, List<String>> payloads;
    @YamlProperty
    private List<TemplateMatcher> matchers;

    public List<String> getRaw() {
        return this.raw;
    }

    public void addRaw(String... raw) {
        if (this.raw == null) {
            setRaw(raw);
        } else {
            this.raw.addAll(normalizeRawRequest(Arrays.stream(raw)));
        }
    }

    public void setRaw(String... raw) {
        // needed otherwise the dumped raw request will be shown in-line, between double quotes
        this.raw = normalizeRawRequest(Arrays.stream(raw));
    }

    public void setRaw(byte[]... raw) {
        setRaw(Arrays.stream(raw).map(String::new).toArray(String[]::new));
    }

    public List<TemplateMatcher> getMatchers() {
        return this.matchers;
    }

    /**
     * For requests with payloads, use {@link #setTransformedRequest} instead
     */
    public void setMatchers(TemplateMatcher... matchers) {
        setMatchers(List.of(matchers));
    }

    public void setMatchers(Collection<TemplateMatcher> matchers) {
        this.matchers = new ArrayList<>(matchers);

        if (Objects.isNull(this.matchersCondition) && this.matchers.size() > 1) {
            this.matchersCondition = MatchersCondition.and;
        }
    }

    public void setMatchersCondition(MatchersCondition matchersCondition) {
        this.matchersCondition = matchersCondition;
    }

    public MatchersCondition getMatchersCondition() {
        return this.matchersCondition;
    }

    public AttackType getAttack() {
        return this.attack;
    }

    public Map<String, List<String>> getPayloads() {
        return this.payloads;
    }

    public void setTransformedRequest(TransformedRequest transformedRequest) {
        this.attack = transformedRequest.getAttackType();
        setRaw(transformedRequest.getRequest());
        this.payloads = transformedRequest.getParameters();
    }

    public void addPayloads(AttackType attackType, String key, String... payloads) {
        if (this.attack == null) {
            this.attack = attackType;
        } else if (this.attack != attackType) {
            throw new IllegalArgumentException("An attack type with an associated raw request was already set.");
        }

        if (payloads != null) {
            if (attackType == AttackType.batteringram) {
                this.payloads.values().stream().findFirst().ifPresentOrElse(v -> v.addAll(Arrays.asList(payloads)), () -> addPayloads(key, payloads));
            } else {
                addPayloads(key, payloads);
            }
        }
    }

    private void addPayloads(String key, String... payloads) {
        if (this.payloads == null) {
            this.payloads = new LinkedHashMap<>(Map.of(key, new ArrayList<>(List.of(payloads))));
        } else {
            this.payloads.merge(key, List.of(payloads), (v1, v2) -> {
                v1.addAll(v2);
                return v1;
            });
        }
    }

    private List<String> normalizeRawRequest(Stream<String> content) {
        return content.map(s -> s.replaceAll("\r", "")
                        .replaceAll("Host: [^\\n]+", "Host: {{Hostname}}"))
                .collect(Collectors.toList());
    }
}
