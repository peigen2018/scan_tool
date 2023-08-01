package com.pg.nuclei.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TransformedRequest {
    private final Requests.AttackType attackType;
    private final String request;
    private final Map<String, List<String>> parameters;

    public TransformedRequest(Requests.AttackType attackType, String request, Map<String, List<String>> parameters) {
        this.attackType = attackType;
        this.request = request;
        this.parameters = new LinkedHashMap<>(parameters);
    }

    public Requests.AttackType getAttackType() {
        return this.attackType;
    }

    public String getRequest() {
        return this.request;
    }

    public Map<String, List<String>> getParameters() {
        return this.parameters;
    }
}
