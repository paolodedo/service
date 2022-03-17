package it.paolodedo.service.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import it.paolodedo.service.utils.UUIDStringGenerator;
import lombok.Getter;
import lombok.Setter;
import org.neo4j.driver.internal.shaded.reactor.util.annotation.NonNull;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.LinkedHashSet;
import java.util.Set;

@Node
@Getter
@Setter
public class Group {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;

    /* Neo4j field to implement optimistic locking on entities.
    This attribute will get incremented automatically
    during updates and must not be manually modified. */
    @Version
    @JsonIgnore
    private Long version;

    @NonNull
    private String name;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @Relationship(type = "BELONGS_TO", direction = Relationship.Direction.INCOMING)
    private Set<Device> devices = new LinkedHashSet<>();

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @Relationship(type = "GROUP_LINK")
    private Set<Group> groups = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (!id.equals(group.id)) return false;
        return name.equals(group.name);
    }

    @Override
    public int hashCode() {
        int result = 7;
        result = 31 * result + id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Group{" +
            "id='" + id + '\'' +
            ", version=" + version +
            ", name='" + name + '\'' +
            '}';
    }
}
