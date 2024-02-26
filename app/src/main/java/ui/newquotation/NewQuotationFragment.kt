package ui.newquotation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dadm.aidelrio.quotationshake.R
import dadm.aidelrio.quotationshake.databinding.FragmentNewQuotationBinding

class NewQuotationFragment : Fragment(R.layout.fragment_new_quotation) {

    private var _binding : FragmentNewQuotationBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentNewQuotationBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}