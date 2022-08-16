package com.appoly.slidingswitcherview

fun SlidingSwitcherView.setOnSwitchChangeListener(listener: (state: SwitchSate) -> Unit) {
    setOnSwitchChangeListener(object : SlidingSwitcherView.OnSwitchChangeListener {
        override fun onSwitchStateChanged(state: SwitchSate) { listener(state) }
    })
}