import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dadm.aidelrio.quotationshake.R
import dadm.aidelrio.quotationshake.databinding.FragmentFavouritesBinding
import ui.favourites.FavouritesViewModel
import ui.favourites.QuotationListAdapter

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {
    private val viewModel: FavouritesViewModel by viewModels()
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavouritesBinding.bind(view)
        val adapter = QuotationListAdapter()
        binding.recyclerView.adapter = adapter
        adapter.submitList(viewModel.quotationList.value)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
