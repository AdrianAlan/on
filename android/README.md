

## Google Maps API Key and Dependencies 
The following must be done individually to include google play services and enable google maps.

###Get an Google Maps API Key
https://developers.google.com/maps/documentation/android/start
But don't edit the AndroidManifest, just do the next step.

###Insert API Key into project
Go into ```res/values/``` and copy the file ```api_keys.xml_template``` into ```api_keys.xml```. 
Remove the comment tags around the string resource inside the file and replace the ```API Key here``` with your API key.

###Linking Google Play Services to the project
http://developer.android.com/google/play-services/setup.html
