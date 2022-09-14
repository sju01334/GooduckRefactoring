package com.gooduckrefactoring.util

import com.google.android.material.appbar.AppBarLayout
import java.lang.Math.abs

abstract class AppBarStateChangeListener: AppBarLayout.OnOffsetChangedListener {
    enum class State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private var currentState = State.IDLE

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        appBarLayout?.let {
            when {
                verticalOffset == 0 -> {
                    if (currentState != State.EXPANDED) {
                        onStateChanged(appBarLayout, State.EXPANDED)
                        currentState = State.EXPANDED
                    }
                }
                abs(verticalOffset) >= appBarLayout.totalScrollRange -> {
                    if (currentState != State.COLLAPSED) {
                        onStateChanged(appBarLayout, State.COLLAPSED)
                        currentState = State.COLLAPSED
                    }
                }
                else -> {
                    if (currentState != State.IDLE) {
                        onStateChanged(appBarLayout, State.IDLE)
                        currentState = State.IDLE
                    }
                }
            }
        }
    }

    /**
     * Notifies on state change
     * @param appBarLayout Layout
     * @param state Collapse state
     */
    abstract fun onStateChanged(appBarLayout: AppBarLayout, state: State)
}
