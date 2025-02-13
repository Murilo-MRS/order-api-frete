package com.template.project.models.entities;

import com.template.project.models.enums.DeliveryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
public class Delivery {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String description;

  private LocalDateTime deliveryDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private DeliveryStatus status = DeliveryStatus.PENDING;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public Delivery() {
  }

  public Delivery(
      Long id,
      String description,
      LocalDateTime deliveryDate,
      DeliveryStatus status,
      User user
  ) {
    this.id = id;
    this.description = description;
    this.deliveryDate = deliveryDate;
    this.status = status;
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public LocalDateTime getDeliveryDate() {
    return deliveryDate;
  }

  public void setDeliveryDate(LocalDateTime deliveryDate) {
    this.deliveryDate = deliveryDate;
  }

  public DeliveryStatus getStatus() {
    return status;
  }

  public void setStatus(DeliveryStatus status) {
    this.status = status;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
