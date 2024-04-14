package com.srbastian.notes.View

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.srbastian.notes.Adapters.NoteAdapter
import com.srbastian.notes.Model.Note
import com.srbastian.notes.NoteApplication
import com.srbastian.notes.R
import com.srbastian.notes.ViewModel.NoteViewModel
import com.srbastian.notes.ViewModel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var noteViewModel: NoteViewModel
    lateinit var toolbar: Toolbar
    lateinit var addActivityResultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val noteAdapter = NoteAdapter()
        recyclerView.adapter = noteAdapter

        //register activity for resutl
        registerActivityResultLauncher()

        val viewModelFactory = NoteViewModelFactory((application as NoteApplication).repository)
        noteViewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)

        noteViewModel.myAllNotes.observe(this, Observer { notes ->
            // Update UI
            noteAdapter.setNote(notes)
        })

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                noteViewModel.delete(noteAdapter.getNote(viewHolder.adapterPosition))

            }

        }).attachToRecyclerView(recyclerView)
    }

    fun registerActivityResultLauncher() {
        addActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { resultAddNote ->
                val resultCode = resultAddNote.resultCode
                val data = resultAddNote.data

                if (resultCode == RESULT_OK && data != null) {

                    val noteTitle: String = data.getStringExtra("title").toString()
                    val noteDescription: String = data.getStringExtra("description").toString()

                    val note = Note(noteTitle, noteDescription)
                    noteViewModel.insert(note)
                }

            })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.new_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.iAddNote -> {
                val intent = Intent(this, NoteAddActivity::class.java)
                addActivityResultLauncher.launch(intent)
            }

            R.id.iDeleteAllNotes -> dialogMessage()

        }
        return true
    }

    fun dialogMessage() {
        val dialogMessage = AlertDialog.Builder(this)
        dialogMessage.setTitle("Delete All Notes")
        dialogMessage.setMessage("If you click yes, all notes where delete, if you want to delete specific note please swipe right or left")
        dialogMessage.setNegativeButton(
            "No",
            DialogInterface.OnClickListener { dialog, which ->
                dialog.cancel()
            })
        dialogMessage.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            noteViewModel.deleteAllNotes()
        })
        dialogMessage.create().show()
    }
}