/**
 * 
 */
package org.mycompany.connector;

import java.util.ArrayList;
import java.util.List;

import org.bonitasoft.engine.api.IdentityAPI;
import org.bonitasoft.engine.connector.ConnectorValidationException;
import org.bonitasoft.engine.exception.SearchException;
import org.bonitasoft.engine.filter.UserFilterException;
import org.bonitasoft.engine.identity.Role;
import org.bonitasoft.engine.identity.RoleNotFoundException;
import org.bonitasoft.engine.identity.User;
import org.bonitasoft.engine.identity.UserSearchDescriptor;
import org.bonitasoft.engine.search.SearchOptionsBuilder;
import org.bonitasoft.engine.search.SearchResult;

/**
 * The actor filter execution will follow the steps 1 - setInputParameters() -->
 * the actor filter receives input parameters values 2 -
 * validateInputParameters() --> the actor filter can validate input parameters
 * values 3 - filter(final String actorName) --> execute the user filter 4 -
 * shouldAutoAssignTaskIfSingleResult() --> auto-assign the task if filter
 * returns a single result
 */
public class ManagersOfGroupsImpl extends AbstractManagersOfGroupsImpl {

	/** Name of the manager role as defined in Bonita organization. */
	private static final String MANAGER = "Manager";

	@Override
	public void validateInputParameters() throws ConnectorValidationException {
		// At least one group id should be provided
		if (getGroupIds().isEmpty()) {
			throw new ConnectorValidationException("Provided list of group ids is empty");
		}
	}

	@Override
	public List<Long> filter(final String actorName) throws UserFilterException {
		// List of IDs of users that are manager in the groups associated with provided
		// IDs
		List<Long> userIdsList = new ArrayList<>();

		IdentityAPI identityAPI = getAPIAccessor().getIdentityAPI();

		try {
			// Get all information about manager role (including role id)
			Role managerRole = identityAPI.getRoleByName(MANAGER);

			// Get the actor filter input information
			List<Long> groupIds = getGroupIds();

			// Iterate over all provided groups id
			for (Long groupId : groupIds) {

				// Build a search query to find users member of a group with a given role .
				// Note that we have a limit of number of manager for one role arbitrarily set
				// to 100.
				final SearchOptionsBuilder builder = new SearchOptionsBuilder(0, 100);
				// ID of the group from the actor filter input
				builder.filter(UserSearchDescriptor.GROUP_ID, groupId);
				// ID of the manager role
				builder.filter(UserSearchDescriptor.ROLE_ID, managerRole.getId());
				// Build the search query and run it
				final SearchResult<User> usersSearchResult = identityAPI.searchUsers(builder.done());

				// Get the list of users matching the search query
				List<User> users = usersSearchResult.getResult();
				// Iterate over all users that are manager in the current group (most likely
				// only one user)
				for (User user : users) {
					// Get the user id from the user object and add it to the list
					userIdsList.add(user.getId());
				}
			}
		} catch (RoleNotFoundException e) {
			throw new UserFilterException("Manager role not found", e);
		} catch (SearchException e) {
			throw new UserFilterException("Error while searching for user", e);
		}

		return userIdsList;
	}

}
