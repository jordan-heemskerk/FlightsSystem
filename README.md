# Flights System
This repository is for a project from CSC370: Databases course taught at Uvic during the spring term of 2015. 

# To get setup
Get a fresh VM up and running. 
Open up a terminal and get git
```
sudo yum makecache fast
sudo yum install git
```
Checkout the repo
```
git clone https://github.com/jordan-heemskerk/FlightsSystem
```
Create the database connections and empty tables
```
cd FlightsSystem
./scripts/init.sh
```
Startup tomcat
```
apache-tomcat-6.0.18/bin/startup.sh
```
Build and deploy our files
```
make deploy
```
If you make changes, to see their effects, just deploy again
```
make deploy
```
