This is a TeamCity Cloud Plugin that provides integration with Digital Ocean and allows running build agents in Cloud.

Compatible with TC9 and TC10

Build plugin
===============================
The easiest way is to build plugin with Gradle

<pre>
git clone https://github.com/graf/digitalocean-teamcity-plugin.git
cd digitalocean-teamcity-plugin
./gradlew build
cd plugin-digitalocean-server/build/distributions
</pre>

Install and setup plugin on the server
=============================
- Shutdown TeamCity
- Copy digitalocean-cloud.zip to the folder $TEAM_CITY_HOME/plugins
- Start TeamCity again
- Go to Server Administration - Agent Cloud at the left menu
- Click Create new profile
 * Specify build agent idle time
 * Select Digital Ocean Cloud
 * Enter you API key
 * Enter Image name, use your snapshot name in DigitalOcean
 * Enter ssh key name that you want to install on the client
 * Select region where you instance will be running (For now check manually that snapshot is available in this zone)
 * Select size of your future instance
 * Click Save and make sure that there are no errors
- For the first time client created TeamCity tries to create an instance in cloud, so verify that everything is OK and new instance has actually appeared in your Digital Ocean's account.

Install and configure plugin on the client
============================================
- Setup your testing environment and install build agent as described in TeamCity's documentation
- Edit file $BUILD_AGENT_INSTALL_PATH/conf/buildAgent.properties
 * Find digitalocean-cloud.image.id property and set it to the specific image (aka DigitalOcean's snapshot) that you gonna use. Use name of your snapshot as Image Id.
 * Setup URL to your TeamCity server
 * Make sure that authorization_token is not set
 * Setup any other properties as you want
- Run buildAgent and wait until it connects to the server. During the first run it may download a plenty of plugins from TeamCity server.
- When you are sure that agent and instance are configured properly, shutdown agent and instance.
- Edit buildAgent.properties and remove clean-up authorization_token and name
- Shutdown instance and run it again to ferify that agent run automatically with system start
- Edit buildAgent.properties and clean-up authorization_token and name. Do not forget check it
- Shutdown instance
- Make snapshot and name it the same title as digitalocean-cloud.image.id is
You have just set up an cloud agent, have fun!

License
=======
You may do what ever you like with those sources. 
Or I could also say the license is MIT.
