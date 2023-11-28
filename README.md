# IBSU Mobile Application

The goal of this project is to hopefully make easier for students to access the infromation that they are interested in the most while also providing flexible UI with imporved design tailored for mobile applications.

The app has support for both English and Georgian languages as well as support for both light and dark modes.


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
      <td><img src="https://github.com/TG721/IBSU_Android-_App/assets/85778941/fe0a9923-793c-4f9e-94a1-f16da6c64d6a" width="216"></td>
      <td><img src="https://github.com/TG721/IBSU_Android-_App/assets/85778941/4be71b12-54cc-47b8-a9b0-8cea1f30a318" width="216"></td>
      <td><img src="https://github.com/TG721/IBSU_Android-_App/assets/85778941/ce524982-f681-417a-a8a0-18ebcaf0a0a9" width="216"></td>
      <td><img src="https://github.com/TG721/IBSU_Android-_App/assets/85778941/8d0f0e9d-b923-49bf-9ee9-90388d283d47" width="216"></td>
   </tr>

</table>


## Technical details

### Architecture and More

The applications uses a version of clean architecture providing 3 layers: UI, Domain and Data to separate concerns of application code.
BaseFragment class exists so other fragments can inherit common behaivors of a fragment.
Domain Layer contains IBSURepository interface to abstract away the details of data access. The interface is implemented in Data Layer's IBSURepositoryImp class. 


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

