package com.Reboot.Minty.categories.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name="top_categories")
@Getter
@Transactional(readOnly = true)
public class TopCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
}
