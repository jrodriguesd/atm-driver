# jPOS based ATM driver

## Structure

This project has the following modules:
- `atm-driver`: It provides the connection with the atms and sends the authorization to an ISO switch or authorizer.
- `iso-server-simulator`: An ISO authorizer simulator that has hardcoded return parameters.
- `ndcplus`: An alternate atm driver implementation as provided by Alejandro Revilla <apr@jpos.org>, it implements the server using `java.nio`.

## Database

You can override the database parameters in a `local.porperties` file, the default configuration will use `devel.properties` values, but you can override any or all of them:

```properties
dbname=atm_driver
dbhost=localhost
dbport=3306
dbuser=jpos
dbpass=password
```

### Database initialization

#### Create database in MySQL
```sql
CREATE USER 'jpos'@'%' IDENTIFIED BY 'password';
CREATE USER 'jpos'@'localhost' IDENTIFIED BY 'password';
CREATE DATABASE atm_driver;
GRANT ALL PRIVILEGES ON atm_driver.* TO 'jpos'@'%' WITH GRANT OPTION; 
GRANT ALL PRIVILEGES ON atm_driver.* TO 'jpos'@'localhost' WITH GRANT OPTION;
```

#### Populate database

We use `flywaydb` to version the database, you can update the database structure and needed data, by performing:

```shell
gradle :modules:atm-driver:flywayInfo     # or fI for short, this will tell what migrations need to be applied or give an error if something wrong.
gradle :modules:atm-driver:flywayMigrate  # or fM for short, this applies the migrations
```

#### Implementation details

During building all terms of the form `@xxx@` in text files under `src/dist` will be replaced by `xxx` property value in `devel.properties` or the overriden value in `local.properties`.

You can pass `-Ptarget=yyy` to gradle command if you want properties to be loaded from `yyy.properties` file instead of `devel.properties`.

For instance in `db.propeties`:
```properties
hibernate.connection.username=@dbuser@
hibernate.connection.password=@dbpass@
...
hibernate.connection.url=jdbc:mysql://@dbhost@:@dbport@/@dbname@?autoReconnect=true
```

They are also used in flyway migrations in another way (see `modules/atm-driver/build.gradle`):
```groovy
flyway {

    def tcfg= targetConfiguration
    url= "jdbc:mysql://${tcfg.dbhost}:${tcfg.dbport}/${tcfg.dbname}"
    user= tcfg.dbuser
    password= tcfg.dbpass
```
Where `taretConfiguration` map is filled from the `target.properties` selected in `-Ptarget=...`

## Building
### Build an eclipse project
````
gradle eclipse
````

### Build an IDEA project
````
gradle idea
````

### Build your own jar
````
gradle jar
````

### Check the jPOS version
````
gradle version
````

### Create a distribution of your application
````
gradle dist
````
This creates a tar gzipped file in the `build/distributions` directory.

### Install application in 'build/install' directory
````
gradle installApp
````
Installs application in `build/install` with everything you need to run jPOS. Once the directory is created, you can `cd build/install` and call `java -jar your-project-version.jar` or the `bin/q2` (or `q2.bat`) script available in the `bin` directory.

### Publish Maven artifacts
````
gradle publish
````

### List available Gradle tasks
````
gradle tasks
````

## Running and testing
For testing the driver you need to, at least, run the driver itself along with the atm emulator and the `iso-server-simultor` module.

To start the `iso-server-simulator`:
```shell
cd modules/iso-server-simulator
gradle iA  #short for installApp
build/install/iso-server-simulator/bin/q2
```

To start the atm driver itself:
```shell
cd modules/atm-driver
gradle iA
build/install/atm-driver/bin/q2
```

For the ATM emulator, clone [alectron-atm repo](https://github.com/jrodriguesd/electron-atm) and then:

1. In the cloned directorio run `./install.sh` to perform a custom install.
2. To start it just run `npm start`

