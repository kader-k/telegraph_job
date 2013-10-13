Feature: User Manager
As a part of web and mobile team
I want to be able to manage users in the system
So that I can administrate the users of the system

Scenario: Create a new Subscriber
Given that the "newsubscriber@tmg.com" user does not exist
When data set is:
|type        |firstName    |lastName    |title    |dateOfBirth    |email                  |password |home_houseNum  |home_postcode |billing_houseNum |billing_postcode |
|Subscriber  |firstName    |lastname    |mr       |1990-01-01     |newsubscriber@tmg.com  |password |10             |MK100AB       |10               |MK100AB          |
And I call api register "/register"
Then I should get response status 201


Scenario: Create a new User
Given that the "newuser@tmg.com" user does not exist
And "superuser@tmg.com" exists with password "super"
When username is "superuser@tmg.com"
And password is "super"
And data set is: 
|type        |firstName    |lastName    |title    |dateOfBirth    |email            |password |
|User        |firstName    |lastname    |mr       |1990-01-01     |newuser@tmg.com  |password |
And I call api register "/register"
Then I should get response status 201

Scenario: Retrieve my details as Subscriber
Given that the "subscriber@tmg.com" user exist
When username is "subscriber@tmg.com" 
And password is "subscriber"
And adminUsername is ""
And I call api mydetails "/mydetails" 
Then I should get response status 200


Scenario: Retrieve details of Subscriber as User
Given that the "subscriber@tmg.com" user exist
And that the "user@tmg.com" user exist
When username is "subscriber@tmg.com" 
And password is "user"
And adminUsername is "user@tmg.com"
And I call api mydetails "/mydetails" 
Then I should get response status 200

Scenario: Retrieve details of Subscriber as Super User
Given that the "subscriber@tmg.com" user exist
And that the "superuser@tmg.com" user exist
When username is "subscriber@tmg.com" 
And password is "super"
And adminUsername is "superuser@tmg.com"
And I call api mydetails "/mydetails" 
Then I should get response status 200

Scenario: Retrieve details of User as Super User
Given that the "user@tmg.com" user exist
When username is "user@tmg.com" 
And password is "super"
And adminUsername is "superuser@tmg.com"
And I call api mydetails "/mydetails" 
Then I should get response status 200

Scenario: Update Subscriber details as Super User
Given that the "subscriber@tmg.com" user exist
And that the "superuser@tmg.com" user exist
When password is "super"
And adminUsername is "superuser@tmg.com"
And data set is:
|type        |firstName |lastName    |title    |dateOfBirth    |email               |password |home_houseNum  |home_postcode |billing_houseNum |billing_postcode |
|Subscriber  |update    |lastname    |mr       |1990-01-01     |subscriber@tmg.com  |password |10             |MK100AB       |10               |MK100AB          |
And I call api update "/update" 
Then I should get response status 200


Scenario: Delete a Subscriber as Super User
Given that the "subscriber@tmg.com" user exist
And that the "superuser@tmg.com" user exist
When username is "subscriber@tmg.com" 
And password is "super"
And adminUsername is "superuser@tmg.com"
And I call api delete "/delete" 
Then I should get response status 200



