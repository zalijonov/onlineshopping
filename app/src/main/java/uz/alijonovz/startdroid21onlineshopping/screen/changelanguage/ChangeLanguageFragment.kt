package uz.alijonovz.startdroid21onlineshopping.screen.changelanguage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.orhanobut.hawk.Hawk
import uz.alijonovz.startdroid21onlineshopping.databinding.FragmentChangeLanguageBinding
import uz.alijonovz.startdroid21onlineshopping.screen.MainActivity


class ChangeLanguageFragment : BottomSheetDialogFragment() {

    lateinit var binding: FragmentChangeLanguageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeLanguageBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnUzbek.setOnClickListener {
            Hawk.put("pref_lang", "uz")
            requireActivity().finish()
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }

        binding.btnEnglish.setOnClickListener {
            Hawk.put("pref_lang", "en")
            requireActivity().finish()
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = ChangeLanguageFragment()
    }
}