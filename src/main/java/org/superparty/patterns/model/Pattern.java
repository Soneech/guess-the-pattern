package org.superparty.patterns.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pattern")
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Pattern {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "file_name")
    private String fileName;

    private int guesses;
}
