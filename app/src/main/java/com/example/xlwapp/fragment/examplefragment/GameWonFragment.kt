package com.example.xlwapp.fragment.examplefragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.xlwapp.R
import com.example.xlwapp.databinding.FragmentGameWonBinding

class GameWonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameWonBinding>(inflater,R.layout.fragment_game_won,container,false)
        binding.doAgain.setOnClickListener{v -> v.findNavController().navigate(
            GameWonFragmentDirections.actionGameWonFragmentToGameFragment()) }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.share_menu,menu)

        if(null == getShareIntent().resolveActivity(activity!!.packageManager)){
            menu?.findItem(R.id.share)?.setVisible(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId){
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareSuccess(){
        startActivity(getShareIntent())
    }


    private fun getShareIntent() : Intent{
        val string = "won"
        val intent =  Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT,string)
        return intent
    }

}
