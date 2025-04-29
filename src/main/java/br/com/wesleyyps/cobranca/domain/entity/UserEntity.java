package br.com.wesleyyps.cobranca.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"})
})
@EqualsAndHashCode(callSuper=false)
public class UserEntity extends BaseEntity {
    @Column
    protected Boolean enabled;
    @Column
    protected String name;
    @Column
    protected String email;
}
