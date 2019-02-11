# Example of custom actor filter

*Note: this Git repository includes the content of a Bonita repository. Easiest way to get it in your Studio is to download the [latest release](./release/latest) and import it in Bonita Studio.*

This example provides a custom [actor filter](https://documentation.bonitasoft.com/bonita/7.8/creating-an-actor-filter).

The actor filter require a single inputs: a list of Bonita organization [groups](https://documentation.bonitasoft.com/bonita/7.8/group) IDs (data type: java.util.List<Long>).

Actor filter call the identity API to retrieve all [users](https://documentation.bonitasoft.com/bonita/7.8/manage-a-user) that are members of one of the group with the role "manager".
The name of the role ("manager") is hard coded in this actor filter.
Note that the default Bonita test [organization](https://documentation.bonitasoft.com/bonita/7.8/organization-management-in-bonita-bpm-studio) only declare one role named: member.

The repository includes:
* Actor filter definition (.def, .properties and AbstractManagersOfGroupsImpl.java)
* Actor filter implementation (.impl and ManagersOfGroupsImpl.java)
* An example of process that use the actor filter. Actor filter is configured on the process lane. The single task of the process inherit from the lane configuration. List of selected groups is stored in a business variable that is used as actor filter input.
* A Business Data Model with the definition of a single object used to store the id of groups selected by the user in the instantiation form.
* An instantiation form that display the list of groups deployed and checkbox to select the group to use when searching for user with role manager.
* A test organization based on Bonita default test organization with addition of the manager role and extra membership for walter.bates and helen.kelly users.