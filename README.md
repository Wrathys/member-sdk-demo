# member-sdk-demo

[![Version](https://jitpack.io/v/Wrathys/member-sdk-demo.svg)](https://jitpack.io/#Wrathys/member-sdk-demo)

## Installation

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
     repositories {
	  ...
          maven { url 'https://jitpack.io' }
     }
}
```
Step 2. Add the dependency
```gradle
dependencies {
     implementation 'com.github.Wrathys:member-sdk-demo'
}
```
Step 3. Add the intent filter for receive data from Member Demo on your application:
```xml
<application
    ...
    android:theme="@style/AppTheme">
    ...
    <intent-filter>
        <action android:name="android.intent.action.SEND" />
        <category android:name="android.intent.category.DEFAULT" />
        <data android:mimeType="@string/type_text" />
    </intent-filter>
    ...
```
## Author

Satayu, satayu132@gmail.com

## License

member-sdk-demo is available under the MIT license. See the [LICENSE](https://github.com/Wrathys/member-sdk-demo/blob/master/LICENSE)