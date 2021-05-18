package core.model;

import lombok.*;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Trainer extends BaseEntity<Long>{
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private int age;
}
