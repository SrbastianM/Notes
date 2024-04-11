package com.srbastian.notes.View

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.srbastian.notes.Adapters.NoteAdapter
import com.srbastian.notes.NoteApplication
import com.srbastian.notes.R
import com.srbastian.notes.ViewModel.NoteViewModel
import com.srbastian.notes.ViewModel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var noteViewModel: NoteViewModel
    lateinit var toolbar : Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val noteAdapter = NoteAdapter()
        recyclerView.adapter = noteAdapter

        val viewModelFactory = NoteViewModelFactory((application as NoteApplication).repository)
        noteViewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)

        noteViewModel.myAllNotes.observe(this, Observer { notes ->
            // Update UI
            noteAdapter.setNote(notes)
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
                startActivity(intent)
            }

            R.id.iDeleteAllNotes -> {
                Toast.makeText(applicationContext, "Are you sure?", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}