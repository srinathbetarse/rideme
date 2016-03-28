# RideMe
*A taxi price comparison Android App*

This was a side project I had from late December 2014 to early 2015. The idea was to build an Android app, in general, to:
- Compare the fare prices to go from point A to point B using a taxi or an Uber/ride sharing company
- Book the ride within the app or open the appropiated app to book the ride
 
Due to the lack of time, other side projects and a shift in priorities I have no time to keep working on this project. It has been hibernated for way too long.

This is a prototype which is working quite well. Main requirements are:

- Upgrade to Android M and the latest version of Play Services.
- Get your own API keys from the API providers
- Move strings to strings.xml file
- Load the API keys/tokens from config file
- Improve the user experience and design on selecting a ride
- Improve tje user experience and design while showing/hidding the map
- Polish the design
- Implement tests
- Use JSON SerializeName on Pojo class variables (to remove the "_" variable name in a few classes)
- Implement your own DazitoRestClient (To receive the user feedback, and rename the class name?)
- Finish Hailo & Taxistartup integration
- Refactor
	- Mainly: UberRoutePrice & TaxiFareFinderRoutePrice & RoutePrice -> Builder Pattern?

**PS;** Check whether the API providers endpoints are still producing the expected JSON.

**PS2;** I had opened a request on TaxiFareFinder to not be required to send an Entity object (which would require first a call to their API to get the Entity and then another call (with the Entity) to get the fare. TaxiFareFinder agreed with my me and later on removed this requirement. Still, the app is using the "2 calls way" to get the Fare as at the time I had no time to update the app.

**Feel free to fork and improve it!**

*Goodbye RideMe, was nice to meet you. We had a lot of fun.*
