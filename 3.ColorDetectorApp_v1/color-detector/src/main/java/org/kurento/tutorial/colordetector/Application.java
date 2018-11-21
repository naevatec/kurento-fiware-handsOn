/*
 * Copyright 2018 Kurento (https://www.kurento.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kurento.tutorial.colordetector;

import org.kurento.client.KurentoClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Color Detector (main).
 *
 * @author Guiomar Tuñón (guiomar.tunon@gmail.com)
 * @since 6.8.0
 */
@SpringBootApplication
@EnableWebSocket
public class Application implements WebSocketConfigurer {

  @Bean
  public ColorDetectorHandler handler() {
	return new ColorDetectorHandler();
  }

  @Bean
  public KurentoClient kurentoClient() {
	return KurentoClient.create();
  }

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
	registry.addHandler(handler(), "/colordetector");
  }

  public static void main(String[] args) throws Exception {
	SpringApplication.run(Application.class, args);
  }
}
