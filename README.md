# Flights System
This repository is for a project from CSC370: Databases course taught at Uvic during the spring term of 2015. 

# To get setup
1. Get a fresh VM up and running. 
2. Open up a terminal and get git
```
sudo yum makecache fast
sudo yum install git
```
3. Checkout the repo
```
git clone https://github.com/jordan-heemskerk/FlightsSystem
```
4. Create the database connections and empty tables
```
cd FlightsSystem
./scripts/init.sh
```
5. Startup tomcat
```
apache-tomcat-6.0.18/bin/startup.sh
```
6. Build and deploy our files
```
make deploy
```
7. If you make changes, to see their effects, just deploy again
```
make deploy
```
