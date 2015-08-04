# TwitterTestApp
Test app for hipages

Using the following libraries:
1. Gson - for Json parsing since it makes getting fromJson and toJson easier. Also helps with reducing the boilerplate code for parsing Json response.
2. Picasso - for fetching the profile images of users
3. Retrofit + Rx- to make network calls as it removes the code to create HttpUrlConnections and  communicates with the web services. It is also faster and more efficient with event handling.

With current implementation, I have made synchronous calls to getTweets() after authentication. This has been done using RxJava implementation with Retrofit.

I have used the recyclerview rather than the listview as it more powerful, has more control over the display and animations. 
It also enforces the Viewholder pattern which is the recommended pattern for a list.

For twitter authentication, I am using the application authentication rather than user authentication. 
This lets the user see tweets without authentication but the downside is the limit on number of tweets shown. Also, the user cannot post tweets.

We could also use OAuth2 if we want the user to manually authenticate on twitter. This would let the user view as well as post tweets.

