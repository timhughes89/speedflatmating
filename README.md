# SpeedFlatmating Technical Assignment

Considerations for assignment
 - Generate list of venues (Event, not sure why I called this event over venue)
 - XML Layout for one venue item
 - Handling a phone call (without requesting phone call permissions) to the venue when the event card / title is tapped.

- MainActivity.java 
	- This activity contains a frame layout in which different fragments can be inflated using the fragmentManager. In this project a MainFragment.java is inflated.

- MainFragment.java 
	- This is the parent fragment which is inflated into the MainActivity(frameLayout) and includes a toolbar, tab bar layout & view pager. The tab bar layout and view pager are then paired together so that the user can tab or scroll between two child fragments UpcomingEventFragment() & ArchivedEventFragment()

- UpcomingEventFragment.java
	- initUI() Initializes the UI 
	- fetchDataFromNetwork() Requesting data from the api by creating an instance of retrofit from RetrofitClientInstance.class and querying the provided endpoint  
	- OnItemClick() method that passes through the Event at the position clicked in the recyclerview, creates a Uri that is passed to a new implicit intent with the ACTION_DIAL action.
	- runLayoutAnimation() A method that creates a layoutAnimation from an xml resource file(R.anim.layout_animation_fall_down, R.anim.item_animation_fall_down) and applies it to each item in the recyclerView
	- sortByAscending() / sortByDecesending() Two methods that sort the List<Event> by comparing the startTime()

- ViewPagerAdapter.java
	- A simple adapter that extends the FragmentStatePagerAdapter class

- UpcomingEventAdapter.java
	- A recyclerView Adapter that binds data to the inflated view list_item_event.xml
	- methods for passing data into the adapter, getting the size of the List, and getting the viewType.

- ArchivedEventFragment.java
	- A fragment that contains one textview (I would have liked to expanded on this tab a bit more going forward but felt other areas took priority over this)

- EventUtils.java
	- A utils class that contains methods for formatting date & time, as well as a some methods for loading images using the Glide Library.

- ItemOffsetDecoration.java
	- A UI class that extends RecyclerView.ItemDecoration which takes a spacing int and applies it to the recycler view list items (list_item_event.xml & list_item_header). 

- RetrofitClientInstance.java
	- A class that creates an instance of Retrofit.

- EventApiService.java
	- A class that turns a HTTP API into a Java interface.

- Event.java 
	- A javabean class that holds data provided by the api call.


