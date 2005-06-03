/**
 * Copyright 2005 The Apache Software Foundation
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
package org.apache.commons.cli2.bug;

import junit.framework.TestCase;

import org.apache.commons.cli2.Group;
import org.apache.commons.cli2.OptionException;
import org.apache.commons.cli2.builder.ArgumentBuilder;
import org.apache.commons.cli2.builder.DefaultOptionBuilder;
import org.apache.commons.cli2.builder.GroupBuilder;
import org.apache.commons.cli2.commandline.Parser;

/**
 * The first is a loop in Parser.parse() if I set a non-declared option. This 
 * code goes into a loop in Parser.java method parse this �while� loop runs 
 * endless
 * 
 * @author Steve Alberty
 */
public class BugLoopingOptionLookAlikeTest extends TestCase {

    public void testLoopingOptionLookAlike() {
        final DefaultOptionBuilder obuilder = new DefaultOptionBuilder();
        final ArgumentBuilder abuilder = new ArgumentBuilder();
        final GroupBuilder gbuilder = new GroupBuilder();
        final Group options = gbuilder
            .withName("ant")
            .withOption(obuilder.withShortName("help").withDescription("print this message").create())
            .withOption(obuilder.withShortName("projecthelp").withDescription("print project help information").create())
            .withOption(abuilder.withName("target").create())
            .create();
        
        final Parser parser = new Parser();
        parser.setGroup(options);
        try {
            parser.parse(new String[] { "-abcdef",
                    "testfile.txt ", });
            fail("OptionException");
        } catch (OptionException e) {
            assertEquals("Unexpected -abcdef while processing ant",e.getMessage());
        }
    }
}
