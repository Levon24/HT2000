package org.levon24.ht2000.services.db;

/*
 * User: levon
 * Date: 08.12.2024
 * Time: 19:35
 */

import org.levon24.ht2000.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
  private final EventRepository repository;

  @Autowired
  public EventService(EventRepository repository) {
    this.repository = repository;
  }


}
