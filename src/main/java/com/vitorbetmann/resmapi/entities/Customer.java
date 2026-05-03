package com.vitorbetmann.resmapi.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// JPA
@Entity
@DiscriminatorValue("CUSTOMER")
// Lombok
@NoArgsConstructor
@Getter
@Setter
public class Customer extends User {
    public String getType() {
        return "CUSTOMER";
    }
}
