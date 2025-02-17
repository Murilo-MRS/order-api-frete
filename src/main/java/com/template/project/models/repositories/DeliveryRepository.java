package com.template.project.models.repositories;

import com.template.project.models.entities.Delivery;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
  List<Delivery> findByUserId(Long userId);
}
