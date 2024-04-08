package dadm.aidelrio.quotationshake.ui.favourites

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dadm.aidelrio.quotationshake.R
import dadm.aidelrio.quotationshake.databinding.FragmentFavouritesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouritesFragment : Fragment(R.layout.fragment_favourites), MenuProvider {
    private val viewModel: FavouritesViewModel by activityViewModels()
    private val itemTouchHelper = ItemTouchHelper(SwipeToRightCallback())
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!

    private fun getAuthorInfo(authorName : String) {
        if (authorName.isEmpty())
            Snackbar.make(binding.favouritesFragment, "Autor anónimo", Snackbar.LENGTH_SHORT).show()
        else {
            val intent = Intent().setAction(Intent.ACTION_VIEW)
                .setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=$authorName"))
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Snackbar.make(
                    binding.favouritesFragment,
                    "No hay ninguna actividad disponible",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        _binding = FragmentFavouritesBinding.bind(view)
        val adapter = QuotationListAdapter(::getAuthorInfo)
        binding.recyclerView.adapter = adapter
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.quotationList.collect { list ->
                    adapter.submitList(list)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isDeleteAllMenuVisible.collect {
                    requireActivity().invalidateOptionsMenu()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_favourites, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.favouritesItem) {
            findNavController().navigate(R.id.deleteAllFavouriteDialog)
            return true
        }
        return false
    }

    override fun onPrepareMenu(menu: Menu) {
        // Ocultar o mostrar el elemento de menú en base al valor de isDeleteAllMenuVisible
        val deleteAllItem = menu.findItem(R.id.favouritesItem)
        deleteAllItem.isVisible = viewModel.isDeleteAllMenuVisible.value
    }


    inner class SwipeToRightCallback : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.END) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.deleteQuotationAtPosition(viewHolder.adapterPosition)
        }

        override fun isLongPressDragEnabled(): Boolean {
            return false
        }

        override fun isItemViewSwipeEnabled(): Boolean {
            return true
        }

    }
}
