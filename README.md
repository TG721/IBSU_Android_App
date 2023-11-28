# IBSU Mobile Application

The goal of this project is to hopefully make easier for students to access the infromation that they are interested in the most while also providing flexible UI with imporved design tailored for mobile applications.


## Image Slideshow

Similar to [IBSU Website](https://ibsu.edu.ge/) the app features image slideshow that switches slides automatically and by pressing on slide, user can learn more about particular event.

![Image Slideshow](https://github.com/TG721/IBSU_Android-_App/assets/85778941/1d2f7580-f957-4203-988c-10fe93b83aa9)

## Tab Layout

Appliacation mainly uses TabLayout to allow stutudents to access related information of sort, using simple hand swipe.

![TabLayout](https://github.com/TG721/IBSU_Android-_App/assets/85778941/81957238-9ec5-4b93-8008-587754082a74)



## Swipe to Refresh 

Application allows swiping from top to bottom in case user requests information reload or the information has not yet loaded.

## Screenshots


<table >

   <tr>
      <td><img src="https://github.com/TG721/IBSU_Android-_App/assets/85778941/53754aab-f280-4692-bc3d-200258cec47b" width="216"></td>
      <td><img src="https://github.com/TG721/IBSU_Android-_App/assets/85778941/cbbb20ca-d852-4fe7-a5e0-0de1d762ff64" width="216"> </td>
      <td><img src="https://github.com/TG721/IBSU_Android-_App/assets/85778941/e21ebe4f-e86a-4974-8886-feae6b9028a9" width="216"></td>
      <td><img src="https://github.com/TG721/IBSU_Android-_App/assets/85778941/fe0a9923-793c-4f9e-94a1-f16da6c64d6a" width="216"></td>
   </tr>
   <tr>
      <td colspan="3">Homework 5-10</td>
   </tr>
   <tr>
      <td colspan="3"><img src="https://github.com/TG721/Quantori_JS_Homeworks/assets/85778941/6cf09f74-0e28-48dc-9260-2ff192ba1db4"></td>  
   </tr>
</table>


## Technical details

### Architecture

The applications uses a version of clean architecture providing 3 layers: UI, Domain and Data to separate concerns of application code.

Retrofit is used form making HTTP requests.
Glide is used for Image Loading.

The app also uses WebView to load and itegrate https://sis.ibsu.edu.ge/ into the application.


### Used Additional Dependencies

    //zoom
    implementation 'com.github.MikeOrtiz:TouchImageView:1.4.1' 
    //glide
    implementation 'com.github.bumptech.glide:glide:4.13.2'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.13.2'
    // Coroutines
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4'
    //lottie animation
    implementation "com.airbnb.android:lottie:3.4.0"
    //moshi
    implementation "com.squareup.moshi:moshi:1.13.0"
    implementation "com.squareup.moshi:moshi-kotlin:1.12.0"
    // Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation "com.squareup.okhttp3:logging-interceptor:4.10.0"
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    implementation 'com.squareup.retrofit2:converter-moshi:2.9.0'
    //ImageSlideShow
    implementation 'com.github.denzcoskun:ImageSlideshow:0.1.2'
    //circle imageview
    implementation 'de.hdodenhof:circleimageview:3.1.0'
    //card view
    implementation "androidx.cardview:cardview:1.0.0"
    //recyclerview
    implementation "androidx.recyclerview:recyclerview:1.3.1"
    //Dagger - Hilt
    implementation "com.google.dagger:hilt-android:2.43.2"
    kapt "com.google.dagger:hilt-compiler:2.43.2"
    //swipe refresh
    implementation 'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0'

