package uz.yasindev.noteapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import uz.yasindev.noteapp.R
import uz.yasindev.noteapp.cache.AppCache
import uz.yasindev.noteapp.database.NoteDatabase
import uz.yasindev.noteapp.database.NoteModel
import uz.yasindev.noteapp.databinding.FragmentAddNoteBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class AddNoteFragment : Fragment(R.layout.activity_main) {


    private val binding by lazy { FragmentAddNoteBinding.inflate(layoutInflater) }
    private lateinit var roomDatabase: NoteDatabase
    private var noteCounter = AppCache.getObject().getNoteCount()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roomDatabase =
            Room.databaseBuilder(context =  findNavController().context, NoteDatabase::class.java, "note_db").allowMainThreadQueries()
                .build()


        intentData()


    }


    private fun intentData() {
        val text = binding.descriptionView.text
        val title = binding.titleAddnote.text
        val date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy MM dd")
        val text7 = date.format(formatter)
        val parsedDate = LocalDate.parse(text7, formatter)




        binding.saveButton.setOnClickListener {

            if (text.isNotEmpty() && title.isNotEmpty()) {

                val noteModel =
                    NoteModel(0, title.toString(), text.toString(), 1, parsedDate.toString())

                roomDatabase.getDao().addNote(noteModel)

                Snackbar.make(binding.root,"Note Added Successfully",5000).show()

                noteCounter++
                Log.d("TAGee", "intentData: $noteCounter")
                AppCache.getObject().saveNoteCount(noteCounter)
                findNavController().popBackStack()


            } else snakeBar()


        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun snakeBar() {
        Snackbar.make(binding.root, "Fill the gaps", 3000).show()
    }


}