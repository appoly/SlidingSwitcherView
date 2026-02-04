package com.appoly.slidingswitcherview

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.constraintlayout.motion.widget.MotionLayout
import com.appoly.slidingswitcherview.databinding.SlidingSwitcherViewLayoutBinding

class SlidingSwitcherView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    private val layoutInflater: LayoutInflater
        get() = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val binding: SlidingSwitcherViewLayoutBinding =
        SlidingSwitcherViewLayoutBinding.inflate(layoutInflater, this, true).apply {
            startTab.setOnClickListener { setSwitchState(SwitchSate.Start) }
            endTab.setOnClickListener { setSwitchState(SwitchSate.End) }
        }

    private var switchLayoutState = SwitchSate.Start

    private var onSwitchChangeListener: OnSwitchChangeListener? = null

    fun setSwitchState(state: SwitchSate) {
        switchLayoutState = state
        when(state) {
            SwitchSate.Start -> {
                binding.switchMotionLayout.transitionToStart()
            }
            SwitchSate.End -> {
                binding.switchMotionLayout.transitionToEnd()
            }
        }
    }

    fun setStartText(@StringRes text: Int) = binding.startTab.setText(text)
    fun setStartText(text: CharSequence) { binding.startTab.text = text }

    fun setEndText(@StringRes text: Int) = binding.endTab.setText(text)
    fun setEndText(text: CharSequence) { binding.endTab.text = text }

    fun setOnSwitchChangeListener(onSwitchChangeListener: OnSwitchChangeListener?) {
        this.onSwitchChangeListener = onSwitchChangeListener
    }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SlidingSwitcherView)
        val startItemText = a.getText(R.styleable.SlidingSwitcherView_startItemText)
        val endItemText = a.getText(R.styleable.SlidingSwitcherView_endItemText)
        a.recycle()
        if(!startItemText.isNullOrBlank()) {
            setStartText(startItemText)
        }
        if(!endItemText.isNullOrBlank()) {
            setEndText(endItemText)
        }

        // Setting initial state
        setSwitchState(SwitchSate.Start)
        binding.startTab.isChecked = true
        binding.startTab.setTypeface(null, Typeface.BOLD)
        binding.endTab.isChecked = false
        binding.endTab.setTypeface(null, Typeface.NORMAL)

        // Listener to change the isChecked state with the slide animation
        binding.switchMotionLayout.setTransitionListener(object: MotionLayout.TransitionListener {
            override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {
                when(switchLayoutState) {
                    SwitchSate.Start -> {
                        binding.endTab.isChecked = false
                        binding.endTab.setTypeface(null, Typeface.NORMAL)
                    }
                    SwitchSate.End -> {
                        binding.startTab.isChecked = false
                        binding.startTab.setTypeface(null, Typeface.NORMAL)
                    }
                }
            }
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                when(switchLayoutState) {
                    SwitchSate.Start -> {
                        binding.startTab.isChecked = true
                        binding.startTab.setTypeface(null, Typeface.BOLD)
                    }
                    SwitchSate.End -> {
                        binding.endTab.isChecked = true
                        binding.endTab.setTypeface(null, Typeface.BOLD)
                    }
                }
                //invoke listener
                onSwitchChangeListener?.onSwitchStateChanged(switchLayoutState)
            }
            override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) { /* do nothing */ }
            override fun onTransitionTrigger(motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) { /* do nothing */ }
        })
    }

    interface OnSwitchChangeListener {
        /**
         * Called when the state of the [SlidingSwitcherView] has changed.
         *
         * @param state [SwitchSate] the current state of the switch.
         */
        fun onSwitchStateChanged(state: SwitchSate)
    }
}

