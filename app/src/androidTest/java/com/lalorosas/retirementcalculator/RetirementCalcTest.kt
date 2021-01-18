package com.lalorosas.retirementcalculator


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.microsoft.appcenter.espresso.Factory
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class RetirementCalcTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var reportHelper = Factory.getReportHelper()

    @Test
    fun retirementCalcTest() {
        val textInputEditText = onView(withId(R.id.monthlySavingsEditText))
        textInputEditText.perform(click())

        val textInputEditText2 = onView(withId(R.id.monthlySavingsEditText))
        textInputEditText2.perform(replaceText("200"), closeSoftKeyboard())

        val textInputEditText3 = onView(withId(R.id.interestEditText))
        textInputEditText3.perform(replaceText("3"), closeSoftKeyboard())

        val textInputEditText4 = onView(withId(R.id.ageEditText))
        textInputEditText4.perform(replaceText("30"), closeSoftKeyboard())

        val textInputEditText5 = onView(withId(R.id.retirementEditText))
        textInputEditText5.perform(replaceText("65"), closeSoftKeyboard())

        val textInputEditText6 = onView(withId(R.id.currentEditText))
        textInputEditText6.perform(replaceText("1000"), closeSoftKeyboard())

        val materialButton = onView(withId(R.id.calculateButton))
        materialButton.perform(click())

        val textView = onView(withId(R.id.resultTextView))
        textView.check(matches(withText("At the current rate of 3.0%, saving \$200.0 a month you will have \$151539.484375 by 65.")))
    }

    @After
    fun tearDown() {
        reportHelper.label("Finishing test")
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
