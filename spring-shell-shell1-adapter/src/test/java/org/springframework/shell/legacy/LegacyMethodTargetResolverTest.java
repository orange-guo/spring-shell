/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.shell.legacy;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.MapEntry.entry;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.MethodTarget;
import org.springframework.shell.MethodTargetResolver;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by ericbottard on 09/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LegacyMethodTargetResolverTest.Config.class)
public class LegacyMethodTargetResolverTest {

	@Autowired
	private LegacyCommands legacyCommands;

	@Autowired
	private MethodTargetResolver resolver;

	@Test
	public void findsMethodsAnnotatedWithCliCommand() throws Exception {
		Map<String, MethodTarget> targets = resolver.resolve();

		assertThat(targets).contains(entry(
				"register module",
				new MethodTarget(LegacyCommands.REGISTER_METHOD, legacyCommands, "Register a new module" )
		));
	}

	@Configuration
	static class Config {

		@Bean
		public LegacyCommands legacyCommands() {
			return new LegacyCommands();
		}

		@Bean
		public MethodTargetResolver methodTargetResolver() {
			return new LegacyMethodTargetResolver();
		}
	}

}