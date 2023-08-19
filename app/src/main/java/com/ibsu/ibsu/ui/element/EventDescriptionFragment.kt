package com.ibsu.ibsu.ui.element

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentEventDescriptionBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.extensions.hideBottomNavigation
import com.ibsu.ibsu.extensions.setActionBarName
import com.ibsu.ibsu.extensions.showBackButton
import com.ibsu.ibsu.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventDescriptionFragment :
    BaseFragment<FragmentEventDescriptionBinding>(FragmentEventDescriptionBinding::inflate) {
    private val args by navArgs<EventDescriptionFragmentArgs>()
    override fun setup() {
        setActionBarName(getString(R.string.more_information))
        showBackButton()
        hideBottomNavigation()
        binding.apply {
            Glide.with(imageView)
                .load(args.clickedEventInfo.pictureURL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView)
            var receivedText: String? = null
            if(requireContext().getCurrentLocale(requireContext()).language=="ka") {
                 receivedText = args.clickedEventInfo.descriptionGe
            } else
             receivedText = args.clickedEventInfo.descriptionEn
            textView.text = receivedText?.let {
                makeUrlClickable(receivedText)
            }
            textView.movementMethod = LinkMovementMethod.getInstance()
        }
    }
    private fun makeUrlClickable(text: String): SpannableString {
        val pattern = "(http[s]?://[^\\s]+)".toRegex()

        val spannableString = SpannableString(text)
        val matcher = pattern.findAll(text)
        for (match in matcher) {
            val url = match.value
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    // Handle the click action here
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    requireContext().startActivity(intent)
                }

                override fun updateDrawState(ds: TextPaint) {
                    // Customize the appearance of the clickable text if needed
                    // For example, you can change the color or underline style
                    ds.isUnderlineText = true
                    ds.color = Color.BLUE
                }
            }

            val start = match.range.first
            val end = match.range.last + 1
            spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

        return spannableString
    }

}