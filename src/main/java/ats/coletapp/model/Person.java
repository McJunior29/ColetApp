package ats.coletapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @OneToOne
    private Address address;

    public Person(Long id) {
        this.id = id;
    }

    public String getFirtsName() {
        if (name != null) {
            return name.split(" ")[0];
        }
        return "";
    }

    public String getLastName() {
        if (name != null) {
            String[] separateNameBySpace = name.split(" ");

            return separateNameBySpace[separateNameBySpace.length - 1];
        }
        return "";
    }
}
