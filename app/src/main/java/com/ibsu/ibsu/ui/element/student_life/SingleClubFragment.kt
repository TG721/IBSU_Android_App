package com.ibsu.ibsu.ui.element.student_life

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.ibsu.ibsu.R
import com.ibsu.ibsu.databinding.FragmentSingleClubBinding
import com.ibsu.ibsu.extensions.getCurrentLocale
import com.ibsu.ibsu.extensions.hideBottomNavigation
import com.ibsu.ibsu.extensions.setActionBarName
import com.ibsu.ibsu.extensions.showBackButton
import com.ibsu.ibsu.ui.common.BaseFragment
import com.ibsu.ibsu.utils.LanguagesLocale.georgianLocale


class SingleClubFragment :
    BaseFragment<FragmentSingleClubBinding>(FragmentSingleClubBinding::inflate) {
    private val args by navArgs<SingleClubFragmentArgs>()
    override fun setup() {
        setupInfo()
        setActionBarName(getString(R.string.club_information))
        hideBottomNavigation()
        showBackButton()
    }

    override fun listeners() {
        binding.presidentEmail.setOnClickListener {
            openEmail(((it as TextView).text.toString()))
        }

        binding.vicePresidentEmail.setOnClickListener {
            openEmail(((it as TextView).text.toString()))
        }
        binding.presidentNumber.setOnClickListener {
            openNumber((it as TextView).text.toString())
        }
        binding.vicePresidentNumber.setOnClickListener {
            openNumber((it as TextView).text.toString())
        }
        binding.imageViewFacebook.setOnClickListener {
            openFacebookPage(args.club.fbLink!!)
        }
    }

    private fun openEmail(email: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        }
        val packageManager = requireContext().packageManager
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "No email app found.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        val packageManager = requireContext().packageManager
        if (intent.resolveActivity(packageManager) != null) {
            requireContext().startActivity(intent)
        } else {
            Toast.makeText(context, "No phone app found.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun openFacebookPage(facebookPageUrl: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(facebookPageUrl)))
    }

    private fun setupInfo() {
        if (requireContext().getCurrentLocale().language == georgianLocale)
            binding.clubName.text = args.club.clubNameGe
        else binding.clubName.text = args.club.clubNameEn
        if (args.club.descriptionEn != null) {
            if (requireContext().getCurrentLocale().language == georgianLocale)
                binding.clubdescription.text = args.club.descriptionGe
            else binding.clubdescription.text = args.club.descriptionEn
            binding.clubDescriptionTitle.visibility = View.VISIBLE
        } else {
            binding.clubDescriptionTitle.visibility = View.GONE
        }
        if (args.club.vicePresidentEmail != null) {
            binding.vicePresidentEmail.text = args.club.vicePresidentEmail
            binding.vicePresidentEmail.visibility = View.VISIBLE
        } else binding.vicePresidentEmail.text = ""
        if (args.club.vicePresidentNameEn != null) {
            if (requireContext().getCurrentLocale().language == "ka")
                binding.vicePresidentName.text = "Vice President - " + args.club.vicePresidentNameGe
            else binding.vicePresidentName.text =
                "Vice President - " + args.club.vicePresidentNameEn
            binding.vicePresidentName.visibility = View.VISIBLE
        } else binding.vicePresidentName.text = ""
        if (args.club.vicePresidentNumber != null) {
            binding.vicePresidentNumber.text = args.club.vicePresidentNumber
            binding.vicePresidentNumber.visibility = View.VISIBLE
        } else binding.vicePresidentNumber.text = ""

        if (args.club.fbLink != null) binding.imageViewFacebook.visibility = View.VISIBLE
        else binding.imageViewFacebook.visibility = View.GONE

        var presidentName = ""
        if (requireContext().getCurrentLocale().language == "ka") presidentName =
            args.club.presidentNameGe
        else presidentName = args.club.presidentNameEn
        binding.presidentName.text = getString(R.string.president) + presidentName
        binding.presidentEmail.text = args.club.presidentEmail
        binding.presidentNumber.text = args.club.presidentNumber
        binding.presidentEmail.paintFlags =
            binding.presidentEmail.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.vicePresidentEmail.paintFlags =
            binding.vicePresidentEmail.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.presidentNumber.paintFlags =
            binding.presidentEmail.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.vicePresidentNumber.paintFlags =
            binding.vicePresidentEmail.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        if (args.club.presidentNumber == "") binding.presidentNumber.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        setActionBarName(getString(R.string.club_information))
    }

}