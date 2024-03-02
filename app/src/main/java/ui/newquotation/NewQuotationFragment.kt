package ui.newquotation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dadm.aidelrio.quotationshake.R
import dadm.aidelrio.quotationshake.databinding.FragmentNewQuotationBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewQuotationFragment : Fragment(R.layout.fragment_new_quotation), MenuProvider {

    private val viewModel: NewQuotationViewModel by viewModels()

    private var _binding : FragmentNewQuotationBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentNewQuotationBinding.bind(view)
        // Añadimos esta instancia como proveedor de menús a su actividad solo cuando el fragmento sea interactivo
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isLoading.collect { isLoading ->
                        binding.swipeToRefresh.isRefreshing = isLoading
                    }
                }

                launch {
                    viewModel.userName.collect { userName ->
                        binding.bienvenidaTextView.text = getString(
                            R.string.new_quotation_welcome,
                            userName.ifEmpty { getString(R.string.anonymous) })
                    }
                }

                launch {
                    viewModel.favButtonVisible.collect { favButtonVisible ->
                        binding.addFavouriteButton.isVisible = favButtonVisible
                    }
                }

                launch {
                    viewModel.quotation.collect { quotation ->
                        if (quotation == null) {
                            binding.bienvenidaTextView.isVisible = true
                        } else {
                            binding.bienvenidaTextView.isVisible = false
                            binding.citeTextView.text = quotation.text
                            if (quotation.author.isEmpty())
                                binding.authorTextView.text = "Anonymous"
                            else
                                binding.authorTextView.text = quotation.author
                        }
                    }
                }
                // Añadimos al botón un Listener para que haga su acción de añadir un favorito
                binding.addFavouriteButton.setOnClickListener{viewModel.addNewFavourite()}
                // Añadimos al swipeToRefresh un Listener para que busque una nueva cita
                binding.swipeToRefresh.setOnRefreshListener{viewModel.getNewQuotation()}
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