package ui.favourites

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import dadm.aidelrio.quotationshake.R
import dadm.aidelrio.quotationshake.databinding.FragmentFavouritesBinding

class FavouritesFragment : Fragment(R.layout.fragment_favourites), MenuProvider {
    private val viewModel: FavouritesViewModel by viewModels()
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        requireActivity().invalidateMenu()
        _binding = FragmentFavouritesBinding.bind(view)
        val adapter = QuotationListAdapter()
        binding.recyclerView.adapter = adapter
        adapter.submitList(viewModel.quotationList.value)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_favourites, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.favouritesItem)
        {
            findNavController().navigate(R.id.deleteAllFavouriteDialog)
            return true
        }
        return false
    }

    override fun onPrepareMenu(menu: Menu) {
        if (!viewModel.isDeleteAllMenuVisible.value)
            menu.removeItem(R.menu.menu_favourites)
    }
}
