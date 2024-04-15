package com.srbastian.notes.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.srbastian.notes.R

class UpdateActivity : AppCompatActivity() {
    lateinit var editTextTitle: EditText
    lateinit var editTextDescription: EditText
    lateinit var btnCancel: Button
    lateinit var btnSave: Button

    var currentId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        supportActionBar?.title = "Update Note"

        btnCancel = findViewById(R.id.btnCancelUpdate)
        btnSave = findViewById(R.id.btnSaveUpdate)
        editTextTitle = findViewById(R.id.etTitleUpdate)
        editTextDescription = findViewById(R.id.etDescriptionUpdate)

        getAndSetData()


        btnCancel.setOnClickListener {
            Toast.makeText(applicationContext, "Nothing Update", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnSave.setOnClickListener {
            updateNote()
        }

    }

    private fun updateNote() {
        val updatedTitle = editTextTitle.text.toString()
        val updatedDescription = editTextDescription.text.toString()

        val intent = Intent()
        intent.putExtra("updatedTitle", updatedTitle)
        intent.putExtra("updatedDescription", updatedDescription)

        if (currentId != -1) {
            intent.putExtra("noteId", currentId)
            setResult(RESULT_OK, intent)
            finish()
        }

    }

    private fun getAndSetData() {
        val currentTitle = intent.getStringExtra("currentTitle")
        val currentDescription = intent.getStringExtra("currentDescription")
        currentId = intent.getIntExtra("currentId", -1)

        //set data in the view
        editTextTitle.setText(currentTitle)
        editTextDescription.setText(currentDescription)


    }
}