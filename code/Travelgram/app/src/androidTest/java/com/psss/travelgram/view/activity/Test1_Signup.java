package com.psss.travelgram.view.activity;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.psss.travelgram.R;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Test1_Signup {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void test1_Signup() {

        try { Thread.sleep(7000); } catch (InterruptedException e) { e.printStackTrace(); }

        ViewInteraction appCompatTextView = onView(withId(R.id.clickSignup));
        appCompatTextView.perform(scrollTo(), click());

        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        ViewInteraction appCompatEditText = onView(withId(R.id.signupUsername));
        appCompatEditText.perform(scrollTo(), replaceText("espresso"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(withId(R.id.signupEmail));
        appCompatEditText2.perform(scrollTo(), replaceText("espresso@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(withId(R.id.signupPassword));
        appCompatEditText3.perform(scrollTo(), replaceText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(withId(R.id.signupPassword2));
        appCompatEditText4.perform(scrollTo(), replaceText("123456"), closeSoftKeyboard());

        // pressBack();

        ViewInteraction appCompatButton = onView(withId(R.id.signupBtn));
        appCompatButton.perform(scrollTo(), click());

        try { Thread.sleep(7000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
