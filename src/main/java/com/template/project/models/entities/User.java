package com.template.project.models.entities;

import com.template.project.models.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @Column(unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @OneToMany(
      mappedBy = "user",
      cascade = CascadeType.ALL
  )
  private List<Delivery> deliveries = new ArrayList<>();

  public User() {
  }

  public User(Long id, String name, String email, String password, Role role) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public List<Delivery> getDeliveries() {
    return deliveries;
  }
  public Delivery addDelivery(Delivery delivery) {
    deliveries.add(delivery);
    delivery.setUser(this);
    return delivery;
  }

  public Delivery removeDelivery(Delivery delivery) {
    deliveries.remove(delivery);
    delivery.setUser(null);
    return delivery;
  }
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.toString()));
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserDetails.super.isAccountNonLocked();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return UserDetails.super.isEnabled();
  }
}
