## Project discription :
# Login to Amazone and adding best seller search to cart for page 1.


## Check you have jdk
java -version
javac -version


# Check JAVA_HOME
source ~/.bash_profile
echo $JAVA_HOME


## Install maven from brew
brew install maven


## Test execution From cmd prompt

# open in Administration mode
# change directory to project location
# Build + run
# -Dbrowser = "chrome" for chrome browser, "firefox" for firefox browser and "ie" for IE browser
mvn clean 
mvn test -DsuiteXmlFile=Smoke -Dmaven.test.failure.ignore=false -Dbrowser=chrome

## Test execution From Eclipse

# Open and clean project
# Set browser in config.properties Chrome=true for chrome browser, Firefox=true for firefox browser and IE = true for IE browser
# run "Headphones_BuyBestSeller.java" || "Smoke.xml" as TestNG Test
/cisco_demo/src/test/java/in/amazon/testscripts/Headphones_BuyBestSeller.java
/cisco_demo/src/test/resources/testsuite/Smoke.xml


## Execution Results / Reports:

# Location1 
"C:\Users\mrinalm\Desktop\ArchiveReports_Mrinal" (contain all results with execution time)

# Location2 
"/cisco_demo/Result" (inside project)



## Notes-

# Amazone uses capcha validation for user login, so implemented OTP validation through email.
# logs, images and time stamp is added in reports for each step.
# Scripts has been created for all the browsers (IE, Chrome,firefox), But right now its working on chrome smoothly.


## Scripts Execution steps :
1: Open Amazon site.
2. Enter a registered email id and proceed.
3. Select OTP option for login.
4. Open yahoo mail.
5. Goto inbox and open authentication email.
6. Get OTP received with the mail and login to amazon.
7. logout through yahoo after getting the OTP
8. Open cart and clear the previous item.
9. enter 'headphone best seller' in search box.
10. Verify user should be on the first page.
11. List out all the best seller searched items.
12. Open all the best seller items and add each them to cart.
13. open cart and verify items get added succesfully.
14. Logout through Amazon. 
