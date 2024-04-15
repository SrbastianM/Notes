package com.srbastian.notes.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.srbastian.notes.R

class NoteAddActivity : AppCompatActivity() {
    lateinit var editTextTitle: EditText
    lateinit var editTextDescription: EditText
    lateinit var btnCancel: Button
    lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_add)
        supportActionBar?.title = "Add Note"

        btnCancel = findViewById(R.id.btnCancel)
        btnSave = findViewById(R.id.btnSave)
        editTextTitle = findViewById(R.id.etTitle)
        editTextDescription = findViewById(R.id.etDescription)

        btnCancel.setOnClickListener {
            Toast.makeText(applicationContext, "Nothing save", Toast.LENGTH_SHORT).show()
            finish()
        }
        btnSave.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val noteTitle: String = editTextTitle.text.toString()
        val noteDescription: String = editTextDescription.text.toString()

        val intent = Intent()
        intent.putExtra("title", noteTitle)
        intent.putExtra("description", noteDescription)

        setResult(RESULT_OK, intent)
        finish()
    }
}