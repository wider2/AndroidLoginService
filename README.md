Mobile application of login service.

Based on MVP pattern to make source code easy to read and test.

All asynchronous requests (to database, to internet connection) has been done with help of RxJava2 and Retrofit 2.30

Using Butterknife8 to simplify work with each ID of the View.

SQLlite Google Room provides possibility to store data locally when user in offline mode or in cache mode.

Dagger2 provides dependency injection approach to minimize initializations and increase speed with help of Singletons.

Singleton SharedStates used to pass data between fragments. I do not use Bundle to avoid loss of data.

Also have been made some unit, instrumental and espresso tests.