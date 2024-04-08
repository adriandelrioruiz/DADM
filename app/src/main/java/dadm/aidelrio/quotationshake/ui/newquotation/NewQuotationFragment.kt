package dadm.aidelrio.quotationshake.ui.newquotation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import dadm.aidelrio.quotationshake.R
import dadm.aidelrio.quotationshake.databinding.FragmentNewQuotationBinding
import dadm.aidelrio.quotationshake.ui.favourites.FavouritesViewModel
import dadm.aidelrio.quotationshake.utils.NoInternetException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewQuotationFragment : Fragment(R.layout.fragment_new_quotation), MenuProvider {

    private val viewModel: NewQuotationViewModel by viewModels()

    private var _binding : FragmentNewQuotationBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentNewQuotationBinding.bind(view)
        // Añadimos esta instancia como proveedor de menús a su actividad solo cuando el fragmento sea interactivo
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Añadimos al botón un Listener para que haga su acción de añadir un favorito
        binding.addFavouriteButton.setOnClickListener{
            viewModel.addNewFavourite()
            /*viewModel.quotation.value?.let { quotation ->
                favouritesViewModel.addFavouriteQuotation(quotation)
            }*/
        }

        // Añadimos al swipeToRefresh un Listener para que busque una nueva cita
        binding.swipeToRefresh.setOnRefreshListener{viewModel.getNewQuotation()}

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.favButtonVisible.collect { favButtonVisible ->
                        binding.addFavouriteButton.isVisible = favButtonVisible
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isLoading.collect { isLoading ->
                        binding.swipeToRefresh.isRefreshing = isLoading
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.userName.collect { userName ->
                        binding.bienvenidaTextView.text = getString(
                            R.string.new_quotation_welcome,
                            userName.ifEmpty { getString(R.string.anonymous) })
                    }
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.quotation.collect { quotation ->
                        if (quotation == null) {
                            binding.bienvenidaTextView.isVisible = true
                        } else {
                            binding.bienvenidaTextView.isVisible = false
                            binding.citeTextView.text = quotation.text
                            if (quotation.author.isEmpty())
                                binding.authorTextView.text = getString(R.string.anonymous)
                            else
                                binding.authorTextView.text = quotation.author
                        }
                    }
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.error.collect { error ->
                        if (error != null) {
                            if (error is NoInternetException)
                                Snackbar.make(binding.swipeToRefresh, R.string.no_internet_error, Snackbar.LENGTH_SHORT).show()
                            else
                                Snackbar.make(binding.swipeToRefresh, R.string.sudden_error, Snackbar.LENGTH_SHORT).show()
                        }
                        viewModel.resetError()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_new_quotation, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.newQuotationItem)
        {
            viewModel.getNewQuotation()
            return true
        }
        return false
    }
}