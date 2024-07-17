package uz.yasindev.noteapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import uz.yasindev.noteapp.R
import uz.yasindev.noteapp.adapters.NoteAdapter
import uz.yasindev.noteapp.cache.AppCache
import uz.yasindev.noteapp.database.NoteDatabase
import uz.yasindev.noteapp.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by lazy { FragmentMainBinding.inflate(layoutInflater) }
    private lateinit var roomDatabase: NoteDatabase
    private val adapter = NoteAdapter()
    private var noteCounter = 0

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
            Room.databaseBuilder(findNavController().context, NoteDatabase::class.java, "note_db")
                .allowMainThreadQueries()
                .build()


        setAdapter()
        changeStatusBarIconColor()
        loadMenu()
        loadActions()
        binding.noteCountView.text = "Notes:$noteCounter"
        binding.searchView1.visibility = View.INVISIBLE

    }


    private fun setAdapter() {
        adapter.setData(roomDatabase.getDao().getAllData())

        val spanCount = 2 // Number of columns or rows
        val layoutManager = StaggeredGridLayoutManager(spanCount, GridLayoutManager.VERTICAL)
        val layoutManager2 = LinearLayoutManager(findNavController().context)
        binding.recyclerView.layoutManager = layoutManager

        binding.recyclerView.adapter = adapter
        Log.d("TAGppp", "setAdapter: ${roomDatabase.getDao().getAllData()}")
    }

    private fun loadActions() {
        binding.searchView.setOnClickListener {

        }

        binding.fab.setOnClickListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddNoteFragment2())

        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    binding.noteCountView2.visibility = View.VISIBLE
                    binding.noteCountView2.text = "Notes:${AppCache.getObject().getNoteCount()}"
                    binding.appName.visibility = View.GONE
                } else if (dy < 0) {
                    // Scrolling up
                    binding.appName.visibility = View.VISIBLE
                    binding.noteCountView2.visibility = View.INVISIBLE
                }
            }
        })

        adapter.onClickItemListener = { noteModel ->
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToEditFragment(
                    noteModel.id,
                    noteModel.title,
                    noteModel.description
                )
            )
        }

        binding.searchView.setOnClickListener {
            binding.searchView.visibility = View.INVISIBLE
            binding.searchView1.visibility = View.VISIBLE
            binding.burger.visibility = View.INVISIBLE
            binding.noteCountView2.visibility = View.INVISIBLE

            binding.searchView1.setOnQueryTextListener(object :
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    roomDatabase.getDao().searchTitle(query.toString())

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val searchTitle = roomDatabase.getDao().searchTitle(newText.toString())
                    adapter.setData(searchTitle)

                    if (newText.toString().isEmpty()) {
                        binding.searchView1.visibility = View.INVISIBLE
                        binding.searchView.visibility = View.VISIBLE
                        binding.burger.visibility = View.VISIBLE
                        binding.noteCountView2.visibility = View.VISIBLE
                    }

                    return false
                }

            })

            binding.searchView1.setOnCloseListener {

                binding.searchView1

                // TODO: Fixing closing the search view

                true
            }
        }
    }

    private fun loadMenu() {
        binding.optionsMenu.setOnClickListener { view ->

            val popupMenu = PopupMenu(findNavController().context, view)

            popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener {

                when (it.itemId) {
                    R.id.delete -> {
                        roomDatabase.getDao().deleteAllData()
                        adapter.setData(roomDatabase.getDao().getAllData())
                        AppCache.getObject().deleteCount()
                        binding.noteCountView.text = "Notes:${AppCache.getObject().getNoteCount()}"
                        binding.noteCountView2.text = "Notes:${AppCache.getObject().getNoteCount()}"
                    }

                    R.id.view -> {

                    }

                    R.id.pin -> {

                    }
                }

                true
            }
            popupMenu.show()
        }
    }


    private fun changeStatusBarIconColor() {
        // Set the status bar icons to dark (light background)
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onResume() {
        super.onResume()
        adapter.setData(roomDatabase.getDao().getAllData())
        binding.noteCountView.text = "Notes:${AppCache.getObject().getNoteCount()}"
    }

}