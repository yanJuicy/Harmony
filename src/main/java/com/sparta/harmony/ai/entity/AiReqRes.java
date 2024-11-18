package com.sparta.harmony.ai.entity;

import com.sparta.harmony.menu.entity.Menu;
import com.sparta.harmony.order.entity.Timestamped;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_ai_requests")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AiReqRes extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID aiReqResId;

    private String question;

    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Builder
    public AiReqRes(String question, String answer, Menu menu) {
        this.question = question;
        this.answer = answer;
        this.menu = menu;
    }
}
