package ui.favourites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dadm.aidelrio.quotationshake.R
import dadm.aidelrio.quotationshake.databinding.FragmentFavouritesBinding

class FavouritesFragment : Fragment(R.layout.fragment_favourites) {

    private var _binding : FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentFavouritesBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}