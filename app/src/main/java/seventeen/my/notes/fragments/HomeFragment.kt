package seventeen.my.notes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.fragment_home.*
import seventeen.my.notes.R
import seventeen.my.notes.adapter.NotesAdapter
import seventeen.my.notes.db.DatabaseHandler
import seventeen.my.notes.model.Notes
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    var notesArray = ArrayList<Notes>()
    var notesAdapter: NotesAdapter = NotesAdapter()
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient= GoogleSignIn.getClient(requireActivity(), gso)

        navController = Navigation.findNavController(view)

        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = StaggeredGridLayoutManager(
            2, StaggeredGridLayoutManager.VERTICAL
        )
        notesAdapter.setOnClickListener(onClicked)

        loadNotes()

        fabBtnCreateNote.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                val tempArr = ArrayList<Notes>()

                for (arr in notesArray) {
                    if (arr.title!!.toLowerCase(Locale.getDefault()).contains(p0.toString())) {
                        tempArr.add(arr)
                    }
                }

                notesAdapter.setData(tempArr)
                notesAdapter.notifyDataSetChanged()
                return true
            }

        })

        btn_logout.setOnClickListener {
            mGoogleSignInClient.signOut()
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_loginFragment)
        }


    }

    private val onClicked = object :NotesAdapter.OnItemClickListener{
        override fun onClicked(noteId: Int) {

            val bundle = bundleOf("noteId" to noteId)
            navController.navigate(R.id.action_homeFragment_to_createNotesFragment, bundle)

        /*    val fragment :Fragment
            val bundle = Bundle()
            bundle.putInt("noteId",noteId)
            fragment = CreateNoteFragment.newInstance()
            fragment.arguments = bundle*/
//            view.findNavController().navigate(R.id.action_homeFragment_to_createNotesFragment)

        }

    }

    private fun loadNotes() {
        val db = DatabaseHandler(requireContext())
        notesArray = db.allNotes
        if (notesArray.size != 0) {
            notesAdapter.setData(notesArray)
            recycler_view.adapter = notesAdapter
        }
    }



}