/**
 * Copyright 2003-2004 The Apache Software Foundation
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
package org.apache.commons.cli2.option;

import java.util.ListIterator;

import org.apache.commons.cli2.DisplaySetting;
import org.apache.commons.cli2.Option;

/**
 * A base implementation of Option providing limited ground work for further
 * Option implementations.
 */
public abstract class OptionImpl implements Option {

    private final int id;
    private final boolean required;

    /**
     * Creates an OptionImpl with the specified id
     * @param id the unique id of this Option
     * @param required true iff this Option must be present
     */
    public OptionImpl(final int id, final boolean required) {
        this.id = id;
        this.required = required;
    }

    public boolean canProcess(final ListIterator arguments) {
        if (arguments.hasNext()) {
            final String argument = (String)arguments.next();
            arguments.previous();
            return canProcess(argument);
        }
        else {
            return false;
        }
    }

    public String toString() {
        final StringBuffer buffer = new StringBuffer();
        appendUsage(buffer, DisplaySetting.ALL, null);
        return buffer.toString();
    }

    public int getId() {
        return id;
    }

    public boolean equals(final Object thatObj) {
        if (thatObj instanceof OptionImpl) {
            final OptionImpl that = (OptionImpl)thatObj;

            return getId() == that.getId()
                && getPreferredName().equals(that.getPreferredName())
                && (getDescription() == that.getDescription()
                    || getDescription().equals(that.getDescription()))
                && getPrefixes().equals(that.getPrefixes())
                && getTriggers().equals(that.getTriggers());
        }
        else {
            return false;
        }
    }

    public int hashCode() {
        int hashCode = getId();
        hashCode = hashCode * 37 + getPreferredName().hashCode();
        if (getDescription() != null) {
            hashCode = hashCode * 37 + getDescription().hashCode();
        }
        hashCode = hashCode * 37 + getPrefixes().hashCode();
        hashCode = hashCode * 37 + getTriggers().hashCode();
        return hashCode;
    }
    
	public Option findOption(String trigger) {
		if(getTriggers().contains(trigger)){
			return this;
		}
		else{
			return null;
		}
	}

    public boolean isRequired() {
        return required;
    }
}