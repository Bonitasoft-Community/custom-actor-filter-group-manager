/**
 * 
 */
package org.mycompany.connector;

import org.bonitasoft.engine.filter.AbstractUserFilter;
import org.bonitasoft.engine.connector.ConnectorValidationException;

/**
 * This abstract class is generated and should not be modified.
 */
public abstract class AbstractManagersOfGroupsImpl extends AbstractUserFilter {

	protected final static String GROUPIDS_INPUT_PARAMETER = "groupIds";

	@SuppressWarnings("unchecked")
	protected final java.util.List<Long> getGroupIds() {
		return (java.util.List<Long>) getInputParameter(GROUPIDS_INPUT_PARAMETER);
	}

	@Override
	public void validateInputParameters() throws ConnectorValidationException {
		try {
			getGroupIds();
		} catch (ClassCastException cce) {
			throw new ConnectorValidationException("groupIds type is invalid");
		}

	}

}
