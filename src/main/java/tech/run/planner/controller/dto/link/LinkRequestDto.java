package tech.run.planner.controller.dto.link;

import tech.run.planner.entity.Link;
import tech.run.planner.entity.Trip;

public record LinkRequestDto(String title, String url) {
    public Link toLink(Trip trip) {
        return new Link(
                url,
                title,
                trip
        );
    }
}
