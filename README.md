[![Build Status](https://travis-ci.org/dat3startcode/rest-jpa-devops-startcode.svg?branch=master)](https://travis-ci.org/dat3startcode/rest-jpa-devops-startcode)

### Preconditions
*In order to use this code, you should have a local developer setup + a "matching" droplet on Digital Ocean* 
# Getting Started
* Clone this project

* Delete the .git folder and Do "Your own" git init

* Create your OWN repository for this project on github

* Commit and Push your code to this repository

* Go to Travis-ci.com and sign in with GitHub

* Click the green activate button, and select the the new repository to be used with Travis CI

* Create two local databases (on your vagrant image) named exactly (exactly is only for this proof of concept) as below:

  startcode
  startcode_test
  
* Create a REMOTE database on your Droplet named exacly like this: startcode

* In a terminal (git bash for Windows Users) in the root of the project type: mvn test

Hopefully the previous step was a success, if not, fix the problem(s)
### Deploying to droplet via Travis CI
Remember to change the <remote.server> property in pom.xml to match the IP/domain name of your droplet. 
Also, don't forget to create environment variables on Travis CI: 

* Login to travis using Github, and select your project on the dashboard

* Click "More options" and select "settings"

* Create two Environment Variables with names and values as sketched below (must be done in two steps);

  REMOTE_PW : *Your value for the script_user password*<br>
  REMOTE_USER : *script_user*
  
### Fetching data from servers
The template for fetching data from multiple servers is created in the ServerFacade class. In this example, data is only fetched from a single API, and this should eventually be changed by adding a list of different hosts and looping through this instead.
### JWT signature
A fixed secret key is displayed in plain text in security.SharedSecret. This should be removed, and eventually be read as an environment variable on the remote production server.



