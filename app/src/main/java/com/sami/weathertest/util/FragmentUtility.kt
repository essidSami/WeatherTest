package com.sami.weathertest.util

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sami.weathertest.R


/**
 * This method push fragment to back stack
 * @param fragmentManager
 * @param fragment
 */
fun pushFragment(fragmentManager: FragmentManager, fragment: Fragment) {
    val fragmentTransaction = fragmentManager.beginTransaction()
    // work here to change Activity fragments (add, remove, etc.).  Example here of adding.

    fragmentTransaction.add(android.R.id.content, fragment)
    fragmentTransaction.commit()
}

/**
 * This method push fragment to back stack
 * @param fragmentManager
 * @param fragment
 */
fun pushFragment(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    addToBackStack: Boolean,
    popBackStack: Boolean
) {
    val fragmentTransaction = fragmentManager.beginTransaction()

    if (popBackStack) {
        fragmentManager.popBackStackImmediate(fragment.tag, 0)
    }

    fragmentTransaction.add(android.R.id.content, fragment)

    if (addToBackStack)
        fragmentTransaction.addToBackStack(fragment.tag)
    fragmentTransaction.commit()

}

/**
 * This method remove fragment from back stack
 * @param fragmentManager
 * @param fragment
 */
fun removeFragment(fragmentManager: FragmentManager, fragment: Fragment) {
    val fragmentTransaction = fragmentManager.beginTransaction()
    // work here to change Activity fragments (add, remove, etc.).  Example here of adding.
    fragmentTransaction.remove(fragment)
    fragmentTransaction.commit()
}


/**
 * This method replace current fragment in back stack
 * @param fragmentManager
 * @param fragment
 * @param fragmentContainer
 */
fun replaceFragment(fragmentManager: FragmentManager, fragment: Fragment, addToBackStack: Boolean) {

    val transaction = fragmentManager.beginTransaction()
    transaction.setCustomAnimations(
        android.R.anim.slide_in_left,
        android.R.anim.slide_out_right
    )
    transaction.replace(R.id.frame_content, fragment)

    if (addToBackStack)
        transaction.addToBackStack(fragment.tag)

    transaction.commit()
}

/**
 * This method replace current fragment in back stack
 * @param fragmentManager
 * @param fragment
 * @param frame
 * @param addToBackStack
 */
fun replaceFragment(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    frame: Int,
    addToBackStack: Boolean
) {

    val transaction = fragmentManager.beginTransaction()
    transaction.replace(frame, fragment)
    transaction.setCustomAnimations(
        android.R.anim.slide_in_left,
        android.R.anim.slide_out_right
    )
    if (addToBackStack)
        transaction.addToBackStack(fragment.tag)

    transaction.commit()
}

/**
 * This method clear back stack
 * @param fragmentManager
 */
fun clearBackStack(fragmentManager: FragmentManager) {

    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}

/**
 * Load a fragment transaction to the activity state.
 *
 * @param isAddToBackStack Optional if you will replace a fragment without adding it to back Stack,
 * default value is False
 * @param transitionPairs Map of name [String] and View
 */
inline fun AppCompatActivity.loadFragment(
    isAddToBackStack: Boolean = false,
    transitionPairs: Map<String, View> = mapOf(),
    transaction: FragmentTransaction.() -> Unit
) {
    val beginTransaction = supportFragmentManager.beginTransaction()
    beginTransaction.transaction()
    for ((name, view) in transitionPairs) {
        ViewCompat.setTransitionName(view, name)
        beginTransaction.addSharedElement(view, name)
    }

    if (isAddToBackStack) beginTransaction.addToBackStack(null)
    beginTransaction.commit()
}

