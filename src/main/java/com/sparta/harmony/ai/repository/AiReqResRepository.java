package com.sparta.harmony.ai.repository;

import com.sparta.harmony.ai.entity.AiReqRes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AiReqResRepository extends JpaRepository<AiReqRes, UUID> {
}
