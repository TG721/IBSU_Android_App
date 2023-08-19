package com.ibsu.ibsu.ui.common

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.ibsu.ibsu.R

class HoverButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.attr.materialButtonStyle
) : MaterialButton(context, attrs, defStyleAttr) {
    private var hoverTextColor = Color.GRAY
    private var defaultTextColor: Int = currentTextColor


    init {
        // Read the custom attribute for hoverTextColor
        context.obtainStyledAttributes(attrs, R.styleable.HoverButton).apply {
             hoverTextColor =
                getColor(R.styleable.HoverButton_hoverTextColor, hoverTextColor)
            recycle()
        }

        // Set the hover listener
        setOnHoverListener { view, motionEvent ->
            when (motionEvent.action) {
                // When the button is being hovered
                MotionEvent.ACTION_HOVER_ENTER -> {
                    setTextColor(hoverTextColor)
                }
                // When the hover ends
                MotionEvent.ACTION_HOVER_EXIT -> {
                    // Set the text color back to the default color
                    setTextColor(defaultTextColor)
                }
            }
            // Return false in order to allow other click events to be triggered as well
            false
        }
    }
}