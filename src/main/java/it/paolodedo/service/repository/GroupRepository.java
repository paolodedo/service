package it.paolodedo.service.repository;

import it.paolodedo.service.domain.Group;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends Neo4jRepository<Group, String> {
}
