# TwitterTestApp
Test app for hipages

Using the following libraries:
1. Gson - for Json parsing since it makes getting fromJson and toJson easier. Also helps with reducing the boilerplate code for parsing Json response.
2. Picasso - for fetching the profile images of users
3. Retrofit - to make network calls as it removes the code to create HttpUrlConnections and  communicates with the web services. It is also faster and more efficient.

With current implementation, I have made synchronous calls to getTweets() after authentication. This has been done by making the second call in the 'success' of the getBearerToken() method. A much cleaner way to do this is by using RxJava. But due to time limitations I could not implement that solution.

I have used the recyclerview rather than the listview as it more powerful, has more control over the display and animations. 
It also enforces the Viewholder pattern which is the recommended pattern for a list.

I have put in commented code which we could use to see the name and screen name of the user. But since that was not 
a part of requirement I have commented out that part.

For twitter authentication, I am using the application authentication rather than user authentication. 
This lets the user see tweets without authentication but the downside is the limit on number of tweets shown. Also, the user cannot post tweets.

We could also use OAuth2 if we want the user to manually authenticate on twitter. This would let the user view as well as post tweets.

