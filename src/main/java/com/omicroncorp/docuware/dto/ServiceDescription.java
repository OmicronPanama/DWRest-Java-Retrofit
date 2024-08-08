package com.omicroncorp.docuware.dto;

import java.util.List;
import java.util.Objects;

public final class ServiceDescription {
    private String Token;
    private final List<Link> Links;
    private final Tests Test;
    private final Documentation Documentation;
    private final List<Resource> Resources;
    private final Statistics Statistics;
    private final String Version;

    public ServiceDescription(List<Link> Links, Tests Test, Documentation Documentation, List<Resource> Resources, Statistics Statistics, String Version) {
        this.Links = Links;
        this.Test = Test;
        this.Documentation = Documentation;
        this.Resources = Resources;
        this.Statistics = Statistics;
        this.Version = Version;
    }

    public String Token() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public List<Link> Links() {
        return Links;
    }

    public Tests Test() {
        return Test;
    }

    public Documentation Documentation() {
        return Documentation;
    }

    public List<Resource> Resources() {
        return Resources;
    }

    public Statistics Statistics() {
        return Statistics;
    }

    public String Version() {
        return Version;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ServiceDescription) obj;
        return Objects.equals(this.Token, that.Token) &&
               Objects.equals(this.Links, that.Links) &&
               Objects.equals(this.Test, that.Test) &&
               Objects.equals(this.Documentation, that.Documentation) &&
               Objects.equals(this.Resources, that.Resources) &&
               Objects.equals(this.Statistics, that.Statistics) &&
               Objects.equals(this.Version, that.Version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Token, Links, Test, Documentation, Resources, Statistics, Version);
    }

    @Override
    public String toString() {
        return "ServiceDescription[" +
               "Token=" + Token + ", " +
               "Links=" + Links + ", " +
               "Test=" + Test + ", " +
               "Documentation=" + Documentation + ", " +
               "Resources=" + Resources + ", " +
               "Statistics=" + Statistics + ", " +
               "Version=" + Version + ']';
    }

}
