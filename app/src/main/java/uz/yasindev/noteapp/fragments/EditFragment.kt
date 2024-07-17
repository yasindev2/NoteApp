package uz.yasindev.noteapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import uz.yasindev.noteapp.database.NoteDatabase
import uz.yasindev.noteapp.database.NoteModel
import uz.yasindev.noteapp.databinding.FragmentEditBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class EditFragment : Fragment() {

    private val binding by lazy { FragmentEditBinding.inflate(layoutInflater) }
    private lateinit var roomDatabase: NoteDatabase
    private val args:EditFragmentArgs by navArgs()

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
            context?.let {
                Room.databaseBuilder(context = it, NoteDatabase::class.java, "note_db").allowMainThreadQueries()
                    .build()
            }!!

        editNote()




    }


    private fun editNote() {

        val date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy MM dd")
        val text7 = date.format(formatter)
        val parsedDate = LocalDate.parse(text7, formatter)

        val id = args.id
        val title = args.title
        val description = args.description


        binding.titleViewEdit.text = title

        binding.titleAddNoteEdit.setText(title)
        binding.descriptionViewEdit.setText(description)

        binding.updateButtonEdit.setOnClickListener {
            val editedTitle = binding.titleAddNoteEdit.text
            val editedDescription = binding.descriptionViewEdit.text

            val noteModel = NoteModel(
                id,
                editedTitle.toString(),
                editedDescription.toString(),
                1,
                parsedDate.toString()
            )
            roomDatabase.getDao().updateNote(noteModel)

            Snackbar.make(binding.root, "Edited successfully", 5000).show()

            findNavController().popBackStack()

        }

        binding.backButtonEdit.setOnClickListener {
            findNavController().popBackStack()
        }

    }

}