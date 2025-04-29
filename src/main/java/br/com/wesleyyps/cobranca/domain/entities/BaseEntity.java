package br.com.wesleyyps.cobranca.domain.entities;

import java.sql.Timestamp;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "char(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    protected UUID id;
    @Column(updatable = false)
    @CreationTimestamp
    protected Timestamp createdAt;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    protected UserEntity createdBy;
    @UpdateTimestamp
    protected Timestamp updatedAt;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    protected UserEntity updatedBy;
}
