# Weather App

This is an assignment given to us to develop a Weather App for android.

# Technology

Using Android Studio as our IDE and also some added library like:
1. Retrofit to fetch data from WeatherApi.
2. Picasso to display image from url
3. RecyclerView to handle listview
4. And Swipe to Refresh

# Link to Demo Video

Youtube : [video](https://youtu.be/LDSHMnYg2bo)

# Problem an Challange

There's little problem with Retrofit, in bad internet speed, sometimes it fails to fetch data form our desired api endpoint. So in response to this, i made some little nice feature using Swipe to Refresh. When Retrofit fails, my app will display some user-friendly error view that tells user to refresh the app by pulling down the page (like all apps do). I can't seem to find reasons why Retrofit sometimes fails other than slow internet speed.
