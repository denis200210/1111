package com.example.guidetovladivostok.activity

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.guidetovladivostok.presenter.DeleteLocationContract
import com.example.guidetovladivostok.presenter.DeleteLocationPresenter

/** Класс отвечает за показ диалогового окна удаления локации **/
class DeleteDialogFragment constructor(documentId: String, nameLocation: String)
    : DialogFragment(), DeleteLocationContract.View{

    private var documentId: String
    private var nameLocation: String
    private lateinit var presenter: DeleteLocationContract.Presenter

    private val onPositiveClickListener = DialogInterface
        .OnClickListener { dialogInterface, i ->
            presenter.deleteLocation(documentId)
            dialogInterface.cancel()
        }

    private val onNegativeClickListener = DialogInterface
        .OnClickListener { dialogInterface, i ->
            dialogInterface.cancel()
        }

    init {
        this.documentId = documentId
        this.nameLocation = nameLocation
        presenter = DeleteLocationPresenter(this)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Вы хотите удалить локацию: $nameLocation?")
        builder.setPositiveButton("Да", onPositiveClickListener)
        builder.setNegativeButton("Нет", onNegativeClickListener)
        return builder.create()
    }
}