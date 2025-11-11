package tsg.consuming_rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Fact(String id, String text, String source, String source_url, String language) {
}
