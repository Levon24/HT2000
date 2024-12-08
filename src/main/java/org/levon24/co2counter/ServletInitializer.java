package org.levon24.co2counter;

/*
 * User: levon
 * Date: 15.04.2022
 * Time: 12:22
 */

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(Co2counterApplication.class);
  }
}
