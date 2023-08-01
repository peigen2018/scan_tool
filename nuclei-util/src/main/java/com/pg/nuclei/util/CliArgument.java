package com.pg.nuclei.util;

public class CliArgument {
    private final String shortName;
    private final String longName;
    private final String description;

    public CliArgument(String shortName, String description) {
        this(shortName, null, description);
    }

    public CliArgument(String shortName, String longName, String description) {
        this.shortName = shortName;
        this.longName = longName;
        this.description = description;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder(this.shortName);

        if (this.longName != null) {
            result.append(", ").append(this.longName);
        }

        result.append(' ').append(this.description);

        return result.toString();
    }

    public String getShortName() {
        return this.shortName;
    }

    public String getLongName() {
        return this.longName;
    }

    public String getDescription() {
        return this.description;
    }
}
