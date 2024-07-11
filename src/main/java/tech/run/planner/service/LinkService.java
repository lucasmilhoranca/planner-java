package tech.run.planner.service;

import org.springframework.stereotype.Service;
import tech.run.planner.entity.Link;
import tech.run.planner.repository.LinkRepository;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {

    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public Link createLink(Link link) {
            return linkRepository.save(link);
    }

    public List<Link> getAllLinksFromTrip(UUID tripId) {
        return linkRepository.findByTripId(tripId);
    }
}
