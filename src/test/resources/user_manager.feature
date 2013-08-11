Feature: User Manager
As a part of web and mobile team
I want to be able to manage users in the system
So that I can administrate the users of the system

Scenario: Create a new Subscriber
Given that the "newsubscriber@tmg.com" user does not exist

When I call api "http://localhost:8080/tmg/api/user/register" with:
|type			|firstName	|lastName	|title	|dateOfBirth	|email					|password	|home_houseNum	|home_postcode	|billing_houseNum	|billing_postcode	|
|Subscriber	|firstName|lastname	|mr|1990-01-01	|newsubscriber@tmg.com|password	|10			|MK100AB		|10				|MK100AB			|
Then I should get created status 201
