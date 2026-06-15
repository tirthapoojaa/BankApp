# Banking Application - Java EE Servlet Setup

## Project Structure

```
BankingApp/
├── src/
│   ├── main/          (existing core app)
│   ├── servlet/       (NEW: servlet controllers)
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── repositoryimpl/
│   ├── model/
│   └── enums/
└── WebContent/
    ├── WEB-INF/
    │   └── web.xml    (servlet mapping)
    ├── index.html
    ├── css/
    │   └── style.css
    └── js/
        └── app.js
```

## Compilation & Deployment

### Step 1: Compile Java Servlets

```bash
cd /Users/philip-10174/Downloads/New\ Folder\ With\ Items/BankingApp

# Compile servlets with javax servlet libraries
javac -cp ".:$CATALINA_HOME/lib/*" -d out src/servlet/*.java src/controller/*.java src/service/*.java src/repository/*.java src/repositoryimpl/*.java src/model/*.java src/enums/*.java src/main/*.java
```

### Step 2: Create WAR File Structure

```bash
# Create WAR structure
mkdir -p war/BankingApp/WEB-INF/classes
mkdir -p war/BankingApp/WEB-INF/lib

# Copy compiled classes
cp -r out/* war/BankingApp/WEB-INF/classes/

# Copy web resources
cp WebContent/index.html war/BankingApp/
cp -r WebContent/css war/BankingApp/
cp -r WebContent/js war/BankingApp/
cp WebContent/WEB-INF/web.xml war/BankingApp/WEB-INF/
```

### Step 3: Create WAR File

```bash
cd war
jar -cvf BankingApp.war BankingApp/
```

### Step 4: Deploy on Tomcat

1. **Download & Install Tomcat** (if not already installed):
   ```bash
   # macOS via Homebrew
   brew install tomcat

   # Set CATALINA_HOME
   export CATALINA_HOME=/usr/local/opt/tomcat
   ```

2. **Copy WAR to Tomcat**:
   ```bash
   cp war/BankingApp.war $CATALINA_HOME/webapps/
   ```

3. **Start Tomcat**:
   ```bash
   $CATALINA_HOME/bin/startup.sh
   # or on macOS:
   catalina start
   ```

4. **Access the App**:
   ```
   http://localhost:8080/BankingApp
   ```

## Alternative: Direct Deployment (No WAR)

If you want to avoid packaging:

1. Copy entire `WebContent/` folder to `$CATALINA_HOME/webapps/BankingApp/`
2. Copy compiled classes to `$CATALINA_HOME/webapps/BankingApp/WEB-INF/classes/`
3. Copy `web.xml` to `$CATALINA_HOME/webapps/BankingApp/WEB-INF/`
4. Restart Tomcat

## Notes

- The frontend HTML/CSS/JS in `WebContent/` is served as static content
- Servlets handle API calls via `/api/*` endpoints
- All business logic (services/repositories) is reused from the original Java app
- The application uses in-memory storage (you can extend with database later)

## Troubleshooting

- **Port 8080 in use**: Change port in `$CATALINA_HOME/conf/server.xml`
- **Servlet not found**: Ensure `web.xml` is in `WEB-INF/` and deployment path matches `<url-pattern>` in web.xml
- **CORS issues**: Add CORS headers in servlet response if needed
- **Compilation errors**: Ensure `javax.servlet` library is in classpath
