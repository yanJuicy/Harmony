package com.sparta.harmony.menu.repository;

import com.sparta.harmony.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {
}
