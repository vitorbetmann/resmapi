package com.vitorbetmann.resmapi.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// JPA
@Entity
@DiscriminatorValue("OWNER")
// Lombok
@NoArgsConstructor
@Getter
@Setter
public class Owner extends User {
    public String getType() {
        return "OWNER";
    }
}
