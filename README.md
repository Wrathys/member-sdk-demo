# member-sdk-demo

[![Version](https://jitpack.io/v/Wrathys/member-sdk-demo.svg)](https://jitpack.io/#Wrathys/member-sdk-demo)

## Installation

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories
```gradle
allprojects {
     repositories {
          maven { url 'https://jitpack.io' }
     }
}
```
Step 2. Add the dependency and sync now
```gradle
dependencies {
     implementation 'com.github.Wrathys:member-sdk-demo'
}
```

## Example

### Use default button
1.  Add Button XML layout in `you_activity.xml`
```xml
<LinearLayout>
     <com.satayupomsri.membersdkdemo.MemberSignInButton
     android:id="@+id/bt_sign_in"
     android:layout_width="wrap_content"
     android:layout_height="wrap_content" />
</LinearLayout>
```
2. `findViewById` and `setOnSignInListener` for callback response data
```java
public class YourActivity extends AppCompatActivity {

    private MemberSignInButton signInButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        signInButton = findViewById(R.id.bt_sign_in);
        signInButton.setOnSignInListener(new MemberSignInListener() {
            @Override
            public void onSignInSuccess(String id, String name, String thumbnail) {

            }

            @Override
            public void onSignInFail(MemberStatus memberStatus) {

            }

            @Override
            public void onSignOut(MemberStatus memberStatus) {

            }
        });
    }
}
```

### Custom button
1. Create your button and set id in `your_activity.xml`
2. Use `MemberSignInManager`
3. Add your button by `setCustomButton`
4. set callback `setOnSignInListener`
```java
public class YourActivity extends AppCompatActivity {

    MemberSignInManager signInManager;
    Button yourButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        yourButton = findViewById(R.id.your_button);

        signInManager = new MemberSignInManager(this);
        signInManager.setCustomButton(yourButton);
        signInManager.setOnSignInListener(new MemberSignInListener() {
            @Override
            public void onSignInSuccess(String id, String name, String thumbnail) {

            }

            @Override
            public void onSignInFail(MemberStatus memberStatus) {

            }

            @Override
            public void onSignOut(MemberStatus memberStatus) {

            }
        });
    }
}
```

## Author

Satayu, satayu132@gmail.com

## License

member-sdk-demo is available under the MIT license. See the [LICENSE](https://github.com/Wrathys/member-sdk-demo/blob/master/LICENSE)
