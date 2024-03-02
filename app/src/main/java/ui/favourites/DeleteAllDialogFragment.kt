package ui.favourites

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels

class DeleteAllDialogFragment : DialogFragment() {

    private val viewModel: FavouritesViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        return builder.setTitle("Delete all favourite quotations")
        .setMessage("Do you really want to delete all your quotations?")
        .setPositiveButton("Delete") { _,_-> viewModel.deleteAllFavouriteQuotations()}
        .setNegativeButton("Cancel") {_,_ -> this.dismiss()}
        .create()

    }
}