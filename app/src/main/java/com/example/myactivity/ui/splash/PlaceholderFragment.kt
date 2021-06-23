package com.example.myactivity.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.myactivity.R
import com.example.myactivity.databinding.PlaceholderFragmentBinding

class PlaceholderFragment : Fragment() {
    private var _binding: PlaceholderFragmentBinding? = null
    private val binding get() = _binding!!

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private val ARG_SECTION_NUMBER = "section_number"
    var img: ImageView? = null

    private val onBoardImages = intArrayOf(R.raw.people, R.raw.compi, R.raw.start)

    private val onBoardTitle = arrayOf("Batu Kertas Gunting", "KAWIBAWIBO", "Batu Kertas Gunting")
    private val onBoardTitleSub =
        arrayOf("Bermain dengan Sesama Pemain", "Bermain lawan Komputer", "Siap Bermain")
    private val onBoardImageSub = arrayOf(
        "Pemain dapat bermain dengan teman terdekat.",
        "Pemain dapat bermain dengan computer untuk menguji kehandalan bermain.",
        "Sudah tentukan pilihan bermain"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PlaceholderFragmentBinding.inflate(inflater, container, false)
        val rootView = binding.root


        binding.placeHolderTvTitle.text = onBoardTitle[requireArguments().getInt(ARG_SECTION_NUMBER) - 1]
        binding.placeHolderTvTitleSub.text =
            onBoardTitleSub[requireArguments().getInt(ARG_SECTION_NUMBER) - 1]
        binding.placeHolderLottieAnim.setAnimation(
            onBoardImages[requireArguments().getInt(
                ARG_SECTION_NUMBER
            ) - 1]
        )
        binding.placeHolderTvImgSub.text =
            onBoardImageSub[requireArguments().getInt(ARG_SECTION_NUMBER) - 1]

        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(sectionNumber: Int) =
            PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}