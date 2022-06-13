package com.project.finance.entities;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long tokenId;
    @OneToOne
    @JoinColumn(name = "client_token_id", referencedColumnName = "client_id")
    private Client client;
    @Column(name = "token")
    private String token;
    @Column(name = "expirytime")
    private Instant expiryDate;
}
