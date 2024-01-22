package com.ibsu.ibsu.ui.element

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentEventDescriptionBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.extensions.hideBottomNavigation
import com.ibsu.ibsu.extensions.loadFromUrl
import com.ibsu.ibsu.extensions.setActionBarName
import com.ibsu.ibsu.extensions.showBackButton
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.utils.LanguagesLocale.georgianLocale
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
            imageView.loadFromUrl(args.clickedEventInfo.pictureURL, progressBar)
            var receivedText: String? = null
            if(requireContext().getCurrentLocale().language==georgianLocale) {
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

    override fun onResume() {
        super.onResume()
        hideBottomNavigation()
        showBackButton()
    }

}