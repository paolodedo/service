package it.paolodedo.service.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.LinkedHashSet;
import java.util.Set;


@Node
@Getter
@Setter
public class Device {

    @Id
    private Long id;

    /* Neo4j field to implement optimistic locking on entities.
    This attribute will get incremented automatically
    during updates and must not be manually modified. */
    @Version
    @JsonIgnore
    private Long version;

    private String name;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @Relationship(type = "BELONGS_TO", direction = Relationship.Direction.OUTGOING)
    private Set<Group> groups = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Device device = (Device) o;

        return id.equals(device.id);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Device{" +
            "id=" + id +
            ", version=" + version +
            ", name='" + name + '\'' +
            '}';
    }
}
